#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <pthread.h>
#include "Queue.h"
#include "PPFunctions.h"
#include "GenericFunctions.h"

#ifndef TRUE
#define TRUE 1
#define FALSE 0
#endif

void runPP(char* filename){

        Queue* queue = newQueue();
        double averageResult[2] = {0.0f, 0.0f};
			
        /* read in the queue from file */
        readFile(filename, queue);
        arrivalTimeSort(queue);
        queue = calcPP(queue);
        printGantt(queue);
        calcAverage(queue, averageResult);
        printf("Average waiting time = %f, Average turnaround = %f", averageResult[0], averageResult[1]);
        freeQueue(queue);
}

void prioritySort(Queue* inQueue){

    QueueNode* head = inQueue->head;
    QueueNode* insertionPointer = head;
    QueueNode* current = inQueue->head->next;

    while(current != NULL){

        insertionPointer = head;

        while(insertionPointer != current){
            if(insertionPointer->task.priority > current->task.priority){
                Task tempTask = current->task;
                current->task = insertionPointer->task;
                insertionPointer->task = tempTask;
            }

            else{
                insertionPointer = insertionPointer->next;
            }
        }
        current = current->next;
    }
}

/**********************************
* calcPP creates new queues
* to sort on priority preemptive
* algorithm. Time complexity of
* this algorithm can be improved but
* designed to mimic CPU operation
* with a processed, ready and task
* queue.
**********************************/

Queue* calcPP(Queue* inTaskQueue){
    Queue* processSched = newQueue();
    Queue* readyQueue = newQueue();
    int clockTime;
    int done = FALSE;
    clockTime = inTaskQueue->head->task.arrivalTime;

    /* if there is a delay at the start of the process list */
    if(clockTime != 0){
        
		Task task;
        clockTime = 0;
        task.arrivalTime = 0;
        task.burstTime = inTaskQueue->head->task.arrivalTime;
        task.priority = 1000;
        sprintf(task.taskName, "%s", "IDLE");
        sprintf(task.taskNum, "%-9s", "I");
        task.timeElapsed = clockTime;
        task.runningTime = 0;
        task.startTime = clockTime;
        task.timeRemaining =  inTaskQueue->head->task.arrivalTime;
        enQueue(task, processSched);
    }

    while(!done){

        while(!isEmpty(inTaskQueue) && inTaskQueue->head->task.arrivalTime == clockTime){
            enQueue(deQueue(inTaskQueue), readyQueue);
            readyQueue->tail->task.timeElapsed = clockTime;
            readyQueue->tail->task.startTime = clockTime;

            /* sort ready queue on priority */
            prioritySort(readyQueue);
        }

        /* if this is the first item in readyQueue or a process just finished add the new process to processSched */
        if(isEmpty(processSched) || processSched->tail->task.runningTime == processSched->tail->task.burstTime){

            /* case for a process just finished or first arriving process */
            if(!isEmpty(readyQueue)){
                enQueue(deQueue(readyQueue),processSched);
                processSched->tail->task.timeElapsed = clockTime;
                processSched->tail->task.startTime = clockTime;
            }

            /* CPU is in idle state, create an idle process     */
            /* and put onto process queue until another process */
            /* arrives. Used for special case where there is    */
            /* time delay in process arrival time.              */
            else{

                Task task;
                task.arrivalTime = clockTime;
                task.burstTime = inTaskQueue->head->task.burstTime;
                task.priority = 1000;
                sprintf(task.taskName, "%s", "IDLE");
                sprintf(task.taskNum, "%-9s", "I");
                task.timeElapsed = clockTime;
                task.runningTime = 0;
                task.timeRemaining =  inTaskQueue->head->task.arrivalTime - clockTime;
                task.startTime = clockTime;
                enQueue(task, processSched);
            }
        }
		
        else if(!isEmpty(readyQueue)){
            if(readyQueue->head->task.priority < processSched->tail->task.priority){

                /* split the task and put remainder back on ready queue */
                Task task;
                task.arrivalTime = processSched->tail->task.arrivalTime;
                task.burstTime = processSched->tail->task.burstTime;
                task.priority = processSched->tail->task.priority;
                sprintf(task.taskName, "%s",processSched->tail->task.taskName);
                sprintf(task.taskNum, "%s", processSched->tail->task.taskNum);
                task.runningTime = processSched->tail->task.runningTime;
                task.timeRemaining = task.burstTime - task.runningTime;
                task.timeElapsed = clockTime;
                task.startTime = clockTime;

                /* exclude ready queue enQueuing for Idle processes */
                if(!strcmp(processSched->tail->task.taskName, "IDLE") == 0){
                    enQueue(task, readyQueue);
                }

                prioritySort(readyQueue);
                enQueue(deQueue(readyQueue), processSched);
            }
			
        }/* while loop exit point */
		
        if(inTaskQueue->count == 0 && readyQueue->count == 0){
            /* update last process */
            processSched->tail->task.runningTime += processSched->tail->task.timeRemaining;
            processSched->tail->task.timeElapsed += processSched->tail->task.timeRemaining;
            processSched->tail->task.timeRemaining = 0;
            done = TRUE;
        }
        else{
            /* increment the running time of the current process */
            processSched->tail->task.runningTime++;
            processSched->tail->task.timeRemaining--;
            processSched->tail->task.timeElapsed++;
        }

        /* increment clock */
        clockTime++;
    }
	
    /* free queues */
    freeQueue(inTaskQueue);
    freeQueue(readyQueue);

    return processSched;
}

#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include "Queue.h"

#ifndef TRUE
#define TRUE 1
#define FALSE 0
#endif

void arrivalTimeSort(Queue* inQueue){

    QueueNode* head = inQueue->head;
    QueueNode* insertionPointer = head;
    QueueNode* current = inQueue->head->next;

    while(current != NULL){

        insertionPointer = head;

        while(insertionPointer != current){
            if(insertionPointer->task.arrivalTime > current->task.arrivalTime){
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

int fileExists(char* fileName){
{
		int result = FALSE;
		FILE *file;
		
		if ((file = fopen(fileName, "r")))
		{
			fclose(file);
			result = TRUE;
		}
		else{
			printf("\nNo such file, try again...\n");
			result = FALSE;
		}
		
		return result;
	}
}

void readFile(char* inFileName, Queue* inQueue){

    FILE* file;

    /* attempt file read  */
    file = fopen(inFileName, "r");

    /* File reading error */
    if(file == NULL){
        perror("Error reading file\n");
    }

    /* read the file */
    else{

        char line[100];
        int proccessNum = 0;
		
        while(fgets(line, sizeof(line), file)){

            /* create a new task struct with this line data
               and add to Queue. */
            Task task;
            proccessNum++;
            task.arrivalTime = (int)strtol(strtok(line, " "), NULL, 10);
            task.burstTime = (int)strtol(strtok(NULL, " "), NULL, 10);
            task.priority = (int)strtol(strtok(NULL, " "),NULL, 10);
            task.runningTime = 0;
            task.timeRemaining = task.burstTime;
            task.timeElapsed = 0;
            task.startTime = task.arrivalTime;
            sprintf(task.taskName, "P%3d",proccessNum);
            sprintf(task.taskNum, "%-9d",proccessNum);
            enQueue(task , inQueue);

        }

     }
	 
     /* close the file after reading */
     fclose(file);
}

void printGantt(Queue* inQueue){

    Queue* temp = copyQueue(inQueue);
    QueueNode* current = temp->head;
    int totalTime;
    int numlength = 8;
    printf("\n\nPrinting Gantt chart...\n");
    printf("key:\ndigit = process number\nI = processor idle\n\n|");

    while(current != NULL){
        printf("%s", current->task.taskNum);
        printf("|");
        current = current->next;
    }

    printf("\n");
    current = temp->head;
    totalTime = 0;

    while(current != NULL){
        int jj;

        if(totalTime > 99){
            numlength = 7;
        }

        if(totalTime < 10){
            numlength = 9;
        }
        if(totalTime > 9 && totalTime < 100){
            numlength = 8;
        }

        printf("%d", totalTime);
        for(jj = 0; jj < numlength; jj++){
            printf(" ");
        }
        totalTime = current->task.timeElapsed;
        current = current->next;

    }

    printf("%d\n",totalTime);
    printf("\n");

    /* free the copied queue */
    freeQueue(temp);
}

void calcAverage(Queue* inQueue, double inResult[2]){

    QueueNode* current = inQueue->head;
    int processCount = 0;
    int waitingTime = 0;
    int totalWaitingTime = 0;
    int turnaroundTime = 0;
    int totalTurnaroundTime = 0;
    double averageWaitingTime = 0.0f;
    double averageTurnaround = 0.0f;

    while(current!= NULL){
        if(strcmp(current->task.taskName, "IDLE") != 0 && current->task.timeRemaining == 0){
            processCount++;
            turnaroundTime = current->task.timeElapsed - current->task.arrivalTime;
            waitingTime = turnaroundTime - current->task.burstTime;
            totalWaitingTime += waitingTime;
            totalTurnaroundTime += turnaroundTime;
        }
        current = current->next;
    }
	
    averageWaitingTime = (double)totalWaitingTime/(double)processCount;
    averageTurnaround = (double)totalTurnaroundTime/(double)processCount;
    inResult[0] = averageWaitingTime;
    inResult[1] = averageTurnaround;

}


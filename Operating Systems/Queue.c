
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "Queue.h"

#ifndef TRUE
#define TRUE 1
#define FALSE 0
#endif

/*************************************
 * newQueue, creates a new
 * empty Queue with head and tail
 * pointer set to null.
 * ***********************************/
Queue* newQueue()
{
    Queue* queue = (Queue*)malloc(sizeof(Queue));
    queue->count = 0;
    queue->head = NULL;
    queue->tail = NULL;
    return queue;
}

/**************************************
 * copyQueue, creates a duplicate queue
 * ************************************/
Queue* copyQueue(Queue* inQueue){

    Queue* outQueue = newQueue();
    QueueNode* current = inQueue->head;
    int ii = 0;

    for(ii = 0; ii < inQueue->count; ii++){
        Task currentTask;
        currentTask.arrivalTime = current->task.arrivalTime;
        currentTask.burstTime = current->task.burstTime;
        currentTask.priority = current->task.priority;
        currentTask.runningTime = current->task.runningTime;
        sprintf(currentTask.taskName, "%s", current->task.taskName);
        sprintf(currentTask.taskNum, "%s", current->task.taskNum);
        currentTask.timeElapsed = current->task.timeElapsed;
        currentTask.timeRemaining = current->task.timeRemaining;
        currentTask.startTime = current->task.startTime;
        enQueue(currentTask, outQueue);
        current = current->next;
    }
    return outQueue;
}

/**************************************
 * enQueue, takes a task struct,
 * creates a new queueNode and
 * inserts it at the end of the queue.
 * ************************************/
void enQueue(Task inTask, Queue* inQueue)
{

    QueueNode* current = (QueueNode*)malloc(sizeof(QueueNode));
    current->next = NULL;
    current->prev = NULL;
    current->task = inTask;

    /* case for empty queue */
    if(isEmpty(inQueue))
    {
        inQueue->head = current;
        inQueue->tail = current;
    }

    /* case for single value list */
    else if(inQueue->head == inQueue->tail)
    {
        inQueue->tail = current;
        inQueue->tail->prev = inQueue->head;
        inQueue->head->next = inQueue->tail;
    }

    /* case for list count > 1 */
    else
    {
        inQueue->tail->next = current;
        current->prev = inQueue->tail;
        inQueue->tail = current;
    }

    inQueue->count++;
}


/********************************************
 * deQueue, removes the first node in
 * the Queue.
 * *****************************************/
Task deQueue(Queue* inQueue)
{
    Task task;
    task.arrivalTime = 0;
    task.burstTime = 0;
    task.priority = 0;

    /* print error message if list is empty */
    if(isEmpty(inQueue))
    {
        printf("Error, queue is empty!");
    }

    /* case for 1 value */
    else if(inQueue->head->next == NULL)
    {
        task = inQueue->head->task;
        freeNode(inQueue->head);
        inQueue->head = NULL;
        inQueue->tail = NULL;
        inQueue->count--;
    }

    /* case for 2 values */
    else if(inQueue->head->next == inQueue->tail)
    {
        task = inQueue->head->task;
        inQueue->tail->prev = NULL;
        freeNode(inQueue->head);
        inQueue->head = inQueue->tail;
        inQueue->count--;
    }

    /* case for Queue count > 2 */
    else
    {
        task = inQueue->head->task;
        inQueue->head = inQueue->head->next;
        freeNode(inQueue->head->prev);
        inQueue->head->prev = NULL;
        inQueue->count--;
    }

    return task;

}


/***********************************
 * Print queue, prints all values
 * stored in queue.
 * *********************************/
void printQueue(Queue* inQueue)
{

    QueueNode* current = inQueue->head;

    /* traverse queue from head and print values */
    printf("name arrival running priority elapsed remaining burst  start\n");
    while(current != NULL)
    {
        printf("%s  %5d  %5d     %5d    %5d   %5d  %5d   %5d\n", current->task.taskName, current->task.arrivalTime, 
		current->task.runningTime, current->task.priority, current->task.timeElapsed, current->task.timeRemaining, 
		current->task.burstTime, current->task.startTime);
		
        current = current->next;
    }

}

QueueNode* getIndex(Queue* inQueue, int inIndex){
    QueueNode* node = inQueue->head;
    int ii = 0;

    if(inIndex <= inQueue->count){
        while(inIndex != ii){
            node = node->next;
            ii++;
        }
    }
    else{
        printf("Error, index does not exist in Queue!");
    }

    return node;
}

/***********************************
 * freeNode, call to free individual
 * node.
 * *********************************/
void freeNode(QueueNode* inNode)
{
    free(inNode);
    inNode = NULL;
}

/**************************************
 * freeQueue, call to free entire
 * queue in case of error.
 * ***********************************/
void freeQueue(Queue* inQueue)
{

    while(!isEmpty(inQueue))
    {

        (void)deQueue(inQueue);
    }

    free(inQueue);
    inQueue = NULL;
}

/************************************
 * isEmpty, returns a boolean, false
 * if Queue is populated, true if not.
 * ********************************/
int isEmpty(Queue* inQueue)
{
    int empty = FALSE;

    if(inQueue->count == 0)
    {
        empty = TRUE;
    }
    return empty;
}

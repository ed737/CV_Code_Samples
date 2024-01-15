#ifndef QUEUE_H
#define QUEUE_H

/****************************
 * definition for struct to
 * create Queue node
 * for use with the above
 * functions. Stores a Task
 * struct with arrival time,
 * burst time and priority
 * **************************/
typedef struct Task{
    char taskName[5];
    char taskNum[10];
    int arrivalTime;
    int burstTime;
    int priority;
    int runningTime;
    int timeRemaining;
    int timeElapsed;
    int startTime;
}Task;

typedef struct QueueNode{
    Task task;
    struct QueueNode* next;
    struct QueueNode* prev;
} QueueNode;

/****************************
 * Queue  struct. stores
 * the head and tail pointers
 * of the List as well
 * as and int for count.
 * **************************/
typedef struct Queue {
    int count;
    int totalTime;
    QueueNode* head;
    QueueNode* tail;
} Queue;

Queue* newQueue();
Queue* copyQueue(Queue* inQueue);
void enQueue(Task inTask, Queue* inQueue);
Task deQueue(Queue* inQueue);
void printQueue(Queue* inQueue);
QueueNode* getIndex(Queue* inQueue, int inIndex);
void freeQueue(Queue* inQueue);
void freeNode(QueueNode* inNode);
int isEmpty(Queue* inQueue);

#endif

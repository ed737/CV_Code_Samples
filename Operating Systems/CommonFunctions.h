
#ifndef COMMON_FUNCTIONS_H
#define COMMON_FUNCTIONS_H

#include "Queue.h"

void arrivalTimeSort(Queue* inQueue);
void printGantt(Queue* inQueue);
void calcAverage(Queue* inQueue, double inResult[2]);
void readFile(char* inFileName, Queue* inQueue);
int fileExists(char* fileName);

#endif


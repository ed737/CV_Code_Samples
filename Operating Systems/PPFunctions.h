#ifndef PPFUNCTIONS_H
#define PPFUNCTIONS_H

#include "Queue.h"

void runPP(char* filename);
Queue* calcPP(Queue* inTaskQueue);
void prioritySort(Queue* inQueue);

#endif

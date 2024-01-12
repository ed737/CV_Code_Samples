#include <stdio.h>
#include <pthread.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include "Queue.h"
#include "GenericFunctions.h"
#include "PPFunctions.h"
#include "SRTFFunctions.h"

#ifndef TRUE
#define TRUE 1
#define FALSE 0
#endif

void* runParentThread(void* arg);
void* runSRTFThread(void* arg);
void* runPPThread(void* arg);

/* global variables and flags for pthread */
char buffer1[10];
char buffer2[100];
int done;
int programFinished;
int threadFinished;
int resultReady;
int filenameReady;
int terminalBusy;
int loopPP;
int loopSRTF;
int childThreadClose;
int parentThreadClose;

pthread_t ppThread;
pthread_t srtfThread;
pthread_t parentThread;
pthread_mutex_t buffer1Lock;
pthread_cond_t buffer1Cond;
pthread_mutex_t buffer2Lock;
pthread_cond_t buffer2Cond;
pthread_mutex_t terminalLock;
pthread_cond_t terminalCond;
pthread_mutex_t runSRTFLock;
pthread_cond_t runSRTFCond;
pthread_mutex_t runPPLock;
pthread_cond_t runPPCond;
pthread_mutex_t threadCloseLock;
pthread_cond_t threadCloseCond;
pthread_cond_t programFinishedCond;
pthread_mutex_t programFinishedLock;

void* runParentThread(void* arg){

     int validFile = FALSE;
     childThreadClose = FALSE;
     parentThreadClose = FALSE;
     done = FALSE;

     while(done == FALSE){


        /* initialize all pthread variables */
        pthread_mutex_init(&buffer1Lock, NULL);
        pthread_cond_init(&buffer1Cond, NULL);
        pthread_mutex_init(&buffer2Lock, NULL);
        pthread_cond_init(&buffer2Cond, NULL);
        pthread_mutex_init(&terminalLock, NULL);
        pthread_cond_init(&terminalCond, NULL);
        pthread_mutex_init(&threadCloseLock, NULL);
        pthread_cond_init(&threadCloseCond, NULL);
        pthread_mutex_init(&runSRTFLock, NULL);
        pthread_cond_init(&runSRTFCond, NULL);
        pthread_mutex_init(&runPPLock, NULL);
        pthread_cond_init(&runPPCond, NULL);

        /* create child threads */
        pthread_create(&ppThread, NULL, runPPThread, NULL);
        pthread_create(&srtfThread, NULL, runSRTFThread, NULL);

        strcpy(buffer1, "");
        strcpy(buffer2, "");
        terminalBusy = FALSE;
        filenameReady = FALSE;
		
        pthread_mutex_lock(&buffer1Lock);

        do{
            printf("\nPP and SRTF simulation:");
            printf("Please enter a file name or QUIT to exit...\n");
            scanf("%s", buffer1);
			
            if(strcmp(buffer1, "QUIT") == 0 || strcmp(buffer1, "quit") == 0 ){
                validFile = TRUE;
            }
            else{
                validFile = fileExists(buffer1);
            }

        } while(!validFile);

        printf("\n\n!!!!!!! %s !!!!!!!!\n\n", buffer1);

        if(strcmp(buffer1, "QUIT") != 0 && strcmp(buffer1, "quit") != 0 ){

            filenameReady = TRUE;
            resultReady = FALSE;

            /* signal other threads to read file */
            loopSRTF = TRUE;
            loopPP = TRUE;
			
            pthread_cond_signal(&runSRTFCond);
            pthread_mutex_unlock(&runSRTFLock);
            pthread_cond_signal(&runPPCond);
            pthread_mutex_unlock(&runPPLock);
            pthread_cond_signal(&buffer1Cond);
            pthread_mutex_unlock(&buffer1Lock);

            /* wait for other threads to return average data*/
            pthread_mutex_lock(&buffer2Lock);
            while(resultReady == FALSE){
                pthread_cond_wait(&buffer2Cond, &buffer2Lock);
            }

            pthread_mutex_lock(&terminalLock);
            while(terminalBusy == TRUE){
                pthread_cond_wait(&terminalCond, &terminalLock);
            }

            terminalBusy = TRUE;
            printf("\n%s \n", buffer2);
            resultReady = FALSE;
            terminalBusy = FALSE;
            pthread_cond_signal(&terminalCond);
            pthread_mutex_unlock(&terminalLock);
            pthread_cond_signal(&buffer2Cond);
            pthread_mutex_unlock(&buffer2Lock);

            /* first result has been printed */
			
            pthread_mutex_lock(&buffer2Lock);
            while(resultReady == FALSE){
                pthread_cond_wait(&buffer2Cond, &buffer2Lock);
            }

            pthread_mutex_lock(&terminalLock);
            while(terminalBusy == TRUE){
                pthread_cond_wait(&terminalCond, &terminalLock);
            }
			
            terminalBusy = TRUE;
            printf("\n%s \n", buffer2);
            terminalBusy = FALSE;
            pthread_mutex_unlock(&terminalLock);
            pthread_cond_signal(&terminalCond);
            pthread_mutex_unlock(&buffer2Lock);
            pthread_cond_signal(&buffer2Cond);

        }

        /* exit case */
        else{
             printf("Exiting\n");
            done = TRUE;
            loopSRTF = TRUE;
            loopPP = TRUE;
			
            pthread_cond_signal(&runSRTFCond);
            pthread_mutex_unlock(&runSRTFLock);
            
			pthread_cond_signal(&runPPCond);
            pthread_mutex_unlock(&runPPLock);
            
			pthread_mutex_lock(&threadCloseLock);
            
			while(parentThreadClose == FALSE){
				pthread_cond_wait(&threadCloseCond, &threadCloseLock);
            }
			
            terminalBusy = TRUE;
            printf("\nParent Thread Terminating.\n");
			
            pthread_cond_signal(&terminalCond);
            pthread_mutex_unlock(&terminalLock);
            
			terminalBusy = FALSE;
        }

        /* close child threads */

        pthread_join(ppThread, NULL);
        pthread_join(srtfThread, NULL);
    }

    /* signal program end */
    programFinished = TRUE;
    pthread_cond_signal(&programFinishedCond);
    pthread_mutex_unlock(&programFinishedLock);

    return NULL;
}

void* runSRTFThread(void* arg){

    Queue* queue = newQueue();
    char filename[10];
    double averageResult[2];

    pthread_mutex_lock(&runSRTFLock);
	
    while(!loopSRTF){
        pthread_cond_wait(&runSRTFCond, &runSRTFLock);
    }
	
    loopSRTF = FALSE;

    if(!done){
		
        pthread_mutex_lock(&buffer1Lock);
		
        while(!filenameReady){
            pthread_cond_wait(&buffer1Cond, &buffer1Lock);
        }
		
        strcpy(filename, buffer1);

        /* read in the queue from file */
        readFile(filename, queue);

        pthread_cond_signal(&buffer1Cond);
        pthread_mutex_unlock(&buffer1Lock);

        arrivalTimeSort(queue);
        queue = calcSRTF(queue);
		
        pthread_mutex_lock(&terminalLock);
		
        while(terminalBusy == TRUE){
            pthread_cond_wait(&terminalCond, &terminalLock);
        }
		
        terminalBusy = TRUE;
		
        printf("Printing SRTF:\n");
        printQueue(queue);
        printGantt(queue);
        terminalBusy = FALSE;
		
        pthread_cond_signal(&terminalCond);
        pthread_mutex_unlock(&terminalLock);
		
        calcAverage(queue, averageResult);
		
        while(resultReady == TRUE){
            pthread_cond_wait(&buffer2Cond, &buffer2Lock);
        }
		
        sprintf(buffer2, "\nSRTF, average waiting time = %f, average turnaround time = %f\n", averageResult[0], averageResult[1]);
        resultReady = TRUE;
		
        pthread_cond_signal(&buffer2Cond);
        pthread_mutex_unlock(&buffer2Lock);

    } /* done */
	
    else{
		
        pthread_mutex_lock(&terminalLock);
        terminalBusy = TRUE;
        printf("\nSRTF Thread Terminating.\n");
        terminalBusy = FALSE;
		
        pthread_cond_signal(&terminalCond);
        pthread_mutex_unlock(&terminalLock);

        if(childThreadClose == FALSE){
            childThreadClose = TRUE;
        }
		
        else{
            parentThreadClose = TRUE;
            pthread_cond_signal(&threadCloseCond);
            pthread_mutex_unlock(&threadCloseLock);
        }

    }
	
    freeQueue(queue);
    return NULL;

}


void* runPPThread(void* arg){

    Queue* queue = newQueue();
    char filename[10];
    double averageResult[2];


    pthread_mutex_lock(&runPPLock);
    while(!loopPP){
        pthread_cond_wait(&runPPCond, &runPPLock);
    }
    loopPP = FALSE;
    if(!done){
        pthread_mutex_lock(&buffer1Lock);
        while(!filenameReady){
            pthread_cond_wait(&buffer1Cond, &buffer1Lock);
        }

        strcpy(filename, buffer1);
         /* read in the queue from file */
        readFile(filename, queue);
        arrivalTimeSort(queue);
        /* signal other thread if ready */
        pthread_cond_signal(&buffer1Cond);
        pthread_mutex_unlock(&buffer1Lock);


        queue = calcPP(queue);

        /* lock terminal while printing */
        pthread_mutex_lock(&terminalLock);
        while(terminalBusy == TRUE && !done){
            pthread_cond_wait(&terminalCond, &terminalLock);
        }
        terminalBusy = TRUE;
        printf("Printing PP:\n");
        printQueue(queue);
        printGantt(queue);
        terminalBusy = FALSE;
        pthread_cond_signal(&terminalCond);
        pthread_mutex_unlock(&terminalLock);

        calcAverage(queue, averageResult);
        /* pthread_mutex_lock(&buffer2Lock); */
        while(resultReady == TRUE){
            pthread_cond_wait(&buffer2Cond, &buffer2Lock);
        }
        sprintf(buffer2, "\nPP, average waiting time = %f, average turnaroung time = %f\n", averageResult[0], averageResult[1]);
        resultReady = TRUE;
        pthread_cond_signal(&buffer2Cond);
        pthread_mutex_unlock(&buffer2Lock);

    }
    else{

        pthread_mutex_lock(&terminalLock);
        while(terminalBusy){
            pthread_cond_wait(&terminalCond, &terminalLock);
        }

        terminalBusy = TRUE;
        printf("\nPP Thread Terminating.\n");
        terminalBusy = FALSE;
        pthread_cond_signal(&terminalCond);
        pthread_mutex_unlock(&terminalLock);

        if(childThreadClose == FALSE){
            childThreadClose = TRUE;
        }
        else{
            parentThreadClose = TRUE;
            pthread_cond_signal(&threadCloseCond);
            pthread_mutex_unlock(&threadCloseLock);
        }
    }

    freeQueue(queue);

    return NULL;

}

int main(int argc, char** argv){

    done = FALSE;
    terminalBusy = FALSE;
    programFinished = FALSE;

    pthread_mutex_init(&programFinishedLock,NULL);
    pthread_cond_init(&programFinishedCond, NULL);

    /* create parent thread */
    pthread_create(&parentThread, NULL, runParentThread, NULL);

    /* halt main thread until program is done. */
    pthread_mutex_lock(&programFinishedLock);
	
    while(programFinished == FALSE){
        pthread_cond_wait(&programFinishedCond, &programFinishedLock);
    }

    /* close parent thread */
    pthread_join(parentThread, NULL);

    /* destroy pthread variables */
    pthread_mutex_destroy(&buffer1Lock);
    pthread_cond_destroy(&buffer1Cond);
    pthread_mutex_destroy(&buffer2Lock);
    pthread_cond_destroy(&buffer2Cond);
    pthread_mutex_destroy(&terminalLock);
    pthread_cond_destroy(&terminalCond);

    return 0;
}

#include <stdio.h>
#include <pthread.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include "Queue.h"
#include "SRTFFunctions.h"
#include "CommonFunctions.h"

#ifndef TRUE
#define TRUE 1
#define FALSE 0
#endif

// this executable begins 
// shortest remaining time first
// scheduling if requested by user.
  
int main(int argc, char** argv){

    char filename[10];
    int done = FALSE;
    int validFile = FALSE;

    while(done == FALSE){
        do{

            printf("\nSRTF simulation:");
            printf("Please enter a file name or QUIT to exit...\n");
			
            scanf("%s", filename);
			
            if(strcmp(filename, "QUIT") == 0 || strcmp(filename, "quit") == 0 ){
                validFile = TRUE;
            }
			
            else{
                validFile = fileExists(filename);
            }

    	} while(!validFile);

        if(strcmp(filename, "QUIT") == 0 || strcmp(filename, "quit") == 0 ){
            printf("Exiting\n");
            done = TRUE;
        }

        else{
            runSRTF(filename);
        }
    }
	
    return 0;
}

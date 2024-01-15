CFLAGS = -c -Wall -Werror -ansi -pedantic -g
CC = gcc
CC2 = gcc -pthread
EXE1 = PP
EXE2 = SRTF
EXE3 = simulation

all: $(EXE1) $(EXE2) $(EXE3)

$(EXE1): PP.o CommonFunctions.o PPFunctions.o Queue.o
	$(CC) PP.o CommonFunctions.o PPFunctions.o Queue.o -o $(EXE1) -lm

$(EXE2): SRTF.o CommonFunctions.o SRTFFunctions.o Queue.o
	$(CC) SRTF.o CommonFunctions.o SRTFFunctions.o Queue.o -o $(EXE2) -lm

$(EXE3): simulation.o CommonFunctions.o SRTFFunctions.o PPFunctions.o Queue.o
	$(CC2) simulation.o CommonFunctions.o SRTFFunctions.o PPFunctions.o Queue.o -o $(EXE3) -lm

simulation.o: simulation.c
	$(CC2) simulation.c $(CFLAGS)

PP.o: PP.c
	$(CC) PP.c $(CFLAGS)

SRTF.o: SRTF.c
	$(CC) SRTF.c $(CFLAGS)

CommonFunctions.o: CommonFunctions.c CommonFunctions.h Queue.h
	$(CC) CommonFunctions.c CommonFunctions.h Queue.h $(CFLAGS)

PPFunctions.o: PPFunctions.c PPFunctions.h Queue.h
	$(CC) PPFunctions.c PPFunctions.h Queue.h $(CFLAGS)

SRTFFunctions.o: SRTFFunctions.c SRTFFunctions.h Queue.h
	$(CC) SRTFFunctions.c SRTFFunctions.h Queue.h $(CFLAGS)

Queue.o: Queue.c Queue.h
	$(CC) Queue.c Queue.h $(CFLAGS)

clean:
	rm *.o $(EXE1)
	rm *.o $(EXE2)
	rm *.o $(EXE3)


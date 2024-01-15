CPU SCHEDULING SIMULATION PROGRAMS
Edward Munns, Curtin University 
semester 1 2021

DESCRIPTION:

This software is designed to simulate two common
CPU scheduling algorithms, the Shortest Job First
Premptive algorithm and the Priority Premptive 
Algorithm using the pthread library in Linux.
 
Running make will produce 3 exeutablefiles:
-PP, priority premptive only
-SRTF, shortest remaining time first only
-simulation, a multithreaded application that calculates both simultaneousy. 

The programs asks the user to imput a filename, reads
data from file and produces the gantt chart,
average waiting time and average turnaround time
for the respective algorithm(s). An element to
deal with idle CPU states has also been implimented
with idle periods displaying "I" on the gantt chart.
These idle periods have been excluded from average 
calculations apart from the time delay to later processes.


Building:

To build the 3 applications in a Linux environment, 
navigate to the file directory from the terminal and type "make". 
This will build all three programs. 
To run the programs type "./<program name>"
(ie, ./PP, ./SRTF or ./simulation)

TESTING:

Testing of the programs was carried out in Ubuntu linux by running a series 
of pre-calculted datasets and checking for result accuracy. The multi-threaded
section of the program was repeatedly run to ensure threads were running 
concurrently where appropriate. Note this concurrency results in the displaying
of average times and gantt charts in an unspecified printing order.
This was left in the program to demopnstrate concurrency. 
The program was also memory tested using valgrind with no errors found.  

SYCRONIZATION HANDLING:

Syncronization issues were handled in the simulation program via the use of conditional variables (mutex locks).
Locks were used to remedy race conditions for terminal use, filename and 
averaging data buffers as well as on thread execution timing and thread destruction. Program flow is as follows:

1. All threads are created.
2. Child threads (PP and SRTF) wait for parent thread
3. User inputs filename
4. Parent thread sends child threads filename, they read data and calculate
   averages while the parent thread waits.
5. Upon average calculation for each child thread the averages are sent to the
   parent thread which then prints results. Race conditions through this section
   are handled with mutex locks.
6. Mutex locks were also used for thread destruction as child threads had to 
   be closed before the parent thread to prevent the creation of zombie threads.
   This was implemented by setting the parent thread to hold waiting while the 
   2 child threads were closed, with this closing triggering the parent thread
   to close.    
  
LIMITATIONS:

Requires Linux environment and the pthread library
For clear terminal output the number of input tasks is 
limited by the terminal line character width
(10 tasks maximum in testing).

OUTPUT EXAMPLES:

Images of the output of the simulation program can be found in the EXAMPLE folder 
along with the file "data1" which was the input file for the given output.

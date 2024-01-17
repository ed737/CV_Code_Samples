/*
 * timer.cpp
 *
 * Created: 10/08/2022 8:49:20 PM
 *  Author: ed
 */ 

#include "timer1.h"
#include "data.h"

extern State state;

// when this value reaches 15 PID calculation is performed
volatile uint8_t calcPIDcounter;

/*****************************************
* PID and sensor reading timer functions
*
* PID update rate = 64 ms
* 16 Error samples taken each PID update per channel
* Error sample taken every 4ms (interrupt frequency for this timer)
* error sample frequency = 250 Hz
* PID update frequency 15.625 Hz 
* F_cpu = 16MHZ
* F_cpu/F_error = 64000 so we don't need a pre scaler as top is 2^16
* set interrupt on compare A to 64000-1
* CTC mode
*******************************************/
 
void timer1_PID_init(){
	// CTC mode, TOP is OCR1A 
	TCCR1B |= (1 << WGM12);

	// set the compare register to 64000 - 1
	// this is a 16 bit register but compiler handles writing to high and low bytes
	OCR1A = 0xF9FF; // 63999
}


 void start_PID_timer(){

	// start conversion on first error values
	adc_request_read();
	
	calcPIDcounter = 0;

	TCCR1B &= ~((1<<CS12) | (1<<CS11) | (1<<CS10)); // reset clock select bits
	TIMSK1 |= (1<<OCIE1A); // enable interrupt on compare match 
	TCCR1B |= (1<<CS10); // start timer with system clock no divider
 }

 
 void stop_PID_timer(){
	TCCR1B &= ~((1<<CS12) | (1<<CS11) | (1<<CS10)); // set timer clock select to no source, stop timer
	TIMSK1 &= ~(1<<OCIE1A); // disable interrupt
 }


 // this interrupt updates error samples until we have 16 samples, then we calculate the PID values 
 ISR (TIMER1_COMPA_vect){

	if(calcPIDcounter == 15){
		
		// set the calculate PID flag in the state struct
		state.calcPID = true;
		// reset PID counter
		calcPIDcounter = 0;

	}

	else{
		adc_request_read();
		calcPIDcounter++;	
	}
 }

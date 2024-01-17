/*
 * micro.cpp
 *
 * Created: 3/08/2022 4:40:54 PM
 * Author : ed
 */ 

// define CPU frequency
#ifndef F_CPU
#define F_CPU 16000000UL
#endif

#include <avr/io.h>
#include <util/delay.h>
#include <avr/interrupt.h>
#include <inttypes.h>
#include <stdbool.h>
#include <stdlib.h>
#include <string.h>
#include "board.h"
#include "data.h"
#include "adc.h"
#include "PID.h"
#include "timer1.h"
#include "usart.h"
#include "adc.h"


State state;

void handle_cartMode_change(void);

int main(void)
{
	// initialize board
	board_init();
	usart_init(9600);
	data_init();
	timer1_PID_init();
	adc_init();
	_delay_ms(500);

	// enable interrupts
	sei();

    /* Main loop */
    while (1) 
    {
		
		// handle state flags
		// these should be organized in order of 
		// importance to program flow
		if(state.messageRecieved){
			usart_message_recieved();
		}

		if(state.cartModeChanged){
			handle_cartMode_change();
		}
		if(state.calcPID){
			calc_PID();
			state.calcPID = false;
		}
		if(state.loadDataFromEEPROM){
			
		}

		if(state.saveDataToEEPROM){

		}

		
		// main state machine switch statement
		switch(state.cartMode){
			// most of these case statements are not used as all is done with interrupts
			// every state left here in main to demonstrate the program flow of the state machine
			case unavailable:
			break;
			
			case standby:
			break;
			
			case manual:
			break;
			
			case autoLineFollow:
				
			break;

			case tuning:
			
			break;

			case loadData:
			break;

			case saveData:
			break;

		}
		
	}
    
}


void handle_cartMode_change(){

	// this switch statement to turn off
	// any state related hardware
	switch(state.previousCartMode){
		// this case not used on controller side, added just for PC code clarity
		case unavailable:
		break;
		
		case standby:
		break;
		
		case manual:
			setDACLeft_Value(STOP);
			setDACRight_Value(STOP);
		break;
		
		case autoLineFollow:
			stop_PID_timer();
			adc_off();
			setDACLeft_Value(STOP);
			setDACRight_Value(STOP);
			// stop_PID_timer();
		break;
		
		case tuning:
			setDACLeft_Value(STOP);
			setDACRight_Value(STOP);
		break;

		case loadData:
		break;

		case saveData:
		break;
	}

	
	// this switch statement to turn on new
	// state related hardware etc
	switch(state.cartMode){
		// this case not used on controller side, added just for PC code clarity
		case unavailable:
		break;
		
		case standby:
		break;
		
		case manual:
		break;
		
		case autoLineFollow:
			adc_init();
			start_PID_timer();
			
		break;
		
		case tuning:
		
		break;

		case loadData:
		break;

		case saveData:
		break;
	}

	state.cartModeChanged = false;
}

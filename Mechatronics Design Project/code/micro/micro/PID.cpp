/*
 * PID.cpp
 *
 * Created: 5/08/2022 3:50:46 PM
 *  Author: ed

 * NOTE: This PID calculation uses fixed point integers
 * our dt value per PID update = 64 ms, this is represented as 64
 * so our resolution is 0.001 for the PID coefficients and for sample rate and error sampling rate
 * this will require 32 bit integer mathematics
 */ 


// PID algorithm: u(t) = Kp*e(t) + Ki* { integral(0,t) of the e(t)dt } + Kd * d/dt * e(t)
// udot(t) = Kp * edot(t) + Ki*e(t)
// where udot(t) = du/dt
// edot(t) = de/dt
 
// approximation: udot(t) = (uk+1 - uk)/sample time, edot(t) = (ek+1 - ek)/sample time
// final equation: uk+1 = uk + Kp*(ek+1 - ek) + Ki * sample time * ek


// NOTES: use a dt value that is a power of 2 to allow bit shift division and multiplication
// for both sensor reading and PID calculation
// if possible also use a byte format for the kP, kI and kD values (ideally also a power of 2)

#include "PID.h"
#include "data.h"
#include "usart.h"
#include "string.h"

#define PID_MESSAGE_ARRAY_LENGTH 10

volatile uint16_t messageTimerByte = 0; 

extern State state;

uint8_t pidMessageArray[PID_MESSAGE_ARRAY_LENGTH];


void init_PID(){
	defaultPID(LEFT_SIDE);
	defaultPID(RIGHT_SIDE);
}


void defaultPID(bool side){
	if(side == LEFT_SIDE){
		state.leftPID.previousError = 0;
		state.leftPID.newError = 0;
		state.leftPID.previousDACValue = STOP;
		state.leftPID.newDACValue = STOP; // set to stop
		setDACLeft_Value(state.leftPID.newDACValue);
		state.leftPID.kP = 1;
		state.leftPID.kI = 1;
		state.leftPID.kD = 1;
		state.leftPID.Dt = PID_UPDATE_DT;
		state.leftPID.kIDt = state.leftPID.kI * state.leftPID.Dt; // update this value to avoid multiplication in PID calculation
		//state.leftPID.sensorReadingArrayIndex = 0;
		state.leftPID.summedErrorSamples = 0;
		/*
		// zero the error sample array
		for(uint8_t ii = 0; ii < ERROR_SAMPLES_PER_PID_UPDATE; ii++){
			state.leftPID.sensorReadingArray[ii] = 0;
		}
		*/

	}
	else{
		state.rightPID.previousError = 0;
		state.rightPID.newError = 0;
		state.rightPID.previousDACValue = STOP;
		state.rightPID.newDACValue = STOP; // set to stop
		setDACRight_Value(state.rightPID.newDACValue);
		state.rightPID.previousError = 0;
		state.rightPID.kP = 1;
		state.rightPID.kI = 1;
		state.rightPID.kD = 1;
		state.rightPID.Dt = PID_UPDATE_DT;
		state.rightPID.kIDt = state.rightPID.kI * state.rightPID.Dt; // update this value to avoid multiplication in PID calculation
		state.rightPID.summedErrorSamples = 0;
		//state.rightPID.sensorReadingArrayIndex = 0;
		
		/*
		// zero the error sample array
		for(uint8_t ii = 0; ii < ERROR_SAMPLES_PER_PID_UPDATE; ii++){
			state.rightPID.sensorReadingArray[ii] = 0;
		}
		*/
	}
}

 void calc_PID(){



	// copy old error and duty cycle values into previous variables
	state.leftPID.previousError = state.leftPID.newError;
	state.rightPID.previousError = state.rightPID.newError;
	state.leftPID.previousDACValue = state.leftPID.newDACValue;
	state.rightPID.previousDACValue = state.rightPID.newDACValue;

	//uint16_t summedLeftError = 0;
	//uint16_t summedRightError = 0;
	/*
	for(uint8_t ii = 0; ii < 16; ii++){
		summedLeftError += state.leftPID.sensorReadingArray[ii];
		summedRightError += state.rightPID.sensorReadingArray[ii];
	}
	*/
	//pidMessageArray[0] = state.leftPID.sensorReadingArray[0];
	//pidMessageArray[1] = state.rightPID.sensorReadingArray[0];
	
	// divide the summed error samples by the 16 samples this PID update
	uint8_t averagedLeftErrorSample = state.leftPID.summedErrorSamples >> 0x04;
	uint8_t averagedRightErrorSample = state.rightPID.summedErrorSamples >> 0x04;
	
	/*
	if(averagedLeftErrorSample < 200 && averagedLeftErrorSample > 100){
		averagedLeftErrorSample = 0;
	}
	else{
		averagedLeftErrorSample = 8;
	}

	if(averagedRightErrorSample < 200 && averagedRightErrorSample > 100){
		averagedRightErrorSample = 0;
	}
	else{
		averagedRightErrorSample = 8;
	}
	*/

	//uint8_t averagedRightErrorSample = state.rightPID.summedErrorSamples >> 0x04;

	// add the averaged sensor values to the data report
	pidMessageArray[0] = averagedLeftErrorSample;
	pidMessageArray[1] = averagedRightErrorSample;

	// zero the summed errors
	state.leftPID.summedErrorSamples = 0;
	state.rightPID.summedErrorSamples = 0;

	

	// subtract the mean of the error samples from the set point
	//state.leftPID.newError = (int16_t)(state.leftPID.previousDACValue - averagedLeftErrorSample);
	//state.rightPID.newError = (int16_t)(state.rightPID.previousDACValue - averagedRightErrorSample);

	// calculate new DAC values

	// left side
	// kp term:
	int16_t l_kp_term = (int16_t)((state.leftPID.kP * (state.leftPID.newError - state.leftPID.previousError))); //need to divide by 8 for this
	int16_t l_ki_term = (int16_t)(state.leftPID.kIDt * state.leftPID.previousError); // needs to divide by 8
	int16_t l_summed_terms = (l_kp_term + l_ki_term);
	int8_t l_scaled = l_summed_terms/64;
	state.leftPID.newDACValue = (uint8_t)(state.leftPID.previousDACValue + l_scaled);
	
	/*
	if(averagedRightErrorSample > 0 && state.leftPID.previousDACValue > 155){
		state.leftPID.newDACValue -= 1;
	}
	else if(averagedLeftErrorSample == 0 && state.leftPID.previousDACValue < 254){
		state.leftPID.newDACValue += 1;
	}

	if(averagedLeftErrorSample > 0 && state.rightPID.previousDACValue > 155){
		state.rightPID.newDACValue -= 1;
	}
	else if(averagedLeftErrorSample == 0 && state.rightPID.previousDACValue < 254){
		state.rightPID.newDACValue += 1;
	}
	*/

	pidMessageArray[2] = state.leftPID.newDACValue;

	// right side
	// kp term:
	
	int16_t r_kp_term = (int16_t)((state.rightPID.kP * (state.rightPID.newError - state.rightPID.previousError)));
	int16_t r_ki_term = (int16_t)(state.rightPID.kIDt * state.rightPID.previousError);
	int16_t r_summed_terms = (r_kp_term + r_ki_term);
	int8_t r_scaled = (uint8_t)(r_summed_terms/64);
	state.rightPID.newDACValue = state.rightPID.previousDACValue + r_scaled;
	
	pidMessageArray[3] = state.rightPID.newDACValue;
	
	//state.rightPID.newDACValue = 127;
	setOutput();

	messageTimerByte++;

	// use this if statement to change serial report update rate
	if(messageTimerByte == 10){
		send_PID_data_report();
		messageTimerByte = 0;
	}

 }

 void send_PID_data_report(){
	usart_send_bytes(pidMessageArray, 4, AUTO_MODE_DATA_REPORT);
 }

 void setOutput(){
	// set the DAC ports with new PID values 
	setDACLeft_Value(state.leftPID.newDACValue);
	setDACRight_Value(state.rightPID.newDACValue);
 }

// DAC port 1
// arduino pins:    MSB{pin2,  pin3,  pin4,  pin5,  pin6,  pin7,   pin8,   pin9}LSB
// atmel pins       MSB{PD2,  PD3,  PD4,  PD5,  PD6,  PD7,   PB0,   PB1}LSB
void setDACLeft_Value(uint8_t inDACValue){
	
	PORTD &= ~0xFC; // clear pins 7 to 2 of port D
	PORTD |= ((inDACValue & BIT7) ? (1<<PORTD2) : (0<<PORTD2));
	PORTD |= ((inDACValue & BIT6) ? (1<<PORTD3) : (0<<PORTD3));
	PORTD |= ((inDACValue & BIT5) ? (1<<PORTD4) : (0<<PORTD4));
	PORTD |= ((inDACValue & BIT4) ? (1<<PORTD5) : (0<<PORTD5));
	PORTD |= ((inDACValue & BIT3) ? (1<<PORTD6) : (0<<PORTD6));
	PORTD |= ((inDACValue & BIT2) ? (1<<PORTD7) : (0<<PORTD7));

	PORTB &= ~0x03; // reset bits 0 and 1 of port B
	PORTB |= ((inDACValue & BIT1) ? (1<<PORTB0) : (0<<PORTB0));
	PORTB |= ((inDACValue & BIT0) ? (1<<PORTB1) : (0<<PORTB1));
}

// DAC port 2
// arduino pins:    MSB{D10,  D11,  A0,  A1,  A5,  A4,  A3,  A2}LSB
// atmel pins       MSB{PB2,  PB3,  PC0, PC1, PC5, PC4, PC3, PC2}LSB
void setDACRight_Value(uint8_t inDACValue){

	PORTB &= ~0x0C; // reset bit 2 and 3 of port B
	PORTB |= ((inDACValue & BIT7) ? (1<<PORTB2) : (0<<PORTB2));
	PORTB |= ((inDACValue & BIT6) ? (1<<PORTB3) : (0<<PORTB3));
	 
	PORTC &= ~0x3F; // reset port C bits 0-5
	PORTC |= ((inDACValue & BIT5) ? (1<<PORTC0) : (0<<PORTC0));
	PORTC |= ((inDACValue & BIT4) ? (1<<PORTC1) : (0<<PORTC1));
	PORTC |= ((inDACValue & BIT3) ? (1<<PORTC5) : (0<<PORTC5));
	PORTC |= ((inDACValue & BIT2) ? (1<<PORTC4) : (0<<PORTC4));
	PORTC |= ((inDACValue & BIT1) ? (1<<PORTC3) : (0<<PORTC3));
	PORTC |= ((inDACValue & BIT0) ? (1<<PORTC2) : (0<<PORTC2));
}



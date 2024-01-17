/*
 * timer0.cpp
 *
 * Created: 5/09/2022 9:32:15 AM
 *  Author: ed
 */ 
 #include "timer0.h"
 #include "data.h"
 


 /*********************
 * PID and Sensor Sampling timer
 * sensor values are averaged
 * from the left and right sensor 
 * sample arrays in data.h
 * and the sample count in the state struct
 * start with PID dt value of 100ms
 * fcpu = 16MHz, fPID = 100ms = 
 * TOP = 100
 * switch to higher/lower 
 * OCR0B = duty cycle 
 * MClk = 16Mhz
 * Divisor = 8
 * f = 16MHz/8*100 = 200kHz
 **********************/
 /*
 void timer0_init(void){
	//cli();
	TCCR0A = 0x00; // reset A control register
	TCCR0A |= (1 << WGM01); // set CTC mode, OCR0A = top, set OCR0B to duty cycle.
	TCCR0B = 0x00; // reset B control register
	OCR0A = 100; // set top count to 100 counts as percentage duty cycle
	timer0_set_DAC_value1(voltageData.highDACValue);
	OCR0B = voltageData.dutyCycle;
	//TIMSK0 |= ((1 << OCIE0A) | (1 << OCIE0B)); // enable A and B compare interrupts
	//sei();
	
 }

 void timer0_start(void){
	TCCR0B |= (1 << CS01); // set clock divisor to 8, start timer.
 }

 void timer0_stop(void){
	TCCR0B &= ~((1 <<CS01) | (1 << CS02) | (1 << CS00)); // stop timer by clearing clock select bits
 }


 void timer0_set_DAC_value1(uint8_t inDACValue){
	//voltageData.currentDACValue = inDACValue; // save the current DAC value
	PORTB &= ~0x03; // reset bits 0 and 1 of port B
	PORTB |= (inDACValue >> 0x06); // set port B bits 1 and 0 to MSBs of DAC value
	PORTD &= ~0xFC; // clear pins 7 to 2 of port D
	PORTD |= (inDACValue << 0x02); // set port D bits 7 to 2
 }

 void timer0_set_DC_value1(void){
	OCR0B = voltageData.dutyCycle;
 }


 // interrupt for top reset
 ISR (TIMER0_COMPA_vect){
		timer0_set_DAC_value1(voltageData.highDACValue);
 }

 // interrupt for end of duty cycle period
 ISR (TIMER0_COMPB_vect){
	 timer0_set_DAC_value1(voltageData.lowDACValue);
 }
 */

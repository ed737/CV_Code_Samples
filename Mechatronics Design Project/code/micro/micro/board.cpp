// initialize all board
// and register variables
// before execution of 
// main loop

#include "board.h"


void board_init(){



	// clock setup
	CLKPR |= 0x80;  // set clock scaler to 1

	// turn off watchdog timer
	wdt_disable();
	
	// port initialization, note example arduino sketch reverses these bytes
	// also pin out does not match PCB schematic.
	// DAC port 1
	// arduino pins:    MSB{pin2,  pin3,  pin4,  pin5,  pin6,  pin7,   pin8,   pin9}LSB
	// atmel pins       MSB{PD2,  PD3,  PD4,  PD5,  PD6,  PD7,   PB0,   PB1}LSB
	// DAC port 2
	// arduino pins:    MSB{D10,  D11,  A0,  A1,  A5,  A4,  A3,  A2}LSB
	// atmel pins       MSB{PB2,  PB3,  PC0, PC1, PC5, PC4, PC3, PC2}LSB
		
	DDRB |= 0x0F; // port B output pins 0-1 output for DAC1 and output pins 2 and 3 DAC2 (pins 8 to 11 on Arduino)
	DDRD = 0xFC; // port D output pins 2-7 for DAC1 (pins 14 to 17 on Arduino)
	DDRC = 0x3F; // port C output pins 0-5 For DAC2


	// setup PORT C for input on analog channels ADC6 and ADC7 (arduino pins A6 and A7)
	DDRC &= ~(BIT6 | BIT7);


	//DDRD &= ~(BIT2 + BIT3); // setup portD pins 2 and 3 as input for interrupt pins for keys (Arduino pins 2 and 3)
		
	//PCMSK0 |= PCINT0 | PCINT1; // enable pin interrupts on INT0 and INT1
	//PCICR |= PCIE0; // enable pin change interrupt control register for INT0 and INT1
		
	// pin initialization

	// peripheral initialization

}
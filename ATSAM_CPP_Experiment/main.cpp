/*-------------------------------------------------------------------------------------------------------------------------
*	File:		main.cpp
*	Project:	HAL_Testing
*	Author:		Edward Munns
*	Created:	05 Aug 2023
*--------------------------------------------------------------------------------------------------------------------------
* 	Brief:
*	
--------------------------------------------------------------------------------------------------------------------------*/
/*
#include <cinttypes>


#include <sam.h>

// LED on B27 (Arduino Due)
#define LED_PIN 27

// PMC definitions
#define PMC_PCER0 *(volatile uint32_t *)0x400E0610
//#define ID_PIOB 12

void delay (volatile uint32_t time)
{
	while (time--)
	__asm ("nop");
}

int main(void) {
	
	SystemInit();
	
	// enable peripheral clock
	//  PMC_WPMR  = PMC_WPKEY << 8 | 0;
	PMC_PCER0 = 1 << ID_PIOB;
	
	PIOB->PIO_PER = 1 << LED_PIN;
	PIOB->PIO_OER = 1 << LED_PIN;
	PIOB->PIO_OWER = 1 << LED_PIN;
	
	while (1) {
		 PIOB->PIO_SODR = 1 << LED_PIN;
		 delay (100000);
		 PIOB->PIO_CODR = 1 << LED_PIN;
		 delay (100000);
	}
}
*/
/*
#include "SysReg.h"
#include "GPIO.h"
#include "Board.h"
#include "Utils.h"

int main(void) {
	
	using namespace GPIO;
	PortB.setupOutputs(PB27);
	
	Board::init();
	
	while(1) {
		PortB.setHigh(PB27);
		Utils::delayCycles(1000000);
		PortB.setLow(PB27);
		Utils::delayCycles(1000000);
	}
}
*/

#include "SysReg.h"
#include "GPIO.h"
#include "Board.h"
#include "Utils.h"

int main(void) {
	
	static volatile uint_fast8_t i = 0;

	using namespace GPIO;
	
	DataBus<Periph::ID::_PIOC>  dataBus(PC2 | PC3 | PC4 | PC5 | PC6 | PC7 | PC8 | PC9, Dir::_Output);
	
	Board::init();
	dataBus.write(0xFFu);
	Utils::delayCycles(1000000);
	dataBus.write(0x00u);
	Utils::delayCycles(1000000);
	
	while(1) {
		
		dataBus.write(i);
		i++;
		Utils::delayCycles(500000);
	}
}


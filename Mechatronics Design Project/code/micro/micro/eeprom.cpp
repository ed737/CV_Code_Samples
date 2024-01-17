/*
 * eeprom.cpp
 * EEPROM of the atmega328p contains
 * 1Kb of data memory
 * Created: 5/08/2022 2:20:07 PM
 *  Author: ed
 */ 

 #include "eeprom.h"

 void EEPROM_init(){
	// set the initial values of the EEPROM address register
	// this register is 9 bit so high and low bytes must be written
	// however this is done by the compiler in C
	EEARH = MEM_LOCATION_1;
 }

  /***************************************
 * read byte array from EEPROM
 * will start from startAddress
 * and read numBytes bytes to
 * dataBuf. Interrupts are disabled in 
 * these functions.
 ***************************************/
 void eeprom_read(uint16_t startAddress, uint8_t* dataBuf, uint8_t numBytes){
	cli();
	while(numBytes > 0){
		while(EECR & (1<<EEPE)); // wait while EEPROM busy
		EEAR = startAddress;     // set EEPROM memory address to be written to
		EECR |= (1<<EERE);       // set read enable bit to begin read
		*dataBuf = EEDR;         // write data to dataBuf
		dataBuf++;               // increment data buffer pointer
		startAddress++;          // increment memory address pointer
		numBytes--;              // decrement number bytes
	}
	sei();
 }

 /***************************************
 * write byte array to EEPROM
 * will start from startAddress
 * and write numBytes bytes to
 * EEPROM.
 ***************************************/
 void eeprom_write(uint16_t startAddress, uint8_t* dataBuf, uint8_t numBytes){
	cli();
	while(numBytes > 0){
		while(EECR & (1<<EEPE)); // wait while EEPROM busy
			EEAR = startAddress;     // set EEPROM memory address to be written to
			EEDR = *dataBuf;         // set EEPROM data register to incoming data byte
			EECR |= (1<<EEMPE);      // set master write enable bit
			EECR |= (1<<EEPE);       // set write enable bit to begin write
			dataBuf++;               // increment data buffer pointer
			startAddress++;          // increment memory address pointer
			numBytes--;              // decrement number bytes
	}
	sei();
 }
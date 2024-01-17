/*
 * eeprom.h
 *
 * Created: 5/08/2022 2:19:43 PM
 *  Author: ed
 */ 


#ifndef EEPROM_H_
#define EEPROM_H_

#include <avr/io.h>
#include <inttypes.h>
#include <avr/interrupt.h>

// move 20 bytes between memory address locations
#define MEM_LOCATION_0  0x0000
#define MEM_LOCATION_1  0x0014
#define MEM_LOCATION_2  0x0028
#define MEM_LOCATION_3  0x003C

// function definitions
void EEPROM_init(void);
void eeprom_read(uint16_t address, uint8_t* dataBuf, uint8_t numBytes);
void eeprom_write(uint16_t address, uint8_t* dataBuf, uint8_t numBytes);



#endif /* EEPROM_H_ */
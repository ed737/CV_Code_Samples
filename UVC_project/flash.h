/*
 * flash.h
 *
 *  Created on: Dec 2, 2021
 *      Author: edm73
 */

#ifndef FLASH_H_
#define FLASH_H_

#include <msp430.h>
#include <inttypes.h>
#include <intrinsics.h>
#include "clock.h"

#define INFO_SEG_D 0x1000
#define INFO_SEG_C 0x1040
#define INFO_SEG_B 0x1080
#define INFO_SEG_A 0x10C0

void Flash_Init(void);
void Flash_Erase(uint16_t* address);
void Flash_Write(uint8_t* dataBuf, uint16_t* address, uint8_t numBytes);
void Flash_Read(uint8_t* array, uint16_t* address, uint8_t readLength);

#endif /* FLASH_H_ */

/*
 * Flash.c
 *
 *  Created on: Dec 2, 2021
 *  Updated and tested 15/12/2021
 *      Author: edm73
 *
 *  Notes: to write must call Flash_Erase first EXTERNALLY from this library
 *  Data is read word at a time (2 bytes) and split into bytes for dataBuf
 *  info segment A cannot be used with this implementation as it is protected
 *  by a lock. This segment holds the calibration values for internal clock
 *  setup anyway so may be better to leave them there.
 */

#include "flash.h"

void Flash_Init(void){


// setup clock divider depending on SMCLK
// pre-scaler = divider + 1	
#ifdef MHZ_1
    FCTL2 = FWKEY + FSSEL_2 + 2u; // divide by 3 = 333khz
#endif

#ifdef MHZ_4
    FCTL2 = FWKEY + FSSEL_2 + 9u; // divide by 10 = 400khz
#endif

#ifdef MHZ_8
    FCTL2 = FWKEY + FSSEL_2 + 20u; // divide by 20 = 400khz
#endif

#ifdef MHZ_16
    FCTL2 = FWKEY + FSSEL_2 + 40u; // divide by 40 = 400khz
#endif

}

// must erase flash segment before write
void Flash_Erase(uint16_t* address){
    __bic_SR_register(GIE);       // disable interrupts
    while(FCTL3 & BUSY);          // wait while flash is busy
    FCTL1 = FWKEY + ERASE;        // set erase bit for segment erase
    FCTL3 =  FWKEY;               // clear lock bit in FCTL3
    *address = 0;                 // dummy write to erase flash
    while(FCTL3 & BUSY);          // wait while flash is busy
    FCTL1 = FWKEY;                // clear erase bit
    FCTL3 = FWKEY + LOCK;         // lock flash
    __bis_SR_register(GIE);       // enable interrupts
}

void Flash_Write(uint8_t* dataBuf, uint16_t* address, uint8_t numBytes){
    uint8_t ii = 0;
    uint16_t word = 0;
    __bic_SR_register(GIE);       // disable interrupts
    while(FCTL3 & BUSY);          // wait while flash is busy
    FCTL3 = FWKEY;                // clear lock bit
    FCTL1 = FWKEY + WRT;          // setup for write

    // copy bytes
    for(ii = 0; ii < numBytes; ii += 2){
        word = *dataBuf++;
        word <<= 0x08;
        word += *dataBuf++;
        *address++ = word;
        while(FCTL3 & BUSY);      // wait while flash is busy
    }

    FCTL1 = FWKEY;                // clear write bit
    FCTL3 = FWKEY + LOCK;         // lock flash
    __bis_SR_register(GIE);       // enable interrupts

}

void Flash_Read(uint8_t* array, uint16_t* address, uint8_t readLength){
    uint8_t ii = 0;
    uint16_t word;

    for(ii = 0; ii < readLength; ii += 2){
        word = *address++;
        *array++ = (uint8_t)(word >> 0x08);
        *array++ = (uint8_t)((word << 0x08) >> 0x08);
    }
}

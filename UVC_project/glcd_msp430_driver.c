
#include <msp430.h>
#include <stdint.h>
#include <pgmspace.h>
#include "ks0108.h"

// Data Port Setup
#define GLCD_DATA_IN    P2IN
#define GLCD_DATA_OUT   P2OUT
#define GLCD_DATA_DIR   P2DIR
#define GLCD_DATA_REN   P2REN
#define GLCD_CMD_REN    P3REN

// Command Port Setup
#define GLCD_CMD_PORT_2 P3OUT
#define GLCD_CMD_PORT_1 P1OUT
#define GLCD_CMD_1_DIR  P1DIR
#define GLCD_CMD_2_DIR  P3DIR
#define GLCD_CMD_PORT_3 P5OUT
#define GLCD_CMD_3_DIR  P5DIR

// Command Port Bits
#define RS               0x00
#define RW               0x04
#define EN               0x05

// Page Select Bits
#define CS1              0x07
#define CS2              0x00
#define CS3              0x03
#define RESET            0x02

#define DISPLAY_BUSY     0x80

extern uint8_t screen_x;
extern uint8_t screen_y;

// initial port setup
void GLCD_Init_Ports(void){

   GLCD_CMD_PORT_3 |= (0x01 << RESET);
   GLCD_DATA_REN    = 0xFF;   // set port 2 pull up down resistors
   GLCD_CMD_1_DIR  |= (BIT0 | BIT4 | BIT5 | BIT7);
   GLCD_CMD_2_DIR  |= (BIT0 | BIT3);
   GLCD_CMD_3_DIR  |=  BIT2;
   GLCD_CMD_PORT_1 |= (CS1); // set CS1- CS3 bits high (disabled)
   GLCD_CMD_PORT_2 |= (CS2 | CS3);
}

/********************************************************
 * From ks0108 data sheet,
 * enable high level width > 450 ns
 * enable low level width > 450 ns
 * Address setup time >  140 ns
 * Address hold time > 10 ns
 * Data setup time > 200 ns
 * enable cycle total > 1000us
 * MSP430 @ 16MHz:
 * 1/16,000,000 = 62.5ns per cycle
 * ceiling(450/62.5) = 8 cycles for enable/disable.
 * ceiling(140/62.5) = 3 cycles for address setup
 * ceiling(200/62.5) = 4 cycles for data setup
 ********************************************************/

// Delay cycle constants, these must be 
// compile time constants for the 
// __delay_cycles() intrinsic
// TODO: these need to be calculated
// off M_CLK frequency. 
#define ENABLE_DELAY_CYCLES 8
#define ENABLE_DELAY			__delay_cycles(ENABLE_DELAY_CYCLES)
#define WRITE_DELAY_CYCLES  40
#define WRITE_DELAY				__delay_cycles(WRITE_DELAY_CYCLES)

// enable GLCD controller (0-2)
void GLCD_Enable_Page(uint8_t page){
    switch(page){
        case 0: GLCD_CMD_PORT_1 &= ~(0x01 << CS1); break;
        case 1: GLCD_CMD_PORT_2 &= ~(0x01 << CS2); break;
        case 2: GLCD_CMD_PORT_2 &= ~(0x01 << CS3); break;
    }
}

// disable controller (0-2)
void GLCD_Disable_Page(uint8_t page){
    switch(page){
        case 0: GLCD_CMD_PORT_1 |= 0x01 << CS1; break;
        case 1: GLCD_CMD_PORT_2 |= 0x01 << CS2; break;
        case 2: GLCD_CMD_PORT_2 |= 0x01 << CS3; break;
    }
}

// read status from page (0-2)
uint8_t GLCD_Read_Status(uint8_t page){
   uint8_t status = 0x00;
   GLCD_DATA_DIR = 0x00;
   GLCD_CMD_PORT_1 |= 0x01 << RW;
   GLCD_CMD_PORT_1 &= ~(0x01 << RS);
   GLCD_Enable_Page(page);
   GLCD_CMD_PORT_1 |= 0x01 << EN;
   ENABLE_DELAY;
   status = GLCD_DATA_IN;
   GLCD_CMD_PORT_1 &= ~(0x01 << EN);
   WRITE_DELAY;
   GLCD_Disable_Page(page);
   WRITE_DELAY;
   return status;
}

// write command to page (0-2)
void GLCD_Write_Command(uint8_t command, uint8_t page){
    while((GLCD_Read_Status(page) & DISPLAY_BUSY)); // wait if controller is busy
    GLCD_DATA_OUT = 0x00;
    GLCD_DATA_DIR = 0xFF;
    GLCD_Enable_Page(page);
    GLCD_DATA_OUT = command;
    GLCD_CMD_PORT_1 &= ~(0x01 << RW);
    GLCD_CMD_PORT_1 &= ~(0x01 << RS);
    GLCD_CMD_PORT_1 |= 0x01 << EN;
    ENABLE_DELAY;
    GLCD_CMD_PORT_1 &= ~(0x01 << EN);
    WRITE_DELAY;
    GLCD_Disable_Page(page);
    WRITE_DELAY;
    GLCD_DATA_OUT = 0x00;
}

// read data from current position
uint8_t GLCD_Read_Data(void){
    uint8_t data = 0x00;
    while(GLCD_Read_Status((screen_x >> 0x06)) & DISPLAY_BUSY); // wait if controller is busy
    GLCD_DATA_OUT = 0x00;
    GLCD_DATA_DIR = 0x00;
    GLCD_Enable_Page((screen_x >> 6));
    GLCD_CMD_PORT_1 |= 0x01 << RW;
    GLCD_CMD_PORT_1 |= 0x01 << RS;
    GLCD_CMD_PORT_1 |= 0x01 << EN;
    ENABLE_DELAY;
    data = GLCD_DATA_IN;
    GLCD_CMD_PORT_1 &= ~(0x01 << EN);
    WRITE_DELAY;
    GLCD_Disable_Page((screen_x >> 6));
    WRITE_DELAY;
    screen_x++;
    return data;
}

// read data from current position
uint8_t GLCD_Read_Data_No_Increment(void){
    uint8_t data = 0x00;
    while(GLCD_Read_Status((screen_x >> 0x06)) & DISPLAY_BUSY); // wait if controller is busy
    GLCD_DATA_OUT = 0x00;
    GLCD_DATA_DIR = 0x00;
    GLCD_Enable_Page((screen_x >> 6));
    GLCD_CMD_PORT_1 |= 0x01 << RW;
    GLCD_CMD_PORT_1 |= 0x01 << RS;
    GLCD_CMD_PORT_1 |= 0x01 << EN;
    ENABLE_DELAY;
    GLCD_CMD_PORT_1 &= ~(0x01 << EN);
    data = GLCD_DATA_IN;
    WRITE_DELAY;
    GLCD_Disable_Page((screen_x >> 6));
    WRITE_DELAY;
    return data;
}

void GLCD_Write_Data(uint8_t data){
    while(GLCD_Read_Status((screen_x >> 0x06)) & DISPLAY_BUSY);
    GLCD_DATA_DIR = 0xFF;
    GLCD_DATA_OUT = 0x00;
    GLCD_DATA_OUT = data;
    GLCD_Enable_Page((screen_x >> 6));
    GLCD_CMD_PORT_1 &= ~(0x01 << RW);
    GLCD_CMD_PORT_1 |= 0x01 << RS;
    GLCD_CMD_PORT_1 |= 0x01 << EN;
    ENABLE_DELAY;
    GLCD_CMD_PORT_1 &= ~(0x01 << EN);
    WRITE_DELAY;
    GLCD_Disable_Page((screen_x >> 6));
    WRITE_DELAY;
    screen_x++;
    GLCD_DATA_OUT = 0x00;
}

void GLCD_Write_Data_No_Increment(uint8_t data){
    while(GLCD_Read_Status((screen_x >> 0x06)) & DISPLAY_BUSY);
    GLCD_DATA_DIR = 0xFF;
    GLCD_DATA_OUT = 0x00;
    GLCD_DATA_OUT = data;
    GLCD_Enable_Page((screen_x >> 6));
    GLCD_CMD_PORT_1 &= ~(0x01 << RW);
    GLCD_CMD_PORT_1 |= 0x01 << RS;
    GLCD_CMD_PORT_1 |= 0x01 << EN;
    ENABLE_DELAY;
    GLCD_CMD_PORT_1 &= ~(0x01 << EN);
    WRITE_DELAY;
    GLCD_Disable_Page((screen_x >> 6));
    WRITE_DELAY;
    GLCD_DATA_OUT = 0x00;
}

uint8_t GLCD_Read_Byte_From_Mem(uint8_t* memPtr){
    uint8_t data;
    data = pgm_read_byte(memPtr);
    return data;
}



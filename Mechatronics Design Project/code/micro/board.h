/***************************
* board.h, setup of hardware
* registers for easier use.
***************************/ 

#ifndef _BOARD_H
#define _BOARD_H


// define single bit binary 
// masks for setting individual
// register bits 
#define BIT0 0x01
#define BIT1 0x02
#define BIT2 0x04
#define BIT3 0x08
#define BIT4 0x10
#define BIT5 0x20
#define BIT6 0x40
#define BIT7 0x80




// function definitions
void board_init();

#endif
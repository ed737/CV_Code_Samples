/*
 * data.h
 *
 * Created: 5/08/2022 3:11:56 PM
 *  Author: ed
 */ 

#ifndef DATA_H_
#define DATA_H_

#include <stdbool.h>
#include <inttypes.h>
#include "eeprom.h"
#include "usart.h"
#include "PID.h"
#include "timer1.h"
#include "board.h"
#include "adc.h"

// define the 2 sensor sample arrays for averaging
// average will be performed every 256 ADC reads 

// this enum for cart control mode
typedef enum CartMode{
	unavailable = 0x00,	
	standby = 0x01,		
	manual = 0x02,			
	autoLineFollow = 0x03, 
	tuning = 0x04,		     
	saveData = 0x05,
	loadData = 0x06
}CartMode;

// State flags and enum
typedef struct State{
	CartMode cartMode;
	CartMode previousCartMode;
	PID leftPID;
	PID rightPID;
	uint16_t currentMemAddress;

	bool calcPID;
	bool messageRecieved;
	bool messageSend;
	bool cartModeChanged;
	bool saveDataToEEPROM;
	bool loadDataFromEEPROM;
}State;

// function definitions
void data_init(void);
void loadDataFromMem(void);
void saveDataToMem(void);

#endif /* DATA_H_ */
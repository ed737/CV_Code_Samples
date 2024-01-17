/*
 * data.cpp
 *
 * Created: 5/08/2022 3:12:11 PM
 *  Author: ed
 */ 

 #include "data.h"
 #include "PID.h"
 #include "eeprom.h"
 #include "usart.h"

 #define MEMORY_ARRAY_LENGTH 6

 uint8_t dataArray[MEMORY_ARRAY_LENGTH];
 extern State state;

// setup data struct
void data_init(){
	state.cartMode = standby;
	state.previousCartMode = standby;
	init_PID();
	state.calcPID = false;
	state.currentMemAddress = 0x0000;
	state.messageRecieved = false;
	state.messageSend = false;
	state.cartModeChanged = false;
}

// load in PID data from EEPROM
void loadDataFromMem(void){
	
	// load in data
	eeprom_read(state.currentMemAddress, dataArray, MEMORY_ARRAY_LENGTH);

	// update variables
	state.leftPID.kP = dataArray[0];
	state.rightPID.kP = dataArray[1];
	state.leftPID.kI = dataArray[2];
	state.rightPID.kI = dataArray[3];
	state.leftPID.kD = dataArray[4];
	state.rightPID.kD = dataArray[5];

	// send results back to PC


	state.loadDataFromEEPROM = false;
	state.previousCartMode = loadData;
	state.cartMode = standby;
	state.cartModeChanged = true;
	usart_send_bytes(dataArray, MEMORY_ARRAY_LENGTH, PID_DATA_LOADED);

}


// save PID data to EEPROM
void saveDataToMem(void){
		
		dataArray[0] = state.leftPID.kP;
		dataArray[1] = state.rightPID.kP;
		dataArray[2] = state.leftPID.kI;
		dataArray[3] = state.rightPID.kI;
		dataArray[4] = state.leftPID.kD;
		dataArray[5] = state.rightPID.kD;

		//eeprom_write(state.currentMemAddress, dataArray, MEMORY_ARRAY_LENGTH);

		usart_send_bytes(dataArray,  MEMORY_ARRAY_LENGTH, PID_DATA_SAVED);

		state.saveDataToEEPROM = false;
		state.previousCartMode = saveData;
		state.cartMode = standby;
		state.cartModeChanged = true;
}

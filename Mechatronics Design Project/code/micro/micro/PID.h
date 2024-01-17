/*
 * PID.h
 *
 * Created: 5/08/2022 3:50:31 PM
 *  Author: ed
 */ 


#ifndef PID_H_
#define PID_H_

#include <inttypes.h>
	
#define LEFT_SIDE 0
#define RIGHT_SIDE 1
#define PID_UPDATE_DT 64   // PID update in ms, 64 chosen as power of 2
#define ERROR_SAMPLES_PER_PID_UPDATE 16 // read the sensors 16 times per PID update
#define STOP 127
#define FULL_FORWARD 255
#define SLOWEST_FORWARD 155
#define FULL_BACKWARD 0
#define SLOWEST_BACKWARD 90	// these values may change


// we have 91 different backwards values (0 to 90) , stop at 127 and 100 forward values (155 to 255)
// these will be used as limits in the PID calculation

// create 2 of these structs called leftPID and rightPID
typedef struct PID{
	uint8_t newDACValue; // current control signal (uk+1)
	uint8_t previousDACValue; // uk
	int16_t newError; // ek+1
	int16_t previousError; //ek
	uint8_t kP; // proportional constant
	uint8_t kI; // integral constant
	uint8_t kD; // derivative constant
	uint8_t Dt; // sample time
	uint16_t kIDt; // this is only calculated when Dt or Ki updated to save processing
	uint8_t sensorReadingArray[ERROR_SAMPLES_PER_PID_UPDATE]; 
	uint8_t sensorReadingArrayIndex;
	uint16_t summedErrorSamples;
}PID;

// function definitions
void init_PID();
void calc_PID();
void send_PID_data_report();
void getError();
void setOutput();
void setDACLeft_Value(uint8_t inDACDValue);
void setDACRight_Value(uint8_t inDACValue);
void defaultPID(bool side);

#endif /* PID_H_ */
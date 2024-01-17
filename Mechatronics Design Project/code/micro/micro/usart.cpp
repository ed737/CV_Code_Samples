/*
 * usart.cpp
 *
 * Created: 13/08/2022 1:54:45 PM
 *  Author: ed
 */ 


#include "usart.h"
#include "data.h"
#include <string.h>

uint8_t recieveBuffer[BUFFER_LENGTH];
uint8_t transmitBuffer[BUFFER_LENGTH];
uint8_t rxLength;
uint8_t txLength;
uint8_t rxPosition;
uint8_t txPosition;

extern State state;

 void usart_init(uint32_t baud){
	uint16_t temp = (uint8_t)((F_CPU/(16*baud))-1);

	cli(); // turn off global interrupts
	UBRR0H = (uint8_t)temp >> 8; // set baud rate register
	UBRR0L = (uint8_t)temp;
	UCSR0B |= (1<< TXEN0) | (1<< RXEN0) | (1<< RXCIE0); // enable transmit and receive and set RX interrupt
	UCSR0C |= (1 << UCSZ01) | (1 << UCSZ00); // setup for 8 bit transmission length
	sei(); // turn on global interrupts

	// set variables
	rxLength = 0;
	txLength = 0;
	rxPosition = 0;
	txPosition = 0;
 }

 void usart_send_string(char* inString){
	state.messageSend = true;
	txLength = strlen(inString) + 2;
	txPosition = 0;
	transmitBuffer[0] = STRING_START; // send string start byte
	transmitBuffer[1] =  txLength; // send length of string
	 
	// load transmit buffer
	for(size_t ii = 2; ii < txLength; ii++){
		transmitBuffer[ii] = (uint8_t)*inString++;
	}
	UCSR0B |= (1 << TXEN0);
	UCSR0B |= (1 << TXCIE0); // setup transmit interrupt
	UDR0 = transmitBuffer[0]; // send string start byte
	txPosition++;
}

 void usart_send_bytes(uint8_t* inDataArray, uint8_t inLength, uint8_t controlByte){
	state.messageSend = true;
	txLength = inLength + 3;
	txPosition = 0;
	transmitBuffer[0] = BYTE_START;
	transmitBuffer[1] = txLength;
	transmitBuffer[2] = controlByte;

	for(int ii = 0; ii < inLength; ii++){
		transmitBuffer[ii + 3] = inDataArray[ii];		
	}
		
	UCSR0B |= (1 << TXEN0);
	UCSR0B |= (1 << TXCIE0); // setup transmit interrupt
	UDR0 = transmitBuffer[0]; // send first byte
	txPosition++;
 }

 void usart_message_recieved(void){
	
		if(usart_calc_checksum()){ // calculate checksum and compare
			
		
			if(state.cartMode != (CartMode)recieveBuffer[2]){
				state.previousCartMode = state.cartMode;
				state.cartMode = (CartMode)recieveBuffer[2];
				state.cartModeChanged = true;
			}

			// switch to handle relevant cart mode message format
			switch(state.cartMode){
				
				// this case not used on controller side, added just for PC code clarity
				case unavailable:
					usart_send_string("UNAVAILABLE\r\n"); // this here just in case of errors, micro should never be in this mode.
				break;
				
				case standby:
					// message format = startByte, msgLength, controlMode, checksum
					usart_send_string("STANDBY MODE\r\n"); // send OK signal
				break;
				
				case manual:
					// message format = startByte, msgLength, controlMode, DACLeftValue, DACRightValue, checksum
					usart_send_string("MANUAL MODE\r\n"); // send OK signal
				break;
				
				case autoLineFollow:
					// incoming message format = startByte, msgLength, controlMode, checksum

					// outgoing message format = byte message start, message length, control byte, left sensor high byte, left sensor low byte,
					// right sensor high byte, right sensor low byte, left DAC value, right DAC value 
					usart_send_string("LINE FOLLOW MODE\r\n"); // send OK signal
				break;
				
				case tuning:
					// message format = startByte, msgLength, controlMode, DACLeftValue, DACRightValue, kP, kI, kD, checksum
					setDACLeft_Value(recieveBuffer[3]);
					setDACRight_Value(recieveBuffer[4]);
					state.leftPID.kP = recieveBuffer[5];
					state.rightPID.kP = recieveBuffer[5];
					state.leftPID.kI = recieveBuffer[6];
					state.rightPID.kI = recieveBuffer[6];
					state.leftPID.kD = recieveBuffer[7];
					state.rightPID.kD = recieveBuffer[7];
					// also need to update kidt
					state.leftPID.kIDt = state.leftPID.kI * state.leftPID.Dt;
					state.rightPID.kIDt = state.rightPID.kI * state.rightPID.Dt;  
					usart_send_string("TUNING MODE\r\n"); // send OK signal
				break;

				case saveData:
					// message format = startByte, msgLength, controlMode, memory location, kP, kI, kD, checksum

					// message format = startByte, msgLength, controlMode, memory location, checksum
					state.currentMemAddress = recieveBuffer[3];
					
					// this switch sets the memory location in EEPROM
					switch(recieveBuffer[3]){

						case 0:
							state.currentMemAddress = MEM_LOCATION_0;
							state.saveDataToEEPROM = true;
						break;

						case 1:
							state.currentMemAddress = MEM_LOCATION_1;
							state.saveDataToEEPROM = true;
						break;

						case 2:
							state.currentMemAddress = MEM_LOCATION_2;
							state.saveDataToEEPROM = true;
						break;

						case 3:
							state.currentMemAddress = MEM_LOCATION_3;
							state.saveDataToEEPROM = true;
						break;

						default:
							state.previousCartMode = state.cartMode;
							state.cartMode = standby;
							state.cartModeChanged = true;
							usart_send_string("INVALID MEMORY ADDRESS\r\n");
						break;
					}

					if(state.cartMode == saveData){
						// save the new data values to the state struct
						state.leftPID.kP = recieveBuffer[4];
						state.rightPID.kP = recieveBuffer[4];
						state.leftPID.kI = recieveBuffer[5];
						state.rightPID.kI = recieveBuffer[5];
						state.leftPID.kD = recieveBuffer[6];
						state.rightPID.kD = recieveBuffer[6];

						//usart_send_string("SAVING DATA\r\n"); // send OK signal
					}
				break;

				case loadData:
					// message format = startByte, msgLength, controlMode, memory location, checksum
					state.currentMemAddress = recieveBuffer[3];
					
					// this switch sets the memory location in EEPROM
					switch(recieveBuffer[3]){

						case 0:
							state.currentMemAddress = MEM_LOCATION_0;
							state.loadDataFromEEPROM = true;
						break;

						case 1:
							state.currentMemAddress = MEM_LOCATION_1;
							state.loadDataFromEEPROM = true;
						break;

						case 2:
							state.currentMemAddress = MEM_LOCATION_2;
							state.loadDataFromEEPROM = true;
						break;

						case 3:
							state.currentMemAddress = MEM_LOCATION_3;
							state.loadDataFromEEPROM = true;
						break;

						default:
							state.previousCartMode = state.cartMode;
							state.cartMode = standby;
							state.cartModeChanged = true;
							usart_send_string("INVALID MEMORY ADDRESS\r\n");
						break;

					}
					if(state.cartMode == loadData){
						usart_send_string("LOADING DATA\r\n"); // send OK signal
					}
				break;

				default:
					state.previousCartMode = standby;
					state.cartMode = standby;
					state.cartModeChanged = true;
					usart_send_string("CART MODE ERROR\r\n"); // send OK signal
				break;
				
			}
		}

		// case for serial error, send the message back. 
		else{
			usart_send_string("CHECK SUM ERROR\r\n"); // send checksum error signal
			rxLength = 0;
		}

		state.messageRecieved = false; // reset the message flag
		rxPosition = 0;
		rxLength = 0;
		
 }

 bool usart_calc_checksum(void){
		bool checkSumOK = false; 
		uint8_t checkSum = 0;
		for(int ii = 0; ii < rxLength - 1; ii++){
			checkSum += recieveBuffer[ii]; // calculate checksum
		}

		if(checkSum == recieveBuffer[rxLength - 1]){
			checkSumOK = true; // return true if checksum is correct
		}
		
		rxPosition = 0; // reset length
		return checkSumOK;
 }


 ISR(USART_RX_vect){
	
	// 0 position used for message error checking
	if(rxPosition == 0){
		recieveBuffer[rxPosition] = (uint8_t)UDR0; // save first byte
		
		switch(recieveBuffer[rxPosition]){
			case BYTE_START:
				//usart_transmit(0xFF);
				rxPosition++;
			break;
			
			// emergency stop button hit
			// set DAC values to stopped
			case EMERGENCY_STOP:
				setDACLeft_Value(STOP);
				setDACRight_Value(STOP);
				rxLength = 0; // reset counters
				rxPosition = 0;
				usart_send_string("EMEGENCY STOP\r\n");
				state.previousCartMode = state.cartMode;
				state.cartMode = standby;
				state.cartModeChanged = true;
			break;

			case CHECKSUM_OK: // received conformation of last message OK, clear receive buffer 
				rxLength = 0; // reset counters
				rxPosition = 0;
			break;

			case CHECKSUM_ERROR: // last message was error, resend
				rxLength = 0; // reset counters
				rxPosition = 0;
				//usart_transmit(0x1F);
				//state.messageSend = true; // resend message from main
			break;
		}
		
	}
	
	// case for second byte to get message length
	else if(rxPosition == 1){
		recieveBuffer[rxPosition] = (uint8_t)UDR0; // save length byte
		rxLength = recieveBuffer[rxPosition]; // update message length
		//usart_transmit(recieveBuffer[rxPosition]);
		rxPosition++;
		
	}

	// case for bytes higher than 1
	else{
		if(rxPosition < rxLength - 1){
			recieveBuffer[rxPosition] = (uint8_t)UDR0; // save next byte
			//usart_transmit(recieveBuffer[rxPosition]);
			rxPosition++;
			
		}
		// case for last byte of message
		else{
			recieveBuffer[rxPosition] = (uint8_t)UDR0; // save checksum
			//usart_transmit(recieveBuffer[rxPosition]);
			rxPosition = 0;			
			state.messageRecieved = true; // update state for full message done
		}
	}	
 }

 ISR(USART_TX_vect){
	
	// send next byte
	if(txPosition < txLength){
		UDR0 = transmitBuffer[txPosition];
		txPosition++;
	}

	// all bytes sent, turn off TX interrupt and reset position
	else{
		txPosition = 0;
		txLength = 0;
		UCSR0B &= ~(1 << TXCIE0);
		state.messageSend = false;
	}
 }

 // NOTE: the functions in the if statement are for use in usart debugging through PC serial terminal software
 // they are not interrupt based, they hold waiting in the while loops until the serial port is free
 // THEY SHOULD NOT BE USED IN FINAL RELEASE PROJECT CODE!
 // uncomment #define USART_DEBUG  in usart.h to use them in early project development for serial debugging
 #ifdef USART_DEBUG

	 uint8_t usart_debug_receive(){
		 UCSR0B |= (1 << RXEN0);
		 while(!(UCSR0A & (1 << RXC0))); // wait for data received
		 return (uint8_t)UDR0; // return data
	 }
 
	 void usart_debug_transmit(uint8_t data){
		 UCSR0B |= (1 << TXEN0);
		 while(!(UCSR0A & (1 << UDRE0))); // wait while transmit buffer not empty
		 UDR0 = data; // transmit data
	 }

	 void usart_debug_send_string(char* inString){
		 state.messageSend = true;
		 uint8_t ii = 0;

		 while(inString[ii] != '\0'){
			 usart_debug_transmit(inString[ii]);
			 ii++;
		 }
		 usart_debug_transmit('\0'); // transmit null terminator
	 }
 
 #endif /* USART_DEBUG */

  
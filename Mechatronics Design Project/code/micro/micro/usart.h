/*
 * usart.h
 *
 * Created: 13/08/2022 1:54:30 PM
 *  Author: ed
 */ 


#ifndef USART_H_
#define USART_H_

 #include <avr/io.h>
 #include <avr/interrupt.h>
 #include <inttypes.h>
 #include <stdbool.h>
 #include "data.h"

 
 #ifndef F_CPU
 #define F_CPU 16000000UL
 #endif
 
 // serial communications definitions must match C# code
 // these are instruction sent in the first byte of the serial
 // message
 #define BYTE_START 0xFF
 #define STRING_START 0xFE
 #define EMERGENCY_STOP 0xFD
 #define CHECKSUM_OK 0xF1
 #define CHECKSUM_ERROR 0x1F
 #define PID_DATA_SAVED 0xFC
 #define PID_DATA_LOADED 0xFB
 #define AUTO_MODE_DATA_REPORT 0xFA

 // message index defines
 #define BUFFER_LENGTH 255

 // uncomment the define to use debug functions
 // #define USART_DEBUG

// function definitions
void usart_init(uint32_t baud);
void usart_send_string(char* inString);
void usart_send_bytes(uint8_t* buffer, uint8_t length, uint8_t controlByte);
void usart_message_recieved(void);
bool usart_calc_checksum(void);

#ifdef USART_DEBUG
	uint8_t usart_debug_receive();
	void usart_debug_transmit(uint8_t data);
	void usart_debug_send_string(char* inString);
#endif /* debug mode functions */

#endif /* USART_H_ */
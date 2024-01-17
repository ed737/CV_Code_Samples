/*
 * adc.h
 *
 * Created: 10/08/2022 6:27:05 PM
 *  Author: ed
 */ 


#ifndef ADC_H_
#define ADC_H_

#include <inttypes.h>
#include <avr/io.h>
#include <avr/interrupt.h>

#define LEFT_ADC_PORT 0x07
#define RIGHT_ADC_PORT 0x06


void adc_init(void);
void adc_off(void);
void adc_request_read();

#endif /* ADC_H_ */
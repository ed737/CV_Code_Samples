/*
 * adc.cpp
 *
 * Created: 10/08/2022 6:27:25 PM
 *  Author: ed
 */ 


 // Notes: ADC sample frequency at 10 bit is 50 to 200kHz
 // ADC6 and 7 pins have absolute max current input of 40mA, recommended 20 mA  

 //ADC = (VIN * 1024)/Vref

 
 #include "adc.h"
 #include "board.h"
 #include "data.h"

 // these defines control the bool
 // flag to allow for correct ADC channel
 // interrupt ordering
 #define ADC_LEFT_CONVERSION_NEXT 0
 #define ADC_RIGHT_CONVERSION_NEXT 1

 extern State state;
 volatile bool nextADCChannel;

 //uint16_t adcResult10Bit;
 uint8_t adcResult;
 	
 void adc_init(){
	
	// set ADC power reduction bit PRADC to 0 to turn on the ADC
	PRR0 &= ~(1 << PRADC); 

	// setup the ADC for left adjusted result presentation,
	// this means for 8 bit reading keeping MSBs we just read the high byte.
	// use VRef as comparison voltage
	ADMUX |= (1<<ADLAR) | (1<<REFS0);


	// enable ADC with 128 clock divisor for 125kHz at 16MHz Mclk
	ADCSRA |= (1<<ADEN) | (1<<ADPS2) | (1<<ADPS1) | (1<<ADPS0); 

	
	adcResult = 0;
}


void adc_off(void){
	// turn off the ADC and ADC interrupt
	ADCSRA &= ~(1<<ADEN);
	ADCSRA &= ~(1<<ADIE);
}


// this function sets up a read request from the ADC
// the conversion ready interrupt will fire when the
// conversion data is complete 
// first the left channel will be read and added to the error
// array, then the right channel will be read and the interrupt
// turned off.
void adc_request_read(){
	
	// reset the array indexes if the last error value has been read
	
	if(state.leftPID.sensorReadingArrayIndex == 16){
		state.leftPID.sensorReadingArrayIndex = 0;
		state.rightPID.sensorReadingArrayIndex = 0;
	}
	
	// set the ADC control flag to Left side
	nextADCChannel = ADC_LEFT_CONVERSION_NEXT;

	ADMUX = ((ADMUX & 0xF0) | (LEFT_ADC_PORT & 0x0F)); // setup ADC MUX for input on Left ADC channel

	// enable the ADC interrupt and start conversion
	ADCSRA |= (1 << ADIE) | (1 << ADSC);
	
}

  // This interrupt working as a binary First In First Out queue
  // The ADC sensor values are read with an 8 bit resolution
  // so the 2 least significant bits are discarded
  ISR(ADC_vect){
	/*
	adcResult10Bit = 0x0000; // clear the last ADC result
	adcResult10Bit += ADCH; // read high byte (2 bits)
	adcResult10Bit <<= 0x08; // right shift 8
	adcResult10Bit += ADCL; // merge the high and low bytes
	*/


	// read the high ADC byte
	adcResult = ADCH;

    // if the left channel is next conversion in line 
	if(nextADCChannel == ADC_LEFT_CONVERSION_NEXT){
		
		// set the read result to the current index
		state.leftPID.sensorReadingArray[state.leftPID.sensorReadingArrayIndex] = adcResult;
		state.leftPID.summedErrorSamples += adcResult;
		// increment the error array index
		state.leftPID.sensorReadingArrayIndex++;

		ADMUX &= ~((1<<MUX3)|(1<<MUX2)|(1<<MUX1)|(1<<MUX0)); // clear the left multiplexer bits
		ADMUX = ((ADMUX & 0xF0) | (RIGHT_ADC_PORT & 0x0F)); // setup ADC MUX for read on right channel
		ADCSRA |= (1 << ADSC); // ADC start conversion on right side sensor
		
		// flip the ADC control flag
		nextADCChannel = ADC_RIGHT_CONVERSION_NEXT;
	}
	else{
		
		// save the error value to the array
		state.rightPID.sensorReadingArray[state.rightPID.sensorReadingArrayIndex] = adcResult;
		state.rightPID.summedErrorSamples += adcResult;
		// increment the error array index
		state.rightPID.sensorReadingArrayIndex++;

		ADMUX &= ~((1<<MUX3)|(1<<MUX2)|(1<<MUX1)|(1<<MUX0)); // clear the multiplexer bits

		// flip the ADC control flag
		nextADCChannel = ADC_LEFT_CONVERSION_NEXT;

		// disable the ADC interrupt
		// the 2 error values for this error sample period have been read. 
		ADCSRA &= ~(1 << ADIE);
	}

  }
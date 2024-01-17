/*
 * timer0.h
 *
 * Created: 5/09/2022 9:31:58 AM
 *  Author: ed
 */ 


#ifndef TIMER0_H_
#define TIMER0_H_

#include <avr/io.h>
#include <avr/interrupt.h>
#include <inttypes.h>

void timer0_init(void);
void timer0_start(void);
void timer0_stop(void);

#endif /* TIMER0_H_ */
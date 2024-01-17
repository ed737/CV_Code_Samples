/*
 * timer.h
 *
 * Created: 10/08/2022 8:48:44 PM
 *  Author: ed
 */ 


#ifndef TIMER_H_
#define TIMER_H_

#include <avr/io.h>
#include <avr/interrupt.h>
#include <inttypes.h>
#include "data.h"
#include "adc.h"
#include "PID.h"

void timer1_PID_init();
void start_PID_timer();
void stop_PID_timer();

#endif /* TIMER_H_ */
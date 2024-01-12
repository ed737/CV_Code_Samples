/*
 * event_handler.h
 *
 *  Created on: 24 Feb 2022
 *      Author: ed
 */

#ifndef EVENT_HANDLER_H_
#define EVENT_HANDLER_H_

#include "data.h"
#include "cursors.h"
#include "user_interface.h"

void Event_Handler(ProgFlow* pf);
void Handle_Menu_Keypress(ProgFlow* pf);
void Handle_Menu_Enter_Keypress(ProgFlow* pf);
void Handle_Number_Keypress(ProgFlow* pf);

#endif /* EVENT_HANDLER_H_ */

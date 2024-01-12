/*
 *  user_interface.h
 *
 *  Created on: Dec 29, 2021
 *      Author: edm73
 *      Updated 6/1, 7/1
 */

#ifndef USER_INTERFACE_H_
#define USER_INTERFACE_H_

#include <inttypes.h>
#include <stdbool.h>
#include "graphics.h"
#include "ks0108.h"
#include "cursors.h"

#define MAX_STRING_LENGTH 13

// main control function
void Master_Screen_Control(ProgFlow* pf);

// screen functions
void Main_Menu_Create(ProgFlow* pf);
void Main_Menu_Update(ProgFlow* pf);

// inputs and children
void Inputs_Menu_Create(ProgFlow* pf);
void Inputs_Menu_Update(ProgFlow* pf);
void GPS_Menu_Create(ProgFlow* pf);
void GPS_Menu_Update(ProgFlow* pf);
void Run_Hold_Menu_Create(ProgFlow* pf);
void Run_Hold_Menu_Update(ProgFlow* pf);

// outputs and children
void Outputs_Menu_Create(ProgFlow* pf);
void Outputs_Menu_Update(ProgFlow* pf);
void Pump1_Setup_Create(ProgFlow* pf);
void Pump1_Setup_Update(ProgFlow* pf);
void Pump2_Setup_Create(ProgFlow* pf);
void Pump2_Setup_Update(ProgFlow* pf);

// settings and children
void Settings_Menu_Create(ProgFlow* pf);
void Settings_Menu_Update(ProgFlow* pf);
void Tank_Menu_Create(ProgFlow* pf);
void Tank_Menu_Update(ProgFlow* pf);
void Prime_Slow_Menu_Create(ProgFlow* pf);
void Prime_Slow_Menu_Update(ProgFlow* pf);
void Prime_Menu_Create(ProgFlow* pf);
void Prime_Menu_Update(ProgFlow* pf);
void Slow_Hold_Create(ProgFlow* pf);
void Slow_Hold_Update(ProgFlow* pf);

// diagnostics
void Diagnostics_Menu_Create(ProgFlow* pf);
void Diagnostics_Menu_Update(ProgFlow* pf);

// trips and children
void Trips_Menu_Create(ProgFlow* pf);
void Trips_Menu_Update(ProgFlow* pf);
void Trip_Select_Create(ProgFlow* pf);
void Trip_Select_Update(ProgFlow* pf);

// about and children
void About_Menu_Create(ProgFlow* pf);
void About_Menu_Update(ProgFlow* pf);
void Save_Config_Create(ProgFlow* pf);
void Save_Config_Update(ProgFlow* pf);
void Recall_Config_Create(ProgFlow* pf);
void Recall_Config_Update(ProgFlow* pf);
void Language_Menu_Create(ProgFlow* pf);
void Language_Menu_Update(ProgFlow* pf);

// helper functions for main menus
void Draw_Menu_Background(void);
void Clear_Menu_Text(void);
void Clear_Cell_Text(uint8_t cellNumber, uint8_t color);
void Update_Menu_Text(void);
void Update_Cell_Text(uint8_t cellNumber, const char* text, uint8_t color);
void Invert_Menu_Cursor(uint8_t color);
void Activate_Menu_Cursor();
void Deactivate_Menu_Cursor();
void Handle_Menu_Keypress(ProgFlow* pf);
void Handle_Enter_Keypress(ProgFlow* pf);
void Update_Menu_Title(char* newMenuTitle);

#endif /* USER_INTERFACE_H_ */

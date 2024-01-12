/*
 * graphics.h
 *
 *  Created on: Oct 15, 2021
 *      Author: edm73
 *
*      Updated 17/12 for merging into project
 */

#include <stdbool.h>
#include <inttypes.h>
#include "strings.h"
#include "ks0108.h"
#include "data.h"

#ifndef GRAPHICS_H_
#define GRAPHICS_H_

// function defs
void GLCD_Abs(int16_t* num);
void GLCD_Draw_Line_Hor(uint8_t x1, uint8_t y1, uint8_t x2, uint8_t color);
void GLCD_Draw_Line_Vert(uint8_t x1, uint8_t y1, uint8_t y2, uint8_t color);
void GLCD_Draw_Rect(uint8_t x1, uint8_t y1, uint8_t x2, uint8_t y2, uint8_t color);
void GLCD_Draw_Rect_Filled(uint8_t x1, uint8_t y1, uint8_t x2, uint8_t y2, uint8_t color);
void GLCD_Draw_Arc_Point(uint8_t center_x, uint8_t center_y, uint8_t x, uint8_t y, uint8_t octant, uint8_t color);
void GLCD_Draw_Arc(uint8_t center_x, uint8_t center_y, uint8_t radius, uint8_t octant, uint8_t color);
void GLCD_Draw_Circle(uint8_t x, uint8_t y, uint8_t radius, uint8_t color);
void GLCD_Draw_Rect_Rounded(uint8_t x1, uint8_t y1, uint8_t x2, uint8_t y2, uint8_t radius, uint8_t color);
void GLCD_Draw_Line_Angled(uint8_t x1, uint8_t y1, uint8_t x2, uint8_t y2, uint8_t color);
void GLCD_Bar_Graph_Create_16x64(uint8_t x1, uint8_t y1, uint8_t color);
void GLCD_Bar_Graph_Update_16x64(uint8_t x1, uint8_t y1, uint16_t ADC_value, uint8_t color);

#endif /* GRAPHICS_H_ */

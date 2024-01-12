/*
 * ks0108.h
 *
 *  Created on: Oct 11, 2021
 *      Author: edm73
 */

#include <inttypes.h>
#include <stdbool.h>
#include <pgmspace.h>
#include "font_6x8_cyrillic.h"
#include "font5x7cyrillic.h"
#include "smallFontClear.h"

#ifndef KS0108_H_
#define KS0108_H_
#ifndef TRUE
#define TRUE 1
#define FALSE 0
#endif

// define screen size
#define SCREEN_WIDTH       192
#define SCREEN_HEIGHT      64
#define SCREEN_LINE_HEIGHT 8
#define SCREEN_LINES       8
#define SCREEN_PAGES       3

// define commands
#define SET_X              0x40
#define SET_Y              0xB8
#define START_LINE         0xC0
#define DISPLAY_ON         0x3F
#define DISPLAY_OFF        0x3E
#define DISPLAY_BUSY       0x80
#define COLOR_ON           0xFF
#define COLOR_OFF          0x00
#define SHIFT_UP TRUE
#define SHIFT_DOWN FALSE

typedef struct Font{
    uint8_t width;
    uint8_t height;
    const PGM_P charData;
}Font;

typedef struct Fonts{
    Font* size5x7;
    Font* size6x8;
    Font* clear60x8;
}Fonts;

// Function Prototypes
void GLCD_Init(void);
void GLCD_Write_Data(uint8_t data);
void GLCD_Write_Command(uint8_t Command, uint8_t page);
void GLCD_Clear_Screen(uint8_t color);
void GLCD_GoTo(uint8_t x, uint8_t y);
void GLCD_Write_String(char* inString, Font* font, uint8_t color);
void GLCD_Write_Char(char charToWrite, Font* font, uint8_t color);
void GLCD_Read_Write_String(char* inString, Font* font, uint8_t color, bool transparent, bool shiftUp, int8_t pixelShift);
void GLCD_Read_Write_Char(char charToWrite, Font* font, uint8_t color, bool transparent, bool shiftUp, int8_t pixelShift);
uint8_t GLCD_Read_Byte_From_Mem(uint8_t* memPtr);
uint8_t GLCD_Read_Data(void);
uint8_t GLCD_Read_Data_No_Increment(void);
void GLCD_Write_Data_No_Increment(uint8_t data);
void GLCD_Bitmap(char* bmp, uint8_t x, uint8_t y, uint8_t dx, uint8_t dy);
void GLCD_Set_Pixel(uint8_t x, uint8_t y, uint8_t color);
void GLCD_Set_Pixels(uint8_t x1, uint8_t y1, uint8_t x2, uint8_t y2, uint8_t color);
void GLCD_Print_Number(uint32_t number, uint8_t decimalPointIndex, uint8_t numLength, Font* font, bool padZeros, uint8_t color);
void GLCD_Number_ToString(uint32_t number, uint8_t decimalPointIndex, uint8_t numLength, char inString[], bool padZeros);
char* itoa(int i, char b[]);

// debug functions
void GLCD_Print_Hex(uint32_t hexValue, Font* font);
void GLCD_Debug_Print(char* str, Font* font);


#endif /* KS0108_H_ */

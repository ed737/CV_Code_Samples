/*
* ks0108.c
*
*  Created on: Oct 11, 2021
*      Author: edm73
*   Updated and debugged
*   Oct 11 to Oct 17
*
*   Updated 11 Nov:
*   fixed large char printing
*
*   Updated 8/12
*   Fixed integer and float printing.
*/

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "ks0108.h"

extern void GLCD_Init_Ports(void);
extern Fonts fonts;
uint8_t screen_x, screen_y;
static char temp[20];

void GLCD_Init(void){

    uint8_t ii = 0;
	
    // setup ports
    GLCD_Init_Ports();

    // turn on chips
    screen_x = 0;
    screen_y = 0;

    for(ii = 0; ii < SCREEN_PAGES; ii++){
        __delay_cycles(2000);
        GLCD_Write_Command(DISPLAY_ON, ii);
        GLCD_Write_Command(START_LINE | 0x00, ii);
        __delay_cycles(2000);
    }
    GLCD_Clear_Screen(0x00); // clear the screen
}

void GLCD_GoTo(uint8_t x, uint8_t y){

    uint8_t ii;

    screen_x = x;
    screen_y = y;

    for(ii = 0; ii < SCREEN_PAGES; ii++){
        GLCD_Write_Command(SET_X | 0x00, ii);
        GLCD_Write_Command(SET_Y | y, ii);
        GLCD_Write_Command(START_LINE | 0, ii);
    }

    GLCD_Write_Command(SET_X | (x & 0x3F), (x >> 0x06)); // (x % 64), (x / 64)
    GLCD_Write_Command(SET_Y | y, (x >> 0x06));
}

void GLCD_Clear_Screen(uint8_t color){
   
    uint8_t ii, jj;
    for(jj = 0; jj < SCREEN_LINES; jj++){
        GLCD_GoTo(0, jj);
        for(ii = 0; ii < SCREEN_WIDTH; ii++){
            GLCD_Write_Data(color);
        }
    }
}

void GLCD_Write_Char(char charToWrite, Font* font, uint8_t color){

    uint8_t ii, jj, data, pages, x, y, startLine;

    // exclude this for the Cyrillic 6x8 font as it has a different format
    if(font->height != 8){
        charToWrite -= 32;
    }

    x = screen_x;
    y = screen_y;
    startLine = y >> 0x03;
    pages = font->height >> 0x03;

    if(font->height & 0x07 != 0x00){
        pages++;
    }

    for(jj = 0; jj < pages; jj++){
        for(ii = jj; ii < font->width * pages; ii += pages){
            data = GLCD_Read_Byte_From_Mem((char*)(font->charData + (font->width * charToWrite * pages) + ii));

            // if color is COLOR_OFF invert data
            if(color == COLOR_OFF){
                data = ~data & ~color;
            }
            GLCD_Write_Data(data);


        }
        if(font->width != 60){
            GLCD_Write_Data(~color); // print blank line
        }
        GLCD_GoTo(x, screen_y + 1);
    }
    GLCD_GoTo(screen_x + font->width + 1, y);
}

void GLCD_Read_Write_Char(char charToWrite, Font* font, uint8_t color, bool transparent, bool shiftUp, int8_t pixelShift){
    uint8_t ii, jj, data, pages, x, y, startLine, existingData;

    // exclude this for the Cyrillic 6x8 font as it has a different format
    if(font->height != 8){
        charToWrite -= 32;
    }

    x = screen_x;
    y = screen_y;
    startLine = y >> 0x03;
    pages = font->height >> 0x03;

    if(font->height & 0x07 != 0x00){
        pages++;
    }

    for(jj = 0; jj < pages; jj++){
        for(ii = jj; ii < font->width * pages; ii += pages){

            if(transparent){
                existingData = GLCD_Read_Data_No_Increment(); // dummy read
                GLCD_GoTo(screen_x, screen_y);
                existingData = GLCD_Read_Data_No_Increment(); // real read
                GLCD_GoTo(screen_x, screen_y);
            }
            data = GLCD_Read_Byte_From_Mem((char*)(font->charData + (font->width * charToWrite * pages) + ii));

            if(pixelShift != 0){
                if(shiftUp){
                    data >>= pixelShift;
                }
                else{
                    data <<= pixelShift;
                }
            }

            if(color == COLOR_OFF){
                data = ~data & ~color;
            }

            if(transparent){
                data |= existingData;
            }
            GLCD_Write_Data(data);


        }
        GLCD_GoTo(x, screen_y + 1);
    }
    GLCD_GoTo(screen_x + font->width + 1, y);
}


void GLCD_Write_String(char* inString, Font* font, uint8_t color){
    while(*inString){
        GLCD_Write_Char(*inString++, font, color);
    }
}

void GLCD_Read_Write_String(char* inString, Font* font, uint8_t color, bool transparent,  bool shiftUp, int8_t pixelShift){
    while(*inString){
        GLCD_Read_Write_Char(*inString++, font, color, transparent, shiftUp, pixelShift);
    }
}



void GLCD_Set_Pixel(uint8_t x, uint8_t y, uint8_t color){
    uint8_t data = 0x00;
    uint8_t mask = 0x00;
    GLCD_GoTo(x, y >> 0x03); 
    data = GLCD_Read_Data(); // dummy read
    GLCD_GoTo(x, y >> 0x03);
    data = GLCD_Read_Data(); // real read
    GLCD_GoTo(x, y >> 0x03);
    
	 // if color is COLOR_ON OR mask with y % 8 else AND mask with not y % 8
	mask |= (0x01 << (y & 0x07));
    data = ((color == COLOR_ON) ? (data | mask) : (data & ~mask));
    GLCD_Write_Data(data);
}

// set bulk pixels at once
void GLCD_Set_Pixels(uint8_t x1, uint8_t y1, uint8_t x2, uint8_t y2, uint8_t color){

    uint8_t height, width, yOffset, mask, h, ii, data = 0;
    height = y2 - y1 + 0x01;
    width = x2 - x1 + 0x01;
    yOffset = y1 & 0x07;
    y1 -= yOffset;
    mask = 0xFF;
    data = 0x00;

    // calculate top section mask
    if(height < (SCREEN_LINE_HEIGHT - yOffset)){
        mask >>= (SCREEN_LINE_HEIGHT - height);
        h = height;
    }
    else{
        h = SCREEN_LINE_HEIGHT - yOffset;
    }
	
    mask <<= yOffset;

    // print top line of pixels across width
    for(ii = 0; ii < width; ii++){
        GLCD_GoTo(x1 + ii, y1 >> 0x03);
        data = GLCD_Read_Data(); // dummy read
        GLCD_GoTo(x1 + ii, y1 >> 0x03);
        data = GLCD_Read_Data(); // real read
        GLCD_GoTo(x1 + ii, y1 >> 0x03);
		
        // if COLOR_ON OR mask with y % 8 else AND mask with not y % 8
        data = ((color == COLOR_ON) ? (data | mask) : (data & ~mask));
        GLCD_Write_Data(data);
    }

    // print full rows
    while((h + SCREEN_LINE_HEIGHT) <= height){
        h += SCREEN_LINE_HEIGHT;
        y1 += SCREEN_LINE_HEIGHT;
        GLCD_GoTo(x1, y1 >> 0x03);

        // print all lines
        for(ii = 0; ii < width; ii++){
            GLCD_Write_Data(color);
            GLCD_GoTo(screen_x, screen_y);
        }

    }

    // print bottom section
    if(h < height){
        mask  = ~(0xFF << (height - h));
        y1 += SCREEN_LINE_HEIGHT;
        
		// print remaining lines
        for(ii = 0; ii < width; ii++){
            mask  = ~(0xFF << (height - h));
            GLCD_GoTo(x1+ii, y1 >> 0x03);
            data = GLCD_Read_Data(); // dummy read
            GLCD_GoTo(x1+ii, y1 >> 0x03);
            data = GLCD_Read_Data(); // real read
            GLCD_GoTo(x1+ii, y1 >> 0x03);
            data = ((color == COLOR_ON) ? (data | mask) : (data & ~mask));
            GLCD_Write_Data(data);
        }
    }
}

// prints a number
// for floating point send decimal point index at appropriate location (from least significant digit of number)
// for integers dpIndex = 0
// i.e. GLCD_Print_Number(501, 3, 5, font) will print "5.010"
//      GLCD_Print_Number(501, 0, 5, font) will print "  501"
void GLCD_Print_Number(uint32_t number, uint8_t decimalPointIndex, uint8_t numLength, Font* font, bool padZeros, uint8_t color){

    int8_t ii;
    uint8_t digit;

    // case for integer printing
    if(decimalPointIndex == 0){
        numLength -= 1;
    }

    for(ii = numLength; ii > -1; ii--){
        if(ii == numLength - decimalPointIndex && decimalPointIndex != 0){
            temp[ii] = '.';
        }
        else{

            digit = number % 10;
            number /= 10;
            temp[ii] = (char)(digit + '0');
        }
    }

    ii = 0;

    if(!padZeros){
		
        // start from front of string and replace padding zeros with ' '
        while(temp[ii] == '0'){
            if(temp[ii + 1] != '.'){
                temp[ii] = ' ';
            }
            ii++;
        }
    }

    temp[numLength + 1] = '\0'; // add string terminator
    if(color == COLOR_ON){
        GLCD_Write_String(temp, font, COLOR_ON);
    }
    else{
        GLCD_Write_String(temp, font, COLOR_OFF);
    }
}

void GLCD_Number_ToString(uint32_t number, uint8_t decimalPointIndex, uint8_t numLength, char inString[], bool padZeros){

    int8_t ii;
    uint8_t digit;

    if(decimalPointIndex == 0){
        numLength -= 1;
    }

    for(ii = numLength; ii > -1; ii--){
        if(ii == numLength - decimalPointIndex && decimalPointIndex != 0){
            inString[ii] = '.';
        }
        else{

            digit = number % 10;
            number /= 10;
            inString[ii] = (char)(digit + '0');
        }
    }

    ii = 0;
	
    // start from front of string and replace padding zeros with ' '
    if(!padZeros){
        while(inString[ii] == '0'){
            if(inString[ii + 1] != '.'){
                inString[ii] = ' ';
            }
            ii++;
        }
    }
	
    inString[numLength + 1] = '\0'; // add string terminator
}


char* itoa(int i, char b[]){
    char const digit[] = "0123456789";
    char* p = b;

    if(i<0){
        *p++ = '-';
        i *= -1;
    }
    int shifter = i;
    do{
        //Move to where representation ends
        ++p;
        shifter = shifter/10;
    }while(shifter);

    *p = '\0';

    do{
        //Move back, inserting digits as u go
        *--p = digit[i%10];
        i = i/10;
    }while(i);
    return b;
}

// debug functions:
// TODO remove this function in final project to avoid use of stdio.h
void GLCD_Print_Hex(uint32_t hexValue, Font* font){
    char str[8];
    sprintf(str, "%x", hexValue);
    GLCD_Write_String(str, font, COLOR_ON);
}

void GLCD_Debug_Print(char* str, Font* font){
    GLCD_Clear_Screen(0x00);
    GLCD_GoTo(10,3);
    GLCD_Write_String(str, font, COLOR_ON);
}

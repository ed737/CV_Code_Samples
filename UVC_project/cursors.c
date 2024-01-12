/*
 * cursors.c
 *
 *  Created on: 23 Feb 2022
 *      Author: ed
 */

#include "cursors.h"

MenuCursor mc;
NumberCursor nc;
MenuCursor* menuCursor;
NumberCursor* numberCursor;
Cursors cursors;
extern char cellTextArray[6][MAX_STRING_LENGTH];
extern Fonts fonts;
char numCursorText[MAX_STRING_LENGTH];

void cursors_init(void){

    // initialize mc, nc and cursors struct
    mc.activeCells[0] = 1;
    mc.activeCells[1] = 1;
    mc.activeCells[2] = 1;
    mc.activeCells[3] = 1;
    mc.activeCells[4] = 1;
    mc.activeCells[5] = 1;
    mc.cellNumber = 0;
    mc.col = 0;
    mc.row = 0;
    mc.text = "";

    nc.value = 0;
    nc.digitArray[0] = 0;
    nc.digitArray[1] = 0;
    nc.digitArray[2] = 0;
    nc.digitArray[3] = 0;
    nc.digitArray[4] = 0;
    nc.digitArray[5] = 0;
    nc.position = 0;
    nc.sigFigures = 0;
    nc.decimalPointIndex = 0;

    cursors.cursorType = menu;
    cursors.menuCursor = &mc;
    cursors.numberCursor = &nc;

}

/*****************************
 * menu cursor helper functions
 *****************************/
void set_active_cells(bool a, bool b, bool c, bool d, bool e, bool f){
	cursors.menuCursor->activeCells[0] = a;
    cursors.menuCursor->activeCells[1] = b;
    cursors.menuCursor->activeCells[2] = c;
    cursors.menuCursor->activeCells[3] = d;
    cursors.menuCursor->activeCells[4] = e;
    cursors.menuCursor->activeCells[5] = f;
}

/*****************************
 * num cursor helper functions
 *****************************/
void clear_num_cursor(void){
    cursors.numberCursor->value = 0;
    cursors.numberCursor->digitArray[0] = 0;
    cursors.numberCursor->digitArray[1] = 0;
    cursors.numberCursor->digitArray[2] = 0;
    cursors.numberCursor->digitArray[3] = 0;
    cursors.numberCursor->digitArray[4] = 0;
    cursors.numberCursor->digitArray[5] = 0;
    cursors.numberCursor->position		= 0;
    cursors.numberCursor->sigFigures	= 0;
    print_num_cursor();
}

// this function sets num cursor values from the input value
void set_num_cursor(uint32_t value, uint8_t sigFigures, uint8_t decimalPointIndex){
    
	uint8_t ii = 0;

    clear_num_cursor();
    cursors.numberCursor->value = value;

    while(value > 0){
        cursors.numberCursor->digitArray[ii] = value % 10;
        value /= 10;
        ii++;
    }

    cursors.numberCursor->decimalPointIndex = decimalPointIndex;
    cursors.numberCursor->position = 0;
    cursors.numberCursor->sigFigures = sigFigures;
    Clear_Cell_Text(cursors.menuCursor->cellNumber, COLOR_ON);
    print_num_cursor();
}

uint32_t save_num_cursor(void){
    
	uint32_t value = 0;
    uint8_t ii;
    uint32_t jj = 1;

    for(ii = 0; ii < cursors.numberCursor->sigFigures; ii++){
        value += (cursors.numberCursor->digitArray[ii] * jj);
        jj *= 10;
    }

    return value;
}

void move_num_cursor_left(void){
    if(cursors.numberCursor->position < cursors.numberCursor->sigFigures - 1){
        cursors.numberCursor->position++;
        print_num_cursor();
    }
}

void move_num_cursor_right(void){
    if(cursors.numberCursor->position > 0){
            cursors.numberCursor->position--;
            print_num_cursor();
    }
}

void increment_num_cursor(void){
    if(cursors.numberCursor->digitArray[cursors.numberCursor->position] == 9){
        cursors.numberCursor->digitArray[cursors.numberCursor->position] = 0;
        print_num_cursor();
    }
    else{
        cursors.numberCursor->digitArray[cursors.numberCursor->position]++;
        print_num_cursor();
    }
}

void decrement_num_cursor(void){
    if(cursors.numberCursor->digitArray[cursors.numberCursor->position] == 0){
        cursors.numberCursor->digitArray[cursors.numberCursor->position] = 9;
        print_num_cursor();
    }
    else{
        cursors.numberCursor->digitArray[cursors.numberCursor->position]--;
        print_num_cursor();
    }
}

void print_num_cursor(){
     
	 int8_t ii;
     for(ii = 0; ii < cursors.numberCursor->sigFigures; ii++){
         numCursorText[ii] = cursors.numberCursor->digitArray[ii] + '0';
     }

     switch(cursors.menuCursor->cellNumber){
		case 0:		GLCD_GoTo(  8, 2);		break;
        case 1:		GLCD_GoTo(104, 2);		break;
        case 2:		GLCD_GoTo(  8, 4);		break;
        case 3:		GLCD_GoTo(104, 4);		break;
        case 4:		GLCD_GoTo(  8, 6);		break;
        case 5:		GLCD_GoTo(104, 6);		break;
     }

     for(ii = cursors.numberCursor->sigFigures; ii > -1; ii--){
        if(cursors.numberCursor->decimalPointIndex != 0){
             if(ii == cursors.numberCursor->decimalPointIndex - 1){
                 GLCD_Write_Char('.', fonts.size6x8 , COLOR_ON);
             }
         }
         if(ii == cursors.numberCursor->position){
             GLCD_Write_Char(numCursorText[ii], fonts.size6x8 , COLOR_OFF);
         }
         else{
             GLCD_Write_Char(numCursorText[ii], fonts.size6x8 , COLOR_ON);
         }
     }
	 
}

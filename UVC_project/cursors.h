/*
 * cursors.h
 *
 *  Created on: 23 Feb 2022
 *      Author: ed
 */

#ifndef CURSORS_H_
#define CURSORS_H_

#include <inttypes.h>
#include <stdbool.h>
#include <string.h>
#include "data.h"
#include "user_interface.h"

typedef enum CursorType{
    menu,
    number
}CursorType;

typedef struct MenuCursor{
    uint8_t row;
    uint8_t col;
    uint8_t cellNumber;
    const char* text;
    bool activeCells[6];
}MenuCursor;

typedef struct NumberCursor{
    uint8_t position;
    uint8_t digitArray[6];
    uint8_t sigFigures;
    uint8_t decimalPointIndex;
    uint32_t value;
}NumberCursor;

typedef struct Cursors{
    CursorType cursorType;
    MenuCursor* menuCursor;
    NumberCursor* numberCursor;
}Cursors;

// function defs
void cursors_init(void);

// menu cursor helper functions
void set_active_cells(bool a, bool b, bool c, bool d, bool e, bool f);

// num cursor helper functions
void clear_num_cursor(void);
void set_num_cursor(uint32_t value, uint8_t sigFigures, uint8_t decimalPointIndex);
void move_num_cursor_left(void);
void move_num_cursor_right(void);
void increment_num_cursor(void);
void decrement_num_cursor(void);
void print_num_cursor(void);
uint32_t save_num_cursor(void);

#endif /* CURSORS_H_ */

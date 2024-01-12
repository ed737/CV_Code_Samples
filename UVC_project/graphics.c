/*
 * graphics.c
 *
 *  Created on: Oct 15, 2021
 *      Author: edm73
 *      Updated nov 11:
 *      draw circle and arc
 *      draw round rect
 */

#include "graphics.h"
#include "data.h"

extern uint8_t screen_x, screen_y;

// these function declarations local to this translation unit
static void GLCD_Plot_Line_Low(uint8_t x1, uint8_t y1, uint8_t x2, uint8_t y2, uint8_t color);
static void GLCD_Plot_Line_High(uint8_t x1, uint8_t y1, uint8_t x2, uint8_t y2, uint8_t color);

void GLCD_Abs(int16_t* num){
    if(*num < 0){
        *num = -(*num);
    }
}

void GLCD_Draw_Line_Hor(uint8_t x1, uint8_t y1, uint8_t x2, uint8_t color){

    uint8_t ii;
    GLCD_GoTo(x1, y1);

    for(ii = x1; ii < x2; ii++){
        GLCD_Set_Pixel(ii, y1, color);
    }
}

void GLCD_Draw_Line_Vert(uint8_t x1, uint8_t y1, uint8_t y2, uint8_t color){
    GLCD_Set_Pixels(x1, y1, x1, y2, color);
}

void GLCD_Draw_Rect(uint8_t x1, uint8_t y1, uint8_t x2, uint8_t y2, uint8_t color){
    GLCD_Draw_Line_Hor(x1, y1, x2, color);
    GLCD_Draw_Line_Hor(x1, y2, x2, color);
    GLCD_Draw_Line_Vert(x1, y1, y2, color);
    GLCD_Draw_Line_Vert(x2, y1, y2, color);
}

void GLCD_Draw_Rect_Filled(uint8_t x1, uint8_t y1, uint8_t x2, uint8_t y2, uint8_t color){
    GLCD_Set_Pixels(x1, y1, x2, y2, color);
}

// draw angled line using Bresenham line algorithm
void GLCD_Draw_Line_Angled(uint8_t x1, uint8_t y1, uint8_t x2, uint8_t y2, uint8_t color){
    int16_t dy = y2 - y1;
    int16_t dx = x2 - x1;
    GLCD_Abs(&dy);
    GLCD_Abs(&dx);

    if(dy < dx){
        if(x1 > x2){
            GLCD_Plot_Line_Low(x2, y2, x1, y1, color);
        }
        else{
            GLCD_Plot_Line_Low(x1, y1, x2, y2, color);
        }
    }
    else{
        if(y1 > y2){
            GLCD_Plot_Line_High(x2, y2, x1, y1, color);
        }
        else{
            GLCD_Plot_Line_High(x1, y1, x2, y2, color);
        }
    }

}

// this function used for Draw line angled only
void GLCD_Plot_Line_Low(uint8_t x1, uint8_t y1, uint8_t x2, uint8_t y2, uint8_t color){
	
    int16_t dx = x2 - x1;
    int16_t dy = y2 - y1;
    int8_t  yi = 1;
    uint8_t ii;
    int16_t D , y;

    if(dy < 0){
        yi = -1;
        dy = -dy;
    }

    D = dy + dy - dx;
    y = y1;

    for(ii = x1; ii < x2; ii++){
        GLCD_Set_Pixel(ii, y, color);

        if (D > 0){
            y = y + yi;
            D = D + (dy - dx) + (dy - dx);
        }
        else{
            D = D + dy + dy;
        }
    }
}

// this function used for Draw line angled only
void GLCD_Plot_Line_High(uint8_t x1, uint8_t y1, uint8_t x2, uint8_t y2, uint8_t color){
    int16_t dx = x2 - x1;
    int16_t dy = y2 - y1;
    int8_t xi = 1;
    uint8_t ii;
    int16_t D , x;

    if(dx < 0){
        xi = -1;
        dx = -dx; // make dy positive
    }

    D = dx + dx - dy;
    x = x1;

    for(ii = y1; ii < y2; ii++){
        GLCD_Set_Pixel(x, ii, color);

        if (D > 0){
            x = x + xi;
            D = D + ((dx - dy) + (dx - dy));
        }
        else{
            D = D + dx + dx;
        }
    }
}

// draw an arc centered at center_x, center_y from start point x,y in given octant (0-7).
void GLCD_Draw_Arc_Point(uint8_t center_x, uint8_t center_y, uint8_t x, uint8_t y, uint8_t octant, uint8_t color){

    switch(octant){
        case 0:
            GLCD_Set_Pixel(center_x + y, center_y - x, color);
        break;

        case 1:
            GLCD_Set_Pixel(center_x + x, center_y - y, color);
        break;

        case 2:
            GLCD_Set_Pixel(center_x - x, center_y - y, color);
        break;

        case 3:
            GLCD_Set_Pixel(center_x - y , center_y - x, color);
        break;

        case 4:
            GLCD_Set_Pixel(center_x - y, center_y + x, color);
        break;

        case 5:
            GLCD_Set_Pixel(center_x - x, center_y + y, color);
        break;

        case 6:
            GLCD_Set_Pixel(center_x + x, center_y + y, color);
        break;

        case 7:
            GLCD_Set_Pixel(center_x + y, center_y + x, color);
        break;

        default:
        break;
    }
}

// draw an arc in octant (0-7) starting from x +ve y +ve of radius
void GLCD_Draw_Arc(uint8_t center_x, uint8_t center_y, uint8_t radius, uint8_t octant, uint8_t color){
    uint8_t x = 0;
    uint8_t y = radius;
    int8_t  d = 0x03 - (radius + radius);

    GLCD_Draw_Arc_Point(center_x, center_y, x, y, octant , color);

    while(y >= x){
           x++;

           if(d > 0){
               y--;
               d = d + ((x - y) << 0x02) + 10;
           }
           else{
               d = d +  (x << 0x02) + 6;
           }
           GLCD_Draw_Arc_Point(center_x, center_y, x, y, octant , color);
       }
}

// Bresenham circle drawing algorithm
void GLCD_Draw_Circle(uint8_t center_x, uint8_t center_y, uint8_t radius, uint8_t color){
    uint8_t ii;

    for(ii = 0; ii < 8; ii++){
        GLCD_Draw_Arc(center_x, center_y, radius, ii , color);
    }

}

void GLCD_Draw_Rect_Rounded(uint8_t x1, uint8_t y1, uint8_t x2, uint8_t y2, uint8_t radius,  uint8_t color){
    uint8_t dx1 = x1 + radius;
    uint8_t dy1 = y1 + radius;
    uint8_t dx2 = x2 - radius;
    uint8_t dy2 = y2 - radius;
    uint8_t height_total = y2 - y1;
    uint8_t height_p_to_p = height_total - radius - radius;

    // draw in rounded corners:

    // top right
    GLCD_Draw_Arc(dx2, dy2 - height_p_to_p, radius, 0, color);
    GLCD_Draw_Arc(dx2, dy2 - height_p_to_p, radius, 1, color);

    // top left
    GLCD_Draw_Arc(dx1, dy1, radius, 2, color);
    GLCD_Draw_Arc(dx1, dy1, radius, 3, color);

    // bottom left
    GLCD_Draw_Arc(dx1, dy1 + height_p_to_p, radius, 4, color);
    GLCD_Draw_Arc(dx1, dy1 + height_p_to_p, radius, 5, color);

    // bottom right
    GLCD_Draw_Arc(dx2, dy2, radius, 6, color);
    GLCD_Draw_Arc(dx2, dy2, radius, 7, color);

    // draw lines
    GLCD_Draw_Line_Hor(dx1, y1, dx2, color);
    GLCD_Draw_Line_Hor(dx1, y2, dx2, color);
    GLCD_Draw_Line_Vert(x1, dy1, dy2, color);
    GLCD_Draw_Line_Vert(x2, dy1, dy2, color);
}

// Bar graph widgets
void GLCD_Bar_Graph_Create_16x64(uint8_t x1, uint8_t y1, uint8_t color){
    GLCD_Draw_Rect(x1+2, y1+2, x1+14, y1+62, color);
    GLCD_Draw_Rect(x1+4, y1+4, x1+12, y1+60, color);
}

void GLCD_Bar_Graph_Update_16x64(uint8_t x1, uint8_t y1, uint16_t ADC_value, uint8_t color){
    uint16_t d_ADC = 1023 - ADC_value;
    uint8_t dy = d_ADC / 21;
    GLCD_Draw_Rect_Filled(x1+6, y1+6, x1+10, y1+dy+6, COLOR_OFF);
    GLCD_Draw_Rect_Filled(x1+6, y1+dy+6, x1+10, y1+58, color);
}

#endif


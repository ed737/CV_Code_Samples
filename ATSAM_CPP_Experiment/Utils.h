#pragma once

/*-------------------------------------------------------------------------------------------------------------------------
 *	File:		Utils.h
 *	Project:	HAL_Testing
 *	Author:		Edward Munns
 *	Created:	19 Aug 2023
 *--------------------------------------------------------------------------------------------------------------------------
 * 	Brief:
 *	
 *------------------------------------------------------------------------------------------------------------------------*/

#include <cinttypes>

namespace Utils {
	
	inline void delayCycles(volatile uint32_t cycles)
	{
		while(cycles--) __asm("nop");
	}
}//----------------------------------- end namespace Utils ------------------------------------------------------
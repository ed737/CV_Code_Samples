#pragma once

/*-------------------------------------------------------------------------------------------------------------------------
*	File:		Board.h
*	Project:	HAL_Testing
*	Author:		Edward Munns
*	Created:	19 Aug 2023
*--------------------------------------------------------------------------------------------------------------------------
* 	Brief:
*	
--------------------------------------------------------------------------------------------------------------------------*/

namespace Board{

	static void init()
	{
		System::IPMC pmc;
		System::IWDG wdg;
		wdg.disable();
	
		// enable clocks on ports
		pmc.enablePeriphClk(Periph::ID::_PIOA);
		pmc.enablePeriphClk(Periph::ID::_PIOB);
		pmc.enablePeriphClk(Periph::ID::_PIOC);
		pmc.enablePeriphClk(Periph::ID::_PIOD);
	}
}

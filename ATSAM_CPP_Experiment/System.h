#pragma once

/*-------------------------------------------------------------------------------------------------------------------------
*	File:		System.h
*	Project:	HAL_Testing
*	Author:		Edward Munns
*	Created:	19 Aug 2023
*--------------------------------------------------------------------------------------------------------------------------
* 	Brief: initializes Power Management Controller, watchdog timer, reset and power controllers  
--------------------------------------------------------------------------------------------------------------------------*/

#include <cinttypes>
#include "Periphial.h"
#include "SysReg.h"

namespace System{

	class IWDG : protected Reg::Master{
		public:
		enum class Offset : Reg::Offset_t {
			_Control	=	0x00000000u,
			_Mode		=	0x00000004u,
			_Status		=	0x00000008u
		};
	
		static constexpr Reg::Reg_t startAdd = static_cast<Reg::Reg_t>(getStartAddress(Periph::ID::_WDG));
		static constexpr Reg::Reg_t endAdd   = static_cast<Reg::Reg_t>(Offset::_Status) + startAdd;
		static constexpr Reg::Reg_t ctrlKey  = 0xA5000000u;
	 
		WriteOnly<startAdd,	Offset,	Offset::_Control, endAdd> r_Control;
		ReadWrite<startAdd, Offset, Offset::_Mode,	  endAdd> r_Mode;
		ReadOnly<startAdd,	Offset,	Offset::_Status,  endAdd> r_Status;
	
		enum class Mode : Reg::Reg_t {
			_FaultInterrupt = (1 << 12u),
			_ResetEnable	= (1 << 13u),
			_ResetProcessor = (1 << 14u),
			_Disable		= (1 << 15u),
			_DebugHalt		= (1 << 28u),
			_IdleHalt		= (1 << 29u) 	
		}; 
	
		void restart(){ r_Control.write(ctrlKey | 0x1u); };
		void disable(){
			r_Mode.write(static_cast<Reg::Reg_t>(Mode::_Disable));	
		};
		
	};

	class IPMC : protected Reg::Master{
		//static_assert(Periph::getType(inID) == Periph::Type::_Power_Managment_Control, "Invalid Periphial ID used to initialize Power management controller!");
	
		private:
		enum class Offset : Reg::Offset_t {
			_SysClkEnable			=	0x00000000u,
			_SysClkDisable			=	0x00000004u,
			_SysClkStatus			=	0x00000008u,
		
			_PeriphClkEnable_0		=	0x00000010u,
			_PeriphClkDisable_0		=	0x00000014u,
			_PeriphClkStatus_0		=	0x00000018u,
		
			_UTMI_Clk				=	0x0000001Cu,
		
			_MainOscillator			=	0x00000020u,
			_MainClkFreq			=	0x00000024u,
			_PLLA					=	0x00000028u,
		
			_MasterClk				=	0x00000030u,
			_USBClk					=	0x00000038u,
		
			_ProgClk_0				=	0x00000040u,
			_ProgClk_1				=	0x00000044u,
			_ProgClk_2				=	0x00000048u,
		
			_InterruptEnable		=	0x00000060u,
			_InterruptDisable		=	0x00000064u,
			_Status					=	0x00000068u,
			_InterruptMask			=	0x0000006Cu,
		
			_FastStartupMode		=	0x00000070u,
			_FastStartupPolarity	=	0x00000074u,
			_FaultOutputClear		=	0x00000078u,
		
			_WriteProtectMode		=	0x000000E4u,
			_WriteProtectStatus		=	0x000000E8u,
		
			_PeriphClkEnable_1		=	0x00000100u,
			_PeriphClkDisable_1		=	0x00000104u,
			_PeriphClkStatus_1		=	0x00000108u,
			_PeriphControl			=	0x0000010Cu
		};
	
		static constexpr Reg::Reg_t startAdd = static_cast<Reg::Reg_t>(getStartAddress(Periph::ID::_PMC));
		static constexpr Reg::Reg_t endAdd   = static_cast<Reg::Reg_t>(Offset::_PeriphControl) + startAdd;
	
		WriteOnly<startAdd,	Offset,	Offset::_SysClkEnable,			endAdd> r_SysClkEnable;
		WriteOnly<startAdd,	Offset,	Offset::_SysClkDisable,			endAdd>	r_SysClkDisable;
		ReadOnly<startAdd,	Offset,	Offset::_SysClkStatus,			endAdd> r_SysClkStatus;
	
		WriteOnly<startAdd,	Offset,	Offset::_PeriphClkEnable_0,		endAdd> r_PeriphClkEnable_0;
		WriteOnly<startAdd,	Offset,	Offset::_PeriphClkDisable_0,	endAdd> r_PeriphClkDisable_0;
		ReadOnly<startAdd,	Offset,	Offset::_PeriphClkStatus_0,		endAdd> r_PeriphClkStatus_0;
	
		ReadWrite<startAdd, Offset, Offset::_UTMI_Clk,				endAdd>	r_UTMI_Clk;
		ReadWrite<startAdd, Offset, Offset::_MainOscillator,		endAdd>	r_MainOcillator;
		ReadOnly<startAdd,	Offset,	Offset::_MainClkFreq,			endAdd> r_MainClkFreq;
		ReadWrite<startAdd, Offset, Offset::_PLLA,					endAdd>	r_PLLA;
	
		ReadWrite<startAdd, Offset, Offset::_MasterClk,				endAdd>	r_MasterClk;
	
		ReadWrite<startAdd, Offset, Offset::_USBClk,				endAdd>	r_USBClk;
	
		ReadWrite<startAdd, Offset, Offset::_ProgClk_0,				endAdd>	r_ProgClk_0;
		ReadWrite<startAdd, Offset, Offset::_ProgClk_1,				endAdd>	r_ProgClk_1;
		ReadWrite<startAdd, Offset, Offset::_ProgClk_2,				endAdd>	r_ProgClk_2;
	
		WriteOnly<startAdd,	Offset,	Offset::_InterruptEnable,		endAdd> r_InterruptEnable;
		WriteOnly<startAdd,	Offset,	Offset::_InterruptEnable,		endAdd> r_InterruptDisable;
		ReadOnly<startAdd,	Offset,	Offset::_Status,				endAdd> r_Status;
		ReadOnly<startAdd,	Offset,	Offset::_InterruptMask,			endAdd> r_InterruptMask;
	
		ReadWrite<startAdd, Offset, Offset::_FastStartupMode,		endAdd>	r_FastStartupMode;
		ReadWrite<startAdd, Offset, Offset::_FastStartupPolarity,	endAdd>	r_FastStartupPolarity;
		WriteOnly<startAdd,	Offset,	Offset::_FaultOutputClear,		endAdd> r_FaultOutputClear;
	
		ReadWrite<startAdd, Offset, Offset::_WriteProtectMode,		endAdd>	r_WriteProtectMode;
		ReadOnly<startAdd,	Offset,	Offset::_WriteProtectStatus,	endAdd> r_WriteProtectStatus;
	
		WriteOnly<startAdd,	Offset,	Offset::_PeriphClkEnable_1,		endAdd> r_PeriphClkEnable_1;
		WriteOnly<startAdd,	Offset,	Offset::_PeriphClkDisable_1,	endAdd> r_PeriphClkDisable_1;
		ReadOnly<startAdd,	Offset,	Offset::_PeriphClkStatus_1,		endAdd> r_PeriphClkStatus_1;
	
		ReadWrite<startAdd, Offset, Offset::_PeriphControl,			endAdd>	r_PeriphControl;
	
		public:
	
		inline void enablePeriphClk(const Periph::ID inPeriphID){	r_PeriphClkEnable_0.write( 1 << static_cast<uint32_t>(inPeriphID));	}; 
	};
}


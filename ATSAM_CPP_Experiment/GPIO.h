#pragma once

#include <cinttypes>
#include "System.h"

namespace GPIO	{
	
	/***********************************************************************************
	*	File:		gpio.h
	*	Project:	HAL_Testing
	*	Author:		Edward Munns
	*	Created:	05 Aug 2023
	*-----------------------------------------------------------------------------------
	* 	Brief:
	*		
	************************************************************************************/ 
	
	template <Periph::ID portID>
	class IPort : protected Reg::Master{
		static_assert(Periph::getType(portID) == Periph::Type::_GPIO_Port, 
		"GPIO IPort template instantiation attempted on non-port type Periphial!");
		public:
		
		enum class Offset : Reg::Offset_t {
			_GPIO_Enable			= 0x00000000u,		_GPIO_Disable			= 0x00000004u,		_GPIO_Status			= 0x00000008u,
			_OutputEnable			= 0x00000010u,		_OutputDisable			= 0x00000014u,		_OutputStatus			= 0x00000018u,
			_InputFilterEnable		= 0x00000020u,		_InputFilterDisable		= 0x00000024u,		_InputFilterStatus		= 0x00000028u,
			_SetOutputData			= 0x00000030u,		_ClearOutputData		= 0x00000034u,		_OutputDataStatus		= 0x00000038u,
			_PinDataStatus			= 0x0000003Cu,		_InterruptEnable		= 0x00000040u,		_InterruptDisable		= 0x00000044u,
			_InterruptMask			= 0x00000048u,		_InterruptStatus 		= 0x0000004Cu,		_MultiDriverEnable		= 0x00000050u,
			_MultiDriverDisable		= 0x00000054u,		_MultiDriverStatus		= 0x00000058u,		_PullupDisable			= 0x00000060u,
			_PullupEnable			= 0x00000064u,		_PullupStatus			= 0x00000068u,		_PeriphialSelect		= 0x00000070u,
			_GlitchFilterSelect		= 0x00000080u,		_DebounceFilterSelect	= 0x00000084u,		_FilterSelectStatus		= 0x00000088u,
			_SlowClkDebounce		= 0x0000008Cu,		_OutputWriteEnable		= 0x000000A0u,		_OutputWriteDisable		= 0x000000A4u,
			_OutputWriteStatus		= 0x000000A8u,		_ExtraIntModesEnable	= 0x000000B0u,		_ExtraIntModesDisable	= 0x000000B4u,
			_ExtraIntModesMask		= 0x000000B8u,		_EdgeIntModeSelect		= 0x000000C0u,		_LevelIntModeSelect	    = 0x000000C4u,
			_EdgeLevelStatus		= 0x000000C8u,		_FallEdgeLowLevSelect	= 0x000000D0u,		_RiseEdgeHighLevSelect	= 0x000000D4u,
			_FallRiseHighLowStatus	= 0x000000D8u,		_RegWriteLockStatus  	= 0x000000E0u,		_WriteProtectMode		= 0x000000E4u,
			_WriteProtectStatus		= 0x000000E8u,		_FailureTest			= 0x000000F0u
		};
		
		static constexpr Reg::Reg_t startAdd = static_cast<Reg::Reg_t>(getStartAddress(portID));
		static constexpr Reg::Reg_t endAdd   = static_cast<Reg::Reg_t>(Offset::_WriteProtectStatus) + startAdd;
		
		// register declarations
		// TODO: Implement registers with structure binding and emplacement new?
		WriteOnly<startAdd,	Offset,	Offset::_GPIO_Enable,			endAdd>	GPIO_Enable;
		WriteOnly<startAdd,	Offset,	Offset::_GPIO_Disable,			endAdd>	GPIO_Disable;
		ReadOnly<startAdd,	Offset,	Offset::_GPIO_Status,			endAdd> GPIO_Status;
		
		WriteOnly<startAdd,	Offset,	Offset::_OutputEnable,			endAdd> OutputEnable;
		WriteOnly<startAdd,	Offset,	Offset::_OutputDisable,			endAdd> OutputDisable;
		WriteOnly<startAdd,	Offset,	Offset::_OutputStatus,			endAdd> OutputStatus;
		
		WriteOnly<startAdd,	Offset,	Offset::_InputFilterEnable,		endAdd> InputFilterEnable; 
		WriteOnly<startAdd,	Offset,	Offset::_InputFilterDisable,	endAdd> InputFilterDisable;
		ReadOnly<startAdd,	Offset,	Offset::_InputFilterStatus,		endAdd> InputFilterStatus;
		
		WriteOnly<startAdd,	Offset,	Offset::_SetOutputData,			endAdd> SetOutputData;
		WriteOnly<startAdd,	Offset,	Offset::_ClearOutputData,		endAdd> ClearOutputData;
		ReadWrite<startAdd,	Offset,	Offset::_OutputDataStatus,		endAdd> OutputDataStatus;
		
		ReadOnly<startAdd,	Offset,	Offset::_PinDataStatus,			endAdd> PinDataStatus;
		
		WriteOnly<startAdd,	Offset,	Offset::_InterruptEnable,		endAdd>	InterruptEnable;
		WriteOnly<startAdd,	Offset,	Offset::_InterruptDisable,		endAdd>	InterruptDisable;
		ReadOnly<startAdd,	Offset,	Offset::_InterruptMask,			endAdd>	InterruptMask;
		ReadOnly<startAdd,	Offset,	Offset::_InterruptStatus,		endAdd>	InterruptStatus;

		WriteOnly<startAdd,	Offset,	Offset::_MultiDriverEnable,		endAdd>	MultiDriverEnable;
		WriteOnly<startAdd,	Offset,	Offset::_MultiDriverDisable,	endAdd>	MultiDriverDisable;
		ReadOnly<startAdd,	Offset,	Offset::_MultiDriverStatus,		endAdd>	MultiDriverStatus;
		
		WriteOnly<startAdd,	Offset,	Offset::_PullupDisable,			endAdd>	PullupDisable;
		WriteOnly<startAdd,	Offset,	Offset::_PullupEnable,			endAdd>	PullupEnable;
		ReadOnly<startAdd,	Offset,	Offset::_PullupStatus,			endAdd> PullupStatus;

		ReadWrite<startAdd,	Offset,	Offset::_PeriphialSelect,		endAdd>	PeriphialSelect;

		WriteOnly<startAdd,	Offset,	Offset::_GlitchFilterSelect,	endAdd>	GlitchFilterSelect;
		WriteOnly<startAdd,	Offset,	Offset::_DebounceFilterSelect,	endAdd>	DebounceFilterSelect;
		ReadOnly<startAdd,	Offset,	Offset::_FilterSelectStatus,	endAdd>	FilterSelectStatus;
		ReadWrite<startAdd,	Offset,	Offset::_SlowClkDebounce,		endAdd>	SlowClkDebounce;
		
		WriteOnly<startAdd,	Offset,	Offset::_OutputWriteEnable,		endAdd>	OutputWriteEnable;
		WriteOnly<startAdd,	Offset,	Offset::_OutputWriteDisable,	endAdd>	OutputWriteDisable;
		ReadOnly<startAdd,	Offset,	Offset::_OutputWriteStatus,		endAdd>	OutputWriteStatus;

		WriteOnly<startAdd,	Offset,	Offset::_ExtraIntModesEnable,	endAdd>	ExtraIntModesEnable;
		WriteOnly<startAdd,	Offset,	Offset::_ExtraIntModesDisable,	endAdd>	ExtraIntModesDisable;
		ReadOnly<startAdd,	Offset,	Offset::_ExtraIntModesMask,		endAdd>	ExtraIntModesMask;
		
		WriteOnly<startAdd,	Offset,	Offset::_EdgeIntModeSelect,		endAdd>	EdgeIntModeSelect;
		WriteOnly<startAdd,	Offset,	Offset::_LevelIntModeSelect,	endAdd>	LevelIntModeSelect;
		ReadOnly<startAdd,	Offset,	Offset::_EdgeLevelStatus,		endAdd>	EdgeLevelStatus;
		
		WriteOnly<startAdd,	Offset,	Offset::_FallEdgeLowLevSelect,	endAdd>	FallEdgeLowLevSelect;
		WriteOnly<startAdd,	Offset,	Offset::_RiseEdgeHighLevSelect,	endAdd>	RiseEdgeHighLevSelect;
		ReadOnly<startAdd,	Offset,	Offset::_FallRiseHighLowStatus,	endAdd>	FallRiseHighLowStatus;

		ReadOnly<startAdd,	Offset,	Offset::_RegWriteLockStatus,	endAdd>	RegWriteLockStatus;
		ReadWrite<startAdd,	Offset,	Offset::_WriteProtectMode,		endAdd>	WriteProtectMode;
		ReadOnly<startAdd,	Offset,	Offset::_WriteProtectStatus,	endAdd>	WriteProtectStatus;
		
		public:
		enum class FilterMode : Reg::Reg_t { _No_Filter, _Glitch, _Debounce };
		enum class PullupMode : Reg::Reg_t { _Disable, _Enable };
		enum class OpenDrain : Reg::Reg_t { _Disable, _Enable };
		enum class OutputSync : Reg::Reg_t { _Disable, _Enable };
		enum class InterruptMode : Reg::Reg_t { _No_Interrupts, _Any_Edge, _Rising_Edge, _Falling_Edge, _High_Level, _Low_Level };
					
		static constexpr Reg::Reg_t regProtectKey = (0x50494F00u);
		void setInputFilter(const Reg::Reg_t mask, const FilterMode filterMode, const Reg::Reg_t inDiv = 0u){
			switch(filterMode){
				case FilterMode::_No_Filter:	InputFilterDisable.write(mask);									break;
				case FilterMode::_Glitch:		GlitchFilterSelect.write(mask);	InputFilterEnable.write(mask);	break;
				case FilterMode::_Debounce:		SlowClkDebounce.write(inDiv);   DebounceFilterSelect.write(mask);	InputFilterEnable.write(mask);	break;;
			}
		};
		void setInterruptMode(const Reg::Reg_t mask, const InterruptMode intMode){
			switch(intMode){
				case InterruptMode::_No_Interrupts:  ExtraIntModesDisable.write(mask);																			break;
				case InterruptMode::_Any_Edge:		 ExtraIntModesDisable.write(mask);																			break;
				case InterruptMode::_Rising_Edge:	 ExtraIntModesEnable.write(mask);	EdgeIntModeSelect.write(mask);	RiseEdgeHighLevSelect.write(mask);		break;
				case InterruptMode::_Falling_Edge:	 ExtraIntModesEnable.write(mask);   EdgeIntModeSelect.write(mask);	FallEdgeLowLevSelect.write(mask);		break;
				case InterruptMode::_High_Level:	 ExtraIntModesEnable.write(mask);   LevelIntModeSelect.write(mask);	RiseEdgeHighLevSelect.write(mask);		break;
				case InterruptMode::_Low_Level:		 ExtraIntModesEnable.write(mask);   LevelIntModeSelect.write(mask);	FallEdgeLowLevSelect.write(mask);		break;
			}
		};
		void setPullups(const Reg::Reg_t mask, const PullupMode pullupMode){
			(pullupMode == PullupMode::_Disable) ? (PullupDisable.write(mask)) : (PullupEnable.write(mask));
		};
		void setOpenDrain(const Reg::Reg_t mask, const OpenDrain openDrain){
			(openDrain == OpenDrain::_Disable) ? (MultiDriverDisable.write(mask)) : (MultiDriverEnable.write(mask));
		};
		void setOutputSync(const Reg::Reg_t mask, const OutputSync outputSync){
			(outputSync == OutputSync::_Disable) ? (OutputWriteDisable.write(mask)) : (OutputWriteEnable.write(mask));
		};
		void regProtectEnable(){	WriteProtectMode.write(regProtectKey | 1u);	};
		void regProtectDisable(){	WriteProtectMode.write(regProtectKey);		};
		void gpioEnable(const Reg::Reg_t mask){		GPIO_Enable.write(mask);	};	
		
		void setupInputs(const Reg::Reg_t mask, const FilterMode filterMode = FilterMode::_No_Filter, const InterruptMode  intMode = InterruptMode::_No_Interrupts,
							  const PullupMode pullupMode = PullupMode::_Enable, const OpenDrain openDrain = OpenDrain::_Disable, const Reg::Reg_t debounceFilterDiv = 0u){
			gpioEnable(mask);
			setInputFilter(mask, filterMode, debounceFilterDiv);
			setInterruptMode(mask, intMode);
			setPullups(mask, pullupMode);
			setOpenDrain(mask, openDrain);
			OutputDisable.write(mask);
		};
		
		void setupOutputs(const Reg::Reg_t mask, const InterruptMode intMode = InterruptMode::_No_Interrupts, const PullupMode pullupMode = PullupMode::_Disable,
							   const OpenDrain openDrain = OpenDrain::_Disable, const OutputSync outputSync = OutputSync::_Enable){
			gpioEnable(mask);
			setInterruptMode(mask, intMode);
			setPullups(mask, pullupMode);
			setOpenDrain(mask, openDrain);
			setOutputSync(mask, outputSync);
			OutputEnable.write(mask);
		};
		
		void setHigh(const Reg::Reg_t mask){
			SetOutputData.write(mask);
		};
		
		void setLow(const Reg::Reg_t mask){
			ClearOutputData.write(mask);
		};
		
		void init(){
			//Ipmc::enablePeriphClk(portID);
		};
	};
	
	enum class Dir : uint_fast8_t {
		_Input,
		_Output
	};
	
	template <Periph::ID portID>
	class DataBus : public IPort<portID>{
		private:
		Reg::Reg_t mask;
		uint_fast8_t shift;
		public:
			
		Reg::Reg_t read(){
			volatile const Reg::Reg_t val = IPort<portID>::PinDataStatus.read() & mask;
			return val;
		};
				
		void write(const Reg::Reg_t inVal){
			// this operation done in 2 steps to prevent other pins being overwritten.
			IPort<portID>::ClearOutputData.write(mask & ~(inVal << shift)); // set the low bits low
			IPort<portID>::SetOutputData.write(mask & (inVal << shift)); // set the high bits high
		};
				
		void setDir(const Dir inDir){
			(inDir == Dir::_Output) ? (IPort<portID>::OutputEnable.write(mask)) : (IPort<portID>::OutputDisable.write(mask));
		};
		
		// Note: only supports consecutive bits on the same port!
		DataBus(const Reg::Reg_t inMask, const Dir inDir = Dir::_Input) : mask(inMask) {
			//assert(inMask != 0, "0 value used to construct Port DataBus!");
			shift = 0;
			while(((inMask >> shift) & 0x1u) == 0){shift++;} // get first pin index right shift value
			IPort<portID>::gpioEnable(mask);
			IPort<portID>::setOutputSync(mask, IPort<portID>::OutputSync::_Disable); // disable output sync on these pins
			setDir(inDir);
		};
	};

	template <Periph::ID portID> // port template
	class Port : public IPort<portID>, protected Periph::IPeriph<portID>{
		static_assert(Periph::getType(portID) == Periph::Type::_GPIO_Port, 
		"GPIO Port Data template instantiation attempted on non-port type Periphial!");
		public:
		using Bus = DataBus<portID>;
	};

	// port A pin definitions
	// TODO: Create Pin template class and  
	static constexpr Reg::Reg_t PA0 = (1 << 0u);
	static constexpr Reg::Reg_t PA1 = (1 << 1u);
	static constexpr Reg::Reg_t PA2 = (1 << 2u);
	static constexpr Reg::Reg_t PA3 = (1 << 3u);
	static constexpr Reg::Reg_t PA4 = (1 << 4u);
	static constexpr Reg::Reg_t PA5 = (1 << 5u);
	static constexpr Reg::Reg_t PA6 = (1 << 6u);
	static constexpr Reg::Reg_t PA7 = (1 << 7u);
	static constexpr Reg::Reg_t PA8 = (1 << 8u);
	static constexpr Reg::Reg_t PA9 = (1 << 9u);
	static constexpr Reg::Reg_t PA10 = (1 << 10u);
	static constexpr Reg::Reg_t PA11 = (1 << 11u);
	static constexpr Reg::Reg_t PA12 = (1 << 12u);
	static constexpr Reg::Reg_t PA13 = (1 << 13u);
	static constexpr Reg::Reg_t PA14 = (1 << 14u);
	static constexpr Reg::Reg_t PA15 = (1 << 15u);
	static constexpr Reg::Reg_t PA16 = (1 << 16u);
	static constexpr Reg::Reg_t PA17 = (1 << 17u);
	static constexpr Reg::Reg_t PA18 = (1 << 18u);
	static constexpr Reg::Reg_t PA19 = (1 << 19u);
	static constexpr Reg::Reg_t PA20 = (1 << 20u);
	static constexpr Reg::Reg_t PA21 = (1 << 21u);
	static constexpr Reg::Reg_t PA22 = (1 << 22u);
	static constexpr Reg::Reg_t PA23 = (1 << 23u);
	static constexpr Reg::Reg_t PA24 = (1 << 24u);
	static constexpr Reg::Reg_t PA25 = (1 << 25u);
	static constexpr Reg::Reg_t PA26 = (1 << 26u);
	static constexpr Reg::Reg_t PA27 = (1 << 27u);
	static constexpr Reg::Reg_t PA28 = (1 << 28u);
	static constexpr Reg::Reg_t PA29 = (1 << 29u);

	// port B pin definitions
	static constexpr Reg::Reg_t PB0 = (1 << 0u);
	static constexpr Reg::Reg_t PB1 = (1 << 1u);
	static constexpr Reg::Reg_t PB2 = (1 << 2u);
	static constexpr Reg::Reg_t PB3 = (1 << 3u);
	static constexpr Reg::Reg_t PB4 = (1 << 4u);
	static constexpr Reg::Reg_t PB5 = (1 << 5u);
	static constexpr Reg::Reg_t PB6 = (1 << 6u);
	static constexpr Reg::Reg_t PB7 = (1 << 7u);
	static constexpr Reg::Reg_t PB8 = (1 << 8u);
	static constexpr Reg::Reg_t PB9 = (1 << 9u);
	static constexpr Reg::Reg_t PB10 = (1 << 10u);
	static constexpr Reg::Reg_t PB11 = (1 << 11u);
	static constexpr Reg::Reg_t PB12 = (1 << 12u);
	static constexpr Reg::Reg_t PB13 = (1 << 13u);
	static constexpr Reg::Reg_t PB14 = (1 << 14u);
	static constexpr Reg::Reg_t PB15 = (1 << 15u);
	static constexpr Reg::Reg_t PB16 = (1 << 16u);
	static constexpr Reg::Reg_t PB17 = (1 << 17u);
	static constexpr Reg::Reg_t PB18 = (1 << 18u);
	static constexpr Reg::Reg_t PB19 = (1 << 19u);
	static constexpr Reg::Reg_t PB20 = (1 << 20u);
	static constexpr Reg::Reg_t PB21 = (1 << 21u);
	static constexpr Reg::Reg_t PB22 = (1 << 22u);
	static constexpr Reg::Reg_t PB23 = (1 << 23u);
	static constexpr Reg::Reg_t PB24 = (1 << 24u);
	static constexpr Reg::Reg_t PB25 = (1 << 25u);
	static constexpr Reg::Reg_t PB26 = (1 << 26u);
	static constexpr Reg::Reg_t PB27 = (1 << 27u);
	static constexpr Reg::Reg_t PB28 = (1 << 28u);
	static constexpr Reg::Reg_t PB29 = (1 << 29u);
	static constexpr Reg::Reg_t PB30 = (1 << 30u);
	static constexpr Reg::Reg_t PB31 = (1 << 31u);

	// port C pin definitions
	static constexpr Reg::Reg_t PC0 = (1 << 0u);
	static constexpr Reg::Reg_t PC1 = (1 << 1u);
	static constexpr Reg::Reg_t PC2 = (1 << 2u);
	static constexpr Reg::Reg_t PC3 = (1 << 3u);
	static constexpr Reg::Reg_t PC4 = (1 << 4u);
	static constexpr Reg::Reg_t PC5 = (1 << 5u);
	static constexpr Reg::Reg_t PC6 = (1 << 6u);
	static constexpr Reg::Reg_t PC7 = (1 << 7u);
	static constexpr Reg::Reg_t PC8 = (1 << 8u);
	static constexpr Reg::Reg_t PC9 = (1 << 9u);
	static constexpr Reg::Reg_t PC10 = (1 << 10u);
	static constexpr Reg::Reg_t PC11 = (1 << 11u);
	static constexpr Reg::Reg_t PC12 = (1 << 12u);
	static constexpr Reg::Reg_t PC13 = (1 << 13u);
	static constexpr Reg::Reg_t PC14 = (1 << 14u);
	static constexpr Reg::Reg_t PC15 = (1 << 15u);
	static constexpr Reg::Reg_t PC16 = (1 << 16u);
	static constexpr Reg::Reg_t PC17 = (1 << 17u);
	static constexpr Reg::Reg_t PC18 = (1 << 18u);
	static constexpr Reg::Reg_t PC19 = (1 << 19u);
	static constexpr Reg::Reg_t PC20 = (1 << 20u);
	static constexpr Reg::Reg_t PC21 = (1 << 21u);
	static constexpr Reg::Reg_t PC22 = (1 << 22u);
	static constexpr Reg::Reg_t PC23 = (1 << 23u);
	static constexpr Reg::Reg_t PC24 = (1 << 24u);
	static constexpr Reg::Reg_t PC25 = (1 << 25u);
	static constexpr Reg::Reg_t PC26 = (1 << 26u);
	static constexpr Reg::Reg_t PC27 = (1 << 27u);
	static constexpr Reg::Reg_t PC28 = (1 << 28u);
	static constexpr Reg::Reg_t PC29 = (1 << 29u);
	static constexpr Reg::Reg_t PC30 = (1 << 30u);

	// port D pin definitions
	static constexpr Reg::Reg_t PD0 = (1 << 0u);
	static constexpr Reg::Reg_t PD1 = (1 << 1u);
	static constexpr Reg::Reg_t PD2 = (1 << 2u);
	static constexpr Reg::Reg_t PD3 = (1 << 3u);
	static constexpr Reg::Reg_t PD4 = (1 << 4u);
	static constexpr Reg::Reg_t PD5 = (1 << 5u);
	static constexpr Reg::Reg_t PD6 = (1 << 6u);
	static constexpr Reg::Reg_t PD7 = (1 << 7u);
	static constexpr Reg::Reg_t PD8 = (1 << 8u);
	static constexpr Reg::Reg_t PD9 = (1 << 9u);

	// port A->D definitions
	static Port<Periph::ID::_PIOA> PortA;
	static Port<Periph::ID::_PIOB> PortB;
	static Port<Periph::ID::_PIOC> PortC;
	static Port<Periph::ID::_PIOD> PortD;
	
}// end namespace GPIO
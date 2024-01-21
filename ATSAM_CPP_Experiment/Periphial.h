#pragma once

#include <cinttypes>
#include <type_traits>

namespace Periph {
	
	enum class ID : uint_fast8_t {
		_SUCP	=	0u,		_RSTC		=	1u,		_RTC	 =	2u,		_RTT	=	3u,		
		_WDG	=	4u,		_PMC		=   5u,		_EEFC0	 =  6u,		_EEFC1	=	7u,		
		_UART	=	8u,		_SMC_SDRAMC =	9u,		_SDRAMC  = 10u,		_PIOA	=  11u,
		_PIOB   =  12u,		_PIOC		=  13u,		_PIOD	 = 14u,		_PIOE   =  15u, 
		_PIOF	=  16u,		_USART0		=  17u,		_USART1  = 18u,		_USART2 =  19u,		
		_USART3	=  20u,		_HSMCI		=  21u,		_TWI0	 = 22u,		_TWI1   =  23u, 
		_SPI0	=  24u,		_SPI1		=  25u,		_SSC	 = 26u,		_TC0	=  27u,
		_TC1	=  28u,		_TC2		=  29u,		_TC3	 = 30u,		_TC4    =  31u,
		_TC5	=  32u,		_TC6		=  33u,		_TC7	 = 34u,		_TC8	=  35u,		
		_PWM	=  36u,		_ADC		=  37u,		_DACC	 = 38u,		_DMAC   =  39u,
		_UOTGHS =  40u,		_TRNG		=  41u,		_EMAC	 = 42u,		_CAN0	=  43u,		
		_CAN1	=  44u,		_UNDEFINED  = 255u	
	};
	// type must be ID of first instance of a Periphial.
	enum class Type : uint_fast8_t {
		_Supply_Control				= 0u,	_Reset_Control			= 1u,	_Real_Time_Clock			= 2u,
		_Real_Time_Timer			= 3u,	_Watch_Dog_Timer		= 4u,	_Power_Managment_Control	= 5u,
		_Embedded_Flash_Control		= 6u,	_UART					= 8u,	_Static_Mem_SDRAM_Control	= 9u,
		_SDRAM_Controller			= 10u,	_GPIO_Port				= 11u,	_USART						= 17u,
		_High_Speed_Memory_Card		= 21u,	_Two_Wire_Interface		= 22u,	_SPI						= 24u,
		_Sync_Serial_Control		= 26u,	_Timer_Counter			= 27u,	_PWM						= 36u,
		_ADC						= 37u,	_DAC_Control			= 38u,	_Direct_Mem_Access_Control	= 39u,
		_USB_On_The_Go_High_Speed	= 40u,	_True_Random_Number_Gen	= 41u,	_Ethernet_MAC				= 42u,		
		_CAN_Bus					= 43u,	_UNDEFINED              = 255u
	};
	enum class Instance : uint_fast8_t {
		_Instance_A_0	= 0u,			_Instance_B_1	= 1u,			_Instance_C_2	= 2u,			_Instance_D_3	= 3u,
		_Instance_E_4	= 4u,			_Instance_F_5	= 5u,			_Instance_G_6	= 6u,			_Instance_H_7	= 7u,
		_Instance_I_8	= 8u,			_Instance_J_9	= 9u,			_Instance_K_10	= 10u,			_Instance_L_11	= 11u,
		_UNDEFINED		= 255u
	};
	
	static constexpr Type getType(const ID inID){
		switch(inID){
			case ID::_SUCP:			return		Type::_Supply_Control;				break;
			case ID::_RSTC:			return		Type::_Reset_Control;				break;
			case ID::_RTC:			return		Type::_Real_Time_Clock;				break;
			case ID::_RTT:			return		Type::_Real_Time_Timer;				break;
			case ID::_WDG:			return		Type::_Watch_Dog_Timer;				break;
			case ID::_PMC:			return		Type::_Power_Managment_Control;		break;
			case ID::_EEFC0:													;//[[fallthrough]];
			case ID::_EEFC1:		return		Type::_Embedded_Flash_Control;		break;
			case ID::_UART:			return		Type::_UART;						break;
			case ID::_SMC_SDRAMC:	return		Type::_Static_Mem_SDRAM_Control;	break;
			case ID::_SDRAMC:		return		Type::_Real_Time_Clock;				break;
			case ID::_PIOA:														;//[[fallthrough]];
			case ID::_PIOB:														;//[[fallthrough]];
			case ID::_PIOC:														;//[[fallthrough]];
			case ID::_PIOD:														;//[[fallthrough]];
			case ID::_PIOE:														;//[[fallthrough]];
			case ID::_PIOF:			return		Type::_GPIO_Port;					break;
			case ID::_USART0:													;//[[fallthrough]];
			case ID::_USART1:													;//[[fallthrough]];
			case ID::_USART2:													;//[[fallthrough]];
			case ID::_USART3:		return		Type::_USART;						break;
			case ID::_HSMCI:		return		Type::_High_Speed_Memory_Card;		break;
			case ID::_TWI0:														;//[[fallthrough]];
			case ID::_TWI1:			return		Type::_Two_Wire_Interface;			break;
			case ID::_SPI0:														;//[[fallthrough]];
			case ID::_SPI1:			return		Type::_SPI;							break;
			case ID::_SSC:			return		Type::_Sync_Serial_Control;			break;
			case ID::_TC0:														;//[[fallthrough]];
			case ID::_TC1:														;//[[fallthrough]];
			case ID::_TC2:														;//[[fallthrough]];
			case ID::_TC3:														;//[[fallthrough]];
			case ID::_TC4:														;//[[fallthrough]];
			case ID::_TC5:														;//[[fallthrough]];
			case ID::_TC6:														;//[[fallthrough]];
			case ID::_TC7:														;//[[fallthrough]];
			case ID::_TC8:			return		Type::_Timer_Counter;				break;
			case ID::_PWM:			return		Type::_PWM;							break;
			case ID::_ADC:			return		Type::_ADC;							break;
			case ID::_DACC:			return		Type::_DAC_Control;					break;
			case ID::_DMAC:			return		Type::_Direct_Mem_Access_Control;	break;
			case ID::_UOTGHS:		return		Type::_USB_On_The_Go_High_Speed;	break;
			case ID::_TRNG:			return		Type::_True_Random_Number_Gen;		break;
			case ID::_EMAC:			return		Type::_Ethernet_MAC;				break;
			case ID::_CAN0:														;//[[fallthrough]];
			case ID::_CAN1:			return		Type::_CAN_Bus;						break;
			case ID::_UNDEFINED:	return		Type::_UNDEFINED;					break;
			default:				return		Type::_UNDEFINED;					break;
		}
	}
	
	template <ID inID>
	class IPeriph {
		protected:
			static constexpr ID periphID = inID;
			static constexpr Type periphType = getType(inID);
			static constexpr Instance periphInstance = static_cast<Instance>(static_cast<uint_fast8_t>(periphID) - static_cast<uint_fast8_t>(periphType));
	};

}
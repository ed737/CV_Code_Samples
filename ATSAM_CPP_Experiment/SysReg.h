#pragma once

#include <type_traits>
#include <limits>
#include <cinttypes>
#include <cstdint>
#include "Periphial.h"

namespace Reg {

	using Reg_t		=	uintptr_t;	// register base type aliases
	using Offset_t	=	uintptr_t;
	
	static_assert(std::is_unsigned<Reg_t>::value && std::is_unsigned<Offset_t>::value,			
	"Signed type used as register type argument in UTestReg class!");
	
	static_assert(std::numeric_limits<Offset_t>::digits <= std::numeric_limits<Reg_t>::digits,	
	"Offset_t type has higher bit count than Reg_t in UTestReg class!");

	//static_assert(std::alignment_of<Registers<Periph::Type::_GPIO_Port>>::value == 1, "Alignment of GPIO Registers class must be 1 bytes!");
	class Master {
		
		private:
			using RO_ptr = volatile const Reg_t* const; // register pointer type aliases
			using WO_ptr = volatile Reg_t* const;
			using RW_ptr = volatile Reg_t* const;
			
		protected:
			enum class MemoryMap : Reg_t {	// this enum defines all system and peripherals start register addresses
				_BOOT_MEM	=	0x00000000u,
				_INT_FLASH	=	0x00080000u,
				_INT_ROM	=	0x00100000u,

				_END_CODE	=	0x00200000u,
				_ST_SRAM	=	0x1FFFFFFFu,

				_SRAM0		=	0x20000000u,
				_SRAM1		=	0x20080000u,
				_NFC_SRAM	=	0x20100000u,
				_UOTGHS_DMA	=	0x20180000u,

				_END_SRAM	=	0x20200001u,
				_ST_PRPH	=	0x3FFFFFFFu,

				_HSMCI		=	0x40000000u,	// High speed Multimedia Card Interface
				_SSC		=	0x40004000u,	// Sync serial controller
				_SPI0		=	0x40008000u,	// Serial Periphial Interface 0
				_SPI1		=	0x4000C000u,	// Serial Periphial Interface 1
				_TC0		=	0x40080000u,	// Timer counter 0
				_TC1		=	0x40080040u,	// Timer counter 1
				_TC2		=	0x40080080u,	// Timer counter 2
				_TC3		=	0x40084000u,	// Timer counter 3
				_TC4		=	0x40084040u,	// Timer counter 4
				_TC5		=	0x40084080u,	// Timer counter 5
				_TC6		=	0x40088000u,	// Timer counter 6
				_TC7		=	0x40088040u,	// Timer counter 7
				_TC8		=	0x40088080u,	// Timer counter 8
				_TWI0		=	0x4008C000u, 	// Two Wire Interface 0
				_TWI1		=	0x40090000u, 	// Two Wire Interface 1
				_PWM		=	0x40094000u,	// PWM controller
				_USART0		=	0x40098000u,	// Sync receiver transceiver 0
				_USART1		=	0x4009C000u,	// Sync receiver transceiver 1
				_USART2		=	0x400A0000u,	// Sync receiver transceiver 2
				_USART3		=	0x400A4000u,	// Sync receiver transceiver 3
				_UOTGHS		=	0x400AC000u,	// USB on the go
				_EMAC		=	0x400B0000u,	// Ethernet MAC
				_CAN0		=	0x400B4000u,	// CAN bus 0
				_CAN1		=	0x400B8000u,	// CAN bus 1
				_TRNG		=	0x400BC000u,	// true random number generator
				_ADC		=	0x400C0000u,	// Analog to digital converter
				_DMAC		=	0x400C4000u,	// Direct mem access controller
				_DACC		=	0x400C8000u,	// Digital to analog converter controller

				_END_PRPH	=	0x400D0000u,
				_ST_SYSCTR	=	0x400DFFFFu,

				_SMC		=	0x400E0000u,	// Static Memory Controller
				_SDRAMC		=	0x400E0200u,	// SDRAM Controller
				_MATRIX		=	0x400E0400u,	// bus matrix
				_PMC		=	0x400E0600u,	// Power Management Controller
				_UART		=	0x400E0800u,	// Async receiver transceiver
				_CHIPID		=	0x400E0940u,	// chip ID register
				_EEFC0		=	0x400E0A00u,	// Embedded Flash Controller 0
				_EEFC1		=	0x400E0C00u,	// Embedded Flash Controller 1
				_PIOA		=	0x400E0E00u,	// GPIO port A
				_PIOB		=	0x400E1000u,	// GPIO port B
				_PIOC		=	0x400E1200u,	// GPIO port C
				_PIOD		=	0x400E1400u,	// GPIO port D
				_PIOE		=	0x400E1600u,	// GPIO port E
				_PIOF		=	0x400E1800u,	// GPIO port F
				_RSTC		=	0x400E1A00u,	// Reset Controller
				_SUPC		=	0x400E1A10u,	// Supply Controller
				_RTT		=	0x400E1A30u,	// Real Time Timer
				_WDG		=	0x400E1A50u,	// Watchdog Timer
				_RTC		=	0x400E1A60u,	// Real Time Clock
				_GPBR		=	0x400E1A90u,	// General Purpose backup

				_END_SYSCTR	=	0x400E1AB0u,
				_ST_EX_SRAM	=	0x5FFFFFFFu,

				_CS0		=	0x60000000u,	// sram chip select 0
				_CS1		=	0x61000000u,	// sram chip select 1
				_CS2		=	0x62000000u,	// sram chip select 2
				_CS3		=	0x63000000u,	// sram chip select 3
				_CS4		=	0x64000000u,	// sram chip select 4
				_CS5		=	0x65000000u,	// sram chip select 5
				_CS6		=	0x66000000u,	// sram chip select 6
				_CS7		=	0x67000000u,	// sram chip select 7
				_NFC		=	0x68000000u,
				_CSSDRAMC	=	0x70000000u,
			
				_END_EX_SRAM =  0x80000000u,
				_UNDEFINED	 =  UINTPTR_MAX,
				
			}; // end Memory map.
			/*
			template <class In, class Out>
			static constexpr Out ECToEC(const In in ){
				static_assert(std::is_enum<In>::value && std::is_enum<Out>::value,			
				"Non-enum class type used as template argument to EClassToEClass method!");
			}*/
			
			static constexpr MemoryMap getStartAddress(const Periph::ID& inID){
				switch(inID){
					case Periph::ID::_SUCP:			return	MemoryMap::_SUPC;		break;
					case Periph::ID::_RSTC:			return	MemoryMap::_RSTC;		break;
					case Periph::ID::_RTC:			return	MemoryMap::_RTC;		break;
					case Periph::ID::_RTT:			return	MemoryMap::_RTT;		break;
					case Periph::ID::_WDG:			return	MemoryMap::_WDG;		break;
					case Periph::ID::_PMC:			return	MemoryMap::_PMC;		break;
					case Periph::ID::_EEFC0:		return	MemoryMap::_EEFC0;		break;
					case Periph::ID::_EEFC1:		return	MemoryMap::_EEFC1;		break;
					case Periph::ID::_UART:			return	MemoryMap::_UART;		break;
					case Periph::ID::_SMC_SDRAMC:	return	MemoryMap::_SMC;		break;
					case Periph::ID::_SDRAMC:		return	MemoryMap::_SDRAMC;		break;
					case Periph::ID::_PIOA:			return	MemoryMap::_PIOA;		break;
					case Periph::ID::_PIOB:			return	MemoryMap::_PIOB;		break;
					case Periph::ID::_PIOC:			return	MemoryMap::_PIOC;		break;
					case Periph::ID::_PIOD:			return	MemoryMap::_PIOD;		break;
					case Periph::ID::_PIOE:			return	MemoryMap::_PIOE;		break;
					case Periph::ID::_PIOF:			return	MemoryMap::_PIOF;		break;
					case Periph::ID::_USART0:		return	MemoryMap::_USART0;		break;
					case Periph::ID::_USART1:		return	MemoryMap::_USART1;		break;
					case Periph::ID::_USART2:		return	MemoryMap::_USART2;		break;
					case Periph::ID::_USART3:		return	MemoryMap::_USART3;		break;
					case Periph::ID::_HSMCI:		return	MemoryMap::_HSMCI;		break;
					case Periph::ID::_TWI0:			return	MemoryMap::_TWI0;		break;
					case Periph::ID::_TWI1:			return	MemoryMap::_TWI1;		break;
					case Periph::ID::_SPI0:			return	MemoryMap::_SPI0;		break;
					case Periph::ID::_SPI1:			return	MemoryMap::_SPI1;		break;
					case Periph::ID::_SSC:			return	MemoryMap::_SSC;		break;
					case Periph::ID::_TC0:			return	MemoryMap::_TC0;		break;
					case Periph::ID::_TC1:			return	MemoryMap::_TC1;		break;
					case Periph::ID::_TC2:			return	MemoryMap::_TC2;		break;
					case Periph::ID::_TC3:			return	MemoryMap::_TC3;		break;
					case Periph::ID::_TC4:			return	MemoryMap::_TC4;		break;
					case Periph::ID::_TC5:			return	MemoryMap::_TC5;		break;
					case Periph::ID::_TC6:			return	MemoryMap::_TC6;		break;
					case Periph::ID::_TC7:			return	MemoryMap::_TC7;		break;
					case Periph::ID::_TC8:			return	MemoryMap::_TC8;		break;
					case Periph::ID::_PWM:			return	MemoryMap::_PWM;		break;
					case Periph::ID::_ADC:			return	MemoryMap::_ADC;		break;
					case Periph::ID::_DACC:			return	MemoryMap::_DACC;		break;
					case Periph::ID::_DMAC:			return	MemoryMap::_DMAC;		break;
					case Periph::ID::_UOTGHS:		return	MemoryMap::_UOTGHS;		break;
					case Periph::ID::_TRNG:			return	MemoryMap::_TRNG;		break;
					case Periph::ID::_EMAC:			return	MemoryMap::_EMAC;		break;
					case Periph::ID::_CAN0:			return	MemoryMap::_CAN0;		break;
					case Periph::ID::_CAN1:			return	MemoryMap::_CAN1;		break;
					case Periph::ID::_UNDEFINED:	return	MemoryMap::_UNDEFINED;	break;
					default:						return	MemoryMap::_UNDEFINED;  break;
				}
			};
		
			// register access template definitions
			template <Reg_t startAddress, class OffsetEnum, OffsetEnum inOffset, Reg_t endAddress>
			class ReadWrite{
				public:
				static_assert(startAddress <= (startAddress + static_cast<Reg_t>(inOffset)) && 
							 (startAddress + static_cast<Reg_t>(inOffset)) <= endAddress, "ReadWrite register Address out of bounds!");		
				RW_ptr address_ptr = reinterpret_cast<RW_ptr>(startAddress + static_cast<Reg_t>(inOffset));
				inline Reg_t read(){ return *address_ptr; };
				inline void write(const Reg_t inData){ *(address_ptr) |= inData; };
			};
				
			template <Reg_t startAddress, class OffsetEnum, OffsetEnum inOffset, Reg_t endAddress>
			class WriteOnly {
				public:
				static_assert(startAddress <= (startAddress + static_cast<Reg_t>(inOffset)) &&
							 (startAddress + static_cast<Reg_t>(inOffset)) <= endAddress, "WriteOnly register Address out of bounds!");
				WO_ptr address_ptr = reinterpret_cast<WO_ptr>(startAddress + static_cast<Reg_t>(inOffset));
				inline void write(const Reg_t inData){ *(address_ptr) |= inData; };
			};
				
			template <Reg_t startAddress, class OffsetEnum, OffsetEnum inOffset, Reg_t endAddress>
			class ReadOnly {
				public:
				static_assert(startAddress <= (startAddress + static_cast<Reg_t>(inOffset)) &&
				(startAddress + static_cast<Reg_t>(inOffset)) <= endAddress, "ReadOnly register Address out of bounds!");
				RO_ptr address_ptr = reinterpret_cast<RO_ptr>(startAddress + static_cast<Reg_t>(inOffset));
				inline Reg_t read(){ return *address_ptr; };
			};
			
	}; // end class Master
} // end namespace Reg

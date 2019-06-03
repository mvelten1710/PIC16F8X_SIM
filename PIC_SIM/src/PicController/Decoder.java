package PicController;

import static PicController.Controller.*;

public class Decoder
{
	// TODO CLEAR ALL SYSTEM.OUT.PRINTLN!

	private int byteOrientedMask = 0b11111100000000;

	private int adressMask = 0b00000011111111;

	private int bitMask = 0b00001110000000;

	private int bitOrientedMask = 0b11110000000000;

	private int instruction;

	public Decoder()
	{
		instruction = 0;
	}

	public void decode(int instruc)
	{
		setInstruction(instruc);
		int instructionPart = 0;
		int dataPart = 0;
		int adressPart = 0;
		int destinationBit = 0;
		int bitPos = 0;

		// System.out.println("INSTRUCTION: " +
		// Integer.toBinaryString(instruction));

		switch ((instruc >> 12) & 0b11) {
		case 0:
			// Special Cases for some L&C Operations
			switch (instruc) {
			case 0b00000001100100:
				// CLRWDT
				instructionPart = 0b1100100;
				break;

			case 0b00000000001001:
				// RETFIE
				instructionPart = 0b1001;
				break;

			case 0b00000000001000:
				// RETURN
				instructionPart = 0b1000;
				break;

			case 0b00000001100011:
				// SLEEP
				instructionPart = 0b1100011;
				break;
			}

			if (instructionPart == 0) {
				// If it is true then its a byte oriented operations
				instructionPart = instruction & byteOrientedMask;
				dataPart = instruction & adressMask;
				adressPart = dataPart & 0b00000001111111;
				destinationBit = dataPart >> 7;
				if (destinationBit == 1 && instructionPart == 0) {
					// MOVWF
					instructionPart = 0b10000000;
				}
			}
			break;

		case 1:
			// If it is true then its a bit oriented operations
			instructionPart = instruction & bitOrientedMask;
			dataPart = instruction & adressMask;
			adressPart = dataPart & 0b00000001111111;
			bitPos = (instruction & bitMask) >> 7;
			break;

		case 2:
			// If it is true then its a l & c oriented operations
			instructionPart = instruction & 0b11100000000000;
			dataPart = instruction & 0b00011111111111;
			break;

		case 3:
			// If it is true then its a l & c oriented operations
			// Has the same masks like the byte operations
			instructionPart = instruction & byteOrientedMask;
			dataPart = instruction & adressMask;
			break;
		}

		// Search for Instruction
		switch (instructionPart) {
		case 0x0700:
			System.out.println("###ADDWF###");
			addwf(adressPart, destinationBit);
			break;
		case 0x0500:
			System.out.println("###ANDWF###");
			andwf(adressPart, destinationBit);
			break;
		// case 0x0180:
		// // Destination Bit is 1
		// System.out.println("CLRF");
		// clrf(adressPart);
		// break;
		case 0x0100:
			// Destination Bit is 0
			decideCLR(adressPart, destinationBit);
			break;
		case 0x0900:
			System.out.println("###COMF###");
			comf(adressPart, destinationBit);
			break;
		case 0x0300:
			System.out.println("###DECF###");
			decf(adressPart, destinationBit);
			break;
		case 0x0B00:
			System.out.println("###DECFSZ###");
			decfsz(adressPart, destinationBit);
			break;
		case 0x0A00:
			System.out.println("###INCF###");
			incf(adressPart, destinationBit);
			break;
		case 0x0F00:
			System.out.println("###INCFSZ###");
			incfsz(adressPart, destinationBit);
			break;
		case 0x0400:
			System.out.println("###IORWF###");
			iorwf(adressPart, destinationBit);
			break;
		case 0x0800:
			System.out.println("###MOVF###");
			movf(adressPart, destinationBit);
			break;
		case 0x0080:
			// Destination Bit is 1
			System.out.println("###MOVWF###");
			movwf(adressPart);
			break;
		case 0x0000:
			// Destination Bit is 0
			System.out.println("###NOP###");
			nop();
			break;
		case 0x0D00:
			System.out.println("###RLF###");
			rlf(adressPart, destinationBit);
			break;
		case 0x0C00:
			System.out.println("###RRF###");
			rrf(adressPart, destinationBit);
			break;
		case 0x0200:
			System.out.println("###SUBWF###");
			subwf(adressPart, destinationBit);
			break;
		case 0x0E00:
			System.out.println("###SWAPF###");
			swapf(adressPart, destinationBit);
			break;
		case 0x0600:
			System.out.println("###XORWF###");
			xorwf(adressPart, destinationBit);
			break;

		case 0x1000:
			System.out.println("###BCF###");
			bcf(adressPart, bitPos);
			break;
		case 0x1100:
			System.out.println("###BCF###");
			bcf(adressPart, bitPos);
			break;
		case 0x1200:
			System.out.println("###BCF###");
			bcf(adressPart, bitPos);
			break;
		case 0x1300:
			System.out.println("###BCF###");
			bcf(adressPart, bitPos);
			break;
		case 0x1400:
			System.out.println("###BSF###");
			bsf(adressPart, bitPos);
			break;
		case 0x1500:
			System.out.println("###BSF###");
			bsf(adressPart, bitPos);
			break;
		case 0x1600:
			System.out.println("###BSF###");
			bsf(adressPart, bitPos);
			break;
		case 0x1700:
			System.out.println("###BSF###");
			bsf(adressPart, bitPos);
			break;
		case 0x1800:
			System.out.println("###BTFSC###");
			btfsc(adressPart, bitPos);
			break;
		case 0x1900:
			System.out.println("###BTFSC###");
			btfsc(adressPart, bitPos);
			break;
		case 0x1A00:
			System.out.println("###BTFSC###");
			btfsc(adressPart, bitPos);
			break;
		case 0x1B00:
			System.out.println("###BTFSC###");
			btfsc(adressPart, bitPos);
			break;
		case 0x1C00:
			System.out.println("###BTFSS###");
			btfss(adressPart, bitPos);
			break;
		case 0x1D00:
			System.out.println("###BTFSS###");
			btfss(adressPart, bitPos);
			break;
		case 0x1E00:
			System.out.println("###BTFSS###");
			btfss(adressPart, bitPos);
			break;
		case 0x1F00:
			System.out.println("###BTFSS###");
			btfss(adressPart, bitPos);
			break;

		case 0x3E00:
			System.out.println("###ADDLW###");
			addlw(dataPart);
			break;
		case 0x3900:
			System.out.println("###ANDLW###");
			andlw(dataPart);
			break;
		case 0x2000:
			System.out.println("###CALL###");
			call(dataPart);
			break;
		case 0x0064:
			// Other Bitmask
			System.out.println("###CLRWDT###");
			clrwdt();
			break;
		case 0x2800:
			System.out.println("###GOTO###");
			_goto(dataPart);
			break;
		case 0x3800:
			System.out.println("###IORLW###");
			iorlw(dataPart);
			break;
		case 0x3000:
			System.out.println("###MOVLW###");
			movlw(dataPart);
			break;
		case 0x0009:
			System.out.println("###RETFIE###");
			retfie();
			break;
		case 0x3400:
			System.out.println("###RETLW###");
			retlw(dataPart);
			break;
		case 0x0008:
			System.out.println("###RETURN###");
			_return();
			break;
		case 0x0063:
			System.out.println("###SLEEP###");
			sleep();
			break;
		case 0x3C00:
			System.out.println("###SUBLW###");
			sublw(dataPart);
			break;
		case 0x3A00:
			System.out.println("###XORLW###");
			xorlw(dataPart);
			break;
		default:
			System.out.println("###NOT AN OPERATION###");
			break;
		}
	}

	/* ####################START-OF-OPERATIONS#################### */

	public void addwf(int adress, int desti)
	{
		// Adds W with f = data
		if (desti == 0) {
			int helper = W >> 4;
			W = W + f[adress];
			// Set C-Flag
			setFlags(CFLAG, W);
			cutWandF(adress, desti);
			if (helper == 0 && (W >> 4) != 0) {
				// Set DC-Flag
				setFlags(1, W);
			}
			// Set Z-Flag
			setFlags(2, W);
		} else {
			int helper = f[adress] >> 4;
			f[adress] = W + f[adress];
			// Set C-Flag
			setFlags(CFLAG, f[adress]);
			cutWandF(adress, desti);

			if (helper == 0 && (f[adress] >> 4) != 0) {
				// Set DC-Flag
				setFlags(1, f[adress]);
			}
			// Set Z-Flag
			setFlags(2, f[adress]);
		}
		incrementRuntime(1);
		incrementpIndex();
	}

	public void andwf(int adress, int desti)
	{
		// And W with f = data
		if (desti == 0) {
			W = W & f[adress];
			cutWandF(adress, desti);
			// Set Z-Flag
			setFlags(2, W);
		} else {
			f[adress] = W & f[adress];
			cutWandF(adress, desti);
			// Set Z-Flag
			setFlags(2, f[adress]);
		}
		incrementRuntime(1);
		incrementpIndex();
	}

	public void clrf(int adress)
	{
		System.out.println("CLRF");
		f[adress] = 0;
		// Set Z-Flag
		setFlags(2, f[adress]);
		incrementRuntime(1);
		incrementpIndex();
	}

	public void clrw()
	{
		System.out.println("CLRW");
		W = 0;
		// Set Z-Flag
		setFlags(2, W);
		incrementRuntime(1);
		incrementpIndex();
	}

	public void comf(int adress, int desti)
	{
		if (desti == 0) {
			W = ~f[adress];
			cutWandF(adress, desti);
			// Set Z-Flag
			setFlags(2, W);
		} else {
			f[adress] = ~f[adress];
			cutWandF(adress, desti);
			// Set Z-Flag
			setFlags(2, f[adress]);
		}
		incrementRuntime(1);
		incrementpIndex();
	}

	public void decf(int adress, int desti)
	{
		if (desti == 0) {
			W = f[adress] - 1;
			cutWandF(adress, desti);
			// Set Z-Flag
			setFlags(2, W);
		} else {
			--f[adress];
			cutWandF(adress, desti);
			// Set Z-Flag
			setFlags(2, f[adress]);
		}
		incrementRuntime(1);
		incrementpIndex();
	}

	public void decfsz(int adress, int desti)
	{
		if (desti == 0) {
			W = --f[adress];
			cutWandF(adress, desti);
			if (W == 0) {
				nop();
			}
		} else {
			--f[adress];
			cutWandF(adress, desti);
			if (f[adress] == 0) {
				nop();
			}
		}
		incrementRuntime(1);
		incrementpIndex();
	}

	public void incf(int adress, int desti)
	{
		if (desti == 0) {
			W = ++f[adress];
			cutWandF(adress, desti);
			// Set Z-Flag
			setFlags(2, W);
		} else {
			++f[adress];
			cutWandF(adress, desti);
			// Set Z-Flag
			setFlags(2, f[adress]);
		}
		incrementRuntime(1);
		incrementpIndex();
	}

	public void incfsz(int adress, int desti)
	{
		if (desti == 0) {
			W = ++f[adress];
			cutWandF(adress, desti);
		} else {
			++f[adress];
			cutWandF(adress, desti);
		}
		if (f[adress] == 0) {
			nop();
		}
		incrementRuntime(1);
		incrementpIndex();
	}

	public void iorwf(int adress, int desti)
	{
		if (desti == 0) {
			W = W | f[adress];
			cutWandF(adress, desti);
			// Set Z-Flag
			setFlags(2, W);
		} else {
			f[adress] = W | f[adress];
			cutWandF(adress, desti);
			// Set Z-Flag
			setFlags(2, f[adress]);
		}
		incrementRuntime(1);
		incrementpIndex();
	}

	public void movf(int adress, int desti)
	{
		if (desti == 0) {
			W = f[adress];
			cutWandF(adress, desti);
			// Set Z-Flag
			setFlags(2, W);
		} else {
			// Set Z-Flag
			setFlags(2, f[adress]);
		}
		incrementRuntime(1);
		incrementpIndex();
	}

	public void movwf(int adress)
	{
		f[adress] = W;
		cutWandF(adress, 1);
		incrementRuntime(1);
		incrementpIndex();
	}

	public void rlf(int adress, int desti)
	{
		// helper gets the first bit that goes later to C
		int helper = 0;
		if (desti == 0) {
			helper = W;
			W = (f[adress] << 1);
			cutWandF(adress, desti);
		} else {
			helper = f[adress];
			f[adress] = (f[adress] << 1);
			cutWandF(adress, desti);
		}
		setFlags(CFLAG, helper);
		incrementRuntime(1);
		incrementpIndex();
	}

	public void rrf(int adress, int desti)
	{
		int helper = 0;
		helper = helper >> 7;
		if (desti == 0) {
			helper = W;
			W = (f[adress] >> 1);
			cutWandF(adress, desti);
		} else {
			helper = f[adress];
			f[adress] = (f[adress] >> 1);
			cutWandF(adress, desti);
		}
		setFlags(-2, helper);
		incrementRuntime(1);
		incrementpIndex();
	}

	public void subwf(int adress, int desti)
	{
		if (desti == 0) {
			int helper = W >> 4;
			W = f[adress] + _2complement();
			cutWandF(adress, desti);
			// Set C-Flag
			setFlags(CFLAG, W);
			if (helper == 0 && (W >> 4) != 0) {
				// Set DC-Flag
				setFlags(1, W);
			}
			// Set Z-Flag
			setFlags(2, W);
		} else {
			int helper = f[adress] >> 4;
			f[adress] = f[adress] + _2complement();
			cutWandF(adress, desti);
			// Set C-Flag
			setFlags(CFLAG, f[adress]);
			if (helper == 0 && (f[adress] >> 4) != 0) {
				// Set DC-Flag
				setFlags(1, f[adress]);
			}
			// Set Z-Flag
			setFlags(2, f[adress]);
		}
		incrementRuntime(1);
		incrementpIndex();
	}

	public void swapf(int adress, int desti)
	{
		int upperNibble = f[adress] >> 4;
		int lowerNibble = f[adress] << 4;
		int total = lowerNibble | upperNibble;
		if (desti == 0) {
			W = total;
			cutWandF(adress, desti);
		} else {
			f[adress] = total;
			cutWandF(adress, desti);
		}
		incrementRuntime(1);
		incrementpIndex();
	}

	public void xorwf(int adress, int desti)
	{
		if (desti == 0) {
			W = W ^ f[adress];
			cutWandF(adress, desti);
			// Set Z-Flag
			setFlags(2, W);
		} else {
			f[adress] = W ^ f[adress];
			cutWandF(adress, desti);
			// Set Z-Flag
			setFlags(2, f[adress]);
		}
		incrementRuntime(1);
		incrementpIndex();
	}

	public void bcf(int adress, int b)
	{
		f[adress] = f[adress] & ~(1 << b);
		cutWandF(adress, 1);
		incrementRuntime(1);
		incrementpIndex();
	}

	public void bsf(int adress, int b)
	{
		f[adress] = f[adress] | ~(1 << b);
		cutWandF(adress, 1);
		incrementRuntime(1);
		incrementpIndex();
	}

	public void btfsc(int adress, int b)
	{
		if ((f[adress] & (1 << b)) == 0) {
			nop();
		}
		incrementRuntime(1);
		incrementpIndex();
	}

	public void btfss(int adress, int b)
	{
		if ((f[adress] & (1 << b)) == 1) {
			nop();
		}
		incrementRuntime(1);
		incrementpIndex();
	}

	public void addlw(int data)
	{
		int helper = W >> 4;
		W = W + data;
		// Set C-Flag
		setFlags(CFLAG, W);
		cutWandF(0, 0);
		// Set DC-Flag
		if (helper == 0 && (W >> 4) != 0) {
			setFlags(1, W);
		}
		// Set Z-Flag
		setFlags(2, W);
		incrementRuntime(1);
		incrementpIndex();
	}

	public void andlw(int data)
	{
		W = W & data;
		cutWandF(0, 0);
		// Set Z-Flag
		setFlags(2, W);
		incrementRuntime(1);
		incrementpIndex();
	}

	public void call(int data)
	{
		pushStack(++pIndex);
		pIndex = data;
		incrementRuntime(2);
	}

	public void clrwdt()
	{
		// TODO FINISH WATCHDOG FOR THIS OPERATION
		// TO = 0;
		// PD = 0;
		incrementRuntime(1);
	}

	public void _goto(int data)
	{
		pIndex = data;
		incrementRuntime(2);
	}

	public void iorlw(int data)
	{
		W = W | data;
		cutWandF(0, 0);
		// Set Z-Flag
		setFlags(2, W);
		incrementRuntime(1);
		incrementpIndex();
	}

	public void movlw(int data)
	{
		W = data;
		incrementRuntime(1);
		incrementpIndex();
	}

	public void retfie()
	{
		// TODO FINISH
		incrementRuntime(2);

	}

	public void retlw(int data)
	{
		W = data;
		pIndex = popStack();
		incrementRuntime(2);
	}

	public void _return()
	{
		pIndex = popStack();
		incrementRuntime(2);
	}

	public void sleep()
	{
		// TODO FINISH MAYBE?
		// PD = 1;
		// TO = 0;
		incrementRuntime(1);
		incrementpIndex();
	}

	public void sublw(int data)
	{
		W = data + _2complement();
		cutWandF(0, 0);
		// Set C-Flag
		setFlags(-1, W);
		// Set Z-Flag
		setFlags(2, W);
		incrementRuntime(1);
		incrementpIndex();
	}

	public void xorlw(int data)
	{
		W = W ^ data;
		cutWandF(0, 0);
		// Set Z-Flag
		setFlags(2, W);
		incrementRuntime(1);
		incrementpIndex();
	}

	public void nop()
	{
		incrementpIndex();
		incrementRuntime(1);
		return;
	}

	/* ####################END-OF-OPERATIONS#################### */

	/* ####################START-OF-FUNCTIONS#################### */

	private void setInstruction(int instruc)
	{
		this.instruction = instruc;
	}

	private int _2complement()
	{
		int helper = W;
		helper = ~helper;
		helper++;
		return helper;
	}

	private void decideCLR(int adress, int desti)
	{
		if (desti == 0) {
			clrw();
		} else {
			clrf(adress);
		}
	}

	private void incrementpIndex()
	{
		pIndex++;
	}
	
	private void incrementRuntime(int value) 
	{
		runtime += value;
	}

	private void setFlags(int flagSec, int selector)
	{

		switch (flagSec) {
		// C-Flag (RRF)
		case -2:
			if ((selector & 1) != 0) {
				dataMemory[STATUS] |= 0b00000001;
			} else {
				dataMemory[STATUS] &= ~0b00000001;
			}
			break;
		// C & DC -Flag (SUBLW)
		case -1:
			if (selector >= 0) {
				dataMemory[STATUS] |= 0b00000001; // C-Flag
				dataMemory[STATUS] |= 0b00000010; // DC-Flag
			} else {
				dataMemory[STATUS] &= ~0b00000001; // C-Flag
				dataMemory[STATUS] &= ~0b00000010; // DC-Flag
			}
			break;
		// C-Flag
		case 0:
			if ((selector & (1 << 7)) != 0) {
				dataMemory[STATUS] |= 0b00000001;
			} else {
				dataMemory[STATUS] &= ~0b00000001;
			}
			break;

		// DC-Flag
		case 1:
			if ((selector & (1 << 4)) != 0) {
				dataMemory[STATUS] |= 0b00000010;
			} else {
				dataMemory[STATUS] &= ~0b00000010;
			}
			break;

		// Z-Flag
		case 2:
			if (selector == 0) {
				dataMemory[STATUS] |= 0b00000100;
			} else {
				dataMemory[STATUS] &= ~0b00000100;
			}
			break;
		}

	}

	private void cutWandF(int adress, int desti)
	{
		if (desti == 0) {
			W &= 0xFF;
		} else {
			f[adress] &= 0xFF;
		}

	}
	/* ####################END-OF-FUNCTIONS#################### */
}

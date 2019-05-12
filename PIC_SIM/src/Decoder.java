
public class Decoder
{
	// Test

	// Working Register
	public static int W;

	public static int f[] = new int[128];

	// Flags
	int C, DC, Z, TO, PD;

	private static int byteOrientedMask = 0b11111100000000;

	private static int adressMask = 0b00000011111111;

	private static int bitMask = 0b00001110000000;

	private static int bitOrientedMask = 0b11110000000000;

	public int instruction;

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

		System.out.println("INSTRUCTION: " + Integer.toBinaryString(instruction));

		instruc = instruc >> 12;
		switch (instruc & 0b11000000000000) {
		case 0:
			// Special Cases for some L&C Operations
			switch (instruction) {
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
				adressPart = dataPart >> 1;
				destinationBit = dataPart >> 7;
			}
			break;

		case 1:
			// If it is true then its a bit oriented operations
			instructionPart = instruction & bitOrientedMask;
			dataPart = instruction & adressMask;
			adressPart = dataPart >> 1;
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
			System.out.println("ADDWF");
			addwf(adressPart, destinationBit);
			break;
		case 0x0500:
			System.out.println("ANDWF");
			andwf(adressPart, destinationBit);
			break;
		case 0x0180:
			// Destination Bit is 1
			System.out.println("CLRF");
			clrf(adressPart);
			break;
		case 0x0100:
			// Destination Bit is 0
			System.out.println("CLRW");
			clrw();
			break;
		case 0x0900:
			System.out.println("COMF");
			comf(adressPart, destinationBit);
			break;
		case 0x0300:
			System.out.println("DECF");
			decf(adressPart, destinationBit);
			break;
		case 0x0B00:
			System.out.println("DECFSZ");
			decfsz(adressPart, destinationBit);
			break;
		case 0x0A00:
			System.out.println("INCF");
			incf(adressPart, destinationBit);
			break;
		case 0x0F00:
			System.out.println("INCFSZ");
			incfsz(adressPart, destinationBit);
			break;
		case 0x0400:
			System.out.println("IORWF");
			iorwf(adressPart, destinationBit);
			break;
		case 0x0800:
			System.out.println("MOVF");
			movf(adressPart, destinationBit);
			break;
		case 0x0080:
			// Destination Bit is 1
			System.out.println("MOVWF");
			movf(adressPart, destinationBit);
			break;
		case 0x0000:
			// Destination Bit is 0
			System.out.println("NOP");
			nop();
			break;
		case 0x0D00:
			System.out.println("RLF");
			rlf(adressPart, destinationBit);
			break;
		case 0x0C00:
			System.out.println("RRF");
			rrf(adressPart, destinationBit);
			break;
		case 0x0200:
			System.out.println("SUBWF");
			subwf(adressPart, destinationBit);
			break;
		case 0x0E00:
			System.out.println("SWAPF");
			swapf(adressPart, destinationBit);
			break;
		case 0x0600:
			System.out.println("XORWF");
			xorwf(adressPart, destinationBit);
			break;

		case 0x1000:
			System.out.println("BCF");
			bcf(adressPart, bitPos);
			break;
		case 0x1400:
			System.out.println("BSF");
			bsf(adressPart, bitPos);
			break;
		case 0x1800:
			System.out.println("BTFSC");
			btfsc(adressPart, bitPos);
			break;
		case 0x1C00:
			System.out.println("BTFSS");
			btfss(adressPart, bitPos);
			break;

		case 0x3E00:
			System.out.println("ADDLW");
			addlw(dataPart);
			break;
		case 0x3900:
			System.out.println("ANDLW");
			andlw(dataPart);
			break;
		case 0x2000:
			System.out.println("CALL");
			call(dataPart);
			break;
		case 0x0064:
			// Other Bitmask
			System.out.println("CLRWDT");
			clrwdt();
			break;
		case 0x2800:
			System.out.println("GOTO");
			_goto(dataPart);
			break;
		case 0x3800:
			System.out.println("IORLW");
			iorlw(dataPart);
			break;
		case 0x3000:
			System.out.println("MOVLW");
			movlw(dataPart);
			break;
		case 0x0009:
			System.out.println("RETFIE");
			retfie();
			break;
		case 0x3400:
			System.out.println("RETLW");
			retlw(dataPart);
			break;
		case 0x0008:
			System.out.println("RETURN");
			_return();
			break;
		case 0x0063:
			System.out.println("SLEEP");
			sleep();
			break;
		case 0x3C00:
			System.out.println("SUBLW");
			sublw(dataPart);
			break;
		case 0x3A00:
			System.out.println("XORLW");
			xorlw(dataPart);
			break;
		default:
			System.out.println("NICHT VORHANDEN");
			break;
		}

	}

	public void addwf(int adress, int desti)
	{
		boolean helper;
		// Adds W with f = data
		if (desti == 0) {
			helper = ((W & (1 << 4)) == 0);
			W = W + f[adress];
			if (W == 0) {
				C = 1;
				Z = 1;
			} else {
				C = 0;
				Z = 0;
			}
			if ((W & (1 << 4)) == 1 && helper) {
				DC = 1;
			} else {
				DC = 0;
			}
		} else {
			helper = ((f[adress] & (1 << 4)) == 0);
			f[adress] = W + f[adress];
			if (f[adress] == 0) {
				C = 1;
				Z = 1;
			} else {
				C = 0;
				Z = 0;
			}
			if ((f[adress] & (1 << 4)) == 1 && helper) {
				DC = 1;
			} else {
				DC = 0;
			}
		}
	}

	public void andwf(int adress, int desti)
	{
		// And W with f = data
		if (desti == 0) {
			W = W & f[adress];
		} else {
			f[adress] = W & f[adress];
		}

	}

	public void clrf(int adress)
	{
		f[adress] = 0;
		Z = 1;
	}

	public void clrw()
	{
		W = 0;
		Z = 1;
	}

	public void comf(int adress, int desti)
	{
		if (desti == 0) {
			W = ~f[adress];
		} else {
			f[adress] = ~f[adress];
		}
	}

	public void decf(int adress, int desti)
	{
		if (desti == 0) {
			W = --f[adress];
			if (f[adress] == 0) {
				Z = 1;
			} else {
				Z = 0;
			}
		} else {
			--f[adress];
			if (f[adress] == 0) {
				Z = 1;
			} else {
				Z = 0;
			}
		}
	}

	public void decfsz(int adress, int desti)
	{
		if (desti == 0) {
			W = --f[adress];
		} else {
			--f[adress];
		}
		if (f[adress] == 0) {
			nop();
		}
	}

	public void incf(int adress, int desti)
	{
		if (desti == 0) {
			W = ++f[adress];
			if (f[adress] == 0) {
				Z = 1;
			} else {
				Z = 0;
			}
		} else {
			++f[adress];
			if (f[adress] == 0) {
				Z = 1;
			} else {
				Z = 0;
			}
		}
	}

	public void incfsz(int adress, int desti)
	{
		if (desti == 0) {
			W = ++f[adress];
		} else {
			++f[adress];
		}
		if (f[adress] == 0) {
			nop();
		}
	}

	public void iorwf(int adress, int desti)
	{
		if (desti == 0) {
			W = W | f[adress];
			if (f[adress] == 0) {
				Z = 1;
			} else {
				Z = 0;
			}
		} else {
			f[adress] = W | f[adress];
			if (f[adress] == 0) {
				Z = 1;
			} else {
				Z = 0;
			}
		}
	}

	public void movf(int adress, int desti)
	{
		if (desti == 0) {
			W = f[adress];
			if (f[adress] == 0) {
				Z = 1;
			} else {
				Z = 0;
			}
		} else {
			if (f[adress] == 0) {
				Z = 1;
			} else {
				Z = 0;
			}
		}
	}

	public void movwf(int adress)
	{
		f[adress] = W;
	}

	public void rlf(int adress, int desti)
	{
		// helper gets the first bit that goes later to C
		int helper = f[adress] >> 7;
		if (desti == 0) {
			W = (f[adress] << 1) | C;
		} else {
			f[adress] = (f[adress] << 1) | C;
		}
		C = helper;
	}

	public void rrf(int adress, int desti)
	{
		int helper = f[adress] << 7;
		helper = helper >> 7;
		if (desti == 0) {
			W = (f[adress] >> 1) | C;
		} else {
			f[adress] = (f[adress] >> 1) | C;
		}
		C = helper;
	}

	public void subwf(int adress, int desti)
	{
		boolean helper;
		if (desti == 0) {
			helper = ((W & (1 << 4)) == 0);
			W = f[adress] + _2complement();
			if (W == 0) {
				C = 1;
				Z = 1;
			}
			if ((W & (1 << 4)) == 1 && helper) {
				DC = 1;
			} else {
				DC = 0;
			}
		} else {
			helper = ((f[adress] & (1 << 4)) == 0);
			f[adress] = f[adress] + _2complement();
			if (f[adress] == 0) {
				C = 1;
				Z = 1;
			}
			if ((f[adress] & (1 << 4)) == 1 && helper) {
				DC = 1;
			} else {
				DC = 0;
			}
		}
	}

	public void swapf(int adress, int desti)
	{
		int upperNibble = f[adress] >> 4;
		int lowerNibble = f[adress] << 4;
		int total = lowerNibble | upperNibble;
		if (desti == 0) {
			W = total;
		} else {
			f[adress] = total;
		}
	}

	public void xorwf(int adress, int desti)
	{
		if (desti == 0) {
			W = W ^ f[adress];
			if (f[adress] == 0) {
				Z = 1;
			} else {
				Z = 0;
			}
		} else {
			f[adress] = W ^ f[adress];
			if (f[adress] == 0) {
				Z = 1;
			} else {
				Z = 0;
			}
		}
	}

	public void bcf(int adress, int b)
	{
		f[adress] = f[adress] & ~(1 << b);
	}

	public void bsf(int adress, int b)
	{
		f[adress] = f[adress] | ~(1 << b);
	}

	public void btfsc(int adress, int b)
	{
		if ((f[adress] & (1 << b)) == 0) {
			nop();
		}
	}

	public void btfss(int adress, int b)
	{
		if ((f[adress] & (1 << b)) == 1) {
			nop();
		}
	}

	public void addlw(int data)
	{
		boolean helper = ((W & (1 << 4)) == 0);
		W = W + data;
		if (W == 0) {
			C = 1;
			Z = 1;
		}
		if ((W & (1 << 4)) == 1 && helper) {
			DC = 1;
		} else {
			DC = 0;
		}
		System.out.println("W Register: " + W);
	}

	public void andlw(int data)
	{
		W = W & data;
		if (W == 0) {
			Z = 1;
		} else {
			Z = 0;
		}
		System.out.println("W Register: " + W);
	}

	public void call(int data)
	{
		// TODO FINISH WITH GOTO

	}

	public void clrwdt()
	{
		// TODO FINISH WATCHDOG FOR THIS OPERATION
		TO = 0;
		PD = 0;
	}

	public void _goto(int data)
	{
		// TODO FINISH WITH CALL
	}

	public void iorlw(int data)
	{
		W = W | data;
		if (W == 0) {
			Z = 1;
		} else {
			Z = 0;
		}
		System.out.println("W Register: " + W);
	}

	public void movlw(int data)
	{
		W = data;
		System.out.println("W Register: " + W);
	}

	public void retfie()
	{
		// TODO FINISH
	}

	public void retlw(int data)
	{
		// TODO FINISH
		W = data;
	}

	public void _return()
	{
		// TODO FINISH
	}

	public void sleep()
	{
		// TODO FINISH MAYBE?
		PD = 1;
		TO = 0;
	}

	public void sublw(int data)
	{
		boolean helper;
		helper = ((W & (1 << 4)) == 0);
		W = data + _2complement();
		if (W == 0) {
			C = 1;
			Z = 1;
		} else {
			C = 0;
			Z = 0;
		}
		if ((W & (1 << 4)) == 1 && helper) {
			DC = 1;
		} else {
			DC = 0;
		}
		System.out.println("W Register: " + W);
	}

	public void xorlw(int data)
	{
		W = W ^ data;
		if (W == 0) {
			Z = 1;
		} else {
			Z = 0;
		}
		System.out.println("W Register: " + W);
	}

	public void nop()
	{
		return;
	}

	private void setInstruction(int instruc)
	{
		this.instruction = instruc;
	}

	private int _2complement()
	{
		W = ~W;
		W++;
		return W;
	}
}

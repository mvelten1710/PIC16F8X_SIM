
public class Decoder
{
	// Test

	// Working Register
	public static int W;

	public static int f;

	// Flags
	int C, DC, Z, TO, PD;

	private static int byteOrientedMask = 0b11111100000000;

	private static int byteOrientedDataMask = 0b00000011111111;

	public int instruction;

	public Decoder()
	{
		instruction = 0;
	}

	public void decode(int instruc)
	{
		setInstruction(instruc);
		// Bitmask for Byte Oriented Operations
		int instructionID = instruction & byteOrientedMask;
		int dataID = instruction & byteOrientedDataMask;

		// Bitmask for Bit Oriented Operations

		// Bitmask for Literal And Control Operations

		// Search for Instruction
		switch (instructionID) {
		case 0x0700:
			System.out.println("ADDWF");
			addwf(dataID);
			break;
		case 0x0500:
			System.out.println("ANDWF");
			break;
		// case 0x0100:
		// //Destination Bit is 1
		// System.out.println("CLRF");
		// break;
		// case 0x0100:
		// //Destination Bit is 0
		// System.out.println("CLRW");
		// break;
		case 0x0900:
			System.out.println("COMF");
			break;
		case 0x0300:
			System.out.println("DECF");
			break;
		case 0x0B00:
			System.out.println("DECFSZ");
			break;
		case 0x0A00:
			System.out.println("INCF");
			break;
		case 0x0F00:
			System.out.println("INCFSZ");
			break;
		case 0x0400:
			System.out.println("IORWF");
			break;
		case 0x0800:
			System.out.println("MOVF");
			break;
		case 0x0080:
			// Destination Bit is 1
			System.out.println("MOVWF");
			break;
		// case 0x0000:
		// //Destination Bit is 0
		// System.out.println("NOP");
		// break;
		// case 0x0B00:
		// System.out.println("RLF");
		// break;
		case 0x0C00:
			System.out.println("RRF");
			break;
		case 0x0200:
			System.out.println("SUBWF");
			break;
		case 0x0E00:
			System.out.println("SWAPF");
			break;
		case 0x0600:
			System.out.println("XORWF");
			break;

		case 0x1000:
			System.out.println("BCF");
			break;
		case 0x1400:
			System.out.println("BSF");
			break;
		case 0x1800:
			System.out.println("BTFSC");
			break;
		case 0x1C00:
			System.out.println("BTFSS");
			break;

		case 0x3E00:
			System.out.println("ADDLW");
			break;
		case 0x3900:
			System.out.println("ANDLW");
			break;
		case 0x2000:
			System.out.println("CALL");
			break;
		case 0x0064:
			// Other Bitmask
			System.out.println("CLRWDT");
			break;
		case 0x2800:
			System.out.println("GOTO");
			break;
		case 0x3800:
			System.out.println("IORLW");
			break;
		case 0x3000:
			System.out.println("MOVLW");
			movlw(instruc);
			break;
		case 0x0009:
			System.out.println("RETFIE");
			break;
		case 0x3400:
			System.out.println("RETLW");
			break;
		case 0x0008:
			System.out.println("RETURN");
			break;
		case 0x0063:
			System.out.println("SLEEP");
			break;
		case 0x3C00:
			System.out.println("SUBLW");
			break;
		case 0x3A00:
			System.out.println("XORLW");
			break;
		default:
			System.out.println("NICHT VORHANDEN");
			break;
		}

	}

	public int addwf(int data)
	{
		// Adds W with f = data
		if ((data >> 7) == 0) {
			return W += data;
		} else {
			return f += data;
		}
	}

	public int andwf(int data)
	{
		// And W with f = data
		if ((data >> 7) == 0) {
			return W &= data;
		} else {
			return f &= data;
		}

	}

	public void clrf()
	{

	}

	public void clrw()
	{
		W = 0;
		Z = 1;
	}

	public int comf(int data)
	{
		if ((data >> 7) == 0) {
			return W = ~data;
		} else {
			return f = ~data;
		}
	}

	public int decf(int data)
	{
		if ((data >> 7) == 0) {
			--data;
			if (data == 0) {
				Z = 1;
			}
			return W = --data;
		} else {
			if (data == 0) {
				Z = 1;
			}
			return f = --data;
		}
	}

	// TODO Dont forget to improve this (skip etc.)
	public int decfsz(int data)
	{
		if ((data >> 7) == 0) {
			--data;
			if (data == 0) {
				nop();
				return -1;
			}
			return W = data;
		} else {
			if (data == 0) {
				return -1;
			}
			return f = data;
		}
	}

	public int incf(int data)
	{
		if ((data >> 7) == 0) {
			++data;
			if (data == 0) {
				Z = 1;
			} else {
				Z = 0;
			}
			return W = data;
		} else {
			++data;
			if (data == 0) {
				Z = 1;
			} else {
				Z = 0;
			}
			return f = data;
		}
	}

	// TODO Dont forget to improve this (skip etc.)
	public int incfsz(int data)
	{
		if ((data >> 7) == 0) {
			++data;
			if (data == 0) {
				nop();
				// -1 indicator for skiping
				return -1;
			}
			return W = data;
		} else {
			if (data == 0) {
				// -1 indicator for skiping
				return -1;
			}
			return f = data;
		}
	}

	public int iorwf(int data)
	{
		if ((data >> 7) == 0) {
			data = data | W;
			if (data == 0) {
				Z = 1;
			} else {
				Z = 0;
			}
			return W = data;
		} else {
			data = data | W;
			if (data == 0) {
				Z = 1;
			} else {
				Z = 0;
			}
			return f = data;
		}
	}

	public int movf(int data)
	{
		if ((data >> 7) == 0) {
			if (data == 0) {
				Z = 1;
			} else {
				Z = 0;
			}
			return W = data;
		} else {
			if (data == 0) {
				Z = 1;
			} else {
				Z = 0;
			}
			return f = data;
		}
	}

	public int movwf(int data)
	{
		return f = W;
	}

	public int rlf(int data)
	{
		if (data < 128) {
			C = 0;
		} else {
			C = 1;
		}
		if ((data >> 7) == 0) {
			return W = data << 1;
		} else {
			return f = data << 1;
		}
	}

	public void movlw(int data)
	{

	}

	public void addlw()
	{

	}

	public void sublw()
	{

	}

	public void call()
	{

	}

	public void _goto()
	{

	}

	public void movf()
	{

	}

	public void subwf()
	{

	}

	public void incfsz()
	{

	}

	public void rrf()
	{

	}

	public void bsf()
	{

	}

	public void bcf()
	{

	}

	public void btfsc()
	{

	}

	public void btfss()
	{

	}

	public void nop()
	{
		return;
	}

	private void setInstruction(int instruc)
	{
		this.instruction = instruc;
	}
}

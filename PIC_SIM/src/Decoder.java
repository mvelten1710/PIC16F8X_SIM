

public class Decoder {
	
	private static int byteOrientedMask = 0b11111100000000;
	public int instruction;
	
	
	public Decoder() {
		instruction = 0;	
	}
	
	public void decode(int instruc) {
		setInstruction(instruc);
		//Bitmask for Byte Oriented Operations
		int instructionID = instruction & byteOrientedMask; 
		
		//Bitmask for Bit Oriented Operations
		
		//Bitmask for Literal And Control Operations
		
		
		//Search for Instruction
		switch (instructionID) {
			case 0x3000:
				System.out.println("MOVLW");
				break;
			case 0x3E00:
				System.out.println("ADDLW");
				break;
			case 0x3B00:
				System.out.println("SUBLW");
				break;
			case 0x2000:
				System.out.println("CALL");
				break;
			case 0x2800:
				System.out.println("GOTO");
				break;
			case 0x0000:
				System.out.println("MOVWF");
				break;
			case 0x0800:
				System.out.println("MOVF");
				break;
			case 0x0700:
				System.out.println("ADDWF");
				break;
			case 0x0200:
				System.out.println("SUBWF");
				break;
			case 0x0E00:
				System.out.println("INCFSZ");
				break;
			case 0x0C00:
				System.out.println("RLF");
				break;
			case 0x0B00:
				System.out.println("RRF");
				break;
			case 0x1400:
				System.out.println("BSF");
				break;
			case 0x1000:
				System.out.println("BCF");
				break;
			case 0x1800:
				System.out.println("BTFSC");
				break;
			case 0x1B00:
				System.out.println("BTFSS");
				break;
			default:
				System.out.println("NICHT VORHANDEN");
				break;
		}
		
	}
	
	public void movlw() {
		
	}
	public void addlw() {
		
	}
	public void sublw() {
		
	}
	public void call() {
		
	}
	public void _goto() {
		
	}
	public void movwf() {
		
	}
	public void movf() {
		
	}
	public void addwf() {
		
	}
	public void subwf() {
		
	}
	public void decfsz() {
		
	}
	public void incfsz() {
		
	}
	public void rlf() {
		
	}
	public void rrf() {
		
	}
	public void bsf() {
		
	}
	public void bcf() {
		
	}
	public void btfsc() {
		
	}
	public void btfss() {
		
	}

	private void setInstruction(int instruc) {
		this.instruction = instruc;
	}
}

package PicController;

import java.io.IOException;
import java.util.ArrayList;

public class Controller
{
	/* #################PIC-REGISTER################# */
	// Memory
	public static int programMemory[] = new int[1024];

	// DataMemory are the banks -> (bank0(128) & bank1(128))
	public static int dataMemory[] = new int[256];

	// Stack
	public static int stack[] = new int[8];

	// Working Register
	public static int W;

	// f Register
	public static int f[] = new int[128];

	// Stack counter
	private static int sIndex = 0;

	// ProgramMemory Counter
	public static int pIndex = 0;

	/* ############################################## */

	/* ######################BANK0##################### */
	public static final int INDF = 0;
	
	public static final int TMR0 = 1;
	
	public static final int PCL = 2;
	
	public static final int STATUS = 3;
	
	public static final int FSR = 4;
	
	public static final int PORTA = 5;
	
	public static final int PORTB = 6;
	
	public static final int ZERO = 7;
	
	public static final int EEDATA = 8;
	
	public static final int EEADDR = 8;
	
	public static final int PCLATH = 8;
	
	public static final int INTCON = 8;

	/* ############################################## */
	
	/* ####################FLAGS#################### */
	public static final int CFLAG = 0;

	public static final int DCFLAG = 1;

	public static final int ZFLAG = 2;

	public static final int PDFLAG = 3;

	public static final int TOFLAG = 4;

	public static final int RP0FLAG = 5;

	public static final int RP1FLAG = 6;

	public static final int IRPFLAG = 7;
	/* ############################################## */

	/* ####################PORT-A#################### */
	public static final int RA0 = 0;

	public static final int RA1 = 1;

	public static final int RA2 = 2;

	public static final int RA3 = 3;

	public static final int RA4 = 4;
	/* ############################################## */

	/* ####################PORT-B#################### */
	public static final int RB0 = 0;

	public static final int RB1 = 1;

	public static final int RB2 = 2;

	public static final int RB3 = 3;

	public static final int RB4 = 4;

	public static final int RB5 = 5;

	public static final int RB6 = 6;

	public static final int RB7 = 7;
	/* ############################################## */
	
	/* ####################INTCON#################### */
	public static final int RBIF = 0;

	public static final int INTF = 1;

	public static final int T0IF = 2;

	public static final int RBIE = 3;

	public static final int INTE = 4;

	public static final int T0IE = 5;

	public static final int EEIE = 6;

	public static final int GIE = 7;
	/* ############################################## */
	
	/* #################OWN-VARIABLES################ */
	protected static boolean clockRunning;

	protected static boolean stepping;

	protected static boolean allCleared;
	
	protected static boolean breakPointReached;
	
	protected static int selectedRow;
	
	protected static int runtime;
	
	protected static long threadSpeed;
	
	protected static ArrayList<String> operationRow;

	protected static Parser parser;

	protected static Decoder decoder;

	protected static File file;

	protected static CPUClock clock;
	
	protected static LineSelector lineSelector;
	/* ############################################## */

	public static void main(String[] args) throws IOException
	{

		threadSpeed = 800L;
		selectedRow = 0;
		runtime = 0;
		breakPointReached = false;
		clockRunning = false;
		allCleared = true;
		stepping = false;

		// New Parser Object
		parser = new Parser();

		// New Decoder Object
		decoder = new Decoder();

		// New File Object
		file = new File();

		// New Clock Object
		clock = new CPUClock();

		// Starts the UI and combines the Decoder(Commands)
		// and the Parser(Reads LST Files)
		Simulator_UI newWindow = new Simulator_UI();
		newWindow.startWindow();

	}

	public static int getFlag(int flag)
	{
		return ((dataMemory[STATUS] & (1 << flag)) != 0) ? 1 : 0;
	}

	public static void pushStack(int data)
	{
		stack[sIndex] = data;
		sIndex++;
		if (sIndex == 7) {
			sIndex = 0;
		}
	}

	public static int popStack()
	{
		sIndex--;
		int data = stack[sIndex];
		stack[sIndex] = 0;
		return data;

	}

}

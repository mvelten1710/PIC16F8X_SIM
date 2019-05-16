import java.io.IOException;

public class Controller extends Thread
{
	// Memory
	public static int programMemory[] = new int[1024];

	// DataMemory are the banks -> (bank0(128) & bank1(128))
	public static int dataMemory[] = new int[256];

	public static int stack[] = new int[8];

	// Stack counter
	private static int sIndex = 0;

	// ProgramMemory Counter
	public static int pIndex = 0;

	public static int operationCounter = 0;

	public static boolean clockRunning;

	public static boolean stepping;

	public static boolean allCleared;

	public static Parser parser;

	public static Decoder decoder;

	public static File file;

	public static CPUClock clock;

	public static void main(String[] args) throws IOException
	{

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
	
	public static void pushStack(int data) {
		stack[sIndex] = data;
		sIndex++;
	}
	
	public static void popStack() {
		
	}

}

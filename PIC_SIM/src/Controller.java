import java.io.IOException;

public class Controller extends Thread
{
	// Memory
	public static int programMemory[] = new int[1024];

	// DataMemory are the banks -> (bank1(128) & bank2(128))
	public static int dataMemory[] = new int[256];

	public static int stack[] = new int[8];

	// Stack counter
	private static int sCounter = 0;

	// ProgramMemory Counter
	public static int pCounter = 0;

	public static Parser parser;

	public static Decoder decoder;

	public static File file;

	public static CPUClock clock;

	public static boolean clockRunning;

	public static void main(String[] args) throws IOException
	{

		clockRunning = false;
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

}

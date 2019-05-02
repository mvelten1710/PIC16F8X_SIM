import java.io.IOException;

public class Controller
{
	// Test
	public static int programMemory[] = new int[1024];

	public static int dataMemory[] = new int[128];
	
	

	private static int pCounter = 0;

	public static Parser parser;

	public static Decoder decoder;

	public static void main(String[] args) throws IOException
	{
		// Starts the UI and combines the Decoder(Commands)
		// and the Parser(Reads LST Files)
		Simulator_UI newWindow = new Simulator_UI();
		newWindow.startWindow();

		// New Parser Object
		parser = new Parser();

		// New Decoder Object
		decoder = new Decoder();

		for (int c = 0; c < programMemory.length; c++) {
			if (programMemory[c] != 0) {
				System.out.println("\n" + programMemory[c]);
			}
		}

	}

	public void executeFile() throws IOException
	{
		for (; pCounter < programMemory.length; pCounter++) {
			if (programMemory[pCounter] != 0) {
				decoder.decode(programMemory[pCounter]);
			}
		}
	}
	
	public void readFile(String filePath) throws IOException
	{
		programMemory = parser.getCommands(filePath);
		
	}
}

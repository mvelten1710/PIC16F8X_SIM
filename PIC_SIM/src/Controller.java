import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
	//Test
	public static int programMemory[] = new int[1024];
	public static int dataMemory[] = new int [128];
	
	private static int pCounter = 0;

	static int W;
	
	
	
	public static void main(String[] args) throws IOException {
		//Starts the UI and combines the Decoder(Commands)
		//and the Parser(Reads LST Files)
		
		//New Parser Object
		Parser parser = new Parser();
		
		//Adds all Values from Parser to the commandsList in Controller
		programMemory = parser.getCommands();
		//New Decoder Object
		Decoder decoder = new Decoder();
		
		for (; pCounter < programMemory.length; pCounter++) {
			if(programMemory[pCounter] != 0) {
				decoder.decode(programMemory[pCounter]);
			}
		}
		
		
		for (int c = 0; c < programMemory.length; c++) {
			if(programMemory[c] != 0) {
				System.out.println("\n" + programMemory[c]);
			}
		}
		

	}
}

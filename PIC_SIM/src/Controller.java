import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

	public static List<Integer> programMemory = new ArrayList<Integer>();
	
	private static int iPointer = 0;
	
	public static void main(String[] args) throws IOException {
		//Starts the UI and combines the Decoder(Commands)
		//and the Parser(Reads LST Files)
		
		//New Parser Object
		Parser parser = new Parser();
		
		//Adds all Values from Parser to the commandsList in Controller
		programMemory.addAll(parser.getCommands());
		
		//New Decoder Object
		Decoder decoder = new Decoder();
		
		for (; iPointer < programMemory.size(); iPointer++) {
			decoder.decode(programMemory.get(iPointer));
			
		}
		
		
		for (Integer integer : programMemory) {
			System.out.println("\n" + integer);
		}
		

	}
}

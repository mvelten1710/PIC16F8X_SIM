import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

	public static List<Integer> commandsList = new ArrayList<Integer>();
	public static void main(String[] args) throws IOException {
		//Starts the UI and combines the Decoder(Commands)
		//and the Parser(Reads LST Files)
		
		//New Parser Object
		Parser parser = new Parser();
		//Adds all Values from Parser to the commandsList in Controller
		commandsList.addAll(parser.getCommands());
		
		for (Integer integer : commandsList) {
			System.out.println(integer);
		}
	}
}

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Parser
{

	private int counter;

	private int instructionRegister[];

	private BufferedReader reader;

	public Parser()
	{
		// Gets initialized
		instructionRegister = new int[1024];
		counter = 0;
		reader = null;
	}

	public int[] getCommands(String path) throws IOException
	{
		// New File for the LST files
		File file = new File(path);
		// Tries to create the BufferedReader for file
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String content;
		String commands = "";

		while ((content = reader.readLine()) != null) {
			// Takes the 6 until 9 char and saves them in
			// the commands String
			commands = content.substring(5, 9);

			// Saves the command in the commands array(in Controller.java)
			try {
				// tries to parse the string to an integer
				instructionRegister[counter] = Integer.parseInt(commands, 16);
				counter++;
			} catch (NumberFormatException e) {
				// Gets own output in the UI
				// System.out.println("ERROR: CANT PARSE TO INT");
			}

		}
		return instructionRegister;
	}

}

package PicController;

import static PicController.Controller.clockRunning;
import static PicController.Controller.decoder;
import static PicController.Controller.pIndex;
import static PicController.Controller.parser;
import static PicController.Controller.programMemory;

import java.io.IOException;

public class File
{

	public File()
	{
	}

	// Later with a clock that calls one operation at a time
	public void saveOperationsIntoMemory() throws IOException
	{
		for (; pIndex < programMemory.length; pIndex++) {
			if (programMemory[pIndex] != 0) {
				decoder.decode(programMemory[pIndex]);
			}
		}
	}

	public void readFile(String filePath) throws IOException
	{
		programMemory = parser.getCommands(filePath);
	}

	public void executeOperation()
	{
		// Test run
		if (pIndex < programMemory.length) {
			decoder.decode(programMemory[pIndex]);
		} else {
			clockRunning = false;
		}
	}

}

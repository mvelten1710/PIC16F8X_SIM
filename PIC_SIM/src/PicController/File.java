package PicController;

import static PicController.Controller.INTCON;
import static PicController.Controller.clockRunning;
import static PicController.Controller.dataMemory;
import static PicController.Controller.decoder;
import static PicController.Controller.interrupt;
import static PicController.Controller.pIndex;
import static PicController.Controller.parser;
import static PicController.Controller.programMemory;

import java.io.IOException;

public class File
{

	public File()
	{
	}

	public void readFile(String filePath) throws IOException
	{
		programMemory = parser.getCommands(filePath);
	}

	public void executeOperation()
	{
		if (pIndex < programMemory.length) {
			if(!((dataMemory[INTCON] & (1 << 5)) == 0) && !((dataMemory[INTCON] & (1 << 7)) == 0) 
					&& !((dataMemory[INTCON] & (1 << 2)) == 0)) {
				System.out.println("INTERRUPT BEFEHL");
				interrupt.run();
			}else {
				System.out.println("NORMAL BEFEHL");
				decoder.decode(programMemory[pIndex]);
			}
		} else {
			clockRunning = false;
		}
	}

}

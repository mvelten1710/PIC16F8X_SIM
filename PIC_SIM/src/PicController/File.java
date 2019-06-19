package PicController;

import static PicController.Controller.*;

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
					&& !((dataMemory[INTCON] & (1 << 2)) == 0) && firstTimeInterrupt) {
				Interrupt.init();
			}else {
				decoder.decode(programMemory[pIndex]);
			}
			
		} else {
			clockRunning = false;
		}
	}

}

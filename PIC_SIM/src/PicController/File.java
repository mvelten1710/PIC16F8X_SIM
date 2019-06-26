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
			if(((dataMemory[INTCON] & (1 << 7)) != 0) && 												// Global Interrupt Enable Bit
					(((dataMemory[INTCON] & (1 << 5)) != 0 && (dataMemory[INTCON] & (1 << 2)) != 0)		// Timer Interrupt
					|| ((dataMemory[INTCON] & (1 << 4)) != 0 && (dataMemory[INTCON] & (1 << 1)) != 0)	// RBO Interrupt
					|| ((dataMemory[INTCON] & (1 << 3)) != 0 && (dataMemory[INTCON] & (1 << 0)) != 0))	// RB4-RB7 Interrupt
					&& firstTimeInterrupt) {
				Interrupt.init();
			}else {
				decoder.decode(programMemory[pIndex]);
			}
			
		} else {
			clockRunning = false;
		}
	}

}

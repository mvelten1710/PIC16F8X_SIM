import java.io.IOException;

public class File extends Controller
{

	public File()
	{
	}

	// Later with a clock that calls one operation at a time
	public void saveOperationsIntoMemory() throws IOException
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
		for (int i : programMemory) {
			if (i != 0) {
				System.out.println(i);
			}
		}
	}

	public void executeOperation()
	{
		// TODO Build here the execution process of the operations
		// Dont forget step execution and continues execution

		// Test run
		if (programMemory[pCounter] != 0) {
			decoder.decode(programMemory[pCounter]);
			pCounter++;
		} else {
			clockRunning = false;
		}
	}

}

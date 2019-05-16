import java.io.IOException;

public class File extends Controller
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
		for (int i : programMemory) {
			if (i != 0) {
				System.out.println(i);
			}
		}
	}

	public void executeOperation()
	{
		// Test run
		if (operationCounter != 0) {
			decoder.decode(programMemory[pIndex]);
			if(!decoder.ispIndexModified()) {
				pIndex++;
				//TODO pIndexModified look after!
			}
			operationCounter--;
		} else {
			clockRunning = false;
		}
	}

}

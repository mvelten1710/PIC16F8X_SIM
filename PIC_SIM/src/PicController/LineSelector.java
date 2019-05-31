package PicController;

import static PicController.Controller.*;

public class LineSelector {
	
					//operationRow		//Holds the Information for the operations
	private String operationSelector; //To identify which operation it is (GOTO, CALL, RETURN
	private static int operationData; //For Call and Goto to identify which row they're calling
	private static int opBegin, opEnd; //First line of the first operation and last line
	private static int index = 0;
	
	private static void setStartAndEnd()
	{
		//Gets the operation beginning line
		opBegin = Integer.parseInt((String) operationRow.get(0).subSequence(20, 25));
		System.out.println(opBegin);
		opEnd = Integer.parseInt((String) operationRow.get(operationRow.size()-1).subSequence(20, 25));
		System.out.println(opEnd);
	}

	//Called when one operation gets executed (updateUI?)
	public static void identifyRow()
	{
		//36 Chars until operation +3-6 for operation
		//TODO Finish the rest of this...

	}
}

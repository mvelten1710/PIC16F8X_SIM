package PicController;

import static PicController.Controller.*;

import java.util.ArrayList;

public class LineSelector {
	
	private ArrayList<String> fileContent; //Holds the Information for the operations
	private String operationSelector; //To identify which operation it is (GOTO, CALL, RETURN
	private int operationData; //For CALL and GOTO to identify which row they're calling
	private int bit;
	private int opNext;
	public int opBegin;
	private int index = 0, stackIndex = 0;
	private int returnValues[];
	
	public LineSelector() {
		//Gets the operation beginning line
		returnValues = new int[8];
		fileContent = new ArrayList<String>(operationRow);
		opBegin = Integer.parseInt((String) fileContent.get(0).subSequence(20, 25))-1;
	}
	
	public int nextRow() 
	{
		operationSelector = fileContent.get(index).substring(36, fileContent.get(index).length());
		//We need to distinguish between these operation because they can alter the operation flow
		switch (operationSelector) {
		case "call":
			operationData = Integer.parseInt((String) fileContent.get(index).subSequence(6, 9), 16) & 0b00011111111111;
			pushStack(index + 1);
			index = operationData;
			break;

		case "goto":
			operationData = Integer.parseInt((String) fileContent.get(index).subSequence(7, 9), 16);
			index = operationData;
			break;
			
		case "decfsz":
			operationData = Integer.parseInt((String) fileContent.get(index).subSequence(7, 9), 16);
			operationData = operationData & 0b01111111;
			if(dataMemory[indirectRead(operationData)] == 0) {
				index += 2;
			}else {
				index++;
			}
			break;
			
		case "incfsz":
			operationData = Integer.parseInt((String) fileContent.get(index).subSequence(7, 9), 16);
			operationData = operationData & 0b01111111;
			if(dataMemory[indirectRead(operationData)] == 0) {
				index += 2;
			}else {
				index++;
			}
			break;
			
		case "btfsc":
			bit = (Integer.parseInt((String) fileContent.get(index).subSequence(5, 9), 16) & 0b00001110000000) >> 7;
			operationData = Integer.parseInt((String) fileContent.get(index).subSequence(5, 9), 16) & 0b00000001111111;
			if (((dataMemory[indirectRead(operationData)] & (1 << bit)) >> bit) == 0) {
				index += 2;
			}else {
				index++;
			}
			break;
			
		case "btfss":
			bit = (Integer.parseInt((String) fileContent.get(index).subSequence(5, 9), 16) & 0b00001110000000) >> 7;
			operationData = Integer.parseInt((String) fileContent.get(index).subSequence(5, 9), 16) & 0b00000001111111;
			if (((dataMemory[indirectRead(operationData)] & (1 << bit)) >> bit) == 1) {
				index += 2;
			}else {
				index++;
			}
			break;
			
		case "return":
			index = popStack();
			break;
			
		case "retlw":
			index = popStack();
			break;
			
		case "retifie":
			
			break;
			
		default:
			index++;
			break;
		}
		
		opNext = Integer.parseInt((String) fileContent.get(index).subSequence(20, 25));
		return opNext-1;
		
	}
	
	private void pushStack(int data) 
	{
		returnValues[stackIndex] = data;
		stackIndex++;
		if (stackIndex == 7) {
			stackIndex = 0;
		}
	}
	
	private int popStack() 
	{
		stackIndex--;
		return returnValues[stackIndex];
	}
}

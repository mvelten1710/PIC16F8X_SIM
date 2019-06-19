package PicController;

import static PicController.Controller.*;

public class Interrupt {
	
	
	Interrupt(){
		System.out.println("INTERRUPT INIT");
	}
	
	public static void init() {
		pushStack(++pIndex);
		lineSelector.pushStack(pIndex);
		pIndex = 4;
		interruptReached = true;
		firstTimeInterrupt = false;
	}
}

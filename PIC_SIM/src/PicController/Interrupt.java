package PicController;

import static PicController.Controller.*;

public class Interrupt {
	
	private static boolean firstTime = true;
	
	Interrupt(){
		System.out.println("INTERRUPT GESTARTET");
	}
	
	public static void run() {
		if(firstTime) {
			pushStack(++pIndex);
			pIndex = 4;
			firstTime = false;
		}
		decoder.decode(programMemory[pIndex]);
	}
}

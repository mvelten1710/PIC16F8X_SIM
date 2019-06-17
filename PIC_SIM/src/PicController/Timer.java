package PicController;

import static PicController.Controller.*;

public class Timer {
	
	private int timerCounter;
	private int clockCounter;
	
	public Timer(){
		timerCounter = 0;
		clockCounter = 0;
	}
	
	
	public void incTimer() {
		int helper = dataMemory[TMR0];
		if((dataMemory[OPTION_REG] & (1 << 5)) == 0) {
			clockCounter++;
			if(clockCounter % 4 == 0) {
				if(cycles == 2) {
					dataMemory[TMR0] = timerCounter++;
				}
				dataMemory[TMR0] = timerCounter++;
			}
			if(dataMemory[TMR0] >= 256) {
				resetTimer();
				dataMemory[TMR0] = timerCounter;
			}
		}
		if(dataMemory[TMR0] < helper)
			dataMemory[INTCON] |= (1 << 2);
	}

	public void resetTimer() {
		timerCounter = 0;
		clockCounter = 0;
	}
}

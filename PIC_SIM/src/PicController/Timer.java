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
			if(clockCounter % checkPrescale() == 0) {
				if(cycles == 2) {
					dataMemory[TMR0] = ++timerCounter;
				}
				dataMemory[TMR0] = ++timerCounter;
			}
			if(timerCounter >= 256) {
				resetTimer();
				dataMemory[TMR0] = timerCounter;
			}
		}
		if(dataMemory[TMR0] < helper) {
			dataMemory[INTCON] |= (1 << 2);
			dataMemory[139] |= (1 << 2);
		}
	}

	public void resetTimer() {
		timerCounter = 0;
		clockCounter = 0;
	}
	
	
	private int checkPrescale() {
		if (((dataMemory[OPTION_REG] & (1 << 5)) != 0)) {
			return 4;
		}
		switch (dataMemory[OPTION_REG]) {
		case 0:
			return 2;
		case 1:
			return 4;
		case 2:
			return 8;
		case 3:
			return 18;
		case 4:
			return 32;
		case 5:
			return 64;
		case 6:
			return 128;
		case 7:
			return 256;
		}
		return 2;
	}
	
}

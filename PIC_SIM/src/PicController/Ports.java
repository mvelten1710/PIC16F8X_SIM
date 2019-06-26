package PicController;

import static PicController.Controller.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Ports {
	
	//Initial State of the ports
	private ArrayList<Boolean> portStat = new ArrayList<Boolean>(Arrays.asList(false, false, false, false, false, false, false, false));
	
	protected boolean rb0Rising = false;
	
	protected void checkPorts() {
			//###################RB0#######################
		
			if ((dataMemory[PORTB] & 1) != 0) {
				if (!portStat.get(RB0)) {
					portStat.set(RB0, true);
					if (rb0Rising) {
						dataMemory[INTCON] |= 2;
					}
				}
			}else {
				if (portStat.get(RB0)) {
					portStat.set(RB0, false);
					if (!rb0Rising) {
						dataMemory[INTCON] |= 2;
					}
				}
			}
			//#############################################
			
			if ((dataMemory[PORTB] & (1 << 1)) != 0) {
				if (!portStat.get(RB1)) {
					
				}
			}
			if ((dataMemory[PORTB] & (1 << 2)) != 0) {
				if (!portStat.get(RB2)) {
					
				}
			}
			if ((dataMemory[PORTB] & (1 << 3)) != 0) {
				if (!portStat.get(RB3)) {
					
				}
			}
			
			//##################RB4-RB7#####################
			if ((dataMemory[PORTB] & (1 << 4)) != 0) {
				if (!portStat.get(RB4)) {
					portStat.set(RB4, true);
					if ((dataMemory[TRISB] & (1 << 4)) != 0) {
						dataMemory[INTCON] |= 1;
					}
					
				}
			}else {
				if (portStat.get(RB4)) {
					portStat.set(RB4, false);
					if ((dataMemory[TRISB] & (1 << 4)) != 0) {
						dataMemory[INTCON] |= 1;
					}
					
				}
			}
			if ((dataMemory[PORTB] & (1 << 5)) != 0) {
				if (!portStat.get(RB5)) {
					portStat.set(RB5, true);
					if ((dataMemory[TRISB] & (1 << 5)) != 0) {
						dataMemory[INTCON] |= 1;
					}
					
				}
			}else {
				if (portStat.get(RB5)) {
					portStat.set(RB5, false);
					if ((dataMemory[TRISB] & (1 << 5)) != 0) {
						dataMemory[INTCON] |= 1;
					}
					
				}
			}
			if ((dataMemory[PORTB] & (1 << 6)) != 0) {
				if (!portStat.get(RB6)) {
					portStat.set(RB6, true);
					if ((dataMemory[TRISB] & (1 << 6)) != 0) {
						dataMemory[INTCON] |= 1;
					}
					
				}
			}else {
				if (portStat.get(RB6)) {
					portStat.set(RB6, false);
					if ((dataMemory[TRISB] & (1 << 6)) != 0) {
						dataMemory[INTCON] |= 1;
					}
					
				}
			}
			if ((dataMemory[PORTB] & (1 << 7)) != 0) {
				if (!portStat.get(RB7)) {
					portStat.set(RB7, true);
					if ((dataMemory[TRISB] & (1 << 7)) != 0) {
						dataMemory[INTCON] |= 1;
					}
					
				}
			}else {
				if (portStat.get(RB7)) {
					portStat.set(RB7, false);
					if ((dataMemory[TRISB] & (1 << 7)) != 0) {
						dataMemory[INTCON] |= 1;
					}
					
				}
			}
			//##############################################
		}
	
	
	protected void setrb0Rising(boolean newValue) {
		this.rb0Rising = newValue;
	}
	
	protected boolean getrb0Rising() {
		return rb0Rising;
	}
	

}

package PicController;

import static PicController.Controller.*;

public class CPUClock extends Thread
{

	public void run()
	{
		// Things to run
		while (true) {
			try {
				Thread.sleep(threadSpeed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// Runs only if the User presses the button RUN
			Simulator_UI.checkBreakpoint(selectedRow);
			Simulator_UI.updatePorts();
			if (!breakPointReached) {
				if (clockRunning) {
					file.executeOperation();
					timer.incTimer();
					setCycles();
					ports.checkPorts();
					Simulator_UI.updateUI();
					if (stepping) {
						clockRunning = false;
					}
				}
			}else {
				clockRunning = false;
				stepping = false;
			}
			
		}
	}

	public CPUClock()
	{
		start();
	}
}

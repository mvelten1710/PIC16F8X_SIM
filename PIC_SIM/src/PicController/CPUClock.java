package PicController;

import static PicController.Controller.*;

public class CPUClock extends Thread
{

	

	public void run()
	{

		System.out.println("Operation Thread started...");
		// Things to run
		while (true) {
			try {
				Thread.sleep(threadSpeed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// Runs only if the User presses the button RUN
			if (!Simulator_UI.getBreakpointPos(selectedRow)) {
				if (clockRunning) {
					file.executeOperation();
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

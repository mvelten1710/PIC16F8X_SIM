package PicController;

import static PicController.Controller.clockRunning;
import static PicController.Controller.file;
import static PicController.Controller.stepping;

public class CPUClock extends Thread
{

	private long threadSpeed;

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
			if (clockRunning) {
				file.executeOperation();
				Simulator_UI.updateUI();
				if (stepping) {
					clockRunning = false;
				}

			}
		}
	}

	public CPUClock()
	{
		threadSpeed = 500;
		start();
	}

	// Can only be set when no LST File is selected
	public void setThreadSpeed(long speed)
	{
		this.threadSpeed = speed;
	}
}

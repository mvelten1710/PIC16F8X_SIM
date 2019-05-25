package PicController;

import static PicController.Controller.clockRunning;
import static PicController.Controller.file;
import static PicController.Controller.stepping;

public class CPUClock extends Thread
{
	public void run()
	{

		System.out.println("Operation Thread started...");
		// Things to run
		while (true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// Runs only if the User presses the button RUN
			if (clockRunning) {
				file.executeOperation();
				if (stepping) {
					clockRunning = false;
				}

			}
		}
	}

	public CPUClock()
	{
		start();
	}
}
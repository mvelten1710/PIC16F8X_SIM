public class CPUClock extends Controller
{
	public void run()
	{

		System.out.println("Thread " + getName() + " started...");
		// Things to run
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// Runs only if the User presses the button RUN
			if (clockRunning) {
				file.executeOperation();
				System.out.println("Operation executed...");

			}
		}
	}

	public CPUClock()
	{
		start();
	}
}

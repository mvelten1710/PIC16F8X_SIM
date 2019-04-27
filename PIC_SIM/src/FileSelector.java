import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class FileSelector extends Simulator_UI
{

	private JFrame selectorWindow;

	private String filePath;

	/**
	 * Launch the application.
	 */
	public void start()
	{
		EventQueue.invokeLater(new Runnable() {
			public void run()
			{
				try {
					FileSelector window = new FileSelector();
					window.selectorWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FileSelector()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		selectorWindow = new JFrame();
		selectorWindow.setBounds(100, 100, 695, 450);
		selectorWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JFileChooser fileChooser = new JFileChooser();
		int rueckgabewert = fileChooser.showOpenDialog(null);
		selectorWindow.getContentPane().add(fileChooser, BorderLayout.CENTER);
		if (rueckgabewert == JFileChooser.APPROVE_OPTION) {
			filePath = fileChooser.getSelectedFile().getAbsolutePath();
			setFilePath();
			try {
				executeFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setTextArea();

		}

	}

	public void setFilePath()
	{
		parser.setFilePath(filePath);
	}

	public String getFilePath()
	{
		return filePath;
	}

}

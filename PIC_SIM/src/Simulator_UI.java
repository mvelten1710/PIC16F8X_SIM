import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

public class Simulator_UI extends Controller
{

	private JFrame frmPicSimulator;

	/**
	 * Launch the application.
	 */
	public void startWindow()
	{
		EventQueue.invokeLater(new Runnable() {
			public void run()
			{
				try {
					Simulator_UI window = new Simulator_UI();
					window.frmPicSimulator.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Simulator_UI()
	{
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */

	private JTextArea textArea;

	private JTable table;

	private DefaultTableModel model = new DefaultTableModel();

	private void initialize()
	{
		frmPicSimulator = new JFrame();
		frmPicSimulator.setTitle("PIC Simulator");
		frmPicSimulator.setBounds(100, 100, 928, 573);
		frmPicSimulator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPicSimulator.getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("Open File");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				// Opens the new Window to import the LST file
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.showOpenDialog(null);
				int rueckgabewert = fileChooser.showOpenDialog(null);
				if (rueckgabewert == JFileChooser.APPROVE_OPTION) {
					String filePath = fileChooser.getSelectedFile()
							.getAbsolutePath();
					try {
						readFile(filePath);
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (model.getRowCount() != 0) {
						// removeContent();
						setContent();
					} else {
						setContent();
					}
					// textArea.setText(parser.getContent());

				}

			}
		});
		btnNewButton.setBounds(383, 250, 89, 23);
		frmPicSimulator.getContentPane().add(btnNewButton);

		JButton btnStart = new JButton("Start");
		btnStart.setBounds(482, 250, 89, 23);
		frmPicSimulator.getContentPane().add(btnStart);

		JButton btnStep = new JButton("Step");
		btnStep.setBounds(579, 250, 89, 23);
		frmPicSimulator.getContentPane().add(btnStep);

		JButton btnStop = new JButton("Stop");
		btnStop.setBounds(678, 250, 89, 23);
		frmPicSimulator.getContentPane().add(btnStop);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(383, 284, 519, 239);
		frmPicSimulator.getContentPane().add(scrollPane);
		scrollPane.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		textArea = new JTextArea();
		textArea.setBounds(0, 0, 200, 200);
		// frmPicSimulator.getContentPane().add(textArea);

		// TODO DONT FORGET
		table = new JTable(model);
		table.setBounds(10, 515, 323, -230);
		frmPicSimulator.getContentPane().add(table);
		scrollPane.setViewportView(table);
		model.addColumn("LST FILE");

	}

	private void setContent()
	{
		for (int i = 0; i < parser.getContent().length; i++) {
			if (parser.getContent()[i] != null) {
				model.insertRow(i, new Object[] { parser.getContent()[i] });
			}
		}
	}

	// private void removeContent()
	// {
	// int index = 0;
	// if (model.getRowCount() != 0) {
	// while (model.getRowCount() > 0) {
	// model.removeRow(index);
	// index++;
	// }
	// }
	// }
}

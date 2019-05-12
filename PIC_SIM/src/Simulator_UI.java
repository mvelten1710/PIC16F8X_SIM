import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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

	private JTable table;

	private DefaultTableModel model;

	private void initialize()
	{
		frmPicSimulator = new JFrame();
		frmPicSimulator.setTitle("PIC Simulator");
		frmPicSimulator.setBounds(100, 100, 928, 573);
		frmPicSimulator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPicSimulator.getContentPane().setLayout(null);

		JButton btnStart = new JButton("RUN");
		btnStart.setEnabled(false);
		btnStart.setBounds(482, 250, 89, 23);
		frmPicSimulator.getContentPane().add(btnStart);
		btnStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				clockRunning = true;
				System.out.println(clockRunning);
			}
		});

		JButton btnStep = new JButton("STEP");
		btnStep.setBounds(579, 250, 89, 23);
		frmPicSimulator.getContentPane().add(btnStep);

		JButton btnStop = new JButton("RESET");
		btnStop.setBounds(678, 250, 89, 23);
		frmPicSimulator.getContentPane().add(btnStop);

		JButton btnNewButton = new JButton("LOAD");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				// Opens the new Window to import the LST file
				JFileChooser fileChooser = new JFileChooser();
				int rueckgabewert = fileChooser.showOpenDialog(null);
				if (rueckgabewert == JFileChooser.APPROVE_OPTION) {
					try {
						file.readFile(
								fileChooser.getSelectedFile().getAbsolutePath());
						if (model.getRowCount() > 0) {
							removeContent();
						}
						setContent();
						btnStart.setEnabled(true);
					} catch (IOException e) {
						e.printStackTrace();
					}

					// textArea.setText(parser.getContent());

				}

			}
		});
		btnNewButton.setBounds(383, 250, 89, 23);
		frmPicSimulator.getContentPane().add(btnNewButton);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(383, 284, 519, 239);
		frmPicSimulator.getContentPane().add(scrollPane);
		scrollPane.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		table = new JTable();
		model = (DefaultTableModel) table.getModel();
		table.setBounds(10, 515, 323, -230);
		frmPicSimulator.getContentPane().add(table);
		model.addColumn("LST FILE");
		scrollPane.setViewportView(table);

	}

	private void setContent()
	{
		for (int i = 0; i < parser.getContent().length; i++) {
			if (parser.getContent()[i] != null) {
				model.insertRow(i, new Object[] { parser.getContent()[i] });
			}
		}
	}

	private void removeContent()
	{
		// TODO Code for the removal of the rows
	}
}

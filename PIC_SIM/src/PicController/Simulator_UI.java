package PicController;

import static PicController.Controller.allCleared;
import static PicController.Controller.clockRunning;
import static PicController.Controller.file;
import static PicController.Controller.pIndex;
import static PicController.Controller.parser;
import static PicController.Controller.programMemory;
import static PicController.Controller.stepping;

import java.awt.Checkbox;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class Simulator_UI
{
	// TODO Delete all println after work is finished

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

	private DefaultTableModel model = new DefaultTableModel();

	private JTable table;

	private JButton btnStart, btnStep, btnReset;

	private void initialize()
	{
		frmPicSimulator = new JFrame();
		frmPicSimulator.setTitle("PIC Simulator");
		frmPicSimulator.setBounds(100, 100, 928, 573);
		frmPicSimulator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPicSimulator.getContentPane().setLayout(null);

		btnStart = new JButton("RUN");
		btnStart.setEnabled(false);
		btnStart.setBounds(482, 250, 89, 23);
		frmPicSimulator.getContentPane().add(btnStart);
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				clockRunning = true;
				if (stepping) {
					stepping = false;
				}
				System.out.println("Clock: " + clockRunning);
			}
		});

		btnStep = new JButton("STEP");
		btnStep.setEnabled(false);
		btnStep.setBounds(579, 250, 89, 23);
		frmPicSimulator.getContentPane().add(btnStep);
		btnStep.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// Stops after each Operation and waits for next click
				clockRunning = true;
				if (!stepping) {
					stepping = true;
				}
			}
		});

		btnReset = new JButton("RESET");
		btnReset.setEnabled(false);
		btnReset.setBounds(678, 250, 89, 23);
		frmPicSimulator.getContentPane().add(btnReset);
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				cleanUp();
			}
		});

		JButton btnNewButton = new JButton("LOAD");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				// Opens the new Window to import the LST file
				// TODO Delete Path afterwards
				JFileChooser fileChooser = new JFileChooser(
						"F:\\Workspace\\Simulator\\PIC16F8X_SIM\\PIC_SIM\\LST Files");
				int rueckgabewert = fileChooser.showOpenDialog(null);
				if (rueckgabewert == JFileChooser.APPROVE_OPTION) {
					if (!allCleared) {
						cleanUp();
					}
					try {
						file.readFile(
								fileChooser.getSelectedFile().getAbsolutePath());
						setContent();

						btnStart.setEnabled(true);
						btnStep.setEnabled(true);
						btnReset.setEnabled(true);
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
		scrollPane.setBounds(383, 281, 515, 242);
		frmPicSimulator.getContentPane().add(scrollPane);
		scrollPane.setHorizontalScrollBarPolicy(
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		table = new JTable(model);
		table.setRowSelectionAllowed(false);
		table.setFont(new Font("Monospaced", Font.PLAIN, 11));
		scrollPane.setViewportView(table);
		TableColumnModel columnModel = table.getColumnModel();
		model.addColumn("BP");
		model.addColumn("LST FILE");
		// TODO Maybe do it dynamically later?
		columnModel.getColumn(0).setPreferredWidth(45);
		columnModel.getColumn(1).setPreferredWidth(470);
	}

	private void setContent()
	{
		for (int i = 0; i < parser.getContent().length; i++) {
			if (parser.getContent()[i] != null) {
				// model.insertRow(i, new Object[] { parser.getContent()[i] });
				model.setRowCount(i + 1);
				model.setValueAt(parser.getContent()[i], i, 1);
				// TODO Add Checkboxes
				model.setValueAt(new Checkbox(), i, 0);
			}
		}
		model.fireTableDataChanged();
		allCleared = false;
	}

	private void cleanUp()
	{
		// Clears everything (Stack, ProgramMemory, TableContent etc.)
		clockRunning = false;
		stepping = false;
		pIndex = 0;
		btnStart.setEnabled(false);
		btnStep.setEnabled(false);
		btnReset.setEnabled(false);

		// TODO Clear DataMemory and Stack later
		parser.clearContent();
		// TODO Maybe auslagern?
		for (int i = 0; i < programMemory.length; i++) {
			if (programMemory[i] != 0) {
				programMemory[i] = 0;
			}
		}

		// JTable cleanup
		model.setRowCount(0);

		allCleared = true;
		System.out.println("\n######ALL-CLEARED######\n");
	}

}

package PicController;

import static PicController.Controller.W;
import static PicController.Controller.allCleared;
import static PicController.Controller.clockRunning;
import static PicController.Controller.dataMemory;
import static PicController.Controller.f;
import static PicController.Controller.file;
import static PicController.Controller.pIndex;
import static PicController.Controller.parser;
import static PicController.Controller.programMemory;
import static PicController.Controller.stack;
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
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
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

	private DefaultTableModel parserModel = new DefaultTableModel();

	private static DefaultTableModel fRegisterModel = new DefaultTableModel();

	private JTable parserTable, fRegisterTable;

	private JButton btnStart, btnStep, btnReset;

	private static JLabel wRegister;

	private void initialize()
	{
		frmPicSimulator = new JFrame();
		frmPicSimulator.getContentPane()
				.setFont(new Font("Tahoma", Font.PLAIN, 13));
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

		JScrollPane parserScroll = new JScrollPane();
		parserScroll.setBounds(383, 281, 515, 242);
		frmPicSimulator.getContentPane().add(parserScroll);
		parserScroll.setHorizontalScrollBarPolicy(
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		parserScroll
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		parserTable = new JTable(parserModel);
		parserTable.setFillsViewportHeight(true);
		parserTable.setRowSelectionAllowed(false);
		parserTable.setFont(new Font("Monospaced", Font.PLAIN, 12));
		parserScroll.setViewportView(parserTable);
		TableColumnModel parserColumnModel = parserTable.getColumnModel();
		parserModel.addColumn("BP");
		parserModel.addColumn("LST FILE");

		parserColumnModel.getColumn(0).setPreferredWidth(45);
		parserColumnModel.getColumn(1).setPreferredWidth(470);

		JLabel lblFregister = new JLabel("F-Register");
		lblFregister.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFregister.setHorizontalAlignment(SwingConstants.LEFT);
		lblFregister.setBounds(10, 11, 208, 23);
		frmPicSimulator.getContentPane().add(lblFregister);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 208, 215);
		frmPicSimulator.getContentPane().add(scrollPane);
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		fRegisterTable = new JTable(fRegisterModel);
		fRegisterTable.setFillsViewportHeight(true);
		fRegisterTable.setRowSelectionAllowed(false);
		fRegisterTable.setFont(new Font("Monospaced", Font.PLAIN, 13));
		scrollPane.setViewportView(fRegisterTable);
		TableColumnModel fRegisterColumnModel = fRegisterTable.getColumnModel();
		fRegisterModel.addColumn("-");
		fRegisterModel.addColumn("-");
		fRegisterModel.addColumn("-");
		// TODO Fix: Twice as much columns as needed!
		fRegisterColumnModel.getColumn(0).setPreferredWidth(50);
		fRegisterColumnModel.getColumn(1).setPreferredWidth(50);
		fRegisterColumnModel.getColumn(2).setPreferredWidth(50);

		JLabel lblWregister = new JLabel("W-Register");
		lblWregister.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblWregister.setBounds(10, 262, 74, 23);
		frmPicSimulator.getContentPane().add(lblWregister);

		wRegister = new JLabel("-");
		wRegister.setBounds(94, 263, 50, 23);
		frmPicSimulator.getContentPane().add(wRegister);

	}

	/* ####################START-OF-FUNCTIONS#################### */

	private void setContent()
	{
		for (int i = 0; i < parser.getContent().length; i++) {
			if (parser.getContent()[i] != null) {
				// model.insertRow(i, new Object[] { parser.getContent()[i] });
				parserModel.setRowCount(i + 1);
				parserModel.setValueAt(parser.getContent()[i], i, 1);
				// TODO Add Checkboxes
				parserModel.setValueAt(new Checkbox(), i, 0);
			}
		}
		updateFRegister();
		fRegisterModel.fireTableDataChanged();
		parserModel.fireTableDataChanged();
		allCleared = false;
	}

	private static void updateFRegister()
	{
		/* ###############F-REGISTER############### */
		int row = 0;
		int column = 0;
		for (int i = 0; i < f.length; i++) {
			fRegisterModel.setRowCount(row + 1);
			fRegisterModel.setValueAt(f[i], row, column);
			if (column == 2) {
				row++;
				column = 0;
			} else {
				column++;
			}
		}
		row = 0;
		column = 0;
		/* ######################################## */
	}

	private static void updateWRegister()
	{
		wRegister.setText(Integer.toHexString(W) + "h");
	}

	public static void updateUI()
	{
		updateFRegister();
		updateWRegister();
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

		for (int i = 0; i < dataMemory.length; i++) {
			if (dataMemory[i] != 0) {
				dataMemory[i] = 0;
			}
		}
		for (int i = 0; i < stack.length; i++) {
			if (stack[i] != 0) {
				stack[i] = 0;
			}
		}
		parser.clearContent();
		for (int i = 0; i < programMemory.length; i++) {
			if (programMemory[i] != 0) {
				programMemory[i] = 0;
			}
		}

		// Parser Table cleanup
		parserModel.setRowCount(0);

		// F-Register Table cleanup
		fRegisterModel.setRowCount(0);

		allCleared = true;
		System.out.println("\n######ALL-CLEARED######\n");
	}
}

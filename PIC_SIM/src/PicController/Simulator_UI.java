package PicController;

import static PicController.Controller.*;

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
import javax.swing.table.TableCellRenderer;
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

	private static DefaultTableModel parserModel;

	private static DefaultTableModel fRegisterModel;

	private static JTable parserTable, fRegisterTable;

	private JButton btnStart, btnStep, btnReset;

	private static JLabel wRegister, cFlag, dcFlag, zFlag, toFlag, pdFlag;
	
	private static OwnCellRenderer parserCellRenderer;

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
				JFileChooser fileChooser = new JFileChooser("C:\\Users\\gudda\\OneDrive - stud.hs-offenburg.de\\Hochschule\\SS2019\\Rechnerarchitekturen\\PIC16F8X_SIM\\PIC_SIM\\LST Files");
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
		
		/* ####################START-OF-PARSER_TABLE#################### */

		JScrollPane parserScroll = new JScrollPane();
		parserScroll.setBounds(383, 281, 515, 242);
		frmPicSimulator.getContentPane().add(parserScroll);
		parserScroll.setHorizontalScrollBarPolicy(
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		parserScroll
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		parserTable = new JTable(parserModel = new DefaultTableModel());
		
		parserTable.setFillsViewportHeight(true);
		parserTable.setRowSelectionAllowed(false);
		parserTable.setFont(new Font("Monospaced", Font.PLAIN, 12));
		parserScroll.setViewportView(parserTable);
		TableColumnModel parserColumnModel = parserTable.getColumnModel();

		parserModel.addColumn("BP");
		parserModel.addColumn("LST FILE");

		parserColumnModel.getColumn(0).setPreferredWidth(45);
		parserColumnModel.getColumn(1).setPreferredWidth(470);
		
		/* ####################END-OF-PARSER_TABLE#################### */

		/* ####################START-OF-F_REGISTER#################### */
		
		JLabel lblFregister = new JLabel("F-Register");
		lblFregister.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFregister.setHorizontalAlignment(SwingConstants.LEFT);
		lblFregister.setBounds(10, 11, 208, 23);
		frmPicSimulator.getContentPane().add(lblFregister);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 240, 215);
		frmPicSimulator.getContentPane().add(scrollPane);
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		fRegisterTable = new JTable(fRegisterModel = new DefaultTableModel());
		fRegisterTable.setFillsViewportHeight(true);
		fRegisterTable.setRowSelectionAllowed(false);
		fRegisterTable.setFont(new Font("Monospaced", Font.PLAIN, 13));
		scrollPane.setViewportView(fRegisterTable);
		TableColumnModel fRegisterColumnModel = fRegisterTable.getColumnModel();
		for (int i = 0; i < 6; i++) {
			fRegisterModel.addColumn("-");
		}
		/* ####################END-OF-F_REGISTER#################### */


		JLabel lblWregister = new JLabel("W-Register");
		lblWregister.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblWregister.setBounds(10, 262, 74, 23);
		frmPicSimulator.getContentPane().add(lblWregister);

		wRegister = new JLabel("-");
		wRegister.setBounds(94, 263, 50, 23);
		frmPicSimulator.getContentPane().add(wRegister);
		
		JLabel lblFlags = new JLabel("Flags");
		lblFlags.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFlags.setBounds(10, 302, 74, 23);
		frmPicSimulator.getContentPane().add(lblFlags);
		
		JLabel lblZflag = new JLabel("Z-Flag");
		lblZflag.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblZflag.setBounds(10, 408, 48, 23);
		frmPicSimulator.getContentPane().add(lblZflag);
		
		JLabel lblCflag = new JLabel("C-Flag");
		lblCflag.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCflag.setBounds(10, 336, 48, 26);
		frmPicSimulator.getContentPane().add(lblCflag);
		
		JLabel lblDcflag = new JLabel("DC-Flag");
		lblDcflag.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDcflag.setBounds(10, 373, 48, 24);
		frmPicSimulator.getContentPane().add(lblDcflag);
		
		JLabel lblToflag = new JLabel("TO-Flag");
		lblToflag.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblToflag.setBounds(10, 442, 48, 26);
		frmPicSimulator.getContentPane().add(lblToflag);
		
		JLabel lblPdflag = new JLabel("PD-Flag");
		lblPdflag.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPdflag.setBounds(10, 479, 48, 23);
		frmPicSimulator.getContentPane().add(lblPdflag);
		
		cFlag = new JLabel("-");
		cFlag.setBounds(78, 343, 48, 14);
		frmPicSimulator.getContentPane().add(cFlag);
		
		dcFlag = new JLabel("-");
		dcFlag.setBounds(78, 379, 48, 14);
		frmPicSimulator.getContentPane().add(dcFlag);
		
		zFlag = new JLabel("-");
		zFlag.setBounds(78, 413, 48, 14);
		frmPicSimulator.getContentPane().add(zFlag);
		
		toFlag = new JLabel("-");
		toFlag.setBounds(78, 449, 48, 14);
		frmPicSimulator.getContentPane().add(toFlag);
		
		pdFlag = new JLabel("-");
		pdFlag.setBounds(78, 484, 48, 14);
		frmPicSimulator.getContentPane().add(pdFlag);

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
		parserTable.getColumnModel().getColumn(1).setCellRenderer(parserCellRenderer = new OwnCellRenderer());
		parserCellRenderer.getTableCellRendererComponent(parserTable, 20, true, false, 17, 1);
		allCleared = false;
	}

	private static void updateFRegister()
	{
		/* ###############F-REGISTER############### */
		int row = 0;
		int column = 0;
		for (int i = 0; i < f.length; i++) {
			fRegisterModel.setRowCount(row + 1);
			fRegisterModel.setValueAt(Integer.toHexString(f[i])+"h", row, column);
			if (column == 5) {
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
	
	private static void updateFlagLabels()
	{
		cFlag.setText(Integer.toString(getFlag(CFLAG)));
		dcFlag.setText(Integer.toString(getFlag(DCFLAG)));
		zFlag.setText(Integer.toString(getFlag(ZFLAG)));
		toFlag.setText(Integer.toString(getFlag(TOFLAG)));
		pdFlag.setText(Integer.toString(getFlag(PDFLAG)));
	}
	
	//TODO Update Parser selected row of operation and change name of method
	private static void rofl()
	{
		
	}

	public static void updateUI()
	{
		//All Updates to the UI should be placed here!
		updateFRegister();
		wRegister.setText(Integer.toHexString(W) + "h");
		updateFlagLabels();
		rofl();
		
	}
	
	private static void clearUI()
	{
		// Parser Table cleanup
		parserModel.setRowCount(0);
		// F-Register Table cleanup
		fRegisterModel.setRowCount(0);
		wRegister.setText("-");
		cFlag.setText("-");
		dcFlag.setText("-");
		zFlag.setText("-");
		toFlag.setText("-");
		pdFlag.setText("-");
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
		
		for (int i = 0; i < programMemory.length; i++) {
			if (programMemory[i] != 0) {
				programMemory[i] = 0;
			}
		}

		for (int i = 0; i < f.length; i++) {
			if (f[i] != 0) {
				f[i] = 0;
			}
		}
		
		W = 0;
		

		parser.clearContent();

		clearUI();
		allCleared = true;
	}
}

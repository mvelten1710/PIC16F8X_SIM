package PicController;

import static PicController.Controller.*;

import java.awt.Checkbox;
import java.awt.Color;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.JTextField;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;

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
	
	private JTextField enteredFrequ;

	private void initialize()
	{
		frmPicSimulator = new JFrame();
		frmPicSimulator.getContentPane()
				.setFont(new Font("Tahoma", Font.PLAIN, 13));
		frmPicSimulator.setTitle("PIC Simulator");
		frmPicSimulator.setBounds(100, 100, 980, 580);
		frmPicSimulator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPicSimulator.getContentPane().setLayout(null);
		frmPicSimulator.setResizable(false);

		btnStart = new JButton("START");
		btnStart.setEnabled(false);
		btnStart.setBounds(361, 249, 89, 23);
		frmPicSimulator.getContentPane().add(btnStart);
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (!clockRunning) {
					clockRunning = true;
					if (stepping) 
						stepping = false;
				}else {
					clockRunning = false;
					stepping = false;
				}
				
			}
		});

		btnStep = new JButton("STEP");
		btnStep.setEnabled(false);
		btnStep.setBounds(460, 249, 89, 23);
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
		btnReset.setBounds(559, 249, 89, 23);
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
				JFileChooser fileChooser = new JFileChooser("C:\\Users\\todus\\git\\PIC16F8X_SIM\\PIC_SIM\\LST Files");
				int rueckgabewert = fileChooser.showOpenDialog(null);
				if (rueckgabewert == JFileChooser.APPROVE_OPTION) {
					if (!allCleared) {
						cleanUp();
					}
					try {
						file.readFile(
								fileChooser.getSelectedFile().getAbsolutePath());
						setContent();
						//New LineSelector for the File
						lineSelector = new LineSelector();
						selectedRow = lineSelector.opBegin;
						parserTable.getCellRenderer(selectedRow, 1);
						parserModel.fireTableDataChanged();
						
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
		btnNewButton.setBounds(262, 249, 89, 23);
		frmPicSimulator.getContentPane().add(btnNewButton);
		
		/* ####################START-OF-PARSER_TABLE#################### */

		JScrollPane parserScroll = new JScrollPane();
		parserScroll.setBounds(260, 281, 704, 259);
		frmPicSimulator.getContentPane().add(parserScroll);
		parserScroll
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		

		parserTable = new JTable(parserModel = new DefaultTableModel() 
		{
			private static final long serialVersionUID = 1L;

			public Class<?> getColumnClass(int columnIndex)
			{
				if(columnIndex == 0)
					return Boolean.class;
				return String.class;
			}
		})
		{
			private static final long serialVersionUID = 1L;

			public TableCellRenderer getCellRenderer(int row, int column)
			{
				DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
				
				if(row == selectedRow)
					renderer.setBackground(Color.CYAN);
				
				return renderer;
			}
		};
		parserTable.setFillsViewportHeight(true);
		parserTable.setRowSelectionAllowed(false);
		parserTable.setFont(new Font("Monospaced", Font.PLAIN, 12));
		parserScroll.setViewportView(parserTable);
		TableColumnModel parserColumnModel = parserTable.getColumnModel();

		parserModel.addColumn("BP");
		parserModel.addColumn("LST FILE");

		parserColumnModel.getColumn(0).setPreferredWidth(45);
		parserColumnModel.getColumn(1).setPreferredWidth(659);
		
		/* ####################END-OF-PARSER_TABLE#################### */

		/* ####################START-OF-F_REGISTER#################### */
		
		JLabel lblFregister = new JLabel("F-Register");
		lblFregister.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblFregister.setHorizontalAlignment(SwingConstants.LEFT);
		lblFregister.setBounds(10, 12, 208, 23);
		frmPicSimulator.getContentPane().add(lblFregister);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 240, 197);
		frmPicSimulator.getContentPane().add(scrollPane);
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		fRegisterTable = new JTable(fRegisterModel = new DefaultTableModel());
		fRegisterTable.setFillsViewportHeight(true);
		fRegisterTable.setRowSelectionAllowed(false);
		fRegisterTable.setFont(new Font("Monospaced", Font.PLAIN, 13));
		scrollPane.setViewportView(fRegisterTable);
		for (int i = 0; i < 6; i++) {
			fRegisterModel.addColumn("-");
		}
		/* ####################END-OF-F_REGISTER#################### */


		JLabel lblWregister = new JLabel("W-Register");
		lblWregister.setHorizontalAlignment(SwingConstants.CENTER);
		lblWregister.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblWregister.setBounds(10, 248, 74, 23);
		frmPicSimulator.getContentPane().add(lblWregister);

		wRegister = new JLabel("-");
		wRegister.setFont(new Font("Tahoma", Font.BOLD, 12));
		wRegister.setBounds(94, 248, 59, 23);
		frmPicSimulator.getContentPane().add(wRegister);
		
		JLabel lblFlags = new JLabel("Flags");
		lblFlags.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblFlags.setBounds(10, 452, 74, 23);
		frmPicSimulator.getContentPane().add(lblFlags);
		
		JLabel lblZflag = new JLabel("Z");
		lblZflag.setHorizontalAlignment(SwingConstants.CENTER);
		lblZflag.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblZflag.setBounds(70, 481, 20, 23);
		frmPicSimulator.getContentPane().add(lblZflag);
		
		JLabel lblCflag = new JLabel("C");
		lblCflag.setHorizontalAlignment(SwingConstants.CENTER);
		lblCflag.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCflag.setBounds(10, 479, 20, 26);
		frmPicSimulator.getContentPane().add(lblCflag);
		
		JLabel lblDcflag = new JLabel("DC");
		lblDcflag.setHorizontalAlignment(SwingConstants.CENTER);
		lblDcflag.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDcflag.setBounds(40, 480, 20, 24);
		frmPicSimulator.getContentPane().add(lblDcflag);
		
		JLabel lblToflag = new JLabel("TO");
		lblToflag.setHorizontalAlignment(SwingConstants.CENTER);
		lblToflag.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblToflag.setBounds(100, 479, 20, 26);
		frmPicSimulator.getContentPane().add(lblToflag);
		
		JLabel lblPdflag = new JLabel("PD");
		lblPdflag.setHorizontalAlignment(SwingConstants.CENTER);
		lblPdflag.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPdflag.setBounds(130, 481, 20, 23);
		frmPicSimulator.getContentPane().add(lblPdflag);
		
		cFlag = new JLabel("-");
		cFlag.setFont(new Font("Tahoma", Font.BOLD, 12));
		cFlag.setHorizontalAlignment(SwingConstants.CENTER);
		cFlag.setBounds(10, 509, 20, 14);
		frmPicSimulator.getContentPane().add(cFlag);
		
		dcFlag = new JLabel("-");
		dcFlag.setFont(new Font("Tahoma", Font.BOLD, 12));
		dcFlag.setHorizontalAlignment(SwingConstants.CENTER);
		dcFlag.setBounds(40, 509, 20, 14);
		frmPicSimulator.getContentPane().add(dcFlag);
		
		zFlag = new JLabel("-");
		zFlag.setFont(new Font("Tahoma", Font.BOLD, 12));
		zFlag.setHorizontalAlignment(SwingConstants.CENTER);
		zFlag.setBounds(70, 509, 20, 14);
		frmPicSimulator.getContentPane().add(zFlag);
		
		toFlag = new JLabel("-");
		toFlag.setFont(new Font("Tahoma", Font.BOLD, 12));
		toFlag.setHorizontalAlignment(SwingConstants.CENTER);
		toFlag.setBounds(100, 509, 20, 14);
		frmPicSimulator.getContentPane().add(toFlag);
		
		pdFlag = new JLabel("-");
		pdFlag.setFont(new Font("Tahoma", Font.BOLD, 12));
		pdFlag.setHorizontalAlignment(SwingConstants.CENTER);
		pdFlag.setBounds(130, 509, 20, 14);
		frmPicSimulator.getContentPane().add(pdFlag);
		
		enteredFrequ = new JTextField();
		enteredFrequ.setBounds(173, 250, 79, 20);
		frmPicSimulator.getContentPane().add(enteredFrequ);
		enteredFrequ.setColumns(10);
		
		JLabel frequency = new JLabel("1000");
		frequency.setFont(new Font("Tahoma", Font.PLAIN, 9));
		frequency.setBounds(224, 270, 28, 14);
		frmPicSimulator.getContentPane().add(frequency);
		
		JLabel uiMessage = new JLabel("");
		uiMessage.setFont(new Font("Tahoma", Font.ITALIC, 10));
		uiMessage.setForeground(Color.RED);
		uiMessage.setBounds(658, 253, 306, 14);
		frmPicSimulator.getContentPane().add(uiMessage);
		
		JButton acceptFrequ = new JButton("ENTER");
		acceptFrequ.setBounds(173, 292, 79, 23);
		frmPicSimulator.getContentPane().add(acceptFrequ);
		acceptFrequ.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(!enteredFrequ.getText().isEmpty()) 
				{
					try 
					{
						threadSpeed = Long.parseLong(enteredFrequ.getText());
						uiMessage.setText("");
						
					} catch (NumberFormatException e2) 
					{
						uiMessage.setText("ERROR: ONLY NUMBERS ARE ALLOWED FOR THE FREQUENCY!");
					}
					frequency.setText("" + threadSpeed);
					enteredFrequ.setText("");
				}
			}
		});
		
		JLabel lblNewLabel = new JLabel("Instruction/ms");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(173, 235, 77, 14);
		frmPicSimulator.getContentPane().add(lblNewLabel);
		
		JLabel lblLabel = new JLabel("Memory");
		lblLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblLabel.setBounds(260, 16, 74, 14);
		frmPicSimulator.getContentPane().add(lblLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Frequency:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblNewLabel_1.setBounds(175, 270, 52, 14);
		frmPicSimulator.getContentPane().add(lblNewLabel_1);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(262, 36, 702, 197);
		frmPicSimulator.getContentPane().add(tabbedPane);

	}

	/* ####################START-OF-FUNCTIONS#################### */

	private void setContent()
	{
		for (int i = 0; i < parser.getContent().length; i++) {
			if (parser.getContent()[i] != null) {
				// model.insertRow(i, new Object[] { parser.getContent()[i] });
				parserModel.setRowCount(i + 1);
				parserModel.setValueAt(parser.getContent()[i], i, 1);
				parserModel.setValueAt(false, i, 0);
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
	
	
	private static void updateSelectedRow()
	{
		selectedRow = lineSelector.nextRow();
		parserTable.getCellRenderer(selectedRow, 1);
		parserModel.fireTableDataChanged();
	}

	public static void updateUI()
	{
		//All Updates to the UI should be placed here!
		updateFRegister();
		wRegister.setText(Integer.toHexString(W) + "h");
		updateFlagLabels();
		updateSelectedRow();
		
	}
	
	public static boolean getBreakpointPos(int index) {
		if(clockRunning) {
			if ((boolean) parserTable.getValueAt(index, 0)) {
				return true;
			}
		}
		//If no Breakpoint is found
		return false;
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
		breakPointReached = false;
		clockRunning = false;
		stepping = false;
		pIndex = 0;
		selectedRow = -1;
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

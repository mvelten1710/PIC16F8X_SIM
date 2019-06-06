package PicController;

import static PicController.Controller.*;

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

	private static DefaultTableModel parserModel, gprRegisterModel, sfrRegisterModel, stackModel;

	private static JTable parserTable, gprRegisterTable;

	private static JButton btnStart;

	private JButton btnStep;

	private JButton btnReset;
	
	private static JLabel uiMessage;

	private static JLabel wRegister, lZaehler;
	
	private JTextField enteredFrequ;
	private JTable stackTable;
	private JTable sfrRegisterTable;

	private void initialize()
	{
		frmPicSimulator = new JFrame();
		frmPicSimulator.getContentPane()
				.setFont(new Font("Tahoma", Font.PLAIN, 13));
		frmPicSimulator.setTitle("PIC Simulator");
		frmPicSimulator.setBounds(100, 100, 1055, 645);
		frmPicSimulator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPicSimulator.setResizable(false);
		frmPicSimulator.getContentPane().setLayout(null);
		
		/* ####################START-OF-PARSER_TABLE#################### */

		JScrollPane parserScroll = new JScrollPane();
		parserScroll.setBounds(335, 314, 704, 290);
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

			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
				boolean lol = false;
				if(column == 0) {
					lol = true;
				}
				return lol;
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



		JLabel lblWregister = new JLabel("W-Register");
		lblWregister.setBounds(10, 230, 74, 23);
		lblWregister.setHorizontalAlignment(SwingConstants.CENTER);
		lblWregister.setFont(new Font("Tahoma", Font.BOLD, 13));
		frmPicSimulator.getContentPane().add(lblWregister);

		wRegister = new JLabel("-");
		wRegister.setBounds(94, 230, 74, 23);
		wRegister.setFont(new Font("Tahoma", Font.BOLD, 12));
		frmPicSimulator.getContentPane().add(wRegister);
		
		enteredFrequ = new JTextField();
		enteredFrequ.setBounds(460, 232, 79, 20);
		frmPicSimulator.getContentPane().add(enteredFrequ);
		enteredFrequ.setColumns(10);
		
		JLabel frequency = new JLabel("800");
		frequency.setBounds(511, 218, 28, 14);
		frequency.setFont(new Font("Tahoma", Font.PLAIN, 9));
		frmPicSimulator.getContentPane().add(frequency);
		
		JButton acceptFrequ = new JButton("ENTER");
		acceptFrequ.setBounds(549, 231, 79, 23);
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
		
		JLabel lblLabel = new JLabel("Memory");
		lblLabel.setBounds(10, 264, 94, 14);
		lblLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		frmPicSimulator.getContentPane().add(lblLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Frequency:");
		lblNewLabel_1.setBounds(460, 218, 52, 14);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 9));
		frmPicSimulator.getContentPane().add(lblNewLabel_1);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 289, 315, 315);
		frmPicSimulator.getContentPane().add(tabbedPane);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		tabbedPane.addTab("Stack", null, scrollPane_1, null);
		
		stackTable = new JTable(stackModel = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		});
		stackTable.setRowSelectionAllowed(false);
		scrollPane_1.setViewportView(stackTable);
		stackModel.addColumn("###STACK###");
		
		/* ####################START-OF-RAM#################### */
		JScrollPane scrollPane_2 = new JScrollPane();
		tabbedPane.addTab("GPR", null, scrollPane_2, null);
		gprRegisterTable = new JTable(gprRegisterModel = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;
	
			@Override
			public boolean isCellEditable(int row, int column) {
				//all cells false
				return false;
			}
		});
		scrollPane_2.setViewportView(gprRegisterTable);
		gprRegisterTable.setFillsViewportHeight(true);
		gprRegisterTable.setRowSelectionAllowed(false);
		gprRegisterTable.setFont(new Font("Monospaced", Font.PLAIN, 13));
		for (int i = 0; i < 6; i++) {
			gprRegisterModel.addColumn("-");
		}
		gprRegisterTable.setFont(new Font("Monospaced", Font.PLAIN, 13));
	

		
		JScrollPane scrollPane = new JScrollPane();
		tabbedPane.addTab("SFR", null, scrollPane, null);
		
		sfrRegisterTable = new JTable(sfrRegisterModel = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;
	
			@Override
			public boolean isCellEditable(int row, int column) {
				//all cells false
				return true;
			}
		});
		sfrRegisterModel.addColumn("ADRESS");
		sfrRegisterModel.addColumn("BANK0");
		sfrRegisterModel.addColumn("BANK1");
		sfrRegisterModel.addColumn("ADRESS");
		scrollPane.setViewportView(sfrRegisterTable);
		
		/* ####################END-OF-RAM#################### */
		
		JLabel lblLaufzeitzaehler = new JLabel("Laufzeitz\u00E4hler");
		lblLaufzeitzaehler.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblLaufzeitzaehler.setHorizontalAlignment(SwingConstants.LEFT);
		lblLaufzeitzaehler.setBounds(335, 235, 77, 14);
		frmPicSimulator.getContentPane().add(lblLaufzeitzaehler);
		
		lZaehler = new JLabel("0");
		lZaehler.setFont(new Font("Tahoma", Font.BOLD, 10));
		lZaehler.setHorizontalAlignment(SwingConstants.RIGHT);
		lZaehler.setBounds(376, 235, 40, 14);
		frmPicSimulator.getContentPane().add(lZaehler);
		
		JLabel lbls = new JLabel("\u00B5s");
		lbls.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lbls.setBounds(422, 235, 28, 14);
		frmPicSimulator.getContentPane().add(lbls);
		
		JPanel panel = new JPanel();
		panel.setBounds(335, 255, 388, 59);
		frmPicSimulator.getContentPane().add(panel);
		
				JButton btnNewButton = new JButton("LOAD");
				btnNewButton.setBounds(0, 32, 89, 23);
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
		
				btnStart = new JButton("START");
				btnStart.setBounds(99, 32, 89, 23);
				btnStart.setEnabled(false);
				btnStart.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e)
					{
						if (breakPointReached) {
							uiMessage.setText("ERROR: REMOVE BREAKPOINT FIRST!");
						}else {
							if (!clockRunning) {
								clockRunning = true;
								if (stepping) 
									stepping = false;
							}else {
								clockRunning = false;
								stepping = false;
								btnStart.setText("STOP");
							}
						}
					}
				});
		
				btnStep = new JButton("STEP");
				btnStep.setBounds(198, 32, 89, 23);
				btnStep.setEnabled(false);
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
				btnReset.setBounds(297, 32, 89, 23);
				btnReset.setEnabled(false);
				btnReset.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e)
					{
						cleanUp();
					}
				});
		panel.setLayout(null);
		panel.add(btnNewButton);
		panel.add(btnStart);
		panel.add(btnStep);
		panel.add(btnReset);
		
		JLabel lblLstFile = new JLabel("LST File");
		lblLstFile.setBounds(0, 11, 386, 14);
		panel.add(lblLstFile);
		lblLstFile.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		uiMessage = new JLabel("");
		uiMessage.setBounds(724, 289, 315, 23);
		frmPicSimulator.getContentPane().add(uiMessage);
		uiMessage.setFont(new Font("Tahoma", Font.ITALIC, 10));
		uiMessage.setForeground(Color.RED);
		
	}

	/* ####################START-OF-FUNCTIONS#################### */

	private void setContent()
	{
		//PARSER CONTENT
		for (int i = 0; i < parser.getContent().length; i++) {
			if (parser.getContent()[i] != null) {
				parserModel.setRowCount(i + 1);
				parserModel.setValueAt(parser.getContent()[i], i, 1);
				parserModel.setValueAt(false, i, 0);
			}
		}
		
		updateStack();
		updateGPR();
		updateSFR();
		gprRegisterModel.fireTableDataChanged();
		parserModel.fireTableDataChanged();
		allCleared = false;
	}
	
	private static void updateStack()
	{
		//STACK CONTENT
		for (int i = 0; i < stack.length; i++) {
			stackModel.setRowCount(i + 1);
			stackModel.setValueAt(Integer.toHexString(stack[i]) + "h", i, 0);	
		}
	}

	private static void updateGPR()
	{
		/* ###############F-REGISTER############### */
		int row = 0;
		int column = 0;
		for (int i = 0x0C; i < 0x50; i++) {
			gprRegisterModel.setRowCount(row + 1);
			gprRegisterModel.setValueAt(Integer.toHexString(dataMemory[i]) + "h", row, column);
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
	
	private static boolean firstTime = true;
	private static void updateSFR()
	{
		sfrRegisterModel.setRowCount(0x0C);
		if (firstTime) {
			for (int i = 0x00; i < 0x0C; i++) {
				sfrRegisterModel.setValueAt(String.format("%02X", i) + "h", i, 0);
				sfrRegisterModel.setValueAt("8" + Integer.toHexString(i) + "h", i, 3);
			}
			firstTime = false;
		}
		for (int i = 0x00; i < 0x0C; i++) {
			sfrRegisterModel.setValueAt(Integer.toBinaryString(dataMemory[i]) + "b", i, 1);
			sfrRegisterModel.setValueAt(Integer.toBinaryString(dataMemory[i]) + "b", i, 2);
		}
		
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
		updateStack();
		updateGPR();
		updateSFR();
		wRegister.setText(Integer.toHexString(W) + "h");
		lZaehler.setText(Integer.toString(runtime));
		updateSelectedRow();
	}
	
	public static void checkBreakpoint(int index) {
		if (parserModel.getRowCount() != 0) {
			if ((boolean) parserTable.getValueAt(index, 0)) {
				btnStart.setText("START");
				breakPointReached = true;
				return;
			}
			//If no Breakpoint is found
			breakPointReached = false;
			return;
		}
		
	}
	
	private static void clearUI()
	{
		// All Table cleanup & others
		parserModel.setRowCount(0);
		stackModel.setRowCount(0);
		gprRegisterModel.setRowCount(0);
		sfrRegisterModel.setRowCount(0);
		uiMessage.setText("");
		wRegister.setText("-");
		lZaehler.setText("0");
	}

	private void cleanUp()
	{
		// Clears everything (Stack, ProgramMemory, TableContent etc.)
		firstTime = true;
		breakPointReached = false;
		clockRunning = false;
		stepping = false;
		pIndex = 0;
		selectedRow = 0;
		runtime = 0;
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
		
		W = 0;
		

		parser.clearContent();

		clearUI();
		allCleared = true;
	}
}

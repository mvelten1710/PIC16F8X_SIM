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
import javax.swing.JRadioButton;

public class Simulator_UI
{
	// TODO Delete all println after work is finished

	private JFrame frmPicSimulator;

	/**
	 * Launch the application.
	 */
	public void startWindow(Simulator_UI newWindow)
	{
		EventQueue.invokeLater(new Runnable() {
			public void run()
			{
				try {
					newWindow.frmPicSimulator.setVisible(true);
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
	
	private JTable sfrRegisterTable, stackTable;

	private static JButton btnStart;

	private JButton btnStep, btnReset;
	
	private static JLabel uiMessage, wRegister;
	
	private JTextField enteredFrequ;
	
	protected static JLabel frequency;
	
	private static JRadioButton rdbtnRa, rdbtnRa_1, rdbtnRa_2, rdbtnRa_3, rdbtnRa_4, rdbtnRb, rdbtnRb_1, rdbtnRb_2, rdbtnRb_3, rdbtnRb_4, rdbtnRb_5, rdbtnRb_6, rdbtnRb_7;
	
	private static JRadioButton rdbtnPortChangesToggle, rdbtnActivatePorts;
	
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
		frmPicSimulator.addKeyListener(new OwnKeyListener());
		frmPicSimulator.setFocusable(true);
		
		/* ####################START-OF-PARSER_TABLE#################### */

		JScrollPane parserScroll = new JScrollPane();
		parserScroll.setBounds(10, 315, 704, 290);
		frmPicSimulator.getContentPane().add(parserScroll);
		
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
		lblWregister.setBounds(805, 173, 74, 23);
		lblWregister.setHorizontalAlignment(SwingConstants.CENTER);
		lblWregister.setFont(new Font("Tahoma", Font.BOLD, 13));
		frmPicSimulator.getContentPane().add(lblWregister);

		wRegister = new JLabel("-");
		wRegister.setHorizontalAlignment(SwingConstants.CENTER);
		wRegister.setBounds(805, 203, 74, 23);
		wRegister.setFont(new Font("Tahoma", Font.BOLD, 12));
		frmPicSimulator.getContentPane().add(wRegister);
		
		enteredFrequ = new JTextField();
		enteredFrequ.setBounds(410, 281, 79, 23);
		frmPicSimulator.getContentPane().add(enteredFrequ);
		enteredFrequ.setColumns(10);
		
		frequency = new JLabel("800");
		frequency.setHorizontalAlignment(SwingConstants.CENTER);
		frequency.setBounds(410, 269, 79, 14);
		frequency.setFont(new Font("Tahoma", Font.PLAIN, 9));
		frmPicSimulator.getContentPane().add(frequency);
		
		JButton acceptFrequ = new JButton("ENTER");
		acceptFrequ.addKeyListener(new OwnKeyListener());
		acceptFrequ.setBounds(499, 281, 79, 23);
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
		
		uiMessage = new JLabel("");
		uiMessage.setBounds(724, 218, 315, 23);
		frmPicSimulator.getContentPane().add(uiMessage);
		uiMessage.setFont(new Font("Tahoma", Font.ITALIC, 10));
		uiMessage.setForeground(Color.RED);
		
		JScrollPane stackScroll = new JScrollPane();
		stackScroll.setBounds(805, 11, 74, 151);
		frmPicSimulator.getContentPane().add(stackScroll);
		
		stackTable = new JTable(stackModel = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		});
		stackTable.setFont(new Font("Monospaced", Font.PLAIN, 13));
		stackTable.setRowSelectionAllowed(false);
		stackScroll.setViewportView(stackTable);
		stackModel.addColumn("STACK");
		
		JScrollPane gprScroll = new JScrollPane();
		gprScroll.setBounds(585, 11, 210, 215);
		frmPicSimulator.getContentPane().add(gprScroll);
		gprRegisterTable = new JTable(gprRegisterModel = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;
	
			@Override
			public boolean isCellEditable(int row, int column) {
				//all cells false
				return false;
			}
		});
		
		gprScroll.setViewportView(gprRegisterTable);
		gprRegisterTable.setFillsViewportHeight(true);
		gprRegisterTable.setRowSelectionAllowed(false);
		gprRegisterTable.setFont(new Font("Monospaced", Font.PLAIN, 13));
		for (int i = 0; i < 6; i++) {
			gprRegisterModel.addColumn("-");
		}

		/* ####################START-OF-RAM#################### */
			JScrollPane sfrScroll = new JScrollPane();
			sfrScroll.setBounds(265, 11, 310, 215);
			frmPicSimulator.getContentPane().add(sfrScroll);
			
			sfrRegisterTable = new JTable(sfrRegisterModel = new DefaultTableModel(){
				private static final long serialVersionUID = 1L;
				
				@Override
				public boolean isCellEditable(int row, int column) {
					//all cells false
					return false;
				}
			});
			
			sfrRegisterTable.setFont(new Font("Monospaced", Font.PLAIN, 13));
			sfrScroll.setViewportView(sfrRegisterTable);
			
			JLabel lblLstFile = new JLabel("LST File");
			lblLstFile.setBounds(10, 256, 114, 14);
			frmPicSimulator.getContentPane().add(lblLstFile);
			lblLstFile.setFont(new Font("Tahoma", Font.BOLD, 13));
			
			JButton btnNewButton = new JButton("LOAD");
			btnNewButton.addKeyListener(new OwnKeyListener());
			btnNewButton.setBounds(10, 281, 89, 23);
			frmPicSimulator.getContentPane().add(btnNewButton);
			
					btnStart = new JButton("START");
					btnStart.addKeyListener(new OwnKeyListener());
					btnStart.setBounds(109, 281, 89, 23);
					frmPicSimulator.getContentPane().add(btnStart);
					btnStart.setEnabled(false);
					
							btnStep = new JButton("STEP");
							btnStep.addKeyListener(new OwnKeyListener());
							btnStep.setBounds(208, 282, 89, 23);
							frmPicSimulator.getContentPane().add(btnStep);
							btnStep.setEnabled(false);
							
									btnReset = new JButton("RESET");
									btnReset.addKeyListener(new OwnKeyListener());
									btnReset.setBounds(307, 281, 89, 23);
									frmPicSimulator.getContentPane().add(btnReset);
									btnReset.setEnabled(false);
									
									JLabel lblPorts = new JLabel("Ports");
									lblPorts.setHorizontalAlignment(SwingConstants.CENTER);
									lblPorts.setFont(new Font("Tahoma", Font.BOLD, 13));
									lblPorts.setBounds(10, 12, 245, 14);
									frmPicSimulator.getContentPane().add(lblPorts);
									
									JLabel lblPortA = new JLabel("Port A");
									lblPortA.setHorizontalAlignment(SwingConstants.CENTER);
									lblPortA.setBounds(10, 37, 109, 14);
									frmPicSimulator.getContentPane().add(lblPortA);
									
									rdbtnRa = new JRadioButton("RA0");
									rdbtnRa.setBounds(10, 58, 109, 23);
									rdbtnRa.addKeyListener(new OwnKeyListener());
									frmPicSimulator.getContentPane().add(rdbtnRa);
									
									rdbtnRa_1 = new JRadioButton("RA1");
									rdbtnRa_1.setBounds(10, 84, 109, 23);
									rdbtnRa_1.addKeyListener(new OwnKeyListener());
									frmPicSimulator.getContentPane().add(rdbtnRa_1);
									
									rdbtnRa_2 = new JRadioButton("RA2");
									rdbtnRa_2.setBounds(10, 110, 109, 23);
									rdbtnRa_2.addKeyListener(new OwnKeyListener());
									frmPicSimulator.getContentPane().add(rdbtnRa_2);
									
									rdbtnRa_3 = new JRadioButton("RA3");
									rdbtnRa_3.setBounds(10, 136, 109, 23);
									rdbtnRa_3.addKeyListener(new OwnKeyListener());
									frmPicSimulator.getContentPane().add(rdbtnRa_3);
									
									rdbtnRa_4 = new JRadioButton("RA4");
									rdbtnRa_4.setBounds(10, 162, 109, 23);
									rdbtnRa_4.addKeyListener(new OwnKeyListener());
									frmPicSimulator.getContentPane().add(rdbtnRa_4);
									
									JLabel lblPortB = new JLabel("Port B");
									lblPortB.setHorizontalAlignment(SwingConstants.CENTER);
									lblPortB.setBounds(156, 37, 109, 14);
									frmPicSimulator.getContentPane().add(lblPortB);
									
									rdbtnRb = new JRadioButton("RB0");
									rdbtnRb.setBounds(156, 58, 109, 23);
									rdbtnRb.addKeyListener(new OwnKeyListener());
									frmPicSimulator.getContentPane().add(rdbtnRb);
									
									rdbtnRb_1 = new JRadioButton("RB1");
									rdbtnRb_1.setBounds(156, 84, 109, 23);
									rdbtnRb_1.addKeyListener(new OwnKeyListener());
									frmPicSimulator.getContentPane().add(rdbtnRb_1);
									
									rdbtnRb_2 = new JRadioButton("RB2");
									rdbtnRb_2.setBounds(156, 110, 109, 23);
									rdbtnRb_2.addKeyListener(new OwnKeyListener());
									frmPicSimulator.getContentPane().add(rdbtnRb_2);
									
									rdbtnRb_3 = new JRadioButton("RB3");
									rdbtnRb_3.setBounds(156, 136, 109, 23);
									rdbtnRb_3.addKeyListener(new OwnKeyListener());
									frmPicSimulator.getContentPane().add(rdbtnRb_3);
									
									rdbtnRb_4 = new JRadioButton("RB4");
									rdbtnRb_4.setBounds(156, 162, 109, 23);
									rdbtnRb_4.addKeyListener(new OwnKeyListener());
									frmPicSimulator.getContentPane().add(rdbtnRb_4);
									
									rdbtnRb_5 = new JRadioButton("RB5");
									rdbtnRb_5.setBounds(156, 188, 109, 23);
									rdbtnRb_5.addKeyListener(new OwnKeyListener());
									frmPicSimulator.getContentPane().add(rdbtnRb_5);
									
									rdbtnRb_6 = new JRadioButton("RB6");
									rdbtnRb_6.setBounds(156, 218, 109, 23);
									rdbtnRb_6.addKeyListener(new OwnKeyListener());
									frmPicSimulator.getContentPane().add(rdbtnRb_6);
									
									rdbtnRb_7 = new JRadioButton("RB7");
									rdbtnRb_7.setBounds(156, 244, 109, 23);
									rdbtnRb_7.addKeyListener(new OwnKeyListener());
									frmPicSimulator.getContentPane().add(rdbtnRb_7);
									
									rdbtnPortChangesToggle = new JRadioButton("Port (A)");
									rdbtnPortChangesToggle.setFont(new Font("Tahoma", Font.BOLD, 11));
									rdbtnPortChangesToggle.setBounds(10, 188, 144, 23);
									rdbtnPortChangesToggle.addKeyListener(new OwnKeyListener());
									frmPicSimulator.getContentPane().add(rdbtnPortChangesToggle);
									
									rdbtnActivatePorts = new JRadioButton("Activate Ports");
									rdbtnActivatePorts.setFont(new Font("Tahoma", Font.BOLD, 11));
									rdbtnActivatePorts.setBounds(10, 218, 144, 23);
									rdbtnActivatePorts.addKeyListener(new OwnKeyListener());
									frmPicSimulator.getContentPane().add(rdbtnActivatePorts);
									
									btnReset.addActionListener(new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent e)
										{
											cleanUp();
										}
									});
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
								}
							}
						}
					});
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

			sfrRegisterModel.addColumn("ADRESS");
			sfrRegisterModel.addColumn("BANK0");
			sfrRegisterModel.addColumn("BANK1");
			sfrRegisterModel.addColumn("ADRESS");
			rdbtnRa.setEnabled(false);
			rdbtnRa_1.setEnabled(false);
			rdbtnRa_2.setEnabled(false);
			rdbtnRa_3.setEnabled(false);
			rdbtnRa_4.setEnabled(false);
			rdbtnRb.setEnabled(false);
			rdbtnRb_1.setEnabled(false);
			rdbtnRb_2.setEnabled(false);
			rdbtnRb_3.setEnabled(false);
			rdbtnRb_4.setEnabled(false);
			rdbtnRb_5.setEnabled(false);
			rdbtnRb_6.setEnabled(false);
			rdbtnRb_7.setEnabled(false);
			rdbtnPortChangesToggle.setEnabled(false);
			
			/* ####################END-OF-RAM#################### */
		
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
				sfrRegisterModel.setValueAt("8" + String.format("%01X", i) + "h", i, 3);
			}
			firstTime = false;
		}
		for (int i = 0x00; i < 0x0C; i++) {
			sfrRegisterModel.setValueAt(Integer.toBinaryString(dataMemory[i]) + "b", i, 1);
		}
		int helper = 0;
		for (int i = 0x80; i < 0x8C; i++) {
			sfrRegisterModel.setValueAt(Integer.toBinaryString(dataMemory[i]) + "b", helper++, 2);
		}
		helper = 0;
	}
	
	
	private static void updateSelectedRow()
	{
		selectedRow = lineSelector.nextRow();
		parserTable.getCellRenderer(selectedRow, 1);
		parserModel.fireTableDataChanged();
	}

	private static boolean canPortChange = false;
	protected static void updatePorts() {
		if (rdbtnActivatePorts.isSelected()) {
			rdbtnRa.setEnabled(true);
			rdbtnRa_1.setEnabled(true);
			rdbtnRa_2.setEnabled(true);
			rdbtnRa_3.setEnabled(true);
			rdbtnRa_4.setEnabled(true);
			rdbtnRb.setEnabled(true);
			rdbtnRb_1.setEnabled(true);
			rdbtnRb_2.setEnabled(true);
			rdbtnRb_3.setEnabled(true);
			rdbtnRb_4.setEnabled(true);
			rdbtnRb_5.setEnabled(true);
			rdbtnRb_6.setEnabled(true);
			rdbtnRb_7.setEnabled(true);
			rdbtnPortChangesToggle.setEnabled(true);
			canPortChange = true;
		}else {
			rdbtnRa.setEnabled(false);
			rdbtnRa_1.setEnabled(false);
			rdbtnRa_2.setEnabled(false);
			rdbtnRa_3.setEnabled(false);
			rdbtnRa_4.setEnabled(false);
			rdbtnRb.setEnabled(false);
			rdbtnRb_1.setEnabled(false);
			rdbtnRb_2.setEnabled(false);
			rdbtnRb_3.setEnabled(false);
			rdbtnRb_4.setEnabled(false);
			rdbtnRb_5.setEnabled(false);
			rdbtnRb_6.setEnabled(false);
			rdbtnRb_7.setEnabled(false);
			rdbtnPortChangesToggle.setEnabled(false);
			canPortChange = false;
		}
		if (canPortChange) {
			if(!rdbtnPortChangesToggle.isSelected()) {
				rdbtnPortChangesToggle.setText("Port (A)");
				/*######PORT A*/
				if (rdbtnRa.isSelected()) {
					dataMemory[PORTA] |= 1;
				}else {
					dataMemory[PORTA] &= ~1;
				}
				if (rdbtnRa_1.isSelected()) {
					dataMemory[PORTA] |= (1 << 1);
				}else {
					dataMemory[PORTA] &= ~(1 << 1);
				}
				if (rdbtnRa_2.isSelected()) {
					dataMemory[PORTA] |= (1 << 2);
				}else {
					dataMemory[PORTA] &= ~(1 << 2);
				}
				if (rdbtnRa_3.isSelected()) {
					dataMemory[PORTA] |= (1 << 3);
				}else {
					dataMemory[PORTA] &= ~(1 << 3);
				}
				if (rdbtnRa_4.isSelected()) {
					dataMemory[PORTA] |= (1 << 4);
				}else {
					dataMemory[PORTA] &= ~(1 << 4);
				}
			}else {
				/*PORTB*/
				rdbtnPortChangesToggle.setText("Port (B)");
				if (rdbtnRb.isSelected()) {
					dataMemory[PORTB] |= 1;
				}else {
					dataMemory[PORTB] &= ~1;
				}
				if (rdbtnRb_1.isSelected()) {
					dataMemory[PORTB] |= (1 << 1);
				}else {
					dataMemory[PORTB] &= ~(1 << 1);
				}
				if (rdbtnRb_2.isSelected()) {
					dataMemory[PORTB] |= (1 << 2);
				}else {
					dataMemory[PORTB] &= ~(1 << 2);
				}
				if (rdbtnRb_3.isSelected()) {
					dataMemory[PORTB] |= (1 << 3);
				}else {
					dataMemory[PORTB] &= ~(1 << 3);
				}
				if (rdbtnRb_4.isSelected()) {
					dataMemory[PORTB] |= (1 << 4);
				}else {
					dataMemory[PORTB] &= ~(1 << 4);
				}
				if (rdbtnRb_5.isSelected()) {
					dataMemory[PORTB] |= (1 << 5);
				}else {
					dataMemory[PORTB] &= ~(1 << 5);
				}
				if (rdbtnRb_6.isSelected()) {
					dataMemory[PORTB] |= (1 << 6);
				}else {
					dataMemory[PORTB] &= ~(1 << 6);
				}
				if (rdbtnRb_7.isSelected()) {
					dataMemory[PORTB] |= (1 << 7);
				}else {
					dataMemory[PORTB] &= ~(1 << 7);
				}
			}
		}
	}
	
	public static void updateUI()
	{
		//All Updates to the UI should be placed here!
		updateStack();
		updateGPR();
		updateSFR();
		updatePorts();
		wRegister.setText(Integer.toHexString(W) + "h");
		updateSelectedRow();
	}
	
	public static void checkBreakpoint(int index) {
		if (parserModel.getRowCount() != 0) {
			if ((boolean) parserTable.getValueAt(index, 0)) {
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
		timer.resetTimer();
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

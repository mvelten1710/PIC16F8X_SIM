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
	
	protected static JLabel frequency, label;
	
	private static JRadioButton rdbtnRa, rdbtnRa_1, rdbtnRa_2, rdbtnRa_3, rdbtnRa_4, rdbtnRb, rdbtnRb_1, rdbtnRb_2, rdbtnRb_3, rdbtnRb_4, rdbtnRb_5, rdbtnRb_6, rdbtnRb_7;
	
	private static JRadioButton rdbtnPortChangesToggle, rdbtnActivatePorts, rdbtnIntrb;
	
	private static JRadioButton ra0IO, ra1IO, ra2IO, ra3IO, ra4IO, rb0IO, rb1IO, rb2IO, rb3IO, rb4IO, rb5IO, rb6IO, rb7IO;
	
	private void initialize()
	{
		frmPicSimulator = new JFrame();
		frmPicSimulator.getContentPane()
				.setFont(new Font("Tahoma", Font.PLAIN, 13));
		frmPicSimulator.setTitle("PIC Simulator");
		frmPicSimulator.setBounds(100, 100, 900, 644);
		frmPicSimulator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPicSimulator.setResizable(false);
		frmPicSimulator.getContentPane().setLayout(null);
		frmPicSimulator.addKeyListener(new OwnKeyListener());
		frmPicSimulator.setFocusable(true);
		
		/* ####################START-OF-PARSER_TABLE#################### */

		JScrollPane parserScroll = new JScrollPane();
		parserScroll.setBounds(10, 315, 869, 290);
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
		parserColumnModel.getColumn(0).setPreferredWidth(40);
		parserColumnModel.getColumn(1).setPreferredWidth(860);

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
		
		frequency = new JLabel("500");
		frequency.setHorizontalAlignment(SwingConstants.CENTER);
		frequency.setBounds(410, 269, 79, 14);
		frequency.setFont(new Font("Tahoma", Font.BOLD, 9));
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
		uiMessage.setBounds(588, 281, 296, 23);
		frmPicSimulator.getContentPane().add(uiMessage);
		uiMessage.setFont(new Font("Tahoma", Font.ITALIC, 9));
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
			TableColumnModel sfrColumnModel = sfrRegisterTable.getColumnModel();
			
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
									rdbtnRa.setBounds(10, 58, 74, 23);
									rdbtnRa.addKeyListener(new OwnKeyListener());
									frmPicSimulator.getContentPane().add(rdbtnRa);
									
									rdbtnRa_1 = new JRadioButton("RA1");
									rdbtnRa_1.setBounds(10, 84, 74, 23);
									rdbtnRa_1.addKeyListener(new OwnKeyListener());
									frmPicSimulator.getContentPane().add(rdbtnRa_1);
									
									rdbtnRa_2 = new JRadioButton("RA2");
									rdbtnRa_2.setBounds(10, 110, 74, 23);
									rdbtnRa_2.addKeyListener(new OwnKeyListener());
									frmPicSimulator.getContentPane().add(rdbtnRa_2);
									
									rdbtnRa_3 = new JRadioButton("RA3");
									rdbtnRa_3.setBounds(10, 136, 74, 23);
									rdbtnRa_3.addKeyListener(new OwnKeyListener());
									frmPicSimulator.getContentPane().add(rdbtnRa_3);
									
									rdbtnRa_4 = new JRadioButton("RA4");
									rdbtnRa_4.setBounds(10, 162, 74, 23);
									rdbtnRa_4.addKeyListener(new OwnKeyListener());
									frmPicSimulator.getContentPane().add(rdbtnRa_4);
									
									JLabel lblPortB = new JLabel("Port B");
									lblPortB.setHorizontalAlignment(SwingConstants.CENTER);
									lblPortB.setBounds(156, 37, 109, 14);
									frmPicSimulator.getContentPane().add(lblPortB);
									
									rdbtnRb = new JRadioButton("RB0");
									rdbtnRb.setBounds(156, 58, 68, 23);
									rdbtnRb.addKeyListener(new OwnKeyListener());
									frmPicSimulator.getContentPane().add(rdbtnRb);
									
									rdbtnRb_1 = new JRadioButton("RB1");
									rdbtnRb_1.setBounds(156, 84, 68, 23);
									rdbtnRb_1.addKeyListener(new OwnKeyListener());
									frmPicSimulator.getContentPane().add(rdbtnRb_1);
									
									rdbtnRb_2 = new JRadioButton("RB2");
									rdbtnRb_2.setBounds(156, 110, 68, 23);
									rdbtnRb_2.addKeyListener(new OwnKeyListener());
									frmPicSimulator.getContentPane().add(rdbtnRb_2);
									
									rdbtnRb_3 = new JRadioButton("RB3");
									rdbtnRb_3.setBounds(156, 136, 68, 23);
									rdbtnRb_3.addKeyListener(new OwnKeyListener());
									frmPicSimulator.getContentPane().add(rdbtnRb_3);
									
									rdbtnRb_4 = new JRadioButton("RB4");
									rdbtnRb_4.setBounds(156, 162, 68, 23);
									rdbtnRb_4.addKeyListener(new OwnKeyListener());
									frmPicSimulator.getContentPane().add(rdbtnRb_4);
									
									rdbtnRb_5 = new JRadioButton("RB5");
									rdbtnRb_5.setBounds(156, 188, 68, 23);
									rdbtnRb_5.addKeyListener(new OwnKeyListener());
									frmPicSimulator.getContentPane().add(rdbtnRb_5);
									
									rdbtnRb_6 = new JRadioButton("RB6");
									rdbtnRb_6.setBounds(156, 218, 68, 23);
									rdbtnRb_6.addKeyListener(new OwnKeyListener());
									frmPicSimulator.getContentPane().add(rdbtnRb_6);
									
									rdbtnRb_7 = new JRadioButton("RB7");
									rdbtnRb_7.setBounds(156, 244, 68, 23);
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
					JFileChooser fileChooser = new JFileChooser();
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

			
			sfrRegisterModel.addColumn("-");
			sfrRegisterModel.addColumn("BANK0");
			sfrRegisterModel.addColumn("BANK1");
			sfrRegisterModel.addColumn("-");
			sfrColumnModel.getColumn(0).setPreferredWidth(30);
			sfrColumnModel.getColumn(1).setPreferredWidth(125);
			sfrColumnModel.getColumn(2).setPreferredWidth(125);
			sfrColumnModel.getColumn(3).setPreferredWidth(30);
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
			
			JLabel lblFrequency = new JLabel("Frequency");
			lblFrequency.setFont(new Font("Tahoma", Font.PLAIN, 10));
			lblFrequency.setHorizontalAlignment(SwingConstants.CENTER);
			lblFrequency.setBounds(410, 256, 79, 14);
			frmPicSimulator.getContentPane().add(lblFrequency);
			
			JLabel lblLaufzeit = new JLabel("Laufzeit: ");
			lblLaufzeit.setHorizontalAlignment(SwingConstants.CENTER);
			lblLaufzeit.setFont(new Font("Tahoma", Font.PLAIN, 10));
			lblLaufzeit.setBounds(499, 257, 79, 14);
			frmPicSimulator.getContentPane().add(lblLaufzeit);
			
			label = new JLabel("0");
			label.setFont(new Font("Tahoma", Font.BOLD, 9));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(499, 269, 79, 14);
			frmPicSimulator.getContentPane().add(label);
			
			rdbtnIntrb = new JRadioButton("INT(RB0) Rising");
			rdbtnIntrb.setFont(new Font("Tahoma", Font.PLAIN, 10));
			rdbtnIntrb.setBounds(265, 233, 109, 23);
			frmPicSimulator.getContentPane().add(rdbtnIntrb);
			
			ra0IO = new JRadioButton("");
			ra0IO.setHorizontalAlignment(SwingConstants.CENTER);
			ra0IO.setBounds(86, 58, 38, 23);
			frmPicSimulator.getContentPane().add(ra0IO);
			
			ra1IO = new JRadioButton("");
			ra1IO.setHorizontalAlignment(SwingConstants.CENTER);
			ra1IO.setBounds(86, 84, 38, 23);
			frmPicSimulator.getContentPane().add(ra1IO);
			
			ra2IO = new JRadioButton("");
			ra2IO.setHorizontalAlignment(SwingConstants.CENTER);
			ra2IO.setBounds(86, 110, 38, 23);
			frmPicSimulator.getContentPane().add(ra2IO);
			
			ra3IO = new JRadioButton("");
			ra3IO.setHorizontalAlignment(SwingConstants.CENTER);
			ra3IO.setBounds(86, 136, 38, 23);
			frmPicSimulator.getContentPane().add(ra3IO);
			
			ra4IO = new JRadioButton("");
			ra4IO.setHorizontalAlignment(SwingConstants.CENTER);
			ra4IO.setBounds(86, 162, 38, 23);
			frmPicSimulator.getContentPane().add(ra4IO);
			
			rb0IO = new JRadioButton("");
			rb0IO.setHorizontalAlignment(SwingConstants.CENTER);
			rb0IO.setBounds(226, 58, 33, 23);
			frmPicSimulator.getContentPane().add(rb0IO);
			
			rb1IO = new JRadioButton("");
			rb1IO.setHorizontalAlignment(SwingConstants.CENTER);
			rb1IO.setBounds(226, 84, 33, 23);
			frmPicSimulator.getContentPane().add(rb1IO);
			
			rb2IO = new JRadioButton("");
			rb2IO.setHorizontalAlignment(SwingConstants.CENTER);
			rb2IO.setBounds(226, 110, 33, 23);
			frmPicSimulator.getContentPane().add(rb2IO);
			
			rb3IO = new JRadioButton("");
			rb3IO.setHorizontalAlignment(SwingConstants.CENTER);
			rb3IO.setBounds(226, 136, 33, 23);
			frmPicSimulator.getContentPane().add(rb3IO);
			
			rb4IO = new JRadioButton("");
			rb4IO.setHorizontalAlignment(SwingConstants.CENTER);
			rb4IO.setBounds(226, 162, 33, 23);
			frmPicSimulator.getContentPane().add(rb4IO);
			
			rb5IO = new JRadioButton("");
			rb5IO.setHorizontalAlignment(SwingConstants.CENTER);
			rb5IO.setBounds(226, 188, 33, 23);
			frmPicSimulator.getContentPane().add(rb5IO);
			
			rb6IO = new JRadioButton("");
			rb6IO.setHorizontalAlignment(SwingConstants.CENTER);
			rb6IO.setBounds(226, 218, 33, 23);
			frmPicSimulator.getContentPane().add(rb6IO);
			
			rb7IO = new JRadioButton("");
			rb7IO.setHorizontalAlignment(SwingConstants.CENTER);
			rb7IO.setBounds(226, 244, 33, 23);
			frmPicSimulator.getContentPane().add(rb7IO);
			
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
		//Selector if RB0 should activate interrupt on
		//falling or rising flank
		if (!rdbtnIntrb.isSelected()) {
			rdbtnIntrb.setText("INT(RB0) Rising"); 
			ports.setrb0Rising(true);
		}else {
			rdbtnIntrb.setText("INT(RB0) Falling");
			ports.setrb0Rising(false);
		}
		
		//Activates/Deactivates all Ports
		if (rdbtnActivatePorts.isSelected()) {
			
			if (!rdbtnPortChangesToggle.isSelected()) {
				//Activates/Deactivates PORTA
				rdbtnRa.setEnabled(true);
				ra0IO.setEnabled(true);
				rdbtnRa_1.setEnabled(true);
				ra1IO.setEnabled(true);
				rdbtnRa_2.setEnabled(true);
				ra2IO.setEnabled(true);
				rdbtnRa_3.setEnabled(true);
				ra3IO.setEnabled(true);
				rdbtnRa_4.setEnabled(true);
				ra4IO.setEnabled(true);
			}else {
				rdbtnRa.setEnabled(false);
				ra0IO.setEnabled(false);
				rdbtnRa_1.setEnabled(false);
				ra1IO.setEnabled(false);
				rdbtnRa_2.setEnabled(false);
				ra2IO.setEnabled(false);
				rdbtnRa_3.setEnabled(false);
				ra3IO.setEnabled(false);
				rdbtnRa_4.setEnabled(false);
				ra4IO.setEnabled(false);
			}
			
			if (rdbtnPortChangesToggle.isSelected()) {
				//Activates/Deactivates PORTB
				rdbtnRb.setEnabled(true);
				rb0IO.setEnabled(true);
				rdbtnRb_1.setEnabled(true);
				rb1IO.setEnabled(true);
				rdbtnRb_2.setEnabled(true);
				rb2IO.setEnabled(true);
				rdbtnRb_3.setEnabled(true);
				rb3IO.setEnabled(true);
				rdbtnRb_4.setEnabled(true);
				rb4IO.setEnabled(true);
				rdbtnRb_5.setEnabled(true);
				rb5IO.setEnabled(true);
				rdbtnRb_6.setEnabled(true);
				rb6IO.setEnabled(true);
				rdbtnRb_7.setEnabled(true);
				rb7IO.setEnabled(true);
			}else {
				rdbtnRb.setEnabled(false);
				rb0IO.setEnabled(false);
				rdbtnRb_1.setEnabled(false);
				rb1IO.setEnabled(false);
				rdbtnRb_2.setEnabled(false);
				rb2IO.setEnabled(false);
				rdbtnRb_3.setEnabled(false);
				rb3IO.setEnabled(false);
				rdbtnRb_4.setEnabled(false);
				rb4IO.setEnabled(false);
				rdbtnRb_5.setEnabled(false);
				rb5IO.setEnabled(false);
				rdbtnRb_6.setEnabled(false);
				rb6IO.setEnabled(false);
				rdbtnRb_7.setEnabled(false);
				rb7IO.setEnabled(false);
			}
			
			rdbtnPortChangesToggle.setEnabled(true);
			canPortChange = true;
		}else {
			rdbtnRa.setEnabled(false);
			ra0IO.setEnabled(false);
			rdbtnRa_1.setEnabled(false);
			ra1IO.setEnabled(false);
			rdbtnRa_2.setEnabled(false);
			ra2IO.setEnabled(false);
			rdbtnRa_3.setEnabled(false);
			ra3IO.setEnabled(false);
			rdbtnRa_4.setEnabled(false);
			ra4IO.setEnabled(false);
			rdbtnRb.setEnabled(false);
			rb0IO.setEnabled(false);
			rdbtnRb_1.setEnabled(false);
			rb1IO.setEnabled(false);
			rdbtnRb_2.setEnabled(false);
			rb2IO.setEnabled(false);
			rdbtnRb_3.setEnabled(false);
			rb3IO.setEnabled(false);
			rdbtnRb_4.setEnabled(false);
			rb4IO.setEnabled(false);
			rdbtnRb_5.setEnabled(false);
			rb5IO.setEnabled(false);
			rdbtnRb_6.setEnabled(false);
			rb6IO.setEnabled(false);
			rdbtnRb_7.setEnabled(false);
			rb7IO.setEnabled(false);
			
			rdbtnRa.setSelected(false);
			ra0IO.setSelected(false);
			rdbtnRa_1.setSelected(false);
			ra1IO.setSelected(false);
			rdbtnRa_2.setSelected(false);
			ra2IO.setSelected(false);
			rdbtnRa_3.setSelected(false);
			ra3IO.setSelected(false);
			rdbtnRa_4.setSelected(false);
			ra4IO.setSelected(false);
			rdbtnRb.setSelected(false);
			rb0IO.setSelected(false);
			rdbtnRb_1.setSelected(false);
			rb1IO.setSelected(false);
			rdbtnRb_2.setSelected(false);
			rb2IO.setSelected(false);
			rdbtnRb_3.setSelected(false);
			rb3IO.setSelected(false);
			rdbtnRb_4.setSelected(false);
			rb4IO.setSelected(false);
			rdbtnRb_5.setSelected(false);
			rb5IO.setSelected(false);
			rdbtnRb_6.setSelected(false);
			rb6IO.setSelected(false);
			rdbtnRb_7.setSelected(false);
			rb7IO.setSelected(false);
			rdbtnPortChangesToggle.setEnabled(false);
			rdbtnPortChangesToggle.setSelected(false);
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
		//##############TRISA##############
		if (ra0IO.isSelected()) {
			dataMemory[TRISA] |= 1;
		}else {
			dataMemory[TRISA] &= ~1;
		}
		if (ra1IO.isSelected()) {
			dataMemory[TRISA] |= (1 << 1);
		}else {
			dataMemory[TRISA] &= ~(1 << 1);
		}
		if (ra2IO.isSelected()) {
			dataMemory[TRISA] |= (1 << 2);
		}else {
			dataMemory[TRISA] &= ~(1 << 2);
		}
		if (ra3IO.isSelected()) {
			dataMemory[TRISA] |= (1 << 3);
		}else {
			dataMemory[TRISA] &= ~(1 << 3);
		}
		if (ra4IO.isSelected()) {
			dataMemory[TRISA] |= (1 << 4);
		}else {
			dataMemory[TRISA] &= ~(1 << 4);
		}
		//#################################
		
		//##############TRISB##############
		if (rb0IO.isSelected()) {
			dataMemory[TRISB] |= 1;
		}else {
			dataMemory[TRISB] &= ~1;
		}
		if (rb1IO.isSelected()) {
			dataMemory[TRISB] |= (1 << 1);
		}else {
			dataMemory[TRISB] &= ~(1 << 1);
		}
		if (rb2IO.isSelected()) {
			dataMemory[TRISB] |= (1 << 2);
		}else {
			dataMemory[TRISB] &= ~(1 << 2);
		}
		if (rb3IO.isSelected()) {
			dataMemory[TRISB] |= (1 << 3);
		}else {
			dataMemory[TRISB] &= ~(1 << 3);
		}
		if (rb4IO.isSelected()) {
			dataMemory[TRISB] |= (1 << 4);
		}else {
			dataMemory[TRISB] &= ~(1 << 4);
		}
		if (rb5IO.isSelected()) {
			dataMemory[TRISB] |= (1 << 5);
		}else {
			dataMemory[TRISB] &= ~(1 << 5);
		}
		if (rb6IO.isSelected()) {
			dataMemory[TRISB] |= (1 << 6);
		}else {
			dataMemory[TRISB] &= ~(1 << 6);
		}
		if (rb7IO.isSelected()) {
			dataMemory[TRISB] |= (1 << 7);
		}else {
			dataMemory[TRISB] &= ~(1 << 7);
		}
		//#################################
	}
	
	public static void updateUI()
	{
		//All Updates to the UI should be placed here!
		updateStack();
		updateGPR();
		updateSFR();
		updatePorts();
		label.setText(Integer.toString(runtime) + " Cycles");
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
		firstTimeInterrupt = true;
		breakPointReached = false;
		interruptReached = false;
		clockRunning = false;
		stepping = false;
		pIndex = 0;
		selectedRow = 0;
		runtime = 0;
		cycles = 0;
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
		
		lineSelector.cleanUp();
		parser.clearContent();

		clearUI();
		allCleared = true;
	}
}

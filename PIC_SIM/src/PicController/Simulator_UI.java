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
	
	private static JLabel frequency;
	
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
		frmPicSimulator.addKeyListener(new OwnKeyListener("JFrame Listener erstellt"));
		frmPicSimulator.setFocusable(true);
		
		/* ####################START-OF-PARSER_TABLE#################### */

		JScrollPane parserScroll = new JScrollPane();
		parserScroll.setBounds(335, 314, 704, 290);
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
		lblWregister.setBounds(965, 173, 74, 23);
		lblWregister.setHorizontalAlignment(SwingConstants.CENTER);
		lblWregister.setFont(new Font("Tahoma", Font.BOLD, 13));
		frmPicSimulator.getContentPane().add(lblWregister);

		wRegister = new JLabel("-");
		wRegister.setHorizontalAlignment(SwingConstants.CENTER);
		wRegister.setBounds(965, 202, 74, 23);
		wRegister.setFont(new Font("Tahoma", Font.BOLD, 12));
		frmPicSimulator.getContentPane().add(wRegister);
		
		enteredFrequ = new JTextField();
		enteredFrequ.setBounds(752, 283, 79, 20);
		frmPicSimulator.getContentPane().add(enteredFrequ);
		enteredFrequ.setColumns(10);
		
		frequency = new JLabel("800");
		frequency.setHorizontalAlignment(SwingConstants.CENTER);
		frequency.setBounds(752, 270, 79, 14);
		frequency.setFont(new Font("Tahoma", Font.PLAIN, 9));
		frmPicSimulator.getContentPane().add(frequency);
		
		JButton acceptFrequ = new JButton("ENTER");
		acceptFrequ.addKeyListener(new OwnKeyListener("AcceptButton Listener erstellt"));
		acceptFrequ.setBounds(841, 280, 79, 23);
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
		stackScroll.setBounds(965, 11, 74, 151);
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
		gprScroll.setBounds(745, 10, 210, 215);
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
			sfrScroll.setBounds(422, 11, 310, 215);
			frmPicSimulator.getContentPane().add(sfrScroll);
			
			sfrRegisterTable = new JTable(sfrRegisterModel = new DefaultTableModel(){
				private static final long serialVersionUID = 1L;
	
				@Override
				public boolean isCellEditable(int row, int column) {
					//all cells false
					return true;
				}
			});
			sfrRegisterTable.setFont(new Font("Monospaced", Font.PLAIN, 13));
			sfrScroll.setViewportView(sfrRegisterTable);
			
			JLabel lblLstFile = new JLabel("LST File");
			lblLstFile.setBounds(335, 257, 386, 14);
			frmPicSimulator.getContentPane().add(lblLstFile);
			lblLstFile.setFont(new Font("Tahoma", Font.BOLD, 13));
			
			JButton btnNewButton = new JButton("LOAD");
			btnNewButton.addKeyListener(new OwnKeyListener("LOADButton Listener erstellt"));
			btnNewButton.setBounds(335, 280, 89, 23);
			frmPicSimulator.getContentPane().add(btnNewButton);
			
					btnStart = new JButton("START");
					btnStart.addKeyListener(new OwnKeyListener("STARTButton Listener erstellt"));
					btnStart.setBounds(434, 280, 89, 23);
					frmPicSimulator.getContentPane().add(btnStart);
					btnStart.setEnabled(false);
					
							btnStep = new JButton("STEP");
							btnStep.addKeyListener(new OwnKeyListener("STEPButton Listener erstellt"));
							btnStep.setBounds(533, 280, 89, 23);
							frmPicSimulator.getContentPane().add(btnStep);
							btnStep.setEnabled(false);
							
									btnReset = new JButton("RESET");
									btnReset.addKeyListener(new OwnKeyListener("RESETButton Listener erstellt"));
									btnReset.setBounds(632, 280, 89, 23);
									frmPicSimulator.getContentPane().add(btnReset);
									btnReset.setEnabled(false);
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
		frequency.setText(Long.toString(threadSpeed));
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

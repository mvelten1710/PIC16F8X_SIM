package PicController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static PicController.Controller.*;

public class Parser
{

	private int counter;

	private BufferedReader reader;

	private static String content[];

	private int contentIndex = 0;

	public Parser()
	{
		counter = 0;
		reader = null;
		content = new String[1000];
	}

	public int[] getCommands(String path) throws IOException
	{
		int instructionRegister[] = new int[1024];
		operationRow = new ArrayList<String>();
		
		// New File for the LST files
		File file = new File(path);
		// Tries to create the BufferedReader for file
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String lineContent = "";
		String commands = "";
		
		while ((lineContent = reader.readLine()) != null) {
			setContent(lineContent);
			int stringEndPos = 37;
			
			/*Cuts only the line with operations. Only after the operations name*/
			if (!Character.isWhitespace(lineContent.charAt(0))) {
				while (true) {
					if (!Character.isWhitespace(lineContent.charAt(stringEndPos))) {
						stringEndPos++;
						if(stringEndPos == lineContent.length()) {
							break;
						}
					}else{
						break;
					}
				}
				
				operationRow.add(lineContent.substring(0, stringEndPos));
			}
			
			
			// Takes the 6 until 9 char and saves them in
			// the commands String
			commands = lineContent.substring(5, 9);

			// Saves the command in the commands array(in Controller.java)
			try {
				// tries to parse the string to an integer
				instructionRegister[counter] = Integer.parseInt(commands, 16);
				counter++;
			} catch (NumberFormatException e) {
				// Gets own output in the UI
				// System.out.println("ERROR: CANT PARSE TO INT");
			}

		}
		return instructionRegister;
	}

	public String[] getContent()
	{
		return content;
	}

	private void setContent(String newLine)
	{
		content[contentIndex] = newLine;
		contentIndex++;
	}

	public void clearContent()
	{
		for (int i = 0; i < content.length; i++) {
			if (content[i] != null)
				content[i] = null;
		}
		contentIndex = 0;
		counter = 0;
	}
}

package com.test9.irc.newEngine;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

public class ReadServerConfig {
	private final String fFileName;
	private final String fEncoding = "UTF-8";
	//private final String FIXED_TEXT = "But soft! what code in yonder program breaks?";

//	public static void main(String[] args) throws IOException {
//		String fileName = "test.txt";
//		String encoding = "UTF-8";
//		ReadServerConfig test = new ReadServerConfig(fileName, encoding);
//		test.write();
//		test.read();
//	}

	/** Constructor. */
	public ReadServerConfig(String aFileName){
		fFileName = aFileName;
	}

	/** Write fixed content to the given file. */
	public void write(String field, String line) throws IOException  {
		log("Writing to file named " + fFileName + ". Encoding: " + fEncoding);
		Writer out = new OutputStreamWriter(new FileOutputStream(fFileName), fEncoding);
		try {
			out.write(line);
		}
		finally {
			out.close();
		}
	}

	/** Read the contents of the given file. */
	public void read(String fileName) throws IOException {
		log("Reading from file.");
		StringBuilder text = new StringBuilder();
		String NL = System.getProperty("line.separator");
		Scanner scanner = new Scanner(new FileInputStream(fileName), fEncoding);
		try {
			while (scanner.hasNextLine()){
				text.append(scanner.nextLine() + NL);
			}
		}
		finally{
			scanner.close();
		}
		log("Text read in: " + text);
	}

	private void log(String aMessage){
		System.out.println(aMessage);
	}
}

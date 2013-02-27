package com.test9.irc.engine;

import java.io.IOException;

import javax.swing.UIManager;

public class NewEngineTester {

	public static final String VERSION = "11";
	/**
	 * @param args
	 * @throws IOException 
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		try
		{
		        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e){
		        System.out.println("Shit gone down");
		}
		ConnectionEngine CE = new ConnectionEngine();

	}
}

package com.test9.irc.display;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class NewServerWindow extends JFrame {
	
	private static final long serialVersionUID = 285475674231316918L;
	private static final Toolkit KIT = Toolkit.getDefaultToolkit();
	private static double screenHeight = KIT.getScreenSize().getHeight();
	private static double screenWidth = KIT.getScreenSize().getWidth();

	public NewServerWindow()
	{
		setTitle("New Server");
		setResizable(false);

		
		pack();
		setVisible(true);
	}

}

package com.test9.irc.display;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class DisplayStarter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(System.getProperty("os.name").equals("Mac OS X"))
		{
			try {
				System.setProperty("com.apple.mrj.application.apple.menu.about.name", "ermahgerd");
				System.setProperty("apple.laf.useScreenMenuBar", "true");
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e){}


            
		}
		ChatWindow cw = new ChatWindow("server1", null);
		cw.joinServer("server2");
		cw.joinChannel("server2", "channel2", false);
		cw.joinChannel("server1", "channel1", false);
		cw.joinChannel("server2", "channel1", false);
		cw.joinChannel("server2", "channel3", false);
		cw.joinChannel("server2", "channel4", false);
		cw.joinChannel("server2", "channel5", false);
		
		cw.newUser("server2", "channel3", "jared");
		cw.newUser("server2", "channel3", "chris");
		cw.newUser("server2", "channel3", "scott");

		cw.leaveChannel("server1", "channel1");
		cw.newMessage("server2","channel2","does it work?");
		cw.leaveChannel("server2", "channel4");
		cw.newMessage("server2", "channel5", "server2channel5");
		cw.newMessage("server1", "channel1", "server1channel1");
		cw.newMessage("server2", "channel3", "server2channel3");
		cw.newMessage("server2", "channel2", "server2channel2");

		//cw.leaveServer("server2");
		
		//new NewServerWindow();
		
		
	}

}

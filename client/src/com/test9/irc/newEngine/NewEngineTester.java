package com.test9.irc.newEngine;

import java.io.IOException;
import java.sql.Date;

import com.test9.irc.display.ChatWindow;

public class NewEngineTester {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		IRCConnection connection = new IRCConnection("irc.ecsig.com", 6667, null, "jared-bot", 
				"jared-bot", "jared-bot");
		ChatWindow chatWindow = new ChatWindow(connection.getHost());
		ChatWindow.getIrcConnections().add(connection);
		connection.cw = chatWindow;
		connection.connect();
		try {
			connection.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//connection.send("JOIN #ecsig");
		connection.send("JOIN #jircc");
	}

}

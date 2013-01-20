package com.test9.irc.newEngine;

import java.io.IOException;

import com.test9.irc.display.ChatWindow;

public class ConnectionEngine {
	
	private IRCConnection connection;
	private ChatWindow cw;
	
	public ConnectionEngine() throws IOException {

		connection = new IRCConnection("irc.ecsig.com", 6667, null, "jared-bot", 
				"jared-bot", "jared-bot");
		cw = new ChatWindow(connection.getHost());
		connection.addIRCEventListener(new IRCEventAdapter(this));

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

	/**
	 * @return the connection
	 */
	public IRCConnection getConnection() {
		return connection;
	}

	/**
	 * @param connection the connection to set
	 */
	public void setConnection(IRCConnection connection) {
		this.connection = connection;
	}

	/**
	 * @return the cw
	 */
	public ChatWindow getCw() {
		return cw;
	}

	/**
	 * @param cw the cw to set
	 */
	public void setCw(ChatWindow cw) {
		this.cw = cw;
	}
}

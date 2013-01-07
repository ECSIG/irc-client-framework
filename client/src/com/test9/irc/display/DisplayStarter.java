package com.test9.irc.display;

public class DisplayStarter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ChatWindow cw = new ChatWindow("server1");
		cw.joinServer("server2");
		cw.joinChannel("server2", "chan1", false);
		
		
	}

}

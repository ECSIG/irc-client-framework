package com.test9.irc.display;

public class DisplayTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ChatWindow cw = new ChatWindow();
		cw.addChatWindowListener(new EventAdapter(cw, cw.getUtil()));
		Listener l = cw.getListener();
		l.onJoinServer("test");
		l.onJoinChannel("test", "#testChannel");

	}

}

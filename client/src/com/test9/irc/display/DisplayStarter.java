package com.test9.irc.display;

public class DisplayStarter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ChatWindow cw = new ChatWindow("server1");
		cw.joinServer("server2");
		cw.joinChannel("server2", "channel2", false);
		cw.joinChannel("server1", "channel1", false);
		cw.joinChannel("server2", "channel1", false);
		cw.joinChannel("server2", "channel3", false);
		cw.joinChannel("server2", "channel4", false);
		cw.joinChannel("server2", "channel5", false);
		
		cw.leaveChannel("server1", "channel1");
		cw.newMessage("server2","channel2","does it work?");
		cw.leaveChannel("server2", "channel4");
		//cw.leaveServer("server2");
		
	}

}

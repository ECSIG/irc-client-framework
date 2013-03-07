package com.test9.irc.display;

import com.test9.irc.display.Util;
import com.test9.irc.engine.IRCConnection;

public class CommandSender {
	
	private ChatWindow owner;
	private Util util;
	public CommandSender(ChatWindow owner) {
		this.owner = owner;
		util = new Util(owner);
	}
	
	public void sendPart(String hostName, String channel) {
	}
}

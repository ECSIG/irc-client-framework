package com.test9.irc.display;

import com.test9.irc.display.Util;
import com.test9.irc.parser.OutputFactory;

public class CommandSender {
	
	private static ChatWindow owner;
	private static Util util;
	public CommandSender(ChatWindow owner) {
		CommandSender.owner = owner;
		util = new Util(owner);
	}
	
	public static void sendPart() {
		util.findActiveIRCConnection().send(
				OutputFactory.formatMessage("/part "+owner.getActiveChannel(), owner.getActiveServer()));
	}
	
	public static void sendJoin(String channel) {
		util.findActiveIRCConnection().send(
				OutputFactory.formatMessage("/join "+channel, owner.getActiveServer()));
	}
}

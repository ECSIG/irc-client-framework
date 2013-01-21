package com.test9.irc.newEngine;

import java.util.EventListener;

import com.test9.irc.parser.Message;

/**
 * <li>Connect</li>
 * <li>Disconnect</li>
 * <li>Error</li>
 * <li>Invite</li>
 * <li>Join</li>
 * <li>Kick</li>
 * <li>Private Message</li>
 * <li>Mode (Chan)</li>
 * <li>Mode (User)</li>
 * <li>Nick</li>
 * <li>Notice</li>
 * <li>Numeric Reply</li>
 * <li>Numeric Error</li>
 * <li>Part</li>
 * <li>Ping</li>
 * <li>Quit</li>
 * <li>Topic</li>
 * @author Jared Patton
 *
 */
public interface IRCEventListener extends EventListener, IRCConstants {
	public void onConnect(Message m);
	public void onDisconnect();
	public void onError();
	public void onInvite();
	public void onJoin(String host, Message m);
	public void onKick();
	public void onPrivmsg(String host, Message m);
	public void onMode(Message m);
	public void onMode(int two);
	public void onNick(Message m);
	public void onNotice();
	public void onPart(Message m);
	public void onPing(String string);
	public void onQuit(Message m);
	public void onReply(Message m);
	public void onTopic(Message m);
	public void onUnknown(String host, String line);
}

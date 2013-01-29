package com.test9.irc.display;

import com.test9.irc.engine.User;

public interface Listener {
	
	public void onJoinChannel(String server, String channel);
	public void onJoinServer(String server);
	public void onLeaveServer(String server);
	public void onNewMessage(String server, String channel, String message);
	public void onNewPrivMessage(User user, String server, String channel, String nick, String message);
	public void onNewTopic(String server, String channel, String topic);
	public void onNewUserMode(String server, String channel, String mode);
	public void onNickChange(String oldNick, String newNick);
	public void onPartChannel(String server, String channel);
	public void onUserJoin(String server, String channel, String nick);
	public void onUserPart(String server, String channel, String nick);
	public void onUserQuit(String server, String nick, String reason);
	

}

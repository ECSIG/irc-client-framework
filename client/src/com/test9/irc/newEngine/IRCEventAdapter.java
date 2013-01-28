package com.test9.irc.newEngine;

import com.test9.irc.display.ChatWindow;
import com.test9.irc.parser.Message;

/**
 * 
 * @author Jared Patton
 *
 */
public class IRCEventAdapter implements IRCEventListener {

	private ConnectionEngine owner;
	private IRCConnection connection;
	private ChatWindow cw;

	public IRCEventAdapter(ConnectionEngine connectionEngine) {
		this.owner = connectionEngine;
		connection = owner.getConnection();
		cw = connectionEngine.getCw();
	}

	@Override
	public void onConnect(Message m) {


	}

	@Override
	public void onDisconnect() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onInvite() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onJoin(String host, Message m) {
		if(m.getUser().equals(connection.getNick())) {
			cw.joinChannel(host, m.getContent());	
		} else {
			cw.userJoin(host, m.getContent(), m.getNickname());
			if(!(IRCConnection.getUsers().contains(m.getNickname())))
			{
				IRCConnection.getUsers().add(new User(m.getNickname()));
			}
		}
	}

	@Override
	public void onKick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMode(Message m) {

	}

	@Override
	public void onMode(int two) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNick(Message m) {

		cw.nickChange(m.getNickname(), m.getContent());
	}

	@Override	
	public void onNotice() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPart(Message m) {
		System.out.println("onPart()");
		if(m.getNickname().equals(connection.getNick())) {
			cw.partChannel(connection.getHost(), m.getParams()[0]);
		} else {
			cw.userPart(connection.getHost(), m.getParams()[0], m.getNickname());
		}
	}

	@Override
	public void onPing(String line) {
		connection.send("PONG " + line);
	}

	@Override
	public void onPrivmsg(String host, Message m) {
		if(m.getContent().contains(connection.getNick()))
			cw.newMessageHighlight(host, m.getParams()[0], m.getNickname(), m.getContent());
		else
			cw.newPRIVMessage(IRCConnection.getUser(m.getNickname()), host, m.getParams()[0], m.getNickname(), m.getContent());		
	}

	@Override
	public void onQuit(Message m) {
		cw.userQuit(connection.getHost(), m.getNickname(), m.getContent());
	}

	@Override
	public void onReply(Message m) {
		int numCode = Integer.valueOf(m.getCommand());

		if(numCode == IRCUtil.RPL_WELCOME) {
			ChatWindow.getIrcConnections().add(connection);
			cw.joinServer(m.getServerName());

		} else if(numCode == IRCUtil.RPL_NAMREPLY) {
			System.out.println("RPL_NAMERPKY");
			String[] nicks = m.getContent().split(" ");
			for(String n : nicks)
			{
				cw.userJoin(connection.getHost(), m.getParams()[2], n);
				IRCConnection.getUsers().add(new User(n));
			}
		} else if(numCode == IRCUtil.RPL_TOPIC) {
			cw.newTopic(m.getPrefix(), m.getParams()[1], m.getContent());
			cw.newMessage(m.getPrefix(), m.getParams()[1], "<Topic> " + m.getContent());
		}// else if(numCode == IRCUtil.RPL_UMODEIS) {

		//}


	}

	@Override
	public void onTopic(String host, Message m) {
		cw.newTopic(host, m.getParams()[0], m.getContent());
	}

	@Override
	public void onUnknown(String host, String line) {
		cw.newMessage(host, host, line);		
	}
}

package com.test9.irc.newEngine;

import com.test9.irc.display.ChatWindow;
import com.test9.irc.parser.Message;

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
		cw.getIrcConnections().add(connection);
		cw.joinServer(m.getServerName());
		
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
		cw.joinChannel(host, m.getContent());		
	}

	@Override
	public void onKick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPrivmsg(String host, Message m) {
		cw.newMessage(host, m.getParams()[0], m.getNickname(), m.getContent());		
	}

	@Override
	public void onMode() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMode(int two) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNotice() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPing(String line) {
		connection.send("PONG " + line);
	}

	@Override
	public void onQuit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTopic() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnknown(String host, String host2, String line) {
		cw.newMessage(host, host, line);		
	}
 }

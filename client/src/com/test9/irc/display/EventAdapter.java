package com.test9.irc.display;

import com.test9.irc.engine.ConnectionEngine;
import com.test9.irc.engine.IRCConnection;
import com.test9.irc.engine.User;

public class EventAdapter implements Listener {
	private ChatWindow owner;
	private Util util;

	public EventAdapter(ChatWindow cw, Util util) {
		this.owner = cw;
		this.util = util;
	}

	public void createPrivateChannel(String server, String channel, String nick) {
		onJoinChannel(server, channel);
		onUserJoin(server, channel, nick, false);
		onUserJoin(server, channel, ConnectionEngine.getConnection(server).getNick(), false);
	}

	@Override
	public void onAwayStatus(String server, String nick, boolean isAway) {
		for(UserListPanel u : owner.getUserListPanels())
		{
			if(u.getServer().equals(server)) {

				if(u.getListModel().contains(nick)) {
					u.updateAwayStatus(nick, isAway);
				} // endif
			} // endif
		} // endfor

	}

	/**
	 * Called when a user wishes to join a channel on a server
	 * that has already been connected to. Adds the appropriate title
	 * choice, sets the channel to activeChannel and adds the appropriate
	 * user list and output panels. Finally adds the channel node to
	 * the connection tree.
	 * @param server Name of the server that the channel resides on.
	 * @param channel Name of the channel that is to be joined.
	 */
	@Override
	public void onJoinChannel(String server, String channel) {
		System.out.println("Joined a channel:"+server+","+channel);
		if(!util.doesExist(server, channel)) {
			ChatWindow.getServersAndChannels().add(server+","+channel);
			owner.getTitles().add(new Title(server, channel));
			owner.setActiveChannel(channel);
			owner.newOutputPanel(server, channel);
			owner.newUserListPanel(server, channel);
			owner.getConnectionTree().newChannelNode(server, channel);
		}
	}

	/**
	 * Must be called when a server is joined. It will create the default channel
	 * for console messages, add a server node to the connection tree and 
	 * make sure joinedAServer is set to true. Also sets the activeServer as the newly
	 * joined server.
	 * @param server The name of the server that is to be joined.
	 */
	@Override
	public void onJoinServer(String server) {
		if(!util.doesExist(server, server)) {
			owner.joinServerChannel(server);
			owner.getConnectionTree().newServerNode(server);
			owner.setJoinedAServer(true);
			owner.setActiveChannel(server);
			owner.setActiveServer(server);	
		}
	}

	/**
	 * Must be called when a server is left. It removes the appropriate
	 * outputPanel and userListPanel from the outputPanels and userListPanels
	 * array lists.
	 * @param server The name of the server that is to be left.
	 */
	@Override
	public void onLeaveServer(String server) {
		/*
		 * Searches for the output panel in the outputPanels
		 * and then removes it from the layeredPane
		 * when the correct one is found. Also removes it from
		 * the ArrayList of outputPanels.
		 */
		for(OutputPanel oPanel: owner.getOutputPanels())
		{
			if(oPanel.getServer().equals(server))
			{
				owner.getOutputFieldLayeredPane().remove(oPanel);
				owner.getOutputPanels().remove(oPanel);
			}
		}

		/*
		 * Searches for the userList panel in the userListPanels
		 * and then removes it from the layeredPane
		 * when the correct one is found. Also removes it from
		 * the ArrayList of userListPanels.
		 */
		for(UserListPanel uLPanel: owner.getUserListPanels())
		{
			if(uLPanel.getServer().equals(server))
			{
				owner.getUserListsLayeredPane().remove(uLPanel);
				owner.getUserListPanels().remove(uLPanel);
			}
		}

		// Calls on the connectionTree to remove the appropriate server node.
		owner.getConnectionTree().removeServerNode(server);

	}

	@Override
	public void onNewHighlight(User user, String host, String params, String nickname,
			String content) {
		owner.newMessageHighlight(host, params, nickname, content);
		owner.getTerminalPanel().newMessage(user, nickname, params, content, TextFormat.PRIVMSG);



	}

	@Override
	public void onNewIRCConnection(IRCConnection connection) {
		owner.getIrcConnections().add(connection);
	}

	/**
	 * Called when a message is received. It takes in the server name, 
	 * the channel name, and the actual message.
	 * ChatWindow has a channel for the server connection itself that is of the same
	 * name as the server. The server channel should receive messages that are command
	 * responses.
	 * @param server The server that the channel is from.
	 * @param channel The channel the message is from.
	 * @param message The message that was received. 
	 */
	@Override
	public void onNewMessage(String server, String channel, String message, String command) {
		OutputPanel op = util.findOutputPanel(server, channel);
		if(op != null) {
			if(command.equals("TOPIC")) {
				op.newMessage(message, TextFormat.TOPIC);
				owner.getTerminalPanel().newMessage(null, null, channel, message, TextFormat.TOPIC);
			} else if(command.equals("REPLY") || command.equals("ERROR")) {
				op.newMessage(message, TextFormat.REPLY);
				if(command.equals("REPLY")) {
					owner.getTerminalPanel().newMessage(null, null, server, message, TextFormat.REPLY);
				} else if(command.equals("ERROR")) {
					owner.getTerminalPanel().newMessage(null, null, server, message, TextFormat.ERROR);
				}
			}
		}
		else
			System.err.println("Cound not find channel to append message to.");

	}

	@Override
	public void onNewPrivMessage(User user, String server, String channel,
			String nick, String message, boolean isLocal) {
		OutputPanel op = util.findOutputPanel(server, channel);
		
		if(op != null) {
			op.newMessage(user, nick, message, isLocal);
		} else {
			createPrivateChannel(server, channel, nick);
			onNewPrivMessage(user, server, channel, nick, message, isLocal);
		}

		if(!(owner.getActiveServer().equals(server)) || (!(owner.getActiveChannel().equals(channel)))) {
			owner.getTerminalPanel().newMessage(user, nick, channel, message, TextFormat.PRIVMSG);
		} 

	}

	/**
	 * Adds a new topic to the topics ArrayList.
	 * @param server The name of the server the topic came from.
	 * @param channel The channel that the topic is from.
	 * @param topic The topic of the channel/server.
	 */
	@Override
	public void onNewTopic(String server, String channel, String topic) {
		System.out.println(server);
		System.out.println(channel);
		System.out.println(topic);
		Title t = util.findTitle(server, channel);
		t.setTopic(topic);
		owner.getFrame().setTitle(t.getFullTitle());
		owner.getTerminalPanel().newMessage(null, null, channel, topic, TextFormat.TOPIC);

	}

	@Override
	public void onNewUserMode(String server, String channel, String mode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNotice(String server, String params, String content) {
		util.findOutputPanel(server, server).newMessage(content, TextFormat.NOTICE);
		owner.getTerminalPanel().newMessage(null, null, server, content, TextFormat.NOTICE);
	}

	/**
	 * Called when a user changes their nick.
	 * @param oldNick The original nick of the user.
	 * @param newNick The new nick of a user.
	 */
	@Override
	public void onNickChange(String host, String oldNick, String newNick) {
		for(UserListPanel u : owner.getUserListPanels())
		{
			if(u.getServer().equals(host)) {
				if(u.nickChange(oldNick, newNick)) {
					onNewMessage(u.getServer(), u.getChannel(), 
							oldNick + " is now known as "+ newNick, "REPLY");
				}
			}
		}
		owner.getTerminalPanel().newMessage(null, null, host, 
				oldNick + " is now known as "+newNick, TextFormat.NICK);

	}

	/**
	 * Called when a user leaves a channel. It removes the 
	 * output panel, user list and the appropriate node from
	 * the connection tree.
	 * @param server Name of the server that is being left.
	 * @param channel Name of the channel that is being parted from.
	 */
	@Override
	public void onPartChannel(String server, String channel) {
		OutputPanel op = util.findOutputPanel(server, channel);
		UserListPanel ulp = util.findUserListPanel(server, channel);
		if(util.doesExist(server, channel))
			ChatWindow.getServersAndChannels().remove(new String(server +","+channel));
		owner.getOutputFieldLayeredPane().remove(op);
		owner.getUserListsLayeredPane().remove(ulp);
		owner.getOutputPanels().remove(op);
		owner.getUserListPanels().remove(ulp);
		owner.getConnectionTree().removeChannelNode(server, channel);
		owner.getTitles().remove(util.findTitle(server, channel));
		owner.newActiveChannels(server, server, true);

		owner.getOutputFieldLayeredPane().invalidate();
		owner.getUserListsLayeredPane().invalidate();
	}

	@Override
	public void onUserJoin(String server, String channel, String nick, boolean isUserRply) {
		UserListPanel ulp = util.findUserListPanel(server, channel);
		
		if(ulp != null) {
			ulp.newUser(nick);
			if(!isUserRply) {
				util.findOutputPanel(server, channel).newMessage(nick + " has joined.", TextFormat.JOIN);
			}
		} else {
			System.err.println("[ChatWindowError] Cound not find channel to add new user.");
		}
		
		if(!isUserRply) {
			owner.getTerminalPanel().newMessage(null, null, channel, nick+" joined "+channel, TextFormat.JOIN);
		}
	}

	@Override
	public void onUserPart(String server, String channel, String nick) {
		UserListPanel ulp = util.findUserListPanel(server, channel);
		if(ulp != null) {
			ulp.userPart(nick);
			util.findOutputPanel(server, channel).newMessage(nick+" has parted.", TextFormat.PART);
		} else {
			System.err.println("Cound not find channel to add new user.");
		}
		owner.getTerminalPanel().newMessage(null, null, channel, nick + " left "+channel, TextFormat.PART);

	}

	@Override
	public void onUserQuit(String server, String nick, String reason) {
		for(UserListPanel u : owner.getUserListPanels())
		{
			if(u.getServer().equals(server)) {

				if(u.getListModel().contains(nick)) {
					u.userPart(nick);
					util.findOutputPanel(server, u.getChannel()).newMessage(
									nick +" "+ reason, TextFormat.QUIT);
				} // endif
			} // endif
		} // endfor

		owner.getTerminalPanel().newMessage(null, null, server, 
				nick+" quit. Reason("+reason+")", TextFormat.QUIT);
	}
}

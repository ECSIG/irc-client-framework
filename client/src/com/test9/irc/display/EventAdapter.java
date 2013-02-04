package com.test9.irc.display;

import com.test9.irc.engine.User;

public class EventAdapter implements Listener {
	private ChatWindow owner;
	private Util util;

	public EventAdapter(ChatWindow cw, Util util) {
		this.owner = cw;
		this.util = util;

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
	public void onJoinChannel(String server, String channel) {
		owner.getTitles().add(new Title(server, channel));
		owner.setActiveChannel(channel);
		owner.newOutputPanel(server, channel);
		owner.newUserListPanel(server, channel);
		owner.getConnectionTree().newChannelNode(server, channel);

	}

	/**
	 * Must be called when a server is joined. It will create the default channel
	 * for console messages, add a server node to the connection tree and 
	 * make sure joinedAServer is set to true. Also sets the activeServer as the newly
	 * joined server.
	 * @param server The name of the server that is to be joined.
	 */
	public void onJoinServer(String server) {
		owner.joinChannel(server);
		owner.getConnectionTree().newServerNode(server);
		owner.setJoinedAServer(true);
		owner.setActiveChannel(server);
		owner.setActiveServer(server);	
	}

	/**
	 * Must be called when a server is left. It removes the appropriate
	 * outputPanel and userListPanel from the outputPanels and userListPanels
	 * array lists.
	 * @param server The name of the server that is to be left.
	 */
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
	public void onNewMessage(String server, String channel, String message, String command) {
		if(util.findChannel(server, channel,0) != -1)
		{
			if(command.equals("TOPIC")) {
				owner.getOutputPanels().get(
						util.findChannel(server, channel, 0)).newMessage(
								message, TextFormat.topic);
			} else if(command.equals("REPLY")) {
				owner.getOutputPanels().get(
						util.findChannel(server, channel, 0)).newMessage(
								message, TextFormat.reply);
			}
		}
		else
			System.err.println("Cound not find channel to append message to.");

	}

	@Override
	public void onNewPrivMessage(User user, String server, String channel,
			String nick, String message, boolean isLocal) {
		if(util.findChannel(server, channel,0) != -1) {
			owner.getOutputPanels().get(
					util.findChannel(server, channel, 0)).newMessage(user, nick, message, isLocal);
		} else {
			onJoinChannel(server, channel);
			onUserJoin(server, channel, nick, false);
			onUserJoin(server, channel, channel, false);
			onNewPrivMessage(user, server, channel, nick, message, isLocal);
		}

	}

	/**
	 * Adds a new topic to the topics ArrayList.
	 * @param server The name of the server the topic came from.
	 * @param channel The channel that the topic is from.
	 * @param topic The topic of the channel/server.
	 */
	public void onNewTopic(String server, String channel, String topic) {
		//System.out.println(owner.getTitles().get(util.findTitle(server, channel)));
		System.out.println("server: "+server+" channel: "+channel+" topic: "+topic);
		owner.getTitles().get(util.findTitle(server, channel)).setTopic(topic);
		owner.getFrame().setTitle(owner.getTitles().get(
				util.findTitle(owner.getActiveServer(), 
						owner.getActiveChannel())).getFullTitle());

	}

	@Override
	public void onNewUserMode(String server, String channel, String mode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNotice(String server, String params, String content) {
		System.out.println("ONNOTICE");
		owner.getOutputPanels().get(util.findChannel(server, server, 0)).newMessage(content, TextFormat.notice);
		
	}

	/**
	 * Called when a user changes their nick.
	 * @param oldNick The original nick of the user.
	 * @param newNick The new nick of a user.
	 */
	public void onNickChange(String oldNick, String newNick) {
		for(UserListPanel u : owner.getUserListPanels())
		{
			u.nickChange(oldNick, newNick);
		}

	}

	/**
	 * Called when a user leaves a channel. It removes the 
	 * output panel, user list and the appropriate node from
	 * the connection tree.
	 * @param server Name of the server that is being left.
	 * @param channel Name of the channel that is being parted from.
	 */
	public void onPartChannel(String server, String channel) {
		int outputPanelId = util.findChannel(server, channel, 0);
//		owner.getOutputPanels().get(outputPanelId).stopDelayThread();
		owner.getOutputPanels().remove(outputPanelId);
		owner.getUserListPanels().remove(util.findChannel(server, channel, 1));
		owner.getConnectionTree().removeChannelNode(server, channel);
	}

	@Override
	public void onTerminalMessage(String message) {
		// TODO Auto-generated method stub
		
	}

	public void onUserJoin(String server, String channel, String nick, boolean isUserRply) {
		if(util.findChannel(server, channel,1) != -1) {
			owner.getUserListPanels().get(util.findChannel(server, channel,1)).newUser(nick);
			if(!isUserRply)
				owner.getOutputPanels().get(util.findChannel(server, channel, 0)).newMessage(
						nick + " has joined.", TextFormat.join);

		} else {
			System.err.println("[ChatWindowError] Cound not find channel to add new user.");
		}
	}

	public void onUserPart(String server, String channel, String nick) {
		if(util.findChannel(server,channel,1) != -1) {
			owner.getUserListPanels().get(util.findChannel(server, channel,1)).userPart(nick);
			owner.getOutputPanels().get(
					util.findChannel(server, channel, 0)).newMessage(
							nick+" has parted.", TextFormat.part);
		} else {
			System.err.println("Cound not find channel to add new user.");
		}

	}

	public void onUserQuit(String server, String nick, String reason) {
		for(UserListPanel u : owner.getUserListPanels())
		{
			if(u.getServer().equals(server)) {

				if(u.getListModel().contains(nick)) {
					u.userPart(nick);
					owner.getOutputPanels().get(util.findChannel(
							server, u.getChannel(), 0)).newMessage(
									nick +" "+ reason, TextFormat.quit);
				} // endif
			} // endif
		} // endfor
	}
}

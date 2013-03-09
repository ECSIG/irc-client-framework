package com.test9.irc.display;

import com.test9.irc.engine.IRCConnection;

public class Util {

	private ChatWindow owner;

	public Util(ChatWindow owner) {
		this.owner = owner;
	}

	/**
	 * Finds the appropriate channel for a given action.
	 * 
	 * @param server Server to be found.
	 * @param channel Channel to be found.
	 * @param type (0 is for outputPanels, 1 for userListPanels)
	 * @return Returns the index of the outputPanel or userListPanel
	 */
	OutputPanel findOutputPanel(String server, String channel)
	{
		int i = 0;
		OutputPanel op;

		while(i < owner.getOutputPanels().size()) {
			op = owner.getOutputPanels().get(i);
			if(op.getServer().equals(server) && op.getChannel().equals(channel)){
				return op;
			} else { 
				i++;
			}
		}
		return null;
	}

	UserListPanel findUserListPanel(String server, String channel) {
		int i = 0;
		UserListPanel ulp;
		while(i < owner.getUserListPanels().size())	{
			ulp = owner.getUserListPanels().get(i);
			if(ulp.getServer().equals(server) && ulp.getChannel().equals(channel))
			{
				return ulp;
			}	
			else i++;
		}
		return null;
	}

	/**
	 * Finds an IRCConnection from the arrayList of IRCConnections.
	 * @return The index of the IRCConnection in the ArrayList.
	 */
	IRCConnection findActiveIRCConnection(){
		int index = 0;
		IRCConnection c;
		String activeServer = owner.getActiveServer();
		while(index < owner.getIrcConnections().size()) {
			c = owner.getIrcConnections().get(index);
			if(c.getConnectionName().equals(activeServer)) {
				return c;
			} else {
				index++;
			}
		}
		return null;
	}


	/**
	 * Finds the title that is for a given server and channel.
	 * @param server Name of the server.
	 * @param channel Name of the channel.
	 * @return The index of the title in the ArrayList.
	 */
	Title findTitle(String server, String channel) {
		int index = 0;
		Title t;
		while(index < owner.getTitles().size())	{
			t = owner.getTitles().get(index);
			if(t.getServer().equals(server)) {
				if(t.getChannel().equals(channel)) {
					return t;
				} else {
					index++;
				}
			} else {
				index++;
			}
		}
		return null;
	}

	boolean doesExist(String server, String channel){
		if(ChatWindow.getServersAndChannels().contains(server+","+channel))
			return true;
		else
			return false;
	}
}

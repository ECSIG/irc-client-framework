package com.test9.irc.display;

import java.util.ArrayList;
import java.util.Arrays;

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
	int findChannel(String server, String channel, int type)
	{
		//boolean found = false; After closer examination, found is never actually used, see comment below
		int i = 0;
		if(type==0)
		{
			while(
					//!found && This cannot happen, in every case which found==true the method returns which breaks the loop 
					i < owner.getOutputPanels().size())
			{
				if(owner.getOutputPanels().get(i).getServer().equals(server) && 
						owner.getOutputPanels().get(i).getChannel().equals(channel))
				{
					//found = true;
					return i;
				}
				else 
					i++;
			}
		}
		else if (type==1)
		{
			while(
					//!found && This cannot happen, in every case which found==true the method returns which breaks the loop 
					i < owner.getUserListPanels().size())
			{
				if(owner.getUserListPanels().get(i).getServer().equals(server) && 
						owner.getUserListPanels().get(i).getChannel().equals(channel))
				{
					//found = true;
					return i;
				}	
				else i++;
			}
		}
		return -1;
	}
	
	
	/**
	 * Finds an IRCConnection from the arrayList of IRCConnections.
	 * @return The index of the IRCConnection in the ArrayList.
	 */
	int findIRCConnection()
	{
		//boolean found = false; After closer examination, found is never actually used, see comment below
		int index = 0;

		while(
				//!found && This cannot happen, in every case which found==true the method returns which breaks the loop 
				index < owner.getIrcConnections().size())
		{
			if(owner.getIrcConnections().get(index).getHost().equals(owner.getActiveServer()))
			{
				//found = true;	
				return index;
			}
			else
				index++;
		}
		System.err.println("Error finding channel while sending message");
		return -1;
	}
	
	
	/**
	 * Finds the title that is for a given server and channel.
	 * @param server Name of the server.
	 * @param channel Name of the channel.
	 * @return The index of the title in the ArrayList.
	 */
	int findTitle(String server, String channel)
	{
		for(Title t : owner.getTitles()) {
			System.out.println("server:"+t.getServer()+", channel:"+t.getChannel());
		}
		
		int index = 0;
		while(index < owner.getTitles().size())	{
			if(owner.getTitles().get(index).getServer().equals(server))
			{
				if(owner.getTitles().get(index).getChannel().equals(channel))
				{
					return index;
				}
				else
					index++;
			}
			else
				index++;
		}
		System.err.println("Error finding title you were looking for");
		return -1;
	}
}

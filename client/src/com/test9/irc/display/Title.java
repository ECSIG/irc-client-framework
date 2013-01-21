package com.test9.irc.display;

public class Title {

	private String server;
	private String channel;
	private String mode;
	private String topic;
	private String userMode;
	//private String fullTitle;

	public Title(String server){
		this.server = server;
	}

	public Title(String server, String channel)
	{
		System.out.println("new title made");
		this.server = server;
		this.channel = channel;
	}

	public void setTopic(String topic)
	{
		this.topic = topic;
	}

	public void setUserMode(String userMode)
	{
		this.userMode = userMode;
	}

	public String getServer()
	{
		return this.server;
	}

	public String getChannel()
	{
		return this.channel;
	}

	public String getFullTitle()
	{
		if(mode != null && topic != null) {
			return channel+" mode("+mode+")"+topic;
			
		} else if(mode == null && topic != null) {
			return channel+" "+topic;
			
		} else if(topic == null && mode != null) {
			return channel + " mode("+mode+")";
			
		} else
			return channel;
	}
}

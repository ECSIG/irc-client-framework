package com.test9.irc.display;

public class Title {

	private String server;
	private String channel;
	private String mode;
	private String topic;
	@SuppressWarnings("unused")
	private String userMode;

	/**
	 * Constructs a new title for a server connection.
	 * @param server Name of the server.
	 */
	public Title(String server){
		this.server = server;
	}

	/**
	 * Constructs a title for a channel.
	 * @param server Name of the server.
	 * @param channel Name of the channel.
	 */
	public Title(String server, String channel)
	{
		System.out.println("new title made");
		this.server = server;
		this.channel = channel;
	}

	/**
	 * Sets a new topic.
	 * @param topic The new topic.
	 */
	public void setTopic(String topic)
	{
		this.topic = topic;
	}

	/**
	 * Sets a new UserMode
	 * @param userMode The new userMode.
	 */
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

	/**
	 * Returns a constructed title for a JFrame.
	 * @return A title for a JFrame.
	 */
	public String getFullTitle()
	{
		if(mode != null && topic != null) {
			// Returns everything if nothing is null.
			return channel+" mode("+mode+")"+topic;
			
		} else if(mode == null && topic != null) {
			// Returns a title with a channel and topic only.
			return channel+" "+topic;
			
		} else if(topic == null && mode != null) {
			// Returns the channel and mode if a topic is not present.
			return channel + " mode("+mode+")";
			
		} else
			// Returns the channel name if nothing else is present.
			return channel;
	}
}

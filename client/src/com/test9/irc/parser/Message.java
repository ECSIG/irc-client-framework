package com.test9.irc.parser;

public class Message {
	
	private String prefix = "";
	private String command = "";
	private String params = "";
	private String server_name = "";
	private String nickname = "";
	private String user = "";
	private String host = "";
	private String content = "";
	
	public Message(String prefix, String command, String params, 
			String server_name, String nickname, String user, String  host, String content)
	{
		this.prefix = prefix;
		this.command = command;
		this.params = params;
		this.server_name = server_name;
		this.nickname = nickname;
		this.user = user;
		this.host = host;
		this.content = content;
	}

	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @param prefix the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * @return the command
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * @param command the command to set
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * @return the params
	 */
	public String getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(String params) {
		this.params = params;
	}

	/**
	 * @return the server_name
	 */
	public String getServer_name() {
		return server_name;
	}

	/**
	 * @param server_name the server_name to set
	 */
	public void setServer_name(String server_name) {
		this.server_name = server_name;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	
}

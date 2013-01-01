package com.test9.irc.parser;

public class Message {
	
	private static String prefix = "";
	private static String command = "";
	private static String params = "";
	private static String server_name = "";
	private static String nickname = "";
	private static String user = "";
	private static String host = "";
	
	public Message(String[] args)
	{
		prefix = args[0];
		command = args[1];
		params = args[2];
		server_name = args[3];
		nickname = args[4];
		user = args[5];
		host = args[6];
	}

	public static String getPrefix() {
		return prefix;
	}

	public static void setPrefix(String prefix) {
		Message.prefix = prefix;
	}

	public static String getCommand() {
		return command;
	}

	public static void setCommand(String command) {
		Message.command = command;
	}

	public static String getParams() {
		return params;
	}

	public static void setParams(String params) {
		Message.params = params;
	}

	public static String getServer_name() {
		return server_name;
	}

	public static void setServer_name(String server_name) {
		Message.server_name = server_name;
	}

	public static String getNickname() {
		return nickname;
	}

	public static void setNickname(String nickname) {
		Message.nickname = nickname;
	}

	public static String getUser() {
		return user;
	}

	public static void setUser(String user) {
		Message.user = user;
	}

	public static String getHost() {
		return host;
	}

	public static void setHost(String host) {
		Message.host = host;
	}

}

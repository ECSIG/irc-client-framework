package com.test9.irc.parser;

public class OutputFactory {
	private static boolean init = false;
	
	public OutputFactory() {
		init = true;
	}

	public String format_message(String command, String msgtarget, String message)
	{
		String formatted_message = "";
		
		switch(command){
		case "PASS":
		case "NICK":
		case "USER":
		case "OPER":
		case "MODE":
		case "SERVICE":
		case "QUIT":
		case "SQUIT":
		case "JOIN":
		case "PART":
		//case "MODE":
			//TODO check difference in modes
		case "TOPIC":
		case "NAMES":
		case "LIST":
		case "INVITE":
		case "KICK":
		case "PRIVMSG":
			make_privmsg(msgtarget, message);
			break;
		case "NOTICE":
		case "MOTD":
		case "LUSERS":
		case "VERSION":
		case "STATS":
		case "LINKS":
		case "TIME":
		case "CONNECT":
		case "TRACE":
		case "ADMIN":
		case "INFO":
		case "SERVLIST":
		case "SQUERY":
		case "WHO":
		case "WHOIS":
		case "WHOWAS":
		case "KILL":
		case "PING":
		case "PONG":
		case "ERROR":
		case "AWAY":
		case "REHASH":
		case "DIE":
		case "RESTART":
		case "SUMMON":
		case "USERS":
		case "WALLOPS":
		case "USERHOST":
		case "ISON":
			
		}
		
		
		return formatted_message;
		
	}
	
	/**
	 * 
	 * This constructs a private message that can go to either of several targets.
	 * @param msgtarget
	 * @param message
	 * @return privmsg
	 */
	private String make_privmsg(String msgtarget, String message)
	{
		String privmsg = "";
		
		
		return privmsg;
		
	}
	
	/**
	 * @return the init
	 */
	public static boolean isInit() {
		return init;
	}
}

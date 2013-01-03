package com.test9.irc.parser;

/**
 * 
 * @author Jared Patton
 *
 */
public class OutputFactory {
	private static boolean init = false;

	public OutputFactory() {
		init = true;
	}

	public String formatMessage(String message, String target)
	{
		String formattedMessage = "";

		if(message.startsWith("/"))
		{
			String command = message.substring(1, message.indexOf(" "));

			switch(command) {
			case "/PASS": case "/NICK": case "/OPER":
			case "/INVITE": case "/MOTD": case "/LUSERS":
			case "/VERSION": case "/STATS": case "/LINKS":
			case "/TIME": case "/CONNECT": case "/TRACE":
			case "/ADMIN": case "/INFO": case "/WHO":
			case "/WHOIS": case "/WHOWAS": case "/PING":
			case "/REHASH": case "/DIE": case "/RESTART":
			case "/SUMMON": case "/USERS": case "/ISON":
			case "/JOIN": case "/PART": case "/PONG":
				formattedMessage = message.substring(1, message.length());
				break;
			case "/QUIT":
				formattedMessage = makeQuit(message);
				break;
			}
		} 

		else 
		{
			switch (message.substring(0, message.indexOf(" "))) {
			case "PRIVMSG": case "SQUERY": case "AWAY":
				formattedMessage = makePrivmsg(message, target);
				break;
			}
		}

		/*	
			case "USER":
				//formatted_message = make_user()
			case "MODE":
			case "SERVICE":
		 * 

		case "SQUIT":
			//case "MODE":
			//TODO check difference in modes
		case "TOPIC":
		case "NAMES":
		case "LIST":
		case "KICK":

		case "NOTICE":
		case "SERVLIST":
			//TODO should case "KILL": be included

			break;
			//TODO look into this shiz case "ERROR":
		case "WALLOPS":
		case "USERHOST":	
		}*/

		return formattedMessage;

	}

	/**
	 * 
	 * This constructs a private message that can go to either of several targets.
	 * @param msgtarget
	 * @param message
	 * @return privmsg
	 */
	private String makePrivmsg(String message, String target)
	{
		String privmsg = "";

		privmsg.concat("PRIVMSG ");
		privmsg.concat(target);
		privmsg.concat(" :");
		privmsg.concat(message);

		return privmsg;

	}	
	
	/**
	 * Makes a quit message for when the user quits.
	 * @param message
	 * @return
	 */
	private String makeQuit(String message)
	{
		String quitMessage = "";

		quitMessage.concat("QUIT :");
		quitMessage.concat(message);

		return quitMessage;
	}


	/**
	 * @return the init
	 */
	public static boolean isInit() {
		return init;
	}
}

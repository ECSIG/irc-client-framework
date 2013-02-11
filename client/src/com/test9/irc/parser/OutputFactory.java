package com.test9.irc.parser;

/**
 * Creates properly formatted string that will be readable by an IRC server.
 * Refer to http://tools.ietf.org/html/rfc2812#section-3.3 for proper use of
 * messages and commands.
 * @author Jared Patton
 *
 */
public class OutputFactory {

	private static boolean init = false;
	private static final String RNTAIL= "\r\n";

	final private static String commands[] = 
		{"PASS", "NICK", "OPER", "INVITE", "MOTD", "LUSERS", "VERSION", "STATS", "LINKS",
		"TIME", "CONNECT", "TRACE", "ADMIN", "INFO", "WHO", "WHOIS", "WHOWAS", "PING",
		"REHASH", "DIE", "RESTART", "SUMMON", "USERS", "ISON", "JOIN", "PART", "PONG",
		"MODE", "USER", "SERVICE", "SQUIT", "TOPIC", "NAMES", "LIST", "KICK", "NOTICE",
		"AWAY", "SQUERY", "SERVLIST", "USERHOST", "ERROR", "QUIT"};

	public OutputFactory() {
		init = true;
	}

	/**
	 * Use this method to create a formatted message that can be sent to the server.
	 * 
	 * All command formats should be manually written by the user after referring to
	 * http://tools.ietf.org/html/rfc2812#section-3.3 
	 * 
	 * In case of a PRIVMSG a target will be needed form the caller.
	 * 
	 * The target should be in the format (taken from RFC2812)
	 * 
	 * :Angel!wings@irc.org PRIVMSG Wiz: Are you receiving this message?
	 * 	^ Message to Angel to Wiz.
	 * 
	 * PRIVMSG Angel :yes I'm receiving it! 
	 * ^ privmsg to Angel
	 * 
	 * PRIVMSG jto@tolsun.oulu.fi : Hello ! 
	 * ^ privmsg to a user jto on server tolsun.oulu.fi
	 * 
	 * PRIVMSG kalt%millenium.stealth.net@irc.stealth.net :Are you a frog?
	 * 	^ Sends a message to kalt on server irc.stealth.net who is connected to host millenium.stealth.net
	 * 
	 * PRIVMSG kalt%millenium.stealth.net :Do you like cheese?
	 * 	^ message to a user on the local server with username of 'kalt' and connected from the host millenium.stealth.net
	 * 
	 * PRIVMSG Wiz!jto@tolsum.oulu.fi :Hello !
	 * 	^ message to the user with nickname Wiz who is connected from the host tolsum.oulu.fi and has the username "jto"
	 * 
	 * PRIVMSG $*.fi :Server tolsun.oulu.fi rebooting
	 * 	^ Message to everyone on a server which has a name mathing *.fi
	 * 
	 * PRIVMSG #*.edu :NSFNet is undergoing work, expect interruptions
	 * 	^ Message to all users who come from a host which has a name matching *.edu
	 * 
	 * @param message
	 * @param target
	 * @return
	 */
	public String formatMessage(String message, String target)
	{
		String formattedMessage = "";
		boolean validCommand = false;
		String command = "";
		int commandIndex = -1;

		if(message.startsWith("/"))
		{

			try {
				command = message.substring(1, message.indexOf(" "));
			} catch (StringIndexOutOfBoundsException e) {
				System.out.println("Probably not a vaild command");
				command = message;
			}

			int i = 0;

			while (!validCommand && i < commands.length - 1)
			{
				if(command.equalsIgnoreCase(commands[i]))
				{
					commandIndex = i;
					validCommand = true;
				}

				i++;
			}

			if(validCommand)
			{
				if(command.equals("/QUIT")) {
					formattedMessage = makeQuit(message);
				} else {
					formattedMessage = commands[commandIndex]+ " " + 
							message.substring(message.indexOf(" ")+1, message.length());
				}
			}
		} 
		else 
		{
			formattedMessage = makePrivmsg(message, target);
		}
		
		formattedMessage += RNTAIL;
		//System.out.println("'"+formattedMessage+"'");
		//	case "KILL": be included
		//	The RFC has no syntactical information about this currently.

		//	case "WALLOPS":
		//	This does not need to be included and looks like it should not be 
		//	as described in the RFC.

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

		privmsg += "PRIVMSG ";
		privmsg += target;
		privmsg += " :";
		privmsg += message;
		privmsg += RNTAIL;

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

		quitMessage += "QUIT :";
		quitMessage += message;
		quitMessage += RNTAIL;

		return quitMessage;
	}


	/**
	 * @return the init
	 */
	public static boolean isInit() {
		return init;
	}
}

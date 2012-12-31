package com.test9.irc.parser;

public class Parser {

	final private String PREFIXES[] = {"ERR", "RPL"};
	private static boolean init = false;
	
	final static private String ERR[] = {"NOSUCHNICK", "NOSUCHSERVER", "NOSUCHCHANNEL", 
		"CANNOTSENDTOCHAN", "TOOMANYCHANNELS", "WASNOSUCHNICK", "TOOMANYTARGETS", 
		"NOORIGIN", "NORECIPIENT", "NOTEXTTOSEND", "NOTOPLEVEL", "WILDTOPLEVEL",
		"UNKNOWNCOMMAND", "NOMOTD", "NOADMININFO", "FILEERROR", "NONICKNAMEGIVEN", 
		"ERRONEUSNICKNAME", "NICKNAMEINUSE", "NICKCOLLISION", "USERNOTINCHANNEL", 
		"NOTONCHANNEL", "USERONCHANNEL", "NOLOGIN", "SUMMONDISABLED", "USERSDISABLED", 
		"NOTREGISTERED", "NEEDMOREPARAMS", "ALREADYREGISTRED", "NOPERMFORHOST", "PASSWDMISMATCH", 
		"YOUREBANNEDCREEP", "KEYSET", "CHANNELISFULL", "UNKNOWNMODE", "INVITEONLYCHAN", 
		"BANNEDFROMCHAN", "BADCHANNELKEY", "NOPRIVILEGES", "CHANOPRIVSNEEDED",
		"CANTKILLSERVER", "NOOPERHOST", "UMODEUNKNOWNFLAG", "USERSDONTMATCH"};
	
	private enum RPL {NONE, USERHOST, ISON, AWAY, UNAWAY, NOWAWAY, WHOISUSER, WHOISSERVER,
		WHOISOPERATOR, WHOISIDLE, ENDOFWHOIS, WHOISCHANNELS, WHOWASUSER, ENDOFWHOWAS, LISTSTART,
		LIST, LISTEND, CHANNELMODEIS, NOTOPIC, TOPIC, INVITING, SUMMONING, VERSION, WHOREPLY,
		ENDOFWHO, NAMREPLY, ENDOFNAMES, LINKS, ENDOFLINKS, BANLIST, ENDOFBANLIST, INFO, ENDOFINFO,
		MOTDSTART, MOTD, ENDOFMOTD, YOUREOPER, REHASHING, TIME, USERSSTART, USERS, ENDOFUSERS, NOUSERS,
		TRACELINK, TRACECONNECTING, TRACEHANDSHAKE, TRACEUNKNOWN, TRACEOPERATOR, TRACEUSER, TRACESERVER,
		TRACENEWTYPE, TRACELOG, STATSLINKINFO, STATSCOMMANDS, STATSCLINE, STATSNLINE, STATSILINE, 
		STATSKLINE, STATSYLINE, ENDOFSTATS, STATSLLINE, STATSUPTIME, STATSOLINE, STATSHLINE, UMODEIS,
		LUSERCLIENT, LUSEROP, LUSERUNKNOWN, LUSERCHANNELS, LUSERME, ADMINME, ADMINLOC1, ADMINLOC2, 
		ADMINEMAIL}
		
		
	public static void main(String args[])
	{
		System.out.println(ERR.length);
	}
	public Parser() 
	{
		init = true;
	}

	public int parse(String message)
	{
		int msg = -1;

		String message_split[] = message.split("_");

		find_prefix(message_split[0]);
		
		return msg;
	}


	private int find_prefix(String prefix) 
	{
		int prefixes_index = -1;

		boolean found = false;
		int try_index = 0;

		while(!found && try_index < PREFIXES.length)
		{
			if(prefix.equals(PREFIXES[try_index]))
				found = true;
			else
				try_index++;
		}

		if(found)
			return prefixes_index;
		else
			return -1;
	}

	/**
	 * @return the init
	 */
	public static boolean isInit() {
		return init;
	}
	/**
	 * @param init the init to set
	 */
	public static void setInit(boolean init) {
		Parser.init = init;
	}

}

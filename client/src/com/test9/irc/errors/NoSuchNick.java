package com.test9.irc.errors;

/**
 * 
 * Code: 401
 * ERR_NOSUCHNICK
 * "<nickname> :No such nick/channel"
 * - Used to indicate the nickname parameter supplied to a command is 
 * 		currently unused.
 * @author Jared Patton
 *
 */
public class NoSuchNick {

	public NoSuchNick()
	{	
	}
	
	public int get_irc_code()
	{
		return 401;
	}
}

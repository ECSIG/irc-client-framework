package com.test9.irc.parser;

/**
 *                *( ( "+" / "-" ) *( "i" / "w" / "o" / "O" / "r" ) )

   The user MODE's are typically changes which affect either how the
   client is seen by others or what 'extra' messages the client is sent.

   A user MODE command MUST only be accepted if both the sender of the
   message and the nickname given as a parameter are both the same.  If
   no other parameter is given, then the server will return the current
   settings for the nick.

      The available modes are as follows:

           a - user is flagged as away;
           i - marks a users as invisible;
           w - user receives wallops;
           r - restricted user connection;
           o - operator flag;
           O - local operator flag;
           s - marks a user for receipt of server notices.
 * @author Jared Patton
 *
 */
public class UserModeParser {

	private static boolean isAway = false;
	private static boolean isInvisible = false;
	private static boolean isWallops = false;
	private static boolean isRestrictedUserConnection = false;
	private static boolean isOP = false;
	private static boolean isLocalOP = false;
	private static boolean isRecipiantNotices = false;
	
	public static void ParseMode(String message) {
		
	}
}

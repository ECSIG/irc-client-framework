package com.test9.irc.errors;

/**
 * 404
 * ERR_CANNOTSENDTOCHAN
 * "<channel name> :Cannot send to channel"
 * - Sent to a user who is either (a) not on a channel which 
 * is mode +n or (b) not a chanop (or mode +v) on a channel which 
 * has mode +m set and is trying to send a PRIVMSG message to that channel.
 * @author Jared Patton
 *
 */
public class CannotSendToChan {

}

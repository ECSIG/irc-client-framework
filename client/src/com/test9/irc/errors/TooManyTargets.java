package com.test9.irc.errors;

/**
 * 407
 * ERR_TOOMANYTARGETS
 * "<target> :Duplicate recipients. No message delivered"
 * - Returned to a client which is attempting to send PRIVMSG/NOTICE
 *  using the user@host destination format and for a user@host
 *  which has several occurrences.
 * @author Jared Patton
 *
 */
public class TooManyTargets {

}

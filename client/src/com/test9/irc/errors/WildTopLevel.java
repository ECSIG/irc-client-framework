package com.test9.irc.errors;

/**
 * 414
 * ERR_WILDTOPLEVEL
 * "<mask> :Wildcard in toplevel domain"
 * - 412 - 414 are returned by PRIVMSG to indicate that
 *  the message wasn't delivered for some reason.
 *  ERR_NOTOPLEVEL and ERR_WILDTOPLEVEL are errors that
 *  are returned when an invalid use of "PRIVMSG $<server>" 
 *  or "PRIVMSG #<host>" is attempted.
 * @author Jared Patton
 *
 */
public class WildTopLevel {

}

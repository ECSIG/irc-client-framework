package com.test9.irc.newEngine;

public class IRCUtil implements IRCConstants {
	
	public IRCUtil() {
		
	}

	/**
	 * Parses a <code>String</code> to an <code>int</code> via
	 * <code>Integer.parseInt</code> but avoids the
	 * <code>NumberFormatException</code>.
	 * @param str The <code>String</code> to parse.
	 * @return The parsed new <code>int</code>. <code>-1</code> if
	 *         <code>NumberFormatException</code> was thrown. 
	 */
	public static int parseInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException exc) {
			return -1;
		}
	}

}

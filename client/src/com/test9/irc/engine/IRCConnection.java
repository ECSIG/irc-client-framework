package com.test9.irc.engine;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class IRCConnection {
	
	private Socket socket;
	protected byte level = 0;
	protected String host;
	protected int[] ports;
	private volatile BufferedReader in;
	private PrintWriter out;
	protected String encoding = "ISO-8859-1";
	private int timeout = 1000 * 60 * 15;
	private boolean colorsEnabled = false;
	private boolean pongAutomatic = true;
	private String pass;
	private String nick;
	private String realname;
	private String username;

}

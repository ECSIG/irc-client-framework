package com.test9.irc.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Properties;

import com.test9.irc.display.ChatWindow;
import com.test9.irc.display.EventAdapter;
import com.test9.irc.display.prefWins.NewServerConfigWindow;
import com.test9.irc.engine.SSL.SSLDefaultTrustManager;
import com.test9.irc.engine.SSL.SSLIRCConnection;


public class ConnectionEngine {

	private static ArrayList<IRCConnection> connections = new ArrayList<IRCConnection>();
	private static ChatWindow cw;

	public static String settingsDir = ""; 
	private String name = "", host = "", pass="", nick="", username="", realname="", encoding="";
	private int port;
	private boolean ssl;
	private boolean loadedConnection = false;

	public ConnectionEngine() throws IOException {

		System.out.println(System.getProperty("user.home"));
		String os = System.getProperty("os.name").toLowerCase();
		String userHome = System.getProperty("user.home");

		cw = new ChatWindow();
		cw.addChatWindowListener(new EventAdapter(cw, cw.getUtil()));

		loadConnection();

		//public IRCConnection(String host, int port, String pass, String nick, 
		//		String username, String realname, String encoding) 
	}

	public static void beginSSLIRCConnection(String name, String host, int port, String pass, 
			String nick, String username, String realname, String encoding) {
		SSLIRCConnection sslc = new SSLIRCConnection(name, host, port, pass, nick, 
				username, realname, encoding);
		connections.add(sslc);
		sslc.addIRCEventListener(new IRCEventAdapter(cw.getListener(), sslc));
		sslc.addTrustManager(new SSLDefaultTrustManager());
		cw.getListener().onJoinServer(sslc.getConnectionName());

		try {
			sslc.connect();
		} catch (IOException e) {
			System.err.println("Could not start connection ["+host+"]");
		}
	}

	public static void beginIRCConnection(String name, String host, int port, String pass, String nick, 
			String username, String realname, String encoding) {
		IRCConnection irc = new IRCConnection(name, host, port, pass, nick, 
				username, realname, encoding);
		connections.add(irc);
		cw.getListener().onJoinServer(irc.getConnectionName());
		irc.addIRCEventListener(new IRCEventAdapter(cw.getListener(), irc));
		try {
			irc.connect();
		} catch (IOException e) {
			System.err.println("Could not start connection ["+host+"]");
		}
	}


	


	/**
	 * @return the connection
	 */
	public static IRCConnection getConnection(String host) {
		System.out.println("getconnectionshost:"+host);
		for(IRCConnection temp : connections) {
			if(temp.getHost().equals(host)) {
				System.out.println("hostmatch");
				return temp;
			}
		}
		return null;
	}

	/**
	 * @return the cw
	 */
	public ChatWindow getCw() {
		return cw;
	}
}

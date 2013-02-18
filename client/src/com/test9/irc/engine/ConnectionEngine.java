package com.test9.irc.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.test9.irc.display.ChatWindow;
import com.test9.irc.display.EventAdapter;
import com.test9.irc.engine.SSL.SSLDefaultTrustManager;
import com.test9.irc.engine.SSL.SSLIRCConnection;


public class ConnectionEngine {

	private ArrayList<IRCConnection> connections = new ArrayList<IRCConnection>();
	private ChatWindow cw;
	private String host = "", pass="", nick="", username="", realname="", encoding="";
	private int port;
	private boolean ssl;

	public ConnectionEngine() throws IOException {

		cw = new ChatWindow();
		cw.addChatWindowListener(new EventAdapter(cw, cw.getUtil()));

		loadConnection();
		System.out.println(host);

		if(ssl) {
			SSLIRCConnection sslc = new SSLIRCConnection(host, port, pass, nick, 
					username, realname, encoding);
			connections.add(sslc);
			sslc.addIRCEventListener(new IRCEventAdapter(this, sslc));
			sslc.addTrustManager(new SSLDefaultTrustManager());
			cw.getListener().onJoinServer(sslc.getHost());

			sslc.connect();
		} else {
			IRCConnection temp0 = new IRCConnection(host, port, pass, nick, 
					username, realname, encoding);
			connections.add(temp0);
			cw.getListener().onJoinServer(temp0.getHost());
			temp0.addIRCEventListener(new IRCEventAdapter(this, temp0));
			temp0.connect();
		}
		//public IRCConnection(String host, int port, String pass, String nick, 
		//		String username, String realname, String encoding) 
	}

	private void loadConnection() {
		String serverSettingsPath = "";
		InputStream stream = ConnectionEngine.class.getResourceAsStream("/resources/settings.txt");
		Scanner in = new Scanner(stream);
		try {
			serverSettingsPath = in.nextLine();
			in.close();
			stream.close();
			System.out.println(serverSettingsPath);
			loadConnectionSettings(serverSettingsPath);
		} catch (NoSuchElementException e) {
			try {stream.close();} catch (IOException e2) {}

			System.out.println("This must be the first run, choose where to put file.");
			PrintWriter out;
			try {
				System.out.println("should ask for a file path");
				System.out.println(ConnectionEngine.class.getResource("/resources/settings.txt").getPath());
				out = new PrintWriter(new File(
						ConnectionEngine.class.getResource("/resources/settings.txt").getPath()));
				String nssp = newServerSettingsPath();
				if(nssp != null)
					serverSettingsPath = nssp + "/jirccsettings.txt";

				out.close();
				writeNewServerSettingsFile(serverSettingsPath);
				JOptionPane.showMessageDialog(null, "The client will now close, go to "+serverSettingsPath +
						" to input the settings you want to use." +
						"The file will be named \"ircsettings.txt\".");
				System.exit(0);
			} catch (FileNotFoundException e1) {}
		} catch (IOException e) {}
	}

	private void loadConnectionSettings(String ssp) {
		File file = new File(ssp);
		String temp = "";
		try {
			Scanner in = new Scanner(file);
			temp = in.nextLine();
			temp = in.nextLine();
			host = temp.substring(temp.indexOf("= ")+2, temp.length()).trim();

			temp = in.nextLine();
			String temp0 = temp.substring(temp.indexOf("= ")+2, temp.length()).trim();
			port = Integer.valueOf(temp0);

			temp = in.nextLine();
			pass = temp.substring(temp.indexOf("= ")+2, temp.length()).trim();

			temp = in.nextLine();
			nick = temp.substring(temp.indexOf("= ")+2, temp.length()).trim();

			temp = in.nextLine();
			username = temp.substring(temp.indexOf("= ")+2, temp.length()).trim();

			temp = in.nextLine();
			realname = temp.substring(temp.indexOf("= ")+2, temp.length()).trim();

			temp = in.nextLine();
			encoding = temp.substring(temp.indexOf("= ")+2, temp.length()).trim();

			temp = in.nextLine();
			temp0 = temp.substring(temp.indexOf("= ")+2, temp.length()).trim();
			ssl = Boolean.parseBoolean(temp0);
		} catch (FileNotFoundException e) {}
	}

	private void writeNewServerSettingsFile(String path) {
		try {
			PrintWriter pw = new PrintWriter(new File(path));
			pw.println("#Make sure to put in a space between the '=' and a variables value.");
			pw.println("host = ");
			pw.println("port = ");
			pw.println("pass = ");
			pw.println("nick = ");
			pw.println("userName = ");
			pw.println("realName = ");
			pw.println("encoding = UTF-8");
			pw.println("ssl = ");
			pw.close();

		} catch (FileNotFoundException e) {System.err.println("error writing new settings file");}

	}

	private String newServerSettingsPath() {
		System.out.println("newServerSettingsPath");
		JFrame frame = new JFrame();
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if(chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION)
			return chooser.getSelectedFile().getAbsolutePath();
		else 
			return null;
	}

	/**
	 * @return the connection
	 */
	public IRCConnection getConnection(String host) {
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

	/**
	 * @param cw the cw to set
	 */
	public void setCw(ChatWindow cw) {
		this.cw = cw;
	}
}

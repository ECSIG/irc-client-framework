package com.test9.irc.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import com.test9.irc.engine.SSL.SSLIRCConnection;

public class LoadSettings {
	private ArrayList<IRCConnection> connections = new ArrayList<IRCConnection>();

	private void loadDirectories() {
		System.out.println(System.getProperty("user.home"));
		String os = System.getProperty("os.name").toLowerCase();
		String userHome = System.getProperty("user.home");
		String settingsDir;
		
		if(os.contains("mac os x")) {
			settingsDir = userHome+"/Library/Application Support/JIRCC";
		} else if (os.contains("windows")) {
			settingsDir = userHome+"\\Documents\\JIRCC";
		} else if (os.contains("linux")) {
			settingsDir = userHome + "/JIRCC/connections";
		} else if (os.contains("bsd")) {
			settingsDir = userHome +"/JIRCC/connections";
		}

		File dir = new File(settingsDir);
		System.out.println(dir.getAbsolutePath());
	}
	private void loadConnection() {


		Properties properties = new Properties();

		File connectionsDir = new File(settingsDir+ClientConstants.fileSeparator+"connections");

		if(!connectionsDir.exists()){
			System.out.println("making new connecitons directory");
			connectionsDir.mkdir();
		}

		File[] files = new File(connectionsDir.getPath()).listFiles();
		for(File n : files) {
			if(Character.isLetterOrDigit(n.getName().charAt(0))) {
			//	loadedConnection = true;
				System.out.println(n.getAbsolutePath());
				FileInputStream in;
				try {
					in = new FileInputStream(n);
					properties.load(in);
					in.close();
				} catch (FileNotFoundException e) {
					System.err.println("You seem to not have a settings file. Please create one.");
				} catch (IOException e) {
					System.err.println("Error loading file into properties.");
				}

				String name = properties.getProperty("name", "");
				String host = properties.getProperty("host", "");
				String pass = properties.getProperty("pass", "");
				String nick = properties.getProperty("nick", "");
				String username = properties.getProperty("username", "");
				String realname = properties.getProperty("realname", "");
				String encoding = properties.getProperty("encoding", "");
				int port = Integer.parseInt(properties.getProperty("port"));
				boolean ssl = Boolean.parseBoolean(properties.getProperty("ssl"));

				if(ssl) {
					new IRCConnection(name, host, port, pass, nick, 
							username, realname, encoding);
				} else {
					new SSLIRCConnection(name, host, port, pass, nick, 
							username, realname, encoding);
				}
			}
		}
	}
}

package com.test9.irc.engine;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import com.test9.irc.display.DisplayKeyConsts;
import com.test9.irc.engine.SSL.SSLIRCConnection;

public class LoadSettings {

	private String settingsDir;

	private ArrayList<IRCConnection> servers = new ArrayList<IRCConnection>();

	public LoadSettings(String settingsDir) {
		this.settingsDir = settingsDir;
		loadServers();
		loadGui();
	}

	private void loadServers() {

	}

	private void loadGui() {
		File programStateDir = new File(ConnectionEngine.settingsDir+ClientConstants.fileSeparator+
				fileDirectory);
		if(!programStateDir.exists()) {
			programStateDir.mkdir();
		}
		File settingsFile = new File(programStateDir.getPath()+
				ClientConstants.fileSeparator+"windowState.txt");	

		if(!settingsFile.exists()) {
			try {settingsFile.createNewFile();} catch (IOException e) {}
			loadSettings = false;
			saveWindowState();
		}
		Properties properties = new Properties();
		FileInputStream in;

		if(loadSettings) {
			try {
				in = new FileInputStream(settingsFile);
				properties.load(in);
				in.close();
			} catch (FileNotFoundException e) {
				System.err.println("Error loading windowState.txt.");
			} catch (IOException e) {
				System.err.println("Error reading windowState.txt.");
			}
			frame.setLocation(new Point(
					Integer.valueOf(properties.getProperty(DisplayKeyConsts.WINDOW_LOCATION_X)), 
					Integer.valueOf(properties.getProperty(DisplayKeyConsts.WINDOW_LOCATION_Y))
					));
			frame.setPreferredSize(new Dimension(Integer.valueOf(properties.getProperty(DisplayKeyConsts.WINOW_WIDTH)),
					Integer.valueOf(properties.getProperty(DisplayKeyConsts.WINDOW_HEIGHT))));

			outputSplitPane.setDividerLocation(Integer.valueOf(properties.getProperty(DisplayKeyConsts.OUTPUT_SPLIT_PANE_LOCATION)));

			listsAndOutputSplitPane.setDividerLocation(Integer.valueOf(properties.getProperty(DisplayKeyConsts.LISTS_AND_OUTPUT_SPLIT_PANE_LOCATION)));
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

package com.test9.irc.display;

import com.test9.irc.newEngine.IRCConnection;
import com.test9.irc.parser.OutputFactory;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowStateListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;

public class ChatWindow extends Observable implements ComponentListener,
KeyListener, WindowStateListener, WindowFocusListener, PropertyChangeListener, 
ActionListener {

	private static final JFrame frame = new JFrame();
	private static final Toolkit KIT = Toolkit.getDefaultToolkit();
	private static final int SPLITPANEWIDTH = 4;
	private static final int DEFAULTSIDEBARWIDTH = 150;
	private static Dimension defaultWindowSize = new Dimension(
			KIT.getScreenSize().width / 2, KIT.getScreenSize().height / 2);
	private static ConnectionTree connectionTree;
	private static final JTextField inputField = new JTextField();
	private static JPanel centerJPanel = new JPanel(new BorderLayout());
	private static JPanel treePanel = new JPanel(new BorderLayout());
	private static JSplitPane sidePanelSplitPane, listsAndOutputSplitPane;
	private static final JLayeredPane userListsLayeredPane = new JLayeredPane();
	private static final JLayeredPane outputFieldLayeredPane = new JLayeredPane();
	private static ArrayList<OutputPanel> outputPanels = new ArrayList<OutputPanel>();
	private static ArrayList<UserListPanel> userListPanels = new ArrayList<UserListPanel>();
	private static String activeServer;
	private static String activeChannel;
	private static JScrollPane treeScrollPane;
	private static MenuBar menuBar;
	private static boolean joinedAServer = false;
	private static ArrayList<IRCConnection> ircConnections = new ArrayList<IRCConnection>();
	private static OutputFactory oF = new OutputFactory();
	private static ArrayList<Title> titles = new ArrayList<Title>();

	/**
	 * Initializes a new ChatWindow
	 * @param initialServerName
	 * @param outputManager
	 */
	public ChatWindow(String initialServerName)
	{
		/*
		 * Checks to see if the global OS X menu bar should be used.
		 */
		if(System.getProperty("os.name").equals("Mac OS X"))
		{
			System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "AOSIDHOAISHDOAISHDOIASHD");
			menuBar = new MenuBar();
			frame.setJMenuBar(menuBar);
		}

		/*
		 * Adds some general features to the frame.
		 */
		frame.addKeyListener(this);
		frame.setTitle(initialServerName);
		frame.addComponentListener(this);
		frame.addWindowFocusListener(this);
		frame.setPreferredSize(defaultWindowSize);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);

		/*
		 * Sets up the connection tree that will list all server
		 * and channel connections.
		 */
		connectionTree = new ConnectionTree(initialServerName, this);
		treeScrollPane = new JScrollPane(connectionTree);
		treePanel.add(treeScrollPane, BorderLayout.CENTER);

		//joinServer(initialServerName);

		// Adds the required keylistener to the input field.
		inputField.addKeyListener(this);

		/*
		 * Adds the outputLayeredPane and the input field to the 
		 * center panel.
		 */
		centerJPanel.add(outputFieldLayeredPane);
		centerJPanel.add(inputField, BorderLayout.SOUTH);

		/*
		 * Sets up the side panel with a vertial split (one item on
		 * top of the other) and adds the userListLayeredPane and the 
		 * connection tree.
		 */
		sidePanelSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
				userListsLayeredPane, treePanel);
		sidePanelSplitPane.setDividerSize(SPLITPANEWIDTH);
		sidePanelSplitPane.setDividerLocation((frame.getPreferredSize().height/2)-20);
		sidePanelSplitPane.setContinuousLayout(true);

		/*
		 * Sets up the split pane that splits the side panel
		 * and the main output/input area.
		 */
		listsAndOutputSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				centerJPanel, sidePanelSplitPane);
		listsAndOutputSplitPane.setContinuousLayout(true);
		listsAndOutputSplitPane.setDividerSize(SPLITPANEWIDTH);
		listsAndOutputSplitPane.setDividerLocation(
				frame.getPreferredSize().width-DEFAULTSIDEBARWIDTH);


		/*
		 * Adds the property changed listener to catch resizing
		 * events of the frame.
		 */
		sidePanelSplitPane.addPropertyChangeListener(this);
		listsAndOutputSplitPane.addPropertyChangeListener(this);
		listsAndOutputSplitPane.setResizeWeight(1);

		/*
		 * Adds the split pane that contains all the other components
		 * to the frame.
		 * Packs it and gives the inputField focus.
		 * Presents the packed frame to the user.
		 */
		frame.add(listsAndOutputSplitPane, BorderLayout.CENTER);
		frame.pack();
		inputField.requestFocus();
		frame.setVisible(true);
	}

	/**
	 * Called when a server is joined. 
	 * Takes in the name of the server that is being joined.
	 * @param server
	 */
	public void joinServer(String server)
	{
		joinChannel(server);
		connectionTree.newServerNode(server);
		//titles.add(new String());
		joinedAServer = true;
		activeServer = server;
	}

	/**
	 * Called when the user leaves a server.
	 * Takes in the name of the server that the user is leaving.
	 * @param server
	 */
	public void leaveServer(String server)
	{
		for(OutputPanel oPanel: outputPanels)
		{
			if(oPanel.getServer().equals(server))
			{
				outputFieldLayeredPane.remove(oPanel);
				outputPanels.remove(oPanel);
			}
		}
		for(UserListPanel uLPanel: userListPanels)
		{
			if(uLPanel.getServer().equals(server))
			{
				userListsLayeredPane.remove(uLPanel);
				userListPanels.remove(uLPanel);
			}
		}
		connectionTree.removeServerNode(server);
	}

	/**
	 * Called when a user wishes to join a channel on a server
	 * that has already been connected to.
	 * @param server
	 * @param channel
	 * @param isServer
	 */
	public void joinChannel(String server, String channel)
	{
		titles.add(new Title(server, channel));
		activeChannel = channel;
		newOutputPanel(server, channel);
		newUserListPanel(server, channel);
		connectionTree.newChannelNode(server, channel);
	}

	/**
	 * Constructs a channel for a server connection. Does not join
	 * any channels on the server.
	 * @param server
	 */
	private void joinChannel(String server)
	{
		activeChannel = server;
		titles.add(new Title(server, server));
		newOutputPanel(server, server);
		newUserListPanel(server, server);

	}

	/**
	 * Called when the user leaves a channel on a particular server.
	 * Takes in the server the channel is on, and the channel name.
	 * @param server
	 * @param channel
	 */
	public void leaveChannel(String server, String channel)
	{
		outputPanels.remove(findChannel(server, channel, 0));
		userListPanels.remove(findChannel(server, channel, 1));
		connectionTree.removeChannelNode(server, channel);
	}

	/**
	 * Called when a message is received. It takes in the server name, 
	 * the channel name, and the actual message.
	 * ChatWindow has a channel for the server connection itself that is of the same
	 * name as the server. The server channel should receive messages that are command
	 * responses.
	 * @param server
	 * @param channel
	 * @param message
	 * @throws BadLocationException 
	 */
	public void newMessage(String server, String channel, String message) 
	{
		if(findChannel(server, channel,0) != -1)
		{
			outputPanels.get(findChannel(server, channel, 0)).newMessage(message);
		}
		else
			System.err.println("Cound not find channel to append message to.");
		
	}

	/**
	 * Called when a message is received. It takes in the server name, 
	 * the channel name, and the actual message.
	 * ChatWindow has a channel for the server connection itself that is of the same
	 * name as the server. The server channel should receive messages that are command
	 * responses.
	 * @param server
	 * @param channel
	 * @param message
	 */
	public void newMessage(String server, String channel, String nick, String message)
	{
		if(findChannel(server, channel,0) != -1)
		{
			outputPanels.get(findChannel(server, channel, 0)).newMessage(nick, message);
		}
		else
			System.err.println("Cound not find channel to append message to.");
	}

	/**
	 * Called when a new user joins a channel. Takes in the server name, 
	 * the channel, and the user's nick.
	 * @param server
	 * @param channel
	 * @param user
	 */
	public void userJoin(String server, String channel, String user)
	{
		if(findChannel(server, channel,1) != -1)
			userListPanels.get(findChannel(server, channel,1)).newUser(user);
		else
			System.err.println("[ChatWindowError] Cound not find channel to add new user.");
	}

	/**
	 * Called when a user leaves a channel. Takes in the server name, channel 
	 * name, and the users nick.
	 * @param server
	 * @param channel
	 * @param user
	 */
	public void userPart(String server, String channel, String user)
	{
		if(findChannel(server,channel,1) != -1)
			userListPanels.get(findChannel(server, channel,1)).userPart(user);
		else
			System.err.println("Cound not find channel to add new user.");
	}

	/**
	 * Called when a user quits on an irc server.
	 * @param server
	 * @param nick
	 */
	public void userQuit(String server, String nick, String reason)
	{
		for(UserListPanel u : userListPanels)
		{
			if(u.getServer().equals(server)) {

				if(u.getListModel().contains(nick))
				{
					u.userPart(nick);
					outputPanels.get(findChannel(server, u.getChannel(), 0)).newMessage(
							nick +" "+ reason);
				}
			}
		}
	}

	/**
	 * Called when a user changes their nick.
	 * @param oldNick
	 * @param newNick
	 */
	public void nickChange(String oldNick, String newNick)
	{
		for(UserListPanel u : userListPanels)
		{
			u.nickChange(oldNick, newNick);
		}
	}

	public void newTopic(String server, String channel, String topic) {
		titles.get(findTitle(server, channel)).setTopic(topic);
		frame.setTitle(titles.get(findTitle(activeServer, activeChannel)).getFullTitle());
	}

	public void newUserMode(String server, String channel, String mode) {

	}


	/**
	 * Used to send a message to a particular server and channel.
	 */
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{	
			String m = inputField.getText();
			if(ircConnections.get(findIRCConnection()).send(oF.formatMessage(m, activeChannel))&&!m.equals(""))
			{
				if(m.startsWith("/")) {
					newMessage(activeServer, activeServer, m);
				} else {
					newMessage(activeServer, activeChannel, "me", m);
				}
			}
			inputField.setText("");
		}
	}

	/**
	 * This method must be called each time a channel or server is joined or connected to.
	 * @param channel
	 */
	public void newOutputPanel(String server, String channel)
	{
		OutputPanel newOutputPanel = new OutputPanel(server, channel, 
				(int) outputFieldLayeredPane.getSize().getWidth(),
				(int) outputFieldLayeredPane.getSize().getHeight());

		outputPanels.add(newOutputPanel);
		outputFieldLayeredPane.add(newOutputPanel);
	}

	/**
	 * This needs to be called alone with newOutputPanel
	 */
	public void newUserListPanel(String server, String channel)
	{
		UserListPanel newUserListPanel = new UserListPanel(server, channel,
				(int) userListsLayeredPane.getSize().getWidth(),
				(int) userListsLayeredPane.getSize().getHeight());
		userListPanels.add(newUserListPanel);
		userListsLayeredPane.add(newUserListPanel);
	}


	/**
	 * Finds the appropriate channel for a given action.
	 * 
	 * @param server
	 * @param channel
	 * @param type (0 is for outputPanels, 1 for userListPanels)
	 * @return
	 */
	private int findChannel(String server, String channel, int type)
	{
		boolean found = false;
		int i = 0;
		if(type==0)
		{
			while(!found && i < outputPanels.size())
			{
				if(outputPanels.get(i).getServer().equals(server) && 
						outputPanels.get(i).getChannel().equals(channel))
				{
					found = true;
					return i;
				}
				else 
					i++;
			}
		}
		else if (type==1)
		{
			while(!found && i < userListPanels.size())
			{
				if(userListPanels.get(i).getServer().equals(server) && 
						userListPanels.get(i).getChannel().equals(channel))
				{
					found = true;
					return i;
				}	
				else i++;
			}
		}
		return -1;
	}

	private synchronized int findIRCConnection()
	{
		boolean found = false;
		int index = 0;

		while(!found && index < ircConnections.size())
		{
			if(ircConnections.get(index).getHost().equals(activeServer))
			{
				found = true;	
				return index;
			}
			else
				index++;
		}
		System.err.println("Error finding channel while sending message");
		return -1;
	}

	private static synchronized int findTitle(String server, String channel)
	{
		boolean found = false;
		int index = 0;
		while(!found && index < titles.size())
		{
			if(titles.get(index).getServer().equals(server))
			{
				if(titles.get(index).getChannel().equals(channel))
				{
					found = true;	
					return index;

				}
				else
					index++;
			}
			else
				index++;
		}
		System.err.println("Error finding title you were looking for");
		return -1;

	}


	@Override
	public void componentResized(ComponentEvent e) 
	{
		listsAndOutputSplitPane.setDividerLocation(frame.getWidth()-DEFAULTSIDEBARWIDTH);
		sidePanelSplitPane.setDividerLocation((frame.getHeight()/2)-20);

		//		if(joinedAServer) {
		//			OutputPanel.setNewBounds(outputFieldLayeredPane.getWidth(), 
		//					outputFieldLayeredPane.getHeight());
		//
		//			UserListPanel.setNewBounds(userListsLayeredPane.getWidth(), 
		//					userListsLayeredPane.getHeight());
		//		}
		//		
		//		treeScrollPane.setBounds(0, 0, treePanel.getWidth(), treePanel.getHeight());
		//
		//		for(OutputPanel t : outputPanels)
		//		{
		//			t.setBounds(OutputPanel.getBoundsRec());
		//			t.getScrollPane().getVerticalScrollBar().setValue(
		//					t.getScrollPane().getVerticalScrollBar().getMaximum());
		//		}
		//
		//		for(UserListPanel t: userListPanels)
		//		{
		//			t.setBounds(UserListPanel.getBoundsRec());
		//		}
		listsAndOutputSplitPane.invalidate();
		sidePanelSplitPane.invalidate();

		frame.invalidate();
	}

	public void propertyChange(PropertyChangeEvent evt) {
		if(joinedAServer)
		{
			if(evt.getSource() == listsAndOutputSplitPane)
			{
				OutputPanel.setNewBounds(outputFieldLayeredPane.getWidth(), 
						outputFieldLayeredPane.getHeight());
				UserListPanel.setNewBounds(userListsLayeredPane.getWidth(), 
						userListsLayeredPane.getHeight());

				for(OutputPanel t : outputPanels)
				{
					t.setBounds(OutputPanel.getBoundsRec());
					t.getScrollPane().getVerticalScrollBar().setValue(
							t.getScrollPane().getVerticalScrollBar().getMaximum());
					t.invalidate();

				}

				for(UserListPanel t: userListPanels)
				{
					t.setBounds(UserListPanel.getBoundsRec());
					t.invalidate();

				}
				treeScrollPane.setBounds(0, 0, treePanel.getWidth(), treePanel.getHeight());
				outputFieldLayeredPane.invalidate();
			}
			else if(evt.getSource() == sidePanelSplitPane)
			{
				UserListPanel.setNewBounds(userListsLayeredPane.getWidth(), 
						userListsLayeredPane.getHeight());

				for(UserListPanel t: userListPanels)
				{
					t.setBounds(UserListPanel.getBoundsRec());
					t.invalidate();
				}
				treeScrollPane.setBounds(0, 0, treePanel.getWidth(), treePanel.getHeight());

			}
		}


		frame.invalidate();
	}

	/**
	 * Called by the ConnectionTree class when a new node on the 
	 * tree is selected.
	 * Brings the appropriate output field and user lists
	 * to the front of their JLayeredPanes.
	 * @param activeServer
	 * @param activeChannel
	 */
	static void newActiveChannels(String activeServer, String activeChannel) {
		ChatWindow.activeServer = activeServer;
		ChatWindow.activeChannel = activeChannel;

		//frame.setTitle(activeServer + " " + activeChannel);

		for(OutputPanel t : outputPanels)
		{
			if(!t.getServer().equals(activeServer) || !t.getChannel().equals(activeChannel))
				//outputFieldLayeredPane.moveToBack(t);
				t.setVisible(false);
			else if(t.getServer().equals(activeServer) && t.getChannel().equals(activeChannel))
				t.setVisible(true);//outputFieldLayeredPane.moveToFront(t);
		}

		for(UserListPanel t : userListPanels)
		{
			if(!t.getServer().equals(activeServer) || !t.getChannel().equals(activeChannel))
				t.setVisible(false);
			else if(t.getServer().equals(activeServer) && t.getChannel().equals(activeChannel))
				t.setVisible(true);
		}	
		frame.setTitle(titles.get(findTitle(activeServer, activeChannel)).getFullTitle());
	}

	public void windowGainedFocus(WindowEvent e) {
		inputField.requestFocus();
	}

	@Override
	public void windowStateChanged(WindowEvent e) {

	}


	@Override
	public void keyTyped(KeyEvent e) {
		//System.out.println("keyTyped");
	}


	@Override
	public void keyPressed(KeyEvent e) {
		//System.out.println("keyPressed");
	}


	@Override
	public void componentMoved(ComponentEvent e) {
		//  Auto-generated method stub

	}


	@Override
	public void componentShown(ComponentEvent e) {
		//  Auto-generated method stub

	}


	@Override
	public void componentHidden(ComponentEvent e) {
		//  Auto-generated method stub

	}

	@Override
	public void windowLostFocus(WindowEvent e) {
		//  Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//  Auto-generated method stub

	}

	/**
	 * @return the ircConnections
	 */
	public static ArrayList<IRCConnection> getIrcConnections() {
		return ircConnections;
	}

	/**
	 * @param ircConnections the ircConnections to set
	 */
	public static void setIrcConnections(ArrayList<IRCConnection> ircConnections) {
		ChatWindow.ircConnections = ircConnections;
	}


}
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

public class ChatWindow extends Observable implements ComponentListener,
KeyListener, WindowStateListener, WindowFocusListener, PropertyChangeListener, 
ActionListener {

	/**
	 * The ultimate frame of the chat client that holds
	 * all the components.
	 */
	private static final JFrame frame = new JFrame();

	/**
	 * Tookit that is used to determind the default dimensions
	 * of the frame.
	 */
	private static final Toolkit KIT = Toolkit.getDefaultToolkit();

	/**
	 * Holds the width of the split pane bar
	 * that can be dragged.
	 */
	private static final int SPLITPANEWIDTH = 4;

	/**
	 * The default width of the side panel that will hold
	 * the user list and the connection tree.
	 */
	private static final int DEFAULTSIDEBARWIDTH = 150;

	/**
	 * Default window size of the JFrame calculated from the KIT.
	 * Makes the window half the side of the screen.
	 */
	private static Dimension defaultWindowSize = new Dimension(
			KIT.getScreenSize().width / 2, KIT.getScreenSize().height / 2);

	/**
	 * Holds all the channel and server connections used in the 
	 * JTree.
	 */
	private static ConnectionTree connectionTree;

	/**
	 * Input field to allow the user to input strings and send them to
	 * IRCConnection send().
	 */
	private static final JTextField inputField = new JTextField();

	/**
	 * Holds the center JLayered pane that will hold all of the 
	 * output panels.
	 */
	private static JPanel centerJPanel = new JPanel(new BorderLayout());

	/**
	 * Holds the tree that will list all server and channel connections.
	 */
	private static JPanel treePanel = new JPanel(new BorderLayout());

	/**
	 * Splits the connection tree up from the user list.
	 * User list on top and the connection tree on the 
	 * bottom.
	 */
	private static JSplitPane sidePanelSplitPane;

	/**
	 * Splits the sidePanelSplitPane from the output panels and the 
	 * input panel. Output/Input on the left, side bar on the east
	 * side.
	 */
	private static JSplitPane listsAndOutputSplitPane;

	/**
	 * Holds the user lists for each channel.
	 */
	private static final JLayeredPane userListsLayeredPane = new JLayeredPane();

	/**
	 * Holds the output panels for each channel and server
	 * connection.
	 */
	private static final JLayeredPane outputFieldLayeredPane = new JLayeredPane();

	/**
	 * Holds references to all of the output panels for the connected servers
	 * and channels.
	 */
	private static ArrayList<OutputPanel> outputPanels = new ArrayList<OutputPanel>();

	/**
	 * Holds references to all of the user lists for all of the connected 
	 * channels.
	 */
	private static ArrayList<UserListPanel> userListPanels = new ArrayList<UserListPanel>();

	/**
	 * Holds the name of the currectly active server. This is set by selecting a 
	 * server or channel node on the connectionTree.
	 */
	private static String activeServer;

	/**
	 * Holds the name of the currectly active server. This is set by selecting a 
	 * channel node on the connectionTree.
	 */
	private static String activeChannel;

	/**
	 * A scroll pane to contain the connection tree.
	 */
	private static JScrollPane treeScrollPane;

	/**
	 * Holds the main menubar for the client.
	 */
	private static MenuBar menuBar;

	/**
	 * Checks to see if a server has been joined.
	 */
	private static boolean joinedAServer = false;

	/**
	 * Holds references to each of the ircConnections. Is used when the 
	 * user wishes to send a message to a giver server or channel.
	 */
	private static ArrayList<IRCConnection> ircConnections = new ArrayList<IRCConnection>();

	/**
	 * Used to properly format a message that is sent to an IRCConnection.
	 */
	private static OutputFactory oF = new OutputFactory();

	/**
	 * Holds the possible titles that can be used on the frame.
	 */
	private static ArrayList<Title> titles = new ArrayList<Title>();

	/**
	 * Initializes a new ChatWindow.
	 * @param initialServerName Name of the server that is initially joined.
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

			// Initializes a new menu bar, ultimately should be constructed regardless
			// of the operating system.
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
	 * Must be called when a server is joined. It will create the default channel
	 * for console messages, add a server node to the connection tree and 
	 * make sure joinedAServer is set to true. Also sets the activeServer as the newly
	 * joined server.
	 * @param server The name of the server that is to be joined.
	 */
	public void joinServer(String server)
	{
		joinChannel(server);
		connectionTree.newServerNode(server);
		joinedAServer = true;
		activeServer = server;
	}

	/**
	 * Must be called when a server is left. It removes the appropriate
	 * outputPanel and userListPanel from the outputPanels and userListPanels
	 * array lists.
	 * @param server The name of the server that is to be left.
	 */
	public void leaveServer(String server)
	{
		/*
		 * Searches for the output panel in the outputPanels
		 * and then removes it from the layeredPane
		 * when the correct one is found. Also removes it from
		 * the ArrayList of outputPanels.
		 */
		for(OutputPanel oPanel: outputPanels)
		{
			if(oPanel.getServer().equals(server))
			{
				outputFieldLayeredPane.remove(oPanel);
				outputPanels.remove(oPanel);
			}
		}

		/*
		 * Searches for the userList panel in the userListPanels
		 * and then removes it from the layeredPane
		 * when the correct one is found. Also removes it from
		 * the ArrayList of userListPanels.
		 */
		for(UserListPanel uLPanel: userListPanels)
		{
			if(uLPanel.getServer().equals(server))
			{
				userListsLayeredPane.remove(uLPanel);
				userListPanels.remove(uLPanel);
			}
		}

		// Calls on the connectionTree to remove the appropriate server node.
		connectionTree.removeServerNode(server);
	}

	/**
	 * Called when a user wishes to join a channel on a server
	 * that has already been connected to. Adds the appropriate title
	 * choice, sets the channel to activeChannel and adds the appropriate
	 * user list and output panels. Finally adds the channel node to
	 * the connection tree.
	 * @param server Name of the server that the channel resides on.
	 * @param channel Name of the channel that is to be joined.
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
	 * any channels on the server. This is only called by joinServer()
	 * locally in the class.
	 * @param server Name of the server to join.
	 */
	private void joinChannel(String server)
	{
		activeChannel = server;
		titles.add(new Title(server, server));
		newOutputPanel(server, server);
		newUserListPanel(server, server);

	}

	/**
	 * Called when a user leaves a channel. It removes the 
	 * output panel, user list and the appropriate node from
	 * the connection tree.
	 * @param server Name of the server that is being left.
	 * @param channel Name of the channel that is being parted from.
	 */
	public void partChannel(String server, String channel)
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
	 * @param server The server that the channel is from.
	 * @param channel The channel the message is from.
	 * @param message The message that was received. 
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
	 * @param server The server the message is from.
	 * @param channel The channelt that the user joined.
	 * @param user The nick of the user that joined.
	 */
	public void userJoin(String server, String channel, String nick)
	{
		if(findChannel(server, channel,1) != -1)
			userListPanels.get(findChannel(server, channel,1)).newUser(nick);
		else
			System.err.println("[ChatWindowError] Cound not find channel to add new user.");
	}

	/**
	 * Called when a user leaves a channel. Takes in the server name, channel 
	 * name, and the users nick.
	 * @param server Server that the user parted on.
	 * @param channel Channel that the user parted from.
	 * @param user The nick of the user.
	 */
	public void userPart(String server, String channel, String nick)
	{
		if(findChannel(server,channel,1) != -1)
			userListPanels.get(findChannel(server, channel,1)).userPart(nick);
		else
			System.err.println("Cound not find channel to add new user.");
	}

	/**
	 * Called when a user quits on an irc server.
	 * @param server Server that the user quit from.
	 * @param nick Nick of the quitting user.
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
	 * @param oldNick The original nick of the user.
	 * @param newNick The new nick of a user.
	 */
	public void nickChange(String oldNick, String newNick)
	{
		for(UserListPanel u : userListPanels)
		{
			u.nickChange(oldNick, newNick);
		}
	}

	/**
	 * Adds a new topic to the topics ArrayList.
	 * @param server The name of the server the topic came from.
	 * @param channel The channel that the topic is from.
	 * @param topic The topic of the channel/server.
	 */
	public void newTopic(String server, String channel, String topic) {
		titles.get(findTitle(server, channel)).setTopic(topic);
		frame.setTitle(titles.get(findTitle(activeServer, activeChannel)).getFullTitle());
	}

	public void newUserMode(String server, String channel, String mode) {

	}


	/**
	 * Used to send a message to a particular channel or server when the user hits 
	 * VK_ENTER in the inputfield. Sends the message to the active channel and 
	 * active server after it sends it to the output factory for formatting.
	 */
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{	
			String m = inputField.getText();
			if(ircConnections.get(findIRCConnection()).send(oF.formatMessage(m, activeChannel))&&!m.equals(""))
			{
				if(m.startsWith("/")) {
					// If a command was sent.
					newMessage(activeServer, activeServer, m);
				} else {
					// If a PRIVMSG was sent
					newMessage(activeServer, activeChannel, "me", m);
				}
			}
			// Resets the text in the input field.
			inputField.setText("");
		}
	}

	/**
	 * Creates a new output panel for a server or channel.
	 * Server and Channel name is the same if this is
	 * an output panel for a server.
	 * @param server Name of the server.
	 * @param channel Name of the channel.
	 */
	private void newOutputPanel(String server, String channel)
	{
		OutputPanel newOutputPanel = new OutputPanel(server, channel, 
				(int) outputFieldLayeredPane.getSize().getWidth(),
				(int) outputFieldLayeredPane.getSize().getHeight());

		outputPanels.add(newOutputPanel);
		outputFieldLayeredPane.add(newOutputPanel);
	}

	/**
	 * Creates a new user list for a channel.
	 * @param server Name of the server.
	 * @param channel Name of the channel.
	 */
	private void newUserListPanel(String server, String channel)
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
	 * @param server Server to be found.
	 * @param channel Channel to be found.
	 * @param type (0 is for outputPanels, 1 for userListPanels)
	 * @return Returns the index of the outputPanel or userListPanel
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

	/**
	 * Finds an IRCConnection from the arrayList of IRCConnections.
	 * @return The index of the IRCConnection in the ArrayList.
	 */
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

	/**
	 * Finds the title that is for a given server and channel.
	 * @param server Name of the server.
	 * @param channel Name of the channel.
	 * @return The index of the title in the ArrayList.
	 */
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
	 * @param activeServer The name of the server that the user has selected
	 * in the connectionTree.
	 * @param activeChannel The name of the channel that the user has selected
	 * in the connection tree.
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
package com.test9.irc.display;

import com.test9.irc.engine.InputManager;
import com.test9.irc.parser.Message;

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
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

public class ChatWindow extends Observable implements ComponentListener,
KeyListener, WindowStateListener, WindowFocusListener, PropertyChangeListener, 
ActionListener, Observer {

	private static final JFrame frame = new JFrame();
	private static final Toolkit KIT = Toolkit.getDefaultToolkit();
	private static final int SPLITPANEWIDTH = 4;
	private Dimension defaultWindowSize = new Dimension(
			KIT.getScreenSize().width / 2, KIT.getScreenSize().height / 2);
	private static final int DEFAULTSIDEBARWIDTH = 150;
	private static ConnectionTree connectionTree;
	private static JTextField inputField = new JTextField();
	private static JPanel centerJPanel = new JPanel(new BorderLayout());
	private static JPanel treePanel = new JPanel(new BorderLayout());
	private static JSplitPane sidePanelSplitPane, listsAndOutputSplitPane;
	private static JLayeredPane userListsLayeredPane, outputFieldLayeredPane;
	private static ArrayList<OutputPanel> outputPanels = new ArrayList<OutputPanel>();
	private static ArrayList<UserListPanel> userListPanels = new ArrayList<UserListPanel>();
	private static String activeServer;
	private static String activeChannel;
	private static JScrollPane treeScrollPane;
	private static JMenuBar menuBar;


	/**
	 * Initializes a new ChatWindow
	 * @param initialServerName
	 * @param outputManager
	 */
	public ChatWindow(String initialServerName)
	{
		frame.addKeyListener(this);
		if(System.getProperty("os.name").equals("Mac OS X"))
		{
			menuBar = initMenuBar();
			frame.setJMenuBar(menuBar);
		}
		frame.setTitle(initialServerName);
		frame.addComponentListener(this);
		frame.addWindowFocusListener(this);
		frame.setPreferredSize(defaultWindowSize);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		
		userListsLayeredPane = new JLayeredPane();


		outputFieldLayeredPane = new JLayeredPane();
		
		userListsLayeredPane = new JLayeredPane();

		connectionTree = new ConnectionTree(initialServerName, this);
		System.out.println(connectionTree.toString());
		treeScrollPane = new JScrollPane(connectionTree);
		treePanel.add(treeScrollPane, BorderLayout.CENTER);
		joinServer(initialServerName);



		inputField.addKeyListener(this);
		centerJPanel.add(outputFieldLayeredPane);
		centerJPanel.add(inputField, BorderLayout.SOUTH);


		sidePanelSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
				userListsLayeredPane, treePanel);

		sidePanelSplitPane.setDividerSize(SPLITPANEWIDTH);
		sidePanelSplitPane.setDividerLocation((frame.getPreferredSize().height/2)-20);
		sidePanelSplitPane.setContinuousLayout(true);

		listsAndOutputSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				centerJPanel, sidePanelSplitPane);
		listsAndOutputSplitPane.setContinuousLayout(true);
		listsAndOutputSplitPane.setDividerSize(SPLITPANEWIDTH);

		listsAndOutputSplitPane.setDividerLocation(frame.getPreferredSize().width-DEFAULTSIDEBARWIDTH);


		sidePanelSplitPane.addPropertyChangeListener(this);
		listsAndOutputSplitPane.addPropertyChangeListener(this);

		frame.add(listsAndOutputSplitPane, BorderLayout.CENTER);


		frame.pack();
		inputField.requestFocus();
		frame.setVisible(true);
	}

	@SuppressWarnings("unused")
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof InputManager && arg instanceof Message) {
			Message m = ((Message) arg);

			// TODO nick change
		}
	}

	/**
	 * Called when a server is joined. 
	 * Takes in the name of the server that is being joined.
	 * @param server
	 */
	public void joinServer(String server)
	{
		connectionTree.newServerNode(server);
		joinChannel(server, server, true);
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
	 * Called when a user wishes to join a channel. This takes in
	 * the name of the server that the channel is on, the name 
	 * of the channel, and if being called outside of the ChatWindow
	 * class, isServer should always be false.
	 * @param server
	 * @param channel
	 * @param isServer
	 */
	public void joinChannel(String server, String channel, boolean isServer)
	{
		if(!isServer)
		{
			newOutputPanel(server, channel);
			newUserListPanel(server, channel);
			connectionTree.newChannelNode(server, channel);
		}
		else
		{
			newOutputPanel(server, channel);
			newUserListPanel(server, channel);
		}

	}

	/**
	 * Called when the user leaves a channel on a particular server.
	 * Takes in the server the channel is on, and the channel name.
	 * @param server
	 * @param channel
	 */
	public void leaveChannel(String server, String channel)
	{
		System.out.println("leaving channel");
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
	 */
	public void newMessage(String server, String channel, String message)
	{
		if(findChannel(server, channel,0) != -1)
			outputPanels.get(findChannel(server, channel, 0)).newMessage(message);
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
	public void newUser(String server, String channel, String user)
	{
		if(findChannel(server, channel,1) != -1)
			userListPanels.get(findChannel(server, channel,1)).newUser(user);
		else
			System.err.println("Cound not find channel to add new user.");
	}

	/**
	 * Called when a user leaves a channel. Takes in the server name, channel 
	 * name, and the users nick.
	 * @param server
	 * @param channel
	 * @param user
	 */
	public void deleteUser(String server, String channel, String user)
	{
		if(findChannel(server,channel,1) != -1)
			userListPanels.get(findChannel(server, channel,1)).deleteUser(user);
		else
			System.err.println("Cound not find channel to add new user.");
	}

	/**
	 * Used to send a message to a particular server and channel.
	 */
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			String m = inputField.getText();
			if(!m.startsWith("/"))
				newMessage(activeServer, activeChannel, "[ me ] "+m);
			setChanged();
			notifyObservers(m);

			inputField.setText("");
		}
	}

	@SuppressWarnings("unused")
	private JMenuBar initMenuBar() {
		JMenuBar newMenuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit");

		JMenuItem[] editMenuItems = {
				new JMenuItem("Cut"), new JMenuItem("Copy"), 
				new JMenuItem("Paste")
		}; 

		for(JMenuItem insert : editMenuItems)
			editMenu.add(insert);

		newMenuBar.add(editMenu);

		return newMenuBar;
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



	@Override
	public void componentResized(ComponentEvent e) 
	{
		sidePanelSplitPane.setDividerLocation((frame.getHeight()/2)-20);
		listsAndOutputSplitPane.setDividerLocation(frame.getWidth()-DEFAULTSIDEBARWIDTH);

		OutputPanel.setNewBounds(outputFieldLayeredPane.getWidth(), 
				outputFieldLayeredPane.getHeight());

		UserListPanel.setNewBounds(userListsLayeredPane.getWidth(), 
				userListsLayeredPane.getHeight());

		treeScrollPane.setBounds(0, 0, treePanel.getWidth(), treePanel.getHeight());

		for(OutputPanel t : outputPanels)
		{
			t.setBounds(OutputPanel.getBoundsRec());
			t.getScrollPane().getVerticalScrollBar().setValue(
					t.getScrollPane().getVerticalScrollBar().getMaximum());
		}

		for(UserListPanel t: userListPanels)
		{
			t.setBounds(UserListPanel.getBoundsRec());
		}

		frame.revalidate();
	}

	public void propertyChange(PropertyChangeEvent evt) {
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
			}

			for(UserListPanel t: userListPanels)
			{
				t.setBounds(UserListPanel.getBoundsRec());
			}
			treeScrollPane.setBounds(0, 0, treePanel.getWidth(), treePanel.getHeight());

		}
		else if(evt.getSource() == sidePanelSplitPane)
		{
			UserListPanel.setNewBounds(userListsLayeredPane.getWidth(), 
					userListsLayeredPane.getHeight());

			for(UserListPanel t: userListPanels)
			{
				t.setBounds(UserListPanel.getBoundsRec());
			}
			treeScrollPane.setBounds(0, 0, treePanel.getWidth(), treePanel.getHeight());

		}
		frame.revalidate();
	}

	@Override
	public void windowStateChanged(WindowEvent e) {
		System.out.println("windowStateChanged");
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
		// TODO Auto-generated method stub

	}


	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void windowGainedFocus(WindowEvent e) {
		inputField.requestFocus();
	}


	@Override
	public void windowLostFocus(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the activeServer
	 */
	public static String getActiveServer() {
		return activeServer;
	}

	/**
	 * @param activeServer the activeServer to set
	 */
	public static void setActiveServer(String activeServer) {
		ChatWindow.activeServer = activeServer;
	}

	/**
	 * @return the activeChannel
	 */
	public static String getActiveChannel() {
		return activeChannel;
	}

	/**
	 * @param activeChannel the activeChannel to set
	 */
	public static void setActiveChannel(String activeChannel) {
		ChatWindow.activeChannel = activeChannel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public void newPanelSelections(String activeServer, String activeChannel) {
		
		frame.setTitle(activeServer + " " + activeChannel);
		
		for(OutputPanel t : outputPanels)
		{
			if(!t.getServer().equals(activeServer) || !t.getChannel().equals(activeChannel))
				outputFieldLayeredPane.moveToBack(t);
			else if(t.getServer().equals(activeServer) && t.getChannel().equals(activeChannel))
				outputFieldLayeredPane.moveToFront(t);
		}

		for(UserListPanel t : userListPanels)
		{
			if(!t.getServer().equals(activeServer) || !t.getChannel().equals(activeChannel))
				userListsLayeredPane.moveToBack(t);
			else if(t.getServer().equals(activeServer) && t.getChannel().equals(activeChannel))
				userListsLayeredPane.moveToFront(t);
		}		
	}
}
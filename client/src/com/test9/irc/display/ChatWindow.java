package com.test9.irc.display;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
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

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.Position;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class ChatWindow extends JFrame implements ComponentListener,
KeyListener, WindowStateListener, WindowFocusListener, PropertyChangeListener, 
TreeSelectionListener {

	private static final long serialVersionUID = -6373704295052845871L;
	private static final Toolkit KIT = Toolkit.getDefaultToolkit();
	private static final int SPLITPANEWIDTH = 4;
	private Dimension defaultWindowSize = new Dimension(
			KIT.getScreenSize().width / 2, KIT.getScreenSize().height / 2);
	private static final int DEFAULTSIDEBARWIDTH = 150;
	private static JTree channelTree;
	private static JTextField inputField = new JTextField();
	private static JPanel centerJPanel = new JPanel(new BorderLayout());
	private static JPanel treePanel = new JPanel(new BorderLayout());
	private static JScrollPane treeScrollPane;
	private static JSplitPane sidePanelSplitPane, listsAndOutputSplitPane;
	private static JLayeredPane userListsLayeredPane, outputFieldLayeredPane;
	private static ArrayList<OutputPanel> outputPanels = new ArrayList<OutputPanel>();
	private static ArrayList<UserListPanel> userListPanels = new ArrayList<UserListPanel>();
	private static String activeServer;
	private static String activeChannel;
	private static DefaultTreeModel model;
	private static DefaultMutableTreeNode root;


	public ChatWindow(String initialServerName)
	{
		setTitle(initialServerName);
		addComponentListener(this);
		addWindowFocusListener(this);
		setPreferredSize(defaultWindowSize);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(true);

		userListsLayeredPane = new JLayeredPane();


		outputFieldLayeredPane = new JLayeredPane();
		userListsLayeredPane = new JLayeredPane();
		initializeChannelTree(initialServerName);


		inputField.addKeyListener(this);
		centerJPanel.add(outputFieldLayeredPane);
		centerJPanel.add(inputField, BorderLayout.SOUTH);



		sidePanelSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
				userListsLayeredPane, treePanel);

		sidePanelSplitPane.setDividerSize(SPLITPANEWIDTH);
		sidePanelSplitPane.setDividerLocation((this.getPreferredSize().height/2)-20);
		sidePanelSplitPane.setContinuousLayout(true);

		listsAndOutputSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				centerJPanel, sidePanelSplitPane);
		listsAndOutputSplitPane.setContinuousLayout(true);
		listsAndOutputSplitPane.setDividerSize(SPLITPANEWIDTH);

		listsAndOutputSplitPane.setDividerLocation(this.getPreferredSize().width-DEFAULTSIDEBARWIDTH);


		sidePanelSplitPane.addPropertyChangeListener(this);
		listsAndOutputSplitPane.addPropertyChangeListener(this);

		add(listsAndOutputSplitPane, BorderLayout.CENTER);


		pack();
		inputField.requestFocus();
		setVisible(true);
	}

	private void initializeChannelTree(String initialServerName)
	{
		root = new DefaultMutableTreeNode("root");
		model = new DefaultTreeModel(root);
		channelTree = new JTree(model);
		channelTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		channelTree.addTreeSelectionListener(this);
		treeScrollPane = new JScrollPane(channelTree);
		treePanel.add(treeScrollPane, BorderLayout.CENTER);

		joinServer(initialServerName);
	}

	/**
	 * Must be called when a channel is joined on a particular server (or the server itself).
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
			newChannelNode(server, channel);
		}
		else
		{
			newOutputPanel(server, channel);
			newUserListPanel(server, channel);
		}
	}

	/**
	 * Called when a server is joined. Updates the gui.
	 * @param server
	 */
	public void joinServer(String server)
	{
		newServerNode(server);
		joinChannel(server, server, true);
	}

	/**
	 * Used to add the new channel node to the JTree channelTree.
	 * @param server
	 * @param channel
	 */
	private void newChannelNode(String server, String channel)
	{

		DefaultMutableTreeNode newChannelNode = new DefaultMutableTreeNode(channel);
		newChannelNode.setAllowsChildren(false);
		TreePath path = channelTree.getNextMatch(server, 0, Position.Bias.Forward);

		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) path.getLastPathComponent();

		
		model.insertNodeInto(newChannelNode, parentNode, parentNode.getChildCount());
		channelTree.expandPath(path);

	}

	/**
	 * Used to add a new servers parent node.
	 * @param server
	 */
	private void newServerNode(String server)
	{
		DefaultMutableTreeNode newServerNode = new DefaultMutableTreeNode(server.trim());
		newServerNode.setAllowsChildren(true);
		root.add(newServerNode);
		
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
	 * This method is used to update the GUI with new messages from the server for a 
	 * parcitular channel.
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
	 * When a user joins a channel this method is used to update
	 * the user list for that channel.
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
	 * When a user leaves a channel this method is used to 
	 * update the user list for that channel.
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
	 * Finds the appropriate channel for a given action.
	 * @param server
	 * @param channel
	 * @param type
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
				if(outputPanels.get(i).getServer().equals(server))
				{
					if(outputPanels.get(i).getChannel().equals(channel))
					{
						found = true;
						return i;
					}
				}	
				else i++;
			}
		}
		else if (type==1)
		{
			while(!found && i < userListPanels.size())
			{
				if(userListPanels.get(i).getServer().equals(server))
				{
					if(userListPanels.get(i).getChannel().equals(channel))
					{
						found = true;
						return i;
					}
				}	
				else i++;
			}
		}
		return -1;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			System.out.println("Message was sent");
			inputField.setText("");
		}
	}

	@Override
	public void componentResized(ComponentEvent e) 
	{
		sidePanelSplitPane.setDividerLocation((this.getHeight()/2)-20);
		listsAndOutputSplitPane.setDividerLocation(this.getWidth()-DEFAULTSIDEBARWIDTH);

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
	}

	@Override
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
	}

	/**
	 * Used by the tree to listen for when the user 
	 * changes the channel that is selected.
	 */
	public void valueChanged(TreeSelectionEvent e) {
		activeChannel = channelTree.getSelectionPath().getLastPathComponent().toString();
		activeServer = channelTree.getSelectionPath().getPathComponent(0).toString();

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

	@Override
	public void windowStateChanged(WindowEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

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


}

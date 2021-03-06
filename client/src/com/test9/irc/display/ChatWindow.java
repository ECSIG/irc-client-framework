package com.test9.irc.display;

import com.test9.irc.engine.ClientConstants;
import com.test9.irc.engine.ConnectionEngine;
import com.test9.irc.engine.IRCConnection;
import com.test9.irc.parser.OutputFactory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowStateListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

public class ChatWindow extends Observable implements ComponentListener,
KeyListener, WindowStateListener, WindowFocusListener, PropertyChangeListener, 
ActionListener, WindowListener {

	/**
	 * The ultimate frame of the chat client that holds
	 * all the components.
	 */
	private final JFrame frame = new JFrame();

	/**
	 * Toolkit that is used to determined the default dimensions
	 * of the frame.
	 */
	private static final Toolkit KIT = Toolkit.getDefaultToolkit();

	/**
	 * Holds the width of the split pane bar
	 * that can be dragged.
	 */
	private static final int SPLITPANEWIDTH = 2;

	/**
	 * The default width of the side panel that will hold
	 * the user list and the connection tree.
	 */
	private static final int DEFAULTSIDEBARWIDTH = 150;

	/**
	 * The default width of a scroll bar.
	 */
	private static final Dimension SCROLLBARDIM = new Dimension(9,0);

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
	private ConnectionTree connectionTree;

	/**
	 * Input field to allow the user to input strings and send them to
	 * IRCConnection send().
	 */
	private final JTextField inputField = new JTextField();

	/**
	 * Holds the center JLayered pane that will hold all of the 
	 * output panels.
	 */
	private JPanel centerPanel = new JPanel(new BorderLayout());

	/**
	 * Holds the tree that will list all server and channel connections.
	 */
	private JPanel treePanel = new JPanel(new BorderLayout());

	/**
	 * Splits the connection tree up from the user list.
	 * User list on top and the connection tree on the 
	 * bottom.
	 */
	private JSplitPane sidePanelSplitPane;

	/**
	 * Splits the sidePanelSplitPane from the output panels and the 
	 * input panel. Output/Input on the left, side bar on the east
	 * side.
	 */
	private JSplitPane listsAndOutputSplitPane;

	/**
	 * Holds the user lists for each channel.
	 */
	private final JLayeredPane userListsLayeredPane = new JLayeredPane();

	/**
	 * Holds the output panels for each channel and server
	 * connection.
	 */
	private final JLayeredPane outputFieldLayeredPane = new JLayeredPane();

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
	 * Holds the name of the currently active server. This is set by selecting a 
	 * channel node on the connectionTree.
	 */
	private static String activeChannel;

	/**
	 * A scroll pane to contain the connection tree.
	 */
	private JScrollPane treeScrollPane;

	/**
	 * Holds the main menubar for the client.
	 */
	private MenuBar menuBar;

	/**
	 * Checks to see if a server has been joined.
	 */
	private boolean joinedAServer = false;

	/**
	 * Holds references to each of the ircConnections. Is used when the 
	 * user wishes to send a message to a giver server or channel.
	 */
	private static ArrayList<IRCConnection> ircConnections = new ArrayList<IRCConnection>();

	/**
	 * Holds the possible titles that can be used on the frame.
	 */
	private static ArrayList<Title> titles = new ArrayList<Title>();

	private Listener listener;

	private Util util;

	private static ArrayList<String> messageBuffer = new ArrayList<String>();
	private static int bufferSelection = 0;

	private TerminalPanel terminalPanel;
	private JSplitPane outputSplitPane;

	private static String os;
	public static boolean hasMetaKey = false;

	private static ArrayList<String> serversAndChannels = new ArrayList<String>();

	private static int tabs = 0;
	private static String nickPrefix = "";
	private static boolean bufferedNickPrefix = false;
	private static ImageIcon img;
	private static final String fileDirectory = "windowState";
	private static boolean isOSX = false;

	private static CommandSender cs;

	/**
	 * Initializes a new ChatWindow.
	 * @param initialServerName Name of the server that is initially joined.
	 */
	public ChatWindow()
	{
		cs = new CommandSender(this);
		TextFormat.loadColors();

		img = new ImageIcon(getClass().getResource("elmotrans.png"));
		frame.setIconImage(img.getImage());

		util = new Util(this);
		/*
		 * Checks to see if the global OS X menu bar should be used.
		 */
		os = System.getProperty("os.name");
		if(os.equals("Mac OS X"))
		{
			setOSX(true);
			//SCROLLBARDIM = new Dimension(5,0);
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			hasMetaKey = true;
			// Initializes a new menu bar, ultimately should be constructed regardless
			// of the operating system.
		}

		MySystemTray.init(this);

		terminalPanel = new TerminalPanel();
		menuBar = new MenuBar(this);
		frame.setJMenuBar(menuBar);

		/*
		 * Adds some general features to the frame.
		 */
		frame.addKeyListener(this);
		frame.addComponentListener(this);
		frame.addWindowFocusListener(this);
		frame.setPreferredSize(defaultWindowSize);
		frame.setResizable(true);
		frame.addWindowStateListener(this);
		frame.addWindowListener(this);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


		/*
		 * Sets up the connection tree that will list all server
		 * and channel connections.
		 */
		connectionTree = new ConnectionTree(this);
		connectionTree.addKeyListener(this);
		treeScrollPane = new JScrollPane(connectionTree);
		treeScrollPane.addKeyListener(this);

		treePanel.add(treeScrollPane, BorderLayout.CENTER);
		treePanel.setMinimumSize(new Dimension(0,0));
		treePanel.addKeyListener(this);
		// Adds the required keylistener to the input field.
		inputField.addKeyListener(this);
		inputField.setMinimumSize(new Dimension(0,0));
		inputField.setFocusTraversalKeysEnabled(false);


		/*
		 * Adds the outputLayeredPane and the input field to the 
		 * center panel.
		 */
		centerPanel.add(outputFieldLayeredPane);
		centerPanel.add(inputField, BorderLayout.SOUTH);
		centerPanel.setBorder(null);
		centerPanel.addKeyListener(this);

		outputSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, centerPanel, terminalPanel);
		outputSplitPane.setDividerSize(SPLITPANEWIDTH);
		outputSplitPane.setContinuousLayout(true);
		outputSplitPane.setResizeWeight(1);
		outputSplitPane.setDividerSize(3);
		outputSplitPane.addKeyListener(this);
		outputSplitPane.setDividerLocation(frame.getPreferredSize().height/2);


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
		sidePanelSplitPane.setResizeWeight(0);
		sidePanelSplitPane.addKeyListener(this);

		/*
		 * Sets up the split pane that splits the side panel
		 * and the main output/input area.
		 */
		listsAndOutputSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				outputSplitPane, sidePanelSplitPane);
		listsAndOutputSplitPane.setContinuousLayout(true);
		listsAndOutputSplitPane.setDividerSize(SPLITPANEWIDTH);
		listsAndOutputSplitPane.setDividerLocation(
				frame.getPreferredSize().width-DEFAULTSIDEBARWIDTH);
		listsAndOutputSplitPane.addKeyListener(this);



		/*
		 * Adds the property changed listener to catch resizing
		 * events of the frame.
		 */
		sidePanelSplitPane.addPropertyChangeListener(this);
		listsAndOutputSplitPane.addPropertyChangeListener(this);
		outputSplitPane.addPropertyChangeListener(this);
		listsAndOutputSplitPane.setResizeWeight(1);
		loadColors();

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
		loadPreviousState();
		frame.pack();
	}

	private void loadPreviousState() {
		boolean loadSettings = true;
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
	}

	public void loadColors() {
		//frame.setBackground(Color.BLACK);
		//centerPanel.setBackground(Color.BLACK);
		terminalPanel.setBackground(Color.BLACK);
		treePanel.setBackground(Color.BLACK);
		inputField.setFont(TextFormat.font);
		inputField.setBackground(Color.BLACK);
		inputField.setForeground(Color.WHITE);
		inputField.setCaretColor(Color.WHITE);
		treeScrollPane.setBackground(Color.BLACK);
		//sidePanelSplitPane.setOpaque(true);
		listsAndOutputSplitPane.setOpaque(true);
		//listsAndOutputSplitPane.setBackground(Color.BLACK);
		userListsLayeredPane.setBackground(Color.BLACK);
		//sidePanelSplitPane.setBackground(Color.BLACK);
		//centerJPanel.setBackground(Color.BLACK);
		outputFieldLayeredPane.setBackground(Color.BLACK);


	}

	public void addChatWindowListener(Listener listener) {
		this.listener = listener;
	}

	/**
	 * Constructs a channel for a server connection. Does not join
	 * any channels on the server. This is only called by joinServer()
	 * locally in the class.
	 * @param server Name of the server to join.
	 */
	void joinServerChannel(String server)
	{
		activeChannel = server;
		titles.add(new Title(server, server));
		newOutputPanel(server, server);
		newUserListPanel(server, server);
		serversAndChannels.add(server+","+server);
	}
	void tabComplete() {
		String inputText = inputField.getText().trim();

		if(inputText.contains(" ") && !bufferedNickPrefix) {
			System.out.println(inputText.substring(inputText.lastIndexOf(" ")+1, inputText.length()));
			nickPrefix = inputText.substring(inputText.lastIndexOf(" ")+1, inputText.length());
			inputText = inputText.substring(0, inputText.lastIndexOf(" ")+1);
		} else if(inputText != null && inputText.length() >= 1 && !bufferedNickPrefix) {
			nickPrefix = inputText;
			inputText = "";
		} else if(inputText.contains(" ") && bufferedNickPrefix) {
			inputText = inputText.substring(0, inputText.lastIndexOf(" ")+1);
		} else if(inputText != null && inputText.length() >= 1) {
			inputText = "";
		}

		bufferedNickPrefix = true;

		String returnNick = tabCompleteNick(nickPrefix);
		inputText+=returnNick;
		inputField.setText(inputText+" ");

	}

	String tabCompleteNick(String prefix) {
		System.out.println("prefix:'"+prefix+"'");
		String string = util.findUserListPanel(activeServer, activeChannel).getTabComplete(nickPrefix, tabs);

		if(string == null) {
			tabs = 1;
			return tabCompleteNick(prefix);
		}

		return string;
	}

	/**
	 * Used to send a message to a particular channel or server when the user hits 
	 * VK_ENTER in the inputfield. Sends the message to the active channel and 
	 * active server after it sends it to the output factory for formatting.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getComponent() == inputField) {
			if(e.getKeyCode() != KeyEvent.VK_TAB) {
				tabs = 0;
				bufferedNickPrefix = false;
				nickPrefix = "";
			}
			if(e.getKeyCode() == KeyEvent.VK_TAB) {
				tabs++;
				tabComplete();
			}
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {

				String m = inputField.getText();
				messageBuffer.add(m);
				bufferSelection = messageBuffer.size();
				if(m.startsWith("/part")) {
					util.findActiveIRCConnection().send(
							OutputFactory.formatMessage(m+" "+activeChannel, activeChannel));
					System.out.println("ChatWindow:"+activeServer+" "+activeChannel);
					listener.onPartChannel(activeServer, activeChannel);
					inputField.setText("");
				}
				else if(util.findActiveIRCConnection().send(OutputFactory.formatMessage(
						m, activeChannel))&&!m.equals(""))
				{
					if(m.startsWith("/")) {
						String cmd = "";
						m = inputField.getText().trim();
						if(m.contains(" ")) {
							cmd = inputField.getText().substring(0, m.indexOf(" "));
						} else{
							cmd = inputField.getText();
						}
						if(cmd.equalsIgnoreCase("/join")) {
							listener.onJoinChannel(activeServer, m.substring(m.indexOf(" "), 
									m.length()).trim());
							inputField.setText("");
						}
						listener.onNewMessage(activeServer, activeServer, m, "REPLY");
					}else {
						IRCConnection temp = util.findActiveIRCConnection();
						String nick = util.findActiveIRCConnection().getNick();
						// If a PRIVMSG was sent
						listener.onNewPrivMessage(temp.getUser(temp.getNick()),
								activeServer, activeChannel, nick, m, true);
					}
				}
				// Resets the text in the input field.
				inputField.setText("");
			}

			if(e.getKeyCode() == KeyEvent.VK_UP) {
				if(bufferSelection > 0) {
					bufferSelection--;
					inputField.setText(messageBuffer.get(bufferSelection));
				}
			}

			if(e.getKeyCode() == KeyEvent.VK_DOWN)
			{
				if(bufferSelection < messageBuffer.size() -1 ) {
					bufferSelection++;
					inputField.setText(messageBuffer.get(bufferSelection));
				}

			}
		} // end input field?

		if(!hasMetaKey)
		{
			if(e.getModifiers() == InputEvent.CTRL_MASK) {
				if(Character.isDigit(e.getKeyChar())) {
					connectionTree.metaSelection(activeServer, Character.getNumericValue(e.getKeyChar())-1);		
				} 
			} else if(e.getModifiers() == InputEvent.ALT_MASK) {
				if(Character.isDigit(e.getKeyChar())) {
					connectionTree.metaSelection(activeServer, Character.getNumericValue(e.getKeyChar())-1);		
				} 
			}
		} else {
			if(e.getModifiers()== InputEvent.META_MASK) {
				if(Character.isDigit(e.getKeyChar())) {
					connectionTree.metaSelection(activeServer, Character.getNumericValue(e.getKeyChar())-1);		
				} // end digit?
			} // end key modifier meta?
		}
	}


	/**
	 * Creates a new output panel for a server or channel.
	 * Server and Channel name is the same if this is
	 * an output panel for a server.
	 * @param server Name of the server.
	 * @param channel Name of the channel.
	 */
	void newOutputPanel(String server, String channel)
	{
		OutputPanel newOutputPanel = new OutputPanel(server, channel, 
				(int) outputFieldLayeredPane.getSize().getWidth(),
				(int) outputFieldLayeredPane.getSize().getHeight(), this);
		newOutputPanel.addKeyListener(this);

		outputPanels.add(newOutputPanel);
		outputFieldLayeredPane.add(newOutputPanel);
	}

	/**
	 * Creates a new user list for a channel.
	 * @param server Name of the server.
	 * @param channel Name of the channel.
	 */
	void newUserListPanel(String server, String channel)
	{
		UserListPanel newUserListPanel = new UserListPanel(server, channel,
				(int) userListsLayeredPane.getSize().getWidth(),
				(int) userListsLayeredPane.getSize().getHeight(), this);
		newUserListPanel.addKeyListener(this);
		userListPanels.add(newUserListPanel);
		userListsLayeredPane.add(newUserListPanel);
	}

	@Override
	public void componentResized(ComponentEvent e) 
	{
		sidePanelSplitPane.setDividerLocation((frame.getHeight()/2)-20);

		listsAndOutputSplitPane.invalidate();
		sidePanelSplitPane.invalidate();

		frame.invalidate();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(joinedAServer)
		{
			if((evt.getSource() == listsAndOutputSplitPane) || (evt.getSource() == outputSplitPane))
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
	void newActiveChannels(String activeServer, String activeChannel, Boolean isListener) {
		ChatWindow.activeServer = activeServer;
		ChatWindow.activeChannel = activeChannel;

		for(OutputPanel t : outputPanels)
		{
			if(!t.getServer().equals(activeServer) || !t.getChannel().equals(activeChannel))
				t.setVisible(false);
			else if(t.getServer().equals(activeServer) && t.getChannel().equals(activeChannel))
				t.setVisible(true);
		}

		for(UserListPanel t : userListPanels)
		{
			if(!t.getServer().equals(activeServer) || !t.getChannel().equals(activeChannel))
				t.setVisible(false);
			else if(t.getServer().equals(activeServer) && t.getChannel().equals(activeChannel))
				t.setVisible(true);
		}	
		Title t = util.findTitle(activeServer, activeChannel);
		if(t != null) {
			frame.setTitle(t.getFullTitle());
		} else {
			frame.setTitle(titles.get(0).getFullTitle());
		}
	}

	@Override
	public void windowGainedFocus(WindowEvent e) {
		inputField.requestFocusInWindow();
	}

	@Override
	public void windowStateChanged(WindowEvent e) {

		sidePanelSplitPane.setDividerLocation((frame.getHeight()/2)-20);

		listsAndOutputSplitPane.invalidate();
		sidePanelSplitPane.invalidate();

		//		if(joinedAServer)
		//		{
		//				OutputPanel.setNewBounds(outputFieldLayeredPane.getWidth(), 
		//						outputFieldLayeredPane.getHeight());
		//				UserListPanel.setNewBounds(userListsLayeredPane.getWidth(), 
		//						userListsLayeredPane.getHeight());
		//
		//				for(OutputPanel t : outputPanels)
		//				{
		//					t.setBounds(OutputPanel.getBoundsRec());
		//					t.getScrollPane().getVerticalScrollBar().setValue(
		//							t.getScrollPane().getVerticalScrollBar().getMaximum());
		//					t.invalidate();
		//
		//				}
		//
		//				for(UserListPanel t: userListPanels)
		//				{
		//					t.setBounds(UserListPanel.getBoundsRec());
		//					t.invalidate();
		//
		//				}
		//				treeScrollPane.setBounds(0, 0, treePanel.getWidth(), treePanel.getHeight());
		//				outputFieldLayeredPane.invalidate();
		//			
		//			
		//		}
		frame.invalidate();
	}


	@Override
	public void keyTyped(KeyEvent e) {
		//System.out.println("keyTyped");
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}


	@Override
	public void componentMoved(ComponentEvent e) {

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
	}

	/**
	 * @return the ircConnections
	 */
	public ArrayList<IRCConnection> getIrcConnections() {
		return ircConnections;
	}

	/**
	 * @param ircConnections the ircConnections to set
	 */
	public void setIrcConnections(ArrayList<IRCConnection> ircConnections) {
		ChatWindow.ircConnections = ircConnections;
	}

	public void newMessageHighlight(String server, String channel, String nickname, String content) {
		OutputPanel op = util.findOutputPanel(server, channel);
		if(op != null) {
			op.newMessageHighlight(nickname, content);
			MySystemTray.notification("Highlight", "["+nickname+"] " + content);
		} else {
			System.err.println("Could not write newMessageHighlight");
		}
	}

	/**
	 * @return the activeServer
	 */
	public String getActiveServer() {
		return activeServer;
	}

	/**
	 * @param activeServer the activeServer to set
	 */
	public void setActiveServer(String activeServer) {
		ChatWindow.activeServer = activeServer;
	}

	/**
	 * @return the activeChannel
	 */
	public String getActiveChannel() {
		return activeChannel;
	}

	/**
	 * @param activeChannel the activeChannel to set
	 */
	public void setActiveChannel(String activeChannel) {
		ChatWindow.activeChannel = activeChannel;
	}

	/**
	 * @return the frame
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * @return the connectionTree
	 */
	public ConnectionTree getConnectionTree() {
		return connectionTree;
	}

	/**
	 * @return the userListsLayeredPane
	 */
	public JLayeredPane getUserListsLayeredPane() {
		return userListsLayeredPane;
	}

	/**
	 * @return the outputFieldLayeredPane
	 */
	public JLayeredPane getOutputFieldLayeredPane() {
		return outputFieldLayeredPane;
	}

	/**
	 * @return the outputPanels
	 */
	public ArrayList<OutputPanel> getOutputPanels() {
		return outputPanels;
	}

	/**
	 * @return the userListPanels
	 */
	public ArrayList<UserListPanel> getUserListPanels() {
		return userListPanels;
	}

	/**
	 * @return the titles
	 */
	public ArrayList<Title> getTitles() {
		return titles;
	}

	/**
	 * @param joinedAServer the joinedAServer to set
	 */
	public void setJoinedAServer(boolean joinedAServer) {
		this.joinedAServer = joinedAServer;
	}

	/**
	 * @return the util
	 */
	public Util getUtil() {
		return util;
	}

	/**
	 * @return the listener
	 */
	public Listener getListener() {
		return listener;
	}

	/**
	 * @return the serversAndChannels
	 */
	public static ArrayList<String> getServersAndChannels() {
		return serversAndChannels;
	}

	public TerminalPanel getTerminalPanel() {
		return terminalPanel;
	}

	public static void notifyKeyListener(KeyEvent e) {

	}

	void connectionTreeTabSelection(int c) {
		connectionTree.metaSelection(activeServer, c);		

	}

	void giveInputFieldFocus(char c) {

		String inputText = inputField.getText();
		inputText+=Character.toString(c);
		inputField.setText(inputText);
		inputField.requestFocusInWindow();
	}

	public String getRootConnection() {
		return util.findActiveIRCConnection().getHost();
	}
	
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		int ok = JOptionPane.showConfirmDialog(frame, "Quit JIRCC?", "", JOptionPane.OK_CANCEL_OPTION);
		if(ok == 0) {
			saveWindowState();
			System.exit(0);
		}

	}

	private void saveWindowState() {
		Properties properties = new Properties();
		File stateDir = new File(ConnectionEngine.settingsDir+ClientConstants.fileSeparator+
				fileDirectory);
		if(!stateDir.exists())
			stateDir.mkdir();
		File file = new File(stateDir.getAbsolutePath()+ClientConstants.fileSeparator+"windowState.txt");
		System.out.println("file path in save "+file.getPath());

		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {}
		}
		writeWindowState(properties, file);
	}

	private void writeWindowState(Properties properties, File file) {
		System.out.println("writing window state");
		properties.put(DisplayKeyConsts.WINDOW_LOCATION_X, Integer.toString((int)frame.getLocationOnScreen().getX()));
		properties.put(DisplayKeyConsts.WINDOW_LOCATION_Y, Integer.toString((int)frame.getLocationOnScreen().getY()));
		properties.put(DisplayKeyConsts.WINOW_WIDTH, Integer.toString(frame.getWidth()));
		properties.put(DisplayKeyConsts.WINDOW_HEIGHT, Integer.toString(frame.getHeight()));	
		properties.put(DisplayKeyConsts.OUTPUT_SPLIT_PANE_LOCATION, Integer.toString((int)outputSplitPane.getDividerLocation()));
		properties.put(DisplayKeyConsts.LISTS_AND_OUTPUT_SPLIT_PANE_LOCATION, Integer.toString((int)listsAndOutputSplitPane.getDividerLocation()));
		try {
			FileOutputStream out = new FileOutputStream(file);
			properties.store(out, "Program settings");
			out.close();
		} catch (FileNotFoundException e) {
			System.err.println("Error writing settings to new settings file.");
		} catch (IOException e) {
			System.err.println("Error storing the application settings.");
		}
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the scrollbardim
	 */
	public static Dimension getScrollbardim() {
		return SCROLLBARDIM;
	}

	/**
	 * @return the isOSX
	 */
	public static boolean isOSX() {
		return isOSX;
	}

	/**
	 * @param isOSX the isOSX to set
	 */
	public static void setOSX(boolean isOSX) {
		ChatWindow.isOSX = isOSX;
	}

	public static CommandSender getCs() {
		return cs;
	}
}
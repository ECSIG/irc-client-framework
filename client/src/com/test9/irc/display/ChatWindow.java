package com.test9.irc.display;

import java.awt.BorderLayout;
import java.awt.Color;
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

public class ChatWindow extends JFrame implements ComponentListener,
KeyListener, WindowStateListener, WindowFocusListener, PropertyChangeListener {

	private static final long serialVersionUID = -6373704295052845871L;
	private static final Toolkit KIT = Toolkit.getDefaultToolkit();
	private static final int SPLITPANEWIDTH = 4;
	private Dimension defaultWindowSize = new Dimension(
			KIT.getScreenSize().width / 2, KIT.getScreenSize().height / 2);
	private static final int DEFAULTSIDEBARWIDTH = 150;
	private static JTree channelTree = new JTree();
	private static JTextField inputField = new JTextField();
	private static JPanel centerJPanel = new JPanel(new BorderLayout());
	private static JScrollPane treeScrollPane = new JScrollPane();
	private static JSplitPane sidePanelSplitPane, listsAndOutputSplitPane;
	private static JLayeredPane userListsLayeredPane, outputFieldLayeredPane;
	private static ArrayList<OutputPanel> outputPanels = new ArrayList<OutputPanel>();
	private static ArrayList<UserListPanel> userListPanels = new ArrayList<UserListPanel>();

	public ChatWindow()
	{

		addComponentListener(this);
		addWindowFocusListener(this);
		setPreferredSize(defaultWindowSize);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(true);

		userListsLayeredPane = new JLayeredPane();


		outputFieldLayeredPane = new JLayeredPane();
		newOutputPanel("test"); //TODO this is Hardcode, make sure to remove upon implementation
		outputFieldLayeredPane.add(outputPanels.get(0));//TODO this to^

		userListsLayeredPane = new JLayeredPane();
		newUserListPanel("test");
		userListsLayeredPane.add(userListPanels.get(0));

		inputField.addKeyListener(this);
		centerJPanel.add(outputFieldLayeredPane);
		centerJPanel.add(inputField, BorderLayout.SOUTH);

		treeScrollPane.setBackground(Color.RED);
		JPanel testPanel = new JPanel();
		testPanel.setBackground(Color.BLUE);
		testPanel.setBounds(0, 0, 50, 50);
		treeScrollPane.add(testPanel);
		//treeScrollPane.add(new JTree());

		sidePanelSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
				userListsLayeredPane, testPanel);
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

	/**
	 * This method must be called each time a channel or server is joined or connected to.
	 * @param channel
	 */
	public void newOutputPanel(String channel)
	{
		outputPanels.add(new OutputPanel(channel, 
				(int) outputFieldLayeredPane.getSize().getWidth(),
				(int) outputFieldLayeredPane.getSize().getHeight()));
	}

	/**
	 * This needs to be called alone with newOutputPanel
	 */
	public void newUserListPanel(String channel)
	{
		userListPanels.add(new UserListPanel(channel,
				(int) userListsLayeredPane.getSize().getWidth(),
				(int) userListsLayeredPane.getSize().getHeight()));
	}

	public void newMessage(String channel, String message)
	{
		if(findChannel(channel,0) != -1)
			outputPanels.get(findChannel(channel, 0)).newMessage(message);
		else
			System.err.println("Cound not find channel to append message to.");
	}
	
	public void newUser(String channel, String user)
	{
		if(findChannel(channel,1) != -1)
			userListPanels.get(findChannel(channel,1)).newUser(user);
		else
			System.err.println("Cound not find channel to add new user.");
	}
	
	public void deleteUser(String channel, String user)
	{
		if(findChannel(channel,1) != -1)
			userListPanels.get(findChannel(channel,1)).deleteUser(user);
		else
			System.err.println("Cound not find channel to add new user.");
	}

	private int findChannel(String channel, int type)
	{
		boolean found = false;
		int i = 0;
		if(type==0)
		{
			while(!found && i < outputPanels.size())
			{
				if(outputPanels.get(i).getChannel().equals(channel))
				{
					found = true;
					return i;
				}	
				else i++;
			}
		}
		else if(type == 1)
		{
			while(!found && i < userListPanels.size())
			{
				if(userListPanels.get(i).getChannel().equals(channel))
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
		}
		else if(evt.getSource() == sidePanelSplitPane)
		{
			UserListPanel.setNewBounds(userListsLayeredPane.getWidth(), 
					userListsLayeredPane.getHeight());

			for(UserListPanel t: userListPanels)
			{
				t.setBounds(UserListPanel.getBoundsRec());
			}
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
}

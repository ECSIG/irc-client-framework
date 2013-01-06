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

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;

public class ChatWindow extends JFrame implements ComponentListener,
KeyListener, WindowStateListener, WindowFocusListener, PropertyChangeListener {

	private static final long serialVersionUID = -6373704295052845871L;
	private static final Toolkit KIT = Toolkit.getDefaultToolkit();
	private static final int SPLITPANEWIDTH = 5;
	private Dimension defaultWindowSize = new Dimension(
			KIT.getScreenSize().width / 2, KIT.getScreenSize().height / 2);
	private static final int DEFAULTSIDEBARWIDTH = 150;
	private static JTextArea outputField = new JTextArea();
	private static JList<String> userList = new JList<String>();
	private static JTree channelList = new JTree();
	private static JTextField inputField = new JTextField();
	private static JPanel outputInputPanel = new JPanel(new BorderLayout());
	private static JScrollPane chatOutputScrollPane;
	private static JSplitPane sidePanelSplitPane, listsAndOutputSplitPane;
	private static JLayeredPane userLists, outputFields;


	public ChatWindow()
	{

		addComponentListener(this);
		addWindowFocusListener(this);
		setPreferredSize(defaultWindowSize);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(true);

		userLists = new JLayeredPane();
		
		
		outputField.setLineWrap(true);
		outputField.setEditable(false);
		chatOutputScrollPane = new JScrollPane(outputField);
		outputField.setText("Chat OUTPUT JTEXTAREA");


		inputField.addKeyListener(this);
		outputInputPanel.add(chatOutputScrollPane);
		outputInputPanel.add(inputField, BorderLayout.SOUTH);

		userList.setBackground(Color.GRAY);


		sidePanelSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
				userList, channelList);
		sidePanelSplitPane.setDividerSize(SPLITPANEWIDTH);
		sidePanelSplitPane.setDividerLocation((this.getPreferredSize().height/2)-20);


		listsAndOutputSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				outputInputPanel, sidePanelSplitPane);
		listsAndOutputSplitPane.setDividerSize(SPLITPANEWIDTH);

		listsAndOutputSplitPane.setDividerLocation(this.getPreferredSize().width-DEFAULTSIDEBARWIDTH);


		add(listsAndOutputSplitPane, BorderLayout.CENTER);


		pack();
		inputField.requestFocus();
		setVisible(true);
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
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			System.out.println("Message was sent");
			outputField.append(inputField.getText()+"\r\n");
			inputField.setText("");
			outputField.setCaretPosition(outputField.getText().length());
		}
	}


	@Override
	public void componentResized(ComponentEvent e) 
	{
		System.out.println("Component Resized");
		sidePanelSplitPane.setDividerLocation((this.getHeight()/2)-20);
		listsAndOutputSplitPane.setDividerLocation(this.getWidth()-DEFAULTSIDEBARWIDTH);

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


	@Override
	public void propertyChange(PropertyChangeEvent evt) {

	}

}

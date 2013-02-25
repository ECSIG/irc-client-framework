package com.test9.irc.display;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.test9.irc.display.prefWins.NewServerConfigWindow;

public class MenuBar extends JMenuBar implements MenuListener, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7455171824970879713L;

//	private static final JMenu[] menus = new JMenu[]{
//		new JMenu("File"), new JMenu("Edit"), new JMenu("View"), new JMenu("Server"),
//		new JMenu("Channel"), new JMenu("Window"), new JMenu("Help")
//	};
//
	private static final JMenuItem[] editMenuItems = new JMenuItem[]{
		new JMenuItem("Undo"), new JMenuItem("Redo"), new JMenuItem("Cut"), 
		new JMenuItem("Copy"),	new JMenuItem("Paste"), new JMenuItem("Delete"), new JMenuItem("Select All"),
	};
//
//	private static final JComponent[] serverMenu = new JComponent[]{
//		new JMenuItem("Connect"), new JMenuItem("Disconnect"), new JSeparator(),
//		new JMenuItem("Add Server"), new JMenuItem("Add Channel"), new JSeparator(), 
//		new JMenuItem("Server Properties")
//	};
//
//	private static final JComponent[] channelMenu = new JComponent[]{
//		new JMenuItem("Join"), new JMenuItem("Leave"), new JSeparator(), new JMenuItem("Mode"), 
//		new JMenuItem("Topic"), new JSeparator(), new JMenuItem("Add Channel"), new JMenuItem("Delete Channel"), 
//		new JSeparator(), new JMenuItem("Channel Properties")
//	};
	
	JMenuItem newServer = new JMenuItem("New Server");

	MenuBar() {
		System.out.println("making new server");
		JMenu serverMenu = new JMenu("Server");
		newServer.addActionListener(this);
		serverMenu.add(newServer);
		
		JMenu editMenu = new JMenu("Edit");

		for(JMenuItem c : editMenuItems) {
			editMenu.add(c);
			c.addActionListener(this);
		}
		

		

		add(serverMenu);
		add(editMenu);

	}

	@Override
	public void menuSelected(MenuEvent e) {
		System.out.println("menuSelected");
	}

	@Override
	public void menuDeselected(MenuEvent e) {
		System.out.println("menuDeselected");
	}

	@Override
	public void menuCanceled(MenuEvent e) {
		System.out.println("menyCanceled");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == newServer) {
			new NewServerConfigWindow();
		} else {
			System.out.println("well shit");
		}
		
	}
}

package com.test9.irc.display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.test9.irc.display.prefWins.NewServerConfigWindow;

public class MenuBar extends JMenuBar implements MenuListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7455171824970879713L;

//	private static final JMenu[] menus = new JMenu[]{
//		new JMenu("File"), new JMenu("Edit"), new JMenu("View"), new JMenu("Server"),
//		new JMenu("Channel"), new JMenu("Window"), new JMenu("Help")
//	};
//
//	private static final JComponent[] editMenu = new JComponent[]{
//		new JMenuItem("Undo"), new JMenuItem("Redo"), new JSeparator(), new JMenuItem("Cut"), 
//		new JMenuItem("Copy"),	new JMenuItem("Paste"), new JMenuItem("Delete"), new JMenuItem("Select All"),
//	};
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

		JMenu serverMenu = new JMenu("Server");
		newServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new NewServerConfigWindow();
			}
		});
		serverMenu.add(newServer);
		
		add(serverMenu);

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
}

package com.test9.irc.display;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class MySystemTray {

	private static final PopupMenu popup = new PopupMenu();
	private static final TrayIcon trayIcon =
			new TrayIcon(createImage("elmotrans.png", "tray icon"));
	private static final SystemTray tray = SystemTray.getSystemTray();

	public static void init() {
		if(!SystemTray.isSupported())
			System.out.println("System tray is not  supported.");
		else
			System.out.println("System tray is supported.");

		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Menu menu = new Menu("Display");
		MenuItem about = new MenuItem("About");
		
		popup.add(about);
		trayIcon.setPopupMenu(popup);
		trayIcon.setImageAutoSize(true);
		trayIcon.setToolTip("JIRCC");
		
		
		
		about.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "This is the about option for JIRCC System Tray =D");
            }
        });

	}

	protected static Image createImage(String path, String description) {
		URL imageURL = MySystemTray.class.getResource(path);

		if (imageURL == null) {
			System.err.println("Resource not found: " + path);
			return null;
		} else {
			return (new ImageIcon(imageURL, description)).getImage();
		}
	}

	public static void notification(String type, String message) {
		trayIcon.displayMessage(type, message, TrayIcon.MessageType.INFO);
		
	}
}

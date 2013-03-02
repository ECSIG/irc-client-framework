package com.test9.irc.display;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.test9.irc.engine.NewEngineTester;

public class MySystemTray {

	private static final PopupMenu popup = new PopupMenu();
	private static TrayIcon trayIcon;
	private static final SystemTray tray = SystemTray.getSystemTray();
	@SuppressWarnings("unused")
	private static ChatWindow owner;
	private static ImageIcon img; 


	public static void init(final ChatWindow owner) {
		MySystemTray.owner = owner;
		if(ChatWindow.isOSX()) {
			trayIcon = new TrayIcon(createImage("elmotransBandW.png", "tray icon"));
		} else {
			trayIcon = new TrayIcon(createImage("elmotrans.png", "tray icon"));
		}

		img = new ImageIcon(MySystemTray.class.getResource("elmotrans.png"));

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

		MenuItem about = new MenuItem("About");
		MenuItem quit = new MenuItem("Quit");

		popup.add(about);
		popup.add(quit);
		trayIcon.setPopupMenu(popup);
		trayIcon.setImageAutoSize(true);
		trayIcon.setToolTip("JIRCC");



		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "You are using JIRCC Version: " 
						+ NewEngineTester.VERSION, "JIRCC About", JOptionPane.INFORMATION_MESSAGE, img);

			}
		});

		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				owner.windowClosing(null);
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

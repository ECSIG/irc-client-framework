package com.test9.irc.display.prefWins;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.test9.irc.engine.ConnectionEngine;
import com.test9.irc.engine.ReadServerConfig;

@SuppressWarnings("unused")
public class NewServerConfigWindow implements ActionListener{

	private static final long serialVersionUID = 285475674231316918L;
	private static final Toolkit KIT = Toolkit.getDefaultToolkit();	
	private static double screenHeight = KIT.getScreenSize().getHeight();
	private static double screenWidth = KIT.getScreenSize().getWidth();

	private static JFrame frame = new JFrame("New Server");

	// ** General Tab ** //
	private static JPanel generalPanel = new JPanel();

	private static JTabbedPane tabbedPane = new JTabbedPane();
	private static JTextField networkNameField = new JTextField();
	private static JTextField hostField = new JTextField();
	private static JTextField portField = new JTextField();
	private static JPasswordField serverPasswordField = new JPasswordField();
	private static JTextField nickNameField = new JTextField();
	private static JTextField loginNameField = new JTextField();
	private static JTextField realNameField = new JTextField();
	private static JPasswordField nickservPassword = new JPasswordField();
	private static JTextField altNicks = new JTextField();
	private static JPanel buttonPanel = new JPanel();
	private static JCheckBox connectOnStartup = new JCheckBox("Connect on start up");
	private static JCheckBox sslCheck = new JCheckBox("Use SSL");

	private static String networkNameL = "networkName";
	private static String connectOnStartupL = "connectOnStartup";
	private static String serverL = "server";
	private static String portL = "port";
	private static String sslCheckL = "sslCheck";
	private static String passwordL = "password";
	private static String nickNameL = "nickname";
	private static String loginNameL = "loginname";
	private static String realNameL = "realname";
	private static String nickservPasswordL = "nickservPassword";
	// ** End General Tab ** //

	// ** Details Tab ** //
	private static JPanel detailsPanel = new JPanel();
	private static String[] encodingOptions = new String[]{
		"UTF-8", "ISO-8859-1", "ISO-8859-15", "ISO-8859-2", "ISO-8859-7 (Greek)",
		"ISO-8859-8 (Hebrew)", "ISO-8859-9 (Turkish)", "ISO-8859-11 (Thai)",
		"ISO-2022-JP (Japanese)", "CP949 (Korean)", "EUC-KR (Korean)", "GBK (Chinese)",
		"GB18030 (Chinese)", "BIG5 (Chinese)", "KOI8-U (Ukrainian)", "KOI8-R (Cyrillic)",
		"CP1251 (Cyrillic)", "CP1256 (Arabic)", "CP1257 (Baltic)"
	};
	private static JTextField leavingComment = new JTextField();
	private static JTextField ctcpUserInfo = new JTextField();
	private static JComboBox encoding = new JComboBox();
	private static JComboBox fallbackEncoding = new JComboBox();
	// ** End Details Tab ** //

	private static JPanel onLoginPanel = new JPanel();
	private static JPanel ignorePanel = new JPanel();
	private static JButton cancel = new JButton("Cancel");
	private static JButton ok = new JButton("OK");

	private Properties properties = new Properties();


	public NewServerConfigWindow()
	{
		System.out.println("new window");

		initGeneralPanel();
		initDetailsPanel();
		initOnLoginPanel();
		initIgnorePanel();

		tabbedPane.add("General", generalPanel);
		tabbedPane.add("Details", detailsPanel);
		tabbedPane.add("On Login", onLoginPanel);
		tabbedPane.add("Ignore", ignorePanel);

		//setTitle("New Server");
		frame.setResizable(false);
		frame.setSize(400,450);
		frame.setLayout(new BorderLayout());
		cancel.addActionListener(this);
		ok.addActionListener(this);
		buttonPanel.add(cancel);
		buttonPanel.add(ok);
		frame.add(buttonPanel, BorderLayout.SOUTH);
		frame.add(tabbedPane, BorderLayout.CENTER);

		//pack();
		frame.setVisible(true);

		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	private void initGeneralPanel() {
		generalPanel.setLayout(new GridLayout(11,2));
		generalPanel.setOpaque(true);
		generalPanel.add(new JLabel("Network Name: ", SwingConstants.RIGHT));
		generalPanel.add(networkNameField);
		generalPanel.add(new JLabel());
		generalPanel.add(connectOnStartup);
		generalPanel.add(new JLabel("Server: ", SwingConstants.RIGHT));
		generalPanel.add(hostField);
		generalPanel.add(new JLabel("Port: ", SwingConstants.RIGHT));
		generalPanel.add(portField);
		generalPanel.add(new JLabel());
		generalPanel.add(sslCheck);
		generalPanel.add(new JLabel("Server Password: ", SwingConstants.RIGHT));
		generalPanel.add(serverPasswordField);
		generalPanel.add(new JLabel("Nickname: ", SwingConstants.RIGHT));
		generalPanel.add(nickNameField);
		generalPanel.add(new JLabel("Login Name: ", SwingConstants.RIGHT));
		generalPanel.add(loginNameField);
		generalPanel.add(new JLabel("Real Name: ", SwingConstants.RIGHT));
		generalPanel.add(realNameField);
		generalPanel.add(new JLabel("Nickserv Password: ", SwingConstants.RIGHT));
		generalPanel.add(nickservPassword);
		generalPanel.add(new JLabel("Ald. Nicknames: ", SwingConstants.RIGHT));
		generalPanel.add(altNicks);

	}

	private void initDetailsPanel() {

		detailsPanel.setLayout(new GridLayout(4,2));
		detailsPanel.add(new JLabel("Leaving Comment:", SwingConstants.RIGHT));
		detailsPanel.add(leavingComment);
		detailsPanel.add(new JLabel("CTCP User Info:", SwingConstants.RIGHT));
		detailsPanel.add(ctcpUserInfo);
		for(String e : encodingOptions)
		{
			encoding.addItem(e);
			fallbackEncoding.addItem(e);
		}
		detailsPanel.add(new JLabel("Encoding:", SwingConstants.RIGHT));
		detailsPanel.add(encoding);
		detailsPanel.add(new JLabel("Fallback Encoding:", SwingConstants.RIGHT));
		detailsPanel.add(fallbackEncoding);
	}

	private void initOnLoginPanel() {

	}

	private void initIgnorePanel() {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == ok) {
			savePrefs();
			frame.dispose();
		} else if (e.getSource() == cancel) {
			frame.dispose();
		}
	}
	
	private void savePrefs() {
		File connectionsDir = new File(ConnectionEngine.settingsDir+
				ConnectionEngine.fileSeparator+"connections");
		System.out.println(hostField.getText());
	
		String name = networkNameField.getText();
		String host = hostField.getText();
		int port = Integer.parseInt(portField.getText());
		@SuppressWarnings("deprecation")
		String pass = serverPasswordField.getText();
		String nick = nickNameField.getText();
		String userName = loginNameField.getText();
		String realName = realNameField.getText();
		String encodingChoice = encoding.getSelectedItem().toString();
		boolean ssl = sslCheck.isSelected();
		
		properties.put("name", name);
		properties.put("host", host);
		properties.put("port", Integer.toString(port));
		properties.put("pass", pass);
		properties.put("nick", nick);
		properties.put("username", userName);
		properties.put("realname",realName);
		properties.put("nickservpass", pass);
		properties.put("altnicks", "");
		properties.put("ssl", Boolean.toString(ssl));
		properties.put("encoding", encodingChoice);

		File settingsFile = new File(connectionsDir.getPath() + 
				ConnectionEngine.fileSeparator + name+".txt");
		try {
			settingsFile.createNewFile();			
			FileOutputStream out = new FileOutputStream(settingsFile);
			properties.store(out, "Program settings");
			out.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {}
		if(ssl) {
			ConnectionEngine.beginSSLIRCConnection(name, host, port, pass, nick, userName, realName, encodingChoice);
		}
		else {
			ConnectionEngine.beginIRCConnection(name, host, port, pass, nick, userName, realName, encodingChoice);		}
	}
}

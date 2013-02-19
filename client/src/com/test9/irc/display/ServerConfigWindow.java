package com.test9.irc.display;

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
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.test9.irc.engine.ReadServerConfig;

@SuppressWarnings("unused")
public class ServerConfigWindow implements ActionListener{

	private static final long serialVersionUID = 285475674231316918L;
	private static final Toolkit KIT = Toolkit.getDefaultToolkit();	
	private static double screenHeight = KIT.getScreenSize().getHeight();
	private static double screenWidth = KIT.getScreenSize().getWidth();

	private static JFrame frame = new JFrame("New Server");

	// ** General Tab ** //
	private static JPanel generalPanel = new JPanel();

	private static JTabbedPane tabbedPane = new JTabbedPane();
	private static JTextField networkName = new JTextField();
	private static JTextField server = new JTextField();
	private static JTextField port = new JTextField();
	private static JPasswordField serverPassword = new JPasswordField();
	private static JTextField nickName = new JTextField();
	private static JTextField loginName = new JTextField();
	private static JTextField realName = new JTextField();
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
	private Preferences root = Preferences.userRoot();

	private Preferences prefs;

	public static void main(String args[])
	{
		ServerConfigWindow w = new ServerConfigWindow();
	}
	public ServerConfigWindow()
	{

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
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loadPrefs();
	}

	private void initGeneralPanel() {
		generalPanel.setLayout(new GridLayout(11,2));
		generalPanel.setOpaque(true);
		generalPanel.add(new JLabel("Network Name: ", SwingConstants.RIGHT));
		generalPanel.add(networkName);
		generalPanel.add(new JLabel());
		generalPanel.add(connectOnStartup);
		generalPanel.add(new JLabel("Server: ", SwingConstants.RIGHT));
		generalPanel.add(server);
		generalPanel.add(new JLabel("Port: ", SwingConstants.RIGHT));
		generalPanel.add(port);
		generalPanel.add(new JLabel());
		generalPanel.add(sslCheck);
		generalPanel.add(new JLabel("Server Password: ", SwingConstants.RIGHT));
		generalPanel.add(serverPassword);
		generalPanel.add(new JLabel("Nickname: ", SwingConstants.RIGHT));
		generalPanel.add(nickName);
		generalPanel.add(new JLabel("Login Name: ", SwingConstants.RIGHT));
		generalPanel.add(loginName);
		generalPanel.add(new JLabel("Real Name: ", SwingConstants.RIGHT));
		generalPanel.add(realName);
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
		//ReadServerConfig rsc = new ReadServerConfig(networkName.getText()+".txt");
		if(e.getSource() == ok) {
			savePrefs();
			System.exit(0);
			//frame.dispose();
		} else if (e.getSource() == cancel) {
			// TODO Change to dispose after done debugging
			//frame.dispose();
			// TODO remove system exit
			System.exit(0);
		}
	}

	private void loadPrefs() {
		prefs = root.node(this.getClass()+"_"+networkName.getText());

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));
		chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
			@Override
			public boolean accept(File f) {
				return f.getName().toLowerCase().endsWith(".xml") || f.isDirectory();
			}
			@Override
			public String getDescription()
			{
				return "XML files";
			}
		});
		if(chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION)
			try {
				InputStream in = new FileInputStream(chooser.getSelectedFile());
				Preferences.importPreferences(in);
				in.close();
			} catch (Exception e){}
		networkName.setText(prefs.get(networkNameL, ""));
		connectOnStartup.setText(prefs.get(connectOnStartupL, ""));
		server.setText(prefs.get(serverL, ""));
		port.setText(prefs.get(portL, ""));
		sslCheck.setSelected(prefs.getBoolean(sslCheckL, false));
		serverPassword.setText(prefs.get(passwordL, ""));
		nickName.setText(prefs.get(nickNameL, ""));
		loginName.setText(prefs.get(loginNameL, ""));
		realName.setText(prefs.get(realNameL, ""));
		nickservPassword.setText(prefs.get(nickservPasswordL, ""));
	}

	private void savePrefs() {
		System.out.println("saving prefs");
		prefs = root.node(this.getClass()+"_"+networkName.getText());
		prefs.put(networkNameL, networkName.getText());
		prefs.putBoolean(connectOnStartupL, connectOnStartup.isSelected());
		prefs.put(serverL, server.getText());
		prefs.put(portL, server.getText());
		prefs.putBoolean(sslCheckL, sslCheck.isSelected());
		prefs.put(passwordL, serverPassword.getText());
		prefs.put(nickNameL, nickName.getText());
		prefs.put(loginNameL, loginName.getText());
		prefs.put(realNameL, realName.getText());
		prefs.put(nickservPasswordL, nickservPassword.getText());
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));
		chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
			@Override
			public boolean accept(File f) {
				return f.getName().toLowerCase().endsWith(".xml") || f.isDirectory();
			}
			@Override
			public String getDescription()
			{
				return "XML files";
			}
		});
		if(chooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION)
			try {
				OutputStream out = new FileOutputStream(chooser.getSelectedFile());
				prefs.exportSubtree(out);
				out.close();
			} catch (Exception e){}
	}
}

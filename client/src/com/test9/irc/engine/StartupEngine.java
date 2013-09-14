package com.test9.irc.engine;

import java.io.File;
import java.io.IOException;
import javax.swing.UIManager;
import static java.lang.System.out;

public class StartupEngine {

	public static final String VERSION = "11";
	/**
	 * @param args
	 * @throws IOException 
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		out.println(System.getProperty("user.home"));
		String os = System.getProperty("os.name").toLowerCase();
		String userHome = System.getProperty("user.home");
		String settingsDir = "";

		if(os.contains("mac os x")) {
			settingsDir = userHome+"/Library/Application Support/JIRCC";
		} else if (os.contains("windows")) {
			settingsDir = userHome+"\\Documents\\JIRCC";
		} else if (os.contains("linux")) {
			settingsDir = userHome + "/JIRCC/connections";
		} else if (os.contains("bsd")) {
			settingsDir = userHome +"/JIRCC/connections";
		} else {
			System.err.println("Your operating system is not recognized");
			System.exit(0);
		}

		File dir = new File(settingsDir);
		System.out.println(dir.getAbsolutePath());

		if(!dir.exists()) {
			System.out.println("Starting First Launch");
			FirstLaunch FL = new FirstLaunch(settingsDir);
		}

		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e){
			System.out.println("Shit gone down");
		}

		new LoadSettings(settingsDir);
		new ConnectionEngine();
	}
}

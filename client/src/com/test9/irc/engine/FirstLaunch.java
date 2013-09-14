package com.test9.irc.engine;

import java.io.File;
import com.test9.irc.display.prefWins.*;

public class FirstLaunch {

	public FirstLaunch(String settingsDir) {
		File mainDir = new File(settingsDir);
		mainDir.mkdir();
		
		File serverDir = new File(mainDir+ClientConstants.fileSeparator+"servers");
		serverDir.mkdirs();
		
		File guiDir = new File(mainDir+ClientConstants.fileSeparator+"gui");
		guiDir.mkdir();
		
		new NewServerConfigWindow();

	}
}

import java.io.IOException;

public class LoadedSettings {
	private static final LoadedSettings instance;

	public static final String settingsDir;

	static {
		try {
			instance = new LoadedSettings();
		} catch (IOException e) {
			throw new RuntimeException("Damn, an error occurred!", e);
		}
	}

	public static LoadedSettings getInstance() {
		return instance;
	}

	private LoadedSettings() {

	}
}
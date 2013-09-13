import java.io.IOException;

public class LoadedSettings {
  private static final LoadedSettings instance;
 
  static {
    try {
      instance = new LoadedSettings();
    } catch (IOException e) {
      throw new RuntimeException("Darn, an error occurred!", e);
    }
  }
 
  public static Singleton getInstance() {
    return instance;
  }
 
  private Singleton() {
    // ...
  }
}
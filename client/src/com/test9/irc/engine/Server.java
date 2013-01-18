package com.test9.irc.engine;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import com.test9.irc.display.ChatWindow;

public class Server {

    private final String name;
    private ServerSender sender;
    private InputManager inputManager;
    private OutputManager outputManager;
    private Socket socket = null;
    public boolean isConnected = false;

    // The server to connect to and our details.
    String server = "irc.ecsig.com";
    String botRef = "jared-test";

    String nick = this.botRef + "";
    String login = this.botRef + "jared-test";

    // The channel which the bot will join.
    private List<Channel> channels = Arrays.asList(new Channel("#ecsig"));

    public Server(Boolean DEBUGGING, String name) {
        this.name = name;

        // Try to connect to the server at some string server address
        try {
            // Connect directly to the IRC server.
            this.socket = new Socket(this.server, 6667);
            
            this.inputManager = new InputManager(this);
            this.outputManager = new OutputManager(this);
            ChatWindow cw = new ChatWindow(server);
            
            System.out.println("========ADDDING OBSERVERS=======");
            inputManager.addObserver(outputManager);
           	inputManager.addObserver(cw);
        //    ConsoleInput consoleInput = new ConsoleInput(this);
       //     Thread t = new Thread(consoleInput);
      //      t.start();
            
      //      consoleInput.addObserver(outputManager);
            
            cw.addObserver(outputManager);
            

        } catch (UnknownHostException e) {
            System.err.println("Host not recognized: " + this.server);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for " + "the connection to: "
                    + this.server);
            System.exit(1);
        }
    }
    
    public void sendInitialMessage(){
    	
    		System.out.println("Sending Initial MESSAGE");
			sender.run();
    }
    
    public InputManager getInputManager(){
    	return inputManager;
    }

    public List<Channel> getChannels() {
        return this.channels;
    }

    public String getName() {
        return this.name;
    }

    public Socket getSocket() {
        return this.socket;
    }

    public String getBotRef() {
        return this.botRef;
    }

    public String getNick() {
        return this.nick;
    }

    public String getLogin() {
        return this.login;
    }

	/**
	 * @return the outputManager
	 */
	public OutputManager getOutputManager() {
		return outputManager;
	}

	/**
	 * @param outputManager the outputManager to set
	 */
	public void setOutputManager(OutputManager outputManager) {
		this.outputManager = outputManager;
	}

	/**
	 * @param inputManager the inputManager to set
	 */
	public void setInputManager(InputManager inputManager) {
		this.inputManager = inputManager;
	}
}
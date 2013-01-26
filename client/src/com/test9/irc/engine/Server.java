package com.test9.irc.engine;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import com.test9.irc.display.ChatWindow;

public class Server {
	private static final String RNTAIL = "\r\n";
    //private final String name;
    private ServerSender sender;
    private InputManager inputManager;
    private OutputManager outputManager;
    private Socket socket;
    public boolean isConnected = false;
//    private String server = "irc.ecsig.com";
//    private String botRef = "jared-test";
//    private String nick = this.botRef + "";
//    private String login = this.botRef + "jared-test";
    private String nick;
	protected byte level = 0;
	protected String host;
	private String pass;
	private String realname;
	private String username;
	private int port;

    // The channel which the bot will join.
    private List<Channel> channels = Arrays.asList(new Channel("#ecsig"));

    /**
     * 
     * @param host
     * @param port
     * @param pass
     * @param nick
     * @param username
     * @param realname
     */
    public Server(String host, int port, String pass, String nick, 
			String username, String realname) {
		this.host = host;
		this.port = port;
		this.pass = (pass != null && pass.length() == 0) ? null : pass;
		this.nick = nick;
		this.username = username;
		this.realname = realname;

        // Try to connect to the server at some string server address
        try {
            // Connect directly to the IRC server.
            this.socket = new Socket(this.host, this.port);
            
            this.inputManager = new InputManager(this);
            this.outputManager = new OutputManager(this);
            ChatWindow cw = new ChatWindow(host);
            
            System.out.println("========ADDDING OBSERVERS=======");
            inputManager.addObserver(outputManager);
           //	inputManager.addObserver(cw);
            
            cw.addObserver(outputManager);
            
            register();
            

        } catch (UnknownHostException e) {
            System.err.println("Host not recognized: " + this.host);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for " + "the connection to: "
                    + this.host);
            System.exit(1);
        }
    }
    
	private void register() {
		System.out.println("registering");
		if (pass != null)
			outputManager.getSender().setOutput("PASS "+ pass+RNTAIL);
		outputManager.getSender().setOutput("NICK "+ nick+RNTAIL); 
		outputManager.getSender().setOutput("USER "+ username +" "+ socket.getLocalAddress() +" "+ host 
				+" :"+ realname+RNTAIL); 
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

    public Socket getSocket() {
        return this.socket;
    }

    public String getNick() {
        return this.nick;
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

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}
}
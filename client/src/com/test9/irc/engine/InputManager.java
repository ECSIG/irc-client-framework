package com.test9.irc.engine;

import java.io.IOException;
import java.util.Observable;
import com.test9.irc.parser.*;

public class InputManager extends Observable {
	
	private Parser p;
	private Server server;
	private ServerListener listener;
	
	public InputManager(Server server) {
		p = new Parser();
		this.server = server;
		
		// Apply socket to the sender and listener.
        try {
			this.listener = new ServerListener(server);
		} catch (IOException e) {
            System.err.println("Couldn't get I/O for " + "the connection to: "
                    + this.server);
            System.exit(1);
        }
        
        // Start the listener thread
        this.listener.start();
        System.out.println("Listener started.");
	}
	
	public void sendMessage(String s){
		Message m = p.parse(new StringBuffer(s));

		setChanged();
		notifyObservers(m);
	}
}

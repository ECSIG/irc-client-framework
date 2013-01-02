package com.test9.irc.engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Observable;

public class ConsoleInput extends Observable implements Runnable {

	private Server server;
	private BufferedReader in;
	private String input;

	public ConsoleInput(Server server) {
		this.server = server;

		this.in = new BufferedReader(new InputStreamReader(System.in));

	}

	@Override
	public void run() {
		while (true) {
			// System.out.println("in serverlistener run()	");
			try {
				this.input = this.in.readLine();
				// System.out.println("Server Listener incoming: " +
				// this.input);
				if (this.input != null) {
					System.out.println("=========Received MESSAGE=========");
					this.sendMessage(input);
				}
			} catch (IOException e) {
				e.getStackTrace();
			}

			Thread.yield();
		}
	}
	
	public void sendMessage(String s){
		setChanged();
		
		notifyObservers(s);
	}

}

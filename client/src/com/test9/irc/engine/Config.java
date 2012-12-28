package com.test9.irc.engine;

import java.io.*;
import java.net.Socket;

public class Config {
    // The server to connect to and our details.
    private String server = "irc.ecsig.com";
    private String nick = "brad-bot";
    private String login = "brad-bot";

    // The channel which the bot will join.
    private String channel = "#jircc";

    // These can be possible used later to have a global place to store these
    // values.
    // Maybe eventually
    private Socket socket = null;
    private BufferedReader in = null;
    private BufferedWriter out = null;

    public Config() {
    }

    // -----------------------
    // Accessors and Mutators
    // -----------------------
    public boolean setServer(String server) {
        this.server = server;
        return true;
    }

    public String getServer() {
        return this.server;
    }

    public boolean setNick(String nick) {
        this.nick = nick;
        return true;
    }

    public String getNick() {
        return this.nick;
    }

    public boolean setLogin() {
        this.login = login;
        return true;
    }

    public String getLogin() {
        return this.login;
    }

    public boolean setActiveChannel(String channel) {
        if (channel.charAt(0) == '#') {
            this.channel = channel;
            return true;
        } else {
            return false;
        }
    }

    public String getActiveChannel() {
        return this.channel;
    }

    public Socket getSocket() {
        return this.socket;
    }

    public boolean setSocket(Socket socket) {
        this.socket = socket;
        return true;
    }

    public boolean updateInStream(BufferedReader in) {
        this.in = in;
        return true;
    }

    public BufferedReader getInStream() {
        return this.in;
    }

    public boolean updateOutStream(BufferedWriter out) {
        this.out = out;
        return true;
    }

    public BufferedWriter outStream() {
        return this.out;
    }

}

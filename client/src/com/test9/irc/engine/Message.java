package com.test9.irc.engine;

import java.util.*;
import java.text.SimpleDateFormat;

public class Message {

    // TODO use message class
    private String server, sender, channel, message, timestamp;

    private String getTime() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    public Message(String server, String sender, String channel, String message) {
        this.server = server;
        this.sender = sender;
        this.channel = channel;
        this.message = message;
        this.timestamp = getTime();
    }

    // ------------
    // Accessors

    public String getServer() {
        return server;
    }

    public String getSender() {
        return sender;
    }

    public String getChannel() {
        return channel;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    // -----------
    // Mutators

    public void setServer(String server) {
        this.server = server;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

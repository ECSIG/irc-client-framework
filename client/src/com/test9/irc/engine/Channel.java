package com.test9.irc.engine;

import java.util.ArrayList;

public class Channel {

    private String topic, name, mode;
    private UserList chanUserList;
    private int ops, totalUsers;
    private final ArrayList<String> buffer;

    public Channel(String topic, String name, String mode, int ops,
            int totalUsers) {
        this.topic = topic;
        this.name = name;
        this.mode = mode;
        this.ops = ops;
        this.totalUsers = totalUsers;
        this.buffer = new ArrayList<String>(0);
    }

    public Channel(String name) {
        this.topic = null;
        this.name = name;
        this.mode = null;
        this.ops = 0;
        this.totalUsers = 0;
        this.buffer = new ArrayList<String>(0);
    }

    public void generateChannelUserList(String userList, String userJoinOrPart) {
        this.chanUserList = new UserList(userList, userJoinOrPart);
    }

    public void newMessage(String message) {
        this.buffer.add(message);
        // ircFrame.appendOutput(message);

    }

    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMode() {
        return this.mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public UserList getChanUserList() {
        return this.chanUserList;
    }

    public void setChanUserList(UserList chanUserList) {
        this.chanUserList = chanUserList;
    }

    public int getOps() {
        return this.ops;
    }

    public void setOps(int ops) {
        this.ops = ops;
    }

    public int getTotalUsers() {
        return this.totalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }
    
    @Override
    public String toString(){
    	return this.getName();
    }
}

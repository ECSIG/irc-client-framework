package com.test9.irc.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserList {

    private static final long serialVersionUID = 1L;
    private boolean userArrayListInit = false;
    private final List<User> userAList;

    /**
     * Build user list array from userList then call refreshUserList.
     * 
     * @param userList
     */
    public UserList(String userList, String userJoinOrPart) {
        // TODO Fix user channel when multiple channels are supported
        // TODO Maybe not?
        this.userArrayListInit = true;
        this.userAList = new ArrayList<User>();
        String[] allUsers = userList.split("[ ]+");

        for (String newUserNick : allUsers) {
            User newUser = new User(newUserNick);
            this.userAList.add(newUser);
        }

        this.sortAndRefresh();

    }

    // TODO Fix user channel when multiple channels are supported
    /**
     * Used to append the array list of users when a user parts from a given
     * channel.
     */
    synchronized public void userParted(String userName) {
        System.out.println("user parted!");
        for (int i = 0; i < this.userAList.size(); i++) {
            if ((this.userAList.get(i).getNick()).equals(userName)) {
                this.userAList.remove(i);
                break;
            }
        }

        this.sortAndRefresh();
    }

    // TODO Fix user channel when multiple channels are supported
    /**
     * Used to append the user array list when a user joins a given channel.
     */
    synchronized public void userJoined(String userName) {
        System.out.println("user joined");
        if (this.userArrayListInit) {
            User joinedUser = new User(userName);
            this.userAList.add(joinedUser);
            this.sortAndRefresh();
        }
    }

    /**
     * Updates the users array list when a user changes their nick.
     * 
     * @param oldNick
     * @param newNick
     * @param Channel
     */
    synchronized public void userNickChange(String oldNick, String newNick) {
        System.out.println("nick changed");
        if (this.userArrayListInit) {
            for (int i = 0; i < this.userAList.size(); i++) {
                if ((this.userAList.get(i).getNick()).equals(oldNick)) {
                    this.userAList.get(i).setNick(newNick);
                    break;
                }
            }
            this.sortAndRefresh();
        }
    }

    synchronized private void sortAndRefresh() {
        Collections.sort(this.userAList, new User(true));
    }

    public boolean isUserArrayListInit() {
        return this.userArrayListInit;
    }

    public void setUserArrayListInit(boolean userArrayListInit) {
        this.userArrayListInit = userArrayListInit;
    }
}

package com.test9.irc.engine;

import java.util.Comparator;

public class User implements Comparator<User> {

    private String nick, ident, away, realName;

    // TODO Make sure we don't need user modes.

    public User(String nick, String ident, String away, String realName) {
        this.nick = nick;
        this.ident = ident;
        this.away = away;
        this.realName = realName;
    }

    public User(String nick, String away, String realName) {
        this.nick = nick;
        this.ident = "";
        this.away = away;
        this.realName = realName;
    }

    public User(String nick, String realName) {
        this.nick = nick;
        this.ident = "";
        this.away = "";
        this.realName = realName;
    }

    public User(String nick) {
        this.nick = nick;
        this.ident = "";
        this.away = "";
        this.realName = "";
    }

    public User(boolean isTemp) {
        this.nick = "Temp: if you see this there is an error with a temp user.";
        this.ident = "Temp: if you see this there is an error with a temp user.";
        this.away = "Temp: if you see this there is an error with a temp user.";
        this.realName = "Temp: if you see this there is an error with a temp user.";
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public String getAway() {
        return away;
    }

    public void setAway(String away) {
        this.away = away;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public int compare(User arg0, User arg1) {
        return arg0.getNick().toLowerCase()
                .compareTo(arg1.getNick().toLowerCase());
    }

}

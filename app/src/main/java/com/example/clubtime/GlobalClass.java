package com.example.clubtime;

import android.app.Application;

public class GlobalClass extends Application {
    private String active_user;
    private String active_club;

    public String getActive_user() {
        return active_user;
    }

    public void setActive_user(String active_user) {
        this.active_user = active_user;
    }

    public String getActive_club() {
        return active_club;
    }

    public void setActive_club(String active_club) {
        this.active_club = active_club;
    }
}

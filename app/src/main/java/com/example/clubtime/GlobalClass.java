package com.example.clubtime;

import android.app.Application;

public class GlobalClass extends Application {
    private Usuario active_user;
    private Club active_club;

    public Usuario getActive_user() {
        return active_user;
    }

    public void setActive_user(Usuario active_user) {
        this.active_user = active_user;
    }

    public Club getActive_club() {
        return active_club;
    }

    public void setActive_club(Club active_club) {
        this.active_club = active_club;
    }
}

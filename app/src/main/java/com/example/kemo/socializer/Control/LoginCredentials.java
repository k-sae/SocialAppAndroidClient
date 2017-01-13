package com.example.kemo.socializer.Control;

import io.realm.RealmObject;

/**
 * Created by kemo on 13/01/2017.
 */
public class LoginCredentials extends RealmObject {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


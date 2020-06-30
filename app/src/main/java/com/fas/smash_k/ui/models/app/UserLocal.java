package com.fas.smash_k.ui.models.app;

import java.io.Serializable;

public class UserLocal implements Serializable {
    private Boolean error;
    private int id;
    private String email;
    private String username;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserLocal(Boolean error, int id, String email, String username) {
        this.error = error;
        this.id = id;
        this.email = email;
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserLocal{" +
                "error=" + error +
                ", id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public UserLocal() {
    }
}

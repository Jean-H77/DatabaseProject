package org.db.model;

public abstract class Details {
    private final String username;
    private final String password;

    protected Details(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

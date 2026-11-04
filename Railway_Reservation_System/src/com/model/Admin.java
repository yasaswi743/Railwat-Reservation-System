
package com.model;

public class Admin {

    private String username;
    private String password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public boolean validateLogin(String enteredUsername,
                                 String enteredPassword) {
        return username.equals(enteredUsername)
                && password.equals(enteredPassword);
    }
}


package com.service;

import com.model.Admin;

public class AdminService {

    private Admin admin = new Admin("admin", "admin123");

    public boolean login(String username, String password) {
        return admin.validateLogin(username, password);
    }
}

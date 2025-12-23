package com.models;

public class Admin extends User {

    public Admin(String username, String email,
                 String password, String role) {
        super(username, email, password, role);
    }

    public void showMenu() {
        System.out.println("Admin Menu Loaded.");
    }
}

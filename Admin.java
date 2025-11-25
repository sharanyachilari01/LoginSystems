package com.models;

public class Admin extends User {
    public Admin(String username, String email, String role) {
        super(username, email, role);
    }

    @Override
    public void showMenu() {
        System.out.println("Admin Menu Loaded.");
    }
}

package com.models;

public abstract class User {
    protected String username;
    protected String email;
    protected String role;

    public User(String username, String email, String role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    // Setters (optional, only if you plan to change values later)
    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Abstract method for showing role-specific menus (optional)
    public abstract void showMenu();
}

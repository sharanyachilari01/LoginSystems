package com.models;
public class Student extends User {

    public Student(String username, String email,
                   String password, String role) {
        super(username, email, password, role);
    }

    public void showMenu() {
        System.out.println("Student Menu Loaded.");
    }
}

package com.models;

public class Student extends User {
    public Student(String username, String email) {
        super(username, email, "STUDENT");
    }

    @Override
    public void showMenu() {
        System.out.println("Student Menu Loaded.");
    }
}

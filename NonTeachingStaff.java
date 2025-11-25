package com.models;

public class NonTeachingStaff extends User {
    private String department;

    public NonTeachingStaff(String username, String email, String role, String department) {
        super(username, email, role);
        this.department = department;
    }

    @Override
    public void showMenu() {
        System.out.println("Non-Teaching Staff Menu Loaded.");
    }
}

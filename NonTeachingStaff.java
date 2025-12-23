package com.models;

public class NonTeachingStaff extends User {
    private String department;

    public NonTeachingStaff(String username, String email, String password,
            String role, String department) {

super(username, email, password, role); 
        this.department = department;
    }

    public void showMenu() {
        System.out.println("Non-Teaching Staff Menu Loaded.");
    }
}

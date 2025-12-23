package com.models;

public class TeachingStaff extends User {
    private String department;

    public TeachingStaff(String username, String email, String password,String role, String department) {
    	super(username, email, password, role);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void showMenu() {
        System.out.println("Teaching Staff Menu Loaded.");
    }
}

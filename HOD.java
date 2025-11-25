package com.models;

public class HOD extends TeachingStaff {
    public HOD(String username, String email, String role, String department) {
        super(username, email, role, department);
    }

    @Override
    public void showMenu() {
        System.out.println("HOD Menu Loaded.");
    }
}

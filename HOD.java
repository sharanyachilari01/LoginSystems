package com.models;

public class HOD extends TeachingStaff {
    public HOD(String username, String email, String password,
            String role, String department) {

     super(username, email, password, role, department); 
    }

    @Override
    public void showMenu() {
        System.out.println("HOD Menu Loaded.");
    }
}

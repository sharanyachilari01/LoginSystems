package com.main;

import java.util.*;
import java.sql.Connection;
import com.db.DBConnection;
import com.login.AuthService;
import com.models.*;
import com.exceptions.*;

public class MainApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try (Connection conn = DBConnection.getConnection()) {
            AuthService auth = new AuthService(conn);
            System.out.println("=== LOGIN SYSTEM WITH ROLE-BASED ACCESS ===");
            System.out.print("Enter username: ");
            String uname = sc.nextLine();
            System.out.print("Enter password: ");
            String pwd = sc.nextLine();
            User user = auth.login(uname, pwd);
            System.out.println("\n✅ Welcome, " + user.getName() + " (" + user.getRole() + ")");
            switch (user.getRole()) {
            case "ADMIN" -> Dashboard.adminMenu((Admin) user);
            case "STUDENT" -> Dashboard.studentMenu((Student) user);
            case "TEACHING_STAFF"-> Dashboard.facultyMenu((TeachingStaff) user);
            case "HOD" -> Dashboard.hodMenu((HOD)user);
            case "NON_TEACHING" -> Dashboard.nonTeachingMenu((NonTeachingStaff) user);
            default -> System.out.println("⚠ Role not supported yet.");
        }
        } catch (UserNotFoundException | InvalidCredentialsException e) {
            System.out.println("❌ " + e.getMessage());
        } catch (Exception e) {
            System.out.println("⚠ Error: " + e.getMessage());
        }
    }
}

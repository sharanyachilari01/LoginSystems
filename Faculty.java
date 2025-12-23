package com.models;

import com.db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Faculty extends User {
    protected String department;

    public Faculty(String username, String email, String password,
            String role, String department) {

super(username, email, password, role); 
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    // ✅ Fixed markAttendance method with full functionality
    public static void markAttendance(String studentUsername, String subject, String status, String markedBy) {
        String sql = "INSERT INTO attendance (student_username, subject, status, marked_by, date) VALUES (?, ?, ?, ?, CURDATE())";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, studentUsername);
            ps.setString(2, subject);
            ps.setString(3, status);
            ps.setString(4, markedBy);
            ps.executeUpdate();

            System.out.println("✅ Attendance marked successfully for " + studentUsername);

        } catch (SQLException e) {
            System.out.println("❌ Error marking attendance: " + e.getMessage());
        }
    }

    public void showMenu() {
        System.out.println("=== FACULTY DASHBOARD ===");
        System.out.println("1. View Attendance");
        System.out.println("2. Update Attendance");
        System.out.println("3. View Publications");
        System.out.println("4. Approve Project Requests");
    }
}

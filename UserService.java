package com.services;

import java.sql.*;
import com.db.DBConnection;
import com.utils.HashUtil;

public class UserService {

    // Add User
    public static void addUser(String username, String password, String role, String dept) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO users (username, password, role, department) VALUES (?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, HashUtil.md5(password));
            ps.setString(3, role);
            ps.setString(4, dept.isEmpty() ? null : dept);
            ps.executeUpdate();
            System.out.println("✅ User added successfully!");
        } catch (SQLException e) {
            System.out.println("❌ Error adding user: " + e.getMessage());
        }
    }

    // View All Users
    public static void viewUsers() {
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT username, role, email FROM users")) {

            System.out.println("\n--- ALL USERS ---");
            while (rs.next()) {
                System.out.printf("%-15s %-20s %-25s%n",
                        rs.getString("username"),
                        rs.getString("role"),
                        rs.getString("email"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching users: " + e.getMessage());
        }
    }
    

    // Delete User
    public static void deleteUser(String username) {
        try (Connection conn = DBConnection.getConnection()) {
            // Delete dependent records first
            String[] tables = {"system_logs", "attendance", "project_requests", "projects", "publications"};
            for (String table : tables) {
                try (PreparedStatement ps = conn.prepareStatement(
                        "DELETE FROM " + table + " WHERE username=? OR student_username=? OR faculty_username=?")) {
                    ps.setString(1, username);
                    ps.setString(2, username);
                    ps.setString(3, username);
                    ps.executeUpdate();
                }
            }
            // Delete user
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE username=?")) {
                ps.setString(1, username);
                int rows = ps.executeUpdate();
                System.out.println(rows > 0 ? "✅ User deleted successfully!" : "⚠ User not found.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error deleting user: " + e.getMessage());
        }
    }
    public static void changePassword(String username, String newPass) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "UPDATE users SET password=? WHERE username=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, HashUtil.md5(newPass)); // Hash the password
            ps.setString(2, username);
            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("✅ Password changed successfully for " + username);
            else System.out.println("⚠ User not found: " + username);
        } catch (SQLException e) {
            System.out.println("❌ Error changing password: " + e.getMessage());
        }
    }
}

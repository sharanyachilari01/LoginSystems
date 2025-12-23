package com.services;

import java.sql.*;
import java.util.*;

import com.db.DBConnection;
import com.utils.HashUtil;

public class UserService {

    // Add User
	public static void addUser(String username, String email, String password,
            String role, String department) {

String hashedPassword = HashUtil.md5(password);

String sql = "INSERT INTO users (username, email, password, role, department) " +
         "VALUES (?, ?, ?, ?, ?)";

try (Connection con = DBConnection.getConnection();
 PreparedStatement ps = con.prepareStatement(sql)) {

ps.setString(1, username);
ps.setString(2, email);
ps.setString(3, hashedPassword);
ps.setString(4, role);
ps.setString(5, department);

ps.executeUpdate();
System.out.println("User added successfully!");

} catch (SQLException e) {
e.printStackTrace();
}
}


    // View All Users
	public static void viewUsers() {

	    String sql = "SELECT username, email, role, department FROM users";

	    try (Connection con = DBConnection.getConnection();
	         Statement st = con.createStatement();
	         ResultSet rs = st.executeQuery(sql)) {

	        System.out.println("\nUSERNAME | EMAIL | ROLE | DEPARTMENT");
	        System.out.println("------------------------------------------------");

	        while (rs.next()) {
	            System.out.println(
	                rs.getString("username") + " | " +
	                rs.getString("email") + " | " +
	                rs.getString("role") + " | " +
	                rs.getString("department")
	            );
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

    

    // Delete User
	public static void deleteUser(String username) {
	    try (Connection conn = DBConnection.getConnection()) {

	        // system_logs
	        PreparedStatement ps1 = conn.prepareStatement(
	            "DELETE FROM system_logs WHERE username = ?"
	        );
	        ps1.setString(1, username);
	        ps1.executeUpdate();

	        // attendance
	        PreparedStatement ps2 = conn.prepareStatement(
	            "DELETE FROM attendance WHERE student_username = ?"
	        );
	        ps2.setString(1, username);
	        ps2.executeUpdate();

	        // users table
	        PreparedStatement ps = conn.prepareStatement(
	            "DELETE FROM users WHERE username = ?"
	        );
	        ps.setString(1, username);

	        int rows = ps.executeUpdate();

	        System.out.println(rows > 0
	                ? "✅ User deleted successfully!"
	                : "⚠ User not found.");

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

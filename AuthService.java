package com.login;

import java.sql.*;
import java.security.MessageDigest;
import com.db.DBConnection;
import com.exceptions.*;
import com.models.*;
import java.util.Scanner;

public class AuthService {

    private Connection conn;

    public AuthService(Connection conn) {
        this.conn = conn;
    }

    // ---------- Utility: Hash Password using SHA-256 ----------
    private String hashPassword(String password) throws InvalidInputException {
        if (password == null) {
            throw new InvalidInputException("Password cannot be null");
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.trim().getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new InvalidInputException("Error hashing password: " + e.getMessage());
        }
    }

    // ---------- Register New User ----------
    public void registerUser(String username, String password, String email, String role, String department)
            throws InvalidInputException, DatabaseConnectionException {

        if (username == null || username.trim().isEmpty())
            throw new InvalidInputException("Username cannot be empty.");
        if (password == null || password.trim().length() < 6)
            throw new InvalidInputException("Password must be at least 6 characters long.");
        if (email == null || !email.contains("@"))
            throw new InvalidInputException("Invalid email format.");

        String hashedPassword = hashPassword(password);

        String sql = "INSERT INTO users (username, password, email, role, department) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username.trim());
            ps.setString(2, hashedPassword);
            ps.setString(3, email.trim());
            ps.setString(4, role.toUpperCase().trim());
            ps.setString(5, department != null ? department.trim() : null);
            ps.executeUpdate();
            System.out.println("✅ User registered successfully!");
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new InvalidInputException("Username or email already exists.");
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database error: " + e.getMessage());
        }
    }

    // ---------- Login with Hashed Password ----------
    public User login(String username, String password)
            throws InvalidInputException, UserNotFoundException, InvalidCredentialsException, DatabaseConnectionException {

        if (username == null || username.trim().isEmpty())
            throw new InvalidInputException("Username cannot be empty.");
        if (password == null || password.trim().isEmpty())
            throw new InvalidInputException("Password cannot be empty.");

        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username.trim());
            ResultSet rs = ps.executeQuery();

            if (!rs.next())
                throw new UserNotFoundException("No such user found.");

            String dbPassword = rs.getString("password");
            String hashedInput = hashPassword(password);

            if (!dbPassword.equals(hashedInput))
                throw new InvalidCredentialsException("Incorrect password.");

            String role = rs.getString("role").toUpperCase();
            String email = rs.getString("email");
            String dept = rs.getString("department");

            return switch (role) {

            case "ADMIN" ->
                new Admin(username, email, dbPassword, role);

            case "STUDENT" ->
                new Student(username, email, dbPassword, role);

            case "HOD" ->
                new HOD(username, email, dbPassword, role, dept);

            case "TEACHING_STAFF" ->
                new TeachingStaff(username, email, dbPassword, role, dept);

            case "NON_TEACHING" ->
                new NonTeachingStaff(username, email, dbPassword, role, dept);

            default ->
                throw new InvalidInputException("Unknown role: " + role);
        };


        } catch (SQLException e) {
            throw new DatabaseConnectionException("SQL Error: " + e.getMessage());
        }
    }

    // ---------- Change Password with Simulated OTP ----------
 // ---------- Change Password with Simulated OTP ----------
    public boolean changePasswordWithOTP(String username, String email, String newPassword)
            throws InvalidInputException, DatabaseConnectionException {

        Scanner sc = new Scanner(System.in);

        // Generate 6-digit OTP
        String otp = String.valueOf((int) (Math.random() * 900000) + 100000);

        // Simulate sending OTP (for console-based demo)
        System.out.println("\n📩 Sending OTP to registered email: " + email);
        System.out.println("✅ OTP has been sent to " + email + " (Simulated)");
        System.out.println("DEBUG (for demo): OTP = " + otp);

        System.out.print("Enter OTP received on your email: ");
        String enteredOTP = sc.nextLine();

        if (!otp.equals(enteredOTP)) {
            System.out.println("❌ Invalid OTP. Password change cancelled.");
            logActivity(username, "Failed password change attempt (Invalid OTP)");
            return false;
        }

        // Update password in DB after verifying OTP
        String sql = "UPDATE users SET password = ? WHERE username = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hashPassword(newPassword.trim()));
            ps.setString(2, username.trim());
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Password changed successfully for " + username + "!");
                logActivity(username, "Password changed successfully");
                return true;
            } else {
                System.out.println("❌ User not found.");
                logActivity(username, "Password change failed (User not found)");
                return false;
            }
        } catch (SQLException e) {
            logActivity(username, "Password change failed (SQL Error)");
            throw new DatabaseConnectionException("SQL Error: " + e.getMessage());
        }
    
}
 // ---------- Utility: Log activity to system_logs ----------
    private void logActivity(String username, String activity) {
        String sql = "INSERT INTO system_logs (username, activity) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, activity);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("⚠ Could not log activity: " + e.getMessage());
        }
    }

}

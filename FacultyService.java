package com.services;

import java.sql.*;
import com.db.DBConnection;

public class FacultyService {

    // Add Publication
    public static void addPublication(String faculty, String title, String journal, int year) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO publications (faculty_username, title, journal, year) VALUES (?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, faculty);
            ps.setString(2, title);
            ps.setString(3, journal);
            ps.setInt(4, year);
            ps.executeUpdate();
            System.out.println("✅ Publication added!");
        } catch (SQLException e) {
            System.out.println("❌ Error adding publication: " + e.getMessage());
        }
    }

    // Delete Publication
    public static void deletePublication(String faculty, String title) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM publications WHERE faculty_username=? AND title=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, faculty);
            ps.setString(2, title);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "✅ Publication deleted!" : "⚠ No such publication found.");
        } catch (SQLException e) {
            System.out.println("❌ Error deleting publication: " + e.getMessage());
        }
    }
    public static void viewPublications(String faculty) {
        String sql = "SELECT * FROM publications WHERE faculty_username=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, faculty);
            ResultSet rs = ps.executeQuery();
            System.out.println("\n--- PUBLICATIONS ---");
            boolean hasPublications = false;
            while (rs.next()) {
                hasPublications = true;
                System.out.printf("• %s (%s, %d)%n",
                        rs.getString("title"),
                        rs.getString("journal"),
                        rs.getInt("year"));
            }
            if (!hasPublications) {
                System.out.println("No publications found.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching publications: " + e.getMessage());
        }
    }

    // Mark Attendance
    public static void markAttendance(String student, String faculty) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO attendance (student_username, faculty_username, status, date) VALUES (?, ?, 'PRESENT', NOW())";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, student);
            ps.setString(2, faculty);
            ps.executeUpdate();
            System.out.println("✅ Attendance marked for " + student);
        } catch (SQLException e) {
            System.out.println("❌ Error marking attendance: " + e.getMessage());
        }
    }

    // View Attendance
    public static void viewAttendance(String faculty) {
        String sql = "SELECT * FROM attendance WHERE faculty_username=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, faculty);
            ResultSet rs = ps.executeQuery();
            System.out.println("\n--- Attendance Records ---");
            while (rs.next()) {
                System.out.printf("%s | %s | %s%n",
                        rs.getString("student_username"),
                        rs.getString("status"),
                        rs.getTimestamp("date"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching attendance: " + e.getMessage());
        }
    }

    // Approve or Reject Project Request
    public static void approveOrRejectProject(String student, String faculty, boolean approve) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE project_requests SET status=? WHERE student_username=? AND faculty_username=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, approve ? "APPROVED" : "REJECTED");
            ps.setString(2, student);
            ps.setString(3, faculty);
            ps.executeUpdate();
            System.out.println("✅ Project request " + (approve ? "approved" : "rejected") + "!");
        } catch (SQLException e) {
            System.out.println("❌ Error updating project request: " + e.getMessage());
        }
    }

    // Update Project Marks
    public static void updateProjectMarks(String student, int marks) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE projects SET marks=? WHERE student_username=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, marks);
            ps.setString(2, student);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "✅ Marks updated!" : "⚠ Student not found.");
        } catch (SQLException e) {
            System.out.println("❌ Error updating marks: " + e.getMessage());
        }
    }

    // Apply for Leave (Faculty/HOD)
    public static void applyLeave(String faculty, String reason) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO leave_requests (faculty_username, reason, status, applied_on) VALUES (?, ?, 'PENDING', NOW())";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, faculty);
            ps.setString(2, reason);
            ps.executeUpdate();
            System.out.println("✅ Leave request submitted!");
        } catch (SQLException e) {
            System.out.println("❌ Error submitting leave: " + e.getMessage());
        }
    }

    // HOD Approve/Reject Leave
    public static void approveOrRejectLeave(int leaveId, boolean approve) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE leave_requests SET status=? WHERE leave_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, approve ? "APPROVED" : "REJECTED");
            ps.setInt(2, leaveId);
            ps.executeUpdate();
            System.out.println("✅ Leave request updated!");
        } catch (SQLException e) {
            System.out.println("❌ Error updating leave: " + e.getMessage());
        }
    }

    // View Pending Leaves (for HOD)
    public static void viewPendingLeaves() {
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM leave_requests WHERE status='PENDING'")) {

            System.out.println("\n--- PENDING LEAVE REQUESTS ---");
            while (rs.next()) {
                System.out.printf("Leave ID: %d | Faculty: %s | Reason: %s | Applied On: %s%n",
                        rs.getInt("leave_id"),
                        rs.getString("faculty_username"),
                        rs.getString("reason"),
                        rs.getTimestamp("applied_on"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching leaves: " + e.getMessage());
        }
    }
    
}

package com.services;

import java.sql.*;
import com.db.DBConnection;

public class StudentService {
	public static void applyForProjectGuide(String student, String faculty, String projectTitle) {
	    try (Connection conn = DBConnection.getConnection()) {
	        String query = "INSERT INTO project_requests (student_username, faculty_username, project_title, status) VALUES (?, ?, ?, 'PENDING')";
	        PreparedStatement ps = conn.prepareStatement(query);
	        ps.setString(1, student);
	        ps.setString(2, faculty);
	        ps.setString(3, projectTitle);
	        ps.executeUpdate();
	        System.out.println("✅ Project request sent from " + student + " to " + faculty);
	    } catch (SQLException e) {
	        System.out.println("❌ Error sending project request: " + e.getMessage());
	    }
	}

    public static void checkGrades(String uname) {
        String sql = "SELECT title, marks FROM projects WHERE student_username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, uname);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n--- PROJECT GRADES ---");
            boolean hasGrades = false;
            while (rs.next()) {
                System.out.printf("Project: %s | Marks: %d%n",
                        rs.getString("title"),
                        rs.getInt("marks"));
                hasGrades = true;
            }
            if (!hasGrades) {
                System.out.println("⚠ No grades found for " + uname);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching grades: " + e.getMessage());
        }
    }
    public static void viewProfile(String uname) {
        String sql = "SELECT * FROM students WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, uname);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.printf("\nName: %s%nSemester: %d%nGPA: %.2f%nDepartment: %s%n",
                        rs.getString("full_name"),
                        rs.getInt("semester"),
                        rs.getDouble("gpa"),
                        rs.getString("department"));
            } else {
                System.out.println("⚠ No profile found for " + uname);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching profile: " + e.getMessage());
        }
    }
}

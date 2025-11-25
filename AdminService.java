package com.services;

import java.sql.*;
import com.db.DBConnection;

public class AdminService {

    // Create a new course
    public static void createCourse(String name, String dept) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO courses (course_name, department) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, dept);
            ps.executeUpdate();
            System.out.println("✅ Course created: " + name + " (" + dept + ")");
        } catch (SQLException e) {
            System.out.println("❌ Error creating course: " + e.getMessage());
        }
    }

    // Assign faculty to an existing course
    public static void assignFaculty(int courseId, String facultyUsername) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "UPDATE courses SET faculty_assigned=? WHERE course_id=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, facultyUsername);
            ps.setInt(2, courseId);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Faculty " + facultyUsername + " assigned to course " + courseId);
            } else {
                System.out.println("⚠ Course ID not found: " + courseId);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error assigning faculty: " + e.getMessage());
        }
    }
}

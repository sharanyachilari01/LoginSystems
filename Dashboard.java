package com.main;
import java.sql.*;
import java.util.Scanner;

import com.db.*;
import com.models.*;
import com.services.AdminService;
import com.services.FacultyService;
import com.services.StudentService;
import com.services.UserService;
public class Dashboard {
    // ================= ADMIN MENU =================
    public static void adminMenu(Admin admin) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== ADMIN DASHBOARD ===");
            System.out.println("1. View All Users");
            System.out.println("2. Create Course");
            System.out.println("3. Assign Faculty to Course");
            System.out.println("4. View System Logs");
            System.out.println("5. Add User");
            System.out.println("6. Delete User");
            System.out.println("7. Change Password");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> UserService.viewUsers();
                case 2 -> createCourse(sc);
                case 3 -> assignFaculty(sc);
                case 4 -> viewSystemLogs();
                case 5 -> addUser(sc, admin);
                case 6 -> deleteUser(sc, admin);
                case 7 -> changePassword(admin.getUsername(), admin.getEmail());
                case 0 -> System.out.println("Logging out...");
                default -> System.out.println("❌ Invalid choice!");
            }
        } while (choice != 0);
    }

    // ================= FACULTY MENU =================
    public static void facultyMenu(TeachingStaff f) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== TEACHING STAFF DASHBOARD ===");
            System.out.println("1. Mark Attendance");
            System.out.println("2. View Attendance");
            System.out.println("3. View Publications");
            System.out.println("4. Add Publication");
            System.out.println("5. Delete Publication");
            System.out.println("6. Approve/Reject Project Requests");
            System.out.println("7. Update Project Marks");
            System.out.println("8. Apply for leave");
            System.out.println("9. Change Password");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Student Username: ");
                    String stu = sc.nextLine();
                    FacultyService.markAttendance(stu, f.getUsername());
                }
                case 2 -> FacultyService.viewAttendance(f.getUsername());
                case 3 -> viewPublications(f.getUsername());
                case 4 -> {
                    System.out.print("Enter Publication Title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter Journal Name: ");
                    String journal = sc.nextLine();
                    FacultyService.addPublication(f.getUsername(), title, journal, 2025);
                }
                case 5 -> {
                    System.out.print("Enter Publication Title to Delete: ");
                    String title = sc.nextLine();
                    FacultyService.deletePublication(f.getUsername(), title);
                }
                case 6 -> approveOrRejectProjects(sc, f.getUsername());
                case 7 -> {
                    System.out.print("Enter Student Username: ");
                    String stu = sc.nextLine();
                    System.out.print("Enter Marks: ");
                    int marks = sc.nextInt();
                    sc.nextLine();
                    FacultyService.updateProjectMarks(stu, marks);
                }
                case 8 -> {
                    System.out.print("Enter Reason for Leave: ");
                    String reason = sc.nextLine();
                    FacultyService.applyLeave(f.getUsername(), reason);
                }
                case 9 -> changePassword(f.getUsername(), f.getEmail());
                case 0 -> System.out.println("Logging out...");
                default -> System.out.println("❌ Invalid choice!");
            }
        } while (choice != 0);
    }

    // ================= HOD MENU =================
    public static void hodMenu(HOD hod) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== HOD DASHBOARD ===");
            System.out.println("1. Mark Attendance");
            System.out.println("2. View Attendance");
            System.out.println("3. View Publications");
            System.out.println("4. Add Publication");
            System.out.println("5. Delete Publication");
            System.out.println("6. Approve/Reject Project Requests");
            System.out.println("7. Update Project Marks");
            System.out.println("8. Apply for Leave");
            System.out.println("9. Approve/Reject Leaves");
            System.out.println("10. Change Password");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Student Username: ");
                    String stu = sc.nextLine();
                    FacultyService.markAttendance(stu, hod.getUsername());
                }
                case 2 -> FacultyService.viewAttendance(hod.getUsername());
                case 3 -> viewPublications(hod.getUsername());
                case 4 -> {
                    System.out.print("Enter Publication Title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter Journal Name: ");
                    String journal = sc.nextLine();
                    FacultyService.addPublication(hod.getUsername(), title, journal, 2025);
                }
                case 5 -> {
                    System.out.print("Enter Publication Title to Delete: ");
                    String title = sc.nextLine();
                    FacultyService.deletePublication(hod.getUsername(), title);
                }
                case 6 -> approveOrRejectProjects(sc, hod.getUsername());
                case 7 -> {
                    System.out.print("Enter Student Username: ");
                    String stu = sc.nextLine();
                    System.out.print("Enter Marks: ");
                    int marks = sc.nextInt();
                    sc.nextLine();
                    FacultyService.updateProjectMarks(stu, marks);
                }
                case 8 -> {
                    System.out.print("Enter Reason for Leave: ");
                    String reason = sc.nextLine();
                    FacultyService.applyLeave(hod.getUsername(), reason);
                }
                case 9 -> {
                    FacultyService.viewPendingLeaves();
                    System.out.print("Enter Leave ID to Approve/Reject: ");
                    int leaveId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Approve (Y/N): ");
                    boolean approve = sc.nextLine().equalsIgnoreCase("Y");
                    FacultyService.approveOrRejectLeave(leaveId, approve);
                }
                case 10 -> changePassword(hod.getUsername(), hod.getEmail());
                case 0 -> System.out.println("Logging out...");
                default -> System.out.println("❌ Invalid choice!");
            }
        } while (choice != 0);
    }

    // ================= NON-TEACHING STAFF MENU =================
    public static void nonTeachingMenu(NonTeachingStaff nt) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== NON-TEACHING STAFF DASHBOARD ===");
            System.out.println("1. Mark Attendance");
            System.out.println("2. View Attendance");
            System.out.println("3. Apply for leave");
            System.out.println("4. Change Password");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Student Username: ");
                    String stu = sc.nextLine();
                    FacultyService.markAttendance(stu, nt.getUsername());
                }
                case 2 -> FacultyService.viewAttendance(nt.getUsername());
                case 3 -> {
                    System.out.print("Enter Reason for Leave: ");
                    String reason = sc.nextLine();
                    FacultyService.applyLeave(nt.getUsername(), reason);
                }
                case 4 -> changePassword(nt.getUsername(), nt.getEmail());
                case 0 -> System.out.println("Logging out...");
                default -> System.out.println("❌ Invalid choice!");
            }
        } while (choice != 0);
    }

    // ================= STUDENT MENU =================
    public static void studentMenu(Student s) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== STUDENT DASHBOARD ===");
            System.out.println("1. View Profile");
            System.out.println("2. Check Grades");
            System.out.println("3. Apply for Project Guide");
            System.out.println("4. Change Password");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> viewProfile(s.getUsername());
                case 2 -> checkGrades(s.getUsername());
                case 3 -> applyForProjectGuide(sc, s.getUsername());
                case 4 -> changePassword(s.getUsername(), s.getEmail());
                case 0 -> System.out.println("Logging out...");
                default -> System.out.println("❌ Invalid choice!");
            }
        } while (choice != 0);
    }

    // ================= COMMON DASHBOARD UTILITIES =================

    // Add User
    private static void addUser(Scanner sc, Admin admin) {
        System.out.print("Enter Username: ");
        String username = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        System.out.print("Enter Role (STUDENT, TEACHING_STAFF, HOD, NON_TEACHING, ADMIN): ");
        String role = sc.nextLine().toUpperCase();
        System.out.print("Enter Department (if applicable, else leave blank): ");
        String dept = sc.nextLine();
        UserService.addUser(username, password, role, dept);
    }

    // Delete User
    private static void deleteUser(Scanner sc, Admin admin) {
        System.out.print("Enter Username to Delete: ");
        String username = sc.nextLine();
        UserService.deleteUser(username);
    }

    // Change Password
    private static void changePassword(String username, String email) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter new password: ");
        String newPass = sc.nextLine();
        UserService.changePassword(username, newPass);
    }

    // View System Logs
    private static void viewSystemLogs() {
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM system_logs ORDER BY timestamp DESC LIMIT 10")) {

            System.out.println("\n--- RECENT SYSTEM LOGS ---");
            while (rs.next()) {
                System.out.printf("[%s] %s - %s%n",
                        rs.getTimestamp("timestamp"),
                        rs.getString("username"),
                        rs.getString("activity"));
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching logs: " + e.getMessage());
        }
    }


    // Approve/Reject Projects (menu wrapper)
    private static void approveOrRejectProjects(Scanner sc, String faculty) {
        System.out.print("Enter Student Username: ");
        String stu = sc.nextLine();
        System.out.print("Approve Project? (Y/N): ");
        boolean approve = sc.nextLine().equalsIgnoreCase("Y");
        FacultyService.approveOrRejectProject(stu, faculty, approve);
    }

    // View Publications (menu wrapper)
    private static void viewPublications(String faculty) {
        // Call FacultyService to fetch and print publications
        FacultyService.viewPublications(faculty);
    }

    // Student Methods
    private static void viewProfile(String uname) {
        StudentService.viewProfile(uname);
    }

    private static void checkGrades(String uname) {
        StudentService.checkGrades(uname);
    }


    private static void applyForProjectGuide(Scanner sc, String uname) {
        System.out.print("Enter Faculty Username: ");
        String faculty = sc.nextLine();
        System.out.print("Enter Project Title: ");
        String title = sc.nextLine();
        StudentService.applyForProjectGuide(uname, faculty, title);
    }

    // Course-related Methods
    private static void createCourse(Scanner sc) {
        System.out.print("Enter Course Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Department: ");
        String dept = sc.nextLine();
        AdminService.createCourse(name, dept);
    }

    private static void assignFaculty(Scanner sc) {
        System.out.print("Enter Course ID: ");
        int cid = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Faculty Username: ");
        String uname = sc.nextLine();
        AdminService.assignFaculty(cid, uname);
    }

}
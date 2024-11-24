package main;

import dao.ProjectRepositoryImpl;
import entity.Employee;
import entity.Project;
import entity.Task;
import exception.EmployeeNotFoundException;
import exception.ProjectNotFoundException;
import util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class ProjectManagement {
    public static void main(String[] args) {
        Connection connection = DBConnection.getConnection();
        ProjectRepositoryImpl projectRepo = new ProjectRepositoryImpl(connection);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Add Employee");
            System.out.println("2. Add Project");
            System.out.println("3. Delete Employee");
            System.out.println("4. Delete Project");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Employee ID: ");
                    int empId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String empName = scanner.nextLine();
                    System.out.print("Enter Designation: ");
                    String designation = scanner.nextLine();
                    System.out.print("Enter Gender: ");
                    String gender = scanner.nextLine();
                    System.out.print("Enter Salary: ");
                    double salary = scanner.nextDouble();
                    Employee emp = new Employee(empId, empName, designation, gender, salary);
                    if (projectRepo.createEmployee(emp)) {
                        System.out.println("Employee added successfully!");
                    } else {
                        System.out.println("Failed to add employee.");
                    }
                    break;

                case 2:
                    System.out.print("Enter Project ID: ");
                    int projId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Project Name: ");
                    String projName = scanner.nextLine();
                    System.out.print("Enter Description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter Start Date (yyyy-mm-dd): ");
                    Date startDate = Date.valueOf(scanner.nextLine());
                    System.out.print("Enter Status: ");
                    String status = scanner.nextLine();
                    Project proj = new Project(projId, projName, description, startDate, status);
                    if (projectRepo.createProject(proj)) {
                        System.out.println("Project added successfully!");
                    } else {
                        System.out.println("Failed to add project.");
                    }
                    break;

                case 3:
                    System.out.print("Enter Employee ID to delete: ");
                    int delEmpId = scanner.nextInt();
                    try {
                        if (projectRepo.deleteEmployee(delEmpId)) {
                            System.out.println("Employee deleted successfully!");
                        }
                    } catch (EmployeeNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 4:
                    System.out.print("Enter Project ID to delete: ");
                    int delProjId = scanner.nextInt();
                    try {
                        if (projectRepo.deleteProject(delProjId)) {
                            System.out.println("Project deleted successfully!");
                        }
                    } catch (ProjectNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 5:
                    System.out.println("Exit");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}

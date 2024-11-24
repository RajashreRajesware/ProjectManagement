package dao;

import entity.Employee;
import entity.Project;
import entity.Task;
import exception.EmployeeNotFoundException;
import exception.ProjectNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepositoryImpl implements IProjectRepository {
    private Connection conn;

    public ProjectRepositoryImpl(Connection conn) {
        this.conn = conn;
    }

    public boolean createEmployee(Employee emp) {
        String query = "INSERT INTO Employee (id, name, designation, gender, salary) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, emp.getId());
            stmt.setString(2, emp.getName());
            stmt.setString(3, emp.getDesignation());
            stmt.setString(4, emp.getGender());
            stmt.setDouble(5, emp.getSalary());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createProject(Project project) {
        String query = "INSERT INTO Project (id, project_name, description, start_date, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, project.getId());
            stmt.setString(2, project.getProjectName());
            stmt.setString(3, project.getDescription());
            stmt.setDate(4, project.getStartDate());
            stmt.setString(5, project.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteEmployee(int employeeId) throws EmployeeNotFoundException {
        String query = "DELETE FROM Employee WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, employeeId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new EmployeeNotFoundException("Employee with ID " + employeeId + " not found.");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteProject(int projectId) throws ProjectNotFoundException {
        String query = "DELETE FROM Project WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, projectId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new ProjectNotFoundException("Project with ID " + projectId + " not found.");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Task> getAllTasks(int employeeId, int projectId) {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM Task WHERE employee_id = ? AND project_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, employeeId);
            stmt.setInt(2, projectId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tasks.add(new Task(
                    rs.getInt("task_id"),
                    rs.getString("task_name"),
                    rs.getInt("project_id"),
                    rs.getInt("employee_id"),
                    rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

	@Override
	public boolean createTask(Task task) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean assignProjectToEmployee(int projectId, int employeeId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean assignTaskToEmployee(int taskId, int projectId, int employeeId) {
		// TODO Auto-generated method stub
		return false;
	}
}

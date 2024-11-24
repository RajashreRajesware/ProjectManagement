package dao;

import entity.Employee;
import entity.Project;
import entity.Task;
import exception.EmployeeNotFoundException;
import exception.ProjectNotFoundException;

import java.util.List;

public interface IProjectRepository {
    boolean createEmployee(Employee emp);
    boolean createProject(Project project);
    boolean createTask(Task task);
    boolean assignProjectToEmployee(int projectId, int employeeId);
    boolean assignTaskToEmployee(int taskId, int projectId, int employeeId);
    boolean deleteEmployee(int employeeId) throws EmployeeNotFoundException;
    boolean deleteProject(int projectId) throws ProjectNotFoundException;
    List<Task> getAllTasks(int employeeId, int projectId);
}

package dao;

import entity.Employee;
import entity.Project;
import entity.Task;
import exception.EmployeeNotFoundException;
import exception.ProjectNotFoundException;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectRepositoryImplTest {

    private Connection connection;
    private ProjectRepositoryImpl projectRepository;

    @BeforeEach
    void setUp() throws SQLException {
        // Initialize in-memory H2 database
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        projectRepository = new ProjectRepositoryImpl(connection);

        // Create tables
        connection.createStatement().execute("CREATE TABLE Employee (" +
                "id INT PRIMARY KEY, " +
                "name VARCHAR(255), " +
                "designation VARCHAR(100), " +
                "gender VARCHAR(10), " +
                "salary DECIMAL(10, 2), " +
                "project_id INT)");

        connection.createStatement().execute("CREATE TABLE Project (" +
                "id INT PRIMARY KEY, " +
                "project_name VARCHAR(255), " +
                "description TEXT, " +
                "start_date DATE, " +
                "status VARCHAR(50))");

        connection.createStatement().execute("CREATE TABLE Task (" +
                "task_id INT PRIMARY KEY, " +
                "task_name VARCHAR(255), " +
                "project_id INT, " +
                "employee_id INT, " +
                "status VARCHAR(50))");
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.createStatement().execute("DROP ALL OBJECTS"); // Clean database
        connection.close();
    }

    @Test
    void testCreateEmployee_Success() {
        Employee employee = new Employee(1, "John Doe", "Developer", "Male", 75000.0);

        boolean result = projectRepository.createEmployee(employee);

        assertTrue(result, "Employee creation should return true");

        // Verify the data in the database
        try (var stmt = connection.createStatement();
             var rs = stmt.executeQuery("SELECT * FROM Employee WHERE id = 1")) {
            assertTrue(rs.next(), "Employee record should exist in the database");
            assertEquals("John Doe", rs.getString("name"));
            assertEquals("Developer", rs.getString("designation"));
            assertEquals("Male", rs.getString("gender"));
            assertEquals(75000.0, rs.getDouble("salary"));
        } catch (SQLException e) {
            fail("Exception while verifying employee record: " + e.getMessage());
        }
    }

    @Test
    void testSearchProjectsAndTasksForEmployee() throws SQLException {
        // Insert prerequisite data
        connection.createStatement().execute("INSERT INTO Project (id, project_name, description, status) VALUES (1, 'Project A', 'Description', 'started')");
        connection.createStatement().execute("INSERT INTO Employee (id, name, designation, gender, salary) VALUES (1, 'John Doe', 'Developer', 'Male', 75000.0)");
        connection.createStatement().execute("INSERT INTO Task (task_id, task_name, project_id, employee_id, status) VALUES (1, 'Task A', 1, 1, 'Assigned')");

        List<Task> tasks = projectRepository.getAllTasks(1, 1);

        assertNotNull(tasks, "Task list should not be null");
        assertEquals(1, tasks.size(), "There should be one task assigned to the employee");

        Task task = tasks.get(0);
        assertEquals("Task A", task.getTaskName(), "Task name should match");
        assertEquals(1, task.getProjectId(), "Project ID should match");
        assertEquals(1, task.getEmployeeId(), "Employee ID should match");
        assertEquals("Assigned", task.getStatus(), "Task status should match");
    }

    @Test
    void testDeleteEmployee_ThrowsEmployeeNotFoundException() {
        EmployeeNotFoundException exception = assertThrows(
                EmployeeNotFoundException.class,
                () -> projectRepository.deleteEmployee(99), // Non-existent employee ID
                "Should throw EmployeeNotFoundException for non-existent employee"
        );

        assertEquals("Employee with ID 99 not found.", exception.getMessage());
    }

    @Test
    void testDeleteProject_ThrowsProjectNotFoundException() {
        ProjectNotFoundException exception = assertThrows(
                ProjectNotFoundException.class,
                () -> projectRepository.deleteProject(99), // Non-existent project ID
                "Should throw ProjectNotFoundException for non-existent project"
        );

        assertEquals("Project with ID 99 not found.", exception.getMessage());
    }
}

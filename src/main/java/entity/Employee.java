package entity;

public class Employee {
    private int id;
    private String name;
    private String designation;
    private String gender;
    private double salary;

    public Employee() {}

    public Employee(int id, String name, String designation, String gender, double salary) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.gender = gender;
        this.salary = salary;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
}

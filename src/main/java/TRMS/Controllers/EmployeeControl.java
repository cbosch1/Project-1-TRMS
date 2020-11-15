package TRMS.Controllers;

import java.util.List;

import TRMS.Pojos.Employee;

public interface EmployeeControl {

    public boolean createEmployee(int employeeId, String name, String title, int supervisor, 
                                    String department, boolean deptHead);

    public Employee readEmployee(int employeeId);

    public List<Employee> readAllEmployees();

    public boolean updateEmployee(int employeeId, String name, String title, int supervisor,
                                    String department, boolean deptHead);

    public boolean deleteEmployee(int employeeId);
}

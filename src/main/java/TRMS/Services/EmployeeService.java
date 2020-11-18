package TRMS.services;

import java.util.List;

import TRMS.pojos.Employee;

public interface EmployeeService {

    public int createEmployee(int employeeId, String name, String title, int supervisor, 
                                    String department, boolean deptHead);

    public Employee readEmployee(int employeeId);

    public List<Employee> readAllEmployees();

    public boolean updateEmployee(int employeeId, String name, String title, int supervisor,
                                    String department, boolean deptHead);

    public boolean deleteEmployee(int employeeId);
}

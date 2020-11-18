package TRMS.daos;

import java.sql.SQLException;
import java.util.List;

import TRMS.pojos.Employee;

public interface EmployeeDao {
    
    public int createEmployee(Employee employee) throws SQLException;

    public Employee readEmployee(int employeeId) throws SQLException;

    public List<Employee> readAllEmployees() throws SQLException;

    public boolean updateEmployee(Employee employee) throws SQLException;

    public boolean deleteEmployee(int employeeId) throws SQLException;
}

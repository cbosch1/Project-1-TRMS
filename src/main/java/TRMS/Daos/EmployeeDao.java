package TRMS.Daos;

import java.sql.SQLException;
import java.util.List;

import TRMS.Pojos.Employee;

public interface EmployeeDao {
    
    public void createEmployee(Employee employee) throws SQLException;

    public Employee readEmployee(int employeeId) throws SQLException;

    public List<Employee> readAllEmployees() throws SQLException;

    public void updateEmployee(Employee employee) throws SQLException;

    public void deleteEmployee(int employeeId) throws SQLException;
}

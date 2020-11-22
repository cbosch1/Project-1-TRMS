package TRMS.daos;

import java.sql.SQLException;
import java.util.List;

import TRMS.pojos.Employee;

/**
 * EmployeeDao interface, the goal of an implementation of this 
 * interface will be to take in employee related values and perform
 * CRUD operations with a method of storage the implementation specifies.
 */
public interface EmployeeDao {

    /**
     * Inserts employee object into storage.
     * @param employee object to be inserted
     * @return int - The id of the inserted employee
     */
    public int createEmployee(Employee employee) throws SQLException;

    /**
     * Returns a specific employee from storage with provided employee id.
     * @param employeeId of the employee desired
     * @return employee object that has that id
     */
    public Employee readEmployee(int employeeId) throws SQLException;

    /**
     * Returns all employees stored in storage as a list object.
     * @return List of employee objects
     */
    public List<Employee> readAllEmployees() throws SQLException;

    /**
     * Updates employee data inside storage, pulls the object's id number
     * and will update all information except the id number.
     * @param employee object with updated fields
     * @return true if request was successful
     */
    public boolean updateEmployee(Employee employee) throws SQLException;

    /**
     * Removes employee data from storage that relates to employee with provided id.
     * @param employeeId that relates to the employee to delete.
     * @return true if request was successful
     */
    public boolean deleteEmployee(int employeeId) throws SQLException;
}

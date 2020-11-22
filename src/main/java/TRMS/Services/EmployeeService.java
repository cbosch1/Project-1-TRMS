package TRMS.services;

import java.util.List;

import TRMS.pojos.Employee;

/**
 * EmployeeService interface, the goal of most methods inside an implementation
 * of this interface will be to take in various values and convert them to the form
 * an equivalent Dao requires, then call it's equivalent Dao method, 
 * handle any exceptions, and return what the Dao returns.
 */
public interface EmployeeService {

    /**
     * Converts inputs into an employee object and then sends object
     * to the Dao. Returns the generated id.
     * @param name of the employee in the format "First Last"
     * @param title The employee's title within the company "Director of Marketing, Software Engineer, ect."
     * @param supervisor The employeeId of this employee's supervisor
     * @param department Department which the employee works in
     * @param deptHead If this employee is head of their department or not
     * @return attachId the generated Id for this object
     */
    public int createEmployee(String name, String title, int supervisor, 
                                    String department, boolean deptHead);                                    

    /**
     * Calls the Dao to remove the employee with the given id
     * @param employeeId the Id of the employee to delete
     * @return true if the request was successful
     */
    public Employee readEmployee(int employeeId);

    /**
     * Calls the Dao to return a list of all employees stored
     * @return A list of employee objects
     */
    public List<Employee> readAllEmployees();

    /**
     * Calls the Dao to read the employee with the given id
     * @param employeeId the Id of the employee to read
     * @return Employee object representing the employee with given id
     */
    public boolean updateEmployee(int employeeId, String name, String title, int supervisor,
                                    String department, boolean deptHead);

    /**
     * Calls the Dao to update an employee with given id. The input fields
     * should be given the updated information.
     * @param employeeId the Id of the employee to update
     * @param name of the employee in the format "First Last"
     * @param title The employee's title within the company "Director of Marketing, Software Engineer, ect."
     * @param supervisor The employeeId of this employee's supervisor
     * @param department Department which the employee works in
     * @param deptHead If this employee is head of their department or not
     * @return true if the request was successful
     */
    public boolean deleteEmployee(int employeeId);
}

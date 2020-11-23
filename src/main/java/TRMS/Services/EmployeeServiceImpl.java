package TRMS.services;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.daos.EmployeeDao;
import TRMS.pojos.Employee;

/**
 * EmployeeService implementation, dependency is an object that implements
 * the EmployeeDao interface. Most methods inside this object will take in
 * various values and convert them to the form the Dao requires, then call it's 
 * equivalent Dao method, handle any exceptions and return what the Dao returns.
 */
public class EmployeeServiceImpl implements EmployeeService {

    private static Logger Log = LogManager.getLogger("Service");

    private EmployeeDao employeeDao;

    public EmployeeServiceImpl(EmployeeDao employeeDao){
        super();
        this.employeeDao = employeeDao;
    }

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
    @Override
    public int createEmployee(String name, String title, int supervisor, String department, boolean deptHead) {
        Log.info("Responding to create employee...");
        int result = -1;

        Employee employee = new Employee(0, name, title, supervisor, department, deptHead);

        try {
            result = employeeDao.createEmployee(employee);
            Log.info("Successfully created employee");
        } catch (SQLException e) {
            Log.warn("Error thrown in dao call: ", e);
        }
        return result;
    }

    /**
     * Calls the Dao to remove the employee with the given id
     * @param employeeId the Id of the employee to delete
     * @return true if the request was successful
     */
    @Override
    public boolean deleteEmployee(int employeeId) {
        Log.info("Responding to delete employee...");
        boolean result = false;

        try {
            result = employeeDao.deleteEmployee(employeeId);
            if(result) {
                Log.info("Successfully deleted employee");
            } else {
                Log.warn("Employee was not successfully deleted");
            }
        } catch (SQLException e) {
            Log.warn("Error thrown in dao call: ", e);
        }
        return result;
    }
    
    /**
     * Calls the Dao to return a list of all employees stored
     * @return A list of employee objects
     */
    @Override
    public List<Employee> readAllEmployees() {
        Log.info("Responding to read all employees...");
        List<Employee> result = null;

        try {
            result = employeeDao.readAllEmployees();
            Log.info("Successfully retrieved employees");

        } catch (SQLException e) {
            Log.warn("Error thrown in dao call: ", e);
        }
        return result;
    }

    /**
     * Calls the Dao to read the employee with the given id
     * @param employeeId the Id of the employee to read
     * @return Employee object representing the employee with given id
     */
    @Override
    public Employee readEmployee(int employeeId) {
        Log.info("Responding to read employee...");
        Employee result = null;

        try {
            result = employeeDao.readEmployee(employeeId);
            Log.info("Successfully retrieved employee");

        } catch (SQLException e) {
            Log.warn("Error thrown in dao call: ", e);
        }
        return result;
    }

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
    @Override
    public boolean updateEmployee(int employeeId, String name, String title, int supervisor, String department,
                                    boolean deptHead) {
        Log.info("Responding to update employee...");
        boolean result = false;

        Employee employee = new Employee(employeeId, name, title, supervisor, department, deptHead);

        try {
            result = employeeDao.updateEmployee(employee);
            if(result) {
                Log.info("Successfully updated employee");
            } else {
                Log.warn("Something went wrong, employee not updated properly");
            }
        } catch (SQLException e) {
            Log.warn("Error thrown in dao call: ", e);
        }
        return result;
    }
}

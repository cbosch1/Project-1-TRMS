package TRMS.daos;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.pojos.Employee;
import TRMS.util.ConnectionUtil;


/**
 * Employee Dao that connects to the employee table in the postgres database.
 */
public class EmployeeDaoPostgres implements EmployeeDao {

    private PreparedStatement stmt;

    private static Logger Log = LogManager.getLogger("Dao");

    private ConnectionUtil connUtil;

    public EmployeeDaoPostgres(ConnectionUtil connectionUtil){
        super();
        connUtil = connectionUtil;
    }

    /**
     * Inserts employee object into database. This object's id number
     * will be ignored as the database will generate it's own.
     * @param employee object to be inserted
     * @return int - The generated id
     */
    @Override
    public int createEmployee(Employee employee) throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * Removes employee data from that database that relates to employee with provided id.
     * @param employeeId that relates to the employee to delete.
     * @return true if request was successful
     */
    @Override
    public boolean deleteEmployee(int employeeId) throws SQLException {
        // TODO Auto-generated method stub
        return false;

    }

    /**
     * Returns all employees stored in the database as a list object.
     * @return List of employee objects
     */
    @Override
    public List<Employee> readAllEmployees() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Returns a specific employee from the database with provided employee id.
     * @param employeeId of the employee desired
     * @return employee object that has that id
     */
    @Override
    public Employee readEmployee(int employeeId) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Updates employee data inside the database, pulls the object's id number
     * and will update all information except the id number.
     * @param employee object with updated fields
     * @return true if request was successful
     */
    @Override
    public boolean updateEmployee(Employee employee) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }
    
}

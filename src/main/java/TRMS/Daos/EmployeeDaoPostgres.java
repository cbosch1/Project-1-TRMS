package TRMS.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.pojos.Employee;
import TRMS.util.ConnectionUtil;


/**
 * Employee Dao that connects to the employee table in a postgres database.
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
        int result; 

        try(Connection conn = connUtil.createConnection()) {
            Log.info("Received request to insert employee: " + employee.getName());

            String sql = "INSERT INTO employee VALUES (Default,?,?,?,?,?,?) RETURNING emp_id;";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, employee.getName().split(" ")[1]);
            stmt.setString(2, employee.getName().split(" ")[0]);
            stmt.setString(3, employee.getTitle());
            stmt.setInt(4, employee.getSupervisor());
            stmt.setString(5, employee.getDepartment());
            stmt.setBoolean(6, employee.getDeptHead());

            ResultSet rs = stmt.executeQuery();
            rs.next();
            result = rs.getInt(1);
            Log.info("Request completed, generated employee id: " + result);

        } catch (SQLException e){
            Log.warn("SQLException thrown in create related to employee: " + employee.getName(), e);
            throw e;
        }

        return result;
    }

    /**
     * Removes employee data from that database that relates to employee with provided id.
     * @param employeeId that relates to the employee to delete.
     * @return true if request was successful
     */
    @Override
    public boolean deleteEmployee(int employeeId) throws SQLException {
        boolean result = false; 

        try(Connection conn = connUtil.createConnection()) {
            Log.info("Received request to delete employee with id: " + employeeId);

            String sql = "DELETE FROM employee WHERE emp_id = ?;";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, employeeId);

            result = (1 == stmt.executeUpdate());

            if (result){
                Log.info("Request completed, employee with id: " + employeeId + " was deleted.");
            } else
                Log.warn("Request to delete employee with id: " + employeeId +" was NOT completed");

        } catch (SQLException e){
            Log.warn("SQLException thrown in employee delete for id: " + employeeId, e);
            throw e;
        }

        return result;
    }

    /**
     * Returns all employees stored in the database as a list object.
     * @return List of employee objects
     */
    @Override
    public List<Employee> readAllEmployees() throws SQLException {
        List<Employee> result = new ArrayList<>(); 

        try(Connection conn = connUtil.createConnection()) {
            Log.info("Received request to retrieve all employees");

            String sql = "SELECT * FROM employee;";
            stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Employee e = new Employee(rs.getInt(1), rs.getString(3) +" "+ rs.getString(2), rs.getString(4), 
                                            rs.getInt(5), rs.getString(6), rs.getBoolean(7));
                result.add(e);
            }
            Log.info("Request completed, retrieved " + result.size() + " employees");

        } catch (SQLException e){
            Log.warn("SQLException thrown in read all employees", e);
            throw e;
        }

        return result;
    }

    /**
     * Returns a specific employee from the database with provided employee id.
     * @param employeeId of the employee desired
     * @return employee object that has that id
     */
    @Override
    public Employee readEmployee(int employeeId) throws SQLException {
        Employee result = null; 

        try(Connection conn = connUtil.createConnection()) {
            Log.info("Received request to retrieve employee with id: " + employeeId);

            String sql = "SELECT * FROM employee WHERE emp_id = ?;";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, employeeId);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            result = new Employee(rs.getInt(1), rs.getString(3) +" "+ rs.getString(2), rs.getString(4), 
                                    rs.getInt(5), rs.getString(6), rs.getBoolean(7));

            Log.info("Request completed, retrieved employee: " + result.getName());

        } catch (SQLException e){
            Log.warn("SQLException thrown in read for employee with id: " + employeeId, e);
            throw e;
        }

        return result;
    }

    /**
     * Updates employee data inside the database, pulls the object's id number
     * and will update all information except the id number.
     * @param employee object with updated fields
     * @return true if request was successful
     */
    @Override
    public boolean updateEmployee(Employee employee) throws SQLException {
        boolean result = false; 

        try(Connection conn = connUtil.createConnection()) {
            Log.info("Received request to update employee with id: " + employee.getEmployeeId());

            String sql = "UPDATE employee SET lastname=?, firstname=?, title=?, supervisor=?, "
                                                + "department=?, dept_head=? WHERE emp_id = ?;";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, employee.getName().split(" ")[1]);
            stmt.setString(2, employee.getName().split(" ")[0]);
            stmt.setString(3, employee.getTitle());
            stmt.setInt(4, employee.getSupervisor());
            stmt.setString(5, employee.getDepartment());
            stmt.setBoolean(6, employee.getDeptHead());
            stmt.setInt(7, employee.getEmployeeId());

            result = (1 == stmt.executeUpdate());

            if (result){
                Log.info("Request completed, employee with id: " + employee.getEmployeeId() + " was updated.");
            } else
                Log.warn("Request to update employee with id: " + employee.getEmployeeId() +" was NOT completed");

        } catch (SQLException e){
            Log.warn("SQLException thrown in updated related to employee: " + employee.getEmployeeId(), e);
            throw e;
        }

        return result;
    }
    
}

package TRMS.daos;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.pojos.Employee;
import TRMS.util.ConnectionUtil;

public class EmployeeDaoPostgres implements EmployeeDao {

    private PreparedStatement stmt;

    private static Logger Log = LogManager.getLogger("Dao");

    private ConnectionUtil connUtil;

    public EmployeeDaoPostgres(ConnectionUtil connectionUtil){
        super();
        connUtil = connectionUtil;
    }

    @Override
    public void createEmployee(Employee employee) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteEmployee(int employeeId) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public List<Employee> readAllEmployees() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Employee readEmployee(int employeeId) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateEmployee(Employee employee) throws SQLException {
        // TODO Auto-generated method stub

    }
    
}

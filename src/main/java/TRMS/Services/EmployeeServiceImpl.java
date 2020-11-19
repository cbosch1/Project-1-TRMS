package TRMS.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.daos.EmployeeDao;
import TRMS.pojos.Employee;

public class EmployeeServiceImpl implements EmployeeService {

    private static Logger Log = LogManager.getLogger("Service");

    private EmployeeDao employeeDao;

    public EmployeeServiceImpl(EmployeeDao employeeDao){
        super();
        this.employeeDao = employeeDao;
    }

    @Override
    public int createEmployee(String name, String title, int supervisor, String department, boolean deptHead) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean deleteEmployee(int employeeId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<Employee> readAllEmployees() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Employee readEmployee(int employeeId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean updateEmployee(int employeeId, String name, String title, int supervisor, String department,
                                    boolean deptHead) {
        // TODO Auto-generated method stub
        return false;
    }
    
}

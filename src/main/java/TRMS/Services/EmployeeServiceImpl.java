package TRMS.services;

import java.util.List;

import TRMS.pojos.Employee;

public class EmployeeServiceImpl implements EmployeeService {

    @Override
    public boolean createEmployee(int employeeId, String name, String title, int supervisor, String department,
            boolean deptHead) {
        // TODO Auto-generated method stub
        return false;
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

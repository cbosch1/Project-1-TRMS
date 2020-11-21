package TRMS.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.services.EmployeeService;
import io.javalin.http.Context;

public class EmployeeControl {

    private static Logger Log = LogManager.getLogger("Control");

    private EmployeeService service;

    public EmployeeControl(EmployeeService service){
        super();
        this.service = service;
    }

    public void createEmployee(Context ctx){

    }

    public void readEmployee(Context ctx){

    }

    public void readAllEmployees(Context ctx){

    }

    public void updateEmployee(Context ctx){

    }

    public void deleteEmployee(Context ctx){
        
    }
}

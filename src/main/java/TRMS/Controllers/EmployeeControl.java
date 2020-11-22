package TRMS.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.services.EmployeeService;
import io.javalin.http.Context;

/**
 * EmployeeControl implementation, dependency is an object that implements
 * the EmployeeService interface. The controller will take in a Javalin Context
 * object at each method, pull the information necessary to process the request, 
 * convert that information into what the service expects, and pass it on to the
 * equivalent service object. The controller will also take what ever returns from
 * the Service, and supply it back to the ctx properly.
 */
public class EmployeeControl {

    private static Logger Log = LogManager.getLogger("Control");

    private EmployeeService service;

    public EmployeeControl(EmployeeService service){
        super();
        this.service = service;
    }

    /**
     * For creating an employee within the system.
     * Parses form parameters out of the context object and passes them
     * to the proper Service method. Then this method will apply the correct 
     * status code to the ctx
     * 
     * @param ctx A context object that has at least the following formParams:
     * <ul><li>name</li>
     * <li>title</li>
     * <li>supervisor</li>
     * <li>department</li>
     * <li>deptHead</li></ul>
     */
    public void createEmployee(Context ctx){

    }

    /**
     * For reading the employee with the given employeeId.
     * Parses form parameters out of the context object and passes them
     * to the proper Service method. The ctx will have values set to the 
     * returned employee object. Then this method will apply the correct 
     * status code to the ctx
     * 
     * @param ctx A context object that has at least the following formParams:
     * <ul><li>employeeId</li></ul>
     */
    public void readEmployee(Context ctx){

    }

    /**
     * For reading all employees in the TRMS. The ctx will have values set to the 
     * returned employee objects. Then this method will apply the correct 
     * status code to the ctx
     */
    public void readAllEmployees(Context ctx){

    }

    /**
     * For updating an already existing employee within the system.
     * Parses form parameters out of the context object and passes them
     * to the proper Service method. Then this method will apply the correct 
     * status code to the ctx
     * 
     * @param ctx A context object that has at least the following formParams:
     * <ul><li>employeeId</li>
     * <li>name</li>
     * <li>title</li>
     * <li>supervisor</li>
     * <li>department</li>
     * <li>deptHead</li></ul>
     */
    public void updateEmployee(Context ctx){

    }

    /**
     * For deleting an already existing employee within the system.
     * Parses form parameters out of the context object and passes them
     * to the proper Service method. Then this method will apply the correct 
     * status code to the ctx
     * 
     * @param ctx A context object that has at least the following formParams:
     * <ul><li>employeeId</li></ul>
     */
    public void deleteEmployee(Context ctx){
        
    }
}

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
    private AuthControl auth;

    public EmployeeControl(EmployeeService service, AuthControl auth){
        super();
        this.service = service;
        this.auth = auth;
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
        try {
            String name = ctx.formParam("name");
            String title = ctx.formParam("title");
            int supervisor = Integer.parseInt(ctx.formParam("supervisor"));
            String department = ctx.formParam("department");
            Boolean deptHead = Boolean.parseBoolean(ctx.formParam("deptHead"));
            
            int returnId = service.createEmployee(name, title, supervisor, department, deptHead);

            ctx.json(returnId);
            ctx.status(200);
            Log.info("Successfully inserted employee, id returned: " + returnId);

        } catch (NumberFormatException e){
            Log.warn("NumberFormatException thrown while creating employee: " + e);
            ctx.html("NumberFormatException thrown: " + e);
            ctx.status(500);

        } catch (Exception e) {
            Log.warn("Exception thrown while creating employee: " + e);
            ctx.html("Exception thrown: " + e);
            ctx.status(500);
        }
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
        
        try {
            int employeeId = Integer.parseInt(ctx.formParam("employeeId"));
            ctx.json(service.readEmployee(employeeId));

            ctx.status(200);
            Log.info("Successfully read employee");

        } catch (NumberFormatException e){
            Log.warn("NumberFormatException thrown while reading employee: " + e);
            ctx.html("NumberFormatException thrown: " + e);
            ctx.status(500);

        } catch (Exception e) {
            Log.warn("Exception thrown while reading employee: " + e);
            ctx.html("Exception thrown: " + e);
            ctx.status(500);
        }
    }

    /**
     * For reading all employees in the TRMS. The ctx will have values set to the 
     * returned employee objects. Then this method will apply the correct 
     * status code to the ctx
     */
    public void readAllEmployees(Context ctx){
        try {
            ctx.json(service.readAllEmployees());

            ctx.status(200);
            Log.info("Successfully read all employees");

        } catch (Exception e) {
            Log.warn("Exception thrown while reading all employees: " + e);
            ctx.html("Exception thrown: " + e);
            ctx.status(500);
        }
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
        try {
            int employeeId = Integer.parseInt(ctx.formParam("employeeId"));
            String name = ctx.formParam("name");
            String title = ctx.formParam("title");
            int supervisor = Integer.parseInt(ctx.formParam("supervisor"));
            String department = ctx.formParam("department");
            Boolean deptHead = Boolean.parseBoolean(ctx.formParam("deptHead"));
    
            if (service.updateEmployee(employeeId, name, title, supervisor, department, deptHead)){
                Log.info("Employee successfully updated");
                ctx.status(200);
            } else {
                Log.warn("Service returned false while updating employee");
                ctx.status(500);
            }

        } catch (NumberFormatException e){
            Log.warn("NumberFormatException thrown while updating employee: " + e);
            ctx.html("NumberFormatException thrown: " + e);
            ctx.status(500);

        } catch (Exception e) {
            Log.warn("Exception thrown while updating employee: " + e);
            ctx.html("Exception thrown: " + e);
            ctx.status(500);
        }
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
        try {
            int employeeId = Integer.parseInt(ctx.formParam("employeeId"));

            if (service.deleteEmployee(employeeId)){
                Log.info("Employee successfully deleted");
                ctx.status(200);
            } else {
                Log.warn("Service returned false while deleting employee");
                ctx.status(500);
            }
        } catch (NumberFormatException e){
            Log.warn("NumberFormatException thrown while deleting employee: " + e);
            ctx.html("NumberFormatException thrown: " + e);
            ctx.status(500);

        } catch (Exception e) {
            Log.warn("Exception thrown while deleting employee: " + e);
            ctx.html("Exception thrown: " + e);
            ctx.status(500);
        }
    }

    public void readManager(Context ctx){
        if (auth.checkUser(ctx)){
            try {
                int employeeId = Integer.parseInt(ctx.formParam("employeeId"));
                ctx.json(service.readEmployee(employeeId));

                ctx.status(200);
                Log.info("Successfully read employee");

            } catch (NumberFormatException e){
                Log.warn("NumberFormatException thrown while reading employee: " + e);
                ctx.html("NumberFormatException thrown: " + e);
                ctx.status(500);

            } catch (Exception e) {
                Log.warn("Exception thrown while reading employee: " + e);
                ctx.html("Exception thrown: " + e);
                ctx.status(500);
            }
        }
    }
}

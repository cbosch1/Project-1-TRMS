package TRMS.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.services.ReimburseRequestService;
import io.javalin.http.Context;

/**
 * ReimburseRequestControl implementation, dependency is an object that implements
 * the ReimburseRequestService interface. The controller will take in a Javalin Context
 * object at each method, pull the information necessary to process the request, 
 * convert that information into what the service expects, and pass it on to the
 * equivalent service object. The controller will also take what ever returns from
 * the Service, and supply it back to the ctx properly.
 */
public class ReimburseRequestControl {
    
    private static Logger Log = LogManager.getLogger("Control");

    private ReimburseRequestService service;

    public ReimburseRequestControl(ReimburseRequestService service){
        super();
        this.service = service;
    }

    /**
     * For creating an reimbursement request within the system.
     * Parses form parameters out of the context object and passes them
     * to the proper Service method. Then this method will apply the correct 
     * status code to the ctx.
     * 
     * @param ctx A context object that has at least the following formParams:
     * <ul><li>employeeId</li>
     * <li>location</li>
     * <li>cost</li>
     * <li>type</li>
     * <li>description</li>
     * <li>justification</li>
     * <li>projected - optional</li>
     * <li>urgent - optional</li>
     * <li>status - optional</li>
     * <li>stage - optional</li>
     * <li>dateTime - optional</li></ul>
     */
    public void createRequest(Context ctx){
        
    }

    /**
     * For reading the reimbursement request with the given requestId.
     * Parses form parameters out of the context object and passes them
     * to the proper Service method. The ctx will have values set to the 
     * returned reimbursement request object. Then this method will apply 
     * the correct status code to the ctx.
     * 
     * @param ctx A context object that has at least the following formParams:
     * <ul><li>requestId</li></ul>
     */
    public void readRequest(Context ctx){
        
    }

    /**
     * For reading all reimbursement requests that were sent in by the employee with
     * the given id. Parses form parameters out of the context object and passes them
     * to the proper Service method. The ctx will have values set to the 
     * returned information request objects. Then this method will apply
     * the correct status code to the ctx.
     * 
     * @param ctx A context object that has at least the following formParams:
     * <ul><li>empId</li></ul>
     */
    public void readAllRequestsFor(Context ctx){
        
    }

    /**
     * For reading all reimbursement requests in the TRMS. The ctx will have values
     * set to the returned reimbursement request objects. Then this method will 
     * apply the correct status code to the ctx.
     */
    public void readAllRequests(Context ctx){
        
    }

    /**
     * For updating an already existing reimbursement request within the system.
     * Parses form parameters out of the context object and passes them
     * to the proper Service method. Then this method will apply the correct 
     * status code to the ctx
     * 
     * @param ctx A context object that has at least the following formParams:
     * <ul><li>employeeId</li>
     * <li>location</li>
     * <li>cost</li>
     * <li>type</li>
     * <li>description</li>
     * <li>justification</li>
     * <li>projected - optional</li>
     * <li>urgent - optional</li>
     * <li>status - optional</li>
     * <li>stage - optional</li>
     * <li>dateTime - optional</li></ul>
     */
    public void updateRequest(Context ctx){
        
    }

    /**
     * For deleting an already existing reimbursement request within the system.
     * Parses form parameters out of the context object and passes them
     * to the proper Service method. Then this method will apply the correct 
     * status code to the ctx
     * 
     * @param ctx A context object that has at least the following formParams:
     * <ul><li>requestId</li></ul>
     */
    public void deleteRequest(Context ctx){
        
    }
}

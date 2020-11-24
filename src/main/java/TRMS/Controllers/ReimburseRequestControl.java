package TRMS.controllers;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.enums.AppStage;
import TRMS.enums.AppStatus;
import TRMS.enums.EventType;
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
        try {
            int employeeId = Integer.parseInt(ctx.formParam("employeeId"));
            String location = ctx.formParam("location");
            Double cost = Double.parseDouble(ctx.formParam("cost"));
            EventType type = EventType.valueOf(ctx.formParam("type"));
            String description = ctx.formParam("description");
            String justification = ctx.formParam("justification");
            Double projected = Double.parseDouble(ctx.formParam("projected"));
            boolean urgent = Boolean.parseBoolean(ctx.formParam("urgent"));
            AppStatus status = AppStatus.valueOf(ctx.formParam("status"));
            AppStage stage = AppStage.valueOf(ctx.formParam("stage"));
            LocalDateTime dateTime = LocalDateTime.parse(ctx.formParam("dateTime"));
            
            int returnId = service.createRequest(employeeId, location, cost, type, description, justification, 
                                                projected, urgent, status, stage, dateTime);

            ctx.json(returnId);
            ctx.status(200);
            Log.info("Successfully inserted reimbursement request, id returned: " + returnId);

        } catch (NumberFormatException e){
            Log.warn("NumberFormatException thrown while creating reimbursement request: " + e);
            ctx.html("NumberFormatException thrown: " + e);
            ctx.status(500);

        } catch (Exception e) {
            Log.warn("Exception thrown while creating reimbursement request: " + e);
            ctx.html("Exception thrown: " + e);
            ctx.status(500);
        }
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
        try {
            int requestId = Integer.parseInt(ctx.formParam("requestId"));
            ctx.json(service.readRequest(requestId));

            ctx.status(200);
            Log.info("Successfully read reimbursement request");

        } catch (NumberFormatException e){
            Log.warn("NumberFormatException thrown while reading reimbursement request: " + e);
            ctx.html("NumberFormatException thrown: " + e);
            ctx.status(500);

        } catch (Exception e) {
            Log.warn("Exception thrown while reading reimbursement request: " + e);
            ctx.html("Exception thrown: " + e);
            ctx.status(500);
        }
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
        int employeeId = -1;
        try {
            employeeId = Integer.parseInt(ctx.formParam("employeeId"));
            ctx.json(service.readAllRequestsFor(employeeId));

            ctx.status(200);
            Log.info("Successfully read all reimbursement requests for employee: " + employeeId);

        } catch (Exception e) {
            Log.warn("Exception thrown while reading all reimbursement requests for employee: " + employeeId + e);
            ctx.html("Exception thrown: " + e);
            ctx.status(500);
        }
    }

    /**
     * For reading all reimbursement requests in the TRMS. The ctx will have values
     * set to the returned reimbursement request objects. Then this method will 
     * apply the correct status code to the ctx.
     */
    public void readAllRequests(Context ctx){
        try {
            ctx.json(service.readAllRequests());

            ctx.status(200);
            Log.info("Successfully read all reimbursement requests");

        } catch (Exception e) {
            Log.warn("Exception thrown while reading all reimbursement requests: " + e);
            ctx.html("Exception thrown: " + e);
            ctx.status(500);
        }
    }

    /**
     * For updating an already existing reimbursement request within the system.
     * Parses form parameters out of the context object and passes them
     * to the proper Service method. Then this method will apply the correct 
     * status code to the ctx
     * 
     * @param ctx A context object that has at least the following formParams:
     * <ul><li>requestId</li>
     * <li>employeeId</li>
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
        try {
            int requestId = Integer.parseInt(ctx.formParam("requestId"));
            int employeeId = Integer.parseInt(ctx.formParam("employeeId"));
            String location = ctx.formParam("location");
            Double cost = Double.parseDouble(ctx.formParam("cost"));
            EventType type = EventType.valueOf(ctx.formParam("type"));
            String description = ctx.formParam("description");
            String justification = ctx.formParam("justification");
            Double projected = Double.parseDouble(ctx.formParam("projected"));
            boolean urgent = Boolean.parseBoolean(ctx.formParam("urgent"));
            AppStatus status = AppStatus.valueOf(ctx.formParam("status"));
            AppStage stage = AppStage.valueOf(ctx.formParam("stage"));
            LocalDateTime dateTime = LocalDateTime.parse(ctx.formParam("dateTime"));
            
            if (service.updateRequest(requestId, employeeId, location, cost, type, description, justification, 
                                        projected, urgent, status, stage, dateTime)){
                Log.info("Reimbursement request successfully updated");
                ctx.status(200);
            } else {
                Log.warn("Service returned false while updating reimbursement request");
                ctx.status(500);
            }

        } catch (NumberFormatException e){
            Log.warn("NumberFormatException thrown while updating reimbursement request: " + e);
            ctx.html("NumberFormatException thrown: " + e);
            ctx.status(500);

        } catch (Exception e) {
            Log.warn("Exception thrown while updating reimbursement request: " + e);
            ctx.html("Exception thrown: " + e);
            ctx.status(500);
        }
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
        try {
            int requestId = Integer.parseInt(ctx.formParam("requestId"));

            if (service.deleteRequest(requestId)){
                Log.info("Reimbursement Request successfully deleted");
                ctx.status(200);
            } else {
                Log.warn("Service returned false while deleting reimbursement request");
                ctx.status(500);
            }
        } catch (NumberFormatException e){
            Log.warn("NumberFormatException thrown while deleting reimbursement request: " + e);
            ctx.html("NumberFormatException thrown: " + e);
            ctx.status(500);

        } catch (Exception e) {
            Log.warn("Exception thrown while deleting reimbursement request: " + e);
            ctx.html("Exception thrown: " + e);
            ctx.status(500);
        }
    }
}

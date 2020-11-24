package TRMS.controllers;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.services.InfoRequestService;
import io.javalin.http.Context;

/**
 * InfoRequestControl implementation, dependency is an object that implements
 * the InfoRequestService interface. The controller will take in a Javalin Context
 * object at each method, pull the information necessary to process the request, 
 * convert that information into what the service expects, and pass it on to the
 * equivalent service object. The controller will also take what ever returns from
 * the Service, and supply it back to the ctx properly.
 */
public class InfoRequestControl {

    private static Logger Log = LogManager.getLogger("Control");

    private InfoRequestService service;

    public InfoRequestControl(InfoRequestService service){
        super();
        this.service = service;
    }

    /**
     * For creating an information request within the system.
     * Parses form parameters out of the context object and passes them
     * to the proper Service method. Then this method will apply the correct 
     * status code to the ctx.
     * 
     * @param ctx A context object that has at least the following formParams:
     * <ul><li>relatedId</li>
     * <li>destinationId</li>
     * <li>urgent - optional</li>
     * <li>description</li>
     * <li>dateTime - optional</li></ul>
     */
    public void createInfoRequest(Context ctx){
        try {
            int relatedId = Integer.parseInt(ctx.formParam("relatedId"));
            int destinationId = Integer.parseInt(ctx.formParam("destinationId"));
            boolean urgent = Boolean.parseBoolean(ctx.formParam("urgent"));
            String description = ctx.formParam("description");
            LocalDateTime dateTime = LocalDateTime.parse(ctx.formParam("dateTime"));
            
            int returnId = service.createInfoRequest(relatedId, destinationId, urgent, description, dateTime);

            ctx.json(returnId);
            ctx.status(200);
            Log.info("Successfully inserted info request, id returned: " + returnId);

        } catch (NumberFormatException e){
            Log.warn("NumberFormatException thrown while creating info request: " + e);
            ctx.html("NumberFormatException thrown: " + e);
            ctx.status(500);

        } catch (Exception e) {
            Log.warn("Exception thrown while creating info request: " + e);
            ctx.html("Exception thrown: " + e);
            ctx.status(500);
        }
    }

    /**
     * For reading the information request with the given infoId.
     * Parses form parameters out of the context object and passes them
     * to the proper Service method. The ctx will have values set to the 
     * returned information request object. Then this method will apply 
     * the correct status code to the ctx.
     * 
     * @param ctx A context object that has at least the following formParams:
     * <ul><li>infoId</li></ul>
     */
    public void readInfoRequest(Context ctx){
        try {
            int infoId = Integer.parseInt(ctx.formParam("infoId"));
            ctx.json(service.readInfoRequest(infoId));

            ctx.status(200);
            Log.info("Successfully read information request");

        } catch (NumberFormatException e){
            Log.warn("NumberFormatException thrown while reading information request: " + e);
            ctx.html("NumberFormatException thrown: " + e);
            ctx.status(500);

        } catch (Exception e) {
            Log.warn("Exception thrown while reading information request: " + e);
            ctx.html("Exception thrown: " + e);
            ctx.status(500);
        }
    }

    /**
     * For reading any information requests that were sent to the employee with
     * the given id. Parses form parameters out of the context object and passes them
     * to the proper Service method. The ctx will have values set to the 
     * returned information request objects. Then this method will apply
     * the correct status code to the ctx.
     * 
     * @param ctx A context object that has at least the following formParams:
     * <ul><li>empId</li></ul>
     */
    public void readAllInfoFor(Context ctx){
        int employeeId = -1;
        try {
            employeeId = Integer.parseInt(ctx.formParam("destinationId"));
            ctx.json(service.readAllInfoFor(employeeId));

            ctx.status(200);
            Log.info("Successfully read all info requests for employee: " + employeeId);

        } catch (Exception e) {
            Log.warn("Exception thrown while reading all info requests for employee: " + employeeId + e);
            ctx.html("Exception thrown: " + e);
            ctx.status(500);
        }
    }

    /**
     * For reading all information requests in the TRMS. The ctx will have values
     * set to the returned information request objects. Then this method will 
     * apply the correct status code to the ctx.
     */
    public void readAllInfoReq(Context ctx){
        try {
            ctx.json(service.readAllInfoReq());

            ctx.status(200);
            Log.info("Successfully read all information requests");

        } catch (Exception e) {
            Log.warn("Exception thrown while reading all information requests: " + e);
            ctx.html("Exception thrown: " + e);
            ctx.status(500);
        }
    }

    /**
     * For updating an already existing information request within the system.
     * Parses form parameters out of the context object and passes them
     * to the proper Service method. Then this method will apply the correct 
     * status code to the ctx
     * 
     * @param ctx A context object that has at least the following formParams:
     * <ul><li>infoId</li>
     * <li>relatedId</li>
     * <li>destinationId</li>
     * <li>urgent</li>
     * <li>description</li>
     * <li>dateTime</li></ul>
     */
    public void updateInfoRequest(Context ctx){
        try {
            int infoId = Integer.parseInt(ctx.formParam("infoId"));
            int relatedId = Integer.parseInt(ctx.formParam("relatedId"));
            int destinationId = Integer.parseInt(ctx.formParam("destinationId"));
            boolean urgent = Boolean.parseBoolean(ctx.formParam("urgent"));
            String description = ctx.formParam("description");
            LocalDateTime dateTime = LocalDateTime.parse(ctx.formParam("dateTime"));
    
            if (service.updateInfoRequest(infoId, relatedId, destinationId, urgent, description, dateTime)){
                Log.info("Info request successfully updated");
                ctx.status(200);
            } else {
                Log.warn("Service returned false while updating information request");
                ctx.status(500);
            }

        } catch (NumberFormatException e){
            Log.warn("NumberFormatException thrown while updating information request: " + e);
            ctx.html("NumberFormatException thrown: " + e);
            ctx.status(500);

        } catch (Exception e) {
            Log.warn("Exception thrown while updating information request: " + e);
            ctx.html("Exception thrown: " + e);
            ctx.status(500);
        }
    }

    /**
     * For deleting an already existing information request within the system.
     * Parses form parameters out of the context object and passes them
     * to the proper Service method. Then this method will apply the correct 
     * status code to the ctx
     * 
     * @param ctx A context object that has at least the following formParams:
     * <ul><li>infoId</li></ul>
     */
    public void deleteInfoRequest(Context ctx){
        try {
            int infoId = Integer.parseInt(ctx.formParam("infoId"));

            if (service.deleteInfoRequest(infoId)){
                Log.info("Information Request successfully deleted");
                ctx.status(200);
            } else {
                Log.warn("Service returned false while deleting information request");
                ctx.status(500);
            }
        } catch (NumberFormatException e){
            Log.warn("NumberFormatException thrown while deleting information request: " + e);
            ctx.html("NumberFormatException thrown: " + e);
            ctx.status(500);

        } catch (Exception e) {
            Log.warn("Exception thrown while deleting information request: " + e);
            ctx.html("Exception thrown: " + e);
            ctx.status(500);
        }
    }
}

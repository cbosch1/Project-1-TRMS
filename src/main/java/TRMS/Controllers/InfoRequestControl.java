package TRMS.controllers;

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

    }

    /**
     * For reading the information request with the given requestId.
     * Parses form parameters out of the context object and passes them
     * to the proper Service method. The ctx will have values set to the 
     * returned information request object. Then this method will apply 
     * the correct status code to the ctx.
     * 
     * @param ctx A context object that has at least the following formParams:
     * <ul><li>requestId</li></ul>
     */
    public void readInfoRequest(Context ctx){
        
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
        
    }

    /**
     * For reading all information requests in the TRMS. The ctx will have values
     * set to the returned information request objects. Then this method will 
     * apply the correct status code to the ctx.
     */
    public void readAllInfoReq(Context ctx){
        
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
        
    }
}

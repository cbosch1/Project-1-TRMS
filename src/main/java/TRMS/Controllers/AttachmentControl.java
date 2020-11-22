package TRMS.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.services.AttachmentService;
import io.javalin.http.Context;

/**
 * AttachmentControl implementation, dependency is an object that implements
 * the AttachmentService interface. The controller will take in a Javalin Context
 * object at each method, pull the information necessary to process the request, 
 * convert that information into what the service expects, and pass it on to the
 * equivalent service object. The controller will also take what ever returns from
 * the Service, and supply it back to the ctx properly.
 */
public class AttachmentControl {

    private static Logger Log = LogManager.getLogger("Service");

    private AttachmentService service;

    public AttachmentControl(AttachmentService service){
        super();
        this.service = service;
    }

    /**
     * For creating an attachment within the system.
     * Parses form parameters out of the context object and passes them
     * to the proper Service method. Then this method will apply the correct 
     * status code to the ctx
     * 
     * @param ctx A context object that has at least the following formParams:
     * <ul><li>requestId</li>
     * <li>fileType</li>
     * <li>file</li></ul>
     */
    public void createAttachment(Context ctx){

    }

    /**
     * For reading the attachment with the given attachId.
     * Parses form parameters out of the context object and passes them
     * to the proper Service method. The ctx will have values set to the 
     * returned attachment object.Then this method will apply the correct 
     * status code to the ctx
     * 
     * @param ctx A context object that has at least the following formParams:
     * <ul><li>attachId</li></ul>
     */
    public void readAttachment(Context ctx){

    }

    /**
     * For reading any attachments that are related to the given requestId.
     * Parses form parameters out of the context object and passes them
     * to the proper Service method. The ctx will have values set to the 
     * related attachment ids. Then this method will apply the correct 
     * status code to the ctx
     * 
     * @param ctx A context object that has at least the following formParams:
     * <ul><li>requestId</li></ul>
     */
    public void readRelatedReferences(Context ctx){

    }

    /**
     * For updating an already existing attachment within the system.
     * Parses form parameters out of the context object and passes them
     * to the proper Service method. Then this method will apply the correct 
     * status code to the ctx
     * 
     * @param ctx A context object that has at least the following formParams:
     * <ul><li>attachId</li>
     * <li>requestId</li>
     * <li>fileType</li>
     * <li>file</li></ul>
     */
    public void updateAttachment(Context ctx){

    }

    /**
     * For deleting an already existing attachment within the system.
     * Parses form parameters out of the context object and passes them
     * to the proper Service method. Then this method will apply the correct 
     * status code to the ctx
     * 
     * @param ctx A context object that has at least the following formParams:
     * <ul><li>attachId</li></ul>
     */
    public void deleteAttachment(Context ctx){
        
    }
}
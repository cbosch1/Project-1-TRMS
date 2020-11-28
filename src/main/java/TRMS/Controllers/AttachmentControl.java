package TRMS.controllers;

import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.pojos.Attachment;
import TRMS.services.AttachmentService;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;

/**
 * AttachmentControl implementation, dependency is an object that implements
 * the AttachmentService interface. The controller will take in a Javalin Context
 * object at each method, pull the information necessary to process the request, 
 * convert that information into what the service expects, and pass it on to the
 * equivalent service object. The controller will also take what ever returns from
 * the Service, and supply it back to the ctx properly.
 */
public class AttachmentControl {

    private static Logger Log = LogManager.getLogger("Control");

    private AuthControl auth;
    private AttachmentService service;

    public AttachmentControl(AttachmentService service, AuthControl authControl){
        super();
        this.service = service;
        this.auth = authControl;
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
        try {
            String attachNum = ctx.formParam("attachId");
            int requestId = Integer.parseInt(ctx.formParam("requestId"));
            String fileType = ctx.formParam("fileType");
            UploadedFile file = ctx.uploadedFile("file");
            int returnId;
    
            if (attachNum.equals("")){
                returnId = service.createAttachment(requestId, fileType, file.getContent().readAllBytes());
            } else {
                returnId = service.createAttachment(Integer.parseInt(attachNum), requestId, fileType, file.getContent().readAllBytes());
            }
            ctx.json(returnId);
            ctx.status(200);
            Log.info("Successfully inserted attachment, id returned: " + returnId);

        } catch (NumberFormatException e){
            Log.warn("NumberFormatException thrown while creating attachment: " + e);
            ctx.html("NumberFormatException thrown: " + e);
            ctx.status(500);

        } catch (Exception e) {
            Log.warn("Exception thrown while creating attachment: " + e);
            ctx.html("Exception thrown: " + e);
            ctx.status(500);
        }
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
        try {
            int attachId = Integer.parseInt(ctx.formParam("attachId"));
            ctx.json(service.readAttachment(attachId));
            ctx.status(200);
            Log.info("Successfully read attachment");

        } catch (NumberFormatException e){
            Log.warn("NumberFormatException thrown while reading attachment: " + e);
            ctx.html("NumberFormatException thrown: " + e);
            ctx.status(500);

        } catch (Exception e) {
            Log.warn("Exception thrown while reading attachment: " + e);
            ctx.html("Exception thrown: " + e);
            ctx.status(500);
        }
    }

    public void downloadAttachment(Context ctx) {
        try {
            int attachId = Integer.parseInt(ctx.pathParam("id"));
            Attachment attach = service.readAttachment(attachId);
            List<Object> list = new LinkedList<>();

            list.add(attach.getData());
            list.add(attach.getFileType());
            ctx.json(list);
            
            ctx.status(200);
            Log.info("Successfully read attachment");

        } catch (NumberFormatException e){
            Log.warn("NumberFormatException thrown while downloading attachment: " + e);
            ctx.status(500);

        } catch (Exception e) {
            Log.warn("Exception thrown while downloading attachment: " + e);
            ctx.status(500);
        }
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
        int requestId = -1;

        if(auth.checkUser(ctx)) {
            try {
                requestId = Integer.parseInt(ctx.pathParam("id"));
                ctx.json(service.readRelatedReferences(requestId));

                ctx.status(200);
                Log.info("Successfully read attachments related to: " + requestId);

            } catch (NumberFormatException e){
                Log.warn("NumberFormatException thrown while reading attachments related to: " + requestId + " Exception: " + e);
                ctx.html("NumberFormatException thrown: " + e);
                ctx.status(500);

            } catch (Exception e) {
                Log.warn("Exception thrown while reading attachments related to: " + requestId + " Exception: " + e);
                ctx.html("Exception thrown: " + e);
                ctx.status(500);
            }
        }
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
        try {
            int attachId = Integer.parseInt(ctx.formParam("attachId"));
            int requestId = Integer.parseInt(ctx.formParam("requestId"));
            String fileType = ctx.formParam("fileType");
            UploadedFile file = ctx.uploadedFile("file");
    
            if (service.updateAttachment(attachId, requestId, fileType, file.getContent().readAllBytes())){
                Log.info("Attachment successfully updated");
                ctx.status(200);
            } else {
                Log.warn("Service returned false while updating attachment");
                ctx.status(500);
            }
        } catch (NumberFormatException e){
            Log.warn("NumberFormatException thrown while updating attachment: " + e);
            ctx.html("NumberFormatException thrown: " + e);
            ctx.status(500);

        } catch (Exception e) {
            Log.warn("Exception thrown while updating attachment: " + e);
            ctx.html("Exception thrown: " + e);
            ctx.status(500);
        }
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
        try {
            int attachId = Integer.parseInt(ctx.formParam("attachId"));

            if (service.deleteAttachment(attachId)){
                Log.info("Attachment successfully deleted");
                ctx.status(200);
            } else {
                Log.warn("Service returned false while deleting attachment");
                ctx.status(500);
            }
        } catch (NumberFormatException e){
            Log.warn("NumberFormatException thrown while deleting attachment: " + e);
            ctx.html("NumberFormatException thrown: " + e);
            ctx.status(500);

        } catch (Exception e) {
            Log.warn("Exception thrown while deleting attachment: " + e);
            ctx.html("Exception thrown: " + e);
            ctx.status(500);
        }
    }
}
package TRMS.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.services.AttachmentService;
import io.javalin.http.Context;

public class AttachmentControl {

    private static Logger Log = LogManager.getLogger("Service");

    private AttachmentService service;

    public AttachmentControl(AttachmentService service){
        super();
        this.service = service;
    }

    public void createAttachment(Context ctx){

    }

    public void readAttachment(Context ctx){

    }

    public void readRelatedReferences(Context ctx){

    }

    public void updateAttachment(Context ctx){

    }

    public void deleteAttachment(Context ctx){
        
    }
}
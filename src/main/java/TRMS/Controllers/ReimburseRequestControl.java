package TRMS.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.services.ReimburseRequestService;
import io.javalin.http.Context;

public class ReimburseRequestControl {
    
    private static Logger Log = LogManager.getLogger("Control");

    private ReimburseRequestService service;

    public ReimburseRequestControl(ReimburseRequestService service){
        super();
        this.service = service;
    }

    public void createRequest(Context ctx){
        
    }

    public void readRequest(Context ctx){
        
    }

    public void readAllRequestsFor(Context ctx){
        
    }

    public void readAllRequests(Context ctx){
        
    }

    public void updateRequest(Context ctx){
        
    }

    public void deleteRequest(Context ctx){
        
    }
}

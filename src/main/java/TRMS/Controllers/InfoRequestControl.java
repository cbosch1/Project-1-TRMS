package TRMS.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.services.InfoRequestService;
import io.javalin.http.Context;

public class InfoRequestControl {

    private static Logger Log = LogManager.getLogger("Control");

    private InfoRequestService service;

    public InfoRequestControl(InfoRequestService service){
        super();
        this.service = service;
    }


    public void createInfoRequest(Context ctx){

    }

    public void readInfoRequest(Context ctx){
        
    }

    public void readAllInfoFor(Context ctx){
        
    }

    public void readAllInfoReq(Context ctx){
        
    }

    public void updateInfoRequest(Context ctx){
        
    }

    public void deleteInfoRequest(Context ctx){
        
    }
}

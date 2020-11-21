package TRMS.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.services.UserService;
import io.javalin.http.Context;

public class UserControl {

    private static Logger Log = LogManager.getLogger("Control");

    private UserService service;

    public UserControl(UserService service){
        super();
        this.service = service;
    }
    
    public void createUser(Context ctx){
        
    }

    public void readUser(Context ctx){
        
    }

    public void updateUser(Context ctx){
        
    }

    public void deleteUser(Context ctx){
        
    }
}
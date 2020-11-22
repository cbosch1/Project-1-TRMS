package TRMS.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.services.UserService;
import io.javalin.http.Context;

/**
 * UserControl implementation, dependency is an object that implements
 * the UserService interface. The controller will take in a Javalin Context
 * object at each method, pull the information necessary to process the request, 
 * convert that information into what the service expects, and pass it on to the
 * equivalent service object. The controller will also take what ever returns from
 * the Service, and supply it back to the ctx properly.
 */
public class UserControl {

    private static Logger Log = LogManager.getLogger("Control");

    private UserService service;

    public UserControl(UserService service){
        super();
        this.service = service;
    }
    
    /**
     * For creating a user within the system.
     * Parses form parameters out of the context object and passes them
     * to the proper Service method. Then this method will apply the correct 
     * status code to the ctx
     * 
     * @param ctx A context object that has at least the following formParams:
     * <ul><li>username</li>
     * <li>password</li>
     * <li>employeeId</li>
     * <li>privilege</li></ul>
     */
    public void createUser(Context ctx){
        
    }

    /**
     * For reading the user with the given userId.
     * Parses form parameters out of the context object and passes them
     * to the proper Service method. The ctx will have values set to the 
     * returned user object. Then this method will apply the correct 
     * status code to the ctx
     * 
     * @param ctx A context object that has at least the following formParams:
     * <ul><li>userId</li></ul>
     */
    public void readUser(Context ctx){
        
    }

    /**
     * For updating an already existing user within the system.
     * Parses form parameters out of the context object and passes them
     * to the proper Service method. Then this method will apply the correct 
     * status code to the ctx
     * 
     * @param ctx A context object that has at least the following formParams:
     * <ul><li>userId</li>
     * <li>username</li>
     * <li>password</li>
     * <li>employeeId</li>
     * <li>privilege</li></ul>
     */
    public void updateUser(Context ctx){
        
    }

    /**
     * For deleting an already existing user within the system.
     * Parses form parameters out of the context object and passes them
     * to the proper Service method. Then this method will apply the correct 
     * status code to the ctx
     * 
     * @param ctx A context object that has at least the following formParams:
     * <ul><li>userId</li></ul>
     */
    public void deleteUser(Context ctx){
        
    }
}
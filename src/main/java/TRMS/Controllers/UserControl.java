package TRMS.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.enums.AuthPriv;
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
    private AuthControl auth;

    public UserControl(UserService service, AuthControl auth){
        super();
        this.service = service;
        this.auth = auth;
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
        try {
            String username = ctx.formParam("username");
            String password = ctx.formParam("password");
            int employeeId = Integer.parseInt(ctx.formParam("employeeId"));
            AuthPriv privilege = AuthPriv.valueOf(ctx.formParam("privilege"));
            
            int returnId = service.createUser(username, password, employeeId, privilege);

            ctx.json(returnId);
            ctx.status(200);
            Log.info("Successfully inserted user, id returned: " + returnId);

        } catch (NumberFormatException e){
            Log.warn("NumberFormatException thrown while creating user: " + e);
            ctx.html("NumberFormatException thrown: " + e);
            ctx.status(500);

        } catch (Exception e) {
            Log.warn("Exception thrown while creating user: " + e);
            ctx.html("Exception thrown: " + e);
            ctx.status(500);
        }
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
        if (auth.checkUser(ctx)) {
            try {
                int userId = auth.getId(ctx);
                ctx.json(service.readUser(userId));

                ctx.status(200);
                Log.info("Successfully read user");

            } catch (NumberFormatException e){
                Log.warn("NumberFormatException thrown while reading user: " + e);
                ctx.html("NumberFormatException thrown: " + e);
                ctx.status(500);

            } catch (Exception e) {
                Log.warn("Exception thrown while reading user: " + e);
                ctx.html("Exception thrown: " + e);
                ctx.status(500);
            }
        }
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
        try {
            int userId = Integer.parseInt(ctx.formParam("userId"));
            String username = ctx.formParam("username");
            String password = ctx.formParam("password");
            int employeeId = Integer.parseInt(ctx.formParam("employeeId"));
            AuthPriv privilege = AuthPriv.valueOf(ctx.formParam("privilege"));
    
            if (service.updateUser(userId, username, password, employeeId, privilege)){
                Log.info("User successfully updated");
                ctx.status(200);
            } else {
                Log.warn("Service returned false while updating user");
                ctx.status(500);
            }

        } catch (NumberFormatException e){
            Log.warn("NumberFormatException thrown while updating user: " + e);
            ctx.html("NumberFormatException thrown: " + e);
            ctx.status(500);

        } catch (Exception e) {
            Log.warn("Exception thrown while updating user: " + e);
            ctx.html("Exception thrown: " + e);
            ctx.status(500);
        }
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
        try {
            int userId = Integer.parseInt(ctx.formParam("userId"));

            if (service.deleteUser(userId)){
                Log.info("User successfully deleted");
                ctx.status(200);
            } else {
                Log.warn("Service returned false while deleting user");
                ctx.status(500);
            }
        } catch (NumberFormatException e){
            Log.warn("NumberFormatException thrown while deleting user: " + e);
            ctx.html("NumberFormatException thrown: " + e);
            ctx.status(500);

        } catch (Exception e) {
            Log.warn("Exception thrown while deleting user: " + e);
            ctx.html("Exception thrown: " + e);
            ctx.status(500);
        }
    }

    public void forgotPassword(Context ctx){
        //TODO: Implement forgotPassword id="requester-email"
    }

    public void forgotUsername(Context ctx){
        //TODO: Implement forgotUsername id="requester-email"

    }
}
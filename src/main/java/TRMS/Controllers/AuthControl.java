package TRMS.controllers;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.enums.AuthPriv;
import TRMS.pojos.User;
import TRMS.services.AuthService;
import TRMS.services.UserService;
import io.javalin.http.Context;

public class AuthControl {
    
    private static Logger Log = LogManager.getLogger("Control");

    private static final String TOKEN_NAME = "TRMS-CB-Security-Token";

    private AuthService authService;
    private UserService userService;

    public AuthControl(AuthService authService, UserService userService){
        super();
        this.authService = authService;
        this.userService = userService;
    }

    public boolean login(Context ctx) {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");
        Log.info("Received request to log in user: " + username);
        User user = userService.loginUser(username, password);

        if(Objects.nonNull(user)) {
            ctx.cookieStore(TOKEN_NAME, authService.createToken(user));
            return true;
        } else {
            Log.warn("Login attempt failed");
            return false;
        }
    }

    public boolean logout(Context ctx){
        try {
            ctx.cookieStore(TOKEN_NAME, "");
            Log.info("User successfully logged out");
            return true;
        } catch (Exception e) {
            Log.warn("Log out failed: " + e);
            return false;
        }
    }

    public boolean checkUser(Context ctx) {
        boolean auth = false;
        try {
            auth = authService.validateToken(ctx.cookieStore(TOKEN_NAME));
        } catch (NullPointerException e) {
            Log.warn("No cookie found on user device: " + e);
        }
        return auth;
    }

    public String getUsername(Context ctx) {
        String name = "";

        try {
            name = authService.readTokenUsername(ctx.cookieStore(TOKEN_NAME));

        } catch (NullPointerException e) {
            Log.warn("Token's name was not able to be found: " + e);
        }

        return name;
    }

    public int getId(Context ctx) {
        int id = 0;

        try {
            id = authService.readTokenId(ctx.cookieStore(TOKEN_NAME));
            
        } catch (NullPointerException e) {
            Log.warn("Token's Id was not able to be found: " + e);
        }

        return id;
    }

    public int getEmp(Context ctx) {
        int emp = 0;

        try {
            emp = authService.readTokenEmp(ctx.cookieStore(TOKEN_NAME));
            
        } catch (NullPointerException e) {
            Log.warn("Token's Emp was not able to be found: " + e);
        }

        return emp;
    }

    public AuthPriv getPrivilege(Context ctx) {
        AuthPriv priv = null;
        try {
            priv = AuthPriv.valueOf(authService.readTokenPrivilege(ctx.cookieStore(TOKEN_NAME)));
        } catch (IllegalArgumentException e) {
            Log.warn("Token's privilege was not able to be cast: " + e);
        } catch (NullPointerException e) {
            Log.warn("Token's privilege was not able to be found: " + e);
        }
        return priv;
    }
}

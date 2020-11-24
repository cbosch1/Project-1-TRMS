package TRMS.controllers;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    public void login(Context ctx) {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");
        Log.info("Received request to log in user: " + username);
        User user = userService.loginUser(username, password);

        if(Objects.nonNull(user)) {
            ctx.cookieStore(TOKEN_NAME, authService.createToken(user));
            ctx.redirect("employee-overview.html");
        } else {
            ctx.redirect("index.html?error=failed-login");
        }
    }

    public void checkUser(Context ctx) {
        ctx.html(Boolean.toString(authService.validateToken(ctx.cookieStore(TOKEN_NAME))));
    }

    public void getPrivilege(Context ctx) {
        ctx.html(authService.readTokenPrivilege(ctx.cookieStore(TOKEN_NAME)));
    }
}

package TRMS.javalin;

import TRMS.controllers.AuthControl;
import TRMS.controllers.UserControl;
import TRMS.daos.UserDao;
import TRMS.daos.UserDaoPostgres;
import TRMS.services.AuthService;
import TRMS.services.AuthServiceImpl;
import TRMS.services.UserService;
import TRMS.services.UserServiceImpl;
import TRMS.util.ConnectionUtil;
import io.javalin.Javalin;

public class WebDriver {

    private static ConnectionUtil connectionUtil = new ConnectionUtil();

    private static UserDao userDao = new UserDaoPostgres(connectionUtil);
    private static UserService userService = new UserServiceImpl(userDao);
    private static UserControl userControl = new UserControl(userService);

    private static AuthService authService = new AuthServiceImpl();
    private static AuthControl authControl = new AuthControl(authService, userService);

    public static void main(String[] args) {
        Javalin app = Javalin.create( config -> {
            config.addStaticFiles("/public");
        }).start(2839);
        app.get("/hello", ctx -> ctx.redirect("index.html"));
        app.post("/login", ctx -> authControl.login(ctx));
        app.get("/login", ctx -> authControl.checkUser(ctx));
        app.post("/priv", ctx -> authControl.getPrivilege(ctx));
        app.post("/create", ctx -> userControl.createUser(ctx));
    }
}

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
import TRMS.webControllers.EmployeeWebControl;
import io.javalin.Javalin;
import io.javalin.http.JavalinServlet;

public class WebDriver {

    private static ConnectionUtil connectionUtil = new ConnectionUtil();

    private static UserDao userDao = new UserDaoPostgres(connectionUtil);
    private static UserService userService = new UserServiceImpl(userDao);
    private static UserControl userControl = new UserControl(userService);

    private static AuthService authService = new AuthServiceImpl();
    private static AuthControl authControl = new AuthControl(authService, userService);

    private static EmployeeWebControl eWebControl = new EmployeeWebControl(authControl);

    private static final String EMPLOYEE_URL = "employee";
    private static final String MANAGER_URL = "manager";

    public static void main(String[] args) {
        Javalin app = Javalin.create( config -> config.addStaticFiles("/public")).start(2839);

        //Open access endpoints
        app.get("", ctx -> ctx.redirect("index.html"));
        app.get("/first-time-user", ctx -> ctx.redirect("first-time-user.html"));
        app.get("/forgot-password", ctx -> ctx.redirect("forgot-password.html"));
        app.get("/forgot-username", ctx -> ctx.redirect("forgot-username.html"));
        app.post("/forgot-password", ctx -> userControl.forgotPassword(ctx));
        app.post("/forgot-username", ctx -> userControl.forgotUsername(ctx));

        //Authorized only endpoints
        app.get(EMPLOYEE_URL, ctx -> ctx.redirect("employee-login.html"));
        app.post(EMPLOYEE_URL, ctx -> { if(authControl.login(ctx)) { eWebControl.getOverview(ctx); } 
                                        else ctx.redirect("index.html?error=index.html?error=failed-login");});
        app.get(MANAGER_URL, ctx -> ctx.redirect("manager-login.html"));
        app.get(EMPLOYEE_URL+"/portal", ctx -> eWebControl.getOverview(ctx));
        app.get(MANAGER_URL+"/portal", ctx -> ctx.redirect("hidden/Manager/manager-overview.html"));

        //Admin only endpoints
        app.post("/login", ctx -> authControl.login(ctx));
        app.get("/login", ctx -> authControl.checkUser(ctx));
        app.post("/priv", ctx -> authControl.getPrivilege(ctx));
        app.post("/create", ctx -> userControl.createUser(ctx));
    }
}

package TRMS;

import TRMS.controllers.AttachmentControl;
import TRMS.controllers.AuthControl;
import TRMS.controllers.EmployeeControl;
import TRMS.controllers.InfoRequestControl;
import TRMS.controllers.ReimburseRequestControl;
import TRMS.controllers.UserControl;
import TRMS.daos.AttachmentDao;
import TRMS.daos.AttachmentDaoPostgres;
import TRMS.daos.EmployeeDao;
import TRMS.daos.EmployeeDaoPostgres;
import TRMS.daos.InfoRequestDao;
import TRMS.daos.InfoRequestDaoPostgres;
import TRMS.daos.ReimburseDaoPostgres;
import TRMS.daos.ReimburseRequestDao;
import TRMS.daos.UserDao;
import TRMS.daos.UserDaoPostgres;
import TRMS.services.AttachmentService;
import TRMS.services.AttachmentServiceImpl;
import TRMS.services.AuthService;
import TRMS.services.AuthServiceImpl;
import TRMS.services.EmployeeService;
import TRMS.services.EmployeeServiceImpl;
import TRMS.services.InfoRequestService;
import TRMS.services.InfoRequestServiceImpl;
import TRMS.services.ReimburseRequestService;
import TRMS.services.ReimburseServiceImpl;
import TRMS.services.UserService;
import TRMS.services.UserServiceImpl;
import TRMS.util.ConnectionUtil;
import TRMS.webControllers.EmployeeWebControl;
import TRMS.webControllers.ManagerWebControl;
import io.javalin.Javalin;

public class WebDriver {

    private static ConnectionUtil connectionUtil = new ConnectionUtil();

    private static UserDao userDao = new UserDaoPostgres(connectionUtil);
    private static AttachmentDao attachDao = new AttachmentDaoPostgres(connectionUtil);
    private static EmployeeDao empDao = new EmployeeDaoPostgres(connectionUtil);
    private static InfoRequestDao infoDao = new InfoRequestDaoPostgres(connectionUtil);
    private static ReimburseRequestDao reimburseDao = new ReimburseDaoPostgres(connectionUtil);

    private static UserService userService = new UserServiceImpl(userDao);
    private static AttachmentService attachService = new AttachmentServiceImpl(attachDao);
    private static EmployeeService empService = new EmployeeServiceImpl(empDao);
    private static InfoRequestService infoService = new InfoRequestServiceImpl(infoDao);
    private static ReimburseRequestService reimburseService = new ReimburseServiceImpl(reimburseDao);

    private static AuthService authService = new AuthServiceImpl();
    private static AuthControl authControl = new AuthControl(authService, userService, empService);

    private static UserControl userControl = new UserControl(userService, authControl);
    private static EmployeeControl empControl = new EmployeeControl(empService, authControl);
    private static AttachmentControl attachControl = new AttachmentControl(attachService, authControl);
    private static InfoRequestControl infoControl = new InfoRequestControl(infoService, authControl, attachService);
    private static ReimburseRequestControl reimburseControl = new ReimburseRequestControl(reimburseService, authControl, attachService);

    private static EmployeeWebControl eWebControl = new EmployeeWebControl(authControl);
    private static ManagerWebControl mWebControl = new ManagerWebControl(authControl);

    private static final String EMPLOYEE_URL = "employee";
    private static final String MANAGER_URL = "manager";

    public static void main(String[] args) {
        Javalin app = Javalin.create( config -> config.addStaticFiles("/public")).start(2839);

        //Open access endpoints
        app.get("", ctx -> ctx.redirect("index.html"));
        app.get("index", ctx -> ctx.redirect("index.html"));
        app.get("home", ctx -> ctx.redirect("index.html"));
        app.get("first-time-user", ctx -> ctx.redirect("first-time-user.html"));
        app.get("forgot-password", ctx -> ctx.redirect("forgot-password.html"));
        app.get("forgot-username", ctx -> ctx.redirect("forgot-username.html"));
        app.post("forgot-password", ctx -> userControl.forgotPassword(ctx));
        app.post("forgot-username", ctx -> userControl.forgotUsername(ctx));
        app.get("logout", ctx -> {authControl.logout(ctx); ctx.redirect("index.html");});

        //Authorized only endpoints - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

        //User endpoints
        app.get("download-attachment/:id", ctx -> attachControl.downloadAttachment(ctx));

        //Employee only endpoints
        app.get(EMPLOYEE_URL, ctx -> { if(authControl.checkUser(ctx)) { eWebControl.getOverview(ctx); } 
                                        else ctx.redirect("employee-login.html");});
        app.post(EMPLOYEE_URL, ctx -> { if(authControl.login(ctx)) { eWebControl.getOverview(ctx); } 
                                        else ctx.redirect("employee-login.html");});
        app.get(EMPLOYEE_URL+"/new-reimbursement", ctx -> eWebControl.getNewReimbursement(ctx));
        app.post(EMPLOYEE_URL+"/new-reimbursement", ctx -> reimburseControl.createRequest(ctx));
        app.get(EMPLOYEE_URL+"/view-reimbursement/:id", ctx -> eWebControl.getViewReimbursement(ctx));
        app.get(EMPLOYEE_URL+"/view-info/:id", ctx -> eWebControl.getViewInfoRequest(ctx));
        app.post(EMPLOYEE_URL+"/cancel-reimbursement/:id", ctx -> reimburseControl.cancelRequest(ctx));
                
            //Javascript endpoints
            app.post("/my-reimbursements", ctx -> { reimburseControl.readAllRequestsFor(ctx);});
            app.post(EMPLOYEE_URL+"/view-reimbursement/:id", ctx -> { reimburseControl.readRequest(ctx);});
            app.post(EMPLOYEE_URL+"/view-reimbursement/:id/attachments", ctx -> { attachControl.readRelatedReferences(ctx);});
            app.post(EMPLOYEE_URL+"/view-reimbursement/:id/infos", ctx -> { infoControl.readAllInfoFor(ctx);});    
            app.post(EMPLOYEE_URL+"/view-info/:id", ctx -> { infoControl.readInfoRequest(ctx);});
            app.post(EMPLOYEE_URL+"/view-info/:id/response", ctx -> { infoControl.createInfoResponse(ctx);});

        //Manager only endpoints
        app.get(MANAGER_URL, ctx -> { if(authControl.checkUser(ctx)) { mWebControl.getOverview(ctx); }
                                        else ctx.redirect("manager-login.html");});
        app.post(MANAGER_URL, ctx -> { if(authControl.login(ctx)) { mWebControl.getOverview(ctx); } 
                                        else ctx.redirect("manager-login.html");});
        app.get(MANAGER_URL+"/portal", ctx -> ctx.redirect("hidden/Manager/manager-overview.html"));
        app.get(MANAGER_URL+"/view-reimbursement/:id", ctx -> mWebControl.getViewReimbursement(ctx));
        app.get(MANAGER_URL+"/view-reimbursement/:id/information", ctx -> { mWebControl.getRequestInformation(ctx);});    
        app.get(MANAGER_URL+"/view-info/:id", ctx -> mWebControl.getViewInfoRequest(ctx));

            //Javascript endpoints
            app.get(MANAGER_URL+"/requests", ctx -> { reimburseControl.readManagedRequests(ctx); });
            app.post(MANAGER_URL+"/view-reimbursement/:id", ctx -> { reimburseControl.readManagedRequest(ctx);});
            app.put(MANAGER_URL+"/view-reimbursement/:id/:approval", ctx -> reimburseControl.reviewReimbursement(ctx));
            app.post(MANAGER_URL+"/view-reimbursement/:id/attachments", ctx -> { attachControl.readRelatedReferences(ctx);});
            app.post(MANAGER_URL+"/view-reimbursement/:id/information", ctx -> { infoControl.createInfoRequest(ctx);});
            app.post(MANAGER_URL+"/view-reimbursement/:id/infos", ctx -> { infoControl.readAllInfoForManager(ctx);});    
            app.post(MANAGER_URL+"/view-info/:id", ctx -> { infoControl.readInfoRequest(ctx);});
            app.post(MANAGER_URL+"/myinfo", ctx -> { userControl.readUser(ctx);});

        //Admin only endpoints
        app.post("admin/login", ctx -> authControl.login(ctx));
        app.get("admin/login", ctx -> authControl.checkUser(ctx));
        app.post("admin/priv", ctx -> authControl.getPrivilege(ctx));
        app.post("admin/user", ctx -> userControl.createUser(ctx));
        app.post("admin/employee", ctx -> empControl.createEmployee(ctx));
    }
}

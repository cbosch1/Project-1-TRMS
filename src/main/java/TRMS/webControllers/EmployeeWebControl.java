package TRMS.webControllers;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.controllers.AuthControl;
import TRMS.enums.AuthPriv;
import io.javalin.http.Context;

public class EmployeeWebControl {

    private static Logger Log = LogManager.getLogger("Control");

    private static final String HIDDEN_PATH = "src/main/resources/hidden/Employee/";

    private static AuthControl auth;

    public EmployeeWebControl(AuthControl auth){
        super();
        this.auth = auth;
    }
    
    public void getOverview(Context ctx){
        if (!auth.checkUser(ctx)){
            ctx.redirect("index.html?error=failed-login-verification");
            Log.warn("Access denied due to login verification");

        } else if (!auth.getPrivilege(ctx).equals(AuthPriv.EMPLOYEE)) {
            ctx.redirect("index.html?error=failed-login-privilege");
            Log.warn("Access denied due to login privilege");
        
        } else {
            servePage(ctx, "employee-overview.html");
        }
    }

    public void getNewReimbursement(Context ctx){
        if (!auth.checkUser(ctx)){
            ctx.redirect("index.html?error=failed-login-verification");
            Log.warn("Access denied due to login verification");

        } else if (!auth.getPrivilege(ctx).equals(AuthPriv.EMPLOYEE)) {
            ctx.redirect("index.html?error=failed-login-privilege");
            Log.warn("Access denied due to login privilege");
        
        } else {
            servePage(ctx, "new-reimbursement.html");
        }
    }

    public void getViewReimbursement(Context ctx){
        if (!auth.checkUser(ctx)){
            ctx.redirect("index.html?error=failed-login-verification");
            Log.warn("Access denied due to login verification");

        } else if (!auth.getPrivilege(ctx).equals(AuthPriv.EMPLOYEE)) {
            ctx.redirect("index.html?error=failed-login-privilege");
            Log.warn("Access denied due to login privilege");
        
        } else {
            servePage(ctx, "view-reimbursement.html");
        }
    }

    private void servePage(Context ctx, String path){
        try {
            Path fullPath = Paths.get(HIDDEN_PATH + path);
            Scanner sc = new Scanner(fullPath);
            StringBuilder sBuilder = new StringBuilder("");
            while (sc.hasNextLine()){
                sBuilder.append(sc.nextLine());
            }
            ctx.html(sBuilder.toString());
            sc.close();
            Log.info("Access approved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

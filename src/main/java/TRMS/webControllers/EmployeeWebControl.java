package TRMS.webControllers;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.controllers.AuthControl;
import TRMS.enums.AuthPriv;
import io.javalin.http.Context;

public class EmployeeWebControl {

    private static Logger Log = LogManager.getLogger("Control");

    private static final String HIDDEN_PATH = "src/main/resources/hidden/Employee/";

    private static AuthControl auth;

    public EmployeeWebControl(AuthControl authC){
        super();
        auth = authC;
    }
    
    public void getOverview(Context ctx){
        if (!auth.checkUser(ctx)){
            ctx.redirect("employee-login.html");
            Log.warn("Access denied due to login verification");

        } else if (!auth.getPrivilege(ctx).equals(AuthPriv.EMPLOYEE)) {
            ctx.redirect("employee-login.html");
            Log.warn("Access denied due to login privilege");
        
        } else {
            servePage(ctx, "employee-overview.html");
        }
    }

    public void getNewReimbursement(Context ctx){
        if (!auth.checkUser(ctx)){
            ctx.redirect("employee-login.html");
            Log.warn("Access denied due to login verification");

        } else if (!auth.getPrivilege(ctx).equals(AuthPriv.EMPLOYEE)) {
            ctx.redirect("employee-login.html");
            Log.warn("Access denied due to login privilege");
        
        } else {
            servePage(ctx, "new-reimbursement.html");
        }
    }

    public void postNewReimbursement(Context ctx){
        if (!auth.checkUser(ctx)){
            ctx.redirect("employee-login.html");
            Log.warn("Access denied due to login verification");

        } else if (!auth.getPrivilege(ctx).equals(AuthPriv.EMPLOYEE)) {
            ctx.redirect("employee-login.html");
            Log.warn("Access denied due to login privilege");
        
        } else {
            //TODO: Add implementation for adding a reimbursement request
        }
    }

    public void getViewReimbursement(Context ctx){
        if (!auth.checkUser(ctx)){
            ctx.redirect("employee-login.html");
            Log.warn("Access denied due to login verification");

        } else if (!auth.getPrivilege(ctx).equals(AuthPriv.EMPLOYEE)) {
            ctx.redirect("employee-login.html");
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

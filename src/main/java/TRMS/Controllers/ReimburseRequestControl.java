package TRMS.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.enums.AppStage;
import TRMS.enums.AppStatus;
import TRMS.enums.AuthPriv;
import TRMS.enums.EventType;
import TRMS.pojos.ReimburseRequest;
import TRMS.services.AttachmentService;
import TRMS.services.ReimburseRequestService;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;

/**
 * ReimburseRequestControl implementation, dependency is an object that
 * implements the ReimburseRequestService interface. The controller will take in
 * a Javalin Context object at each method, pull the information necessary to
 * process the request, convert that information into what the service expects,
 * and pass it on to the equivalent service object. The controller will also
 * take what ever returns from the Service, and supply it back to the ctx
 * properly.
 */
public class ReimburseRequestControl {

    private static Logger Log = LogManager.getLogger("Control");

    private ReimburseRequestService service;
    private AttachmentService attachService;
    private AuthControl auth;

    public ReimburseRequestControl(ReimburseRequestService service, AuthControl auth, AttachmentService attachService) {
        super();
        this.service = service;
        this.attachService = attachService;
        this.auth = auth;
    }

    /**
     * For creating an reimbursement request within the system. Parses form
     * parameters out of the context object and passes them to the proper Service
     * method. Then this method will apply the correct status code to the ctx.
     * 
     * @param ctx A context object that has at least the following formParams:
     *            <ul>
     *            <li>employeeId</li>
     *            <li>location</li>
     *            <li>cost</li>
     *            <li>type</li>
     *            <li>description</li>
     *            <li>justification</li>
     *            <li>projected - optional</li>
     *            <li>urgent - optional</li>
     *            <li>status - optional</li>
     *            <li>stage - optional</li>
     *            <li>dateTime - optional</li>
     *            </ul>
     */
    public void createRequest(Context ctx) {
        try {
            int employeeId = auth.getEmp(ctx);
            String location = ctx.formParam("event-location");
            Double cost = Double.parseDouble(ctx.formParam("event-cost"));
            EventType type = EventType.valueOf(ctx.formParam("event-type"));
            String description = ctx.formParam("description");
            String justification = ctx.formParam("justification");
            String grading = ctx.formParam("grading");
            boolean urgent = Boolean.parseBoolean(ctx.formParam("urgency"));
            AppStatus status = AppStatus.PENDING;
            AppStage stage = AppStage.SUPERVISOR;
            LocalDateTime dateTime = LocalDateTime.of(LocalDate.parse(ctx.formParam("event-date")),
            LocalTime.parse(ctx.formParam("event-time")));

            Double projected;

            switch (type) {
                case UNI_COURSE:
                    projected = (cost * 0.8);
                    break;

                case SEMINAR:
                    projected = (cost * 0.6);
                    break;

                case CERT_PREP_CLASS:
                    projected = (cost * 0.75);
                    break;

                case CERTIFICATION:
                    projected = (cost);
                    break;

                case TECHNICAL_TRAINING:
                    projected = (cost * 0.9);
                    break;

                default:
                    projected = (cost * 0.3);
                    break;
            }

            boolean supervisor = Boolean.parseBoolean(ctx.formParam("Supervisor"));
            boolean deptHead = Boolean.parseBoolean(ctx.formParam("Dept_Head"));

            if (supervisor) {
                stage = AppStage.DEPT_HEAD;
                if (deptHead) {
                    stage = AppStage.BENCO;
                }
            }
            
            if (dateTime.compareTo(LocalDateTime.now().plusDays(7)) <= 0){
                urgent = true;
            }

            int returnId = service.createRequest(employeeId, location, cost, type, description, justification, 
                                                grading, projected, urgent, status, stage, dateTime);
            
            ctx.uploadedFiles("files").forEach(f -> {
                try {
                attachService.createAttachment(returnId, f.getFilename(), f.getContent().readAllBytes());
                }  catch(IOException e) {
                    Log.warn("Exception thrown while creating info request: " + e);
                    ctx.status(500);
                }
            });
            
            ctx.status(200);
            Log.info("Successfully inserted reimbursement request, id returned: " + returnId);
            ctx.redirect("../employee");

        } catch (NumberFormatException e){
            Log.warn("NumberFormatException thrown while creating reimbursement request: " + e);
            ctx.status(500);
            ctx.redirect("employee-login.html");

        } catch (Exception e) {
            Log.warn("Exception thrown while creating reimbursement request: " + e);
            ctx.status(500);
            ctx.redirect("employee-login.html");
        }
    }

    /**
     * For reading the reimbursement request with the given requestId.
     * Parses form parameters out of the context object and passes them
     * to the proper Service method. The ctx will have values set to the 
     * returned reimbursement request object. Then this method will apply 
     * the correct status code to the ctx.
     * 
     * @param ctx A context object that has at least the following formParams:
     * <ul><li>requestId</li></ul>
     */
    public void readRequest(Context ctx){
        int requestId = -1;

        if(auth.checkUser(ctx)) {
            try {
                requestId = Integer.parseInt(ctx.pathParam("id"));
                ReimburseRequest request = service.readRequest(requestId);

                if (request.getEmployeeId() == auth.getEmp(ctx)){
                    ctx.json(request);
                    ctx.status(200);
                    Log.info("Successfully read reimbursement request");
                } else {
                    ctx.status(401);
                    Log.info("Unauthorized access attempted to view reimbursement request");
                }

            } catch (NumberFormatException e){
                Log.warn("NumberFormatException thrown while reading reimbursement request: " + e);
                ctx.status(500);

            } catch (Exception e) {
                Log.warn("Exception thrown while reading reimbursement request: " + e);
                ctx.status(500);
            }
        }
    }

    public void readManagedRequest(Context ctx){
        int requestId = -1;

        if(auth.checkUser(ctx)) {
            try {
                requestId = Integer.parseInt(ctx.pathParam("id"));
                ReimburseRequest request = service.readRequest(requestId);
                AuthPriv userPriv = auth.getPrivilege(ctx);

                if (userPriv == AuthPriv.BENCO ||
                    userPriv == AuthPriv.DEPT_HEAD ||
                    userPriv == AuthPriv.SUPERVISOR){

                    ctx.json(request);
                    ctx.status(200);
                    Log.info("Successfully read reimbursement request");
                } else {
                    ctx.status(401);
                    Log.info("Unauthorized access attempted to view reimbursement request");
                }

            } catch (NumberFormatException e){
                Log.warn("NumberFormatException thrown while reading reimbursement request: " + e);
                ctx.status(500);

            } catch (Exception e) {
                Log.warn("Exception thrown while reading reimbursement request: " + e);
                ctx.status(500);
            }
        }
    }

    /**
     * For reading all reimbursement requests that were sent in by the employee with
     * the given id. Parses form parameters out of the context object and passes them
     * to the proper Service method. The ctx will have values set to the 
     * returned information request objects. Then this method will apply
     * the correct status code to the ctx.
     * 
     * @param ctx A context object that has at least the following formParams:
     * <ul><li>empId</li></ul>
     */
    public void readAllRequestsFor(Context ctx){
        int employeeId = -1;

        if(auth.checkUser(ctx)) {
            try {
                employeeId = auth.getEmp(ctx);
                ctx.json(service.readAllRequestsFor(employeeId));

                ctx.status(200);
                Log.info("Successfully read all reimbursement requests for employee: " + employeeId);
            
            } catch (Exception e) {
                Log.warn("Exception thrown while reading all reimbursement requests for employee: " + employeeId + e);
                ctx.status(500);
            }
        }
    }

    public void readManagedRequests(Context ctx){
        int managerId = -1;

        if(auth.checkUser(ctx)) {
            try {
                managerId = auth.getEmp(ctx);
                    if(auth.getPrivilege(ctx).equals(AuthPriv.BENCO)){
                        ctx.json(service.readBencoRequests(managerId));
                    } else {
                        ctx.json(service.readManagedRequests(managerId));
                    }

                ctx.status(200);
                Log.info("Successfully read all reimbursement requests for manager: " + managerId);
            
            } catch (Exception e) {
                Log.warn("Exception thrown while reading all reimbursement requests for manager: " + managerId + e);
                ctx.status(500);
            }
        }
    }

    /**
     * For reading all reimbursement requests in the TRMS. The ctx will have values
     * set to the returned reimbursement request objects. Then this method will 
     * apply the correct status code to the ctx.
     */
    public void readAllRequests(Context ctx){
        try {
            ctx.json(service.readAllRequests());

            ctx.status(200);
            Log.info("Successfully read all reimbursement requests");

        } catch (Exception e) {
            Log.warn("Exception thrown while reading all reimbursement requests: " + e);
            ctx.html("Exception thrown: " + e);
            ctx.status(500);
        }
    }

    /**
     * For updating an already existing reimbursement request within the system.
     * Parses form parameters out of the context object and passes them
     * to the proper Service method. Then this method will apply the correct 
     * status code to the ctx
     * 
     * @param ctx A context object that has at least the following formParams:
     * <ul><li>requestId</li>
     * <li>employeeId</li>
     * <li>location</li>
     * <li>cost</li>
     * <li>type</li>
     * <li>description</li>
     * <li>justification</li>
     * <li>projected - optional</li>
     * <li>urgent - optional</li>
     * <li>status - optional</li>
     * <li>stage - optional</li>
     * <li>dateTime - optional</li></ul>
     */
    public void updateRequest(Context ctx){
        try {
            int requestId = Integer.parseInt(ctx.formParam("requestId"));
            int employeeId = Integer.parseInt(ctx.formParam("employeeId"));
            String location = ctx.formParam("location");
            Double cost = Double.parseDouble(ctx.formParam("cost"));
            EventType type = EventType.valueOf(ctx.formParam("type"));
            String description = ctx.formParam("description");
            String justification = ctx.formParam("justification");
            String grading = ctx.formParam("grading");
            Double projected = Double.parseDouble(ctx.formParam("projected"));
            boolean urgent = Boolean.parseBoolean(ctx.formParam("urgent"));
            AppStatus status = AppStatus.valueOf(ctx.formParam("status"));
            AppStage stage = AppStage.valueOf(ctx.formParam("stage"));
            LocalDateTime dateTime = LocalDateTime.parse(ctx.formParam("dateTime"));
            
            if (service.updateRequest(requestId, employeeId, location, cost, type, description, justification, 
                                        grading, projected, urgent, status, stage, dateTime)){
                Log.info("Reimbursement request successfully updated");
                ctx.status(200);
            } else {
                Log.warn("Service returned false while updating reimbursement request");
                ctx.status(500);
            }

        } catch (NumberFormatException e){
            Log.warn("NumberFormatException thrown while updating reimbursement request: " + e);
            ctx.html("NumberFormatException thrown: " + e);
            ctx.status(500);

        } catch (Exception e) {
            Log.warn("Exception thrown while updating reimbursement request: " + e);
            ctx.html("Exception thrown: " + e);
            ctx.status(500);
        }
    }

    public void reviewReimbursement(Context ctx){
        try {
            int requestId = Integer.parseInt(ctx.pathParam("id"));
            boolean approved = Boolean.parseBoolean(ctx.pathParam("approval"));
            
            ReimburseRequest request = service.readRequest(requestId);

            if (approved) {
                switch (request.getStage()){

                    case END:
                        break;

                    case EVENT:
                        request.setStage(AppStage.END);
                        break;

                    case BENCO:
                        request.setStage(AppStage.EVENT);
                        break;

                    case DEPT_HEAD:
                        request.setStage(AppStage.BENCO);
                        break;
                        
                    case SUPERVISOR:
                        request.setStage(AppStage.DEPT_HEAD);
                        break;

                    case UPLOAD:
                        request.setStage(AppStage.SUPERVISOR);
                        break;
                }

            } else {
                request.setStatus(AppStatus.DENIED);
            }
            
            if (service.updateRequest(request)){
                Log.info("Reimbursement request successfully updated");
                ctx.status(200);
            } else {
                Log.warn("Service returned false while updating reimbursement request");
                ctx.status(500);
            }

        } catch (NumberFormatException e){
            Log.warn("NumberFormatException thrown while updating reimbursement request: " + e);
            ctx.html("NumberFormatException thrown: " + e);
            ctx.status(500);

        } catch (Exception e) {
            Log.warn("Exception thrown while updating reimbursement request: " + e);
            ctx.html("Exception thrown: " + e);
            ctx.status(500);
        }

    }

    /**
     * For deleting an already existing reimbursement request within the system.
     * Parses form parameters out of the context object and passes them
     * to the proper Service method. Then this method will apply the correct 
     * status code to the ctx
     * 
     * @param ctx A context object that has at least the following formParams:
     * <ul><li>requestId</li></ul>
     */
    public void deleteRequest(Context ctx){
        int requestId = -1;
        if (auth.checkUser(ctx)) {
            try {
                requestId = Integer.parseInt(ctx.pathParam("id"));

                if (service.deleteRequest(requestId)){
                    Log.info("Reimbursement Request successfully deleted");
                    ctx.status(200);
                    ctx.redirect("../../employee");
                } else {
                    Log.warn("Service returned false while deleting reimbursement request");
                    ctx.status(500);
                }
            } catch (NumberFormatException e){
                Log.warn("NumberFormatException thrown while deleting reimbursement request: " + e);
                ctx.status(400);

            } catch (Exception e) {
                Log.warn("Exception thrown while deleting reimbursement request: " + e);
                ctx.status(500);
            }
        }
    }

    public void cancelRequest(Context ctx){
        int requestId = -1;

        if(auth.checkUser(ctx)) {
            try {
                requestId = Integer.parseInt(ctx.pathParam("id"));
                ReimburseRequest request = service.readRequest(requestId);
                if (request.getStatus() == AppStatus.PENDING){
                    request.setStatus(AppStatus.CANCELLED);
                    
                    if (service.updateRequest(request)){
                        Log.info("Reimbursement request successfully updated");
                        ctx.status(200);
                        ctx.redirect("../../employee");
                    } else {
                        Log.warn("Service returned false while updating reimbursement request");
                        ctx.status(500);
                    }
                } else {
                    Log.info("Cannot change status of event's not pending");
                    ctx.redirect("../employee");
                }
                
            } catch (NumberFormatException e){
                Log.warn("NumberFormatException thrown while reading reimbursement request: " + e);
                ctx.status(500);

            } catch (Exception e) {
                Log.warn("Exception thrown while reading reimbursement request: " + e);
                ctx.status(500);
            }
        }
    }

    public void finalGradeRequest(Context ctx){
        int requestId = -1;

        if(auth.checkUser(ctx)) {
            try {
                requestId = Integer.parseInt(ctx.pathParam("id"));
                ReimburseRequest request = service.readRequest(requestId);

                String grade = ctx.formParam("grade");
                boolean isPresentation = Boolean.parseBoolean(ctx.formParam("isPresentation"));

                if (Objects.nonNull(ctx.uploadedFile("file"))) {
                    int attachNum = attachService.createAttachment(requestId, ctx.uploadedFile("file").getFilename(), 
                                                                    ctx.uploadedFile("file").getContent().readAllBytes());
                    Log.info("Attachment number: " + attachNum);
                } else {
                    Log.info("No attachment included");
                }
                
                if (isPresentation){
                    request.setGrade("Presentation");
                } else {
                    request.setGrade(grade);
                }

                if (service.updateRequestGrade(request)){
                    Log.info("Reimbursement request successfully updated");
                    ctx.status(200);
                    ctx.redirect("../../../employee");
                } else {
                    Log.warn("Service returned false while updating reimbursement request");
                    ctx.status(500);
                }
                
            } catch (NumberFormatException e){
                Log.warn("NumberFormatException thrown while reading reimbursement request: " + e);
                ctx.status(500);

            } catch (Exception e) {
                Log.warn("Exception thrown while reading reimbursement request: " + e);
                ctx.status(500);
            }
        }
    }
}

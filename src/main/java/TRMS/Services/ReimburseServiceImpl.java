package TRMS.services;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.daos.ReimburseRequestDao;
import TRMS.enums.AppStage;
import TRMS.enums.AppStatus;
import TRMS.enums.EventType;
import TRMS.pojos.ReimburseRequest;

/**
 * ReimburseRequestService implementation, dependency is an object that implements
 * the ReimburseRequestDao interface. Most methods inside this object will take in
 * various values and convert them to the form the Dao requires, then call it's 
 * equivalent Dao method, handle any exceptions and return what the Dao returns.
 */
public class ReimburseServiceImpl implements ReimburseRequestService {

    private static Logger Log = LogManager.getLogger("Service");

    private ReimburseRequestDao reimburseDao;

    public ReimburseServiceImpl(ReimburseRequestDao reimburseDao) {
        super();
        this.reimburseDao = reimburseDao;
    }

    /**
     * Converts inputs into a reimbursement request object and then sends
     * that object into the Dao. Returns the generated id.
     * @param employeeId the id of the employee making the request for reimbursement
     * @param location the location where the event will take place
     * @param cost the cost of event participation the employee will have to pay (if the reimbursement is not approved)
     * @param type the type of event (UNI_COURSE, SEMINAR, CERT_PREP_CLASS, CERTIFICATION, TECHNICAL_TRAINING, OTHER)
     * @param description a description of the event and what it entails
     * @param justification the work related justification for this event
     * @param projected the projected reimbursement amount
     * @param urgent if the request is urgent or not
     * @param status what status the request current has (PENDING, APPROVED, DENIED, CANCELLED)
     * @param stage what stage the request is currently in (UPLOAD, SUPERVISOR, DEPT_HEAD, BENCO, EVENT, END)
     * @param dateTime a DateTime object representing when the request was made
     * @return The generated id for this object
     */
    @Override
    public int createRequest(int employeeId, String location, double cost, EventType type, String description, 
                            String justification, double projected, boolean urgent, AppStatus status,
                            AppStage stage, LocalDateTime dateTime) {
        Log.info("Responding to create reimbursement request...");
        int result = -1;

        ReimburseRequest request = new ReimburseRequest(0, employeeId, location, cost, type, description, 
                                                        justification, projected, urgent, status, stage, dateTime);
        try {
            result = reimburseDao.createRequest(request);
            Log.info("Successfully created reimbursement request");
        } catch (SQLException e) {
            Log.warn("Error thrown in dao call: ", e);
        }
        return result;
    }

    /**
     * Calls the Dao to remove the reimbursement request with the given id
     * @param requestId the id of the request to delete
     * @return true if the operation was successful
     */
    @Override
    public boolean deleteRequest(int requestId) {
        Log.info("Responding to delete reimbursement request...");
        boolean result = false;

        try {
            result = reimburseDao.deleteRequest(requestId);
            if(result) {
                Log.info("Successfully deleted reimbursement request");
            } else {
                Log.warn("Reimbursement request was not successfully deleted");
            }
        } catch (SQLException e) {
            Log.warn("Error thrown in dao call: ", e);
        }
        return result;
    }

    /**
     * Calls the Dao to return a list of all the Reimbursement Requests
     * @return A list of reimbursement requests
     */
    @Override
    public List<ReimburseRequest> readAllRequests() {
        Log.info("Responding to read all reimbursement requests...");
        List<ReimburseRequest> result = null;

        try {
            result = reimburseDao.readAllRequests();
            Log.info("Successfully retrieved reimbursement requests");

        } catch (SQLException e) {
            Log.warn("Error thrown in dao call: ", e);
        }
        return result;
    }

    /**
     * Calls the Dao to return a list of all reimbursement requests
     * that have been made by an employee
     * @param employeeId the id of the employee who's requests should be returned
     * @return A list of reimbursement requests
     */
    @Override
    public List<ReimburseRequest> readAllRequestsFor(int employeeId) {
        Log.info("Responding to read all reimbursement requests for employee " +employeeId+ "...");
        List<ReimburseRequest> result = null;

        try {
            result = reimburseDao.readAllRequestsFor(employeeId);
            Log.info("Successfully retrieved reimbursement requests");

        } catch (SQLException e) {
            Log.warn("Error thrown in dao call: ", e);
        }
        return result;
    }

    /**
     * Calls the Dao to read a specific reimbursement request
     * that corresponds to the given id.
     * @param requestId the id of the reimbursement request to return
     * @return the reimbursement request with given id
     */
    @Override
    public ReimburseRequest readRequest(int requestId) {
        Log.info("Responding to read reimbursement request...");
        ReimburseRequest result = null;

        try {
            result = reimburseDao.readRequest(requestId);
            Log.info("Successfully retrieved reimbursement request");

        } catch (SQLException e) {
            Log.warn("Error thrown in dao call: ", e);
        }
        return result;
    }

    /**
     * Calls the Dao to update a reimbursement request with given id.
     * The input fields should be given the updated information.
     * @param employeeId the id of the employee making the request for reimbursement
     * @param location the location where the event will take place
     * @param cost the cost of event participation the employee will have to pay (if the reimbursement is not approved)
     * @param type the type of event (UNI_COURSE, SEMINAR, CERT_PREP_CLASS, CERTIFICATION, TECHNICAL_TRAINING, OTHER)
     * @param description a description of the event and what it entails
     * @param justification the work related justification for this event
     * @param projected the projected reimbursement amount
     * @param urgent if the request is urgent or not
     * @param status what status the request current has (PENDING, APPROVED, DENIED, CANCELLED)
     * @param stage what stage the request is currently in (UPLOAD, SUPERVISOR, DEPT_HEAD, BENCO, EVENT, END)
     * @param dateTime a DateTime object representing when the request was made
     * @return true if the operation was successful
     */
    @Override
    public boolean updateRequest(int requestId, int employeeId, String location, double cost, EventType type,
            String description, String justification, double projected, boolean urgent, AppStatus status,
            AppStage stage, LocalDateTime dateTime) {

                Log.info("Responding to update reimbursement request...");
                boolean result = false;
        
                ReimburseRequest request = new ReimburseRequest(requestId, employeeId, location, cost, type, description, 
                                                                justification, projected, urgent, status, stage, dateTime);
                try {
                    result = reimburseDao.updateRequest(request);
        
                    if(result) {
                        Log.info("Successfully updated reimbursement request");
                    } else {
                        Log.info("Something went wrong, reimbursement request not updated properly");
                    }
                } catch (SQLException e) {
                    Log.warn("Error thrown in dao call: ", e);
                }
                return result;
    }
}

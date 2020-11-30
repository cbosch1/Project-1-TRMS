package TRMS.services;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.daos.InfoRequestDao;
import TRMS.enums.AuthPriv;
import TRMS.pojos.InfoRequest;

/**
 * InfoRequestService implementation, dependency is an object that implements
 * the InfoRequestDao interface. Most methods inside this object will take in
 * various values and convert them to the form the Dao requires, then call it's 
 * equivalent Dao method, handle any exceptions and return what the Dao returns.
 */
public class InfoRequestServiceImpl implements InfoRequestService {

    private static Logger Log = LogManager.getLogger("Service");

    private InfoRequestDao infoDao;

    public InfoRequestServiceImpl(InfoRequestDao infoRequestDao){
        super();
        this.infoDao = infoRequestDao;
    }

    /**
     * Converts inputs into an infoRequest object and then sends object
     * to the Dao. Returns the generated info id.
     * @param relatedId the Id of the reimbursement request that this info request is related to
     * @param destinationId the Id of the employee that is to receive this request
     * @param urgent a boolean representing if this request should be marked as urgent or not
     * @param description the main body of this request
     * @param dateTime a DateTime object representing when the request was made
     * @return infoId the generated Id for this object
     */
    @Override
    public int createInfoRequest(int relatedId, int destinationId, int senderId,  String sender, boolean urgent, String description,
            LocalDateTime dateTime) {
        Log.info("Responding to create information request...");
        int result = -1;

        InfoRequest info = new InfoRequest(0, relatedId, destinationId, senderId, sender, urgent, description, dateTime);

        try {
            result = infoDao.createInfoRequest(info);
            Log.info("Successfully created information request");
        } catch (SQLException e) {
            Log.warn("Error thrown in dao call: ", e);
        }
        return result;
    }

    @Override
    public int createInfoRequest(int relatedId, AuthPriv dest, int senderId,  String sender, boolean urgent, String description,
            LocalDateTime dateTime) {
        Log.info("Responding to create information request...");
        int result = -1;

        try {
            result = infoDao.createInfoRequest(relatedId, dest, senderId, sender, urgent, description, dateTime);
            Log.info("Successfully created information request");
        } catch (SQLException e) {
            Log.warn("Error thrown in dao call: ", e);
        }
        return result;
    }

    /**
     * Calls the Dao to remove the information request with the given id
     * @param infoId the id of the info request to delete
     * @return true if the operation was successful
     */
    @Override
    public boolean deleteInfoRequest(int infoId) {
        Log.info("Responding to delete information request...");
        boolean result = false;

        try {
            result = infoDao.deleteInfoRequest(infoId);
            if(result) {
                Log.info("Successfully deleted information request");
            } else {
                Log.warn("Information request was not successfully deleted");
            }
        } catch (SQLException e) {
            Log.warn("Error thrown in dao call: ", e);
        }
        return result;
    }

    /**
     * Calls the Dao to return a list of all information requests
     * that have been made of an employee
     * @param employeeId the id of the employee who's requests should be returned
     * @return A list of Info Requests
     */
    @Override
    public List<InfoRequest> readAllInfoFor(int employeeId) {
        Log.info("Responding to read all information requests for employee " +employeeId+ "...");
        List<InfoRequest> result = null;

        try {
            result = infoDao.readAllInfoFor(employeeId);
            Log.info("Successfully retrieved information requests");

        } catch (SQLException e) {
            Log.warn("Error thrown in dao call: ", e);
        }
        return result;
    }

    /**
     * Calls the Dao to return a list of all the Information Requests
     * @return A list of Info Requests
     */
    @Override
    public List<InfoRequest> readAllInfoReq() {
        Log.info("Responding to read all information requests...");
        List<InfoRequest> result = null;

        try {
            result = infoDao.readAllInfoReq();
            Log.info("Successfully retrieved information requests");

        } catch (SQLException e) {
            Log.warn("Error thrown in dao call: ", e);
        }
        return result;
    }

    /**
     * Calls the Dao to read the information request with the given id
     * @param infoId the id of the request to read
     * @return The Info request with the given id
     */
    @Override
    public InfoRequest readInfoRequest(int infoId) {
        Log.info("Responding to read information request...");
        InfoRequest result = null;

        try {
            result = infoDao.readInfoRequest(infoId);
            Log.info("Successfully retrieved information request");

        } catch (SQLException e) {
            Log.warn("Error thrown in dao call: ", e);
        }
        return result;
    }

    /**
     * Calls the Dao to update an information request with given id.
     * The input fields should be given the updated information.
     * @param infoId the id of the request to update
     * @param relatedId the Id of the reimbursement request that this info request is related to
     * @param destinationId the Id of the employee that is to receive this request
     * @param urgent a boolean representing if this request should be marked as urgent or not
     * @param description the main body of this request
     * @param dateTime a DateTime object representing when the request was made
     * @return true if the operation was successful
     */
    @Override
    public boolean updateInfoRequest(int infoId, int relatedId, int destinationId,  int senderId, String sender, boolean urgent, String description,
            LocalDateTime dateTime) {
        Log.info("Responding to update information request...");
        boolean result = false;

        InfoRequest info = new InfoRequest(infoId, relatedId, destinationId, senderId, sender, urgent, description, dateTime);

        try {
            result = infoDao.updateInfoRequest(info);

            if(result) {
                Log.info("Successfully updated information request");
            } else {
                Log.warn("Something went wrong, information request not updated properly");
            }
        } catch (SQLException e) {
            Log.warn("Error thrown in dao call: ", e);
        }
        return result;
    }
    
}

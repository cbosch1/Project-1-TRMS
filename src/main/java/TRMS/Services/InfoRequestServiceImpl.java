package TRMS.services;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.daos.InfoRequestDao;
import TRMS.pojos.InfoRequest;

/**
 * InfoRequestService implementation, dependency is an object that implements
 * the InfoRequestDao interface. Most methods inside this object will take in
 * various values and convert them to the form the Dao requires, then call it's 
 * equivalent Dao method, handle any exceptions and return what the Dao returns.
 */
public class InfoRequestServiceImpl implements InfoRequestService {

    private static Logger Log = LogManager.getLogger("Service");

    private InfoRequestDao infoRequestDao;

    public InfoRequestServiceImpl(InfoRequestDao infoRequestDao){
        super();
        this.infoRequestDao = infoRequestDao;
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
    public int createInfoRequest(int relatedId, int destinationId, boolean urgent, String description,
            LocalDateTime dateTime) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * Calls the Dao to remove the information request with the given id
     * @param infoId the id of the info request to delete
     * @return true if the operation was successful
     */
    @Override
    public boolean deleteInfoRequest(int infoId) {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * Calls the Dao to return a list of all information requests
     * that have been made of an employee
     * @param employeeId the id of the employee who's requests should be returned
     * @return A list of Info Requests
     */
    @Override
    public List<InfoRequest> readAllInfoFor(int employeeId) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Calls the Dao to return a list of all the Information Requests
     * @return A list of Info Requests
     */
    @Override
    public List<InfoRequest> readAllInfoReq() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Calls the Dao to read the information request with the given id
     * @param infoId the id of the request to read
     * @return The Info request with the given id
     */
    @Override
    public InfoRequest readInfoRequest(int infoId) {
        // TODO Auto-generated method stub
        return null;
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
    public boolean updateInfoRequest(int infoId, int relatedId, int destinationId, boolean urgent, String description,
            LocalDateTime dateTime) {
        // TODO Auto-generated method stub
        return false;
    }
    
}

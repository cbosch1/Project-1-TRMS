package TRMS.daos;

import java.sql.SQLException;
import java.util.List;

import TRMS.pojos.InfoRequest;

/**
 * InfoRequestDao interface, the goal of an implementation of this 
 * interface will be to take in information request related values and perform
 * CRUD operations with a method of storage the implementation specifies.
 */
public interface InfoRequestDao {
    
    /**
     * Insert infoRequest object into storage.
     * @param info the information request to be inserted
     * @return int - The id of the inserted information request
     */
    public int createInfoRequest(InfoRequest info) throws SQLException;

    /**
     * Returns a single information request that has provided info Id
     * @param infoId of the infoRequest desired
     * @return an info request object
     */
    public InfoRequest readInfoRequest(int infoId) throws SQLException;

    /**
     * Returns all information requests that relate to provided employee id
     * @param employeeId of who's info requests we want
     * @return List of those info request objects
     */
    public List<InfoRequest> readAllInfoFor(int employeeId) throws SQLException;

    /**
     * Returns all information requests stored
     * @return List of InfoRequest objects
     */
    public List<InfoRequest> readAllInfoReq() throws SQLException;

    /**
     * Updates infoRequest data inside storage, pulls the object's id number
     * and will update all information except the id number.
     * @param info the information request with updated fields
     * @return true if request is successful
     */
    public boolean updateInfoRequest(InfoRequest info) throws SQLException;

    /**
     * Remove infoRequest object from storage that has provided id
     * @param infoId that relates to the info request to delete
     * @return true if request was successful
     */
    public boolean deleteInfoRequest(int infoId) throws SQLException;
}

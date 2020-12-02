package TRMS.daos;

import java.sql.SQLException;
import java.util.List;

import TRMS.pojos.ReimburseRequest;

/**
 * ReimburseRequestDao interface, the goal of an implementation of this 
 * interface will be to take in reimbursement request related values and perform
 * CRUD operations with a method of storage the implementation specifies.
 */
public interface ReimburseRequestDao {
    
    /**
     * Insert Reimbursement Request object into storage.
     * @param request The reimbursement request to be inserted
     * @return int - The id of the inserted reimbursement request
     */
    public int createRequest(ReimburseRequest request) throws SQLException;

    /**
     * Returns a specific reimbursement request that has provided request id
     * @param requestId of the request to be returned
     * @return List of reimbursement request objects
     */
    public ReimburseRequest readRequest(int requestId) throws SQLException;

    /**
     * Returns all reimbursement request that were made by the employee with
     * the provided id.
     * @param employeeId of employee whom requests are desired
     * @return List of reimbursement request objects
     */
    public List<ReimburseRequest> readAllRequestsFor(int employeeId) throws SQLException;

    public List<ReimburseRequest> readManagedRequests(int managerId) throws SQLException;

    public List<ReimburseRequest> readBencoRequests(int managerId) throws SQLException;

    /**
     * Returns all reimbursement requests from storage in a list
     * @return List of reimbursement request objects
     */
    public List<ReimburseRequest> readAllRequests() throws SQLException;

    /**
     * Updates reimbursement data inside storage, pulls the object's id number
     * and will update all information except the id number.
     * @param request The reimbursement request to be updated
     * @return true if request was successful
     */
    public boolean updateRequest(ReimburseRequest request) throws SQLException;

    public boolean updateRequestGrade(ReimburseRequest request) throws SQLException;

    /**
     * Remove Reimbursement Request object from storage that has provided id
     * @param requestId The id that relates to the reimbursement request to delete.
     * @return true if request was successful
     */
    public boolean deleteRequest(int requestId) throws SQLException;
}

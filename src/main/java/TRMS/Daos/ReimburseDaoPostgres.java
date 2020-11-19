package TRMS.daos;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.pojos.ReimburseRequest;
import TRMS.util.ConnectionUtil;

/**
 * Reimbursement Request Dao that connects to the reimbursement and reimburse_status tables
 * in the postgres database. The dao consolidates the data from each table into a single object.
 */
public class ReimburseDaoPostgres implements ReimburseRequestDao {

    private PreparedStatement stmt;

    private static Logger Log = LogManager.getLogger("Dao");

    private ConnectionUtil connUtil;

    public ReimburseDaoPostgres(ConnectionUtil connectionUtil){
        super();
        connUtil = connectionUtil;
    }
    
    /**
     * Insert Reimbursement Request object into the database. This object's id number
     * will be ignored as the database will generate it's own.
     * @param reimbursmentRequest to be inserted
     * @return int - The generated id of the request
     */
    @Override
    public int createRequest(ReimburseRequest request) throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * Remove Reimbursement Request object from the database that has provided id
     * @param requestId that relates to the reimbursement request to delete.
     * @return true if request was successful
     */
    @Override
    public boolean deleteRequest(int requestId) {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * Returns all reimbursement requests from the database in a list
     * @return List of reimbursement request objects
     */
    @Override
    public List<ReimburseRequest> readAllRequests() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Returns all reimbursement request that were made by the employee with
     * the provided id.
     * @param employeeId of employee whom requests are desired
     * @return List of reimbursement request objects
     */
    @Override
    public List<ReimburseRequest> readAllRequestsFor(int employeeId) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Returns a specific reimbursement request that has provided request id
     * @param requestId of the request to be returned
     * @return List of reimbursement request objects
     */
    @Override
    public ReimburseRequest readRequest(int requestId) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Updates reimbursement data inside the database, pulls the object's id number
     * and will update all information except the id number.
     * @param request to be updated
     * @return true if request was successful
     */
    @Override
    public boolean updateRequest(ReimburseRequest request) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }
    
}

package TRMS.daos;

import java.sql.SQLException;
import java.util.List;

import TRMS.pojos.ReimburseRequest;

public class ReimburseDaoPostgres implements ReimburseRequestDao {

    @Override
    public void createRequest(ReimburseRequest request) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteRequest(int requestId) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<ReimburseRequest> readAllRequests() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ReimburseRequest> readAllRequestsFor(int employeeId) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ReimburseRequest readRequest(int requestId) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateRequest(ReimburseRequest request) throws SQLException {
        // TODO Auto-generated method stub

    }
    
}

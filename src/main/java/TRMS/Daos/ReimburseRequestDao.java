package TRMS.daos;

import java.sql.SQLException;
import java.util.List;

import TRMS.pojos.ReimburseRequest;

public interface ReimburseRequestDao {
    
    public void createRequest(ReimburseRequest request) throws SQLException;

    public ReimburseRequest readRequest(int requestId) throws SQLException;

    public List<ReimburseRequest> readAllRequestsFor(int employeeId) throws SQLException;

    public List<ReimburseRequest> readAllRequests() throws SQLException;

    public void updateRequest(ReimburseRequest request) throws SQLException;

    public void deleteRequest(int requestId);
}

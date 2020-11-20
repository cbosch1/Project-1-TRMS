package TRMS.daos;

import java.sql.SQLException;
import java.util.List;

import TRMS.pojos.ReimburseRequest;

public interface ReimburseRequestDao {
    
    public int createRequest(ReimburseRequest request) throws SQLException;

    public ReimburseRequest readRequest(int requestId) throws SQLException;

    public List<ReimburseRequest> readAllRequestsFor(int employeeId) throws SQLException;

    public List<ReimburseRequest> readAllRequests() throws SQLException;

    public boolean updateRequest(ReimburseRequest request) throws SQLException;

    public boolean deleteRequest(int requestId) throws SQLException;
}

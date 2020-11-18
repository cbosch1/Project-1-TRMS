package TRMS.daos;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.pojos.ReimburseRequest;
import TRMS.util.ConnectionUtil;

public class ReimburseDaoPostgres implements ReimburseRequestDao {

    private PreparedStatement stmt;

    private static Logger Log = LogManager.getLogger("Dao");

    private ConnectionUtil connUtil;

    public ReimburseDaoPostgres(ConnectionUtil connectionUtil){
        super();
        connUtil = connectionUtil;
    }
    
    @Override
    public int createRequest(ReimburseRequest request) throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean deleteRequest(int requestId) {
        // TODO Auto-generated method stub
        return false;
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
    public boolean updateRequest(ReimburseRequest request) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }
    
}

package TRMS.services;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.daos.ReimburseRequestDao;
import TRMS.enums.AppStage;
import TRMS.enums.AppStatus;
import TRMS.enums.EventType;
import TRMS.pojos.ReimburseRequest;

public class ReimburseServiceImpl implements ReimburseRequestService {

    private static Logger Log = LogManager.getLogger("Service");

    private ReimburseRequestDao reimburseDao;

    public ReimburseServiceImpl(ReimburseRequestDao reimburseDao) {
        super();
        this.reimburseDao = reimburseDao;
    }

    @Override
    public boolean createRequest(int requestId, int employeeId, String location, double cost, EventType type,
            String description, String justification, double projected, boolean urgent, AppStatus status,
            AppStage stage, LocalDateTime dateTime) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteRequest(int requestId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<ReimburseRequest> readAllRequests() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ReimburseRequest> readAllRequestsFor(int employeeId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ReimburseRequest readRequest(int requestId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean updateRequest(int requestId, int employeeId, String location, double cost, EventType type,
            String description, String justification, double projected, boolean urgent, AppStatus status,
            AppStage stage, LocalDateTime dateTime) {
        // TODO Auto-generated method stub
        return false;
    }
    
}

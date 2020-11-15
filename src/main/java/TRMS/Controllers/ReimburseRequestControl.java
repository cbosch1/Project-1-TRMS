package TRMS.Controllers;

import java.time.LocalDateTime;
import java.util.List;

import TRMS.Pojos.ReimburseRequest;
import TRMS.Pojos.Enums.*;

public interface ReimburseRequestControl {

    public boolean createRequest(int requestId, int employeeId, String location, double cost, EventType type, 
                                String description, String justification, double projected, boolean urgent,
                                AppStatus status, AppStage stage, LocalDateTime dateTime);

    public ReimburseRequest readRequest(int requestId);

    public List<ReimburseRequest> readAllRequestsFor(int employeeId);

    public List<ReimburseRequest> readAllRequests();

    public boolean updateRequest(int requestId, int employeeId, String location, double cost, EventType type, 
                                String description, String justification, double projected, boolean urgent,
                                AppStatus status, AppStage stage, LocalDateTime dateTime);

    public boolean deleteRequest(int requestId);
}

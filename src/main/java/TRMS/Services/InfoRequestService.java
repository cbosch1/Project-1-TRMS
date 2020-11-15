package TRMS.Services;

import java.time.LocalDateTime;
import java.util.List;

import TRMS.Pojos.InfoRequest;

public interface InfoRequestService {

    public boolean createInfoRequest(int infoId, int relatedId, int destinationId, 
                                    boolean urgent, String description, LocalDateTime dateTime);

    public InfoRequest readInfoRequest(int infoId);

    public List<InfoRequest> readAllInfoFor(int employeeId);

    public List<InfoRequest> readAllInfoReq();

    public boolean updateInfoRequest(int infoId, int relatedId, int destinationId, 
                                    boolean urgent, String description, LocalDateTime dateTime);

    public boolean deleteInfoRequest(int infoId);
}

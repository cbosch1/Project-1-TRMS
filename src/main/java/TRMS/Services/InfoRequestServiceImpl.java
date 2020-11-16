package TRMS.services;

import java.time.LocalDateTime;
import java.util.List;

import TRMS.pojos.InfoRequest;

public class InfoRequestServiceImpl implements InfoRequestService {

    @Override
    public boolean createInfoRequest(int infoId, int relatedId, int destinationId, boolean urgent, String description,
            LocalDateTime dateTime) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteInfoRequest(int infoId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<InfoRequest> readAllInfoFor(int employeeId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<InfoRequest> readAllInfoReq() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public InfoRequest readInfoRequest(int infoId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean updateInfoRequest(int infoId, int relatedId, int destinationId, boolean urgent, String description,
            LocalDateTime dateTime) {
        // TODO Auto-generated method stub
        return false;
    }
    
}

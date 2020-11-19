package TRMS.services;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.daos.InfoRequestDao;
import TRMS.pojos.InfoRequest;

public class InfoRequestServiceImpl implements InfoRequestService {

    private static Logger Log = LogManager.getLogger("Service");

    private InfoRequestDao infoRequestDao;

    public InfoRequestServiceImpl(InfoRequestDao infoRequestDao){
        super();
        this.infoRequestDao = infoRequestDao;
    }

    @Override
    public int createInfoRequest(int relatedId, int destinationId, boolean urgent, String description,
            LocalDateTime dateTime) {
        // TODO Auto-generated method stub
        return 0;
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

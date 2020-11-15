package TRMS.daos;

import java.sql.SQLException;
import java.util.List;

import TRMS.pojos.InfoRequest;

public interface InfoRequestDao {
    
    public void createInfoRequest(InfoRequest info) throws SQLException;

    public InfoRequest readInfoRequest(int infoId) throws SQLException;

    public List<InfoRequest> readAllInfoFor(int employeeId) throws SQLException;

    public List<InfoRequest> readAllInfoReq() throws SQLException;

    public void updateInfoRequest(InfoRequest info) throws SQLException;

    public void deleteInfoRequest(int infoId) throws SQLException;
}

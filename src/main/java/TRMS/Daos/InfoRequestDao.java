package TRMS.daos;

import java.sql.SQLException;
import java.util.List;

import TRMS.pojos.InfoRequest;

public interface InfoRequestDao {
    
    public int createInfoRequest(InfoRequest info) throws SQLException;

    public InfoRequest readInfoRequest(int infoId) throws SQLException;

    public List<InfoRequest> readAllInfoFor(int employeeId) throws SQLException;

    public List<InfoRequest> readAllInfoReq() throws SQLException;

    public boolean updateInfoRequest(InfoRequest info) throws SQLException;

    public boolean deleteInfoRequest(int infoId) throws SQLException;
}

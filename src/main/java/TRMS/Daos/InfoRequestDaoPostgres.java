package TRMS.daos;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.pojos.InfoRequest;
import TRMS.util.ConnectionUtil;

public class InfoRequestDaoPostgres implements InfoRequestDao {

    private PreparedStatement stmt;

    private static Logger Log = LogManager.getLogger("Dao");

    private ConnectionUtil connUtil;

    public InfoRequestDaoPostgres(ConnectionUtil connectionUtil){
        super();
        connUtil = connectionUtil;
    }

    @Override
    public int createInfoRequest(InfoRequest info) throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean deleteInfoRequest(int infoId) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<InfoRequest> readAllInfoFor(int employeeId) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<InfoRequest> readAllInfoReq() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public InfoRequest readInfoRequest(int infoId) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean updateInfoRequest(InfoRequest info) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }
    
}

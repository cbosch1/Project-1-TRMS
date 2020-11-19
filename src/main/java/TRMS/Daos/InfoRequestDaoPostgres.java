package TRMS.daos;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.pojos.InfoRequest;
import TRMS.util.ConnectionUtil;

/**
 * Info Request Dao that connects to the info_request table in the postgres database.
 */
public class InfoRequestDaoPostgres implements InfoRequestDao {

    private PreparedStatement stmt;

    private static Logger Log = LogManager.getLogger("Dao");

    private ConnectionUtil connUtil;

    public InfoRequestDaoPostgres(ConnectionUtil connectionUtil){
        super();
        connUtil = connectionUtil;
    }

    /**
     * Insert infoRequest object into the database This object's id number will 
     * be ignored as the database will generate it's own.
     * @param infoRequest object to be inserted
     * @return int - The generated id
     */
    @Override
    public int createInfoRequest(InfoRequest info) throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * Remove infoRequest object from the database that has provided id
     * @param infoId that relates to the info request to delete
     * @return true if request was successful
     */
    @Override
    public boolean deleteInfoRequest(int infoId) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * Returns all information requests that relate to provided employee id
     * @param employeeId of who's info requests we want
     * @return List of those info request objects
     */
    @Override
    public List<InfoRequest> readAllInfoFor(int employeeId) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Returns all information requests stored in the database
     * @return List of InfoRequest objects
     */
    @Override
    public List<InfoRequest> readAllInfoReq() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Returns a single information request that has provided info Id
     * @param infoId of the infoRequest desired
     * @return an info request object
     */
    @Override
    public InfoRequest readInfoRequest(int infoId) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Updates infoRequest data inside the database, pulls the object's id number
     * and will update all information except the id number.
     * @param infoRequest object with updated fields
     * @return true if request is successful
     */
    @Override
    public boolean updateInfoRequest(InfoRequest info) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }
    
}

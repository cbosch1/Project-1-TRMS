package TRMS.daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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
        int result; 

        try(Connection conn = connUtil.createConnection()) {
            Log.info("Received request to insert info request related to reimbursement: " + info.getRelatedId());

            String sql = "INSERT INTO info_request VALUES (Default,?,?,?,?,?,?) RETURNING info_id;";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, info.getRelatedId());
            stmt.setInt(2, info.getDestinationId());
            stmt.setBoolean(3, info.getUrgent());
            stmt.setString(4, info.getDescription());
            stmt.setDate(5, Date.valueOf(LocalDate.from(info.getDateTime())));
            stmt.setTime(6, Time.valueOf(LocalTime.from(info.getDateTime())));

            ResultSet rs = stmt.executeQuery();
            rs.next();
            result = rs.getInt(1);
            Log.info("Request completed, generated info request id: " + result);

        } catch (SQLException e){
            Log.warn("SQLException thrown in create info request related to reimbursement: " + info.getRelatedId(), e);
            throw e;
        }

        return result;
    }

    /**
     * Remove infoRequest object from the database that has provided id
     * @param infoId that relates to the info request to delete
     * @return true if request was successful
     */
    @Override
    public boolean deleteInfoRequest(int infoId) throws SQLException {
        boolean result = false; 

        try(Connection conn = connUtil.createConnection()) {
            Log.info("Received request to delete info request with id: " + infoId);

            String sql = "DELETE FROM info_request WHERE info_id = ?;";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, infoId);

            result = (1 == stmt.executeUpdate());

            if (result){
                Log.info("Request completed, info request with id: " + infoId + " was deleted.");
            } else
                Log.warn("Request to delete info request with id: " + infoId +" was NOT completed");

        } catch (SQLException e){
            Log.warn("SQLException thrown in info request delete for id: " + infoId, e);
            throw e;
        }

        return result;
    }

    /**
     * Returns all information requests that relate to provided employee id
     * @param employeeId of who's info requests we want
     * @return List of those info request objects
     */
    @Override
    public List<InfoRequest> readAllInfoFor(int employeeId) throws SQLException {
        List<InfoRequest> result = new ArrayList<>(); 

        try(Connection conn = connUtil.createConnection()) {
            Log.info("Received request to retrieve info requests related to employee with id: " + employeeId);

            String sql = "SELECT * FROM info_request WHERE destination_id = ?;";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, employeeId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                InfoRequest i = new InfoRequest(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getBoolean(4), 
                rs.getString(5), LocalDateTime.of(rs.getDate(6).toLocalDate(), rs.getTime(7).toLocalTime()));
                result.add(i);
            }

            Log.info("Request completed, retrieved info requests, count: " + result.size());

        } catch (SQLException e){
            Log.warn("SQLException thrown in read info requests related to employee with id: " + employeeId, e);
            throw e;
        }

        return result;
    }

    /**
     * Returns all information requests stored in the database
     * @return List of InfoRequest objects
     */
    @Override
    public List<InfoRequest> readAllInfoReq() throws SQLException {
        List<InfoRequest> result = new ArrayList<>(); 

        try(Connection conn = connUtil.createConnection()) {
            Log.info("Received request to retrieve all info requests");

            String sql = "SELECT * FROM info_request;";
            stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                InfoRequest i = new InfoRequest(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getBoolean(4), 
                rs.getString(5), LocalDateTime.of(rs.getDate(6).toLocalDate(), rs.getTime(7).toLocalTime()));
                result.add(i);
            }

            Log.info("Request completed, retrieved info requests, count: " + result.size());

        } catch (SQLException e){
            Log.warn("SQLException thrown in read all info requests", e);
            throw e;
        }

        return result;
    }

    /**
     * Returns a single information request that has provided info Id
     * @param infoId of the infoRequest desired
     * @return an info request object
     */
    @Override
    public InfoRequest readInfoRequest(int infoId) throws SQLException {
        InfoRequest result = null; 

        try(Connection conn = connUtil.createConnection()) {
            Log.info("Received request to retrieve all info requests");

            String sql = "SELECT * FROM info_request WHERE info_id = ?;";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, infoId);
            ResultSet rs = stmt.executeQuery();

            rs.next();
            result = new InfoRequest(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getBoolean(4), rs.getString(5), 
                                        LocalDateTime.of(rs.getDate(6).toLocalDate(), rs.getTime(7).toLocalTime()));

            Log.info("Request completed, retrieved info request with id: " + infoId);

        } catch (SQLException e){
            Log.warn("SQLException thrown in read info request with id: " + infoId, e);
            throw e;
        }

        return result;
    }

    /**
     * Updates infoRequest data inside the database, pulls the object's id number
     * and will update all information except the id number.
     * @param infoRequest object with updated fields
     * @return true if request is successful
     */
    @Override
    public boolean updateInfoRequest(InfoRequest info) throws SQLException {
        boolean result = false; 

        try(Connection conn = connUtil.createConnection()) {
            Log.info("Received request to update info request with id: " + info.getInfoId());

            String sql = "UPDATE info_request SET urgent = ?, description = ?, request_date = ?,"
                                                +" request_time = ? WHERE info_id = ?;";
            stmt = conn.prepareStatement(sql);

            stmt.setBoolean(1, info.getUrgent());
            stmt.setString(2, info.getDescription());
            stmt.setDate(3, Date.valueOf(LocalDate.from(info.getDateTime())));
            stmt.setTime(4, Time.valueOf(LocalTime.from(info.getDateTime())));
            stmt.setInt(5, info.getInfoId());

            result = (1 == stmt.executeUpdate());

            if (result){
                Log.info("Request completed, info request with id: " + info.getInfoId() + " was updated.");
            } else
                Log.warn("Request to update info request with id: " + info.getInfoId() +" was NOT completed");

        } catch (SQLException e){
            Log.warn("SQLException thrown in updated related to info request: " + info.getInfoId(), e);
            throw e;
        }

        return result;
    }
    
}

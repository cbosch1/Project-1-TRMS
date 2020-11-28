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

import TRMS.enums.*;
import TRMS.pojos.ReimburseRequest;
import TRMS.util.ConnectionUtil;

/**
 * Reimbursement Request Dao that connects to the reimbursement and reimburse_status tables
 * in the postgres database. The dao consolidates the data from each table into a single object.
 */
public class ReimburseDaoPostgres implements ReimburseRequestDao {

    private PreparedStatement stmt;
    private PreparedStatement stmt2;

    private static Logger Log = LogManager.getLogger("Dao");

    private ConnectionUtil connUtil;

    public ReimburseDaoPostgres(ConnectionUtil connectionUtil){
        super();
        connUtil = connectionUtil;
    }
    
    /**
     * Insert Reimbursement Request object into the database. This object's id number
     * will be ignored as the database will generate it's own.
     * @param reimbursmentRequest to be inserted
     * @return int - The generated id of the request
     */
    @Override
    public int createRequest(ReimburseRequest request) throws SQLException {
        int result; 

        try(Connection conn = connUtil.createConnection()) {
            Log.info("Received request to insert reimbursement for employee with id: " + request.getEmployeeId());

            String sql = "SELECT insert_reimbursement(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            stmt = conn.prepareCall(sql);

            stmt.setInt(1, request.getEmployeeId());
            stmt.setString(2, request.getLocation());
            stmt.setDouble(3, request.getCost());
            stmt.setString(4, request.getType().toString());
            stmt.setString(5, request.getDescription());
            stmt.setString(6, request.getJustification());
            stmt.setString(7, request.getGrading());
            stmt.setDouble(8, request.getProjected());
            stmt.setBoolean(9, request.isUrgent());
            stmt.setString(10, request.getStatus().toString());
            stmt.setString(11, request.getStage().toString());
            stmt.setDate(12, Date.valueOf(LocalDate.from(request.getDateTime())));
            stmt.setTime(13, Time.valueOf(LocalTime.from(request.getDateTime())));
            
            ResultSet rs = stmt.executeQuery();
            rs.next();
            result = rs.getInt(1);
            Log.info("Request completed, generated reimbursement request id: " + result);

        } catch (SQLException e){
            Log.warn("SQLException thrown in create reimbursement for employee with id: " + request.getEmployeeId(), e);
            throw e;
        }

        return result;
    }

    /**
     * Remove Reimbursement Request object from the database that has provided id
     * @param requestId that relates to the reimbursement request to delete.
     * @return true if request was successful
     */
    @Override
    public boolean deleteRequest(int requestId)  throws SQLException {
        boolean result = false; 

        try(Connection conn = connUtil.createConnection()) {
            Log.info("Received request to delete reimbursement with id: " + requestId);

            String sql = "DELETE FROM reimbursement WHERE request_id = ?;";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, requestId);

            result = (1 == stmt.executeUpdate());

            if (result){
                Log.info("Request completed, reimbursement with id: " + requestId + " was deleted.");
            } else
                Log.warn("Request to delete reimbursement with id: " + requestId +" was NOT completed");

        } catch (SQLException e){
            Log.warn("SQLException thrown in delete for reimbursement id: " + requestId, e);
            throw e;
        }

        return result;
    }

    /**
     * Returns all reimbursement requests from the database in a list
     * @return List of reimbursement request objects
     */
    @Override
    public List<ReimburseRequest> readAllRequests() throws SQLException {
        List<ReimburseRequest> result = new ArrayList<>(); 

        try(Connection conn = connUtil.createConnection()) {
            Log.info("Received request to retrieve all reimbursement requests");

            String sql = "SELECT * FROM reimbursement;";
            String sql2 = "SELECT * FROM reimburse_status;";
            stmt = conn.prepareStatement(sql);
            stmt2 = conn.prepareStatement(sql2);

            ResultSet rs = stmt.executeQuery();
            ResultSet rs2 = stmt2.executeQuery();

            while (rs.next()){
                rs2.next();
                ReimburseRequest r = new ReimburseRequest(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDouble(4),
                                    EventType.valueOf(rs.getString(5)), rs.getString(6), rs.getString(7), rs.getString(8), rs2.getDouble(2),
                                    rs2.getBoolean(3), AppStatus.valueOf(rs2.getString(4)), AppStage.valueOf(rs2.getString(5)),
                                    LocalDateTime.of(rs2.getDate(6).toLocalDate(), rs2.getTime(7).toLocalTime()));                    
                result.add(r);
            }

            Log.info("Request completed, retrieved reimbursement requests, count: " + result.size());

        } catch (SQLException e){
            Log.warn("SQLException thrown in read all reimbursement requests", e);
            throw e;
        }

        return result;
    }

    /**
     * Returns all reimbursement request that were made by the employee with
     * the provided id.
     * @param employeeId of employee whom requests are desired
     * @return List of reimbursement request objects
     */
    @Override
    public List<ReimburseRequest> readAllRequestsFor(int employeeId) throws SQLException {
        List<ReimburseRequest> result = new ArrayList<>(); 

        try(Connection conn = connUtil.createConnection()) {
            Log.info("Received request to retrieve reimbursement requests related to employee with id: " + employeeId);

            String sql = "SELECT * FROM reimbursement WHERE emp_id = ?;";
            String sql2 = "SELECT * FROM reimburse_status WHERE request_id IN "
                            + "(SELECT request_id FROM reimbursement WHERE emp_id = ?);";
            stmt = conn.prepareStatement(sql);
            stmt2 = conn.prepareStatement(sql2);

            stmt.setInt(1, employeeId);
            stmt2.setInt(1, employeeId);

            ResultSet rs = stmt.executeQuery();
            ResultSet rs2 = stmt2.executeQuery();

            while (rs.next()){
                rs2.next();
                ReimburseRequest r = new ReimburseRequest(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDouble(4),
                                    EventType.valueOf(rs.getString(5)), rs.getString(6), rs.getString(7), rs.getString(8), rs2.getDouble(2),
                                    rs2.getBoolean(3), AppStatus.valueOf(rs2.getString(4)), AppStage.valueOf(rs2.getString(5)),
                                    LocalDateTime.of(rs2.getDate(6).toLocalDate(), rs2.getTime(7).toLocalTime()));                    
                result.add(r);
            }

            Log.info("Request completed, retrieved reimbursement requests " 
                    +"related to employee with id: " +employeeId+ ", count: " + result.size());

        } catch (SQLException e){
            Log.warn("SQLException thrown in read all reimbursement requests related to employee with id: " + employeeId, e);
            throw e;
        }

        return result;
    }

    /**
     * Returns a specific reimbursement request that has provided request id
     * @param requestId of the request to be returned
     * @return List of reimbursement request objects
     */
    @Override
    public ReimburseRequest readRequest(int requestId) throws SQLException {
        ReimburseRequest result = null; 
        
        try(Connection conn = connUtil.createConnection()) {
            Log.info("Received request to retrieve reimbursement request with id: " + requestId);

            conn.setAutoCommit(false);

            String sql = "SELECT * FROM reimbursement WHERE request_id = ?;";
            String sql2 = "SELECT * FROM reimburse_status WHERE request_id = ?;";
            stmt = conn.prepareStatement(sql);
            stmt2 = conn.prepareStatement(sql2);
            stmt.setInt(1, requestId);
            stmt2.setInt(1, requestId);

            ResultSet rs = stmt.executeQuery();
            ResultSet rs2 = stmt2.executeQuery();
            conn.commit();

            while (rs.next()){
                rs2.next();
                result = new ReimburseRequest(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDouble(4),
                                    EventType.valueOf(rs.getString(5)), rs.getString(6), rs.getString(7), rs.getString(8), rs2.getDouble(2),
                                    rs2.getBoolean(3), AppStatus.valueOf(rs2.getString(4)), AppStage.valueOf(rs2.getString(5)),
                                    LocalDateTime.of(rs2.getDate(6).toLocalDate(), rs2.getTime(7).toLocalTime()));
            }

            Log.info("Request completed, retrieved reimbursement request with id: " + requestId);

        } catch (SQLException e){
            Log.warn("SQLException thrown in reimbursement request with id: " + requestId, e);
            throw e;
        }

        return result;
    }

    /**
     * Updates reimbursement data inside the database, pulls the object's id number
     * and will update all information except the id number.
     * @param request to be updated
     * @return true if request was successful
     */
    @Override
    public boolean updateRequest(ReimburseRequest request) throws SQLException {
        boolean result = false; 
        
        try(Connection conn = connUtil.createConnection()) {
            Log.info("Received request to update reimbursement request with id: " + request.getRequestId());

            conn.setAutoCommit(false);

            String sql = "UPDATE reimbursement SET ev_location = ?, ev_cost = ?, ev_type = ?::event_type, description = ?, "
                                                +"justification = ?, grading_format = ? WHERE request_id = ?;";
            String sql2 = "UPDATE reimburse_status SET projected_award = ?, urgent = ?, status = ?::app_status, "
                                                +"stage = ?::app_stage, request_date = ?, request_time = ? WHERE request_id = ?;";
            stmt = conn.prepareStatement(sql);
            stmt2 = conn.prepareStatement(sql2);

            stmt.setString(1, request.getLocation());
            stmt.setDouble(2, request.getCost());
            stmt.setString(3, request.getType().toString());
            stmt.setString(4, request.getDescription());
            stmt.setString(5, request.getJustification());
            stmt.setString(6, request.getGrading());
            stmt.setInt(7, request.getRequestId());

            stmt2.setDouble(1, request.getProjected());
            stmt2.setBoolean(2, request.isUrgent());
            stmt2.setString(3, request.getStatus().toString());
            stmt2.setString(4, request.getStage().toString());
            stmt2.setDate(5, Date.valueOf(LocalDate.from(request.getDateTime())));
            stmt2.setTime(6, Time.valueOf(LocalTime.from(request.getDateTime())));
            stmt2.setInt(7, request.getRequestId());

            result = (1 == stmt.executeUpdate() && 1 == stmt2.executeUpdate());
            conn.commit();

            if (result){
                Log.info("Request completed, reimbursement with id: " + request.getRequestId() + " was updated.");
            } else
                Log.warn("Request to update reimbursement with id: " + request.getRequestId() +" was NOT completed");

        } catch (SQLException e){
            Log.warn("SQLException thrown in updating reimbursement request with id: " + request.getRequestId(), e);
            throw e;
        }

        return result;
    }
    
}

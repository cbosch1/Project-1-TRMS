package TRMS.daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.pojos.Attachment;
import TRMS.util.ConnectionUtil;

/**
 * AttachmentDao to connect to the attachment table in the Postgres database.
 * This may be phased out with an implementation that utilizes Azure Blob Storage
 * in replacement of the database.
 */
public class AttachmentDaoPostgres implements AttachmentDao {

    private PreparedStatement stmt;

    private static Logger Log = LogManager.getLogger("Dao");

    private ConnectionUtil connUtil;

    public AttachmentDaoPostgres(ConnectionUtil connectionUtil){
        super();
        connUtil = connectionUtil;
    }

    /**
     * Insert attachment file into the database. This object's id number
     * will be ignored as the database will generate it's own.
     * @param file to be inserted
     * @return int - The generated id
     */
    @Override
    public int createAttachment(Attachment file) throws SQLException {

        int result; 

        try(Connection conn = connUtil.createConnection()) {
            Log.info("Received request to insert file for request: " + file.getRequestId());

            String sql = "INSERT INTO attachment VALUES (Default,?,?,?) RETURNING attach_id;";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, file.getRequestId());
            stmt.setString(2, file.getFileType());
            stmt.setBytes(3, file.getData());

            ResultSet rs = stmt.executeQuery();
            rs.next();
            result = rs.getInt(1);
            Log.info("Request completed, generated attachment id: " + result);

        } catch (SQLException e){
            Log.warn("SQLException thrown in create for attachment related to request: " + file.getRequestId(), e);
            throw e;
        }

        return result;
    }

    /**
     * Remove attachment file from the database.
     * @param attachmentId that relates to the attachment file to delete.
     * @return true if request was successful
     */
    @Override
    public boolean deleteAttachment(int attachId) throws SQLException {
        boolean result = false; 

        try(Connection conn = connUtil.createConnection()) {
            Log.info("Received request to delete file with id: " + attachId);

            String sql = "DELETE FROM attachment WHERE attach_id = ?;";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, attachId);

            result = (1 == stmt.executeUpdate());

            if (result){
                Log.info("Request completed, attachment with id: " + attachId + " was deleted.");
            } else
                Log.warn("Request to delete attachment with id: " + attachId +" was NOT completed");

        } catch (SQLException e){
            Log.warn("SQLException thrown in delete for id: " + attachId, e);
            throw e;
        }

        return result;
    }

    /**
     * Read attachment file out of the database with the provided attachment id.
     * @param attachmentId that relates to the attachment file to read.
     * @return attachment object
     */
    @Override
    public Attachment readAttachment(int attachId) throws SQLException {
        Attachment result = null; 

        try(Connection conn = connUtil.createConnection()) {
            Log.info("Received request to retrieve file with id: " + attachId);

            String sql = "SELECT * FROM attachment WHERE attach_id = ?;";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, attachId);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            result = new Attachment(attachId, rs.getInt(2), rs.getString(3)); 
            result.setData(rs.getBinaryStream(4).readAllBytes());

            Log.info("Request completed, retrieved attachment id: " + attachId);

        } catch (SQLException e){
            Log.warn("SQLException thrown in read for attachment with id: " + attachId, e);
            throw e;
        } catch (IOException e) {
            Log.warn("IOException throw while reading attachment file: " + attachId, e);
        }

        return result;
    }

    /**
     * Read multiple attachment files out of the database that are related to the
     * provided Reimbursement Request Id.
     * @param requestId of the reimbursement request that we want attachments from.
     * @return List of Integers that represent related the attachment files
     */
    @Override
    public List<Attachment> readRelatedReference(int requestId) throws SQLException {
        List<Attachment> result = new ArrayList<>(); 

        try(Connection conn = connUtil.createConnection()) {
            Log.info("Received request to retrieve files related to request with id: " + requestId);

            String sql = "SELECT * FROM attachment WHERE request_id = ?;";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, requestId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Attachment attach = new Attachment(rs.getInt(1), rs.getInt(2), rs.getString(3));
                result.add(attach);
            }

            Log.info("Request completed, retrieved attachments, count: " + result.size());

        } catch (SQLException e){
            Log.warn("SQLException thrown in read for files related to request with id: " + requestId, e);
            throw e;
        }

        return result;
    }

    /**
     * Updates attachment data inside the database, pulls the object's id number
     * and will update all information except the id number.
     * @param attach The attachment object with updated fields
     * @return true if request was successful
     */
    public boolean updateAttachment(Attachment attach) throws SQLException {
        boolean result = false; 

        try(Connection conn = connUtil.createConnection()) {
            Log.info("Received request to update attachment with id: " + attach.getAttachId());

            String sql = "UPDATE attachment SET request_id=?, file_type=?, file=? WHERE attach_id = ?;";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, attach.getRequestId());
            stmt.setString(2, attach.getFileType());
            stmt.setBytes(3, attach.getData());
            stmt.setInt(4, attach.getAttachId());

            result = (1 == stmt.executeUpdate());

            if (result){
                Log.info("Request completed, attachment with id: " + attach.getAttachId() + " was updated.");
            } else
                Log.warn("Request to update attachment with id: " + attach.getAttachId() +" was NOT completed");

        } catch (SQLException e){
            Log.warn("SQLException thrown in updated related to attachment: " + attach.getAttachId(), e);
            throw e;
        }

        return result;
    }
    
}

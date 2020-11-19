package TRMS.daos;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * Remove attachment file from the database.
     * @param attachmentId that relates to the attachment file to delete.
     * @return true if request was successful
     */
    @Override
    public boolean deleteAttachment(int attachId) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * Read attachment file out of the database with the provided attachment id.
     * @param attachmentId that relates to the attachment file to read.
     * @return attachment object
     */
    @Override
    public Attachment readAttachment(int attachId) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Read multiple attachment files out of the database that are related to the
     * provided Reimbursement Request Id.
     * @param requestId of the reimbursement request that we want attachments from.
     * @return List of Integers that represent related the attachment files
     */
    @Override
    public List<Integer> readRelatedReference(int requestId) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }
    
}

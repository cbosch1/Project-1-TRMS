package TRMS.daos;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.pojos.Attachment;
import TRMS.util.ConnectionUtil;

public class AttachmentDaoPostgres implements AttachmentDao {

    private PreparedStatement stmt;

    private static Logger Log = LogManager.getLogger("Dao");

    private ConnectionUtil connUtil;

    public AttachmentDaoPostgres(ConnectionUtil connectionUtil){
        super();
        connUtil = connectionUtil;
    }

    @Override
    public int createAttachment(Attachment file) throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean deleteAttachment(int attachId) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Attachment readAttachment(int attachId) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Integer> readRelatedReference(int requestId) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }
    
}

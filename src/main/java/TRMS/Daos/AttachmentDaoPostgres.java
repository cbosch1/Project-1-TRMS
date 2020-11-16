package TRMS.daos;

import java.sql.SQLException;
import java.util.List;

import TRMS.pojos.Attachment;

public class AttachmentDaoPostgres implements AttachmentDao {

    @Override
    public void createAttachment(Attachment file) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteAttachment(int attachId) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public Attachment readAttachment(int attachId) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Integer> readRelatedReferences(int requestId) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }
    
}

package TRMS.Daos;

import java.sql.SQLException;
import java.util.List;

import TRMS.Pojos.Attachment;

public interface AttachmentDao {
    
    public void createAttachment(Attachment file) throws SQLException;

    public Attachment readAttachment(int attachId) throws SQLException;

    public List<Integer> readRelatedReferences(int requestId) throws SQLException;

    public void deleteAttachment(int attachId) throws SQLException;
}

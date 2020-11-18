package TRMS.daos;

import java.sql.SQLException;
import java.util.List;

import TRMS.pojos.Attachment;

public interface AttachmentDao {
    
    public int createAttachment(Attachment file) throws SQLException;

    public Attachment readAttachment(int attachId) throws SQLException;

    public List<Integer> readRelatedReference(int requestId) throws SQLException;

    public boolean deleteAttachment(int attachId) throws SQLException;
}
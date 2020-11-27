package TRMS.daos;

import java.sql.SQLException;
import java.util.List;

import TRMS.pojos.Attachment;

/**
 * AttachmentDao interface, the goal of an implementation of this 
 * interface will be to take in attachment related values and perform
 * CRUD operations with a method of storage the implementation specifies.
 */
public interface AttachmentDao {

    /**
     * Insert attachment file into the storage.
     * @param file to be inserted
     * @return int - The id of the inserted file
     */
    public int createAttachment(Attachment file) throws SQLException;

    /**
     * Read attachment file out of storage with the provided attachment id.
     * @param attachId The id that relates to the attachment file to read.
     * @return attachment object
     */
    public Attachment readAttachment(int attachId) throws SQLException;

    /**
     * Read multiple attachment files out of storage that are related to the
     * provided Reimbursement Request Id.
     * @param requestId The id of the reimbursement request that we want attachments from.
     * @return List of Integers that represent related the attachment files
     */
    public List<Attachment> readRelatedReference(int requestId) throws SQLException;

    /**
     * Remove attachment file from storage.
     * @param attachId The id that relates to the attachment file to delete.
     * @return true if request was successful
     */
    public boolean deleteAttachment(int attachId) throws SQLException;

    /**
     * Updates attachment data inside storage, pulls the object's id number
     * and will update all information except the id number.
     * @param attach The attachment object with updated fields
     * @return true if request was successful
     */
    public boolean updateAttachment(Attachment attach) throws SQLException;
}
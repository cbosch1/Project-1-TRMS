package TRMS.services;

import java.io.InputStream;
import java.util.List;

import TRMS.pojos.Attachment;

/**
 * AttachmentService interface, the goal of most methods inside an implementation
 * of this interface will be to take in various values and convert them to the form
 * an equivalent Dao requires, then call it's equivalent Dao method, 
 * handle any exceptions, and return what the Dao returns.
 */
public interface AttachmentService {

    /**
     * Converts inputs into an attachment object and then sends object
     * to the Dao to store. Returns the generated id.
     * This version allows you to pass in an Id to the Dao, but that
     * does not guarantee the Dao will utilize given Id.
     * @param attachId this id will most likely be discarded
     * @param requestId the id of the reimbursement request to which this attachment relates
     * @param fileType what is this file for? Pre-Approval Email, Grades, Presentation Files, etc.
     * @param data the byte stream of the file to be uploaded
     * @return attachId the generated or provided Id for this object
     */
    public int createAttachment(int attachId, int requestId, String fileType, InputStream data);

    /**
     * Converts inputs into an attachment object and then sends object
     * to the Dao to store. Returns the generated id.
     * @param requestId the id of the reimbursement request to which this attachment relates
     * @param fileType what is this file for? Pre-Approval Email, Grades, Presentation Files, etc.
     * @param data the byte stream of the file to be uploaded
     * @return attachId the generated Id for this object
     */
    public int createAttachment(int requestId, String fileType, InputStream data);

    /**
     * Calls the Dao to delete the attachment file with given id
     * @param attachId the id of the attachment file to delete
     * @return true if request is successful
     */
    public Attachment readAttachment(int attachId);

    /**
     * Calls the Dao to read the attachment file with given id
     * @param attachId the id of the attachment file to read
     * @return attachment, the file with given id
     */
    public List<Attachment> readRelatedReferences(int requestId);

    /**
     * Calls the Dao to read the attachment files that relate to the 
     * reimbursement request with the given id.
     * @param requestId the id of the reimbursement request with the desired files
     * @return A list of the attachment ids of the related files.
     */
    public boolean updateAttachment(int attachId, int requestId, String fileType, InputStream data);

    /**
     * Calls the Dao to update an attachment file with the given id. The input fields
     * should be given the updated information
     * @param attachId the id of the attachment to update
     * @param requestId the id of the reimbursement request to which this attachment relates
     * @param fileType what is this file for? Pre-Approval Email, Grades, Presentation Files, etc.
     * @param data the byte stream of the file to be uploaded
     * @return true if the request was successful
     */
    public boolean deleteAttachment(int attachId);
}
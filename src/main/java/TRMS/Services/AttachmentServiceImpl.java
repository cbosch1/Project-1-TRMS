package TRMS.services;

import java.io.FileInputStream;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.daos.AttachmentDao;
import TRMS.pojos.Attachment;

/**
 * AttachmentService implementation, dependency is an object that implements
 * the AttachmentDao interface. Most methods inside this object will take in
 * various values and convert them to the form the Dao requires, then call it's 
 * equivalent Dao method, handle any exceptions and return what the Dao returns.
 */
public class AttachmentServiceImpl implements AttachmentService {

    private static Logger Log = LogManager.getLogger("Service");

    private AttachmentDao attachDao;

    public AttachmentServiceImpl(AttachmentDao attachDao){
        super();
        this.attachDao = attachDao;
    }

    /**
     * Converts inputs into an attachment object and then sends object
     * to the Dao createAttachment method. Returns the generated id.
     * This version allows you to pass in an Id to the Dao, but that
     * does not guarantee the Dao will utilize given Id.
     * @param attachId this id will most likely be discarded
     * @param requestId the id of the reimbursement request to which this attachment relates
     * @param fileType what is this file for? Pre-Approval Email, Grades, Presentation Files, etc.
     * @param data the byte stream of the file to be uploaded
     * @return attachId the generated or provided Id for this object
     */
    @Override
    public int createAttachment(int attachId, int requestId, String fileType, FileInputStream data) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * Converts inputs into an attachment object and then sends object
     * to the Dao createAttachment method. Returns the generated id.
     * @param requestId the id of the reimbursement request to which this attachment relates
     * @param fileType what is this file for? Pre-Approval Email, Grades, Presentation Files, etc.
     * @param data the byte stream of the file to be uploaded
     * @return attachId the generated Id for this object
     */
    @Override
    public int createAttachment(int requestId, String fileType, FileInputStream data) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * Calls the Dao to delete the attachment file with given id
     * @param attachId the id of the attachment file to delete
     * @return true if request is successful
     */
    @Override
    public boolean deleteAttachment(int attachId) {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * Calls the Dao to read the attachment file with given id
     * @param attachId the id of the attachment file to read
     * @return attachment, the file with given id
     */
    @Override
    public Attachment readAttachment(int attachId) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Calls the Dao to read the attachment files that relate to the 
     * reimbursement request with the given id.
     * @param requestId the id of the reimbursement request with the desired files
     * @return A list of the attachment ids of the related files.
     */
    @Override
    public List<Integer> readRelatedReferences(int requestId) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Calls the Dao to update an attachment file with the given id. The input fields
     * should be given the updated information
     * @param attachId the id of the attachment to update
     * @param requestId the id of the reimbursement request to which this attachment relates
     * @param fileType what is this file for? Pre-Approval Email, Grades, Presentation Files, etc.
     * @param data the byte stream of the file to be uploaded
     * @return true if the request was successful
     */
    @Override
    public boolean updateAttachment(int attachId, int requestId, String fileType, FileInputStream data) {
        // TODO Auto-generated method stub
        return false;
    }
    
}

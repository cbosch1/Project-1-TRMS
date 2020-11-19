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
     */
    @Override
    public int createAttachment(int attachId, int requestId, String fileType, FileInputStream data) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * Converts inputs into an attachment object and then sends object
     * to the Dao createAttachment method. Returns the generated id.
     */
    @Override
    public int createAttachment(int requestId, String fileType, FileInputStream data) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean deleteAttachment(int attachId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Attachment readAttachment(int attachId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Integer> readRelatedReferences(int requestId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean updateAttachment(int attachId, int requestId, String fileType, FileInputStream data) {
        // TODO Auto-generated method stub
        return false;
    }
    
}

package TRMS.services;

import java.io.FileInputStream;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.daos.AttachmentDao;
import TRMS.pojos.Attachment;

public class AttachmentServiceImpl implements AttachmentService {

    private static Logger Log = LogManager.getLogger("Service");

    private AttachmentDao attachDao;

    public AttachmentServiceImpl(AttachmentDao attachDao){
        super();
        this.attachDao = attachDao;
    }

    @Override
    public boolean createAttachment(int attachId, int requestId, String fileType, FileInputStream data) {
        // TODO Auto-generated method stub
        return false;
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

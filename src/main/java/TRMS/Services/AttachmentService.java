package TRMS.services;

import java.io.InputStream;
import java.util.List;

import TRMS.pojos.Attachment;

public interface AttachmentService {

    public int createAttachment(int attachId, int requestId, String fileType, InputStream data);

    public int createAttachment(int requestId, String fileType, InputStream data);

    public Attachment readAttachment(int attachId);

    public List<Integer> readRelatedReferences(int requestId);

    public boolean updateAttachment(int attachId, int requestId, String fileType, InputStream data);

    public boolean deleteAttachment(int attachId);
}
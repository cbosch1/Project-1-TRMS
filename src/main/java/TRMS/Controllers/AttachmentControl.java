package TRMS.Controllers;

import java.io.FileInputStream;
import java.util.List;

import TRMS.Pojos.Attachment;

public interface AttachmentControl {

    public boolean createAttachment(int attachId, int requestId, String fileType, FileInputStream data);

    public Attachment readAttachment(int attachId);

    public List<Integer> readRelatedReferences(int requestId);

    public boolean updateAttachment(int attachId, int requestId, String fileType, FileInputStream data);

    public boolean deleteAttachment(int attachId);
}
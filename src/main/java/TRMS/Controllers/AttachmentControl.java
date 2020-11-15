package TRMS.Controllers;

import java.io.FileInputStream;
import java.util.List;

import TRMS.Pojos.Attachment;
import io.javalin.http.Context;

public interface AttachmentControl {

    public void createAttachment(Context ctx);

    public void readAttachment(Context ctx);

    public void readRelatedReferences(Context ctx);

    public void updateAttachment(Context ctx);

    public void deleteAttachment(Context ctx);
}
package TRMS.pojos;

import java.io.FileInputStream;

public class Attachment {
    private int attachId;
    private int requestId;
    private String fileType;
    private FileInputStream data;

    public Attachment(){
        super();
    }

	public Attachment(int attachId, int requestId, String fileType) {
        super();
		this.attachId = attachId;
		this.requestId = requestId;
		this.fileType = fileType;
    }
    
    public int getAttachId() {
        return attachId;
    }

    public void setAttachId(int attachId) {
        this.attachId = attachId;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public FileInputStream getData() {
        return data;
    }

    public void setData(FileInputStream data) {
        this.data = data;
    }
}

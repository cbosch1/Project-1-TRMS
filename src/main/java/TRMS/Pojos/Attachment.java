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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + attachId;
		result = prime * result + ((fileType == null) ? 0 : fileType.hashCode());
		result = prime * result + requestId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attachment other = (Attachment) obj;
		if (attachId != other.attachId)
			return false;
		if (fileType == null) {
			if (other.fileType != null)
				return false;
		} else if (!fileType.equals(other.fileType))
			return false;
		if (requestId != other.requestId)
			return false;
		return true;
	}
}

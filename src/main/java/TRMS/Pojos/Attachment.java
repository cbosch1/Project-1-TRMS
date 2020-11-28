package TRMS.pojos;


/**
 * A java representation of a file uploaded to the system
 * with specific relation to a reimbursement request.
 */
public class Attachment {
    private int attachId;
    private int requestId;
    private String fileType;
    private byte[] data;

    public Attachment(){
        super();
    }

    /**
     * Construct attachment object with applicable parameters
     * @param attachId the unique identifier for this attachment.
     * @param requestId the id of the reimbursement request to which this attachment relates.
     * @param fileType what is this file for? Pre-Approval Email, Grades, Presentation Files, etc.
     */
	public Attachment(int attachId, int requestId, String fileType) {
        super();
		this.attachId = attachId;
		this.requestId = requestId;
		this.fileType = fileType;
    }
    
    /**
     * @return attachId the unique identifier for this attachment.
     */
    public int getAttachId() {
        return attachId;
    }

    /**
     * @param attachId the unique identifier for this attachment.
     */
    public void setAttachId(int attachId) {
        this.attachId = attachId;
    }

    /**
     * @return requestId the id of the reimbursement request to which this attachment relates.
     */
    public int getRequestId() {
        return requestId;
    }

    /**
     * @param requestId requestId the id of the reimbursement request to which this attachment relates.
     */
    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    /**
     * @return what is this file for? Pre-Approval Email, Grades, Presentation Files, etc.
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * @param fileType what is this file for? Pre-Approval Email, Grades, Presentation Files, etc.
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * @return data the byte stream of the file to be uploaded
     */
    public byte[] getData() {
        return data;
    }

    /**
     * @param data the byte stream of the file to be uploaded
     */
    public void setData(byte[] data) {
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

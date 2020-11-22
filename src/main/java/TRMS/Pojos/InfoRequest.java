package TRMS.pojos;

import java.time.LocalDateTime;

/**
 * A java representation of an information request inside
 * of the TRMS.
 */
public class InfoRequest {
    
    private int infoId;
    private int relatedId;
    private int destinationId;
    private boolean urgent;
    private String description;
    private LocalDateTime dateTime;

    public InfoRequest(){
        super();
    }

	/**
	 * Construct an information request object with the following parameters, the
	 * urgency is default set to false and dateTime is default set to LocalDateTime.now()
	 * @param infoId the unique identifier for this information request.
     * @param relatedId the id of the reimbursement request that this info request is related to.
     * @param destinationId the id of the employee that is to receive this info request.
     * @param description the main body of this request.
	 */
	public InfoRequest(int infoId, int relatedId, int destinationId, String description) {
		this.infoId = infoId;
		this.relatedId = relatedId;
		this.destinationId = destinationId;
		this.urgent = false;
		this.description = description;
		this.dateTime = LocalDateTime.now();
    }

	/**
	 * Construct an information request object with the following parameters
	 * @param infoId the unique identifier for this information request.
	 * @param relatedId the id of the reimbursement request that this info request is related to.
	 * @param destinationId the id of the employee that is to receive this info request.
	 * @param urgent a boolean representing if this request should be marked as urgent or not.
	 * @param description the main body of this request.
	 * @param dateTime a LocalDateTime object representing when the request was made.
	 */
	public InfoRequest(int infoId, int relatedId, int destinationId, boolean urgent, String description,
			LocalDateTime dateTime) {
		this.infoId = infoId;
		this.relatedId = relatedId;
		this.destinationId = destinationId;
		this.urgent = urgent;
		this.description = description;
		this.dateTime = dateTime;
    }

	/**
	 * @return the unique identifier for this information request.
	 */
    public int getInfoId() {
        return infoId;
    }

	/**
	 * @param infoId the unique identifier for this information request.
	 */
    public void setInfoId(int infoId) {
        this.infoId = infoId;
    }

	/**
	 * @return the id of the reimbursement request that this info request is related to.
	 */
    public int getRelatedId() {
        return relatedId;
    }

	/**
	 * @param relatedId the id of the reimbursement request that this info request is related to.
	 */
    public void setRelatedId(int relatedId) {
        this.relatedId = relatedId;
    }

	/**
	 * @return the id of the employee that is to receive this info request.
	 */
    public int getDestinationId() {
        return destinationId;
	}
	
	/**
	 * @param destinationId the id of the employee that is to receive this info request.
	 */
    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

	/**
	 * @return a boolean representing if this request should be marked as urgent or not.
	 */
    public boolean getUrgent() {
        return urgent;
    }

	/**
	 * @param urgent a boolean representing if this request should be marked as urgent or not.
	 */
    public void setUrgent(boolean urgent) {
        this.urgent = urgent;
    }

	/**
	 * @return the main body of this request.
	 */
    public String getDescription() {
        return description;
    }

	/**
	 * @param description the main body of this request.
	 */
    public void setDescription(String description) {
        this.description = description;
	}
	
	/**
	 * @return a LocalDateTime object representing when the request was made.
	 */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

	/**
	 * @param dateTime a LocalDateTime object representing when the request was made.
	 */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateTime == null) ? 0 : dateTime.hashCode());
		result = prime * result + destinationId;
		result = prime * result + infoId;
		result = prime * result + relatedId;
		result = prime * result + (urgent ? 1231 : 1237);
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
		InfoRequest other = (InfoRequest) obj;
		if (dateTime == null) {
			if (other.dateTime != null)
				return false;
		} else if (!dateTime.equals(other.dateTime))
			return false;
		if (destinationId != other.destinationId)
			return false;
		if (infoId != other.infoId)
			return false;
		if (relatedId != other.relatedId)
			return false;
		if (urgent != other.urgent)
			return false;
		return true;
	}
}

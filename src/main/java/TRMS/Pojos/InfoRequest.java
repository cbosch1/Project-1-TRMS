package TRMS.pojos;

import java.time.LocalDateTime;

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

	public InfoRequest(int infoId, int relatedId, int destinationId, String description) {
		this.infoId = infoId;
		this.relatedId = relatedId;
		this.destinationId = destinationId;
		this.urgent = false;
		this.description = description;
		this.dateTime = LocalDateTime.now();
    }

	public InfoRequest(int infoId, int relatedId, int destinationId, boolean urgent, String description,
			LocalDateTime dateTime) {
		this.infoId = infoId;
		this.relatedId = relatedId;
		this.destinationId = destinationId;
		this.urgent = urgent;
		this.description = description;
		this.dateTime = dateTime;
    }
    
    public int getInfoId() {
        return infoId;
    }

    public void setInfoId(int infoId) {
        this.infoId = infoId;
    }

    public int getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(int relatedId) {
        this.relatedId = relatedId;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

    public boolean getUrgent() {
        return urgent;
    }

    public void setUrgent(boolean urgent) {
        this.urgent = urgent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

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

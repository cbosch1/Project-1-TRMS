package TRMS.pojos;

import java.time.LocalDateTime;

import TRMS.enums.AppStage;
import TRMS.enums.AppStatus;
import TRMS.enums.EventType;

public class ReimburseRequest {
    
    private int requestId;
    private int employeeId;
    private String location;
    private double cost;
    private EventType type;
    private String description;
    private String justification;
    private double projected;
    private boolean urgent;
    private AppStatus status;
    private AppStage stage;
    private LocalDateTime dateTime;

    public ReimburseRequest(){
        super();
    }

    /**
     * Constructor for the Reimbursement Request object. Sets default values: projected = 00.00, 
     * urgent = false, status = AppStatus.PENDING, stage = AppStatus.UPLOAD, dateTime = LocalDateTime.now()
     * @param requestId the id of this reimbursement request.
     * @param employeeId the id of the employee making the request for reimbursement
     * @param location the location where the event will take place
     * @param cost the cost of event participation the employee will have to pay (if the reimbursement is not approved)
     * @param type the type of event, and EventType enum: (UNI_COURSE, SEMINAR, CERT_PREP_CLASS, CERTIFICATION, TECHNICAL_TRAINING, OTHER)
     * @param description a description of the event and what it entails
     * @param justification the work related justification for this event
     */
    public ReimburseRequest(int requestId, int employeeId, String location, double cost, EventType type,
            String description, String justification) {
        this.requestId = requestId;
        this.employeeId = employeeId;
        this.location = location;
        this.cost = cost;
        this.type = type;
        this.description = description;
        this.justification = justification;
        this.projected = 00.00;
        this.urgent = false;
        this.status = AppStatus.PENDING;
        this.stage = AppStage.UPLOAD;
        this.dateTime = LocalDateTime.now();
        }

    /**
     * Constructor for the Reimbursement Request object. No default values are used.   
     * @param requestId the id of this reimbursement request.
     * @param employeeId the id of the employee making the request for reimbursement
     * @param location the location where the event will take place
     * @param cost the cost of event participation the employee will have to pay (if the reimbursement is not approved)
     * @param type the type of event (UNI_COURSE, SEMINAR, CERT_PREP_CLASS, CERTIFICATION, TECHNICAL_TRAINING, OTHER)
     * @param description a description of the event and what it entails
     * @param justification the work related justification for this event
     * @param projected the projected reimbursement amount
     * @param urgent if the request is urgent or not
     * @param status AppStatus enum, what status the request current has (PENDING, APPROVED, DENIED, CANCELLED)
     * @param stage AppStage enum, what stage the request is currently in (UPLOAD, SUPERVISOR, DEPT_HEAD, BENCO, EVENT, END)
     * @param dateTime a DateTime object representing when the request was made
     */
	public ReimburseRequest(int requestId, int employeeId, String location, double cost, EventType type,
			String description, String justification, double projected, boolean urgent, AppStatus status,
			AppStage stage, LocalDateTime dateTime) {
		this.requestId = requestId;
		this.employeeId = employeeId;
		this.location = location;
		this.cost = cost;
		this.type = type;
		this.description = description;
		this.justification = justification;
		this.projected = projected;
		this.urgent = urgent;
		this.status = status;
		this.stage = stage;
		this.dateTime = dateTime;
	}
    
    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public double getProjected() {
        return projected;
    }

    public void setProjected(double projected) {
        this.projected = projected;
    }

    public boolean isUrgent() {
        return urgent;
    }

    public void setUrgent(boolean urgent) {
        this.urgent = urgent;
    }

    public AppStatus getStatus() {
        return status;
    }

    public void setStatus(AppStatus status) {
        this.status = status;
    }

    public AppStage getStage() {
        return stage;
    }

    public void setStage(AppStage stage) {
        this.stage = stage;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(cost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((dateTime == null) ? 0 : dateTime.hashCode());
		result = prime * result + employeeId;
		result = prime * result + ((justification == null) ? 0 : justification.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + requestId;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		ReimburseRequest other = (ReimburseRequest) obj;
		if (Double.doubleToLongBits(cost) != Double.doubleToLongBits(other.cost))
			return false;
		if (dateTime == null) {
			if (other.dateTime != null)
				return false;
		} else if (!dateTime.equals(other.dateTime))
			return false;
		if (employeeId != other.employeeId)
			return false;
		if (justification == null) {
			if (other.justification != null)
				return false;
		} else if (!justification.equals(other.justification))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (requestId != other.requestId)
			return false;
		if (type != other.type)
			return false;
		if (urgent != other.urgent)
			return false;
		return true;
	}
}

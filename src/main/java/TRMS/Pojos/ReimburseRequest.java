package TRMS.pojos;

import java.time.LocalDateTime;

import TRMS.enums.*;

public class ReimburseRequest {
    
    private int requestId;
    private int employeeId;
    private String location;
    private double cost;
    private EventType type;
    private String description;
    private String justificiation;
    private double projected;
    private boolean urgent;
    private AppStatus status;
    private AppStage stage;
    private LocalDateTime dateTime;

    public ReimburseRequest(){
        super();
    }

    public ReimburseRequest(int requestId, int employeeId, String location, double cost, EventType type,
            String description, String justificiation) {
        this.requestId = requestId;
        this.employeeId = employeeId;
        this.location = location;
        this.cost = cost;
        this.type = type;
        this.description = description;
        this.justificiation = justificiation;
        this.projected = 00.00;
        this.urgent = false;
        this.status = AppStatus.PENDING;
        this.stage = AppStage.UPLOAD;
        this.dateTime = LocalDateTime.now();
        }

	public ReimburseRequest(int requestId, int employeeId, String location, double cost, EventType type,
			String description, String justificiation, double projected, boolean urgent, AppStatus status,
			AppStage stage, LocalDateTime dateTime) {
		this.requestId = requestId;
		this.employeeId = employeeId;
		this.location = location;
		this.cost = cost;
		this.type = type;
		this.description = description;
		this.justificiation = justificiation;
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

    public String getJustificiation() {
        return justificiation;
    }

    public void setJustificiation(String justificiation) {
        this.justificiation = justificiation;
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
}

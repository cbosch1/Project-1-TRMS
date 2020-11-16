package TRMS.pojos;

public class Employee {
    private int employeeId;
    private String name;
    private String title;
    private int supervisor;
    private String department;
    private boolean deptHead;

    public Employee() {
        super();
    }
    
    public Employee(int employeeId, String name, String title, int supervisor, String department, boolean deptHead) {
		this.employeeId = employeeId;
		this.name = name;
		this.title = title;
		this.supervisor = supervisor;
		this.department = department;
		this.deptHead = deptHead;
	}

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(int supervisor) {
        this.supervisor = supervisor;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public boolean getDeptHead() {
        return deptHead;
    }

    public void setDeptHead(boolean deptHead) {
        this.deptHead = deptHead;
    }
}

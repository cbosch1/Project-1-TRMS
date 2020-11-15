package TRMS.Pojos;

public class Employee {
    private int employeeId;
    private String name;
    private String title;
    private int supervisor;
    private String department;
    private Boolean deptHead;

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

    public Boolean getDeptHead() {
        return deptHead;
    }

    public void setDeptHead(Boolean deptHead) {
        this.deptHead = deptHead;
    }
}

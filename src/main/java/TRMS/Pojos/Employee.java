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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((department == null) ? 0 : department.hashCode());
		result = prime * result + (deptHead ? 1231 : 1237);
		result = prime * result + employeeId;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + supervisor;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Employee other = (Employee) obj;
		if (department == null) {
			if (other.department != null)
				return false;
		} else if (!department.equals(other.department))
			return false;
		if (deptHead != other.deptHead)
			return false;
		if (employeeId != other.employeeId)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (supervisor != other.supervisor)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
}

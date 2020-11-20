package TRMS.pojos;

import TRMS.enums.AuthPriv;

public class User {
	private int userId;
    private String username;
    private String password;
    private int employeeId;
    private AuthPriv privilege;

    public User() {
        super();
    }

    public User(String username, String password, int employeeId, AuthPriv privilege) {
		this.username = username;
		this.password = password;
		this.employeeId = employeeId;
		this.privilege = privilege;
	}

	public User(int userId, String username, String password, int employeeId, AuthPriv privilege) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.employeeId = employeeId;
		this.privilege = privilege;
	}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public AuthPriv getPrivilege() {
        return privilege;
    }

    public void setPrivilege(AuthPriv privilege) {
        this.privilege = privilege;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + employeeId;
		result = prime * result + ((privilege == null) ? 0 : privilege.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (employeeId != other.employeeId)
			return false;
		if (privilege != other.privilege)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}

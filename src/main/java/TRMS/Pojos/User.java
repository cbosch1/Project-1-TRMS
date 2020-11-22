package TRMS.pojos;

import TRMS.enums.AuthPriv;

/**
 * A java representation of an employee's login information
 */
public class User {
	private int userId;
    private String username;
    private String password;
    private int employeeId;
    private AuthPriv privilege;

    public User() {
        super();
    }

	/**
	 * Construct a User with the applicable parameters
     * @param username The username of this user
     * @param password The hashed password corresponding to the user
     * @param employeeId The id of the employee who this user represents
     * @param privilege The level of privilege this user has for accessing data
	 */
    public User(String username, String password, int employeeId, AuthPriv privilege) {
		this.username = username;
		this.password = password;
		this.employeeId = employeeId;
		this.privilege = privilege;
	}

	/**
	 * Construct a User with the applicable parameters
	 * @param userId The unique identifier of this user
     * @param username The username of this user
     * @param password The hashed password corresponding to the user
     * @param employeeId The id of the employee who this user represents
     * @param privilege The level of privilege this user has for accessing data
	 */
	public User(int userId, String username, String password, int employeeId, AuthPriv privilege) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.employeeId = employeeId;
		this.privilege = privilege;
	}

	/**
	 * @return The unique identifier of this user
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId The unique identifier of this user
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return The username of this user
	 */
    public String getUsername() {
        return username;
    }

	/**
	 * @param username The username of this user
	 */
    public void setUsername(String username) {
        this.username = username;
    }

	/**
	 * @return The hashed password corresponding to the user
	 */
    public String getPassword() {
        return password;
    }

	/**
	 * @param password The hashed password corresponding to the user
	 */
    public void setPassword(String password) {
        this.password = password;
    }

	/**
	 * @return The id of the employee who this user represents
	 */
    public int getEmployeeId() {
        return employeeId;
    }

	/**
	 * @param employeeId The id of the employee who this user represents
	 */
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

	/**
	 * @return The level of privilege this user has for accessing data
	 */
    public AuthPriv getPrivilege() {
        return privilege;
    }

	/**
	 * @param privilege The level of privilege this user has for accessing data
	 */
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
}

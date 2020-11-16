package TRMS.pojos;

import TRMS.enums.AuthPriv;

public class User {
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
}

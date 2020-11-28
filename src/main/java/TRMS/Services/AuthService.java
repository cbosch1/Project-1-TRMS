package TRMS.services;

import TRMS.pojos.Employee;
import TRMS.pojos.User;

public interface AuthService {

    public boolean authenticateUser(User user);
	
	public String createToken(User user, Employee emp);
	
	public boolean validateToken(String token);

	public String readTokenName(String token);
	
	public int readTokenId(String token);
	
	public int readTokenEmp(String token);

	public String readTokenPrivilege(String token);
}

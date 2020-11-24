package TRMS.services;

import TRMS.pojos.User;

public interface AuthService {

    public boolean authenticateUser(User user);
	
	public String createToken(User user);
	
	public boolean validateToken(String token);

	public String readTokenPrivilege(String token);
}
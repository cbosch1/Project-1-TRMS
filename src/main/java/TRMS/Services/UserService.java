package TRMS.Services;

import TRMS.Pojos.User;
import TRMS.Pojos.Enums.AuthPriv;

public interface UserService {
    
    public boolean createUser(String username, String password, int employeeId, AuthPriv privilege);

    public User readUser(int userId);

    public boolean updateUser(String username, String password, int employeeId, AuthPriv privilege);

    public boolean deleteUser(int userId);
}
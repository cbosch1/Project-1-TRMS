package TRMS.Controllers;

import TRMS.Pojos.User;
import TRMS.Pojos.Enums.AuthPriv;

public interface UserControl {
    
    public boolean createUser(String username, String password, int employeeId, AuthPriv privilege);

    public User readUser(int userId);

    public boolean updateUser(String username, String password, int employeeId, AuthPriv privilege);

    public boolean deleteUser(int userId);
}
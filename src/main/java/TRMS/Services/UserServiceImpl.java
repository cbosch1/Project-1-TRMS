package TRMS.services;

import TRMS.enums.AuthPriv;
import TRMS.pojos.User;

public class UserServiceImpl implements UserService {

    @Override
    public boolean createUser(String username, String password, int employeeId, AuthPriv privilege) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteUser(int userId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public User readUser(int userId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean updateUser(String username, String password, int employeeId, AuthPriv privilege) {
        // TODO Auto-generated method stub
        return false;
    }
    
}

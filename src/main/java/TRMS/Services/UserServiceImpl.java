package TRMS.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.daos.UserDao;
import TRMS.enums.AuthPriv;
import TRMS.pojos.User;

public class UserServiceImpl implements UserService {

    private static Logger Log = LogManager.getLogger("Service");

    private UserDao userDao;

    public UserServiceImpl(UserDao userDao){
        super();
        this.userDao = userDao;
    }

    @Override
    public int createUser(String username, String password, int employeeId, AuthPriv privilege) {
        // TODO Auto-generated method stub
        return 0;
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

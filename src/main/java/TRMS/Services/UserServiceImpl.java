package TRMS.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.daos.UserDao;
import TRMS.enums.AuthPriv;
import TRMS.pojos.User;

/**
 * UserService implementation, dependency is an object that implements
 * the UserDao interface. Most methods inside this object will take in
 * various values and convert them to the form the Dao requires, then call it's 
 * equivalent Dao method, handle any exceptions and return what the Dao returns.
 */
public class UserServiceImpl implements UserService {

    private static Logger Log = LogManager.getLogger("Service");

    private UserDao userDao;

    public UserServiceImpl(UserDao userDao){
        super();
        this.userDao = userDao;
    }

    /**
     * Converts inputs into a user object and then sends object
     * to the Dao. Returns the generated id.
     * @param username of the user to create
     * @param password the Hashed/Unhashed password corresponding to the user
     * @param employeeId the id of the employee who this user represents
     * @param privilege the level of privilege this user has for accessing data
     * @return The generated id for this user
     */
    @Override
    public int createUser(String username, String password, int employeeId, AuthPriv privilege) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * Calls the Dao to remove the user with the given id
     * @param userId the id of the user to delete
     * @return true if the request was successful
     */
    @Override
    public boolean deleteUser(int userId) {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * Calls the Dao to read the user with the given id
     * @param userId the Id of the user to read
     * @return User object representing the user with given id
     */
    @Override
    public User readUser(int userId) {
        // TODO Auto-generated method stub
        return null;
    }
    /**
     * Calls the Dao to update a user with given id. The input fields
     * should be given the updated information.
     * @param userId the Id of the user to update
     * @param username of the user to update
     * @param password the Hashed/Unhashed password corresponding to the user
     * @param employeeId the id of the employee who this user represents
     * @param privilege the level of privilege this user has for accessing data
     * @return True if the update was successful
     */
    @Override
    public boolean updateUser(int userId, String username, String password, int employeeId, AuthPriv privilege) {
        // TODO Auto-generated method stub
        return false;
    }
    
}

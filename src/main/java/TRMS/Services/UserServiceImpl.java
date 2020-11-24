package TRMS.services;

import java.security.MessageDigest;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.daos.UserDao;
import TRMS.enums.AuthPriv;
import TRMS.pojos.User;

/**
 * UserService implementation, dependency is an object that implements the
 * UserDao interface. Most methods inside this object will take in various
 * values and convert them to the form the Dao requires, then call it's
 * equivalent Dao method, handle any exceptions and return what the Dao returns.
 */
public class UserServiceImpl implements UserService {

    private static Logger Log = LogManager.getLogger("Service");

    private static byte[] salty = "enVariable".getBytes();

    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        super();
        this.userDao = userDao;
    }

    /**
     * Converts inputs into a user object and then sends object to the Dao. Returns
     * the generated id.
     * 
     * @param username   of the user to create
     * @param password   the Hashed/Unhashed password corresponding to the user
     * @param employeeId the id of the employee who this user represents
     * @param privilege  the level of privilege this user has for accessing data
     * @return The generated id for this user
     */
    @Override
    public int createUser(String username, String password, int employeeId, AuthPriv privilege) {
        Log.info("Responding to create user...");
        int result = -1;

        User user = new User(username, hashPass(password), employeeId, privilege);

        try {
            result = userDao.createUser(user);
            Log.info("Successfully created user");
        } catch (SQLException e) {
            Log.warn("Error thrown in dao call: ", e);
        }
        return result;
    }

    /**
     * Calls the Dao to remove the user with the given id
     * 
     * @param userId the id of the user to delete
     * @return true if the request was successful
     */
    @Override
    public boolean deleteUser(int userId) {
        Log.info("Responding to delete user...");
        boolean result = false;

        try {
            result = userDao.deleteUser(userId);
            if (result) {
                Log.info("Successfully deleted user");
            } else {
                Log.warn("user was not successfully deleted");
            }
        } catch (SQLException e) {
            Log.warn("Error thrown in dao call: ", e);
        }
        return result;
    }

    /**
     * Calls the Dao to read the user with the given id
     * 
     * @param userId the Id of the user to read
     * @return User object representing the user with given id
     */
    @Override
    public User readUser(int userId) {
        Log.info("Responding to read user...");
        User result = null;

        try {
            result = userDao.readUser(userId);
            Log.info("Successfully retrieved user");

        } catch (SQLException e) {
            Log.warn("Error thrown in dao call: ", e);
        }
        return result;
    }

    /**
     * Calls the Dao to update a user with given id. The input fields should be
     * given the updated information.
     * 
     * @param userId     the Id of the user to update
     * @param username   of the user to update
     * @param password   the Hashed/Unhashed password corresponding to the user
     * @param employeeId the id of the employee who this user represents
     * @param privilege  the level of privilege this user has for accessing data
     * @return True if the update was successful
     */
    @Override
    public boolean updateUser(int userId, String username, String password, int employeeId, AuthPriv privilege) {
        Log.info("Responding to update user...");
        boolean result = false;

        User user = new User(userId, username, hashPass(password), employeeId, privilege);

        try {
            result = userDao.updateUser(user);
            if (result) {
                Log.info("Successfully updated user");
            } else {
                Log.warn("Something went wrong, user not updated properly");
            }
        } catch (SQLException e) {
            Log.warn("Error thrown in dao call: ", e);
        }
        return result;
    }

    @Override
    public User loginUser(String username, String password) {
        Log.info("Responding to login user...");
        User result = null;

        try {
            result = userDao.loginUser(username, hashPass(password));
            Log.info("Successfully logged in user");
        } catch (SQLException e) {
            Log.warn("Error thrown in dao call: ", e);
        }
        return result;
    }

    private String hashPass(String password) {
        String hashedPass = null;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salty);

            byte[] hashedBytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();

            for (byte b : hashedBytes){
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(0));
            }

            hashedPass = sb.toString();
        
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hashedPass;
    }
    
}

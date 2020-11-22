package TRMS.services;

import TRMS.pojos.User;
import TRMS.enums.AuthPriv;

/**
 * UserService interface, the goal of an implementation of this 
 * interface will be to take in various values and convert them to the form
 * an equivalent Dao requires, then call it's equivalent Dao method, 
 * handle any exceptions, and return what the Dao returns.
 */
public interface UserService {
    
    /**
     * Converts inputs into a user object and then sends object
     * to the Dao. Returns the generated id.
     * @param username of the user to create
     * @param password the Hashed/Unhashed password corresponding to the user
     * @param employeeId the id of the employee who this user represents
     * @param privilege the level of privilege this user has for accessing data
     * @return The generated id for this user
     */
    public int createUser(String username, String password, int employeeId, AuthPriv privilege);

    /**
     * Calls the Dao to remove the user with the given id
     * @param userId the id of the user to delete
     * @return true if the request was successful
     */
    public User readUser(int userId);

    /**
     * Calls the Dao to read the user with the given id
     * @param userId the Id of the user to read
     * @return User object representing the user with given id
     */
    public boolean updateUser(int userId, String username, String password, int employeeId, AuthPriv privilege);
    
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

    public boolean deleteUser(int userId);
}
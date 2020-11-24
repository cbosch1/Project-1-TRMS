package TRMS.daos;

import java.sql.SQLException;

import TRMS.pojos.User;

/**
 * UserDao interface, the goal of an implementation of this 
 * interface will be to take in user related values and perform
 * CRUD operations with a method of storage the implementation specifies.
 */
public interface UserDao {

    /**
     * Insert application user into storage.
     * @param user object to be inserted
     * @return int - The id of the inserted user.
     */
    public int createUser(User user) throws SQLException;

    /**
     * Retrieves a single user from storage that has provided user Id
     * @param userId The id of the user to return
     * @return user object
     */
    public User readUser(int userId) throws SQLException;

    /**
     * Updates user data inside storage, utilizes this object's id number
     * and will update all information except the id number.
     * @param user The user with update fields
     * @return true if request is successful
     */
    public boolean updateUser(User user) throws SQLException;

    /**
     * Remove user data from storage that has provided id
     * @param userId The id that relates to the user to delete
     * @return true if request was successful
     */
    public boolean deleteUser(int userId) throws SQLException;

    public User loginUser(String username, String password) throws SQLException;
}
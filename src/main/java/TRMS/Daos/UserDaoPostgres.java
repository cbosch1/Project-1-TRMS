package TRMS.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.enums.AuthPriv;
import TRMS.pojos.User;
import TRMS.util.ConnectionUtil;

/**
 * User Dao that connects to the user table in the postgres database.
 */
public class UserDaoPostgres implements UserDao {

    private PreparedStatement stmt;

    private static Logger Log = LogManager.getLogger("Dao");

    private ConnectionUtil connUtil;

    public UserDaoPostgres(ConnectionUtil connectionUtil){
        super();
        connUtil = connectionUtil;
    }

    /**
     * Insert application user into the database. This user's id will be
     * ignored as the database will generate it's own.
     * @param user object to be inserted
     * @return integer of the generated id
     */
    @Override
    public int createUser(User user) throws SQLException {
        int result; 

        try(Connection conn = connUtil.createConnection()) {
            Log.info("Received request to insert user: " + user.getUsername());

            String sql = "INSERT INTO userbase VALUES (Default,?,?,?,?::auth_priv) RETURNING user_id;";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, user.getEmployeeId());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getPrivilege().toString());

            ResultSet rs = stmt.executeQuery();
            rs.next();
            result = rs.getInt(1);
            Log.info("Request completed, generated user id: " + result);

        } catch (SQLException e){
            Log.warn("SQLException thrown in create related to user: " + user.getUsername(), e);
            throw e;
        }

        return result;
    }

    /**
     * Remove user data from the database that has provided id
     * @param userId that relates to the user to delete
     * @return true if request was successful
     */
    @Override
    public boolean deleteUser(int userId) throws SQLException {
        boolean result = false; 

        try(Connection conn = connUtil.createConnection()) {
            Log.info("Received request to delete user with id: " + userId);

            String sql = "DELETE FROM userbase WHERE user_id = ?;";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, userId);

            result = (1 == stmt.executeUpdate());

            if (result){
                Log.info("Request completed, user with id: " + userId + " was deleted.");
            } else
                Log.warn("Request to delete user with id: " + userId +" was NOT completed");

        } catch (SQLException e){
            Log.warn("SQLException thrown in user delete for id: " + userId, e);
            throw e;
        }

        return result;
    }

    /**
     * Retrieves a single user from the database that has provided user Id
     * @param userId of the user to return
     * @return user object
     */
    @Override
    public User readUser(int userId) throws SQLException {
        User result = null; 

        try(Connection conn = connUtil.createConnection()) {
            Log.info("Received request to retrieve user with id: " + userId);

            String sql = "SELECT * FROM userbase WHERE user_id = ?;";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            result = new User(rs.getInt(1), rs.getString(3), rs.getString(4), rs.getInt(2), AuthPriv.valueOf(rs.getString(5)));

            Log.info("Request completed, retrieved user: " + result.getUsername());

        } catch (SQLException e){
            Log.warn("SQLException thrown in read for user with id: " + userId, e);
            throw e;
        }

        return result;
    }

    /**
     * Updates user data inside the database, utilizes this object's id number
     * and will update all information except the id number.
     * @param user object with update fields
     * @return true if request is successful
     */
    @Override
    public boolean updateUser(User user) throws SQLException {
        boolean result = false; 

        try(Connection conn = connUtil.createConnection()) {
            Log.info("Received request to update user with id: " + user.getUserId());

            String sql = "UPDATE userbase SET emp_id = ?, username = ?, passphrase = ?, privilege = ?::auth_priv WHERE user_id = ?;";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, user.getEmployeeId());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getPrivilege().toString());
            stmt.setInt(5, user.getUserId());

            result = (1 == stmt.executeUpdate());

            if (result){
                Log.info("Request completed, user with id: " + user.getUserId() + " was updated.");
            } else
                Log.warn("Request to update user with id: " + user.getUserId() +" was NOT completed");

        } catch (SQLException e){
            Log.warn("SQLException thrown in user update for id: " + user.getUserId(), e);
            throw e;
        }

        return result;
    }
    
}

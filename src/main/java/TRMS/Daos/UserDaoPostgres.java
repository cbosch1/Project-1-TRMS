package TRMS.daos;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * Remove user data from the database that has provided id
     * @param userId that relates to the user to delete
     * @return true if request was successful
     */
    @Override
    public boolean deleteUser(int userId) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * Retrieves a single user from the database that has provided user Id
     * @param userId of the user to return
     * @return user object
     */
    @Override
    public User readUser(int userId) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Updates user data inside the database, utilizes this object's id number
     * and will update all information except the id number.
     * @param user object with update fields
     * @return true if request is successful
     */
    @Override
    public boolean updateUser(User user) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }
    
}

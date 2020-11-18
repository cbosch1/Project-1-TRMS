package TRMS.daos;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import TRMS.pojos.User;
import TRMS.util.ConnectionUtil;

public class UserDaoPostgres implements UserDao {

    private PreparedStatement stmt;

    private static Logger Log = LogManager.getLogger("Dao");

    private ConnectionUtil connUtil;

    public UserDaoPostgres(ConnectionUtil connectionUtil){
        super();
        connUtil = connectionUtil;
    }

    @Override
    public int createUser(User user) throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean deleteUser(int userId) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public User readUser(int userId) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }
    
}

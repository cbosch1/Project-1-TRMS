package TRMS.daos;

import java.sql.SQLException;

import TRMS.pojos.User;

public interface UserDao {
    
    public int createUser(User user) throws SQLException;

    public User readUser(int userId) throws SQLException;

    public boolean updateUser(User user) throws SQLException;

    public boolean deleteUser(int userId) throws SQLException;
}
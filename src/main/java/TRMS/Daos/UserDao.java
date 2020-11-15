package TRMS.daos;

import java.sql.SQLException;

import TRMS.pojos.User;

public interface UserDao {
    
    public void createUser(User user) throws SQLException;

    public User readUser(int userId) throws SQLException;

    public void updateUser(User user) throws SQLException;

    public void deleteUser(int userId) throws SQLException;
}
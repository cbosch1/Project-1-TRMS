package TRMS.daos;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import TRMS.enums.AuthPriv;
import TRMS.pojos.User;
import TRMS.util.ConnectionUtil;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoTest {
	
	@Mock
	private ConnectionUtil connUtil;
	@Mock
	private Connection connection;

	private Connection realConn;
	private PreparedStatement stmt;
	private PreparedStatement testStmt;
	private PreparedStatement spy;

	private UserDao userDao;
	private User user;

    @BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		realConn = new ConnectionUtil().createConnection();
		userDao = new UserDaoPostgres(connUtil);
		user = new User("bigbad1", "sdaf394q3jierq3-4rfmaefv", 1, AuthPriv.ADMIN);
	}

	@After
	public void tearDown() throws Exception {
		if (stmt != null){
			stmt.close();
		}
		realConn.close();
	}

	@Test
	public void createUserTest() {
		try {
			//Prep statement with proper SQL
			String sql = "INSERT INTO userbase VALUES (Default,?,?,?::auth_priv) RETURNING emp_id;";

			//Call helper method to initialize mockito spy with test-unique sql
			try {
				initStmtHelper(sql);
			} catch (SQLException e) {
				fail("SQLException thrown in test setup: " + e);
			}

			//Test createEmployee
			try {
				int returnId = userDao.createUser(user);

				verify(spy).setString(1, user.getUsername());
				verify(spy).setString(2, user.getPassword());
				verify(spy).setString(3, user.getPrivilege().toString());

				verify(spy).executeUpdate();

				assertEquals("returned id does not match expected", returnId > 0);

			} catch (SQLException e) {
				fail("SQLException thrown in creation process: " + e);
			} 

		} finally {
			//Removal process, post-test
			try {
				testStmt = realConn.prepareStatement("DELETE FROM userbase WHERE emp_id = ?;");
				testStmt.setInt(1, user.getEmployeeId());
				testStmt.executeUpdate();
			} catch (SQLException e) {
				fail("TEST ERROR, could not properly remove user: " + e);
			}
		}
	}

	@Test
	public void readUserTest() {
		try {
			//Insert test user to be read
			String sql = "INSERT INTO userbase VALUES (?,?,?,?::auth_priv);";
			
			try {
				testStmt = realConn.prepareStatement(sql);
				testStmt.setInt(1, user.getEmployeeId());
				testStmt.setString(2, user.getUsername());
				testStmt.setString(3, user.getPassword());
				testStmt.setString(4, user.getPrivilege().toString());
				assertTrue("Error in inserting user", 1 == testStmt.executeUpdate());
			} catch (SQLException e){
				fail("SQLException thrown in test setup: " + e);
			}

			//Prep statement with proper SQL
			sql = "SELECT * FROM userbase WHERE emp_id = ?;";
			try {
				initStmtHelper(sql);
			} catch (SQLException e){
				fail("SQLException thrown while utilizing helper method");
			}

			try {
				User resultUser = userDao.readUser(user.getEmployeeId());

				//Verify statement was prepared and executed properly
				verify(spy).setInt(1, user.getEmployeeId());
				verify(spy).executeQuery();

				assertTrue("Object returned does not match expected object", user.equals(resultUser));
			} catch(SQLException e) {
				fail("SQLException thrown while attempting to retrieve object: " + e);
			}
		} finally {
			//Removal process, post-test
			try {
				testStmt = realConn.prepareStatement("DELETE FROM userbase WHERE emp_id = ?;");
				testStmt.setInt(1, user.getEmployeeId());
				testStmt.executeUpdate();
			} catch (SQLException e) {
				fail("TEST ERROR, could not properly remove user: " + e);
			}
		}
	}

	@Test
	public void updateUserTest() {
		try {
			//Insert test user to be updated
			String sql = "INSERT INTO userbase VALUES (?,?,?,?::auth_priv);";
			
			try {
				testStmt = realConn.prepareStatement(sql);
				testStmt.setInt(1, user.getEmployeeId());
				testStmt.setString(2, user.getUsername());
				testStmt.setString(3, user.getPassword());
				testStmt.setString(4, user.getPrivilege().toString());
				assertTrue("Error in inserting user", 1 == testStmt.executeUpdate());
			} catch (SQLException e){
				fail("SQLException thrown in test setup: " + e);
			}

			//Prep statement with proper SQL
			sql = "UPDATE userbase SET username = ?, passphrase = ?, privilege = ? WHERE emp_id = ?;";
			try {
				initStmtHelper(sql);
			} catch (SQLException e){
				fail("SQLException thrown while utilizing helper method");
			}

			try {
				user.setUsername("OneEyedNick");
				user.setPassword("asdf239r35oitjetg");
				user.setPrivilege(AuthPriv.BENCO);
				
				userDao.updateUser(user);

				//Verify statement was prepared and executed properly
				verify(spy).setInt(1, user.getEmployeeId());
				verify(spy).setString(2, user.getUsername());
				verify(spy).setString(3, user.getPassword());
				verify(spy).setInt(4, user.getEmployeeId());

				verify(spy).executeUpdate();

				testStmt = realConn.prepareStatement("SELECT * FROM userbase WHERE emp_id = ?;");
				testStmt.setInt(1, user.getEmployeeId());
				ResultSet rs = testStmt.executeQuery();

				rs.next();

				User modUser = new User(rs.getString(2), rs.getString(3), rs.getInt(1), AuthPriv.valueOf(rs.getString(4)));

				assertTrue("Database object does not match as modified", user.equals(modUser));

			} catch(SQLException e) {
				fail("SQLException thrown while attempting to update object: " + e);
			}
		} finally {
			//Removal process, post-test
			try {
				testStmt = realConn.prepareStatement("DELETE FROM userbase WHERE emp_id = ?;");
				testStmt.setInt(1, user.getEmployeeId());
				testStmt.executeUpdate();
			} catch (SQLException e) {
				fail("TEST ERROR, could not properly remove user: " + e);
			}
		}
	}
	
	@Test
	public void deleteUserTest() {
		try {
			//Insert test user to be updated
			String sql = "INSERT INTO userbase VALUES (?,?,?,?::auth_priv);";
			
			try {
				testStmt = realConn.prepareStatement(sql);
				testStmt.setInt(1, user.getEmployeeId());
				testStmt.setString(2, user.getUsername());
				testStmt.setString(3, user.getPassword());
				testStmt.setString(4, user.getPrivilege().toString());
				assertTrue("Error in inserting user", 1 == testStmt.executeUpdate());
			} catch (SQLException e){
				fail("SQLException thrown in test setup: " + e);
			}

			//Prep statement with proper SQL
			sql = "DELETE FROM userbase WHERE emp_id = ?;";
			try {
				initStmtHelper(sql);
			} catch (SQLException e){
				fail("SQLException thrown while utilizing helper method");
			}

			try {
				
				userDao.deleteUser(user.getEmployeeId());

				//Verify statement was prepared and executed properly
				verify(spy).setInt(1, user.getEmployeeId());
				verify(spy).executeUpdate();

			} catch(SQLException e) {
				fail("SQLException thrown while attempting to update object: " + e);
			}
		} finally {
			//Attempt to delete object that was already deleted (Should throw exception)
			try {
				testStmt = realConn.prepareStatement("DELETE FROM userbase WHERE emp_id = ?;");
				testStmt.setInt(1, user.getEmployeeId());
				assertEquals("Object was not deleted properly", 0, testStmt.executeUpdate());
			} catch (SQLException e) {}
		}
	}

	/**
	 * Helper method that initializes mock and spy variables using the prepared sql string provided
	 * @param sql -Prepared SQL String
	 */
	private void initStmtHelper(String sql) throws SQLException {
		//Prep Mockito Spy
		stmt = realConn.prepareStatement(sql);
		spy = Mockito.spy(stmt);

		//Set standard connection mocking methods
		when(connUtil.createConnection()).thenReturn(connection);
		when(connection.prepareStatement(sql)).thenReturn(spy);
	}
}

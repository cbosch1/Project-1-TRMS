package TRMS.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import TRMS.daos.UserDao;
import TRMS.enums.AuthPriv;
import TRMS.pojos.User;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@Mock
	private UserDao mockDao;

	private UserService serviceToTest;
	private User user;
            
    @BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		serviceToTest = new UserServiceImpl(mockDao);
		user = new User(2010, "Biggy Smalls", "asdfkl34rq3rjfpidsa", 0, AuthPriv.EMPLOYEE);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void createUserTest() {
		try {
			user.setUserId(0);
			when(mockDao.createUser(user)).thenReturn(user.getUserId());

			assertTrue("createUser returned false", 
				serviceToTest.createUser(user.getUsername(), user.getPassword(), user.getEmployeeId(), 
										user.getPrivilege()) == user.getUserId());

			verify(mockDao).createUser(user);

		} catch (SQLException e) {
			fail("SQLException thrown by create method: " + e);
		} catch (Exception e) {
			fail("Exception thrown by create method: " + e);
		}
	}

	@Test
	public void readUserTest() {
		try {
			when(mockDao.readUser(user.getUserId())).thenReturn(user);

			User returned = serviceToTest.readUser(user.getUserId());

			verify(mockDao).readUser(user.getUserId()));

			assertTrue("Object read does not match expected", user.equals(returned));

		} catch (SQLException e) {
			fail("SQLException thrown by read method: " + e);
		} catch (Exception e) {
			fail("Exception thrown by read method: " + e);
		}
	}

	@Test
	public void updateUserTest() {
		try {
			when(mockDao.updateUser(user)).thenReturn(true);

			assertTrue("updateUser returned false", 
				serviceToTest.updateUser(user.getUserId(), user.getUsername(), user.getPassword(), 
										user.getEmployeeId(), user.getPrivilege()));

			verify(mockDao).updateUser(user);

		} catch (SQLException e) {
			fail("SQLException thrown by update method: " + e);
		} catch (Exception e) {
			fail("Exception thrown by update method: " + e);
		}
	}
	
	@Test
	public void deleteUserTest() {
		try {
			when(mockDao.deleteUser(user.getUserId())).thenReturn(true);

			assertTrue("deleteUser returned false", serviceToTest.deleteUser(user.getUserId()));

			verify(mockDao).deleteUser(user.getUserId());

		} catch (SQLException e) {
			fail("SQLException thrown by delete method: " + e);
		} catch (Exception e) {
			fail("Exception thrown by delete method: " + e);
		}
	}
}

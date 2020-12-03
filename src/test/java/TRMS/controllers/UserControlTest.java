package TRMS.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import TRMS.enums.AuthPriv;
import TRMS.pojos.User;
import TRMS.services.UserService;
import io.javalin.http.Context;

@RunWith(MockitoJUnitRunner.class)
public class UserControlTest {

	@Mock
	private UserService mockService;
	@Mock
	private Context mockCtx;

	private UserControl controlToTest;
	private User user;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		controlToTest = new UserControl(mockService, null);
		// cSpell:ignore hashedpassword
		user = new User(2010, "username", "hashedpassword123467890", 0, AuthPriv.EMPLOYEE);

		when(mockCtx.formParam("userId")).thenReturn(Integer.toString(user.getUserId()));
		when(mockCtx.formParam("username")).thenReturn(user.getUsername());
		when(mockCtx.formParam("password")).thenReturn(user.getPassword());
		when(mockCtx.formParam("employeeId")).thenReturn(Integer.toString(user.getEmployeeId()));
		when(mockCtx.formParam("privilege")).thenReturn(user.getPrivilege().toString());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void createUserTest() {
		try {
			controlToTest.createUser(mockCtx);

			verify(mockCtx).formParam("username");
			verify(mockCtx).formParam("password");
			verify(mockCtx).formParam("employeeId");
			verify(mockCtx).formParam("privilege");

			verify(mockService).createUser(user.getUsername(), user.getPassword(), user.getEmployeeId(), user.getPrivilege());
			
			verify(mockCtx).status(200);

		} catch (Exception e) {
			fail("Exception thrown during create test: " + e);
		}
	}

	@Test
	public void readUserTest() {
		try {
			controlToTest.readUser(mockCtx);

			verify(mockCtx).formParam("userId");
			verify(mockService).readUser(user.getUserId());

			verify(mockCtx).status(200);
		
		} catch (Exception e) {
			fail("Exception thrown during read test: " + e);
		}
	}

	@Test
	public void updateUserTest() {
		try {
			when(mockService.updateUser(user.getUserId(), user.getUsername(), user.getPassword(), 
										user.getEmployeeId(), user.getPrivilege())).thenReturn(true);

			controlToTest.updateUser(mockCtx);
	
			verify(mockCtx).formParam("userId");
			verify(mockCtx).formParam("username");
			verify(mockCtx).formParam("password");
			verify(mockCtx).formParam("employeeId");
			verify(mockCtx).formParam("privilege");
	
			verify(mockService).updateUser(user.getUserId(), user.getUsername(), user.getPassword(), 
											user.getEmployeeId(), user.getPrivilege());

			verify(mockCtx).status(200);
	
		} catch (Exception e) {
			fail("Exception thrown during update test: " + e);
		}
	}
	
	@Test
	public void deleteUserTest() {
		try {
			when(mockService.deleteUser(user.getUserId())).thenReturn(true);

			controlToTest.deleteUser(mockCtx);

			verify(mockCtx).formParam("userId");
			verify(mockService).deleteUser(user.getUserId());

			verify(mockCtx).status(200);
		
		} catch (Exception e) {
			fail("Exception thrown during delete test: " + e);
		}
	}
}
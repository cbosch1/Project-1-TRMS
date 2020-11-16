package TRMS.daos;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

import TRMS.pojos.Employee;
import TRMS.util.ConnectionUtil;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeDaoTest {

	@Mock
	private ConnectionUtil connUtil;
	@Mock
	private Connection connection;

	private Connection realConn;
	private PreparedStatement stmt;
	private PreparedStatement testStmt;
	private PreparedStatement spy;

	private EmployeeDao employeeDao;
	private Employee employee;
        
    @BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		realConn = new ConnectionUtil().createConnection();
		employeeDao = new EmployeeDaoPostgres(connUtil);
		employee = new Employee(1, "Billy Bob", "Highest of the Hunters", 0, "Hunting", false);
	}

	@After
	public void tearDown() throws Exception {
		if (stmt != null){
			stmt.close();
		}
		realConn.close();
	}

	@Test
	public void createEmployeeTest() {
		try {
			//Prep statement with proper SQL
			String sql = "INSERT INTO employee VALUES (?,?,?,?,?,?);";

			//Call helper method to initilize mockito spy with test-unique sql
			try {
				initStmtHelper(sql);
			} catch (SQLException e) {
				fail("SQLException thrown in test setup: " + e);
			}

			//Test createEmployee
			try {
				employeeDao.createEmployee(employee);

				verify(spy).setInt(1, employee.getEmployeeId());
				verify(spy).setString(2, employee.getName());
				verify(spy).setString(3, employee.getTitle());
				verify(spy).setInt(4, employee.getSupervisor());
				verify(spy).setString(5, employee.getDepartment());
				verify(spy).setBoolean(6, employee.getDeptHead());

				verify(spy).executeUpdate();

			} catch (SQLException e) {
				fail("SQLException thrown in creation process: " + e);
			} 

		} finally {
			//Removal process, post-test
			try {
				testStmt = realConn.prepareStatement("DELETE FROM employee WHERE emp_id = ?;");
				testStmt.setInt(1, employee.getEmployeeId());
				testStmt.executeUpdate();
			} catch (SQLException e) {
				fail("TEST ERROR, could not properly remove employee: " + e);
			}
		}
	}

	@Test
	public void readEmployeeTest() {
		fail("Not yet implemented");
	}

	@Test
	public void readAllEmployeesTest() {
		fail("Not yet implemented");
	}

	@Test
	public void updateEmployeeTest() {
		fail("Not yet implemented");
	}

	@Test
	public void deleteEmployeeTest() {
		fail("Not yet implemented");
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

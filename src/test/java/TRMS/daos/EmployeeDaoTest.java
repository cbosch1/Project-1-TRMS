package TRMS.daos;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
		employee = new Employee(2010, "Billy Bob", "Highest of the Hunters", 1, "Hunting", false);
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
			String sql = "INSERT INTO employee VALUES (Default,?,?,?,?,?,?) RETURNING emp_id;";

			//Call helper method to initialize mockito spy with test-unique sql
			try {
				initStmtHelper(sql);
			} catch (SQLException e) {
				fail("SQLException thrown in test setup: " + e);
			}

			//Test createEmployee
			try {
				int returnId = employeeDao.createEmployee(employee);
				employee.setEmployeeId(returnId);

				verify(spy).setString(1, employee.getName().split(" ")[1]);
				verify(spy).setString(2, employee.getName().split(" ")[0]);
				verify(spy).setString(3, employee.getTitle());
				verify(spy).setInt(4, employee.getSupervisor());
				verify(spy).setString(5, employee.getDepartment());
				verify(spy).setBoolean(6, employee.getDeptHead());

				verify(spy).executeQuery();

				assertTrue("returned id does not match expected", returnId > 0);

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
		try {
			//Insert test employee to be read
			String sql = "INSERT INTO employee VALUES (?,?,?,?,?,?,?);";
			
			try {
				testStmt = realConn.prepareStatement(sql);
				testStmt.setInt(1, employee.getEmployeeId());
				testStmt.setString(2, employee.getName().split(" ")[1]);
				testStmt.setString(3, employee.getName().split(" ")[0]);
				testStmt.setString(4, employee.getTitle());
				testStmt.setInt(5, employee.getSupervisor());
				testStmt.setString(6, employee.getDepartment());
				testStmt.setBoolean(7, employee.getDeptHead());
				assertTrue("Error in inserting employee", 1 == testStmt.executeUpdate());
			} catch (SQLException e){
				fail("SQLException thrown in test setup: " + e);
			}

			//Prep statement with proper SQL
			sql = "SELECT * FROM employee WHERE emp_id = ?;";
			try {
				initStmtHelper(sql);
			} catch (SQLException e){
				fail("SQLException thrown while utilizing helper method");
			}

			try {
				Employee resultEmp = employeeDao.readEmployee(employee.getEmployeeId());

				//Verify statement was prepared and executed properly
				verify(spy).setInt(1, employee.getEmployeeId());
				verify(spy).executeQuery();

				assertTrue("Object returned does not match expected object", employee.equals(resultEmp));
			} catch(SQLException e) {
				fail("SQLException thrown while attempting to retrieve object: " + e);
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
	public void readAllEmployeesTest() {

		Integer numEmployees = -1;

		//Get current num of employees in Database for verification
		String sql = "SELECT COUNT(*) FROM employee;";
		try{
			testStmt = realConn.prepareStatement(sql);
			ResultSet rs = testStmt.executeQuery();
			rs.next();
			numEmployees = rs.getInt(1);
		} catch (SQLException e) {
			fail("SQLException thrown in test setup: " + e);
		}

		//Prep statement with proper SQL
		sql = "SELECT * FROM employee;";
		try {
			initStmtHelper(sql);
		} catch (SQLException e) {
			fail("SQLException thrown: " + e);
		}

		//Test readAllEmployees functionality
		try {
			List<Employee> allEmployees = employeeDao.readAllEmployees();

			//Verify statement was executed properly
			verify(spy).executeQuery();

			//Verify result set returned proper data
			assertTrue("Returned set is not the same size as expected", numEmployees == allEmployees.size());
			for (Employee e : allEmployees){
				assertFalse("Id returned less than 0 for employee: " + e.getName(), 0 > e.getEmployeeId());
				assertFalse("Name returned blank for employee number: " + e.getEmployeeId(), "".equals(e.getName()));
				assertFalse("Title returned blank for employee: " + e.getName(), "".equals(e.getTitle()));
				assertFalse("Department returned blank for employee: " + e.getName(), "".equals(e.getDepartment()));
			}
		} catch (SQLException e) {
			fail("SQLException thrown while reading all employees");
		}
	}

	@Test
	public void updateEmployeeTest() {
		try {
			//Insert test employee to be updated
			String sql = "INSERT INTO employee VALUES (?,?,?,?,?,?,?);";
			
			try {
				testStmt = realConn.prepareStatement(sql);
				testStmt.setInt(1, employee.getEmployeeId());
				testStmt.setString(2, employee.getName().split(" ")[1]);
				testStmt.setString(3, employee.getName().split(" ")[0]);
				testStmt.setString(4, employee.getTitle());
				testStmt.setInt(5, employee.getSupervisor());
				testStmt.setString(6, employee.getDepartment());
				testStmt.setBoolean(7, employee.getDeptHead());
				assertTrue("Error in inserting employee", 1 == testStmt.executeUpdate());
			} catch (SQLException e){
				fail("SQLException thrown in test setup: " + e);
			}

			//Prep statement with proper SQL
			sql = "UPDATE employee SET lastname=?, firstname=?, title=?, supervisor=?, "
									+ "department=?, dept_head=? WHERE emp_id = ?;";
			try {
				initStmtHelper(sql);
			} catch (SQLException e){
				fail("SQLException thrown while utilizing helper method");
			}

			//Test updateEmployee
			try {
				//Modify values
				employee.setName("Doug Judy");
				employee.setTitle("The Pontiac Bandit");
				employee.setSupervisor(0);
				employee.setDepartment("Rogue");
				employee.setDeptHead(true);

				employeeDao.updateEmployee(employee);
				
				//Verify statement was prepared and executed properly
				verify(spy).setString(1, employee.getName().split(" ")[1]);
				verify(spy).setString(2, employee.getName().split(" ")[0]);
				verify(spy).setString(3, employee.getTitle());
				verify(spy).setInt(4, employee.getSupervisor());
				verify(spy).setString(5, employee.getDepartment());
				verify(spy).setBoolean(6, employee.getDeptHead());
				verify(spy).setInt(7, employee.getEmployeeId());

				verify(spy).executeUpdate();

				//Pull modified employee object from database for comparison
				testStmt = realConn.prepareStatement("SELECT * FROM employee WHERE emp_id = ?;");
				testStmt.setInt(1, employee.getEmployeeId());
				ResultSet rs = testStmt.executeQuery();

				rs.next();
				Employee modEmployee = new Employee(rs.getInt(1), rs.getString(3)+" "+rs.getString(2), 
													rs.getString(4), rs.getInt(5), rs.getString(6), rs.getBoolean(7));

				assertTrue("Database object does not match as modified", employee.equals(modEmployee));

			} catch(SQLException e) {
				fail("SQLException thrown while attempting to update object: " + e);
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
	public void deleteEmployeeTest() {
try {
			//Insert test employee to be deleted
			String sql = "INSERT INTO employee VALUES (?,?,?,?,?,?,?);";
			
			try {
				testStmt = realConn.prepareStatement(sql);
				testStmt.setInt(1, employee.getEmployeeId());
				testStmt.setString(2, employee.getName().split(" ")[1]);
				testStmt.setString(3, employee.getName().split(" ")[0]);
				testStmt.setString(4, employee.getTitle());
				testStmt.setInt(5, employee.getSupervisor());
				testStmt.setString(6, employee.getDepartment());
				testStmt.setBoolean(7, employee.getDeptHead());
				assertTrue("Error in inserting employee", 1 == testStmt.executeUpdate());
			} catch (SQLException e){
				fail("SQLException thrown in test setup: " + e);
			}

			//Prep statement with proper SQL
			sql = "DELETE FROM employee WHERE emp_id = ?;";
			try {
				initStmtHelper(sql);
			} catch (SQLException e){
				fail("SQLException thrown while utilizing helper method");
			}

			//Test deleteEmployee
			try {
				employeeDao.deleteEmployee(employee.getEmployeeId());

				//Verify statement was prepared and executed properly
				verify(spy).setInt(1, employee.getEmployeeId());
				verify(spy).executeUpdate();
				
			} catch(SQLException e) {
				fail("SQLException thrown while attempting to delete object: " + e);
			}
		} finally {
			//Attempt to delete object that was already deleted (Should throw exception)
			try {
				testStmt = realConn.prepareStatement("DELETE FROM employee WHERE emp_id = ?;");
				testStmt.setInt(1, employee.getEmployeeId());
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

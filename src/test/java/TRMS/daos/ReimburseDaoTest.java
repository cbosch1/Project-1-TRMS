package TRMS.daos;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

import TRMS.util.ConnectionUtil;
import TRMS.enums.AppStage;
import TRMS.enums.AppStatus;
import TRMS.enums.EventType;
import TRMS.pojos.ReimburseRequest;

@RunWith(MockitoJUnitRunner.class)
public class ReimburseDaoTest {
	
	@Mock
	private ConnectionUtil connUtil;
	@Mock
	private Connection connection;

	private Connection realConn;
	private PreparedStatement stmt;
	private PreparedStatement testStmt;
	private PreparedStatement spy;
	private PreparedStatement spy2;


	private ReimburseRequestDao reimburseDao;
	private ReimburseRequest reimburse;
            
    @BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		realConn = new ConnectionUtil().createConnection();
		reimburseDao = new ReimburseDaoPostgres(connUtil);
		reimburse = new ReimburseRequest(2010, 0, "The room where it happens", 500.00, EventType.SEMINAR,
										 "A seminar to go over well to do writing techniques", 
										"Writing techniques are applicable in work environments", 
										400.00, true, AppStatus.PENDING, AppStage.UPLOAD, 
										LocalDateTime.of(2020, 11, 17, 10, 0));
	}

	@After
	public void tearDown() throws Exception {
		if (stmt != null){
			stmt.close();
		}
		realConn.close();
	}

	@Test
	public void createRequestTest() {
		try {
			//Prep statement with proper SQL
			String sql = "INSERT INTO reimbursement VALUES (?,?,?,?,?,?,?);";
			String sql2 ="INSERT INTO reimburse_status VALUES (?,?,?,?,?,?,?);";

			//Call helper method to initilize mockito spy with test-unique sql
			try {
				initStmtHelper(sql);
				initStmtHelper2(sql2);
			} catch (SQLException e) {
				fail("SQLException thrown in test setup: " + e);
			}

			//Test createRequest
			try {
				reimburseDao.createRequest(reimburse);

				verify(connection).setAutoCommit(false);

				//Verify properly updated reimbursement table
				verify(spy).setInt(1, reimburse.getRequestId());
				verify(spy).setInt(2, reimburse.getEmployeeId());
				verify(spy).setString(3, reimburse.getLocation());
				verify(spy).setDouble(4, reimburse.getCost());
				verify(spy).setString(5, reimburse.getType().toString());
				verify(spy).setString(6, reimburse.getDescription());
				verify(spy).setString(7, reimburse.getJustificiation());
				verify(spy).executeUpdate();

				//Verify properly updated reimburse_status table
				verify(spy2).setInt(1, reimburse.getRequestId());
				verify(spy2).setDouble(2, reimburse.getProjected());
				verify(spy2).setBoolean(3, reimburse.isUrgent());
				verify(spy2).setString(4, reimburse.getStatus().toString());
				verify(spy2).setString(5, reimburse.getStage().toString());
				verify(spy2).setDate(6, Date.valueOf(LocalDate.from(reimburse.getDateTime())));
				verify(spy2).setTime(7, Time.valueOf(LocalTime.from(reimburse.getDateTime())));
				verify(spy2).executeUpdate();

				verify(connection).commit();

			} catch (SQLException e) {
				fail("SQLException thrown in creation process: " + e);
			} 

		} finally {
			//Removal process, post-test
			try {
				testStmt = realConn.prepareStatement("DELETE FROM reimbursement WHERE request_id = ?;");
				testStmt.setInt(1, reimburse.getRequestId());
				testStmt.executeUpdate();
			} catch (SQLException e) {
				fail("TEST ERROR, could not properly remove reimbursement request: " + e);
			}
		}
	}

	@Test
	public void readRequestTest() {
		try {
			//Insert test reimbursement request to be read
			String sql = "INSERT INTO reimbursement VALUES (?,?,?,?,?,?,?);";
			String sql2 ="INSERT INTO reimburse_status VALUES (?,?,?,?,?,?,?);";

			try {
				realConn.setAutoCommit(false);

				testStmt = realConn.prepareStatement(sql);
				testStmt.setInt(1, reimburse.getRequestId());
				testStmt.setInt(2, reimburse.getEmployeeId());
				testStmt.setString(3, reimburse.getLocation());
				testStmt.setDouble(4, reimburse.getCost());
				testStmt.setString(5, reimburse.getType().toString());
				testStmt.setString(6, reimburse.getDescription());
				testStmt.setString(7, reimburse.getJustificiation());
				testStmt.executeUpdate();

				testStmt = realConn.prepareStatement(sql2);
				testStmt.setInt(1, reimburse.getRequestId());
				testStmt.setDouble(2, reimburse.getProjected());
				testStmt.setBoolean(3, reimburse.isUrgent());
				testStmt.setString(4, reimburse.getStatus().toString());
				testStmt.setString(5, reimburse.getStage().toString());
				testStmt.setDate(6, Date.valueOf(LocalDate.from(reimburse.getDateTime())));
				testStmt.setTime(7, Time.valueOf(LocalTime.from(reimburse.getDateTime())));
				testStmt.executeUpdate();

				realConn.commit();
				realConn.setAutoCommit(true);

			} catch (SQLException e) {
				fail("SQLException thrown in test setup: " + e);
			}

			sql = "SELECT * FROM reimbursement WHERE request_id = ?;";
			sql2 ="SELECT * FROM reimburse_status WHERE request_id = ?;";

			//Call helper method to initilize mockito spy with test-unique sql
			try {
				initStmtHelper(sql);
				initStmtHelper2(sql2);
			} catch (SQLException e) {
				fail("SQLException thrown in test setup: " + e);
			}

			//Test readRequest
			try {
				ReimburseRequest resultRequest = reimburseDao.readRequest(reimburse.getEmployeeId());

				verify(connection).setAutoCommit(false);

				verify(spy).setInt(1, reimburse.getRequestId());
				verify(spy).executeUpdate();
				
				verify(spy2).setInt(1, reimburse.getRequestId());
				verify(spy2).executeUpdate();

				verify(connection).commit();

				assertTrue("Object returned does not match expected object", reimburse.equals(resultRequest));

			} catch (SQLException e) {
				fail("SQLException thrown in creation process: " + e);
			} 

		} finally {
			//Removal process, post-test
			try {
				testStmt = realConn.prepareStatement("DELETE FROM reimbursement WHERE request_id = ?;");
				testStmt.setInt(1, reimburse.getRequestId());
				testStmt.executeUpdate();
			} catch (SQLException e) {
				fail("TEST ERROR, could not properly remove reimbursement request: " + e);
			}
		}
	}

	@Test
	public void readAllRequestsForTest() {
		
		Integer numRequests = -1;
		Integer numStatus = -1;

		//Get current num of reimbursement requests and status' to be read
		String sql = "SELECT COUNT(*) FROM reimbursement WHERE emp_id = ?;";
		String sql2 ="SELECT COUNT(*) FROM reimburse_status WHERE request_id IN " +
									"(SELECT request_id FROM reimbursement WHERE emp_id = ?);";
		try{
			testStmt = realConn.prepareStatement(sql);
			testStmt.setInt(1, reimburse.getEmployeeId());
			ResultSet rs = testStmt.executeQuery();
			rs.next();
			numRequests = rs.getInt(1);

			testStmt = realConn.prepareStatement(sql2);
			testStmt.setInt(1, reimburse.getEmployeeId());
			rs = testStmt.executeQuery();
			rs.next();
			numStatus = rs.getInt(1);

			assertTrue("Initial count shows requests' and status' counts not lining up", numRequests == numStatus);

		} catch (SQLException e) {
			fail("SQLException thrown in test setup: " + e);
		}

		//Call helper method to initilize mockito spy with test-unique sql
		sql = "SELECT * FROM reimbursement WHERE emp_id = ?;";
		sql2 ="SELECT * FROM reimburse_status WHERE request_id IN " +
								"(SELECT request_id FROM reimbursement WHERE emp_id = ?);";
		try {
			initStmtHelper(sql);
			initStmtHelper2(sql2);
		} catch (SQLException e) {
			fail("SQLException thrown in test setup: " + e);
		}

		//Test readAllRequestsFor
		try {
			List<ReimburseRequest> requests = reimburseDao.readAllRequestsFor(reimburse.getEmployeeId());

			//Verify statement was excuted properly
			verify(spy).setInt(1, reimburse.getEmployeeId());
			verify(spy).executeQuery();

			verify(spy2).setInt(1, reimburse.getEmployeeId());
			verify(spy2).executeQuery();

			//Verify result set returned proper data
			assertTrue("Returned set is not the same size as expected", numRequests == requests.size());
			for (ReimburseRequest r : requests){
				assertFalse("request_id returned 0 for request with description: " + r.getDescription(), r.getRequestId() == 0);
				assertFalse("emp_id returned 0 for request #"+ r.getRequestId(), r.getEmployeeId() == 0);
				assertFalse("event location returned blank for request #"+ r.getRequestId(), "".equals(r.getLocation()));
				assertFalse("event cost returned 0 for request #"+ r.getRequestId(), r.getCost() == 0);
				assertFalse("event type returned blank for request #"+ r.getRequestId(), "".equals(r.getType().toString()));
				assertFalse("description returned blank for request #"+ r.getRequestId(), "".equals(r.getDescription()));
				assertFalse("justifiction returned blank for request #"+ r.getRequestId(), "".equals(r.getJustificiation()));
				assertFalse("request status returned blank for request #"+ r.getRequestId(), "".equals(r.getStatus().toString()));
				assertFalse("request stage returned blank for request #"+ r.getRequestId(), "".equals(r.getStage().toString()));
				assertFalse("dateTime wrong type for request #"+ r.getRequestId(), LocalDateTime.class.equals(r.getDateTime().getClass()));
			}
		} catch (SQLException e) {
			fail("SQLException thrown while reading related reimbursement requests");
		}
	}

	@Test
	public void readAllRequestsTest() {
		
		Integer numRequests = -1;
		Integer numStatus = -1;

		//Get current num of reimbursement requests and status' to be read
		String sql = "SELECT COUNT(*) FROM reimbursement;";
		String sql2 ="SELECT COUNT(*) FROM reimburse_status;";
		try{
			testStmt = realConn.prepareStatement(sql);
			ResultSet rs = testStmt.executeQuery();
			rs.next();
			numRequests = rs.getInt(1);

			testStmt = realConn.prepareStatement(sql2);
			rs = testStmt.executeQuery();
			rs.next();
			numStatus = rs.getInt(1);

			assertTrue("Initial count shows requests' and status' counts not lining up", numRequests == numStatus);

		} catch (SQLException e) {
			fail("SQLException thrown in test setup: " + e);
		}

		//Call helper method to initilize mockito spy with test-unique sql
		sql = "SELECT * FROM reimbursement;";
		sql2 ="SELECT * FROM reimburse_status;";
		try {
			initStmtHelper(sql);
			initStmtHelper2(sql2);
		} catch (SQLException e) {
			fail("SQLException thrown in test setup: " + e);
		}

		//Test readAllRequestsFor
		try {
			List<ReimburseRequest> requests = reimburseDao.readAllRequestsFor(reimburse.getEmployeeId());

			//Verify statement was excuted properly
			verify(spy).executeQuery();
			verify(spy2).executeQuery();

			//Verify result set returned proper data
			assertTrue("Returned set is not the same size as expected", numRequests == requests.size());
			for (ReimburseRequest r : requests){
				assertFalse("request_id returned 0 for request with description: " + r.getDescription(), r.getRequestId() == 0);
				assertFalse("emp_id returned 0 for request #"+ r.getRequestId(), r.getEmployeeId() == 0);
				assertFalse("event location returned blank for request #"+ r.getRequestId(), "".equals(r.getLocation()));
				assertFalse("event cost returned 0 for request #"+ r.getRequestId(), r.getCost() == 0);
				assertFalse("event type returned blank for request #"+ r.getRequestId(), "".equals(r.getType().toString()));
				assertFalse("description returned blank for request #"+ r.getRequestId(), "".equals(r.getDescription()));
				assertFalse("justifiction returned blank for request #"+ r.getRequestId(), "".equals(r.getJustificiation()));
				assertFalse("request status returned blank for request #"+ r.getRequestId(), "".equals(r.getStatus().toString()));
				assertFalse("request stage returned blank for request #"+ r.getRequestId(), "".equals(r.getStage().toString()));
				assertFalse("dateTime wrong type for request #"+ r.getRequestId(), LocalDateTime.class.equals(r.getDateTime().getClass()));
			}
		} catch (SQLException e) {
			fail("SQLException thrown while reading related reimbursement requests");
		}
	}

	@Test
	public void updateRequestTest() {
		try {
			//Insert test reimbursement request to be updated
			String sql = "INSERT INTO reimbursement VALUES (?,?,?,?,?,?,?);";
			String sql2 ="INSERT INTO reimburse_status VALUES (?,?,?,?,?,?,?);";

			try {
				realConn.setAutoCommit(false);

				testStmt = realConn.prepareStatement(sql);
				testStmt.setInt(1, reimburse.getRequestId());
				testStmt.setInt(2, reimburse.getEmployeeId());
				testStmt.setString(3, reimburse.getLocation());
				testStmt.setDouble(4, reimburse.getCost());
				testStmt.setString(5, reimburse.getType().toString());
				testStmt.setString(6, reimburse.getDescription());
				testStmt.setString(7, reimburse.getJustificiation());
				testStmt.executeUpdate();

				testStmt = realConn.prepareStatement(sql2);
				testStmt.setInt(1, reimburse.getRequestId());
				testStmt.setDouble(2, reimburse.getProjected());
				testStmt.setBoolean(3, reimburse.isUrgent());
				testStmt.setString(4, reimburse.getStatus().toString());
				testStmt.setString(5, reimburse.getStage().toString());
				testStmt.setDate(6, Date.valueOf(LocalDate.from(reimburse.getDateTime())));
				testStmt.setTime(7, Time.valueOf(LocalTime.from(reimburse.getDateTime())));
				testStmt.executeUpdate();

				realConn.commit();
				realConn.setAutoCommit(true);

			} catch (SQLException e) {
				fail("SQLException thrown in test setup: " + e);
			}

			//Call helper method to initilize mockito spy with test-unique sql
			sql = "UPDATE reimbursement SET ev_location = ?, ev_cost = ?, ev_type = ?, description = ?, "
										  + "justification = ? WHERE request_id = ?;";
			sql2 ="UPDATE reimburse_status SET projected_award = ?, urgent = ?, status = ?, stage = ?, "
											 + "request_date = ?, request_time = ? WHERE request_id = ?;";
			try {
				initStmtHelper(sql);
				initStmtHelper2(sql2);
			} catch (SQLException e) {
				fail("SQLException thrown in test setup: " + e);
			}

			//Test updateRequest
			try {
				//Modify values
				reimburse.setLocation("Somewhere over the rainbow");
				reimburse.setCost(200.00);
				reimburse.setType(EventType.OTHER);
				reimburse.setDescription("Just a event with a pot o' gold at the end");
				reimburse.setJustificiation("Being able to find gold means earning more money!");
				reimburse.setProjected(0.00);
				reimburse.setUrgent(false);
				reimburse.setStatus(AppStatus.APPROVED);
				reimburse.setStage(AppStage.EVENT);
				reimburse.setDateTime(LocalDateTime.MAX);

				reimburseDao.updateRequest(reimburse);

				verify(connection).setAutoCommit(false);

				//Verify properly updated reimbursement table
				verify(spy).setString(1, reimburse.getLocation());
				verify(spy).setDouble(2, reimburse.getCost());
				verify(spy).setString(3, reimburse.getType().toString());
				verify(spy).setString(4, reimburse.getDescription());
				verify(spy).setString(5, reimburse.getJustificiation());
				verify(spy).setInt(6, reimburse.getRequestId());
				verify(spy).executeUpdate();

				//Verify properly updated reimburse_status table
				verify(spy2).setDouble(1, reimburse.getProjected());
				verify(spy2).setBoolean(2, reimburse.isUrgent());
				verify(spy2).setString(3, reimburse.getStatus().toString());
				verify(spy2).setString(4, reimburse.getStage().toString());
				verify(spy2).setDate(5, Date.valueOf(LocalDate.from(reimburse.getDateTime())));
				verify(spy2).setTime(6, Time.valueOf(LocalTime.from(reimburse.getDateTime())));
				verify(spy2).setInt(7, reimburse.getRequestId());
				verify(spy2).executeUpdate();

				verify(connection).commit();

				sql = "SELECT * FROM reimbursement WHERE request_id = ?;";
				sql2 ="SELECT * FROM reimburse_status WHERE request_id = ?);";

				testStmt = realConn.prepareStatement(sql);
				testStmt.setInt(1, reimburse.getRequestId());
				ResultSet rs = testStmt.executeQuery();
				rs.next();
	
				testStmt = realConn.prepareStatement(sql2);
				testStmt.setInt(1, reimburse.getRequestId());
				ResultSet rs2 = testStmt.executeQuery();
				rs2.next();
				
				ReimburseRequest modRequest;
				modRequest = new ReimburseRequest(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDouble(4), 
												EventType.valueOf(rs.getString(5)), rs.getString(6), rs.getString(7),
												rs2.getDouble(2), rs2.getBoolean(3), AppStatus.valueOf(rs2.getString(4)), 
												AppStage.valueOf(rs2.getString(5)), 
												LocalDateTime.of(rs2.getDate(6).toLocalDate(), rs2.getTime(7).toLocalTime()));

				assertTrue("Object returned does not match expected object", reimburse.equals(modRequest));

			} catch (SQLException e) {
				fail("SQLException thrown while attempting to update object: " + e);
			} 

		} finally {
			//Removal process, post-test
			try {
				testStmt = realConn.prepareStatement("DELETE FROM reimbursement WHERE request_id = ?;");
				testStmt.setInt(1, reimburse.getRequestId());
				testStmt.executeUpdate();
			} catch (SQLException e) {
				fail("TEST ERROR, could not properly remove reimbursement request: " + e);
			}
		}
	}

	@Test
	public void deleteRequestTest() {
		try {
			//Insert test reimbursement request to be deleted
			String sql = "INSERT INTO reimbursement VALUES (?,?,?,?,?,?,?);";
			String sql2 ="INSERT INTO reimburse_status VALUES (?,?,?,?,?,?,?);";

			try {
				realConn.setAutoCommit(false);

				testStmt = realConn.prepareStatement(sql);
				testStmt.setInt(1, reimburse.getRequestId());
				testStmt.setInt(2, reimburse.getEmployeeId());
				testStmt.setString(3, reimburse.getLocation());
				testStmt.setDouble(4, reimburse.getCost());
				testStmt.setString(5, reimburse.getType().toString());
				testStmt.setString(6, reimburse.getDescription());
				testStmt.setString(7, reimburse.getJustificiation());
				testStmt.executeUpdate();

				testStmt = realConn.prepareStatement(sql2);
				testStmt.setInt(1, reimburse.getRequestId());
				testStmt.setDouble(2, reimburse.getProjected());
				testStmt.setBoolean(3, reimburse.isUrgent());
				testStmt.setString(4, reimburse.getStatus().toString());
				testStmt.setString(5, reimburse.getStage().toString());
				testStmt.setDate(6, Date.valueOf(LocalDate.from(reimburse.getDateTime())));
				testStmt.setTime(7, Time.valueOf(LocalTime.from(reimburse.getDateTime())));
				testStmt.executeUpdate();

				realConn.commit();
				realConn.setAutoCommit(true);

			} catch (SQLException e) {
				fail("SQLException thrown in test setup: " + e);
			}

			//Call helper method to initilize mockito spy with test-unique sql
			sql = "DELETE FROM reimbursement WHERE request_id = ?;";
			try {
				initStmtHelper(sql);
			} catch (SQLException e) {
				fail("SQLException thrown in test setup: " + e);
			}

			//Test deleteRequest
			try {
				reimburseDao.deleteRequest(reimburse.getRequestId());

				verify(spy).setInt(1, reimburse.getRequestId());
				verify(spy).executeUpdate();

			} catch (SQLException e) {
				fail("SQLException thrown while attempting to update object: " + e);
			} 

		} finally {
			//Attempt to delete object that was already deleted (Should throw exception)
			try {
				testStmt = realConn.prepareStatement("DELETE FROM reimbursement WHERE request_id = ?;");
				testStmt.setInt(1, reimburse.getRequestId());
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

	private void initStmtHelper2(String sql) throws SQLException {
		//Prep Mockito Spy
		stmt = realConn.prepareStatement(sql);
		spy = Mockito.spy(stmt);

		//Set standard connection mocking methods
		when(connUtil.createConnection()).thenReturn(connection);
		when(connection.prepareStatement(sql)).thenReturn(spy2);
	}
}

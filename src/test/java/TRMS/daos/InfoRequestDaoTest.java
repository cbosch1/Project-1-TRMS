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
import TRMS.pojos.InfoRequest;

@RunWith(MockitoJUnitRunner.class)
public class InfoRequestDaoTest {

	@Mock
	private ConnectionUtil connUtil;
	@Mock
	private Connection connection;

	private Connection realConn;
	private PreparedStatement stmt;
	private PreparedStatement testStmt;
	private PreparedStatement spy;

	private InfoRequestDao infoDao;
	private InfoRequest info;

    @BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		realConn = new ConnectionUtil().createConnection();
		infoDao = new InfoRequestDaoPostgres(connUtil);
		info = new InfoRequest(2010, 1, 0, true, "Urgent request for information needs approval!", LocalDateTime.of(2020, 11, 15, 15, 30));
	}

	@After
	public void tearDown() throws Exception {
		if (stmt != null){
			stmt.close();
		}
		realConn.close();
	}

	@Test
	public void createInfoRequestTest() {
		try {
			//Prep statement with proper SQL
			String sql = "INSERT INTO info_request VALUES (Default,?,?,?,?,?,?) RETURNING info_id;";

			//Call helper method to initialize mockito spy with test-unique sql
			try {
				initStmtHelper(sql);
			} catch (SQLException e) {
				fail("SQLException thrown in test setup: " + e);
			}

			//Test createEmployee
			try {
				int returnId = infoDao.createInfoRequest(info);
				info.setInfoId(returnId);

				verify(spy).setInt(1, info.getRelatedId());
				verify(spy).setInt(2, info.getDestinationId());
				verify(spy).setBoolean(3, info.getUrgent());
				verify(spy).setString(4, info.getDescription());
				verify(spy).setDate(5, Date.valueOf(LocalDate.from(info.getDateTime())));
				verify(spy).setTime(6, Time.valueOf(LocalTime.from(info.getDateTime())));

				verify(spy).executeQuery();

				assertTrue("returned id does not match expected", returnId > 0);

			} catch (SQLException e) {
				fail("SQLException thrown in creation process: " + e);
			} 

		} finally {
			//Removal process, post-test
			try {
				testStmt = realConn.prepareStatement("DELETE FROM info_request WHERE info_id = ?;");
				testStmt.setInt(1, info.getInfoId());
				testStmt.executeUpdate();
			} catch (SQLException e) {
				fail("TEST ERROR, could not properly remove info request: " + e);
			}
		}
	}

	@Test
	public void readInfoRequestTest() {
		try {
			//Insert test info request to be read
			String sql = "INSERT INTO info_request VALUES (?,?,?,?,?,?,?);";
			
			try {
				testStmt = realConn.prepareStatement(sql);
				testStmt.setInt(1, info.getInfoId());
				testStmt.setInt(2, info.getRelatedId());
				testStmt.setInt(3, info.getDestinationId());
				testStmt.setBoolean(4, info.getUrgent());
				testStmt.setString(5, info.getDescription());
				testStmt.setDate(6, Date.valueOf(LocalDate.from(info.getDateTime())));
				testStmt.setTime(7, Time.valueOf(LocalTime.from(info.getDateTime())));
				assertTrue("Error in inserting info request", 1 == testStmt.executeUpdate());
			} catch (SQLException e){
				fail("SQLException thrown in test setup: " + e);
			}

			//Prep statement with proper SQL
			sql = "SELECT * FROM info_request WHERE info_id = ?;";
			try {
				initStmtHelper(sql);
			} catch (SQLException e){
				fail("SQLException thrown while utilizing helper method");
			}

			try {
				InfoRequest resultInfo = infoDao.readInfoRequest(info.getInfoId());

				//Verify statement was prepared and executed properly
				verify(spy).setInt(1, info.getInfoId());
				verify(spy).executeQuery();

				assertTrue("Object returned does not match expected object", info.equals(resultInfo));

			} catch(SQLException e) {
				fail("SQLException thrown while attempting to retrieve object: " + e);
			}
		} finally {
			//Removal process, post-test
			try {
				testStmt = realConn.prepareStatement("DELETE FROM info_request WHERE info_id = ?;");
				testStmt.setInt(1, info.getInfoId());
				testStmt.executeUpdate();
			} catch (SQLException e) {
				fail("TEST ERROR, could not properly remove info request: " + e);
			}
		}
	}

	@Test
	public void readAllInfoForTest() {

		Integer numRelated = -1;

		//Get current num of related infos in Database for Verification
		String sql = "SELECT COUNT(*) FROM info_request WHERE destination_id = ?;";
		try{
			testStmt = realConn.prepareStatement(sql);
			testStmt.setInt(1, info.getDestinationId());
			ResultSet rs = testStmt.executeQuery();
			rs.next();
			numRelated = rs.getInt(1);
		} catch (SQLException e) {
			fail("SQLException thrown in test setup: " + e);
		}

		//Prep statement with proper SQL
		sql = "SELECT * FROM info_request WHERE destination_id = ?;";
		try {
			initStmtHelper(sql);
		} catch (SQLException e) {
			fail("SQLException thrown: " + e);
		}

		//Test readRelatedReference functionality
		try {
			List<InfoRequest> related = infoDao.readAllInfoFor(info.getDestinationId());

			//Verify statement was executed properly
			verify(spy).setInt(1, info.getDestinationId());
			verify(spy).executeQuery();

			//Verify result set returned proper data
			assertTrue("Returned set is not the same size as expected", numRelated == related.size());
			for (InfoRequest i : related){
				assertFalse("info_id returned 0 for InfoRequest with description: "+ i.getDescription(), 0 == i.getInfoId());
				assertFalse("related_id returned 0 for InfoRequest: "+ i.getInfoId(), 0 == i.getRelatedId());
				assertFalse("destination_id returned 0 for InfoRequest: "+ i.getInfoId(), 0 == i.getDestinationId());
				assertFalse("description returned empty for InfoRequest: "+ i.getInfoId(), "".equals(i.getDescription()));
				assertFalse("dateTime wrong type for InfoRequest: "+ i.getInfoId(), LocalDateTime.class.equals(i.getDateTime().getClass()));
			}
		} catch (SQLException e) {
			fail("SQLException thrown while reading related InfoRequests");
		}
	}

	@Test
	public void readAllInfoReqTest() {

		Integer numInfo = -1;

		//Get current num of related infos in Database for Verification
		String sql = "SELECT COUNT(*) FROM info_request;";
		try{
			testStmt = realConn.prepareStatement(sql);
			ResultSet rs = testStmt.executeQuery();
			rs.next();
			numInfo = rs.getInt(1);
		} catch (SQLException e) {
			fail("SQLException thrown in test setup: " + e);
		}

		//Prep statement with proper SQL
		sql = "SELECT * FROM info_request;";
		try {
			initStmtHelper(sql);
		} catch (SQLException e) {
			fail("SQLException thrown: " + e);
		}

		//Test readAllInfoReq functionality
		try {
			List<InfoRequest> infos = infoDao.readAllInfoReq();

			//Verify statement was executed properly
			verify(spy).executeQuery();

			//Verify result set returned proper data
			assertTrue("Returned set is not the same size as expected", numInfo == infos.size());
			for (InfoRequest i : infos){
				assertFalse("info_id returned 0 for InfoRequest with description: "+ i.getDescription(), 0 == i.getInfoId());
				assertFalse("related_id returned 0 for InfoRequest: "+ i.getInfoId(), 0 == i.getRelatedId());
				assertFalse("destination_id returned 0 for InfoRequest: "+ i.getInfoId(), 0 == i.getDestinationId());
				assertFalse("description returned empty for InfoRequest: "+ i.getInfoId(), "".equals(i.getDescription()));
				assertTrue("dateTime wrong type for InfoRequest: "+ i.getInfoId(), LocalDateTime.class.equals(i.getDateTime().getClass()));
			}
		} catch (SQLException e) {
			fail("SQLException thrown while reading all InfoRequests");
		}
	}

	@Test
	public void updateInfoRequestTest() {
		try {
			//Insert test info request to be updated
			String sql = "INSERT INTO info_request VALUES (?,?,?,?,?,?,?);";
			
			try {
				testStmt = realConn.prepareStatement(sql);
				testStmt.setInt(1, info.getInfoId());
				testStmt.setInt(2, info.getRelatedId());
				testStmt.setInt(3, info.getDestinationId());
				testStmt.setBoolean(4, info.getUrgent());
				testStmt.setString(5, info.getDescription());
				testStmt.setDate(6, Date.valueOf(LocalDate.from(info.getDateTime())));
				testStmt.setTime(7, Time.valueOf(LocalTime.from(info.getDateTime())));
				assertTrue("Error in inserting info request", 1 == testStmt.executeUpdate());
			} catch (SQLException e){
				fail("SQLException thrown in test setup: " + e);
			}

			//Prep statement with proper SQL
			sql = "UPDATE info_request SET urgent = ?, description = ?, request_date = ?, request_time = ? WHERE info_id = ?;";
			try {
				initStmtHelper(sql);
			} catch (SQLException e){
				fail("SQLException thrown while utilizing helper method");
			}

			//Test updateEmployee
			try {
				//Modify values
				info.setUrgent(false);
				info.setDescription("This info actually wasn't that urgent");
				info.setDateTime(LocalDateTime.of(2019, 10, 18, 20, 0));

				infoDao.updateInfoRequest(info);
				
				//Verify statement was prepared and executed properly
				verify(spy).setBoolean(1, info.getUrgent());
				verify(spy).setString(2, info.getDescription());
				verify(spy).setDate(3, Date.valueOf(LocalDate.from(info.getDateTime())));
				verify(spy).setTime(4, Time.valueOf(LocalTime.from(info.getDateTime())));
				verify(spy).setInt(5, info.getInfoId());

				verify(spy).executeUpdate();

				//Pull modified employee object from database for comparison
				testStmt = realConn.prepareStatement("SELECT * FROM info_request WHERE info_id = ?;");
				testStmt.setInt(1, info.getInfoId());
				ResultSet rs = testStmt.executeQuery();

				rs.next();
				InfoRequest modInfo = new InfoRequest(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getBoolean(4),
													  rs.getString(5), LocalDateTime.of(rs.getDate(6).toLocalDate(), rs.getTime(7).toLocalTime()));

				assertTrue("Database object does not match as modified", info.equals(modInfo));

			} catch(SQLException e) {
				fail("SQLException thrown while attempting to update object: " + e);
			}
		} finally {
			//Removal process, post-test
			try {
				testStmt = realConn.prepareStatement("DELETE FROM info_request WHERE info_id = ?;");
				testStmt.setInt(1, info.getInfoId());
				testStmt.executeUpdate();
			} catch (SQLException e) {
				fail("TEST ERROR, could not properly remove info request: " + e);
			}
		}
	}

	@Test
	public void deleteInfoRequestTest() {
		try {
			//Insert test info request to be deleted
			String sql = "INSERT INTO info_request VALUES (?,?,?,?,?,?,?);";
			
			try {
				testStmt = realConn.prepareStatement(sql);
				testStmt.setInt(1, info.getInfoId());
				testStmt.setInt(2, info.getRelatedId());
				testStmt.setInt(3, info.getDestinationId());
				testStmt.setBoolean(4, info.getUrgent());
				testStmt.setString(5, info.getDescription());
				testStmt.setDate(6, Date.valueOf(LocalDate.from(info.getDateTime())));
				testStmt.setTime(7, Time.valueOf(LocalTime.from(info.getDateTime())));
				assertTrue("Error in inserting info request", 1 == testStmt.executeUpdate());
			} catch (SQLException e){
				fail("SQLException thrown in test setup: " + e);
			}

			//Prep statement with proper SQL
			sql = "DELETE FROM info_request WHERE info_id = ?;";
			try {
				initStmtHelper(sql);
			} catch (SQLException e){
				fail("SQLException thrown while utilizing helper method");
			}

			//Test deleteEmployee
			try {
				infoDao.deleteInfoRequest(info.getInfoId());

				verify(spy).setInt(1, info.getInfoId());
				verify(spy).executeUpdate();


			} catch(SQLException e) {
				fail("SQLException thrown while attempting to delete object: " + e);
			}
		} finally {
			//Attempt to delete object that was already deleted (Should throw exception)
			try {
				testStmt = realConn.prepareStatement("DELETE FROM info_request WHERE info_id = ?;");
				testStmt.setInt(1, info.getInfoId());
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

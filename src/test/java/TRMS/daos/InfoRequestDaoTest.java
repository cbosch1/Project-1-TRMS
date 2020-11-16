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
		info = new InfoRequest(2010, 2010, 2010, true, "Urgent request for information needs approval!", LocalDateTime.of(2020, 11, 15, 15, 30));
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
			String sql = "INSERT INTO info_request VALUES (?,?,?,?,?,?,?);";

			//Call helper method to initilize mockito spy with test-unique sql
			try {
				initStmtHelper(sql);
			} catch (SQLException e) {
				fail("SQLException thrown in test setup: " + e);
			}

			//Test createEmployee
			try {
				infoDao.createInfoRequest(info);

				verify(spy).setInt(1, info.getInfoId());
				verify(spy).setInt(2, info.getRelatedId());
				verify(spy).setInt(3, info.getDestinationId());
				verify(spy).setBoolean(4, info.getUrgent());
				verify(spy).setString(5, info.getDescription());
				verify(spy).setDate(6, Date.valueOf(LocalDate.from(info.getDateTime())));
				verify(spy).setTime(7, Time.valueOf(LocalTime.from(info.getDateTime())));

				verify(spy).executeUpdate();

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
		fail("Not yet implemented");
	}

	@Test
	public void readAllInfoForTest() {
		fail("Not yet implemented");
	}

	@Test
	public void readAllInfoReqTest() {
		fail("Not yet implemented");
	}

	@Test
	public void updateInfoRequestTest() {
		fail("Not yet implemented");
	}

	@Test
	public void deleteInfoRequestTest() {
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

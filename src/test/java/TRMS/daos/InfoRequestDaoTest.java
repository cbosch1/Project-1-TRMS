package TRMS.daos;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
		fail("Not yet implemented");
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

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

import TRMS.pojos.Attachment;
import TRMS.util.ConnectionUtil;

@RunWith(MockitoJUnitRunner.class)
public class AttachmentDaoTest {

	@Mock
	private ConnectionUtil connUtil;
	@Mock
	private Connection connection;

	private Connection realConn;
	private PreparedStatement stmt;
	private PreparedStatement testStmt;
	private PreparedStatement spy;

	private AttachmentDaoPostgres attachDao;
	private Attachment attach;

    
    @BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		realConn = new ConnectionUtil().createConnection();
		attachDao = new AttachmentDaoPostgres(connUtil);
		attach = new Attachment(2010, 2010, "College Transcripts");
	}

	@After
	public void tearDown() throws Exception {
		if (stmt != null){
			stmt.close();
		}
		realConn.close();
	}

	@Test
	public void createAttachmentTest() {
		try {
			//Prep statement with proper SQL
			String sql = "INSERT INTO attachment VALUES (?,?,?,?);";

			//Call helper method to initilize mockito spy with test-unique sql
			try {
				initStmtHelper(sql);
			} catch (SQLException e) {
				fail("SQLException thrown in test setup: " + e);
			}

			//Test createEmployee
			try {
				attachDao.createAttachment(attach);

				verify(spy).setInt(1, attach.getAttachId());
				verify(spy).setInt(2, attach.getRequestId());
				verify(spy).setString(3, attach.getFileType());
				//TODO: Figure out how to test stored files

				verify(spy).executeUpdate();

			} catch (SQLException e) {
				fail("SQLException thrown in creation process: " + e);
			} 

		} finally {
			//Removal process, post-test
			try {
				testStmt = realConn.prepareStatement("DELETE FROM attachment WHERE attach_id = ?;");
				testStmt.setInt(1, attach.getAttachId());
				testStmt.executeUpdate();
			} catch (SQLException e) {
				fail("TEST ERROR, could not properly remove attachment: " + e);
			}
		}
	}

	@Test
	public void readAttachmentTest() {
		fail("Not yet implemented");
	}

	@Test
	public void readRelatedReferencesTest() {
		fail("Not yet implemented");
	}
	
	@Test
	public void deleteAttachmentTest() {
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

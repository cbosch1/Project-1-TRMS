package TRMS.daos;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
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
		attach = new Attachment(-1, 0, "College Transcripts");
		byte b[] = new byte[1];
		b[0] = 26;
		attach.setData(b);
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
			String sql = "INSERT INTO attachment VALUES (Default,?,?,?) RETURNING attach_id;";

			//Call helper method to initialize mockito spy with test-unique sql
			try {
				initStmtHelper(sql);
			} catch (SQLException e) {
				fail("SQLException thrown in test setup: " + e);
			}

			//Test createEmployee
			try {
				int returnId = attachDao.createAttachment(attach);
				attach.setAttachId(returnId);
				
				verify(spy).setInt(1, attach.getRequestId());
				verify(spy).setString(2, attach.getFileType());
				verify(spy).setBytes(3, attach.getData());

				verify(spy).executeQuery();

				assertTrue("returned id does not match expected", returnId > 0);

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
		try {
			//Insert test attachment to be read
			String sql = "INSERT INTO attachment VALUES (?,?,?,?);";
			
			try {
				testStmt = realConn.prepareStatement(sql);
				testStmt.setInt(1, attach.getAttachId());
				testStmt.setInt(2, attach.getRequestId());
				testStmt.setString(3, attach.getFileType());
				testStmt.setBytes(4, attach.getData());
				assertTrue("Error in inserting attachment", 1 == testStmt.executeUpdate());
			} catch (SQLException e){
				fail("SQLException thrown in test setup: " + e);
			}

			//Prep statement with proper SQL
			sql = "SELECT * FROM attachment WHERE attach_id = ?;";
			try {
				initStmtHelper(sql);
			} catch (SQLException e){
				fail("SQLException thrown while utilizing helper method");
			}

			try {
				Attachment resultAtt = attachDao.readAttachment(attach.getAttachId());

				//Verify statement was prepared and executed properly
				verify(spy).setInt(1, attach.getAttachId());
				verify(spy).executeQuery();

				assertTrue("Object returned does not match expected object", attach.equals(resultAtt));
			} catch(SQLException e) {
				fail("SQLException thrown while attempting to retrieve object: " + e);
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
	public void readRelatedReferenceTest() {
		
		Integer numRelated = -1;

		//Get current num of related References in Database for Verification
		String sql = "SELECT COUNT(*) FROM attachment WHERE request_id = ?;";
		try{
			testStmt = realConn.prepareStatement(sql);
			testStmt.setInt(1, attach.getRequestId());
			ResultSet rs = testStmt.executeQuery();
			rs.next();
			numRelated = rs.getInt(1);
		} catch (SQLException e) {
			fail("SQLException thrown in test setup: " + e);
		}

		//Prep statement with proper SQL
		sql = "SELECT * FROM attachment WHERE request_id = ?;";
		try {
			initStmtHelper(sql);
		} catch (SQLException e) {
			fail("SQLException thrown: " + e);
		}

		//Test readRelatedReference functionality
		try {
			List<Attachment> related = attachDao.readRelatedReference(attach.getRequestId());

			//Verify statement was executed properly
			verify(spy).setInt(1, attach.getRequestId());
			verify(spy).executeQuery();

			//Verify result set returned proper data
			assertTrue("Returned set is not the same size as expected", numRelated == related.size());
			for (Attachment r : related){
				assertFalse("attachId returned 0 for related attachment ", 0 == r.getAttachId());
				assertFalse("requestId returned 0 for related attachment ", 0 == r.getRequestId());
				assertFalse("type returned empty for related attachment ", "".equals(r.getFileType()));
			}
		} catch (SQLException e) {
			fail("SQLException thrown while reading all employees");
		}
	}
	
	@Test
	public void deleteAttachmentTest() {
		try {
			//Insert test attachment to be deleted
			String sql = "INSERT INTO attachment VALUES (?,?,?,?);";
			
			try {
				testStmt = realConn.prepareStatement(sql);
				testStmt.setInt(1, attach.getAttachId());
				testStmt.setInt(2, attach.getRequestId());
				testStmt.setString(3, attach.getFileType());
				testStmt.setBytes(4, attach.getData());
				assertTrue("Error in inserting attachment", 1 == testStmt.executeUpdate());
			} catch (SQLException e){
				fail("SQLException thrown in test setup: " + e);
			}

			//Prep statement with proper SQL
			sql = "DELETE FROM attachment WHERE attach_id = ?;";
			try {
				initStmtHelper(sql);
			} catch (SQLException e){
				fail("SQLException thrown while utilizing helper method");
			}

			try {
				attachDao.deleteAttachment(attach.getAttachId());

				//Verify statement was prepared and executed properly
				verify(spy).setInt(1, attach.getAttachId());
				verify(spy).executeUpdate();

			} catch(SQLException e) {
				fail("SQLException thrown while attempting to delete object: " + e);
			}
		} finally {
			//Attempt to delete object that was already deleted (Should throw exception)
			try {
				testStmt = realConn.prepareStatement("DELETE FROM attachment WHERE attach_id = ?;");
				testStmt.setInt(1, attach.getAttachId());
				assertEquals("Object was not deleted properly", 0, testStmt.executeUpdate());
			} catch (SQLException e) {}
		}
	}

	@Test
	public void updateAttachmentTest(){
		try {
			//Insert test attachment to be deleted
			String sql = "INSERT INTO attachment VALUES (?,?,?,?);";
			
			try {
				testStmt = realConn.prepareStatement(sql);
				testStmt.setInt(1, attach.getAttachId());
				testStmt.setInt(2, attach.getRequestId());
				testStmt.setString(3, attach.getFileType());
				testStmt.setBytes(4, attach.getData());
				assertTrue("Error in inserting attachment", 1 == testStmt.executeUpdate());
			} catch (SQLException e){
				fail("SQLException thrown in test setup: " + e);
			}

			//Prep statement with proper SQL
			sql = "UPDATE attachment SET request_id=?, file_type=?, file=? WHERE attach_id = ?;";
			try {
				initStmtHelper(sql);
			} catch (SQLException e){
				fail("SQLException thrown while utilizing helper method");
			}

			//Test updateAttachment
			try {
				//Modify values
				byte c[] = new byte[1];
				c[0] = 0;
				attach.setData(c);
				attach.setFileType("This file sux");

				assertTrue("Dao returned false", attachDao.updateAttachment(attach));
				
				//Verify statement was prepared and executed properly
				verify(spy).setInt(1, attach.getRequestId());
				verify(spy).setString(2, attach.getFileType());
				verify(spy).setBytes(3, attach.getData());
				verify(spy).setInt(4, attach.getAttachId());

				verify(spy).executeUpdate();

				//Pull modified attachment object from database for comparison
				testStmt = realConn.prepareStatement("SELECT * FROM attachment WHERE attach_id = ?;");
				testStmt.setInt(1, attach.getAttachId());
				ResultSet rs = testStmt.executeQuery();

				rs.next();
				Attachment modAttach = new Attachment(rs.getInt(1), rs.getInt(2), rs.getString(3));
				try {
					modAttach.setData(rs.getBinaryStream(4).readAllBytes());
				} catch (IOException e) {
					fail("Error thrown getting byte array");
				}

				assertTrue("Database object does not match as modified", attach.equals(modAttach));

			} catch(SQLException e) {
				fail("SQLException thrown while attempting to update object: " + e);
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

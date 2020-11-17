package TRMS.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionUtilTest {

	private ConnectionUtil ConnectionUtil;
            
    @BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		ConnectionUtil = new ConnectionUtil();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void createConnectionTest() {
        try {
			Connection conn = ConnectionUtil.createConnection();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        } 
	}
}

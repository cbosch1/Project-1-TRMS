package TRMS.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@RunWith(MockitoJUnitRunner.class)
public class Log4jTesting {

    private static final Logger Log = LogManager.getLogger("Controller");
     
    @BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void basicRootAppenderTest() {
        Log.info("This is an error message");
        Log.error("This is an error message");
		fail("Not yet implemented");
	}
}

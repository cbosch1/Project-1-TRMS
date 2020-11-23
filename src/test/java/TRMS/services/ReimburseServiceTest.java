package TRMS.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import TRMS.daos.ReimburseRequestDao;
import TRMS.enums.AppStage;
import TRMS.enums.AppStatus;
import TRMS.enums.EventType;
import TRMS.pojos.ReimburseRequest;

@RunWith(MockitoJUnitRunner.class)
public class ReimburseServiceTest {

	@Mock
	private ReimburseRequestDao mockDao;

	private ReimburseRequestService serviceToTest;
	private ReimburseRequest reimburse;
            
    @BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		serviceToTest = new ReimburseServiceImpl(mockDao);
		reimburse = new ReimburseRequest(2010, 0, "Knowhere", 1000.00, EventType.OTHER, "Instruction on the retrieval of artifacts", 
										"The boss said for me to take this", 1000.00, true, AppStatus.PENDING, AppStage.EVENT,
										LocalDateTime.of(2301, 8, 12, 4, 0));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void createRequestTest() {
		try {
			reimburse.setRequestId(0);
			when(mockDao.createRequest(reimburse)).thenReturn(reimburse.getRequestId());

			assertTrue("createInfoRequest returned false", 
				serviceToTest.createRequest(reimburse.getEmployeeId(), reimburse.getLocation(), reimburse.getCost(), 
											reimburse.getType(), reimburse.getDescription(), reimburse.getJustification(), 
											reimburse.getProjected(), reimburse.isUrgent(), reimburse.getStatus(), 
											reimburse.getStage(), reimburse.getDateTime()) == reimburse.getRequestId());

			verify(mockDao).createRequest(reimburse);

		} catch (SQLException e) {
			fail("SQLException thrown by create method: " + e);
		} catch (Exception e) {
			fail("Exception thrown by create method: " + e);
		}
	}

	@Test
	public void readRequestTest() {
		try {
			when(mockDao.readRequest(reimburse.getRequestId())).thenReturn(reimburse);

			ReimburseRequest returned = serviceToTest.readRequest(reimburse.getRequestId());

			verify(mockDao).readRequest(reimburse.getRequestId());

			assertTrue("Object read does not match expected", reimburse.equals(returned));

		} catch (SQLException e) {
			fail("SQLException thrown by read method: " + e);
		} catch (Exception e) {
			fail("Exception thrown by read method: " + e);
		}
	}

	@Test
	public void readAllRequestsForTest() {
		try {
			List<ReimburseRequest> reimburses = new ArrayList<>();
			reimburses.add(reimburse);

			when(mockDao.readAllRequestsFor(reimburse.getEmployeeId())).thenReturn(reimburses);

			List<ReimburseRequest> returned = serviceToTest.readAllRequestsFor(reimburse.getEmployeeId());

			verify(mockDao).readAllRequestsFor(reimburse.getEmployeeId());

			assertTrue("Objects read do not match expected", reimburses.equals(returned));

		} catch (SQLException e) {
			fail("SQLException thrown by read all method: " + e);
		} catch (Exception e) {
			fail("Exception thrown by read all method: " + e);
		}
	}

	@Test
	public void readAllRequestsTest() {
		try {
			List<ReimburseRequest> reimburses = new ArrayList<>();
			reimburses.add(reimburse);

			when(mockDao.readAllRequests()).thenReturn(reimburses);

			List<ReimburseRequest> returned = serviceToTest.readAllRequests();

			verify(mockDao).readAllRequests();

			assertTrue("Objects read do not match expected", reimburses.equals(returned));

		} catch (SQLException e) {
			fail("SQLException thrown by read all method: " + e);
		} catch (Exception e) {
			fail("Exception thrown by read all method: " + e);
		}
	}

	@Test
	public void updateRequestTest() {
		try {
			when(mockDao.updateRequest(reimburse)).thenReturn(true);

			assertTrue("updateInfoRequest returned false", 
				serviceToTest.updateRequest(reimburse.getRequestId(), reimburse.getEmployeeId(), reimburse.getLocation(), 
											reimburse.getCost(), reimburse.getType(), reimburse.getDescription(), 
											reimburse.getJustification(), reimburse.getProjected(), reimburse.isUrgent(), 
											reimburse.getStatus(), reimburse.getStage(), reimburse.getDateTime()));

			verify(mockDao).updateRequest(reimburse);

		} catch (SQLException e) {
			fail("SQLException thrown by update method: " + e);
		} catch (Exception e) {
			fail("Exception thrown by update method: " + e);
		}
	}

	@Test
	public void deleteRequestTest() {
		try {
			when(mockDao.deleteRequest(reimburse.getRequestId())).thenReturn(true);

			assertTrue("deleteInfoRequest returned false", serviceToTest.deleteRequest(reimburse.getRequestId()));

			verify(mockDao).deleteRequest(reimburse.getRequestId());

		} catch (SQLException e) {
			fail("SQLException thrown by delete method: " + e);
		} catch (Exception e) {
			fail("Exception thrown by delete method: " + e);
		}
	}
}

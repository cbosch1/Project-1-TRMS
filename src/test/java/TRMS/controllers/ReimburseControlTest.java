package TRMS.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import TRMS.enums.*;
import TRMS.pojos.ReimburseRequest;
import TRMS.services.ReimburseServiceImpl;
import io.javalin.http.Context;

@RunWith(MockitoJUnitRunner.class)
public class ReimburseControlTest {

	@Mock
	private ReimburseServiceImpl mockService;
	@Mock
	private Context mockCtx;

	private ReimburseRequestControl controlToTest;
	private ReimburseRequest request;
     
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		controlToTest = new ReimburseRequestControl(mockService);
		request = new ReimburseRequest(2010, 0, "Knowhere", 1000.00, EventType.OTHER, "Instruction on the retrieval of artifacts", 
										"The boss said for me to take this", 1000.00, true, AppStatus.PENDING, AppStage.EVENT,
										LocalDateTime.of(2301, 8, 12, 4, 0));

		when(mockCtx.formParam("requestId")).thenReturn(Integer.toString(request.getRequestId()));
		when(mockCtx.formParam("employeeId")).thenReturn(Integer.toString(request.getEmployeeId()));
		when(mockCtx.formParam("location")).thenReturn(request.getLocation());
		when(mockCtx.formParam("cost")).thenReturn(Double.toString(request.getCost()));
		when(mockCtx.formParam("type")).thenReturn(request.getType().toString());
		when(mockCtx.formParam("description")).thenReturn(request.getDescription());
		when(mockCtx.formParam("justification")).thenReturn(request.getJustification());
		when(mockCtx.formParam("projected")).thenReturn(Double.toString(request.getProjected()));
		when(mockCtx.formParam("urgent")).thenReturn(Boolean.toString(request.isUrgent()));
		when(mockCtx.formParam("status")).thenReturn(request.getStatus().toString());
		when(mockCtx.formParam("stage")).thenReturn(request.getStage().toString());
		when(mockCtx.formParam("dateTime")).thenReturn(request.getDateTime().toString());

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void createRequestTest() {
		try {
			controlToTest.createRequest(mockCtx);

			verify(mockCtx).formParam("employeeId");
			verify(mockCtx).formParam("location");
			verify(mockCtx).formParam("cost");
			verify(mockCtx).formParam("type");
			verify(mockCtx).formParam("description");
			verify(mockCtx).formParam("justification");
			verify(mockCtx).formParam("projected");
			verify(mockCtx).formParam("urgent");
			verify(mockCtx).formParam("status");
			verify(mockCtx).formParam("stage");
			verify(mockCtx).formParam("dateTime");

			verify(mockService).createRequest(request.getEmployeeId(), request.getLocation(), request.getCost(), request.getType(),
											request.getDescription(), request.getJustification(), request.getProjected(), 
											request.isUrgent(), request.getStatus(), request.getStage(), request.getDateTime());

			//TODO verify ctx being given proper inputs.
			
			verify(mockCtx).status(200);

		} catch (Exception e) {
			fail("Exception thrown during create test: " + e);
		}
	}

	@Test
	public void readRequestTest() {
		try {
			controlToTest.readRequest(mockCtx);

			verify(mockCtx).formParam("requestId");
			verify(mockService).readRequest(request.getRequestId());

			//TODO verify ctx being given proper inputs.

			verify(mockCtx).status(200);
		
		} catch (Exception e) {
			fail("Exception thrown during read test: " + e);
		}
	}

	@Test
	public void readAllRequestsForTest() {
		try {
			List<ReimburseRequest> requests = new ArrayList<>();
			requests.add(request);

			when(mockService.readAllRequestsFor(request.getEmployeeId())).thenReturn(requests);

			controlToTest.readAllRequestsFor(mockCtx);

			verify(mockCtx).formParam("employeeId");
			verify(mockService).readAllRequestsFor(request.getEmployeeId());

			//TODO verify ctx being given proper inputs.

			verify(mockCtx).status(200);
		
		} catch (Exception e) {
			fail("Exception thrown during read all for test: " + e);
		}
	}

	@Test
	public void readAllRequestsTest() {
		try {
			List<ReimburseRequest> requests = new ArrayList<>();
			requests.add(request);

			when(mockService.readAllRequests()).thenReturn(requests);

			controlToTest.readAllRequests(mockCtx);
			verify(mockService).readAllRequests();

			//TODO verify ctx being given proper inputs.

			verify(mockCtx).status(200);
		
		} catch (Exception e) {
			fail("Exception thrown during read all test: " + e);
		}
	}

	@Test
	public void updateRequestTest() {
		try {
			when(mockService.updateRequest(request.getRequestId(), request.getEmployeeId(), request.getLocation(), 
											request.getCost(), request.getType(), request.getDescription(), 
											request.getJustification(), request.getProjected(), request.isUrgent(), 
											request.getStatus(), request.getStage(), request.getDateTime())).thenReturn(true);

			controlToTest.updateRequest(mockCtx);
	
			verify(mockCtx).formParam("requestId");
			verify(mockCtx).formParam("employeeId");
			verify(mockCtx).formParam("location");
			verify(mockCtx).formParam("cost");
			verify(mockCtx).formParam("type");
			verify(mockCtx).formParam("description");
			verify(mockCtx).formParam("justification");
			verify(mockCtx).formParam("projected");
			verify(mockCtx).formParam("urgent");
			verify(mockCtx).formParam("status");
			verify(mockCtx).formParam("stage");
			verify(mockCtx).formParam("dateTime");

			verify(mockService).updateRequest(request.getRequestId(), request.getEmployeeId(), request.getLocation(), 
											request.getCost(), request.getType(), request.getDescription(), 
											request.getJustification(), request.getProjected(), request.isUrgent(), 
											request.getStatus(), request.getStage(), request.getDateTime());

			//TODO verify ctx being given proper inputs.

			verify(mockCtx).status(200);
	
			} catch (Exception e) {
				fail("Exception thrown during update test: " + e);
			}
	}

	@Test
	public void deleteRequestTest() {
		try {
			when(mockService.deleteRequest(request.getRequestId())).thenReturn(true);

			controlToTest.deleteRequest(mockCtx);

			verify(mockCtx).formParam("requestId");
			verify(mockService).deleteRequest(request.getRequestId());

			//TODO verify ctx being given proper inputs.

			verify(mockCtx).status(200);
		
		} catch (Exception e) {
			fail("Exception thrown during delete test: " + e);
		}
	}
}
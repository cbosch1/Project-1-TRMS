package TRMS.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import TRMS.services.AttachmentService;
import TRMS.services.ReimburseServiceImpl;
import io.javalin.http.Context;

@RunWith(MockitoJUnitRunner.class)
public class ReimburseControlTest {

	@Mock
	private ReimburseServiceImpl mockService;
	@Mock
	private AttachmentService mockAttach;
	@Mock
	private AuthControl mockAuth;
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
		controlToTest = new ReimburseRequestControl(mockService, mockAuth, mockAttach);
		// cSpell:ignore Knowhere
		request = new ReimburseRequest(2010, 0, "Knowhere", 1000.00, EventType.OTHER, "Instruction on the retrieval of artifacts", 
										"The boss said for me to take this", "pass/fail", 300.00, false, AppStatus.PENDING, AppStage.SUPERVISOR,
										LocalDateTime.of(2301, 8, 12, 4, 0));

		when(mockAuth.getEmp(mockCtx)).thenReturn(request.getEmployeeId());
		when(mockCtx.formParam("event-location")).thenReturn(request.getLocation());
		when(mockCtx.formParam("event-cost")).thenReturn(Double.toString(request.getCost()));
		when(mockCtx.formParam("event-type")).thenReturn(request.getType().toString());
		when(mockCtx.formParam("description")).thenReturn(request.getDescription());
		when(mockCtx.formParam("justification")).thenReturn(request.getJustification());
		when(mockCtx.formParam("grading")).thenReturn(request.getGrading());
		when(mockCtx.formParam("urgency")).thenReturn("false");
		when(mockCtx.formParam("event-date")).thenReturn(LocalDate.from(request.getDateTime()).toString());
		when(mockCtx.formParam("event-time")).thenReturn(LocalTime.from(request.getDateTime()).toString());

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void createRequestTest() {
		try {
			controlToTest.createRequest(mockCtx);

			verify(mockAuth).getEmp(mockCtx);
			verify(mockCtx).formParam("event-location");
			verify(mockCtx).formParam("event-cost");
			verify(mockCtx).formParam("event-type");
			verify(mockCtx).formParam("description");
			verify(mockCtx).formParam("justification");
			verify(mockCtx).formParam("grading");
			verify(mockCtx).formParam("urgency");
			verify(mockCtx).formParam("event-date");
			verify(mockCtx).formParam("event-time");

			verify(mockService).createRequest(request.getEmployeeId(), request.getLocation(), request.getCost(), request.getType(),
											request.getDescription(), request.getJustification(), request.getGrading(), request.getProjected(), 
											request.isUrgent(), request.getStatus(), request.getStage(), request.getDateTime());
			
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
											request.getJustification(), request.getGrading(), request.getProjected(), request.isUrgent(), 
											request.getStatus(), request.getStage(), request.getDateTime())).thenReturn(true);
	
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
											request.getJustification(), request.getGrading(), request.getProjected(), request.isUrgent(), 
											request.getStatus(), request.getStage(), request.getDateTime());

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

			verify(mockCtx).status(200);
		
		} catch (Exception e) {
			fail("Exception thrown during delete test: " + e);
		}
	}
}
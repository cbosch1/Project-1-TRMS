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

import TRMS.pojos.InfoRequest;
import TRMS.services.InfoRequestService;
import io.javalin.http.Context;

@RunWith(MockitoJUnitRunner.class)
public class InfoRequestControlTest {

	@Mock
	private InfoRequestService mockService;
	@Mock
	private Context mockCtx;

	private InfoRequestControl controlToTest;
	private InfoRequest request;
  
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		controlToTest = new InfoRequestControl(mockService);
		request = new InfoRequest(2010, 1, 0, true, "Urgent request for information needs approval!", LocalDateTime.of(2020, 11, 15, 15, 30));

		when(mockCtx.formParam("infoId")).thenReturn(Integer.toString(request.getInfoId()));
		when(mockCtx.formParam("relatedId")).thenReturn(Integer.toString(request.getRelatedId()));
		when(mockCtx.formParam("destinationId")).thenReturn(Integer.toString(request.getDestinationId()));
		when(mockCtx.formParam("urgent")).thenReturn(Boolean.toString(request.getUrgent()));
		when(mockCtx.formParam("description")).thenReturn(request.getDescription());
		when(mockCtx.formParam("dateTime")).thenReturn(request.getDateTime().toString());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void createInfoRequestTest() {
		try {
			controlToTest.createInfoRequest(mockCtx);

			verify(mockCtx).formParam("relatedId");
			verify(mockCtx).formParam("destinationId");
			verify(mockCtx).formParam("urgent");
			verify(mockCtx).formParam("description");
			verify(mockCtx).formParam("dateTime");

			verify(mockService).createInfoRequest(request.getRelatedId(), request.getDestinationId(), request.getUrgent(),
													request.getDescription(), request.getDateTime());

			//TODO verify ctx being given proper inputs.
			
			verify(mockCtx).status(200);

		} catch (Exception e) {
			fail("Exception thrown during create test: " + e);
		}
	}

	@Test
	public void readInfoRequestTest() {
		try {
			controlToTest.readInfoRequest(mockCtx);

			verify(mockCtx).formParam("infoId");
			verify(mockService).readInfoRequest(request.getInfoId());

			//TODO verify ctx being given proper inputs.

			verify(mockCtx).status(200);
		
		} catch (Exception e) {
			fail("Exception thrown during read test: " + e);
		}
	}

	@Test
	public void readAllInfoForTest() {
		try {
			List<InfoRequest> requests = new ArrayList<>();
			requests.add(request);

			when(mockService.readAllInfoFor(request.getDestinationId())).thenReturn(requests);

			controlToTest.readAllInfoFor(mockCtx);
			verify(mockService).readAllInfoFor(request.getDestinationId());

			//TODO verify ctx being given proper inputs.

			verify(mockCtx).status(200);
		
		} catch (Exception e) {
			fail("Exception thrown during read all for test: " + e);
		}
	}

	@Test
	public void readAllInfoReqTest() {
		try {
			List<InfoRequest> requests = new ArrayList<>();
			requests.add(request);

			when(mockService.readAllInfoReq()).thenReturn(requests);

			controlToTest.readAllInfoReq(mockCtx);
			verify(mockService).readAllInfoReq();

			//TODO verify ctx being given proper inputs.

			verify(mockCtx).status(200);
		
		} catch (Exception e) {
			fail("Exception thrown during read all test: " + e);
		}
	}

	@Test
	public void updateInfoRequestTest() {
		try {
			controlToTest.updateInfoRequest(mockCtx);
	
			verify(mockCtx).formParam("relatedId");
			verify(mockCtx).formParam("destinationId");
			verify(mockCtx).formParam("urgent");
			verify(mockCtx).formParam("description");
			verify(mockCtx).formParam("dateTime");

			verify(mockService).updateInfoRequest(request.getInfoId(), request.getRelatedId(), request.getDestinationId(), 
													request.getUrgent(), request.getDescription(), request.getDateTime());
			
			//TODO verify ctx being given proper inputs.

			verify(mockCtx).status(200);
	
			} catch (Exception e) {
				fail("Exception thrown during update test: " + e);
			}
	}

	@Test
	public void deleteInfoRequestTest() {
		try {
			controlToTest.deleteInfoRequest(mockCtx);

			verify(mockCtx).formParam("requestId");
			verify(mockService).deleteInfoRequest(request.getInfoId());

			//TODO verify ctx being given proper inputs.

			verify(mockCtx).status(200);
		
		} catch (Exception e) {
			fail("Exception thrown during delete test: " + e);
		}
	}
}
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

import TRMS.daos.InfoRequestDao;
import TRMS.pojos.InfoRequest;

@RunWith(MockitoJUnitRunner.class)
public class InfoRequestServiceTest {

	@Mock
	private InfoRequestDao mockDao;

	private InfoRequestService serviceToTest;
	private InfoRequest info;
            
    @BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		serviceToTest = new InfoRequestServiceImpl(mockDao);
		info = new InfoRequest(2010, 0, 0, false, "I need more details concerning the benefits of this event", LocalDateTime.of(2001, 12, 12, 12, 0));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void createInfoRequestTest() {
		try {
			info.setInfoId(0);
			when(mockDao.createInfoRequest(info)).thenReturn(info.getInfoId());

			assertTrue("createInfoRequest returned false", 
				serviceToTest.createInfoRequest(info.getRelatedId(), info.getDestinationId(), info.getUrgent(), 
												info.getDescription(), info.getDateTime()) == info.getInfoId());

			verify(mockDao).createInfoRequest(info);

		} catch (SQLException e) {
			fail("SQLException thrown by create method: " + e);
		} catch (Exception e) {
			fail("Exception thrown by create method: " + e);
		}
	}

	@Test
	public void readInfoRequestTest() {
		try {
			when(mockDao.readInfoRequest(info.getInfoId())).thenReturn(info);

			InfoRequest returned = serviceToTest.readInfoRequest(info.getInfoId());

			verify(mockDao).readInfoRequest(info.getInfoId());

			assertTrue("Object read does not match expected", info.equals(returned));

		} catch (SQLException e) {
			fail("SQLException thrown by read method: " + e);
		} catch (Exception e) {
			fail("Exception thrown by read method: " + e);
		}
	}

	@Test
	public void readAllInfoForTest() {
		try {
			List<InfoRequest> infos = new ArrayList<>();
			infos.add(info);

			when(mockDao.readAllInfoFor(info.getInfoId())).thenReturn(infos);

			List<InfoRequest> returned = serviceToTest.readAllInfoFor(info.getInfoId());

			verify(mockDao).readAllInfoFor(info.getInfoId());

			assertTrue("Objects read do not match expected", infos.equals(returned));

		} catch (SQLException e) {
			fail("SQLException thrown by read all method: " + e);
		} catch (Exception e) {
			fail("Exception thrown by read all method: " + e);
		}
	}

	@Test
	public void readAllInfoReqTest() {
		try {
			List<InfoRequest> infos = new ArrayList<>();
			infos.add(info);

			when(mockDao.readAllInfoReq()).thenReturn(infos);

			List<InfoRequest> returned = serviceToTest.readAllInfoReq();

			verify(mockDao).readAllInfoReq();

			assertTrue("Objects read do not match expected", infos.equals(returned));

		} catch (SQLException e) {
			fail("SQLException thrown by read all method: " + e);
		} catch (Exception e) {
			fail("Exception thrown by read all method: " + e);
		}
	}

	@Test
	public void updateInfoRequestTest() {
		try {
			when(mockDao.updateInfoRequest(info)).thenReturn(true);

			assertTrue("updateInfoRequest returned false", 
				serviceToTest.updateInfoRequest(info.getInfoId(), info.getRelatedId(), info.getDestinationId(), 
												info.getUrgent(), info.getDescription(), info.getDateTime()));

			verify(mockDao).updateInfoRequest(info);

		} catch (SQLException e) {
			fail("SQLException thrown by update method: " + e);
		} catch (Exception e) {
			fail("Exception thrown by update method: " + e);
		}
	}

	@Test
	public void deleteInfoRequestTest() {
		try {
			when(mockDao.deleteInfoRequest(info.getInfoId())).thenReturn(true);

			assertTrue("deleteInfoRequest returned false", serviceToTest.deleteInfoRequest(info.getInfoId()));

			verify(mockDao).deleteInfoRequest(info.getInfoId());

		} catch (SQLException e) {
			fail("SQLException thrown by delete method: " + e);
		} catch (Exception e) {
			fail("Exception thrown by delete method: " + e);
		}
	}
}

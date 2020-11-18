package TRMS.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
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

import TRMS.daos.AttachmentDao;
import TRMS.pojos.Attachment;

@RunWith(MockitoJUnitRunner.class)
public class AttachmentServiceTest {

	@Mock
	private AttachmentDao mockDao;

	private AttachmentService attachServ;
	private Attachment attach;
        
    @BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		attachServ = new AttachmentServiceImpl(mockDao);
		attach = new Attachment(2010, 1, "College Transcripts");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void createAttachmentTest() {
		try {
			assertTrue("createAttachment returned false", 
						attachServ.createAttachment(attach.getAttachId(), attach.getRequestId(), 
													attach.getFileType(), attach.getData()) == attach.getAttachId());

			verify(mockDao).createAttachment(attach);

		} catch (SQLException e) {
			fail("SQLException thrown by createAttachment: " + e);
		} catch (Exception e) {
			fail("Exception thrown by createAttachment: " + e);
		}
	}

	@Test
	public void readAttachmentTest() {
		try {
			when(mockDao.readAttachment(attach.getAttachId())).thenReturn(attach);

			Attachment readAtt = attachServ.readAttachment(attach.getAttachId());

			assertTrue("Object read does not match expected", attach.equals(readAtt));

		} catch (SQLException e) {
			fail("SQLException thrown by createAttachment: " + e);
		} catch (Exception e) {
			fail("Exception thrown by createAttachment: " + e);
		}
	}

	@Test
	public void readRelatedReferencesTest() {
		try {
			List<Integer> references = new ArrayList<>();
			references.add(1);
			references.add(2);

			when(mockDao.readRelatedReference(attach.getRequestId())).thenReturn(references);

			List<Integer> foundRefs = attachServ.readRelatedReferences(attach.getRequestId());

			assertTrue("Objects read do not match expected", references.equals(foundRefs));

		} catch (SQLException e) {
			fail("SQLException thrown by createAttachment: " + e);
		} catch (Exception e) {
			fail("Exception thrown by createAttachment: " + e);
		}
	}

	@Test
	public void updateAttachmentTest() {
		try {
			assertTrue("updateAttachment returned false", 
						attachServ.updateAttachment(attach.getAttachId(), attach.getRequestId(), 
														attach.getFileType(), attach.getData()));

			verify(mockDao).deleteAttachment(attach.getAttachId());
			verify(mockDao).createAttachment(attach);

		} catch (SQLException e) {
			fail("SQLException thrown by createAttachment: " + e);
		} catch (Exception e) {
			fail("Exception thrown by createAttachment: " + e);
		}
	}
	
	@Test
	public void deleteAttachmentTest() {
		fail("Not yet implemented");
	}
}

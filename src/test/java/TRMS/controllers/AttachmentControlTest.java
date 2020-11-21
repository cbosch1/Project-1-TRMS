package TRMS.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import TRMS.pojos.Attachment;
import TRMS.services.AttachmentService;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;

@RunWith(MockitoJUnitRunner.class)
public class AttachmentControlTest {

	@Mock
	private AttachmentService mockService;
	@Mock
	private Context mockCtx;
	@Mock
	private UploadedFile mockFile;

	private AttachmentControl controlToTest;
	private Attachment attach;
	private File file;
	private FileInputStream stream;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		controlToTest = new AttachmentControl(mockService);
		attach = new Attachment(2010, 1, "College Transcripts");
		file = new File("src\\test\\resources\\fileTest.txt");
		stream = new FileInputStream(file);

		when(mockFile.getContent()).thenReturn(stream);

		when(mockCtx.formParam("attachId")).thenReturn(Integer.toString(attach.getAttachId()));
		when(mockCtx.formParam("requestId")).thenReturn(Integer.toString(attach.getRequestId()));
		when(mockCtx.formParam("fileType")).thenReturn(attach.getFileType());
		when(mockCtx.uploadedFile("file")).thenReturn(mockFile);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void verifyFileInput() {
		try {
			java.util.Scanner s = new java.util.Scanner(stream).useDelimiter("\\A");
			String result = s.hasNext() ? s.next() : "";
			assertEquals("If you are reading this in the application, Good Job!", result);
			s.close();
		} catch (Exception e) {
			fail("File input threw an exception: " + e);
		}
	}


	@Test
	public void createAttachmentTest() {
		try {
			controlToTest.createAttachment(mockCtx);

			verify(mockCtx).formParam("attachId");
			verify(mockCtx).formParam("requestId");
			verify(mockCtx).formParam("fileType");
			verify(mockCtx).uploadedFile("file");

			verify(mockService).createAttachment(attach.getAttachId(), attach.getRequestId(), 
												attach.getFileType(), stream);

			//TODO verify ctx being given proper inputs.
			
			verify(mockCtx).status(200);

		} catch (Exception e) {
			fail("Exception thrown during create test: " + e);
		}
	}

	@Test
	public void createAttachmentNoIdTest() {
		try {
			when(mockCtx.formParam("attachId")).thenReturn("");

			controlToTest.createAttachment(mockCtx);

			verify(mockCtx).formParam("attachId");
			verify(mockCtx).formParam("requestId");
			verify(mockCtx).formParam("fileType");
			verify(mockCtx).uploadedFile("file");
			verify(mockService).createAttachment(attach.getRequestId(), attach.getFileType(), stream);

			//TODO verify ctx being given proper inputs.
			
			verify(mockCtx).status(200);

		} catch (Exception e) {
			fail("Exception thrown during create test: " + e);
		}
	}

	@Test
	public void readAttachmentTest() {
		try {
			controlToTest.readAttachment(mockCtx);

			verify(mockCtx).formParam("attachId");
			verify(mockService).readAttachment(attach.getAttachId());

			//TODO verify ctx being given proper inputs.

			verify(mockCtx).status(200);

		} catch (Exception e) {
			fail("Exception thrown during read test: " + e);
		}
	}

	@Test
	public void readRelatedReferencesTest() {
		try {
			controlToTest.readRelatedReferences(mockCtx);

			verify(mockCtx).formParam("requestId");
			verify(mockService).readRelatedReferences(attach.getRequestId());

			//TODO verify ctx being given proper inputs.

			verify(mockCtx).status(200);

		} catch (Exception e) {
			fail("Exception thrown during read related test: " + e);
		}
	}

	@Test
	public void updateAttachmentTest() {
		try {
			controlToTest.updateAttachment(mockCtx);

			verify(mockCtx).formParam("attachId");
			verify(mockCtx).formParam("requestId");
			verify(mockCtx).formParam("fileType");
			verify(mockCtx).uploadedFile("file");
			verify(mockService).updateAttachment(attach.getAttachId(), attach.getRequestId(), 
												attach.getFileType(), stream);

			//TODO verify ctx being given proper inputs.

			verify(mockCtx).status(200);

		} catch (Exception e) {
			fail("Exception thrown during update test: " + e);
		}
	}
	
	@Test
	public void deleteAttachmentTest() {
		try {
			controlToTest.deleteAttachment(mockCtx);

			verify(mockCtx).formParam("attachId");
			verify(mockService).deleteAttachment(attach.getAttachId());

			//TODO verify ctx being given proper inputs.

			verify(mockCtx).status(200);

		} catch (Exception e) {
			fail("Exception thrown during delete test: " + e);
		}
	}
}
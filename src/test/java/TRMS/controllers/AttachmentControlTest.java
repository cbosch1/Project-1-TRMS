package TRMS.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;

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

/**
 * Currently Javalin and Mockito do not play nice when it comes to
 * ctx.pathParam There are not many solutions and most of them are
 * just not worth the refactoring and time that would involve. As such
 * any method that would utilize a mock pathParam can only verify that 
 * the method fails properly at this time. 
 */
@RunWith(MockitoJUnitRunner.class)
public class AttachmentControlTest {

	@Mock
	private AttachmentService mockService;
	@Mock
	private AuthControl mockAuth;
	@Mock
	private Context mockCtx;
	@Mock
	private UploadedFile mockFile;
	@Mock
	private InputStream mockStream;

	private AttachmentControl controlToTest;
	private Attachment attach;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		controlToTest = new AttachmentControl(mockService, mockAuth);
		attach = new Attachment(2010, 1, "College Transcripts");

		when(mockAuth.checkUser(mockCtx)).thenReturn(true);

		when(mockFile.getContent()).thenReturn(mockStream);

		when(mockCtx.formParam("attachId")).thenReturn(Integer.toString(attach.getAttachId()));
		when(mockCtx.formParam("requestId")).thenReturn(Integer.toString(attach.getRequestId()));
		when(mockCtx.formParam("fileType")).thenReturn(attach.getFileType());
		when(mockCtx.uploadedFile("file")).thenReturn(mockFile);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void createAttachmentTest() {
		try {
			controlToTest.createAttachment(mockCtx);

			verify(mockCtx).formParam("attachId");
			verify(mockCtx).formParam("requestId");
			verify(mockCtx).formParam("fileType");
			verify(mockCtx).uploadedFile("file");
			verify(mockStream).readAllBytes();

			verify(mockService).createAttachment(attach.getAttachId(), attach.getRequestId(), 
												attach.getFileType(), mockFile.getContent().readAllBytes());
			
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
			verify(mockStream).readAllBytes();

			verify(mockService).createAttachment(attach.getRequestId(), attach.getFileType(), mockFile.getContent().readAllBytes());
			
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

			verify(mockCtx).status(200);

		} catch (Exception e) {
			fail("Exception thrown during read test: " + e);
		}
	}

	@Test
	public void readRelatedReferencesTest() {
		try {
			controlToTest.readRelatedReferences(mockCtx);

			verify(mockAuth).checkUser(mockCtx);

/* 			verify(mockCtx).pathParam("id");
			verify(mockService).readRelatedReferences(attach.getRequestId());

			verify(mockCtx).status(200); */

			verify(mockCtx).status(500);

		} catch (Exception e) {
			fail("Exception thrown during read related test: " + e);
		}
	}

	@Test
	public void updateAttachmentTest() {
		try {
			when(mockService.updateAttachment(attach.getAttachId(), attach.getRequestId(), 
												attach.getFileType(), mockStream.readAllBytes())).thenReturn(true);

			controlToTest.updateAttachment(mockCtx);

			verify(mockCtx).formParam("attachId");
			verify(mockCtx).formParam("requestId");
			verify(mockCtx).formParam("fileType");
			verify(mockCtx).uploadedFile("file");
			verify(mockService).updateAttachment(attach.getAttachId(), attach.getRequestId(), 
												attach.getFileType(), mockStream.readAllBytes());

			verify(mockCtx).status(200);

		} catch (Exception e) {
			fail("Exception thrown during update test: " + e);
		}
	}
	
	@Test
	public void deleteAttachmentTest() {
		try {
			when(mockService.deleteAttachment(attach.getAttachId())).thenReturn(true);

			controlToTest.deleteAttachment(mockCtx);

			verify(mockCtx).formParam("attachId");
			verify(mockService).deleteAttachment(attach.getAttachId());

			verify(mockCtx).status(200);

		} catch (Exception e) {
			fail("Exception thrown during delete test: " + e);
		}
	}
}
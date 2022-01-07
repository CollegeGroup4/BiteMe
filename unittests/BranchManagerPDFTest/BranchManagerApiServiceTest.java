package BranchManagerPDFTest;

import static org.junit.Assert.*;
import static org.junit.Assert.*;



import org.junit.Before;

import org.junit.runner.RunWith;
import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.any;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;
import org.mockito.invocation.InvocationOnMock;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;


import java.io.FileInputStream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.aspose.pdf.Document;
import com.aspose.pdf.Image;
import com.aspose.pdf.Page;

import ClassForTests.iDbManager;
import ClassForTests.iFileManager;

public class BranchManagerApiServiceTest {
	
	private class DbManagerStub implements iDbManager{

		@Override
		public String getCeoEmail(String email) {
			return mail;
		}

		@Override
		public void setUp() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class FileManagerStub implements iFileManager{

		@Override
		public String[] getFileListFromDir(String imageDir) {
			return fileList;
		}
		
	}
	
	private class mockDocument{
		
	}
	
	String mail;
	String[] fileList;
	
	@Mock
	Page mockPage;
	@Mock
	Image mockImage;
	@Mock
	FileInputStream mockFileInputStream;
	@Mock
	Document mockDocument;
	
	
	@Before
	void setUp() {
		mockDocument = Mockito.mock(Document.class);
		mockPage = Mockito.mock(Page.class);
		when(mockDocument.getPages().add()).thenReturn(mockPage);
	}
	
	@Test
	public void testSendPdfToCEO() {
		fail("Not yet implemented");
	}

}

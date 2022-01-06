package ClassForTests;

import com.aspose.pdf.Document;
import com.aspose.pdf.Image;
import com.aspose.pdf.Page;

public class PageManager implements iPageManager {
	public void addImageToPage(Page page, Image image1) {
		// Add the image into paragraphs collection of the section
		page.getParagraphs().add(image1);
	}

	public Page setNewPage(Document doc) {
		// Add a page to pages collection of document
		Page page = doc.getPages().add();
		// Set margins so image will fit, etc.
		page.getPageInfo().getMargin().setBottom(0);
		page.getPageInfo().getMargin().setTop(0);
		page.getPageInfo().getMargin().setLeft(0);
		page.getPageInfo().getMargin().setRight(0);
		page.setCropBox(new com.aspose.pdf.Rectangle(0, 0, 800, 600));
		return page;
	}
}

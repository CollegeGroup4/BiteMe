package ClassForTests;

import com.aspose.pdf.Document;
import com.aspose.pdf.Image;
import com.aspose.pdf.Page;

public interface iPageManager {
	public void addImageToPage(Page page, Image image1);
	public Page setNewPage(Document doc);
}

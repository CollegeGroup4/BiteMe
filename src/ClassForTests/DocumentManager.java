package ClassForTests;

import com.aspose.pdf.Document;

public class DocumentManager {
	Document doc;

	public DocumentManager(Document doc) {
		super();
		this.doc = doc;
	}

	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}
	
}

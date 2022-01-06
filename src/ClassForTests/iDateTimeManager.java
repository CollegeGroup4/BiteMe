package ClassForTests;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.aspose.pdf.Document;
import com.aspose.pdf.Image;
import com.aspose.pdf.Page;

public interface iDateTimeManager {
	public DateTimeFormatter getDateTimeFormat();

	public LocalDateTime getCurrentDateTime();
}

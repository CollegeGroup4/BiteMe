package ClassForTests;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.aspose.pdf.Document;
import com.aspose.pdf.Image;
import com.aspose.pdf.Page;

public class DateTimeManager implements iDateTimeManager {
	//toDel
	public DateTimeFormatter getDateTimeFormat() {
		DateTimeFormatter month = DateTimeFormatter.ofPattern("MM");
		return month;
	}
	//toDel - mock is needed
	public LocalDateTime getCurrentDateTime() {
		LocalDateTime now = LocalDateTime.now();
		return now;
	}
}

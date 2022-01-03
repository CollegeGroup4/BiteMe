package logic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class test {
	public static void main(String[] args) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime d1 = LocalDateTime.parse("2021-12-29 16:00", formatter);
		LocalDateTime d2 = LocalDateTime.parse("2021-12-28 23:30", formatter);
		if (d1.until(d2, ChronoUnit.NANOS) >= 120) {
			System.out.println("hi");
		}
		System.out.println(d1.until(d2, ChronoUnit.NANOS));
	}
}

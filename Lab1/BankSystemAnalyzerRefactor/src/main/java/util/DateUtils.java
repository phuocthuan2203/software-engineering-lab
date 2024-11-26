package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private static final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static LocalDate parseDate(String dateString) {
        return LocalDate.parse(dateString, DATE_PATTERN);
    }

    public static String formatDate(LocalDate date) {
        return date.format(DATE_PATTERN);
    }
}

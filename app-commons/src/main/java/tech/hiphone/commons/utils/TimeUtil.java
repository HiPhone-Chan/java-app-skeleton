package tech.hiphone.commons.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;

public class TimeUtil {

    public static Instant toInstant(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toInstant();
    }

    public static String format(LocalDate localDate, String formatString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatString);
        return localDate.format(formatter);
    }

    public static LocalDate toLocalDate(String dateString, String formatString) {
        if (StringUtils.isEmpty(dateString) || dateString.length() < 8) {
            return null;
        }
        return LocalDate.parse(dateString, DateTimeFormatter.ofPattern(formatString));
    }

}

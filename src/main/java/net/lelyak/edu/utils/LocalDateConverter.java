package net.lelyak.edu.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class LocalDateConverter {
    private final static String dateFormat = "MM/dd/yyyy h:mm a";
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);

    public static LocalDate convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }

        return LocalDate.parse(source, formatter);
    }
}
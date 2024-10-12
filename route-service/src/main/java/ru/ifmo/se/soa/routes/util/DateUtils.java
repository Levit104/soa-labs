package ru.ifmo.se.soa.routes.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);

    public static Date parse(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String format(Date date) {
        return DATE_FORMATTER.format(date);
    }
}

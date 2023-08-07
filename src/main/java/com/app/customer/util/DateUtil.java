package com.app.customer.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static final String GENERIC_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(GENERIC_FORMAT);
        return sdf.format(date);
    }

    public static Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(GENERIC_FORMAT);
        return sdf.parse(dateString);
    }
}
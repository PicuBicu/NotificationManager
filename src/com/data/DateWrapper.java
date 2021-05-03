package com.data;

import java.util.Calendar;
import java.util.Date;

public class DateWrapper extends Date {

    public static Date parseDate(String message) {
        String[] items = message.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(items[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(items[1]));
        calendar.set(Calendar.SECOND, Integer.parseInt(items[2]));
        return calendar.getTime();
    }

}

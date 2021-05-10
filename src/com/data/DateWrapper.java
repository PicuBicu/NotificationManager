package com.data;

import java.util.Calendar;
import java.util.Date;

public class DateWrapper extends Date {

    public static Date parseDate(String message) throws BadDataFormatException{
        String[] items = message.split(":");
        if (items.length == 2 && checkIfDataExists(items)) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(items[0]));
            calendar.set(Calendar.MINUTE, Integer.parseInt(items[1]));
            calendar.set(Calendar.SECOND, 0);
            return calendar.getTime();
        }
        throw new BadDataFormatException("Zły format daty");
    }

    public static boolean checkIfDataExists(String[] items) throws BadDataFormatException{
        try {
            int hours = Integer.parseInt(items[0]);
            int minutes = Integer.parseInt(items[1]);
            return hours >= 0 && hours <= 23 && minutes >= 0 && minutes <= 59;
        } catch (NumberFormatException e) {
            throw new BadDataFormatException("Data nie istnieje");
        }
    }

}

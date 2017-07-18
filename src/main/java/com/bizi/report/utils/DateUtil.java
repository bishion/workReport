package com.bizi.report.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by guofangbi on 2016/12/1.
 */
public class DateUtil {

    public static final String yyyy_MM_dd = "yyyy-MM-dd";
    public static String yyyyMMdd = "yyyyMMdd";

    public static int getWeekNo(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }
    public static Date toDate(String format){
        DateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(format);
        } catch (ParseException e) {
            return null;
        }
    }
    public static String toString(Date date,String pattern){
        return new SimpleDateFormat(pattern).format(date);
    }

    public static Date getFirstDayOfThisWeek(){
        return getFirstDayOfThatWeek(new Date());
    }
    public static Date getFirstDayOfThatWeek(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        calendar.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);

        return calendar.getTime();
    }

    public static Date fromString(String dateStr){
        DateFormat format = new SimpleDateFormat(yyyy_MM_dd);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String toString(Date date){
        DateFormat format = new SimpleDateFormat(yyyy_MM_dd);
        return format.format(date);
    }
}

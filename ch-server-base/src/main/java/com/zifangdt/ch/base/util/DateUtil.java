package com.zifangdt.ch.base.util;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 袁兵 on 2017/9/26.
 */
public class DateUtil extends DateFormatUtils {
    public static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");

    public static Date getTomorrow() {
        Calendar calendar = new Calendar.Builder().setInstant(new Date()).build();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static String normalize(Date date) {
        if (date == null) {
            return null;
        }
        Calendar now = new Calendar.Builder().setInstant(new Date()).build();
        Calendar past = new Calendar.Builder().setInstant(date).build();
        if (now.before(past)) {
            throw new IllegalArgumentException(String.format("必须指定一个过去的时间作为参数(now:%s,past:%s)", now.getTime(), past.getTime()));
        }
        if (now.get(Calendar.YEAR) - past.get(Calendar.YEAR) >= 1) {
            return format(date, "yyyy-MM-dd HH:mm");
        }
        int days = now.get(Calendar.DAY_OF_YEAR) - past.get(Calendar.DAY_OF_YEAR);
        if (days > 1) {
            return format(date, "MM-dd HH:mm");
        } else if (days == 1) {
            return "昨日 " + format(date, "HH:mm");
        } else {
            long minutes = (now.getTimeInMillis() - past.getTimeInMillis()) / 60000;
            if (minutes >= 60) {
                return format(date, "HH:mm");
            } else if (minutes < 1) {
                return "刚刚";
            } else {
                return minutes + "分钟前";
            }
        }
    }

    public static Date addDate(Date date, int field, int amount) {
        Calendar calendar = new Calendar.Builder().setInstant(date).build();
        calendar.add(field, amount);
        return calendar.getTime();
    }

    public static int get(Date date, int field) {
        Calendar calendar = new Calendar.Builder().setInstant(date).build();
        return calendar.get(field);
    }

    public static int getDay(Date date) {
        return get(date, Calendar.DAY_OF_WEEK);
    }

    public static int getDayOfMonth(Date date) {
        return get(date, Calendar.DAY_OF_MONTH);
    }

    public static boolean isSameMonth(Date date1, Date date2) {
        Calendar calendar1 = new Calendar.Builder().setInstant(date1).build();
        Calendar calendar2 = new Calendar.Builder().setInstant(date2).build();
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH);
    }

    public static boolean isMonthOf(Date date, int year, int month) {
        Calendar calendar = new Calendar.Builder().setInstant(date).build();
        return calendar.get(Calendar.YEAR) == year
                && calendar.get(Calendar.MONTH) == month - 1;
    }

    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        return DateUtils.isSameDay(date1, date2);
    }

    public static Date getFirstDayOfWeek(int year, int month, int dayOfWeek) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1);

        while (cal.get(Calendar.DAY_OF_WEEK) != dayOfWeek) {
            cal.add(Calendar.DATE, 1);
        }

        return cal.getTime();
    }

    public static boolean isSameDayOrAfter(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        return DateUtils.isSameDay(date1, date2) || date1.after(date2);
    }
}

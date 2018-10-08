package com.theworldofluster.example.ziang.tremblenumber.utils;

/**
 * Created by liupeng on 2018/9/12.
 */

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DateUtil22 {
    public static final String ymdhms = "yyyy-MM-dd HH:mm:ss";
    public static final String ymd = "yyyy-MM-dd";
    public static final String ym = "yyyy-MM";

    public DateUtil22() {
    }

    public static String monthNumToMonthName(String month) {
        String m = month;
        if("1".equals(month)) {
            m = "一月份";
        } else if("2".equals(month)) {
            m = "二月份";
        } else if("3".equals(month)) {
            m = "三月份";
        } else if("4".equals(month)) {
            m = "四月份";
        } else if("5".equals(month)) {
            m = "五月份";
        } else if("6".equals(month)) {
            m = "六月份";
        } else if("7".equals(month)) {
            m = "七月份";
        } else if("8".equals(month)) {
            m = "八月份";
        } else if("9".equals(month)) {
            m = "九月份";
        } else if("10".equals(month)) {
            m = "十月份";
        } else if("11".equals(month)) {
            m = "十一月份";
        } else if("12".equals(month)) {
            m = "十二月份";
        }

        return m;
    }

    public static List<String> getWeekBeginAndEndTime() {
        Calendar cal = Calendar.getInstance();
        if(cal.get(7) == 1) {
            cal.add(5, -1);
        }

        cal.set(7, 2);
        String beginTime = formatDate(cal.getTime().getTime(), "yyyy-MM-dd");
        String endDate = "";
        cal = Calendar.getInstance();
        if(cal.get(7) == 1) {
            endDate = formatDate(cal.getTime().getTime(), "yyyy-MM-dd");
        } else {
            cal.set(7, 7);
            cal.add(5, 1);
            endDate = formatDate(cal.getTime().getTime(), "yyyy-MM-dd");
        }

        List<String> list = new ArrayList();
        list.add(beginTime);
        list.add(endDate);
        return list;
    }

    public static List<String> getLastWeekBeginAndEndTime() {
        Calendar cal = Calendar.getInstance();
        String endTime = "";
        String beginTime = "";
        if(cal.get(7) == 1) {
            cal.set(7, 1);
            cal.set(7, 1);
            cal.add(4, -1);
            endTime = formatDate(cal.getTime().getTime(), "yyyy-MM-dd");
            cal.add(4, -1);
            cal.set(7, 2);
            beginTime = formatDate(cal.getTime().getTime(), "yyyy-MM-dd");
        } else {
            cal.set(7, 1);
            cal.set(7, 1);
            endTime = formatDate(cal.getTime().getTime(), "yyyy-MM-dd");
            cal.add(4, -1);
            cal.set(7, 2);
            beginTime = formatDate(cal.getTime().getTime(), "yyyy-MM-dd");
        }

        List<String> list = new ArrayList();
        list.add(beginTime);
        list.add(endTime);
        return list;
    }

    public static List<String> getMonthBeginAndEndTime(int month) {
        Calendar calendar = new GregorianCalendar();
        if(month != 0) {
            calendar.add(2, month);
        }

        calendar.set(5, 1);
        Date date1 = calendar.getTime();
        String beginDate = formatDate(date1.getTime(), "yyyy-MM-dd");
        calendar.set(5, calendar.getActualMaximum(5));
        Date date2 = calendar.getTime();
        String endDate = formatDate(date2.getTime(), "yyyy-MM-dd");
        List<String> list = new ArrayList();
        list.add(beginDate);
        list.add(endDate);
        return list;
    }

    public static String getTomorrow() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(5, 1);
        int year = calendar.get(1);
        int month = calendar.get(2) + 1;
        int day = calendar.get(5);
        return year + "-" + (month > 9?Integer.valueOf(month):"0" + month) + "-" + day;
    }

    public static String getDayTomorrow(int Y, int M, int D) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Y, M, D);
        calendar.add(5, 1);
        int year = calendar.get(1);
        int month = calendar.get(2) + 1;
        int day = calendar.get(5);
        return (month > 9?Integer.valueOf(month):"0" + month) + "月" + (day > 9?Integer.valueOf(day):"0" + day);
    }

    public static int getYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(1);
    }

    public static int getMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(2) + 1;
    }

    public static int getDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(5) + 1;
    }

    public static String getMonthMM() {
        int mNum = getMonth();
        String mm;
        if(mNum < 10) {
            mm = "0" + mNum;
        } else {
            mm = mNum + "";
        }

        return mm;
    }

    public static String getToday() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(1);
        int month = calendar.get(2) + 1;
        int day = calendar.get(5);
        return year + "-" + (month > 9?Integer.valueOf(month):"0" + month) + "-" + (day > 9?Integer.valueOf(day):"0" + day);
    }

    public static List<Integer> getDateForString(String date) {
        String[] dates = date.split("-");
        List<Integer> list = new ArrayList();
        list.add(Integer.valueOf(Integer.parseInt(dates[0])));
        list.add(Integer.valueOf(Integer.parseInt(dates[1])));
        list.add(Integer.valueOf(Integer.parseInt(dates[2])));
        return list;
    }

    public static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static String formatDate(String date, String format) {
        String resultD = date;
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        try {
            Date d = sdf.parse(date);
            resultD = sdf.format(d);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return resultD;
    }

    public static String formatDate(long milliseconds, String format) {
        String resultD = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        try {
            Date d = new Date(milliseconds);
            resultD = sdf.format(d);
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return resultD;
    }

    public static Date formatDateStr(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date1 = null;

        try {
            date1 = sdf.parse(date);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return date1;
    }

    public static int getMonthDays(int year, int month) {
        ++month;
        switch(month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 2:
                return (year % 4 != 0 || year % 100 == 0) && year % 400 != 0?28:29;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            default:
                return -1;
        }
    }

    public static int getFirstDayWeek(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        Log.d("DateView", "DateView:First:" + calendar.getFirstDayOfWeek());
        return calendar.get(7);
    }

    public static int getFirstDayWeek17(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        Log.d("DateView", "DateView:First:" + calendar.getFirstDayOfWeek());
        int c = calendar.get(7);
        --c;
        if(c == 0) {
            c = 7;
        }

        return c;
    }

    public static String getDayWeek(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Log.d("DateView", "DateView:First:" + calendar.getFirstDayOfWeek());
        switch(calendar.get(7)) {
            case 1:
                return "周日";
            case 2:
                return "周一";
            case 3:
                return "周二";
            case 4:
                return "周三";
            case 5:
                return "周四";
            case 6:
                return "周五";
            case 7:
                return "周六";
            default:
                return "";
        }
    }

    public static String getDayWeek(String date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(formatDateStr(date, "yyyy-MM-dd"));
        switch(calendar.get(7)) {
            case 1:
                return "周日";
            case 2:
                return "周一";
            case 3:
                return "周二";
            case 4:
                return "周三";
            case 5:
                return "周四";
            case 6:
                return "周五";
            case 7:
                return "周六";
            default:
                return "";
        }
    }

    public static String IntYYYYMMToYYYYGANGMM(int date) {
        String s = date + "";
        StringBuilder sb = new StringBuilder(s);
        sb.insert(4, "-");
        return sb.toString();
    }

    public static String getYYYY_MM_DD(int year, int month, int day) {
        String s = "" + year + "-";
        String m = "";
        String d = "";
        if(month < 10) {
            m = "0" + month;
        } else {
            m = month + "";
        }

        if(day < 10) {
            d = "0" + day;
        } else {
            d = day + "";
        }

        s = s + m + "-" + d;
        return s;
    }

    public static String getNowYYYYGANGMM() {
        int year = getYear();
        int month = getMonth();
        int z = year * 100 + month;
        return IntYYYYMMToYYYYGANGMM(z);
    }

    public static int[] getWeekDay(int Y, int M, int D) throws ParseException {
        int[] is = new int[6];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String s = Y + "-" + M + "-" + D;
        Date time = sdf.parse(s + " 14:22:47");
        cal.setTime(time);
        System.out.println("要计算日期为:" + sdf.format(cal.getTime()));
        int dayWeek = cal.get(7);
        if(1 == dayWeek) {
            cal.add(5, -1);
        }

        cal.setFirstDayOfWeek(2);
        int day = cal.get(7);
        cal.add(5, cal.getFirstDayOfWeek() - day);
        System.out.println("所在周星期一的日期：" + sdf.format(cal.getTime()));
        is[0] = Integer.parseInt(sdf.format(cal.getTime()).split("-")[0]);
        is[1] = Integer.parseInt(sdf.format(cal.getTime()).split("-")[1]);
        is[2] = Integer.parseInt(sdf.format(cal.getTime()).split("-")[2]);
        System.out.println(cal.getFirstDayOfWeek() + "-" + day + "+6=" + (cal.getFirstDayOfWeek() - day + 6));
        cal.add(5, 6);
        is[3] = Integer.parseInt(sdf.format(cal.getTime()).split("-")[0]);
        is[4] = Integer.parseInt(sdf.format(cal.getTime()).split("-")[1]);
        is[5] = Integer.parseInt(sdf.format(cal.getTime()).split("-")[2]);
        System.out.println("所在周星期日的日期：" + sdf.format(cal.getTime()));
        return is;
    }
}

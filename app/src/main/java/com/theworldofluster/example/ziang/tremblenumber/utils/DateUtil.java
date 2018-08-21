package com.theworldofluster.example.ziang.tremblenumber.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by liupeng on 2018/7/31.
 */

public class DateUtil {
    //获取上周第一天
    public static String getMonday() {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天
        cal.add(Calendar.DATE, -1*7);
        cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
        String monday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        return monday+"";
    }

    //获取上周最后一天 周日
    public static String getLastWeekSunday() {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天
        cal.add(Calendar.DATE, -1*7);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        String sunday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        return sunday+"";
    }
    public static String getSunday() {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天
//        cal.add(Calendar.DATE, -1*7);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String sunday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        return sunday+"";
    }


    //获取上周第一天
    public static String getMonday2() {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天
        cal.add(Calendar.DATE, -1*7);
        cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
        String monday = new SimpleDateFormat("MM月dd日").format(cal.getTime());
        return monday+"";
    }
    //获取上周最后一天 周日
    public static String getLastWeekSunday2() {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天
        cal.add(Calendar.DATE, -1*7);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        String sunday = new SimpleDateFormat("MM月dd日").format(cal.getTime());
        return sunday+"";
    }


    //获取上周第一天
    public static String getXiaZhouMonday() {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天
        cal.add(Calendar.DATE, 7);
        cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
        String monday = new SimpleDateFormat("MM月dd日").format(cal.getTime());
        return monday+"";
    }
}

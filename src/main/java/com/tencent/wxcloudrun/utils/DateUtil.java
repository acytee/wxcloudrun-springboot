package com.tencent.wxcloudrun.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间
 */
public class DateUtil {

    /**
     * 时间转字符串
     *
     * @param date
     * @return
     */
    public static String dateString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(date);
    }

    /**
     * 24小时制
     *
     * @param date
     * @return
     */
    public static String dateString1(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 时间转字符串
     *
     * @param date
     * @return
     */
    public static String dateString2(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        return sdf.format(date);
    }

    /**
     * 字符串转时间
     *
     * @param date
     * @return
     */
    public static Date stringDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.parse(date);
    }

    public static Date stringDate1(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(date);
    }

    /**
     * 把时间 修改为 当天 00：00：00
     *
     * @return
     */
    public static Date getMinDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 把时间 修改为 当天 23：59：59
     *
     * @return
     */
    public static Date getMaxDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 获取本月第一天
     *
     * @return
     */
    public static Date getMinMonthly(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取本月最后一天
     *
     * @return
     */
    public static Date getMaxMonthly(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 判断日期是不是今天
     *
     * @param date
     * @return
     */
    public static Boolean isToday(Date date) {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowDay = sdf.format(now);
        String dateDay = sdf.format(date);
        return dateDay.equals(nowDay);
    }

    /**
     * 计算距离当前时间 相差多久
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    public static String timeInterval(Date fromDate, Date toDate) {
        String returnMsg = null;
        Calendar from = Calendar.getInstance();
        from.setTime(fromDate);
        Calendar to = Calendar.getInstance();
        to.setTime(toDate);

        int fromYear = from.get(Calendar.YEAR);
        int fromMonth = from.get(Calendar.MONTH);
        int fromDay = from.get(Calendar.DAY_OF_MONTH);

        int toYear = to.get(Calendar.YEAR);
        int toMonth = to.get(Calendar.MONTH);
        int toDay = to.get(Calendar.DAY_OF_MONTH);
        int year = toYear - fromYear;
        int month = toMonth - fromMonth;
        int day = toDay - fromDay;

        if (year > 0) {
            returnMsg = fromYear + "年";
        } else if (month > 0) {
            returnMsg = (fromMonth + 1) + "月" + fromDay + "号";
        } else {

            Calendar c = Calendar.getInstance();
            c.setTime(fromDate);
            c.setFirstDayOfWeek(Calendar.MONDAY);
            int from_week = c.get(Calendar.WEEK_OF_MONTH);
            int from_day = c.get(Calendar.DAY_OF_WEEK);
            c.setTime(toDate);
            c.setFirstDayOfWeek(Calendar.MONDAY);
            int to_week = c.get(Calendar.WEEK_OF_MONTH);


            if (to_week == from_week) {
                if (day == 0) {
                    returnMsg = "今天";
                } else if (day == 1) {
                    returnMsg = "昨天";
                } else if (day == 2) {
                    returnMsg = "前天";
                } else {
                    String[] weeks = {"日", "一", "二", "三", "四", "五", "六"};
                    returnMsg = "星期" + weeks[from_day - 1];
                }
            } else {
                returnMsg = (fromMonth + 1) + "月" + fromDay + "号";
            }
        }

        return returnMsg;
    }


    /**
     * 对日期天数进行加减
     *
     * @param date
     * @param day
     * @return
     */
    public static Date editASDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    public static Date editASYear(Date date, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 计算两个日期差多少天
     * startDate小 相差天数为正数 反之负数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int dayBetween(Date startDate, Date endDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(endDate);
        long time2 = cal.getTimeInMillis();
        long betweenDays = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(betweenDays));
    }

    /**
     * 计算剩余多少天
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static String timeLeft(Date startDate, Date endDate) {

        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

/*
        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays,
                elapsedHours, elapsedMinutes, elapsedSeconds);
*/

        return elapsedDays + "天" + elapsedHours + "小时" + elapsedMinutes + "分钟";
    }

    /**
     * 在原时间上  加秒
     *
     * @param date
     * @param second
     * @return
     */
    public static Date addSecond(Date date, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, second);
        return calendar.getTime();
    }

    public static void main(String[] args) {

        System.out.println(DateUtil.dateString1(new Date()).substring(0, 10));

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println(sdf2.format(addSecond(new Date(), 30)));
        System.out.println(sdf2.format(getMinDay(new Date())));
        System.out.println(sdf2.format(getMaxDay(new Date())));

        Date newT = editASDay(new Date(), -12);
        Date gqT = editASDay(new Date(), 0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(gqT);
        calendar.add(Calendar.HOUR, 10);
        calendar.add(Calendar.MINUTE, 30);
        calendar.add(Calendar.SECOND, 30);
        gqT = calendar.getTime();


        System.out.println("newT当前时间：" + sdf2.format(newT));
        System.out.println("gqT过期时间：" + sdf2.format(gqT));
        System.out.println(newT.getTime());
        System.out.println(gqT.getTime());
        System.out.println(dayBetween(gqT, newT));

        if (newT.getTime() >= gqT.getTime()) {
            System.out.println("ok");
        } else {
            System.out.println("no");
        }

        System.out.println("----");
        timeLeft(gqT, newT);
    }

}

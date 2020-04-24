package com.acongfly.studyjava.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author j.tommy
 * @version 1.0
 * @date 2017/12/10
 */
public final class DateUtil {
    /**
     * 将世界标准时间转换为本地时间
     * 
     * @param gmtDate
     * @return
     */
    public static Date convertGMT2Local(Date gmtDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(gmtDate);
        int zoneOffset = c.get(Calendar.ZONE_OFFSET);
        int dstOffset = c.get(Calendar.DST_OFFSET);
        c.add(Calendar.MILLISECOND, zoneOffset + dstOffset);
        return c.getTime();
    }

    /**
     * 将世界标准时间转换为目标时区的本地时间
     * 
     * @param gmtDate
     * @param id
     * @return
     */
    public static Date convertGMTToLocal(Date gmtDate, String id) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(gmtDate);
        calendar.setTimeZone(TimeZone.getTimeZone(id));
        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
        int dstOffset = calendar.get(Calendar.DST_OFFSET);
        calendar.add(Calendar.MILLISECOND, dstOffset + zoneOffset);
        return calendar.getTime();
    }

    /**
     * 将本地时间转换为世界标准时间
     * 
     * @param date
     * @return
     */
    public static Date convertToGMT(Date date) {
        // Local Time Zone Calendar Instance
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
        int dstOffset = calendar.get(Calendar.DST_OFFSET);
        calendar.add(Calendar.MILLISECOND, -(dstOffset + zoneOffset));
        return calendar.getTime();
    }

    public static void main(String[] args) {
        String gmtDateString = "2017-12-10T04:10:01.794Z";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            Date gmtDate = sdf.parse(gmtDateString);
            Date localDate = convertGMT2Local(gmtDate);
            System.out.println(sdf2.format(localDate));
            // for (String id : TimeZone.getAvailableIDs()) {
            // System.out.println(id);
            // }
            localDate = convertGMTToLocal(gmtDate, "Asia/Hong_Kong");
            System.out.println(sdf2.format(localDate));
            gmtDate = convertToGMT(localDate);
            System.out.println(sdf.format(gmtDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
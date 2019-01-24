package com.jiazhuo.blockgamesquare.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtil {
    private DateUtil(){

    }

    /**
     * 获取两个时间的时间间隔
     * @param date1
     * @param date2
     * @return
     */
    public static long getBetweenTime(Date date1, Date date2){
        return Math.abs((date1.getTime() - date2.getTime())/1000);
    }

    /**
     * 设置一天最晚时间为23:59:59
     * @param date
     * @return
     */
    public static Date getEndTime(Date date){
        if (date != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            return calendar.getTime();
        }
        return null;
    }

    /**
     * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param dateDate
     * @return
     */
    public static String dateToStrLong(java.util.Date dateDate) {
           SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           String dateString = formatter.format(dateDate);
           return dateString;
    }
}

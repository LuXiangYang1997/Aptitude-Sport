package com.huasport.smartsport.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * 时间戳转换日期
     */
    public static String timeToDate(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");//这里的格式可换"yyyy年-MM月dd日-HH时mm分ss秒"等等格式

        String date = sf.format(calendar.getTime());
        return date;
    }

    /**
     * 获取前一天的时间
     *
     * @return
     */
    public static long frontDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1); //向前走一天
        Date date = calendar.getTime();
        long time = date.getTime();
        return time;
    }

    /**
     * 判断是否是同一天
     *
     * @param time
     * @param sameTime
     * @return
     */
    public static boolean isSameDay(long time, long sameTime) {

        Date date = new Date(time);
        Date sameDate = new Date(sameTime);

        if (null == date || null == sameDate) {

            return false;

        }

        Calendar nowCalendar = Calendar.getInstance();

        nowCalendar.setTime(sameDate);

        Calendar dateCalendar = Calendar.getInstance();

        dateCalendar.setTime(date);

        if (nowCalendar.get(Calendar.YEAR) == dateCalendar.get(Calendar.YEAR)
                && nowCalendar.get(Calendar.MONTH) == dateCalendar.get(Calendar.MONTH)
                && nowCalendar.get(Calendar.DATE) == dateCalendar.get(Calendar.DATE)) {
            return true;
        }

        return false;

    }

    /**
     * 時間戳转换成時間
     *
     * @param time
     * @return str
     */
    public static String DateToStr(long time,String pattern) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SimpleDateFormat sf = new SimpleDateFormat(pattern);//这里的格式可换"yyyy年-MM月dd日-HH时mm分ss秒"等等格式

        String date = sf.format(calendar.getTime());
        return date;
    }
}

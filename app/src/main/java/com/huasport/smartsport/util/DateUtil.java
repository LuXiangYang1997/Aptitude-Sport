package com.huasport.smartsport.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static String strformat;
    private static String format = "yyyy年MM月dd日";
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

    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     */
    public static String StrToDate(String str, String format) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = dateFormat.parse(str);

            strformat = simpleDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return strformat;
    }
    /**
     * 将2018-06-01 10：00：00 格式转为 2018年06月01日
     *
     * @param time
     * @return
     */
    public static String getDate(String time) {
        StringBuilder sb = new StringBuilder();
        if (time.contains("-")) {
            String[] times = time.split("-");
            if (times.length == 3) {
                sb.append(times[0] + "年");
                sb.append(times[1] + "月");
                sb.append(times[2].substring(0, 2) + "日");
            }
        }
        return TextUtils.isEmpty(sb.toString()) ? time : sb.toString();
    }

    public static String dateConvert(String startTime, String endTime) {

        String sTime = StrToDate(startTime, format);
        String eTime = StrToDate(endTime, format);
        String strDate = sTime + "-" + eTime;
        return strDate;
    }

}

package com.credit.diversion.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static String YYYYMMDD="YYYYMMdd";

    public static String MM_dd="MM-dd";

    /**
     * 得到几天前的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    /**
     * 获取当前时间几天前的整点时间
     * @param day
     * @return
     */
    public static Date getDateBeforeZero(Date date,int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        now.set(Calendar.SECOND,0);
        now.set(Calendar.MINUTE,0);
        now.set(Calendar.HOUR, 12);
        now.set(Calendar.MILLISECOND,0);
        return now.getTime();
    }

    public static Date getDateBeforeZero(int day) {
        return getDateBeforeZero(new Date(),day);
    }

    /**
     * 字符转时间
     * @param dateStr
     * @param form
     * @return
     */
    public static Date parse(String dateStr,String form){
        SimpleDateFormat sdf = new SimpleDateFormat(form);
        try {
            return sdf.parse(dateStr);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.err.println(getDateBeforeZero(new Date(),15));
    }
}

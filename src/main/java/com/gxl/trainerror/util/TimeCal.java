package com.gxl.trainerror.util;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class TimeCal {
    //时间计算类
   /*
   显示时间：有一个日期，显示多久以前的文件可以显示。
   （一个小时，5个小时，12个小时，24个小时，3天，7天，
     一个月，全部。默认显示5个小时）
    */
    public static Date backTime(Integer hours){

        /*
            以小时计算
         */
        Date date =new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY)-hours);
        Date date1=cal.getTime();
        return date1;
    }
    public static Date backDate(Integer day){

        /*
            以小时计算
         */
        Date date =new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)-day);
        Date date1=cal.getTime();

        return date1;
    }

//    public static void main(String[] args) {
//        TimeCal.backDate(11);
//    }
}

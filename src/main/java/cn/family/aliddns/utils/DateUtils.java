package cn.family.aliddns.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {


    /**
     * 将Timestamp转换为标准Date数据
     * @param timeInMillis  单位：秒
     * @return
     */
    public static Date convertTimestampToDate(Long timeInMillis){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(timeInMillis);
        Date formatDate = null;
        try {
            formatDate = format.parse(format.format(calendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatDate;
    }

    /**
     * 将字符类型日期转换为标准Date数据
     * @param dateTime
     * @return
     */
    public static Date convertTypeStringTimeToDate(String dateTime){
        return convertTypeStringTimeToDate(dateTime,"yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 将字符类型日期转换为Date数据,
     * @param dateTime
     * @return
     */
    public static Date convertTypeStringTimeToDate(String dateTime,String formate){
        SimpleDateFormat format = new SimpleDateFormat(formate);
        Date formatDate = null;
        try {
            formatDate = format.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatDate;
    }



    /**
     * 取得当前时间
     * @return
     */
    public static Date getCurrentDateTime(){
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = calendar.getTime();

        Date formatDate = null;
        try {
            formatDate = format.parse(format.format(currentDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formatDate;
    }

    /**
     * 取得格式化后的当前系统时间[yyyy-MM-dd HH:mm:ss]
     * @return
     */
    public static String getCurrentSytemTimeForString(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(getCurrentDateTime());
    }

    public static String getDateToString(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }


    /**
     * 取得格式化后的当前系统时间[Timestamp类型]
     * @return
     */
    public static Timestamp getCurrentSytemTimeForTimestamp(){

        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = calendar.getTime();

        Date formatDate = null;
        try {
            formatDate = format.parse(format.format(currentDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Timestamp.valueOf(format.format(formatDate));
    }

    /**
     * 取得格式化后的当前系统日期[yyyy-MM-dd]
     * @return
     */
    public static String getCurrentSytemDateForString(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(getCurrentDateTime());
    }

    /**
     * 取得格式化后的当前系统时间[字符串类型]
     * @return
     */
    public static String getCurrentSytemTimeForStringByPackagesCode(){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(getCurrentDateTime());
    }


}

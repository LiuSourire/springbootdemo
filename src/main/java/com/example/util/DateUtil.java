package com.example.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Sourire
 * 时间格式转换
 */
public class DateUtil {

    public static SimpleDateFormat getSimpleDateFormate(String pattern){
        return new SimpleDateFormat();
    }

    /**
     * 字符串转日期格式
     */
    public static Date stringtoDate(String SourceText,String pattern) throws ParseException {
        return DateUtil.getSimpleDateFormate(pattern).parse(SourceText);
    }

    /**
     * 时间格式转字符串
     */
    public static String dateToString(Date date,String pattern){
        return DateUtil.getSimpleDateFormate(pattern).format(date);
    }
}

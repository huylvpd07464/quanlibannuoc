/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duan.qlbn.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author HP
 */
public class XDate {
    static SimpleDateFormat formater = new SimpleDateFormat();

//    Chuyển đổi String sang Date
//    date là String cần chuyển đổi
//    pattern là định dạng thời gian
//    @return date là kết quả     
    public static Date toDate(String date, String pattern) {
        try {
            formater.applyPattern(pattern);
            return formater.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

//    Chuyển đổi Date sang String
//    date là Date cần chuyển đổi
//    pattern là định dạng thời gian
//    @return String là kết quả    
    public static String toString(Date date, String pattern) {
        formater.applyPattern(pattern);
        return formater.format(date);
    }

    public static Date now() {
        return new Date();
    }

//    Bổ sung số ngày vào thời gian
//            
//    date thời gian hiện có
//    days số ngày cần bổ sung vào date
//    @return Date kết quả        
    public static Date addDays(Date date, int days) {
        date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
        return date;
    }

    public static Date add(int days) {
        Date now = XDate.now();
        now.setTime(now.getTime() + days * 24 * 60 * 60 * 1000);
        return now;
    }

}

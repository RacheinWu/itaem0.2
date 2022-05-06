package com.example.itaem.untils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用于各种时间
 */
public class DateUtil {

    /**
     * @return yyyy-MM-dd HH:mm
     */
    public static String yyyy_MM_dd_HH_mm() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
    }
}

package com.adreamer.clock.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by A Dreamer on 2016/8/2.
 */
public class FormatUtils {

    private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    private static Date date = new Date();

    public static String format(int time) {
        date.setTime(time);
        return sdf.format(date);
    }
}

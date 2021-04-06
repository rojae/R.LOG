package kr.or.rlog.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {

    public static String dateTimeToYYYYMMDD(LocalDateTime time){
        if(time == null)
            return "";
        else
            return time.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }

}


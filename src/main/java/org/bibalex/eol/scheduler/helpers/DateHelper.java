package org.bibalex.eol.scheduler.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {
    public static String getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date =sdf.format(new Date());
        return date;
    }

    public static Date convertFromMillisecondsToDate(String milliseconds){
        long longMilliseconds = Long.valueOf(milliseconds);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date resultDate = new Date(longMilliseconds);
        Date finalDate =null;
        try {
            finalDate = sdf.parse(sdf.format(resultDate));
            return finalDate;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}

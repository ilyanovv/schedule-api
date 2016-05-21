package restapi;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Илья on 22.05.2016.
 */
public class TimeDate {
    public static Time getTime(String str){
        if(str.length() != 4)
            return null;
        /*CharSequence cs = str;
        String ret ="";
        ret += cs.charAt(0) + cs.charAt(1) + ":" + cs.charAt(2) + cs.charAt(3) + ":00";
        return  Time.valueOf(ret);*/
        int h = Integer.parseInt(str.substring(0, 1));
        int m = Integer.parseInt(str.substring(2, 3));
        int s = 0;
        System.err.println("time = " + str);
        return new Time(h, m, s);
    }

    public static Date getDate(String str){
        SimpleDateFormat fromUser = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String reformattedStr = myFormat.format(fromUser.parse(str));
            return Date.valueOf(reformattedStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}


package com.sterlite.dccmappfordealersterlite.Utils;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by HP-HP on 19-07-2016.
 */
public class DateTimeUtils {

    public static String parseDateTime(String dateString, String originalFormat, String outputFromat){

        SimpleDateFormat formatter = new SimpleDateFormat(originalFormat, Locale.US);
        Date date = null;
        try {
            date = formatter.parse(dateString);

            SimpleDateFormat dateFormat=new SimpleDateFormat(outputFromat, new Locale("US"));

            return dateFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getRelativeTimeSpan(String dateString, String originalFormat){

        SimpleDateFormat formatter = new SimpleDateFormat(originalFormat, Locale.US);
        Date date = null;
        try {
            date = formatter.parse(dateString);

            return DateUtils.getRelativeTimeSpanString(date.getTime()).toString();

        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    public static String getFormatedDate(Calendar date) {
        int monthCC = date.get(Calendar.MONTH)+1;
        String dateTime = date.get(Calendar.YEAR) + "" + ((monthCC < 10) ? "0" + monthCC : monthCC) + "" + date.get(Calendar.DAY_OF_MONTH);
        String dateCurrent = DateTimeUtils.parseDateTime(dateTime, "yyyyMMdd", "dd-MMM-yyyy");
        return dateCurrent;
    }

    public static String getFormatedDateTime(Calendar date) {
        int monthCC = date.get(Calendar.MONTH);
        String dateTime = date.get(Calendar.YEAR) + "" + ((monthCC < 10) ? "0" + monthCC : monthCC) + "" + date.get(Calendar.DAY_OF_MONTH)+" "+date.get(Calendar.HOUR_OF_DAY)+":"+date.get(Calendar.MINUTE);
        String dateCurrent = DateTimeUtils.parseDateTime(dateTime, "yyyyMMdd HH:mm", "dd-MMM-yyyy hh:mm a");
        return dateCurrent;
    }

    public static String getDateStringByPattern(Date date, String pattern){ SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern); String dateStr = simpleDateFormat.format(date); return dateStr; }
}

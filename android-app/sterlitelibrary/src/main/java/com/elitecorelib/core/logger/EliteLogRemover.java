package com.elitecorelib.core.logger;

import android.content.Context;
import android.os.Environment;

import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EliteLogRemover {

    private final static String MODULE = "EliteLogRemover";

    public static void doFileRemoveOperation(Context ctx) {

        EliteSession.eLog.d(MODULE, " Invoked to delete the files");

        try {
            int stringId = LibraryApplication.getLibraryApplication().getLibraryContext().getApplicationInfo().labelRes;
            String AppName = LibraryApplication.getLibraryApplication().getLibraryContext().getString(stringId);

//				String path = Environment.getExternalStorageDirectory() + File.separator + CoreConstants.LOGGER_FOLDER_NAME;

//            String path = Environment.getExternalStorageDirectory() + File.separator + CoreConstants.LOGGER_FOLDER_NAME + "_" + AppName.replace(" ", "") + File.separator;
            String path = Environment.getExternalStorageDirectory() + File.separator + AppName.replace(" ", "") + File.separator;

            EliteSession.eLog.d(MODULE + " Path: " + path);
            File f = new File(path);
            File file[] = f.listFiles();
            EliteSession.eLog.d(MODULE, " Size: " + file.length);
            for (int i = 0; i < file.length; i++) {
                EliteSession.eLog.d(MODULE, " FileName:" + file[i].getName());
                String[] split = file[i].getName().split("_");
                if (split != null && split.length > 0) {
                    EliteSession.eLog.d(MODULE, " " + split[0]);
                    Date d = null;
                    Date currentDate = null;
                    try {
                        SimpleDateFormat df = new SimpleDateFormat(CoreConstants.LOGGER_DATETIME_FILE_FORMAT);
                        d = df.parse(split[0]);
                        currentDate = new Date(System.currentTimeMillis());
                    } catch (Exception e) {
                        // TODO: handle exception
                        EliteSession.eLog.e(MODULE, " " + e.getMessage());
                    }
                    if (d != null && currentDate != null) {
                        if (daysBetween(d, currentDate) >= CoreConstants.LOOGGER_FILE_DELETE_NUMBERDAYS) {
                            boolean deleted = file[i].delete();
                            if (deleted) {
                                EliteSession.eLog.d(MODULE, " Old log file dated" + d.toString() + "deleted successfully");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            EliteSession.eLog.e(MODULE, " Error while deleting log file..");
        }

//		AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
//		 Calendar calender = Calendar.getInstance();
//		//set the time to 6AM
//		 calender.set(Calendar.HOUR_OF_DAY, 6);
//		 calender.set(Calendar.MINUTE, 0);
//		 calender.set(Calendar.SECOND, 0);
//
//		//create a pending intent to be called at 6 AM
//		//schedule time for pending intent, and set the interval to day so that this event will repeat at the selected time every day
//		am.setRepeating(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), AlarmManager.INTERVAL_DAY*CoreConstants.LOOGGER_FILE_DELETE_NUMBERDAYS, pi);
//		//am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1*20000, pi);
    }

    public static long daysBetween(Date startDate, Date endDate) {
        Calendar sDate = getDatePart(startDate);
        Calendar eDate = getDatePart(endDate);

        long daysBetween = 0;
        while (sDate.before(eDate)) {
            sDate.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        return daysBetween;
    }

    public static Calendar getDatePart(Date date) {
        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        cal.set(Calendar.MINUTE, 0);                 // set minute in hour
        cal.set(Calendar.SECOND, 0);                 // set second in minute
        cal.set(Calendar.MILLISECOND, 0);            // set millisecond in second

        return cal;                                  // return the date part
    }
}

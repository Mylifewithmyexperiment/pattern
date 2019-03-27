package com.elitecorelib.core.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.dao.DBOperation;
import com.elitecorelib.core.dao.DBQueryFieldConstants;
import com.elitecorelib.core.pojo.PozoAnalytic;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chirag Parmar on 29-Aug-16.
 * <p/>
 * This  class to use the analytic to application and make the
 * report of the application use from server side.
 */
public class EliteAnalytics {


    public static EliteAnalytics analytics;
    private final String MODULE = "EliteAnalytics";
    private Context mContext;
    private DBOperation dbOperation;

    public EliteAnalytics(Context context) {
        this.mContext = context;
        dbOperation = DBOperation.getDBHelperOperation();
    }

    /**
     * Make the singleton instance
     *
     * @param context
     * @return
     */
    public static EliteAnalytics getAnalytics(Context context) {

        if (analytics == null) {
            analytics = new EliteAnalytics(context);
        }
        return analytics;
    }

    /**
     * insert analytic details in sqlite database
     *
     * @param eventID    create uniq idetity for the analytic detail
     * @param dateTime   insert time for the data
     * @param eventValue name of the analytic detail context
     * @param args       pass the analytic argument
     */
    public void insertAnalytic(int eventID, long dateTime, String eventValue, String... args) {
        try {

            ContentValues contentValues = new ContentValues();
            contentValues.put(DBQueryFieldConstants.EVENTID, eventID);
            contentValues.put(DBQueryFieldConstants.DATETIME, dateTime);
            contentValues.put(DBQueryFieldConstants.EVENTVALUE, eventValue);

            if (args != null && args.length > 0 && args[0] != null)
                contentValues.put(DBQueryFieldConstants.PARAM1, args[0]);

            if (args != null && args.length > 1 && args[1] != null)
                contentValues.put(DBQueryFieldConstants.PARAM2, args[1]);

            if (args != null && args.length > 2 && args[2] != null)
                contentValues.put(DBQueryFieldConstants.PARAM3, args[2]);

            EliteSession.eLog.d(MODULE, "insert analytic detail = "+contentValues);

            dbOperation.open();

            dbOperation.sQLiteDatabase.insert(DBQueryFieldConstants.TABLE_NAME_ANALYTIC, null, contentValues);

            dbOperation.close();

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error while inserting analytic " + e.getMessage());
        } finally {
            dbOperation.close();
        }
    }

    /**
     * get the all analytic detail
     *
     * @return get the analytic all data to fill arraylist and that array
     * convert to json using Gson and return json string
     */
    public JsonArray getAnalyticData() {

        Cursor cursor = null;

        try {
            dbOperation.open();
            cursor = DBOperation.sQLiteDatabase.query(DBQueryFieldConstants.TABLE_NAME_ANALYTIC, null, null, null, null, null, null);

            if (cursor.getCount() > 0) {

                ArrayList<PozoAnalytic> analyticArrayList = new ArrayList<>();

                while (cursor.moveToNext()) {

                    PozoAnalytic pojoConnection = new PozoAnalytic();

                    pojoConnection.setEventId(cursor.getInt(1));
                    pojoConnection.setDateTime(cursor.getLong(2));
                    pojoConnection.setEventValue(cursor.getString(3));
                    pojoConnection.setParam1(cursor.getString(4));
                    pojoConnection.setParam2(cursor.getString(5));
                    pojoConnection.setParam3(cursor.getString(6));

                    analyticArrayList.add(pojoConnection);
                }

                if (analyticArrayList.size() > 0) {
                    Gson gson = new Gson();
                    JsonElement element = gson.toJsonTree(analyticArrayList, new TypeToken<List<PozoAnalytic>>() {
                    }.getType());

                    if (!element.isJsonArray()) {
                        EliteSession.eLog.e(MODULE, "Analytic array not convert to json string");
                        return null;
                    }
                    JsonArray jsonArray = element.getAsJsonArray();
                    return jsonArray;
                }

                return null;
            }

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());

        } finally {
            if (cursor != null)
                cursor.close();

            dbOperation.close();
        }
        return null;
    }

    /**
     * delete all analytic value after the sync the data to server
     */
    public int deleteAnalytic() {

        try {
            EliteSession.eLog.d(MODULE, "Delete all analytic value");

            dbOperation.open();

            return dbOperation.sQLiteDatabase.delete(DBQueryFieldConstants.TABLE_NAME_ANALYTIC, null, null);

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
            return 0;
        } finally {
            if (dbOperation.isDBOpen())
                dbOperation.close();
        }
    }
}

package com.elitecorelib.core.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PointF;

import com.elitecore.wifi.api.EliteWiFIConstants;
import com.elitecore.wifi.pojo.PojoConnection;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.pojo.PojoAdMapping;
import com.elitecorelib.core.pojo.PojoLocation;
import com.elitecorelib.core.pojo.PojoProfile;
import com.elitecorelib.core.pojo.PojoServiceResponseLocation;
import com.elitecorelib.core.pojo.PojoSubscriber;
import com.elitecorelib.core.pojo.PojoSyncData;
import com.elitecorelib.core.pojo.PojoWifiInformation;
import com.elitecorelib.core.utility.ElitelibUtility;
import com.elitecorelib.core.utility.SharedPreferencesConstant;
import com.elitecorelib.core.utility.SharedPreferencesTask;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.elitecorelib.core.CoreConstants.NEARBYDISTANCE;
import static com.elitecorelib.core.CoreConstants.NEARBYDISTANCE_VALUE;
import static com.elitecorelib.core.dao.DBQueryFieldConstants.LATITUDE;
import static com.elitecorelib.core.dao.DBQueryFieldConstants.LONGITUDE;


public class DBOperation {

    private static final String MODULE = "DBOperation";
    // Database properties
    public static SQLiteDatabase sQLiteDatabase;
    private static Context myContext = LibraryApplication.getLibraryApplication().getLibraryContext();
    private static DBOperation dbOperation = null;
    private final SharedPreferencesTask task = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();

    //COMMON DATABASE METHODS
    private DBOperation() {
        try {
            sQLiteDatabase = myContext.openOrCreateDatabase(DBQueryFieldConstants.DBNAME, SQLiteDatabase.CREATE_IF_NECESSARY, null);
            sQLiteDatabase.execSQL(DBQueryFieldConstants.QUERY_CREATE_TABLE_WIFI_INFORMATION);
            sQLiteDatabase.execSQL(DBQueryFieldConstants.QUERY_CREATE_TABLE_ANALYTIC);

            sQLiteDatabase.execSQL(DBQueryFieldConstants.QUERY_CREATE_TABLE_CONNECTION);
            sQLiteDatabase.execSQL(DBQueryFieldConstants.QUERY_CREATETABLE_LOCATION);
            sQLiteDatabase.execSQL(DBQueryFieldConstants.QUERY_CREATE_TABLE_LOCATIONRELATION);
            sQLiteDatabase.execSQL(DBQueryFieldConstants.QUERY_CREATE_TABLE_PROFILE);
            sQLiteDatabase.execSQL(DBQueryFieldConstants.QUERY_CREATE_TABLE_REGISTRATION);
            sQLiteDatabase.execSQL(DBQueryFieldConstants.QUERY_CREATE_TABLE_RELATION);
            sQLiteDatabase.execSQL(DBQueryFieldConstants.QUERY_CREATETABLE_SSID);
            sQLiteDatabase.execSQL(DBQueryFieldConstants.QUERY_CREATETABLE_USAGE_DETAIL);
            sQLiteDatabase.execSQL(DBQueryFieldConstants.QUERY_CREATETABLE_AD_MAPPING);
            sQLiteDatabase.execSQL(DBQueryFieldConstants.QUERY_CREATETABLE_SYNCDATA_MAPPING);
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());

        }
    }

    public static synchronized DBOperation getDBHelperOperation() {
        try {
            if (null == dbOperation) {
                dbOperation = new DBOperation();
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error while getting DBinstance " + e.getMessage());
        }
        return dbOperation;
    }

    private static String getCallingMethodName() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if (stackTraceElements.length == 3) {
            return stackTraceElements[3].getMethodName();
        } else {
            return stackTraceElements[2].getMethodName();
        }
    }

    // ---opens the database---
    public synchronized void open() throws SQLException {
        try {
            if (sQLiteDatabase.isOpen())
                if (sQLiteDatabase != null)
                    close();
            sQLiteDatabase = myContext.openOrCreateDatabase(DBQueryFieldConstants.DBNAME, SQLiteDatabase.CREATE_IF_NECESSARY, null);
        } catch (Exception e) {

            EliteSession.eLog.e(MODULE, "Error while opening the database " + e.getMessage());
        }

    }

    // ---closes the database---
    public synchronized void close() {
        try {
            if (sQLiteDatabase.isOpen())
                sQLiteDatabase.close();
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error while closing  " + e.getMessage());
        }
    }

    /**
     * This method is use to insert pojowifiinformation
     *
     * @param pojoWifiInformation object
     */
    public void insertWifiInformation(PojoWifiInformation pojoWifiInformation) throws Exception {
        final long insertRecord;
        try {
            open();
            EliteSession.eLog.i(MODULE, "Enter into insertWifiInformation method. with PojoWifiInformation object : " + pojoWifiInformation);
            PojoWifiInformation wifiInfo = getWifiInformationByPriority(pojoWifiInformation.getPriority());
            EliteSession.eLog.d(MODULE, " WIFI info recievied with priority");
            if (wifiInfo == null) {
                EliteSession.eLog.d(MODULE, " WIFI info null, insert record");
                insertRecord = sQLiteDatabase.insert(DBQueryFieldConstants.TABLE_NAME_WIFI_INFORMATION, null, getContentValues(pojoWifiInformation));
                if (insertRecord == -1) {
                    EliteSession.eLog.e(MODULE, "Fail to add personal Wi-Fi because same name SSID is already exist");
                    throw new Exception("Same name SSID is already exist");
                }
            } else {

                List<PojoWifiInformation> wifiInformations = getPojoWifiInformationsPriorityWise(pojoWifiInformation.getPriority());

                deleteWifiInformation(DBQueryFieldConstants.TABLE_NAME_WIFI_INFORMATION, new String[]{DBQueryFieldConstants.PRIORITY}, new String[]{pojoWifiInformation.getPriority().toString()}, true);
                insertRecord = sQLiteDatabase.insert(DBQueryFieldConstants.TABLE_NAME_WIFI_INFORMATION, null, getContentValues(pojoWifiInformation));

                if (insertRecord == -1) {
                    addWifiHelper(wifiInformations, 0);
                    EliteSession.eLog.e(MODULE, "Fail to add personal Wi-Fi because same name SSID is already exist");
                    throw new Exception("Same name SSID is already exist");
                }
                insertExistingRecord(wifiInformations, 1);
//				addWifiHelper(wifiInformations,1);
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, " Error while inserting Wi-Fi information. Reason : " + e.getMessage());
            throw new Exception("Fail to add Wi-Fi.Please try after some time");
        } finally {
            close();
        }
        EliteSession.eLog.i(MODULE, "return from  insertWifiInformation method. Inserted Record Size : " + insertRecord);
    }

    /**
     * This method is use to get wifiinformation object
     *
     * @param ssidName name
     * @return PojoWifiInformation object
     */
    public PojoWifiInformation getWifiInformationBySSIDName(String ssidName) {
        return getWifiInformation(ssidName, true, false);
    }

    /**
     * This method is use to get wifi information priority wise
     *
     * @param priority priority value
     * @return PojoWifiInformation object
     */
    public PojoWifiInformation getWifiInformationByPriority(Integer priority) {
        return getWifiInformation(priority.toString(), false, true);
    }

    /**
     * This method is use to get all wifiinformation
     *
     * @return ArrayList<PojoWifiInformation>
     */
    public ArrayList<PojoWifiInformation> getAllWifiInformation() {
        EliteSession.eLog.i(MODULE, "Enter into getAllWifiInformation method.");
        ArrayList<PojoWifiInformation> pojoWifiInformations = new ArrayList<PojoWifiInformation>();
        Cursor cursor = null;
        try {
            cursor = sQLiteDatabase.query(DBQueryFieldConstants.TABLE_NAME_WIFI_INFORMATION, null, null, null, null, null, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    pojoWifiInformations.add(getWifiInformationObject(cursor));
                }
                EliteSession.eLog.i(MODULE, "Return from getAllWifiInformation method.with  list of pojoWifiInformations : ");
                String json = new Gson().toJson(pojoWifiInformations);
                try {
                    JSONArray mJSONArray = new JSONArray(json);
                    EliteSession.eLog.i(MODULE, " List found, " + mJSONArray.toString());
                } catch (JSONException e) {
                    EliteSession.eLog.e(MODULE, e.getMessage());
                }
                return pojoWifiInformations;
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        EliteSession.eLog.i(MODULE, "Return from getAllWifiInformation method.with empty list");
        return pojoWifiInformations;
    }

    /**
     * This method is use to delete wifi information
     *
     * @param tableName      name of table from delete calue
     * @param whereCondition condition to delete
     * @param whereValues    value of condition
     */
    public void deleteWifiInformation(String tableName, String[] whereCondition, String[] whereValues) {
        deleteWifiInformation(tableName, whereCondition, whereValues, false);
    }

    /**
     * This method is use to delete wifi information priority wise
     *
     * @param tableName      name of table from delete calue
     * @param whereCondition condition to delete
     * @param whereValues    value of condition
     * @param priorityWise   is delete priority wise or not
     */
    public void deleteWifiInformation(String tableName, String[] whereCondition, String[] whereValues, boolean priorityWise) {
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < whereCondition.length; i++) {

                sb.append(whereCondition[i]);
                if (priorityWise)
                    sb.append(">=?");
                else
                    sb.append("=?");
                if (i != whereCondition.length - 1)
                    sb.append(" AND ");
            }
            sQLiteDatabase.delete(tableName, sb.toString(), whereValues);
        } catch (Exception e) {

            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    /**
     * This method is use to delete all walue from specific table
     *
     * @param tableName table name
     */
    public void deleteAllValues(String tableName) {
        open();
        try {
            EliteSession.eLog.d(MODULE, " Table Deleted: " + tableName);
            sQLiteDatabase.delete(tableName, null, null);
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        } finally {
            close();
        }
    }

    /**
     * Method need to check db connection is open
     *
     * @return true if connection is open
     */
    public boolean isDBOpen() {
        return sQLiteDatabase.isOpen();
    }

    public List<PojoWifiInformation> getWifiListOperatorSpecific(List<String> ssidList, boolean isOpenWifi) {
        EliteSession.eLog.i(MODULE, "getWifiListOperatorSpecific method call from " + getCallingMethodName() + " method.");

        List<PojoWifiInformation> pojoWifiInformations = new ArrayList<PojoWifiInformation>();
        Cursor cursor = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ");
            sb.append(DBQueryFieldConstants.TABLE_NAME_WIFI_INFORMATION);
            sb.append(" WHERE ");
            sb.append(DBQueryFieldConstants.IS_OPERATOR_WIFI).append(" = \"Y\" AND ");
            if (isOpenWifi) {
                sb.append(DBQueryFieldConstants.SECURITY_METHOD).append(" = \"").append(EliteWiFIConstants.WIFI_OPEN).append("\" AND ");
            } else {
                sb.append(DBQueryFieldConstants.SECURITY_METHOD).append(" != \"").append(EliteWiFIConstants.WIFI_OPEN).append("\" AND ");
            }
            sb.append(DBQueryFieldConstants.SSID_NAME).append(" in(");
            sb.append(makePlaceholders(ssidList.size())).append(")");
            EliteSession.eLog.d(MODULE, " Query is " + sb.toString());
            cursor = sQLiteDatabase.rawQuery(sb.toString(), ssidList.toArray(new String[ssidList.size()]));
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    pojoWifiInformations.add(getWifiInformationObject(cursor));
                }
                EliteSession.eLog.i(MODULE, "Return from getWifiListOperatorSpecific method.with  list of pojoWifiInformations : ");
                String json = new Gson().toJson(pojoWifiInformations);
                try {
                    JSONArray mJSONArray = new JSONArray(json);
                    EliteSession.eLog.i(MODULE, " List found, " + mJSONArray.toString());
                } catch (JSONException e) {
                    EliteSession.eLog.e(MODULE, e.getMessage());
                }
                return pojoWifiInformations;
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        EliteSession.eLog.i(MODULE, "Return from getWifiListOperatorSpecific method call from " + getCallingMethodName() + "method with empty list of pojoWifiInformations : " + pojoWifiInformations);
        return pojoWifiInformations;

    }

    public List<PojoWifiInformation> getWifisFromSSIDList(List<String> ssidList, boolean isPersonalOnly) {
        EliteSession.eLog.i(MODULE, "getWifisFromSSIDList method call from " + getCallingMethodName() + " method.");
        List<PojoWifiInformation> pojoWifiInformations = new ArrayList<PojoWifiInformation>();
        Cursor cursor = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ");
            sb.append(DBQueryFieldConstants.TABLE_NAME_WIFI_INFORMATION);
            sb.append(" WHERE ");
            if (isPersonalOnly) {
                sb.append(DBQueryFieldConstants.IS_OPERATOR_WIFI).append(" = \"N\" AND ");
            }
            sb.append(DBQueryFieldConstants.SSID_NAME).append(" in(");
            sb.append(makePlaceholders(ssidList.size())).append(")");
            EliteSession.eLog.d(MODULE, " Query is " + sb.toString());
            cursor = sQLiteDatabase.rawQuery(sb.toString(), ssidList.toArray(new String[ssidList.size()]));
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    pojoWifiInformations.add(getWifiInformationObject(cursor));
                }
                EliteSession.eLog.i(MODULE, "Return from getAllPersonalWifi method.with  list of pojoWifiInformations : ");
                String json = new Gson().toJson(pojoWifiInformations);
                try {
                    JSONArray mJSONArray = new JSONArray(json);
                    EliteSession.eLog.i(MODULE, " List found, " + mJSONArray.toString());
                } catch (JSONException e) {
                    EliteSession.eLog.e(MODULE, e.getMessage());
                }
                return pojoWifiInformations;
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        EliteSession.eLog.i(MODULE, "Return from getWifisFromSSIDList method call from " + getCallingMethodName() + " method with  empty list of pojoWifiInformations : ");
        String json = new Gson().toJson(pojoWifiInformations);
        try {
            JSONArray mJSONArray = new JSONArray(json);
            EliteSession.eLog.i(MODULE, " List found, " + mJSONArray.toString());
        } catch (JSONException e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
        return pojoWifiInformations;
    }

    public int updateWifiPriority(PojoWifiInformation wifiInformation) throws Exception {
        EliteSession.eLog.i(MODULE, "Enter into updateWifiPriority method. with pojoWifiInformation is : ");
        String json = new Gson().toJson(wifiInformation);
        try {
            JSONArray mJSONArray = new JSONArray(json);
            EliteSession.eLog.i(MODULE, " List found, " + mJSONArray.toString());
        } catch (JSONException e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
        int count;
        try {
            PojoWifiInformation wifiInfo = getWifiInformationByPriority(wifiInformation.getPriority());
            if (wifiInfo == null) {
                count = updateWifiPriorityHelper(wifiInformation);
            } else {
                PojoWifiInformation updateWifiInfoPriority = getWifiInformationBySSIDName(wifiInformation.getSsidName());
                if (updateWifiInfoPriority != null) {
                    wifiInfo.setPriority(updateWifiInfoPriority.getPriority());
                }

                count = updateWifiInformation(wifiInformation);
                updateWifiInformation(wifiInfo);
            }
        } catch (Exception e) {
            throw new Exception("Fail to update Wi-Fi priority. reason" + e.getMessage());
        }
        return count;
    }

    /**
     * This method is use to getContent value of for save and update of wifiInformation
     *
     * @param pojoWifiInformation wifi information object
     * @return ContentValues object
     */
    private ContentValues getContentValues(PojoWifiInformation pojoWifiInformation) throws Exception {
        ContentValues contentValues = new ContentValues();
        if (pojoWifiInformation.getSsidName() != null && !pojoWifiInformation.getSsidName().isEmpty())
            contentValues.put(DBQueryFieldConstants.SSID_NAME, pojoWifiInformation.getSsidName());
        if (pojoWifiInformation.getSecurityMethod() != null && !pojoWifiInformation.getSecurityMethod().isEmpty())
            contentValues.put(DBQueryFieldConstants.SECURITY_METHOD, pojoWifiInformation.getSecurityMethod());
        if (pojoWifiInformation.getAuthenMethod() != null && !pojoWifiInformation.getAuthenMethod().isEmpty())
            contentValues.put(DBQueryFieldConstants.AUTHENTICATION_METHOD, pojoWifiInformation.getAuthenMethod());
        if (pojoWifiInformation.getPhase2Authentication() != null && !pojoWifiInformation.getPhase2Authentication().isEmpty())
            contentValues.put(DBQueryFieldConstants.PHASE2_AUTHENTICATION, pojoWifiInformation.getPhase2Authentication());

        if (pojoWifiInformation.getIdentity() != null && !pojoWifiInformation.getIdentity().isEmpty())
            contentValues.put(DBQueryFieldConstants.IDENTITY, pojoWifiInformation.getIdentity());
        if (pojoWifiInformation.getPassword() != null && !pojoWifiInformation.getPassword().isEmpty())
            contentValues.put(DBQueryFieldConstants.PASSWORD, pojoWifiInformation.getPassword());
        if (pojoWifiInformation.isAutoLogin() != null)
            contentValues.put(DBQueryFieldConstants.AUTOLOGIN, pojoWifiInformation.isAutoLogin() ? "Y" : "N");
        if (pojoWifiInformation.isOperatorWifi() != null)
            contentValues.put(DBQueryFieldConstants.IS_OPERATOR_WIFI, pojoWifiInformation.isOperatorWifi() ? "Y" : "N");
        if (pojoWifiInformation.getPriority() != null)
            contentValues.put(DBQueryFieldConstants.PRIORITY, pojoWifiInformation.getPriority());
        if (pojoWifiInformation.getDelteOnTurnOffWiFi() != null)
            contentValues.put(DBQueryFieldConstants.DELTEONTURNOFFWIFI, pojoWifiInformation.getDelteOnTurnOffWiFi() ? "Y" : "N");
        if (pojoWifiInformation.getAutoRemovealTimerInterval() != null)
            contentValues.put(DBQueryFieldConstants.AUTOREMOVEALTIMERINTERVAL, pojoWifiInformation.getAutoRemovealTimerInterval());
        return contentValues;
    }

    private String makePlaceholders(int len) {
        if (len < 1) {
            // It will lead to an invalid query anyway ..
            throw new RuntimeException("No placeholders");
        } else {
            StringBuilder sb = new StringBuilder(len * 2 - 1);
            sb.append("?");
            for (int i = 1; i < len; i++) {
                sb.append(",?");
            }
            return sb.toString();
        }
    }

    private int updateWifiPriorityHelper(PojoWifiInformation wifiInformation) throws Exception {
        int count = updateWifiInformation(wifiInformation);
        if (count == 0) {
            EliteSession.eLog.i(MODULE, "No any record updated");
        }
        return count;
    }

    /**
     * This method is use to get wifiinformation object from resultset
     *
     * @param cursor contain information of one DB row
     * @return PojoWifiInformation
     */
    private PojoWifiInformation getWifiInformationObject(Cursor cursor) throws Exception {
        PojoWifiInformation pojoWifiInformation = null;

        if (cursor != null) {
            pojoWifiInformation = new PojoWifiInformation();

            pojoWifiInformation.setWifiInfoId(cursor.getInt(0));
            pojoWifiInformation.setSsidName(cursor.getString(1));
            pojoWifiInformation.setPriority(cursor.getInt(2));
            if (cursor.getString(3).equals("Y")) {
                pojoWifiInformation.setOperatorWifi(true);
            } else {
                pojoWifiInformation.setOperatorWifi(false);
            }
            if (cursor.getString(4).equals("Y")) {
                pojoWifiInformation.setAutoLogin(true);
            } else {
                pojoWifiInformation.setAutoLogin(false);
            }
            pojoWifiInformation.setSecurityMethod(cursor.getString(5));
            if (cursor.getString(6) != null)
                pojoWifiInformation.setIdentity(cursor.getString(6));
            if (cursor.getString(7) != null)
                pojoWifiInformation.setPassword(cursor.getString(7));
            if (cursor.getString(8) != null)
                pojoWifiInformation.setAuthenMethod(cursor.getString(8));
            if (cursor.getString(9) != null)
                pojoWifiInformation.setPhase2Authentication(cursor.getString(9));
            if (cursor.getString(10) != null && cursor.getString(10).equals("Y")) {
                pojoWifiInformation.setDelteOnTurnOffWiFi(true);
            } else {
                pojoWifiInformation.setDelteOnTurnOffWiFi(false);
            }
            if (cursor.getString(11) != null)
                pojoWifiInformation.setAutoRemovealTimerInterval(cursor.getInt(11));

        }
        return pojoWifiInformation;
    }

    private int updateWifiInformation(PojoWifiInformation pojoWifiInformation) throws Exception {
        EliteSession.eLog.i(MODULE, "Enter into updateWifiInformation method. with pojoWifiInformation is : ");
        String json = new Gson().toJson(pojoWifiInformation);
        try {
            JSONArray mJSONArray = new JSONArray(json);
            EliteSession.eLog.i(MODULE, " List found, " + mJSONArray.toString());
        } catch (JSONException e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
        int i;
        try {
            if (pojoWifiInformation == null) {
                EliteSession.eLog.i(MODULE, "fail to update Wi-Fi information. Reason : pojowifiinformation is null ");
                throw new Exception("fail to update Wi-Fi information. Reason : pojowifiinformation is null ");
            } else if (pojoWifiInformation.getSsidName() == null) {
                EliteSession.eLog.i(MODULE, "fail to update Wi-Fi information. Reason : Wi-Fi information ssid name is null ");
                throw new Exception("fail to update Wi-Fi information. Reason : Wi-Fi information ssid name is null ");
            }
            EliteSession.eLog.i(MODULE, "Wi-Fi information id and name for update is Id=" + pojoWifiInformation.getWifiInfoId() + ",SSIDName=" + pojoWifiInformation.getSsidName());
            ContentValues contentValues = getContentValues(pojoWifiInformation);
            //contentValues.put(DBQueryFieldConstants.WIFI_INFO_ID,pojoWifiInformation.getWifiInfoId());
            i = sQLiteDatabase.update(DBQueryFieldConstants.TABLE_NAME_WIFI_INFORMATION, contentValues, DBQueryFieldConstants.SSID_NAME + "=?", new String[]{pojoWifiInformation.getSsidName()});
            EliteSession.eLog.i(MODULE, "return from updateWifiInformation method with success fully update");
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error while  updateWifiInformation" + e.getMessage());
            throw new Exception(e.getMessage());
        }
        EliteSession.eLog.i(MODULE, "Return from updateWifiInformation. with pojoWifiInformation :- ");
        String json2 = new Gson().toJson(pojoWifiInformation);
        try {
            JSONArray mJSONArray = new JSONArray(json2);
            EliteSession.eLog.i(MODULE, " List found, " + mJSONArray.toString());
        } catch (JSONException e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
        return i;
    }

    private void addWifiHelper(final List<PojoWifiInformation> wifiInformations, final int incrementByValue) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    insertExistingRecord(wifiInformations, incrementByValue);
                } catch (Exception e) {
                    EliteSession.eLog.e(MODULE, e.getMessage());
                }
            }
        }).start();
    }

    private void insertExistingRecord(List<PojoWifiInformation> pojoWifiInformations, int incrementByValue) throws Exception {
        for (PojoWifiInformation wifiInfo : pojoWifiInformations) {
            wifiInfo.setPriority(wifiInfo.getPriority() + incrementByValue);
            long insertRecord = sQLiteDatabase.insert(DBQueryFieldConstants.TABLE_NAME_WIFI_INFORMATION, null, getContentValues(wifiInfo));
            if (insertRecord == -1) {
                EliteSession.eLog.e(MODULE, "Fail to add personal Wi-Fi because same name SSID is already exist");
            }
        }
    }

    private List<PojoWifiInformation> getPojoWifiInformationsPriorityWise(Integer priority) {
        EliteSession.eLog.i(MODULE, "Enter into getPojoWifiInformationsPriorityWise method.");
        ArrayList<PojoWifiInformation> pojoWifiInformations = new ArrayList<PojoWifiInformation>();
        Cursor cursor = null;
        try {
            cursor = sQLiteDatabase.query(DBQueryFieldConstants.TABLE_NAME_WIFI_INFORMATION, null, null, null, null, null, null);
            cursor = sQLiteDatabase.query(DBQueryFieldConstants.TABLE_NAME_WIFI_INFORMATION, null, DBQueryFieldConstants.PRIORITY + ">=?", new String[]{priority.toString()}, null, null, DBQueryFieldConstants.PRIORITY);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    pojoWifiInformations.add(getWifiInformationObject(cursor));
                }
                EliteSession.eLog.i(MODULE, "Return from getAllWifiInformation method.with  list of pojoWifiInformations : ");
                String json = new Gson().toJson(pojoWifiInformations);
                try {
                    JSONArray mJSONArray = new JSONArray(json);
                    EliteSession.eLog.i(MODULE, " List found, " + mJSONArray.toString());
                } catch (JSONException e) {
                    EliteSession.eLog.e(MODULE, e.getMessage());
                }
                return pojoWifiInformations;
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        EliteSession.eLog.i(MODULE, "Return from getAllWifiInformation method.with empty list");
        return pojoWifiInformations;
    }

    private PojoWifiInformation getWifiInformation(String keyValue, boolean isBySSID, boolean isByPriority) {
        EliteSession.eLog.i(MODULE, "Enter into getWifiInformationBySSIDName method. with keyValue is : " + keyValue);
        Cursor cursor = null;
        try {
            String keyLable = DBQueryFieldConstants.SSID_NAME;
            if (isBySSID)
                keyLable = DBQueryFieldConstants.SSID_NAME;
            else if (isByPriority)
                keyLable = DBQueryFieldConstants.PRIORITY;

            cursor = sQLiteDatabase.query(DBQueryFieldConstants.TABLE_NAME_WIFI_INFORMATION, null, keyLable + "=?", new String[]{keyValue}, null, null, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                PojoWifiInformation pojoWifiInformation = getWifiInformationObject(cursor);
                if (pojoWifiInformation != null) {
                    EliteSession.eLog.i(MODULE, "return from getWifiInformationBySSIDName method. with PojoWifiInformation object : ");
                    EliteSession.eLog.i(MODULE, pojoWifiInformation.toString());

                    return pojoWifiInformation;
                }
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error while getting wifi information  " + e.getMessage());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        EliteSession.eLog.i(MODULE, "return from getWifiInformationByPriority method. with null PojoWifiInformation object");
        return null;
    }

    //************************************************DBREGISTRATION METHODS
    public void insertRegistration(PojoSubscriber registration) {

        open();

        try {
            //private Integer age;
            //private Timestamp registrationDate;
            //private Timestamp lastUsedTime;
            //private Timestamp uninstallDate;
            //private String status;

            ContentValues contentValues = new ContentValues();
            EliteSession.eLog.d(MODULE, "insertRegistration Storing registration Information ind DB");
            if (registration.getRegisteredDeviceId() != null)
                contentValues.put(DBQueryFieldConstants.REGISTEREDDEVICEID, registration.getRegisteredDeviceId());
            if (registration.getRegisterWith() != null)
                contentValues.put(DBQueryFieldConstants.REGISTERWITH, registration.getRegisterWith());
            if (registration.getBirthDate() != null)
                contentValues.put(DBQueryFieldConstants.BIRTHDATE, registration.getBirthDate().toString());
            if (registration.getLanguage() != null)
                contentValues.put(DBQueryFieldConstants.LANGUAGE, registration.getLanguage());
            if (registration.getLocation() != null)
                contentValues.put(DBQueryFieldConstants.LOCATION, registration.getLocation());
            if (registration.getAgeRange() != null)
                contentValues.put(DBQueryFieldConstants.AGERANGE, registration.getAgeRange());
            if (registration.getImei() != null)
                contentValues.put(DBQueryFieldConstants.IMEI, registration.getImei());
            if (registration.getFirstName() != null)
                contentValues.put(DBQueryFieldConstants.FIRSTNAME, registration.getFirstName());
            if (registration.getLastName() != null)
                contentValues.put(DBQueryFieldConstants.LASTNAME, registration.getLastName());
            if (registration.getUserName() != null)
                contentValues.put(DBQueryFieldConstants.USERNAME, registration.getUserName());
            if (registration.getSport() != null)
                contentValues.put(DBQueryFieldConstants.SPORT, registration.getSport());
            if (registration.getEducation() != null)
                contentValues.put(DBQueryFieldConstants.EDUCATION, registration.getEducation());
            if (registration.getGender() != null)
                contentValues.put(DBQueryFieldConstants.GENDER, registration.getGender());
            if (registration.getImsi() != null)
                contentValues.put(DBQueryFieldConstants.IMSI, registration.getImsi());
            if (registration.getSimOperator() != null)
                contentValues.put(DBQueryFieldConstants.SIMOPERATOR, registration.getSimOperator());
            if (registration.getBrand() != null)
                contentValues.put(DBQueryFieldConstants.BRAND, registration.getBrand());
            if (registration.getManufacturer() != null)
                contentValues.put(DBQueryFieldConstants.MANUFACTURER, registration.getManufacturer());
            if (registration.getModel() != null)
                contentValues.put(DBQueryFieldConstants.MODEL, registration.getModel());
            if (registration.getNetworkOperator() != null)
                contentValues.put(DBQueryFieldConstants.NETWORKOPERATOR, registration.getNetworkOperator());
            if (registration.getRelationshipStatus() != null)
                contentValues.put(DBQueryFieldConstants.RELATIONSHIPSTATUS, registration.getRelationshipStatus());
            if (registration.getOperatingSystem() != null)
                contentValues.put(DBQueryFieldConstants.OPERATINGSYSTEM, registration.getOperatingSystem());
            if (registration.getRegistrationDate() != null)
                contentValues.put(DBQueryFieldConstants.REGISTRATIONDATE, registration.getRegistrationDate().toString());
            if (registration.getLastUsedTime() != null)
                contentValues.put(DBQueryFieldConstants.LASTUSEDTIME, registration.getLastUsedTime().toString());
            if (registration.getUninstallDate() != null)
                contentValues.put(DBQueryFieldConstants.UNINSTALLDATE, registration.getUninstallDate().toString());
            if (registration.getStatus() != null)
                contentValues.put(DBQueryFieldConstants.STATUS, registration.getStatus());
            if (registration.getCity() != null)
                contentValues.put(DBQueryFieldConstants.CITY, registration.getCity());
            if (registration.getState() != null)
                contentValues.put(DBQueryFieldConstants.STATE, registration.getState());
            if (registration.getCountry() != null)
                contentValues.put(DBQueryFieldConstants.COUNTRY, registration.getCountry());

            if (registration.getGeoAddress() != null)
                contentValues.put(DBQueryFieldConstants.GEOADDRESS, registration.getGeoAddress());
            if (registration.getPostalCode() > 0)
                contentValues.put(DBQueryFieldConstants.POSTALCODE, registration.getPostalCode());

            sQLiteDatabase.insert(DBQueryFieldConstants.TABLENAME_REGISTRATION, null, contentValues);
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        } finally {
            close();
        }
    }

    public void insertBulkLocation(PojoServiceResponseLocation responseLocation) {
        try {
            open();
            sQLiteDatabase.beginTransaction();
            for (PojoLocation locObj : responseLocation.getResponseData()) {

                ContentValues contentValues = new ContentValues();
                contentValues.put(DBQueryFieldConstants.LOCATION_ID, locObj.getLocationId());
                contentValues.put(DBQueryFieldConstants.LOCATION_NAME, locObj.getLocationName());
                contentValues.put(LATITUDE, locObj.getLatitude());
                contentValues.put(LONGITUDE, locObj.getLongitude());
                contentValues.put(DBQueryFieldConstants.RADIUS, locObj.getRadius());
                contentValues.put(DBQueryFieldConstants.CATEGORY, locObj.getCategory());
                contentValues.put(DBQueryFieldConstants.LOCATIONDESCRIPTION, locObj.getLocationDescription());
                contentValues.put(DBQueryFieldConstants.ZONEID, locObj.getZoneId());
                contentValues.put(DBQueryFieldConstants.PARAM1_location, locObj.getParam1());
                contentValues.put(DBQueryFieldConstants.PARAM2_location, locObj.getParam2());
                contentValues.put(DBQueryFieldConstants.OFFLINEMESSAGE, locObj.getOfflineMessage());
                contentValues.put(DBQueryFieldConstants.AGENTID, locObj.getAgentId());

                sQLiteDatabase.insert(DBQueryFieldConstants.TABLENAME_LOCATION, null, contentValues);
            }
            sQLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        } finally {
            sQLiteDatabase.endTransaction();
            close();
        }
    }

    //****************************************************DBHELPER AD MAPPING METHODS
    public void insertAdMapping(String screenLocation, String deviceCatagory, String invocationCode, String screenName) {
        // open Database
        open();

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBQueryFieldConstants.SCREENLOCATION, screenLocation);
            contentValues.put(DBQueryFieldConstants.DEVICECATAGORY, deviceCatagory);
            //String appendingHTML="<html><head><style> img {width : 100%;}  body {margin:0px; padding:0px;}</style></head><body>"+invocationCode+"</body></html>";

            contentValues.put(DBQueryFieldConstants.INVOCATIONCODE, invocationCode);
            contentValues.put(DBQueryFieldConstants.SCREENNAME, screenName);

            sQLiteDatabase.insert(DBQueryFieldConstants.TABLENAME_AD_MAPPING, null, contentValues);
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "insertAdMapping - " + e.getMessage());
        } finally {

            close();
        }
    }

    public HashMap<String, PojoAdMapping> getAdMappingHashMap() {

        open();
        final HashMap<String, PojoAdMapping> adMappingHashMap = new HashMap<String, PojoAdMapping>();
        Cursor cursor = null;
        try {
            cursor = sQLiteDatabase.query(DBQueryFieldConstants.TABLENAME_AD_MAPPING, null, null, null, null, null, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    PojoAdMapping adMapping = new PojoAdMapping();
                    adMapping.setScreenLocation(cursor.getString(0));
                    adMapping.setScreenName(cursor.getString(1));
                    adMapping.setInvocationCode(cursor.getString(2));
                    adMapping.setDeviceCatagory(cursor.getString(3));
                    adMappingHashMap.put(adMapping.getScreenName() + adMapping.getScreenLocation(), adMapping);
                }

                EliteSession.eLog.d(MODULE, "Adv List");
                EliteSession.eLog.d(MODULE, adMappingHashMap.toString());
                return adMappingHashMap;
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());

        } finally {
            if (cursor != null)
                cursor.close();

            close();
        }
        return null;
    }

    /**
     * Get Hostpost Location From database which store when registration done and
     * update when syncdata operation and update location click.
     *
     * @return
     */
    public ArrayList<PojoLocation> getLocationHostpost() {
        Cursor cursor = null;
        ArrayList<PojoLocation> locations = new ArrayList<PojoLocation>();

        try {
            // open Database
            open();

            EliteSession.eLog.d(MODULE, "Start Time - " + System.currentTimeMillis());

            cursor = sQLiteDatabase.query(DBQueryFieldConstants.TABLENAME_LOCATION, null, null, null, null, null, null);

            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    PojoLocation location = new PojoLocation();
                    location.setLocationId(cursor.getLong(0));
                    location.setLocationName(cursor.getString(1));
                    location.setLatitude(cursor.getDouble(2));
                    location.setLongitude(cursor.getDouble(3));
                    location.setRadius(cursor.getDouble(4));
                    location.setCategory(cursor.getString(5));
                    location.setLocationDescription(cursor.getString(6));
                    location.setParam1(cursor.getString(7));
                    location.setParam2(cursor.getString(8));
                    location.setZoneId(cursor.getInt(9));
                    location.setOfflineMessage(cursor.getString(10));
                    locations.add(location);
                }

                EliteSession.eLog.d(MODULE, "END Time - " + System.currentTimeMillis());
                EliteSession.eLog.d(MODULE, "Location size - " + locations.size());
                return locations;
            }

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        } finally {

            if (cursor != null)
                cursor.close();

            // Close database
            close();
        }
        return locations;
    }

    /**
     * Get Hostpost Location From database which store when registration done and
     * update when syncdata operation and update location click.
     *
     * @return
     */
    public ArrayList<PojoLocation> getNearHotspotLocation() {
        Cursor cursor = null;
        ArrayList<PojoLocation> locations = new ArrayList<PojoLocation>();

        PointF cPoint = new PointF(Float.parseFloat(task.getString(SharedPreferencesConstant.CURRENT_LATITUDE)), Float.parseFloat(task.getString(SharedPreferencesConstant.CURRENT_LONGITUDE)));
        double DISTANCE = Double.valueOf( ElitelibUtility.getKeyPairvalue(NEARBYDISTANCE,NEARBYDISTANCE_VALUE));
        PointF pointA = ElitelibUtility.calculateDerivedPosition(cPoint, DISTANCE, 0);
        PointF pointB = ElitelibUtility.calculateDerivedPosition(cPoint, DISTANCE, 90);
        PointF pointC = ElitelibUtility.calculateDerivedPosition(cPoint, DISTANCE, 180);
        PointF pointD = ElitelibUtility.calculateDerivedPosition(cPoint, DISTANCE, 270);


        String strWhere = "Select * From " + DBQueryFieldConstants.TABLENAME_LOCATION + " WHERE "
                + LATITUDE + " > " + String.valueOf(pointC.x) + " AND "
                + LATITUDE + " < " + String.valueOf(pointA.x) + " AND "
                + LONGITUDE + " < " + String.valueOf(pointB.y) + " AND "
                + LONGITUDE + " > " + String.valueOf(pointD.y);

        EliteSession.eLog.d(MODULE, "Query  - " + strWhere);

        try {
            // open Database
            open();
            cursor = sQLiteDatabase.rawQuery(strWhere, null);

            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    PojoLocation location = new PojoLocation();
                    location.setLocationId(cursor.getLong(0));
                    location.setLocationName(cursor.getString(1));
                    location.setLatitude(cursor.getDouble(2));
                    location.setLongitude(cursor.getDouble(3));
                    location.setRadius(cursor.getDouble(4));
                    location.setCategory(cursor.getString(5));
                    location.setLocationDescription(cursor.getString(6));
                    location.setParam1(cursor.getString(7));
                    location.setParam2(cursor.getString(8));
                    location.setZoneId(cursor.getInt(9));
                    location.setOfflineMessage(cursor.getString(10));
                    locations.add(location);
                }

                EliteSession.eLog.d(MODULE, "END Time - " + System.currentTimeMillis());
                EliteSession.eLog.d(MODULE, "Location size - " + locations.size());
                return locations;
            }

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        } finally {

            if (cursor != null)
                cursor.close();

            // Close database
            close();
        }
        return locations;
    }


    /*
     * ******************************************************  sync data queries started ********************
     */
    public void insertSyncData(int syncDataId, String moduleName, String modifiedDate) {

        // open Database
        open();

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBQueryFieldConstants.SYNCDATAID, syncDataId);
            contentValues.put(DBQueryFieldConstants.MODULENAME, moduleName);
            contentValues.put(DBQueryFieldConstants.MODIFIEDDATE, modifiedDate);
            sQLiteDatabase.insert(DBQueryFieldConstants.TABLENAME_SYNCDATA_MAPPING, null, contentValues);
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());

        } finally {
            close();
        }
    }

    public void updateSyncData(String moduleName, String modifiedDate) {
        // open Database
        open();

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBQueryFieldConstants.MODIFIEDDATE, modifiedDate);

            sQLiteDatabase.update(DBQueryFieldConstants.TABLENAME_SYNCDATA_MAPPING, contentValues, DBQueryFieldConstants.MODULENAME + "=?", new String[]{moduleName});
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        } finally {
            close();
        }
    }

    public ArrayList<PojoSyncData> getAllSyncData() {
        // open Database
        open();

        Cursor cursor = null;
        try {
            cursor = sQLiteDatabase.query(DBQueryFieldConstants.TABLENAME_SYNCDATA_MAPPING, null, null, null, null, null, null);
            if (cursor.getCount() > 0) {
                ArrayList<PojoSyncData> syncDataList = new ArrayList<PojoSyncData>();

                while (cursor.moveToNext()) {
                    PojoSyncData obj = new PojoSyncData();
                    obj.setPojoSyncDataId(cursor.getInt(0));
                    obj.setModuleName(cursor.getString(1));
                    obj.setModifiedDate(cursor.getString(2));
                    syncDataList.add(obj);
                }
                return syncDataList;
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());

        } finally {

            if (cursor != null)
                cursor.close();

            close();
        }
        return null;
    }

    /* **************************************************** Delete common queries******************************
    *
    */
    public void deleteWhere(String tableName, String[] whereCondition, String[] whereValues) {

        open();

        try {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < whereCondition.length; i++) {
                sb.append(whereCondition[i]);
                sb.append("=?");
                if (i != whereCondition.length - 1)
                    sb.append(" AND ");
            }
            int i = sQLiteDatabase.delete(tableName, sb.toString(), whereValues);

            if (i > 0) {
                EliteSession.eLog.d(MODULE, "Deleted Row is " + i);
            }

        } catch (Exception e) {

            EliteSession.eLog.e(MODULE, e.getMessage());
        } finally {
            close();
        }
    }

    //**************************************************************************DBHELPER PROFILE METHODS
    public void insertProfile(PojoProfile profile) {
        try {
            ContentValues contentValues = new ContentValues();

            contentValues.put(DBQueryFieldConstants.PROFILE_NAME, profile.getName());
            contentValues.put(DBQueryFieldConstants.IS_DEFAULT, profile.isDefault() ? 1 : 0);
            contentValues.put(DBQueryFieldConstants.IS_ACTIVE, profile.isActive() ? 1 : 0);
            contentValues.put(DBQueryFieldConstants.IS_LOCAL, profile.isLocal() ? 1 : 0);
            sQLiteDatabase.insert(DBQueryFieldConstants.TABLENAME_PROFILE, null, contentValues);
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    public String getAdvLocation(String agent_id, String coloum_value) {

        Cursor cursor = null;

        try {
            open();
            cursor = sQLiteDatabase.query(DBQueryFieldConstants.TABLENAME_LOCATION, new String[]{coloum_value}, DBQueryFieldConstants.AGENTID + "=?", new String[]{agent_id}, null, null, null);

            if (cursor.getCount() > 0) {

                cursor.moveToFirst();
                EliteSession.eLog.d(MODULE,"Get Value From - "+coloum_value+" ,  Value is - "+cursor.getString(cursor.getColumnIndex(coloum_value)));
                return cursor.getString(cursor.getColumnIndex(coloum_value));
            }

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());

        } finally {

            if (cursor != null)
                cursor.close();

            close();
        }
        return null;
    }

    public PojoProfile getProfile(String profileName) {

        Cursor cursor = null;

        try {

            cursor = sQLiteDatabase.query(DBQueryFieldConstants.TABLENAME_PROFILE, null, DBQueryFieldConstants.PROFILE_NAME + "=?", new String[]{profileName}, null, null, null);

            if (cursor.getCount() > 0) {

                cursor.moveToFirst();

                PojoProfile profile = new PojoProfile();

                profile.setName(cursor.getString(0));

                if (cursor.getInt(1) == 1) {
                    profile.setDefault(true);
                } else {
                    profile.setDefault(false);
                }

                if (cursor.getInt(2) == 1) {
                    profile.setActive(true);
                } else {
                    profile.setActive(false);
                }

                return profile;
            }

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());

        } finally {

            if (cursor != null)
                cursor.close();
        }

        return null;
    }

    public PojoProfile getActiveProfile() {

        Cursor cursor = null;

        try {

            cursor = sQLiteDatabase.query(DBQueryFieldConstants.TABLENAME_PROFILE, null, DBQueryFieldConstants.IS_ACTIVE + "=?", new String[]{"1"}, null, null, null);

            if (cursor.getCount() > 0) {

                cursor.moveToFirst();

                PojoProfile profile = new PojoProfile();

                profile.setName(cursor.getString(0));

                return profile;
            }

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());

        } finally {

            if (cursor != null)
                cursor.close();
        }

        return null;
    }

    public ArrayList<PojoProfile> getAllProfiles() {

        Cursor cursor = null;

        if (!isDBOpen())
            open();

        try {

            cursor = sQLiteDatabase.query(DBQueryFieldConstants.TABLENAME_PROFILE, null, null, null, null, null, null);

            if (cursor.getCount() > 0) {

                ArrayList<PojoProfile> profiles = new ArrayList<PojoProfile>();

                while (cursor.moveToNext()) {

                    PojoProfile profile = new PojoProfile();

                    profile.setName(cursor.getString(0));

                    if (cursor.getInt(1) == 1) {
                        profile.setDefault(true);
                    } else {
                        profile.setDefault(false);
                    }

                    if (cursor.getInt(2) == 1) {
                        profile.setActive(true);
                    } else {
                        profile.setActive(false);
                    }

                    profiles.add(profile);
                }

                return profiles;
            }

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());

        } finally {

            if (isDBOpen())
                close();

            if (cursor != null)
                cursor.close();
        }

        return null;
    }

    //****************************************************DBHELPERCONNECTION METHODS
    public void insertConnection(PojoConnection pojoConnection) {
        open();
        try {
            ContentValues contentValues = new ContentValues();
            EliteSession.eLog.e(MODULE, "SSID - " + pojoConnection.getSsid());
            contentValues.put(DBQueryFieldConstants.SSID, pojoConnection.getSsid());
            contentValues.put(DBQueryFieldConstants.SECURITY, pojoConnection.getSecurity());
            contentValues.put(DBQueryFieldConstants.ENCRIPTION, pojoConnection.getEncryptionMethod());

            String authenticationMethod = pojoConnection.getAuthenticationMethod();
            contentValues.put(DBQueryFieldConstants.AUTHENTICATION, authenticationMethod);

            String phase2Authentication = pojoConnection.getPhase2Authentication();
            contentValues.put(DBQueryFieldConstants.PHASE2_AUTHENTICATION, phase2Authentication);

            contentValues.put(DBQueryFieldConstants.IDENTITY, pojoConnection.getIdentity());
            contentValues.put(DBQueryFieldConstants.PASSWORD, pojoConnection.getPassword());
            contentValues.put(DBQueryFieldConstants.SHOW_PASSWORD, pojoConnection.isShowPassword() ? 1 : 0);
            contentValues.put(DBQueryFieldConstants.IS_DEFAULT, pojoConnection.isDefault() ? 1 : 0);
            contentValues.put(DBQueryFieldConstants.IS_ACTIVE, pojoConnection.isActive() ? 1 : 0);
            contentValues.put(DBQueryFieldConstants.IS_ACTIVE, pojoConnection.isOutOfRange() ? 1 : 0);
            contentValues.put(DBQueryFieldConstants.IS_LOCAL, pojoConnection.isLocal() ? 1 : 0);

            sQLiteDatabase.insert(DBQueryFieldConstants.TABLENAME_CONNECTION, null, contentValues);
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error while inserting connection " + e.getMessage());
        } finally {
            close();
        }
    }

    //*********************************************DBHelper RELATION Queries
    public void insertRelation(String profile, String connection) {
        open();
        try {
            ContentValues contentValues = new ContentValues();

            contentValues.put(DBQueryFieldConstants.PROFILE_NAME, profile);
            contentValues.put(DBQueryFieldConstants.SSID, connection);
            sQLiteDatabase.insert(DBQueryFieldConstants.TABLENAME_RELATION, null, contentValues);
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        } finally {
            close();
        }
    }

    public ArrayList<PojoLocation> getAllLocations() {
        Cursor cursor = null;
        open();
        try {
            cursor = sQLiteDatabase.query(DBQueryFieldConstants.TABLENAME_LOCATION, null, null, null, null, null, null);
            if (cursor.getCount() > 0) {
                ArrayList<PojoLocation> locations = new ArrayList<PojoLocation>();
                while (cursor.moveToNext()) {
                    PojoLocation location = new PojoLocation();
                    location.setLocationId(cursor.getLong(0));
                    location.setLocationName(cursor.getString(1));
                    location.setLatitude(cursor.getDouble(2));
                    location.setLongitude(cursor.getDouble(3));
                    location.setRadius(cursor.getDouble(4));
                    location.setCategory(cursor.getString(5));
                    location.setLocationDescription(cursor.getString(6));
                    location.setParam1(cursor.getString(7));
                    location.setParam2(cursor.getString(8));
                    location.setZoneId(cursor.getInt(9));
                    location.setOfflineMessage(cursor.getString(10));
                    locations.add(location);
                }

                EliteSession.eLog.d(MODULE, "Hotspot SIze - " + locations.size());
                return locations;
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        } finally {
            if (cursor != null)
                cursor.close();
            close();
        }
        return null;
    }

    public int getRegistration(String imei) {
        Cursor cursor = null;
        open();
        try {
            cursor = sQLiteDatabase.query(DBQueryFieldConstants.TABLENAME_REGISTRATION, null, DBQueryFieldConstants.IMEI + "=?", new String[]{imei}, null, null, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                return 1;
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        } finally {

            close();

            if (cursor != null)
                cursor.close();
        }

        return 0;
    }

    public ArrayList<PojoConnection> getConnections(String profileName) {

        Cursor cursor = null;

        try {
            DBOperation dbHelperConnection = DBOperation.getDBHelperOperation();
            dbHelperConnection.open();

            cursor = sQLiteDatabase.query(DBQueryFieldConstants.TABLENAME_RELATION, null, DBQueryFieldConstants.PROFILE_NAME + "=?", new String[]{profileName}, null, null, null);

            if (cursor.getCount() > 0) {

                ArrayList<PojoConnection> connections = new ArrayList<PojoConnection>();

                while (cursor.moveToNext()) {
                    PojoConnection pojoConnection = dbHelperConnection.getConnection(cursor.getString(1));
                    connections.add(pojoConnection);
                }

                dbHelperConnection.close();

                return connections;
            }

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());

        } finally {

            if (cursor != null)
                cursor.close();
        }

        return null;
    }

    public synchronized PojoConnection getConnection(String ssidName) {

        Cursor cursor = null;

        try {

            cursor = sQLiteDatabase.query(DBQueryFieldConstants.TABLENAME_CONNECTION, null, DBQueryFieldConstants.SSID + "=?", new String[]{ssidName}, null, null, null);

            if (cursor.getCount() > 0) {

                cursor.moveToFirst();

                PojoConnection pojoConnection = new PojoConnection();

                pojoConnection.setSsid(cursor.getString(0));
                pojoConnection.setSecurity(cursor.getString(1));
                pojoConnection.setEncryptionMethod(cursor.getString(2));
                pojoConnection.setAuthenticationMethod(cursor.getString(3));
                pojoConnection.setPhase2Authentication(cursor.getString(4));
                pojoConnection.setIdentity(cursor.getString(5));
                pojoConnection.setPassword(cursor.getString(6));

                if (cursor.getInt(7) == 1) {
                    pojoConnection.setShowPassword(true);
                } else {
                    pojoConnection.setShowPassword(false);
                }

                if (cursor.getInt(8) == 1) {
                    pojoConnection.setDefault(true);
                } else {
                    pojoConnection.setDefault(false);
                }

                if (cursor.getInt(9) == 1) {
                    pojoConnection.setActive(true);
                } else {
                    pojoConnection.setActive(false);
                }

                if (cursor.getInt(10) == 1) {
                    pojoConnection.setOutOfRange(true);
                } else {
                    pojoConnection.setOutOfRange(false);
                }

                return pojoConnection;
            }

        } catch (Exception e) {


            EliteSession.eLog.e(MODULE, "Error while getting connection " + e.getMessage());

        } finally {
            if (cursor != null)
                cursor.close();
        }

        return null;
    }

    //************************************DBHELPER LOCATION METHODS
    public void insertLocation(double locationId, String locationName, double latitude, double longitude, double radius, String category,
                               String locationDescription, int zoneid, String Param1, String Param2,String offlineMessage) {
        try {

            ContentValues contentValues = new ContentValues();
            contentValues.put(DBQueryFieldConstants.LOCATION_ID, locationId);
            contentValues.put(DBQueryFieldConstants.LOCATION_NAME, locationName);
            contentValues.put(DBQueryFieldConstants.LATITUDE, latitude);
            contentValues.put(DBQueryFieldConstants.LONGITUDE, longitude);
            contentValues.put(DBQueryFieldConstants.RADIUS, radius);
            contentValues.put(DBQueryFieldConstants.CATEGORY, category);
            contentValues.put(DBQueryFieldConstants.LOCATIONDESCRIPTION, locationDescription);
            contentValues.put(DBQueryFieldConstants.ZONEID, zoneid);
            contentValues.put(DBQueryFieldConstants.PARAM1, Param1);
            contentValues.put(DBQueryFieldConstants.PARAM2, Param2);
            contentValues.put(DBQueryFieldConstants.OFFLINEMESSAGE, offlineMessage);

            sQLiteDatabase.insert(DBQueryFieldConstants.TABLENAME_LOCATION, null, contentValues);
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

}
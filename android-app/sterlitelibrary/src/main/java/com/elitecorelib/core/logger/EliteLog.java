package com.elitecorelib.core.logger;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.security.BASE64Decoder;
import com.elitecorelib.core.utility.ElitelibUtility;
import com.elitecorelib.core.utility.SharedPreferencesTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import static com.elitecorelib.core.CoreConstants.MAX_CHAR_WRITE_LIMIT;
import static com.elitecorelib.core.utility.ElitelibUtility.arabicToenglish;
import static com.elitecorelib.core.utility.ElitelibUtility.getCurrentAppVersionCode;
import static com.elitecorelib.core.utility.ElitelibUtility.isProbablyArabic;

/**
 * Created by Chirag Parmar on 4/19/2016.
 */
public class EliteLog {

    private static EliteLog instance = null;
    private final String MODULE = "EliteLog";
    private SecretKey key;
    private String formattedMessage = "";
    private String preMessage = "";
    private SharedPreferencesTask spTask = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();

    /*
     * Make Singleton class for batter performance */

    public static EliteLog getLogInstance() {

        if (instance == null) {
            instance = new EliteLog();
        }
        return instance;
    }

    private EliteLog() {
        generateSecreteKey();
    }

    /**
     * Generate Secrete Key for Logger Encode & Decode Process
     */
    private void generateSecreteKey() {

        BASE64Decoder decoder = new BASE64Decoder();
        byte[] encodedKey = null;
        try {
            encodedKey = decoder.decodeBuffer(CoreConstants.LOGGER_ENCRYPTION_KEY);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "DES");
    }

    /**
     * Decode Process of Logger
     *
     * @param encryptedString
     * @return
     * @throws Exception
     */
    private String decodeString(String encryptedString) throws Exception {
        // Get a cipher object.
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);

        //decode the BASE64 coded message
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] raw = decoder.decodeBuffer(encryptedString);

        //decode the message
        byte[] stringBytes = cipher.doFinal(raw);

        //converts the decoded message to a String
        String clear = new String(stringBytes, "UTF8");
        return clear;
    }

    /**
     * Encode Process of Logger
     *
     * @param plainString
     * @return
     * @throws Exception
     */
//    private String encodeString(String plainString) throws Exception {
//        // Get a cipher object.
//        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
//        cipher.init(Cipher.ENCRYPT_MODE, key);
//
//        byte[] stringbytes = plainString.getBytes("UTF8");
//
//        byte[] raw = cipher.doFinal(stringbytes);
//        //decode the BASE64 coded message
//        BASE64Encoder encoder = new BASE64Encoder();
//        String encrypted = encoder.encodeBuffer(raw);
//
//        return encrypted;
//    }


    /**
     * Format the String like json Format if Json available in string
     *
     * @param plainMessage
     */
    private void formatString(String plainMessage) {

        preMessage = "";
        formattedMessage = "";

        /**
         * Check JsonObject in String
         */
        if ((plainMessage.contains("{") && plainMessage.endsWith("}"))) {

            try {
                JSONObject jsonObject = new JSONObject(plainMessage.substring(plainMessage.indexOf("{")));
                formattedMessage = jsonObject.toString(4);
                preMessage = plainMessage.substring(0, plainMessage.indexOf("{"));

            } catch (JSONException je) {
                formattedMessage = plainMessage;
                EliteSession.eLog.e(MODULE, je.getMessage());
            }
        }
        /**
         * Check JsonArray in String
         */
        else if ((plainMessage.contains("[") && plainMessage.endsWith("]"))) {
            try {

                JSONArray jsonArray = new JSONArray(plainMessage.substring(plainMessage.indexOf("[")));
                formattedMessage = jsonArray.toString(4);
                preMessage = plainMessage.substring(0, plainMessage.indexOf("["));

            } catch (JSONException je) {
                formattedMessage = plainMessage;
                EliteSession.eLog.e(MODULE, je.getMessage());
            }
        }
        /**
         * if normal String available
         */
        else {
            formattedMessage = plainMessage;
        }
    }

    /**
     * Debug Mode without module class
     *
     * @param paramString
     * @return
     */
    public int d(String paramString) {
        formatString(paramString);

        /**
         * Only Development mode append the log in log file
         */
        if (spTask.getString(CoreConstants.LOGGER_MODE).endsWith(CoreConstants.DEVELOPMENT_MODE) ||
                spTask.getString(CoreConstants.LOGGER_MODE).endsWith("")) {

            appendLog(getCurrentDateTime() + " [DEBUG] " + " - [" + CoreConstants.LOGGER_TAG + "]  : " + preMessage + " " + formattedMessage);

        }

        if (CoreConstants.LOGGER_CONSOLE_ENABLE)
            return Log.d(CoreConstants.LOGGER_TAG, formattedMessage);
        else
            return 0;

    }

    /**
     * Debug Mode with module class
     *
     * @param paramString1
     * @param paramString2
     * @return
     */
    public int d(String paramString1, String paramString2) {

        formatString(paramString2);

        /**
         * Only Development mode append the log in log file
         */
        if (spTask.getString(CoreConstants.LOGGER_MODE).endsWith(CoreConstants.DEVELOPMENT_MODE) ||
                spTask.getString(CoreConstants.LOGGER_MODE).endsWith("")) {
            appendLog(getCurrentDateTime() + " [DEBUG] " + " - [" + paramString1 + "]  : " + preMessage + " " + formattedMessage);
        }
        if (CoreConstants.LOGGER_CONSOLE_ENABLE)
            return Log.d(paramString1, preMessage + " " + formattedMessage);
        else
            return 0;


    }

    /**
     * Error Mode without module class
     *
     * @param paramString
     * @return
     */
    public int e(String paramString) {

        /**
         * Both mode append the log in log file
         */
        if (spTask.getString(CoreConstants.LOGGER_MODE).endsWith(CoreConstants.DEVELOPMENT_MODE) ||
                spTask.getString(CoreConstants.LOGGER_MODE).endsWith(CoreConstants.RELEASE_MODE) ||
                spTask.getString(CoreConstants.LOGGER_MODE).endsWith("")) {

            appendLog(getCurrentDateTime() + " [ERROR] " + " - [" + CoreConstants.LOGGER_TAG + "]  : " + paramString);
        }
        if (paramString == null)
            paramString = "";
        if (CoreConstants.LOGGER_CONSOLE_ENABLE)
            return Log.e(CoreConstants.LOGGER_TAG, paramString);
        else
            return 0;

    }

    /**
     * Error Mode with module class
     *
     * @param paramString1
     * @param paramString2
     * @return
     */
    public int e(String paramString1, String paramString2) {

        /**
         * Both mode append the log in log file
         */
        if (spTask.getString(CoreConstants.LOGGER_MODE).endsWith(CoreConstants.DEVELOPMENT_MODE) ||
                spTask.getString(CoreConstants.LOGGER_MODE).endsWith(CoreConstants.RELEASE_MODE) ||
                spTask.getString(CoreConstants.LOGGER_MODE).endsWith("")) {
            appendLog(getCurrentDateTime() + " [ERROR] " + " - [" + paramString1 + "]  : " + paramString2);
        }
        if (paramString2 == null)
            paramString2 = "";
        if (CoreConstants.LOGGER_CONSOLE_ENABLE)
            return Log.e(paramString1, paramString2);
        else
            return 0;

    }

    /**
     * Info Mode without Module class
     *
     * @param paramString
     * @return
     */
    public int i(String paramString) {

        formatString(paramString);

        /**
         * Both mode append the log in log file
         */
        if (spTask.getString(CoreConstants.LOGGER_MODE).endsWith(CoreConstants.DEVELOPMENT_MODE) ||
                spTask.getString(CoreConstants.LOGGER_MODE).endsWith(CoreConstants.RELEASE_MODE) ||
                spTask.getString(CoreConstants.LOGGER_MODE).endsWith("")) {
            appendLog(getCurrentDateTime() + " [INFO]  " + " - [" + CoreConstants.LOGGER_TAG + "]  : " + preMessage + " " + formattedMessage);
        }
        if (CoreConstants.LOGGER_CONSOLE_ENABLE)
            return Log.i(CoreConstants.LOGGER_TAG, preMessage + " " + formattedMessage);
        else
            return 0;
    }

    /**
     * Info Mode with Module class
     *
     * @param paramString1
     * @param paramString2
     * @return
     */
    public int i(String paramString1, String paramString2) {

        formatString(paramString2);

        /**
         * Both mode append the log in log file
         */
        if (spTask.getString(CoreConstants.LOGGER_MODE).endsWith(CoreConstants.DEVELOPMENT_MODE) ||
                spTask.getString(CoreConstants.LOGGER_MODE).endsWith(CoreConstants.RELEASE_MODE) ||
                spTask.getString(CoreConstants.LOGGER_MODE).endsWith("")) {
            appendLog(getCurrentDateTime() + " [INFO]  " + " - [" + paramString1 + "]  : " + preMessage + " " + formattedMessage);
        }
        if (CoreConstants.LOGGER_CONSOLE_ENABLE)
            return Log.i(paramString1, preMessage + " " + formattedMessage);
        else
            return 0;
    }

    /**
     * getCurrent Date & Time
     *
     * @return
     */
    private String getCurrentDateTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(CoreConstants.LOGGER_DATETIME_FORMAT,Locale.ENGLISH);
        String formattedDate = df.format(c.getTime());
        /**
         * Convert arabic date to english
         */
        if(isProbablyArabic(formattedDate))
            formattedDate = arabicToenglish(formattedDate);

        // formattedDate have current date/time
        return formattedDate;
    }

    /**
     * append Log in file and Generate the Log file
     *
     * @param text
     */
    private void appendLog(String text) {
        if (!CoreConstants.LOGGER_FILE_ENABLE) {
            return;
        }

        int stringId = LibraryApplication.getLibraryApplication().getLibraryContext().getApplicationInfo().labelRes;
        String AppName = LibraryApplication.getLibraryApplication().getLibraryContext().getString(stringId);
        File dir = new File(Environment.getExternalStorageDirectory() + File.separator +  AppName.replace(" ", "") + File.separator);
        if (!dir.exists()) {
            try {
                dir.mkdirs();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(CoreConstants.LOGGER_DATETIME_FILE_FORMAT, Locale.ENGLISH);
        String formattedDate = df.format(c.getTime());


        /**
         * Convert arabic date to english
         */
        if(isProbablyArabic(formattedDate))
            formattedDate = arabicToenglish(formattedDate);

        File logFile = new File(Environment.getExternalStorageDirectory() + File.separator +AppName.replace(" ", "") + File.separator + formattedDate + "_V2" + CoreConstants.LOGGER_FILE_NAME);
        if (!logFile.exists()) {

            try {
                logFile.createNewFile();
                try {

                    /**
                     * Remove Logger File Call
                     */
                    EliteLogRemover.doFileRemoveOperation(LibraryApplication.getLibraryApplication().getLibraryContext());


                    EliteSession.eLog.d(MODULE, "MANUFACTURER :" + Build.MANUFACTURER);
                    EliteSession.eLog.d(MODULE, "MODEL :" + Build.MODEL);
                    EliteSession.eLog.d(MODULE, "RELEASE :" + Build.VERSION.RELEASE);
                    EliteSession.eLog.d(MODULE, "SDK :" + Build.VERSION.SDK);
                    EliteSession.eLog.d(MODULE, "Application Version :" + getCurrentAppVersionCode(LibraryApplication.getLibraryApplication().getLibraryContext()));
                    EliteSession.eLog.d(MODULE, "BRAND :" + Build.BRAND);
                    EliteSession.eLog.d(MODULE, "LIB VERSION :" + CoreConstants.LIBRARY_VERSION);
                    WifiManager wifiManager = (WifiManager) LibraryApplication.getLibraryApplication().getLibraryContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                    if (connectionInfo != null) {
                        EliteSession.eLog.d(MODULE, "WiFi MAC Address :" + connectionInfo.getMacAddress());
                    }
                    TelephonyManager tm = (TelephonyManager) LibraryApplication.getLibraryApplication().getLibraryContext().getSystemService(Context.TELEPHONY_SERVICE);
                    if (tm.getSubscriberId() != null) {
                        EliteSession.eLog.d(MODULE, "IMSI :" + tm.getSubscriberId());
                        EliteSession.eLog.d(MODULE, "Country Code :" + tm.getSimCountryIso());

                    }

                    ArrayList<String> mccMNC = ElitelibUtility.getMccMnc(tm);

                    EliteSession.eLog.d(MODULE, "MCC :" + mccMNC.get(0));
                    EliteSession.eLog.d(MODULE, "MNC :" + mccMNC.get(1));

                    if (tm.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {
                        final GsmCellLocation location = (GsmCellLocation) tm.getCellLocation();
                        try {
                            if (location != null) {
                                EliteSession.eLog.d(MODULE, "LAC :" + location.getLac());
                                EliteSession.eLog.d(MODULE, "CID :" + location.getCid());
                            }
                        } catch (Exception e) {
                            EliteSession.eLog.e(MODULE, "Error while getting LAC/CID");
                        }

                    }
                    if (tm.getDeviceId() != null) {
                        EliteSession.eLog.d(MODULE, "IMEI :" + tm.getDeviceId());

                    }
                    if (tm.getNetworkOperator() != null)
                        EliteSession.eLog.d(MODULE, "SIM Operater :" + tm.getSimOperatorName());

                } catch (Exception e) {
                    EliteSession.eLog.e(MODULE, e.getMessage());
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter bow = new BufferedWriter(new FileWriter(logFile, true));

            int maxLength = (text.length() < MAX_CHAR_WRITE_LIMIT)?text.length():MAX_CHAR_WRITE_LIMIT;
            text = text.substring(0, maxLength);
            try {
                String encrypted = "";

                /**
                 * Encryption
                 */

                if (CoreConstants.LOGGER_ENCRYPTION_ENABLE)
                    encrypted = ElitelibUtility.encrypt(text + "\n");
                else
                    encrypted = text + "\n";
                bow.append(encrypted);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            bow.flush();
            bow.close();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

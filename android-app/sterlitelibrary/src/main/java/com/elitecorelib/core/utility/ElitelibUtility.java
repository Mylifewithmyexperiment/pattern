package com.elitecorelib.core.utility;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrength;
import android.telephony.PhoneStateListener;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.elitecore.eliteconnectlibrary.R;
import com.elitecore.elitesmp.utility.ElitePropertiesUtil;
import com.elitecore.elitesmp.utility.EliteSMPUtility;
import com.elitecore.wifi.api.EliteWiFIConstants;
import com.elitecore.wifi.listener.OnInternetCheckCompleteListner;
import com.elitecore.wifi.listener.OnWiFiTaskCompleteListner;
import com.elitecore.wifi.pojo.PojoConnection;
import com.elitecorelib.andsf.utility.CustomPhoneStateListner;
import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.dao.DBOperation;
import com.elitecorelib.core.pojo.PojoConfigModel;
import com.elitecorelib.core.pojo.PojoConfigModelResponse;
import com.elitecorelib.core.pojo.PojoGeocodeResponse;
import com.elitecorelib.core.pojo.PojoLocation;
import com.elitecorelib.core.pojo.PojoProfile;
import com.elitecorelib.core.pojo.PojoServerKeyMapping;
import com.elitecorelib.core.pojo.PojoSubscriber;
import com.elitecorelib.core.pojo.PojoWiFiConnections;
import com.elitecorelib.core.pojo.PojoWiFiProfiles;
import com.elitecorelib.core.receiver.AnalyticReceiver;
import com.elitecorelib.core.receiver.MyBroadcastReceiver;
import com.elitecorelib.core.security.BASE64Decoder;
import com.elitecorelib.core.security.BASE64Encoder;
import com.elitecorelib.core.services.ConnectionManagerCompleteListner;
import com.elitecorelib.core.services.ConnectionManagerTaskNew;
import com.elitecorelib.core.services.InterNetAvailabilityCheckTask;
import com.elitecorelib.wifi.utility.WifiUtility;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import fr.bmartel.speedtest.SpeedTestReport;
import fr.bmartel.speedtest.SpeedTestSocket;
import fr.bmartel.speedtest.inter.ISpeedTestListener;
import fr.bmartel.speedtest.model.SpeedTestError;

import static android.content.Context.TELEPHONY_SERVICE;
import static com.elitecore.elitesmp.utility.EliteSMPUtilConstants.SUBPARAM10;
import static com.elitecore.elitesmp.utility.EliteSMPUtilConstants.SUCCESS;
import static com.elitecore.elitesmp.utility.EliteSMPUtilConstants.USERAGENTTYPE;
import static com.elitecore.elitesmp.utility.EliteSMPUtilConstants.VALUE_USERAGENTTYPE;
import static com.elitecorelib.core.CoreConstants.AD_CLICK_URL;
import static com.elitecorelib.core.CoreConstants.APIDENTITY;
import static com.elitecorelib.core.CoreConstants.BROADBAND_PASSWORD;
import static com.elitecorelib.core.CoreConstants.BROADBAND_USERNAME;
import static com.elitecorelib.core.CoreConstants.CALLEDSTATIONID;
import static com.elitecorelib.core.CoreConstants.DOWNLOAD;
import static com.elitecorelib.core.CoreConstants.KEY_HIDDENPARAMS;
import static com.elitecorelib.core.CoreConstants.KEY_OFFLOADSSID;
import static com.elitecorelib.core.CoreConstants.KEY_OFFLOADSSID_LOCAL;
import static com.elitecorelib.core.CoreConstants.LIBRARY_PACKAGE_NAME;
import static com.elitecorelib.core.CoreConstants.NAS_IDENTIFIER;
import static com.elitecorelib.core.CoreConstants.NFCALLBACKINTERVAL;
import static com.elitecorelib.core.CoreConstants.NFCALLBACKMODE;
import static com.elitecorelib.core.CoreConstants.NFCALLBACK_LOCATIONMODE_VALUE;
import static com.elitecorelib.core.CoreConstants.NFCALLBACK_TIMEMODE_VALUE;
import static com.elitecorelib.core.CoreConstants.REGISTRATIONJSON;
import static com.elitecorelib.core.CoreConstants.SYNCTIME;
import static com.elitecorelib.core.CoreConstants.TYPE_OFFLOAD_WIFI;
import static com.elitecorelib.core.CoreConstants.UPLOAD;
import static com.elitecorelib.core.CoreConstants.operatingSystemValue;
import static com.elitecorelib.core.utility.SharedPreferencesConstant.MONETIZATIONREG;

public class ElitelibUtility {

    private static final String MODULE = "ElitelibUtility";
    private static final char[] PASSWORD = "enfldsgbnlsngdlksdsgm".toCharArray();
    private static final byte[] SALT = {(byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12, (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12,};
    public static LatLng currentLocation;
    public static String locationResponse = "";
    public static LatLng currentLatLong;
    public static boolean result = false;
    public static int status = 1;
    private static SharedPreferencesTask spTask = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();
    private static boolean addressFound;
    private static SnackbarView mSnackbarView;
    private static Properties config_properties = null;
    private static Boolean isAddLoaded = false;

    // Download & Upload spped calculate with third party library
    private static String SPEED_TEST_SERVER_URI_DL = "http://ipv4.ikoula.testdebit.info/100M.iso";
    private static String SPEED_TEST_SERVER_URI_DL_UPLOAD = "http://ipv4.ikoula.testdebit.info/";
    private static int REPORT_INTERVAL = 1000;
    private static int SPEED_TEST_DURATION = 15000;
    private static String DownloadSpeed = "";
    private static String UploadSpeed = "";

    private static double LatencyValue = 0;
    private static int LatencySentPackets = 0;
    private static int LatencyReceivePackets = 0;


    static {
        try {
            if (config_properties == null) {
                InputStream is = null;
                try {

                    config_properties = new Properties();
                    Context mContext = LibraryApplication.getLibraryApplication().getLibraryContext();
                    int test = mContext.getResources().getIdentifier("default_configuration", "raw", mContext.getPackageName());

                    if (test > 0) {
                        is = LibraryApplication.getLibraryApplication().getLibraryContext().getResources().openRawResource(R.raw.default_configuration);
                        config_properties.load(is);
                        is.close();
                    }

                    int testAssert = mContext.getResources().getIdentifier(CoreConstants.CONFIG_FILE_NAME, "assets", mContext.getPackageName());

                    if (testAssert > 0) {
                        is = LibraryApplication.getLibraryApplication().getLibraryContext().getAssets().open(CoreConstants.CONFIG_FILE_NAME);
                        config_properties.load(is);
                        is.close();
                    }

                } catch (NoClassDefFoundError ie) {
                    EliteSession.eLog.e(MODULE, ie.getMessage());
                } catch (IOException ie) {
                    EliteSession.eLog.e(MODULE, ie.getMessage());

                } catch (Exception e) {
                    EliteSession.eLog.e(MODULE, e.getMessage());

                }
            }
        } catch (NoClassDefFoundError ie) {
            EliteSession.eLog.e(MODULE, ie.getMessage());
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    private String ADVERTISEMENT_LANGUAGE = "ADVERTISEMENT_LANGUAGE";

    public static void updateDefaultConfigProperty() {

        int x = 2;
        try {
            EliteSession.eLog.d(MODULE, "setting config property from property file first time.");
            spTask.saveString(CoreConstants.SERVERUNREACHBLEMESSAGE, config_properties.getProperty(CoreConstants.SERVERUNREACHBLEMESSAGE));
            spTask.saveString(CoreConstants.NFCALLBACKMODE, config_properties.getProperty(CoreConstants.NFCALLBACKMODE));
            spTask.saveString(CoreConstants.SYNCINTERVALTIME, config_properties.getProperty(CoreConstants.SYNCINTERVALTIME));
            try {
                int syncTime = Integer.parseInt(config_properties.getProperty(CoreConstants.SYNCINTERVALTIME));
                spTask.saveLong(CoreConstants.NEXT_SYNCINTERVALTIME, System.currentTimeMillis() + (syncTime * 60 * 1000));
            } catch (Exception e) {
                // TODO: handle exception
                EliteSession.eLog.d(MODULE, "setting config property Errot to set next time interval");
            }
            spTask.saveString(CoreConstants.NFCALLBACKINTERVAL, config_properties.getProperty(CoreConstants.NFCALLBACKINTERVAL));
            spTask.saveString(CoreConstants.LOCATIONSYNCRANGE, config_properties.getProperty(CoreConstants.LOCATIONSYNCRANGE));
            spTask.saveString(CoreConstants.ADREFRESHINTERVAL, config_properties.getProperty(CoreConstants.ADREFRESHINTERVAL));
            spTask.saveString(CoreConstants.COMMUNICATIONMODE, config_properties.getProperty(CoreConstants.COMMUNICATIONMODE));
            spTask.saveInt(CoreConstants.ADENABLE, Integer.parseInt(config_properties.getProperty(CoreConstants.ADENABLE)));
            spTask.saveString(CoreConstants.OTP_FLOW_ENABLE, config_properties.getProperty(CoreConstants.OTP_FLOW_ENABLE));

            EliteSession.eLog.d(MODULE, "setting config property from property file  completed");
        } catch (Exception e) {
            // TODO: handle exception
            EliteSession.eLog.d(MODULE, "setting config property from property file  throws exception");
        }


    }

    public static String encrypt(String arg) {

        try {
            String secure = CoreConstants.LOGGER_ENCRYPTION_AES_KEY;
            Cipher myCypherOut = Cipher.getInstance("AES");
            myCypherOut.init(Cipher.ENCRYPT_MODE, (new SecretKeySpec((secure.getBytes()), "AES")));
            BASE64Encoder encoder = new BASE64Encoder();
            String encryptedString = encoder.encode(myCypherOut.doFinal(arg.getBytes()));
            return encryptedString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Read the object from Base64 string.
     */
    public static Object fromString(String s) throws IOException,
            ClassNotFoundException {

        byte[] data = new BASE64Decoder().decodeBuffer(s);

        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(data));
        Object o = ois.readObject();
        ois.close();
        return o;
    }

    /**
     * Write the object to a Base64 string.
     */
    public static String toString(Serializable o) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(o);
            oos.close();
            return new String(new BASE64Encoder().encode(baos.toByteArray()));
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

    public static void addAnalytic(int eventID, String eventValue, String... args) {

        if (spTask.getInt(CoreConstants.EVENTANALYTICSENABLE) == 1) {
            LibraryApplication.getLibraryApplication().getEliteAnalytics().insertAnalytic(eventID,
                    System.currentTimeMillis(), eventValue, args);
        }
    }

    public static String getAccountName(Activity activity) {

        String accountName = null;

        AccountManager manager = (AccountManager) activity.getSystemService(Context.ACCOUNT_SERVICE);
        Account[] list = manager.getAccounts();
        for (Account account : list) {
            if (account.type.equalsIgnoreCase("com.google")) {
                accountName = account.name;
                break;
            }
        }
        return accountName;
    }

    public static String getIMSI(boolean getDefaultIfFail, boolean checkOperator, String simpOperatorNamePassed) {
        String imsi = null;
        Context context = LibraryApplication.getLibraryApplication().getLibraryContext();
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        try {
            EliteSession.eLog.d(MODULE + " primary imsi is" + telephony.getSubscriberId());
            for (String currentImsi : CoreConstants.defaultIMSIArray) {
                if (currentImsi.compareTo(telephony.getSubscriberId()) == 0) {
                    return telephony.getSubscriberId();
                }
            }
            if (checkOperator && telephony.getSimOperatorName() != null && (telephony.getSimOperatorName().contains(simpOperatorNamePassed) || telephony.getNetworkOperatorName().contains(simpOperatorNamePassed)) && telephony.getSubscriberId() != null) {
                EliteSession.eLog.d(MODULE, "First SIM Operator Name from first slot is :" + telephony.getSimOperatorName());
                EliteSession.eLog.d(MODULE, "First SIM Operator Name to be compared :" + simpOperatorNamePassed);
                imsi = telephony.getSubscriberId();

            } else {
                if (telephony.getSimOperatorName() != null)
                    EliteSession.eLog.d(MODULE, "First SIM Operator Name from first slot is :" + telephony.getSimOperatorName());
                try {
                    EliteSession.eLog.d(MODULE, "Checking IMSI with getSecondSlotIMSIWithgetSubscriberIdDs");
                    imsi = getSecondSlotIMSIWithgetSubscriberIdDs(checkOperator, simpOperatorNamePassed);

                } catch (Exception e) {
                    EliteSession.eLog.e(MODULE, "Checking IMSI with getSecondSlotIMSIWithgetSubscriberIdDs " + e.getMessage());
                }


                if (imsi == null) {
                    try {
                        EliteSession.eLog.d(MODULE, "Checking IMSI with getSecondSlotIMSIWithgetSubscriberId");
                        imsi = getSecondSlotIMSIWithgetSubscriberId(checkOperator, simpOperatorNamePassed);

                    } catch (Exception e) {
                        EliteSession.eLog.e(MODULE, "Checking IMSI with getSecondSlotIMSIWithgetSubscriberId " + e.getMessage());
                    }

                }
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE + " " + e.getMessage());
        }
        if (getDefaultIfFail && imsi == null) {
            imsi = telephony.getSubscriberId();
        }
        return imsi;
    }

    private static String getSecondSlotIMSIWithgetSubscriberId(boolean checkOperator, String simpOperatorNamePassed) {
        String imsi = null;
        try {
            EliteSession.eLog.e(MODULE + " in first catch Exception, not able to fetch imsi using getSimOperatorNameDs in second slot");
            EliteSession.eLog.d(MODULE + " Cheking for second slot operator name with getSimOperatorName");
            try {

                Context context = LibraryApplication.getLibraryApplication().getLibraryContext();
                TelephonyManager telephony = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
                String simOperatorNameRead = null;


                Class<?> telephonyClass = Class.forName(telephony.getClass().getName());
                Class<?>[] methodParameter = new Class[1];
                methodParameter[0] = int.class;
                Method getSimOperatorName = telephonyClass.getMethod("getSimOperatorName", methodParameter);
                if (getSimOperatorName != null) {
                    Object[] slotNumber = new Object[1];
                    Method getSubscriberId;
                    EliteSession.eLog.d(MODULE + " SIM Operator Name is not " + simpOperatorNamePassed + " trying second slot");
                    slotNumber[0] = 1;
                    simOperatorNameRead = (String) getSimOperatorName.invoke(telephony, slotNumber);
                    if (checkOperator) {
                        if (simOperatorNameRead != null && simOperatorNameRead.contains(simpOperatorNamePassed)) {
                            EliteSession.eLog.d(MODULE + " SIM Operator Name from Second slot is :" + simOperatorNameRead);
                            getSubscriberId = telephonyClass.getMethod("getSubscriberId", methodParameter);
                            slotNumber[0] = 1;
                            if (getSubscriberId != null) {
                                imsi = (String) getSubscriberId.invoke(telephony, slotNumber);
                                EliteSession.eLog.d(MODULE + " IMSI from second slot is :" + imsi);
                            }
                        }
                    } else {
                        EliteSession.eLog.d(MODULE + " SIM Operator Name from Second slot is :" + simOperatorNameRead);
                        getSubscriberId = telephonyClass.getMethod("getSubscriberId", methodParameter);
                        slotNumber[0] = 1;
                        if (getSubscriberId != null) {
                            imsi = (String) getSubscriberId.invoke(telephony, slotNumber);
                            EliteSession.eLog.d(MODULE + " IMSI from second slot is :" + imsi);
                        }

                    }

                }
            } catch (Exception ee) {
                //EliteSession.eLog.e(MODULE + " "+ee.getMessage());
                EliteSession.eLog.e(MODULE + " in second catch Exception, not able to fetch imsi using getSimOperatorName");
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE + "getSecondSlotIMSIWithgetSubscriberId :" + e.getMessage());

        }
        return imsi;
    }

    private static String getSecondSlotIMSIWithgetSubscriberIdDs(boolean checkOperator, String simpOperatorNamePassed) {
        String imsi = null;
        try {
            Context context = LibraryApplication.getLibraryApplication().getLibraryContext();
            TelephonyManager telephony = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            String simOperatorNameRead = null;

            EliteSession.eLog.d(MODULE + " Cheking for second slot operator name with getSimOperatorNameDs");
            Class<?> telephonyClass = Class.forName(telephony.getClass().getName());
            Class<?>[] methodParameter = new Class[1];
            methodParameter[0] = int.class;
            Method getSimOperatorNameDs = telephonyClass.getMethod("getSimOperatorNameDs", methodParameter);
            if (getSimOperatorNameDs != null) {
                Object[] slotNumber = new Object[1];
                slotNumber[0] = 1;
                simOperatorNameRead = (String) getSimOperatorNameDs.invoke(telephony, slotNumber);
                Method getSubscriberIdDs;
                if (checkOperator) {
                    if (simOperatorNameRead != null && simOperatorNameRead.contains(simpOperatorNamePassed)) {
                        EliteSession.eLog.d(MODULE + " SIM Operator Name from second slot is :" + simOperatorNameRead);
                        getSubscriberIdDs = telephonyClass.getMethod("getSubscriberIdDs", methodParameter);
                        slotNumber[0] = 1;
                        if (getSubscriberIdDs != null) {
                            imsi = (String) getSubscriberIdDs.invoke(telephony, slotNumber);
                            EliteSession.eLog.d(MODULE + " IMSI from second slot is :" + imsi);
                        }
                    }
                } else {
                    EliteSession.eLog.d(MODULE + " SIM Operator Name from second slot is :" + simOperatorNameRead);
                    getSubscriberIdDs = telephonyClass.getMethod("getSubscriberIdDs", methodParameter);
                    slotNumber[0] = 1;
                    if (getSubscriberIdDs != null) {
                        imsi = (String) getSubscriberIdDs.invoke(telephony, slotNumber);
                        EliteSession.eLog.d(MODULE + " IMSI from second slot is :" + imsi);
                    }
                }
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE + "getSecondSlotIMSIWithgetSubscriberIdDs :" + e.getMessage());

        }
        return imsi;
    }

    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }

    public static double getDistanceBetweenLocations(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'M') {
            dist = (dist * 1.609344) * 1000;
        }
        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /* :: This function converts radians to decimal degrees : */
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    public static boolean isNetworkWorking(String url) {
        Runtime runtime = Runtime.getRuntime();
        try {

            Process ipProcess = runtime.exec("/system/bin/ping -c 1 " + url);
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static String getDeviceId(Context paramContext) {
        /*
        try
	    {
	      String str1 = ((TelephonyManager)paramContext.getSystemService("phone")).getDeviceId();
	      String str2 = new UUID("airtel".hashCode(), str1.hashCode() << 32 | "hangout".hashCode()).toString();
	      return str2;
	    }
	    catch (Exception localException)
	    {
	      localException.printStackTrace();
	    }
	    return null;
	  }*/
        return null;
    }

    public static long convertBytesToKb(long dataInBytes) {
        long kbValue = 0;
        try {
            kbValue = dataInBytes / 1024;
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());

        }
        return kbValue;
    }

    public static String formatSpeedWithPostFix(long size) {
        String hrSize = null;

        double b = size;
        double k = size / 1024.0;
        double m = ((size / 1024.0) / 1024.0);
        double g = (((size / 1024.0) / 1024.0) / 1024.0);
        double t = ((((size / 1024.0) / 1024.0) / 1024.0) / 1024.0);

        DecimalFormat dec = new DecimalFormat("0.00");
        if (m > 1) {
            hrSize = dec.format(m).concat(" MBPS");
        } else if (k > 1) {
            hrSize = dec.format(k).concat(" KBPS");
        } else {
            hrSize = dec.format(b).concat(" Bytes");
        }
        return hrSize;
    }

    public static void turnGPSOn(Context ctx) {
        Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
        intent.putExtra("enabled", true);
        ctx.sendBroadcast(intent);

        String provider = Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (!provider.contains("gps")) { //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            ctx.sendBroadcast(poke);


        }
    }

    // automatic turn off the gps
    public static void turnGPSOff(Context ctx) {
        String provider = Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (provider.contains("gps")) { //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            ctx.sendBroadcast(poke);
        }
    }

    public static JSONObject pozotoJSONObject(Object object) {
        try {
            return new JSONObject(new Gson().toJson(object));
        } catch (NoClassDefFoundError ie) {
            EliteSession.eLog.e(MODULE, ie.getMessage());
            return new JSONObject();
        } catch (JSONException je) {
            EliteSession.eLog.d(MODULE, je.getMessage());
            return new JSONObject();
        }
    }

    public static JSONArray listtoJSONArray(List<Object> arrayList) {
        try {
            return new JSONArray(new Gson().toJson(arrayList));
        } catch (JSONException e) {
            EliteSession.eLog.d(MODULE, e.getMessage());
            return new JSONArray();
        }
    }

    @SuppressWarnings("deprecation")
    public static PojoSubscriber setUserRegistrationInformation(PojoSubscriber subscriberObj, Context mContext) {
        EliteSession.eLog.d(MODULE, "setUserRegistrationInformation Invoked");
        //Setting device information In subscriber data
        EliteSession.eLog.d(MODULE, "Setting Device Information");
        subscriberObj.setSubscriberProfileType(CoreConstants.SUBSCRIBERPROFILETYPE_VALUE + "");
        try {
            DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
            subscriberObj.setScreenWidth(metrics.widthPixels);
            subscriberObj.setScreenHeight(metrics.heightPixels);
            subscriberObj.setScreenDensity(metrics.densityDpi);
            PackageInfo pInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            subscriberObj.setAppVersion(pInfo.versionName);

        } catch (Exception e) {
            EliteSession.eLog.d(MODULE, "Error on device info screen and type");
        }

        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(TELEPHONY_SERVICE);
        if (tm.getSubscriberId() != null) {
            subscriberObj.setImsi(tm.getSubscriberId());

        }
        if (LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY) != null) {
            subscriberObj.setSecretKey(LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY));
        }
        if (tm.getSimCountryIso() != null) {
            try {
                subscriberObj.setCountryCode(Integer.parseInt(GetCountryZipCode(tm.getSimCountryIso())));
            } catch (Exception e) {
                // TODO: handle exception
                EliteSession.eLog.e(MODULE, e.getMessage());
            }
        }

        ArrayList<String> mccMNC = getMccMnc(tm);

        subscriberObj.setMCC(Integer.parseInt(mccMNC.get(0)));
        subscriberObj.setMNC(Integer.parseInt(mccMNC.get(1)));

        if (tm.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {
            final GsmCellLocation location = (GsmCellLocation) tm.getCellLocation();
            try {
                if (location != null) {
                    subscriberObj.setLAC(location.getLac());
                    subscriberObj.setCellId(location.getCid());
                    // msg.setText("LAC: " + location.getLac() + " CID: " + location.getCid());
                }
            } catch (Exception e) {

            }

        }
        if (tm.getDeviceId() != null) {
            subscriberObj.setImei(tm.getDeviceId());
            spTask.saveString(CoreConstants.imei, tm.getDeviceId());
        }
        if (Build.MANUFACTURER != null)
            subscriberObj.setManufacturer(Build.MANUFACTURER);
        if (Build.MODEL != null)
            subscriberObj.setModel(Build.MODEL);

        if (tm.getSimOperator() != null)
            subscriberObj.setSimOperator(tm.getSimOperatorName());

        if (Build.BRAND != null)
            subscriberObj.setBrand(Build.BRAND);
        if (tm.getNetworkOperator() != null)
            subscriberObj.setNetworkOperator(tm.getNetworkOperatorName());

        CoreConstants.Codenames c = CoreConstants.Codenames.getCodename();
        subscriberObj.setOperatingSystem(operatingSystemValue);
        if (c != null) {
            subscriberObj.setOSVersion(c.name() + "-" + Build.VERSION.RELEASE);
        } else {
            subscriberObj.setOperatingSystem(Build.VERSION.SDK_INT + "");

        }
        //Get register device id from GCM server
        try {
            final String regId = spTask.getString(CoreConstants.DEVICEID);
            subscriberObj.setRegisteredDeviceId(regId);
            subscriberObj.setIsNotificationOn(1);
        } catch (Exception e) {
            EliteSession.eLog.d(MODULE, "GCM GCMRegistrar getRegistrationId Error");
        }
        //set application locale
        try {
            String str = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.LOCALE_KEY);
            subscriberObj.setLocale(str);
        } catch (Exception e) {
            // TODO: handle exception
        }
        try {
            if (spTask.getString(CoreConstants.ADVERTISEMENT_LANGUAGE).trim().length() > 0) {

            }

        } catch (Exception e) {
            // TODO: handle exception
        }


        return subscriberObj;
    }

    public static PojoSubscriber setFbInformation(PojoSubscriber subscriberObj) throws JSONException {
        //Setting facebook parameters
        EliteSession.eLog.d(MODULE, "Facebook User : " + LibraryApplication.getLibraryApplication().getFaceBookUser());
        EliteSession.eLog.d(MODULE, "Setting Facebook User Information");
        subscriberObj.setRegisterWith(CoreConstants.REGISTERWITH_FACEBOOK);
        JSONObject facebookUser = LibraryApplication.getLibraryApplication().getFaceBookUser();
        if (!facebookUser.getString("id").toString().equals("")) {
            subscriberObj.setImageURL("http://graph.facebook.com/" + facebookUser.getString("id").toString() + "/picture?type=large");
            spTask.saveString(CoreConstants.Pref_IMAGEURL, "http://graph.facebook.com/" + facebookUser.getString("id").toString() + "/picture?type=large");
        }

        if (!facebookUser.getString("email").toString().equals("")) {
            String emailid = facebookUser.getString("email").toString();
            subscriberObj.setUserName(emailid);
        }

        if (!facebookUser.getString("name").equals("")) {
            List<String> myList = new ArrayList<String>(Arrays.asList(facebookUser.getString("name").split(" ")));

            if (!myList.get(0).toString().equals(""))
                subscriberObj.setFirstName(myList.get(0).toString());

            if (!myList.get(1).toString().equals(""))
                subscriberObj.setLastName(myList.get(1).toString());
        }

        spTask.saveString(CoreConstants.GoogleFbName, facebookUser.getString("name").toString() + CoreConstants.FbLogin);
        spTask.saveString(SharedPreferencesConstant.FIRST_NAME, facebookUser.get("name").toString());
        spTask.saveString(SharedPreferencesConstant.EMAIL, facebookUser.getString("email").toString());

        try {
            if (!facebookUser.getString("birthday").equals("")) {
                try {
                    subscriberObj.setBirthDate(stringToDate(facebookUser.getString("birthday"), CoreConstants.DATE_FORMAT));
                    subscriberObj.setAge(ElitelibUtility.calculateAge(subscriberObj.getBirthDate()));
                } catch (Exception e) {
                    EliteSession.eLog.d(MODULE, "Timestmap Conversion Error");
                }
            }
        } catch (JSONException e) {
            EliteSession.eLog.e(MODULE, "Birthdate not availabele for this facebook user");
        }
        try {
            if (!facebookUser.getString("gender").equals("")) {
                String gender = facebookUser.getString("gender").toString();
                subscriberObj.setGender(gender);
            }
        } catch (JSONException e) {
            EliteSession.eLog.e(MODULE, "Gender not availabele for this facebook user");
        }

        //Adding mobile number to subscriber data from shared preferrences
        if (spTask.getString(CoreConstants.MSISDN) != null) {
            subscriberObj.setMsisdn(spTask.getString(CoreConstants.MSISDN));
        }
        return subscriberObj;
    }

    public static PojoSubscriber setGoogleInformation(PojoSubscriber subscriberObj) {
        //Setting GOOGLE parameters
        GoogleSignInAccount mGoogleSignInAccount = LibraryApplication.getLibraryApplication().getmGoogleSignInAccount();

        subscriberObj.setRegisterWith(CoreConstants.REGISTERWITH_GOOGLE);
        EliteSession.eLog.d(MODULE, "Setting Google User Information");
        if (mGoogleSignInAccount.getPhotoUrl() != null) {
            subscriberObj.setImageURL(mGoogleSignInAccount.getPhotoUrl().toString());
            spTask.saveString(CoreConstants.Pref_IMAGEURL, mGoogleSignInAccount.getPhotoUrl().toString());
        }

        if (!mGoogleSignInAccount.getDisplayName().equals("")) {
            subscriberObj.setFirstName(mGoogleSignInAccount.getGivenName());
            subscriberObj.setLastName(mGoogleSignInAccount.getFamilyName());

            spTask.saveString(CoreConstants.GoogleFbName, subscriberObj.getFirstName() + " " + subscriberObj.getLastName() + CoreConstants.GoogleLogin);
            spTask.saveString(SharedPreferencesConstant.FIRST_NAME, subscriberObj.getFirstName());
        }


//        Person googleUser = LibraryApplication.getLibraryApplication().getGoogleUser();
//        EliteSession.eLog.d(MODULE, "Google User : " + LibraryApplication.getLibraryApplication().getGoogleUser());
//        subscriberObj.setRegisterWith(CoreConstants.REGISTERWITH_GOOGLE);
//        EliteSession.eLog.d(MODULE, "Setting Google User Information");
//
//        if (googleUser.getImage() != null) {
//            subscriberObj.setImageURL(googleUser.getImage().getUrl());
//            spTask.saveString(CoreConstants.Pref_IMAGEURL, googleUser.getImage().getUrl());
//        }
//
//        if (googleUser.hasBirthday()) {
//            try {
//                String columns[] = googleUser.getBirthday().split("-");
//                Timestamp t;
//                if (columns[0].compareTo("0000") == 0) {
//                    t = new Timestamp(-3800, Integer.parseInt(columns[1]) - 1, Integer.parseInt(columns[2]), 0, 0, 0, 0);
//                    subscriberObj.setBirthDate(t);
//                    subscriberObj.setAge(0);
//                } else {
//                    t = new Timestamp(Integer.parseInt(columns[0]) - 1900, Integer.parseInt(columns[1]) - 1, Integer.parseInt(columns[2]), 0, 0, 0, 0);
//                    subscriberObj.setBirthDate(t);
//                    subscriberObj.setAge(ElitelibUtility.calculateAge(t));
//
//                }
//            } catch (Exception e) {
//                EliteSession.eLog.d(MODULE, "Timestmap Conversion Error");
//            }
//        }
//        EliteSession.eLog.i(MODULE, "organization=" + googleUser.hasOrganizations());
//        if (googleUser.hasOrganizations()) {
//            String education = "";
//            List<String> eduList = new ArrayList<String>();
//            for (int i = 0; i < googleUser.getOrganizations().size(); i++) {
////                EliteSession.eLog.i(MODULE,"organization type  >> " + googleUser.getOrganizations().get(i).getType() + " >>> " + googleUser.getOrganizations().get(i).getTitle());
//                if (googleUser.getOrganizations().get(i).getType() != 0) {
//                    if (googleUser.getOrganizations().get(i).getTitle() != null && !googleUser.getOrganizations().get(i).getTitle().isEmpty()) { /* do your stuffs here */
//                        eduList.add(googleUser.getOrganizations().get(i).getTitle());
//                    }
//                }
//            }
////            EliteSession.eLog.i(MODULE,"organization array size  >> " + eduList.size() + " >> " + eduList.get(0).toString());
//            if (eduList.size() > 1) {
//                String listString = "";
//
//                for (String s : eduList) {
//                    listString += s + ",";
//                }
//                if (listString != null && listString.length() > 0 && listString.charAt(listString.length() - 1) == ',') {
//                    listString = listString.substring(0, listString.length() - 1);
//                }
//                education = listString;
//
//            } else if (eduList.size() == 1) {
//                education = eduList.get(0).toString();
//            }
//            EliteSession.eLog.i(MODULE, "organization name=" + education);
//            subscriberObj.setEducation(education);
//        }
//        if (googleUser.hasCurrentLocation()) {
//            subscriberObj.setLocation(googleUser.getCurrentLocation());
//
//        }
//        if (googleUser.hasPlacesLived()) {
//            List<Person.PlacesLived> list = googleUser.getPlacesLived();
//            String location = "";
//            for (int i = 0; i < list.size(); i++) {
//                if ((i != list.size() - 1)) {
//                    location += list.get(i).getValue() + ",";
//                } else {
//                    location += list.get(i).getValue();
//                }
//            }
//            subscriberObj.setLocation(location);
//        }
//        if (googleUser.hasName()) {
//            if (googleUser.getName().hasGivenName())
//                subscriberObj.setFirstName(googleUser.getName().getGivenName());
//            if (googleUser.getName().hasFamilyName())
//                subscriberObj.setLastName(googleUser.getName().getFamilyName());
//
//            spTask.saveString(CoreConstants.GoogleFbName, subscriberObj.getFirstName() + " " + subscriberObj.getLastName() + CoreConstants.GoogleLogin);
//            spTask.saveString(SharedPreferencesConstant.FIRST_NAME, subscriberObj.getFirstName());
//        }
//        if (googleUser.hasRelationshipStatus()) {
//            loadGoogleRelationShip();
//            subscriberObj.setRelationshipStatus(CoreConstants.googleRelationShipHashMap.get(googleUser.getRelationshipStatus()));
//        }
//        if (googleUser.hasGender()) {
//            if (googleUser.getGender() == 0) {
//                subscriberObj.setGender(CoreConstants.GENDERMALE);
//            } else if (googleUser.getGender() == 1) {
//                subscriberObj.setGender(CoreConstants.GENDERFEMALE);
//            } else {
//                subscriberObj.setGender(CoreConstants.GENDEROTHER);
//            }
//        }
//        if (googleUser.hasAgeRange()) {
//            String ageRange = "";
//            if (googleUser.getAgeRange().hasMin()) {
//                ageRange += googleUser.getAgeRange().getMin() + "-";
//
//            }
//            if (googleUser.getAgeRange().hasMax()) {
//                ageRange += googleUser.getAgeRange().getMax();
//            }
//            subscriberObj.setAgeRange(ageRange);
//        }
        //			if(googleUser.hasLanguage())
        //			{
        //				subscriberObj.setLocale(googleUser.getLanguage());
        //			}

        if (LibraryApplication.getLibraryApplication().getLoggedInUserName() != null)
            subscriberObj.setUserName(LibraryApplication.getLibraryApplication().getLoggedInUserName());
        //Adding mobile number to subscriber data from shared preferrences
        if (spTask.getString(CoreConstants.MSISDN) != null) {
            subscriberObj.setMsisdn(spTask.getString(CoreConstants.MSISDN));
        }

        return subscriberObj;
    }

    public static void eventAnalyticProcess(Context ctx, String... args) {

        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(ctx, AnalyticReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                ctx, CoreConstants.ANALYTIC_REQUEST_CODE, intent, 0);

        if (spTask.getInt(CoreConstants.EVENTANALYTICSENABLE) == 1) {
            EliteSession.eLog.d(MODULE, "Event Analytic Enable, set Analytic Timer");
            try {
                int callTimer = spTask.getInt(CoreConstants.EVENTANALYTICSINTERVAL);
                EliteSession.eLog.d(MODULE, "Analytic Sync time interval = " + callTimer);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
                        (callTimer * (60 * 1000)), pendingIntent);

            } catch (Exception e) {
                EliteSession.eLog.e(MODULE, "Error when set the timer for the Analytic.");
            }
        } else {
            alarmManager.cancel(pendingIntent);
            EliteSession.eLog.d(MODULE, "Event Analytic Disable");
        }
    }

    public static String encodeBaseUTF(String code) {

        String encodeInvocationCode = null;
        try {
            if (code != null) {
                encodeInvocationCode = URLEncoder.encode(code, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
        }
        return encodeInvocationCode;
    }

    /**
     * Check moblie connected to wifi or not and when mobile connected to wifi than
     * we get the connected SSID
     *
     * @return
     */
    public static String getConnectedWIFI() {

        try {
            Context context = LibraryApplication.getLibraryApplication().getLibraryContext();
            final WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (wifiManager != null && wifiManager.getConnectionInfo() != null) {
                EliteSession.eLog.d(MODULE, " Connected with " + wifiManager.getConnectionInfo().getSSID());
                EliteSession.eLog.i(MODULE, " Connected with " + wifiManager.getConnectionInfo().getSSID());
                return wifiManager.getConnectionInfo().getSSID();
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());

        }
        return "";
    }

    public static String getUTF8DecodedString(String message) {
        String decodeInvocationCode = null;
        try {
            if (message != null) {
                decodeInvocationCode = java.net.URLDecoder.decode(message, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            EliteSession.eLog.e(MODULE, "UnsupportedEncodingException " + e.getMessage());
        } catch (Exception e) {
            // TODO: handle exception
            EliteSession.eLog.e(MODULE, "Exception " + e.getMessage());
        }
        return decodeInvocationCode;
    }

    public static List<String> seprateMessage(String message) {
        List<String> messageList = null;
        try {
            if (message.contains(CoreConstants.MESSAGESEPERATOR)) {
                String[] messages = message.split(CoreConstants.REGEXMESSAGESEPERATOR);
                messageList = Arrays.asList(messages);
            } else {
                messageList = new ArrayList<String>();
                messageList.add(message);
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Exception when create list using separater");

        }
        return messageList;
    }

    public static String GetCountryZipCode(String CountryID) {
        // String CountryID="";
        String CountryZipCode = "";

        // TelephonyManager manager = (TelephonyManager) ElitecoreEasyConnect.getEasyConnectApplication().getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        //getNetworkCountryIso
        // CountryID= manager.getSimCountryIso().toUpperCase();
//        String[] rl = LibraryApplication.getLibraryApplication().getLibraryContext().getResources().getStringArray(R.array.CountryCodes);
        String[] rl = CoreConstants.countryCode;
        for (int i = 0; i < rl.length; i++) {
            String[] g = rl[i].split(",");
            if (g[1].trim().equals(CountryID.trim().toUpperCase())) {
                CountryZipCode = g[0];
                break;
            }
        }
        return CountryZipCode;
    }

    public static boolean isNetworkAvaliable(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                haveConnectedWifi = true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                haveConnectedMobile = true;
            }
        }

        return haveConnectedWifi || haveConnectedMobile;
    }

    /*
    This method is added for the OPEN SSID issue to give network priority to WiFi when device latch on the SSID but
    mobile data is enabled.

     */
    public static void setNetWorkPriorityToWiFi(Context context) {

        try {
            EliteSession.eLog.d(MODULE, "Trying to Set network Request priority in WiFI");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                EliteSession.eLog.d(MODULE, "Version is greater thaN Lollipop");
                final ConnectivityManager manager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkRequest.Builder builder;
                builder = new NetworkRequest.Builder();
                //set the transport type do WIFI
                builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI);
                EliteSession.eLog.d(MODULE, "Transport type set to WIFI");
                manager.requestNetwork(builder.build(), new ConnectivityManager.NetworkCallback() {
                    @Override
                    public void onAvailable(Network network) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                            EliteSession.eLog.d(MODULE, "binding process in marshmallow and above version");
                            manager.bindProcessToNetwork(network);
                        } else {
                            //This method was deprecated in API level 23
                            EliteSession.eLog.d(MODULE, "Below marshmallow seting default network process");
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                                ConnectivityManager.setProcessDefaultNetwork(network);
                        }
                        EliteSession.eLog.d(MODULE, "un register network");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                            manager.unregisterNetworkCallback(this);
                    }
                });

            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error while setting network request priority to WIFI" + e.getMessage());

        }

    }

    public static PojoSubscriber setUserRegistrationInformation(PojoSubscriber subscriberObj) {
        EliteSession.eLog.d(MODULE, "setUserRegistrationInformation Invoked");

        subscriberObj.setSubscriberProfileType(CoreConstants.SUBSCRIBERPROFILETYPE_VALUE + "");
        try {
            DisplayMetrics metrics = LibraryApplication.getLibraryApplication().getLibraryContext().getResources().getDisplayMetrics();
            subscriberObj.setScreenWidth(metrics.widthPixels);
            subscriberObj.setScreenHeight(metrics.heightPixels);
            subscriberObj.setScreenDensity(metrics.densityDpi);
            PackageInfo pInfo = LibraryApplication.getLibraryApplication().getLibraryContext().getPackageManager().getPackageInfo(LibraryApplication.getLibraryApplication().getLibraryContext().getPackageName(), 0);
            subscriberObj.setAppVersion(pInfo.versionName);

        } catch (Exception e) {
            EliteSession.eLog.d(MODULE, "Error on device info screen and type");
        }

        TelephonyManager tm = (TelephonyManager) LibraryApplication.getLibraryApplication().getLibraryContext().getSystemService(TELEPHONY_SERVICE);
        EliteSession.eLog.d(MODULE, tm.getSubscriberId() + "");
        if (tm.getSubscriberId() != null) {
            subscriberObj.setImsi(tm.getSubscriberId());
        } else {
            EliteSession.eLog.d(MODULE, "Sim Card Not available in Device");
            subscriberObj.setImsi("NOSIM");

        }
        if (LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY) != null) {
            subscriberObj.setSecretKey(LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY));
        }

        if (tm.getSimCountryIso() != null) {
            try {
                subscriberObj.setCountryCode(Integer.parseInt(GetCountryZipCode(tm.getSimCountryIso())));
            } catch (Exception e) {
                // TODO: handle exception
                EliteSession.eLog.e(MODULE, "Error in setCountryCode" + e.getMessage());
            }
        }

        ArrayList<String> mccMNC = ElitelibUtility.getMccMncFromActiveNetworkSim();

        if (mccMNC != null && mccMNC.size() > 0) {

            EliteSession.eLog.d(MODULE, "MCC :" + mccMNC.get(0));
            EliteSession.eLog.d(MODULE, "MNC :" + mccMNC.get(1));

            subscriberObj.setMCC(Integer.parseInt(mccMNC.get(0)));
            subscriberObj.setMNC(Integer.parseInt(mccMNC.get(1)));
        } else {

            EliteSession.eLog.e(MODULE, "Sim card Not available");
        }

        if (tm.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {

            try {
                final GsmCellLocation location = (GsmCellLocation) tm.getCellLocation();
                if (location != null) {
                    subscriberObj.setLAC(location.getLac());
                    subscriberObj.setCellId(location.getCid());
                    // msg.setText("LAC: " + location.getLac() + " CID: " + location.getCid());
                }
            } catch (Exception e) {
                EliteSession.eLog.e(MODULE, "tm.getCellLocation() is null");
                EliteSession.eLog.e(MODULE, e.getMessage());
            }
        }
        if (tm.getDeviceId() != null) {
            subscriberObj.setImei(tm.getDeviceId());
            spTask.saveString(CoreConstants.imei, tm.getDeviceId());
        }
        if (Build.MANUFACTURER != null)
            subscriberObj.setManufacturer(Build.MANUFACTURER);
        if (Build.MODEL != null)
            subscriberObj.setModel(Build.MODEL);

        if (tm.getSimOperator() != null)
            subscriberObj.setSimOperator(tm.getSimOperatorName());

        if (Build.BRAND != null)
            subscriberObj.setBrand(Build.BRAND);
        if (tm.getNetworkOperator() != null)
            subscriberObj.setNetworkOperator(tm.getNetworkOperatorName());

        CoreConstants.Codenames c = CoreConstants.Codenames.getCodename();
        subscriberObj.setOperatingSystem(operatingSystemValue);
        if (c != null) {
            subscriberObj.setOSVersion(c.name() + "-" + Build.VERSION.RELEASE);
        } else {
            subscriberObj.setOSVersion(Build.VERSION.SDK_INT + "");

        }
        //Get register device id from GCM server
        try {
            final String regId = spTask.getString(CoreConstants.DEVICEID);
            subscriberObj.setRegisteredDeviceId(regId);
            subscriberObj.setIsNotificationOn(1);
        } catch (Exception e) {
            EliteSession.eLog.d(MODULE, "GCM GCMRegistrar getRegistrationId Error");
        }
        //set application locale
        try {
            String str = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.LOCALE_KEY);
            subscriberObj.setLocale(str);
        } catch (Exception e) {
            // TODO: handle exception
        }
        try {
            if (spTask.getString(CoreConstants.ADVERTISEMENT_LANGUAGE).trim().length() > 0) {
            }

        } catch (Exception e) {
            // TODO: handle exception
        }

        //Get current latitude and longitude from google, and derive address detail by reverse Geocoding
        try {
            //gps = new GPSTracker(ElitecoreEasyConnect.getEasyConnectApplication().getContext(),false);

            // check if GPS enabled

            if (currentLatLong != null) {
                Gson gson = new Gson();
                PojoGeocodeResponse resObj = gson.fromJson(locationResponse, PojoGeocodeResponse.class);
                EliteSession.eLog.d(resObj.getResults().get(0).getFormatted_address());
                if (resObj != null && resObj.getStatus().compareTo("OK") == 0) {
                    for (PojoGeocodeResponse.PojoGeocode geoCodeOjb : resObj.getResults()) {

                        Collection<PojoGeocodeResponse.PojoGeoAddressComponent> addressCollection = geoCodeOjb.getAddress_components();

                        for (PojoGeocodeResponse.PojoGeoAddressComponent pojoGeoAddressComponent : addressCollection) {

                            Collection<String> list = pojoGeoAddressComponent.getTypes();
                            if (list.contains("administrative_area_level_2")) {
                                String city = pojoGeoAddressComponent.getLong_name();
                                subscriberObj.setCity(city);
                            } else if (list.contains("administrative_area_level_1")) {
                                String state = pojoGeoAddressComponent.getLong_name();
                                subscriberObj.setState(state);
                            } else if (list.contains("country")) {
                                String country = pojoGeoAddressComponent.getLong_name();
                                subscriberObj.setCountry(country);
                            } else if (list.contains("postal_code")) {
                                String postal_code = pojoGeoAddressComponent.getLong_name();
                                try {
                                    subscriberObj.setPostalCode(Long.parseLong(postal_code));
                                } catch (NumberFormatException e) {
                                    EliteSession.eLog.d(MODULE, "setUserRegistrationInformation number format fail for postal code");
                                }

                            }
                        }
                        if (!addressFound) {
                            if (geoCodeOjb.getFormatted_address() != null) {
                                String address = geoCodeOjb.getFormatted_address();
                                subscriberObj.setGeoAddress(address);
                                addressFound = true;
                            }
                        }
                    }
                }
            } else {
                EliteSession.eLog.d(MODULE, "setUserRegistrationInformation GPS or Network is not enabled");
            }
        } catch (Exception e) {

        }

        return subscriberObj;
    }

    public static void updateServerConfigProperty(PojoConfigModel resObj) {
        if (resObj != null) {
            try {
                EliteSession.eLog.d(MODULE, "setting config property from server.");
                spTask.saveString(CoreConstants.SERVERUNREACHBLEMESSAGE, resObj.getServerUnreachbleMessage() + "");
                spTask.saveString(CoreConstants.TERMS_MODE_PREFERENCE, resObj.getTermsAndConditionMode());
                spTask.saveString(NFCALLBACKMODE, resObj.getNfCallBackMode() + "");
                spTask.saveString(CoreConstants.TERMS_AND_CONDITION_VALUE, resObj.getTermsAndCondition());
                spTask.saveString(CoreConstants.SYNCINTERVALTIME, resObj.getSyncIntervalTime() + "");
                spTask.saveInt(CoreConstants.LOCATIONBASENOTIFICATION, resObj.getLocationBaseNotification());

                spTask.saveInt(CoreConstants.EVENTANALYTICSENABLE, resObj.getEventAnalyticsEnable());
                spTask.saveInt(CoreConstants.EVENTANALYTICSINTERVAL, resObj.getEventAnalyticsInterval());
                spTask.saveString(CoreConstants.EVENTANALYTICSMODE, resObj.getEventAnalyticsMode());

                if (resObj.getUserIdentity() != null && resObj.getUserIdentity().trim().compareTo("") != 0)
                    spTask.saveString(CoreConstants.USERIDENTITY, resObj.getUserIdentity());
                try {
                    spTask.saveLong(CoreConstants.NEXT_SYNCINTERVALTIME, System.currentTimeMillis() + (resObj.getSyncIntervalTime() * 60 * 1000));
                } catch (Exception e) {
                    // TODO: handle exception
                    EliteSession.eLog.d(MODULE, "setting config property Errot to set next time interval");
                }

                spTask.saveString(NFCALLBACKINTERVAL, resObj.getNfCallBackInterval() + "");
                spTask.saveString(CoreConstants.LOCATIONSYNCRANGE, resObj.getLocationSyncRange() + "");
                spTask.saveString(CoreConstants.ADREFRESHINTERVAL, resObj.getAdRefreshInterval() + "");
                spTask.saveString(CoreConstants.COMMUNICATIONMODE, resObj.getCommunicationMode() + "");
                spTask.saveInt(CoreConstants.ADENABLE, resObj.getAdEnable());

                try {
                    spTask.saveString(CoreConstants.WIFISETTINGS_FROM, resObj.getWifiSetting());
                } catch (Exception e) {
                    // TODO: handle exception
                    EliteSession.eLog.e(MODULE, " WiFi setting Values from server is not valid , Setting no connection flag " + e.getMessage());
                    spTask.saveString(CoreConstants.WIFISETTINGS_FROM, CoreConstants.WIFISETTINGS_NOCONNECTION);
                }

                try {
                    if (resObj.getWifiConnectionTimeout() != 0)
                        spTask.saveInt(CoreConstants.WIFI_CONNECTION_TIMEOUT, resObj.getWifiConnectionTimeout());
                    else
                        spTask.saveInt(CoreConstants.WIFI_CONNECTION_TIMEOUT, CoreConstants.WIFI_CONNECTION_DEFAULT_TIMEOUT);
                } catch (Exception e) {
                    EliteSession.eLog.e(MODULE, " WiFi Connectin time out Values from server is not valid , Setting default value " + e.getMessage());
                    spTask.saveInt(CoreConstants.WIFI_CONNECTION_TIMEOUT, CoreConstants.WIFI_CONNECTION_DEFAULT_TIMEOUT);
                }
                EliteSession.eLog.d(MODULE, " WIFI Refresh timer set = " + spTask.getInt(CoreConstants.WIFI_CONNECTION_TIMEOUT));

                //first time by deafult auto wifi connection is off
//                spTask.saveBoolean(SharedPreferencesConstant.ISON, true);
                /*
                if(spTask.getString(CoreConstants.WIFISETTINGS_FROM)!=null && spTask.getString(CoreConstants.WIFISETTINGS_FROM).compareTo(CoreConstants.WIFISETTINGS_NOCONNECTION)==0)
				{
					spTask.saveBoolean(SharedPreferencesConstant.ISON, false);
				}
				else
				{
					spTask.saveBoolean(SharedPreferencesConstant.ISON, true);
				}*/

                EliteSession.eLog.d(MODULE, "setting config property from server completed");
            } catch (Exception e) {
                // TODO: handle exception
                EliteSession.eLog.d(MODULE, "setting config property from server throws exception");
            }

        }
    }

    public static void showMessageDialog(final Activity activity, final String title, final String massege) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(activity, android.R.style.Theme_DeviceDefault_Light));
                builder.setMessage(massege);
                builder.setTitle(title);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                builder.create();

                builder.show();

            }
        });
    }

    public static void showServerMessage(final Context activity, final int code, String massege) {

        if (code == 100) {
            massege = activity.getString(R.string.noInternet);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(activity, android.R.style.Theme_DeviceDefault_Light));
        builder.setMessage(massege);
        builder.setTitle(activity.getString(R.string.alert));
        builder.setPositiveButton(activity.getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create();
//        builder.show();
    }

    public static void setKeyPairValue(List<PojoServerKeyMapping.KEYPAIR> keypairList) {
        EliteSession.eLog.d(MODULE, "set key value parameter from params");
        for (PojoServerKeyMapping.KEYPAIR keypair : keypairList) {
            EliteSession.eLog.d(MODULE, "Key-" + keypair.getParamKey() + " Value-" + keypair.getParamValue());
            spTask.saveString(keypair.getParamKey(), keypair.getParamValue());
            EliteSession.eLog.d(MODULE, "Store Value get - " + spTask.getString(keypair.getParamKey()));
        }
    }

    public static String getKeyPairvalue(String serverKey, String localKey) {
        if (spTask.getString(serverKey) != null && !spTask.getString(serverKey).equals("")) {
            EliteSession.eLog.d(MODULE, "Set Server value - " + spTask.getString(serverKey));
            return spTask.getString(serverKey);
        } else {
            EliteSession.eLog.d(MODULE, "Set Local value - " + localKey);
            return localKey;
        }
    }

    public static void reportProblemUsingExternalMail(Activity activity, String To[]) {

        try {
            int stringId = LibraryApplication.getLibraryApplication().getLibraryContext().getApplicationInfo().labelRes;
            String AppName = LibraryApplication.getLibraryApplication().getLibraryContext().getString(stringId);
            File zipFolder = new File(Environment.getExternalStorageDirectory() + File.separator + AppName.replace(" ", "") + File.separator);

            String mail_subject = "", mail_body = "";

            mail_subject = AppName.replace(" ", "") + " Logs";
            TelephonyManager tm = (TelephonyManager) LibraryApplication.getLibraryApplication().getLibraryContext().getSystemService(TELEPHONY_SERVICE);
            mail_body = "This logs zip sent by end user IMEI " + tm.getDeviceId() + " for " + AppName.replace(" ", "");

            EliteSession.eLog.d(MODULE, "Folder location :" + zipFolder);
            File zipLocation = new File(Environment.getExternalStorageDirectory(), AppName.replace(" ", "") + ".zip");
            EliteSession.eLog.d(MODULE, "Zip location :" + zipLocation);

            if (zipFolder.exists() && zipFolder.isDirectory()) {
                EliteSession.eLog.d(MODULE, "Zip location folder avilable");
                zipFileAtPath(zipFolder.getPath(), zipLocation.getPath());
                Uri path = Uri.fromFile(zipLocation);
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                // set the type to 'email'
                emailIntent.setType("vnd.android.cursor.dir/email");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, To);
                // the attachment
                emailIntent.putExtra(Intent.EXTRA_STREAM, path);
                // the mail subject
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, mail_subject);
                emailIntent.putExtra(Intent.EXTRA_TEXT, mail_body);
                activity.startActivity(Intent.createChooser(emailIntent, mail_body));
            }

        } catch (Exception e) {
            e.printStackTrace();
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    // ------------------------ zip path folder for attachment in send mail
    public static boolean zipFileAtPath(String sourcePath, String toLocation) {
        // ArrayList<String> contentList = new ArrayList<String>();
        final int BUFFER = 2048;

        File sourceFile = new File(sourcePath);
        try {
            BufferedInputStream origin = null;
            FileOutputStream dest = new FileOutputStream(toLocation);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
                    dest));
            if (sourceFile.isDirectory()) {
                zipSubFolder(out, sourceFile, sourceFile.getParent().length());
            } else {
                byte data[] = new byte[BUFFER];
                FileInputStream fi = new FileInputStream(sourcePath);
                origin = new BufferedInputStream(fi, BUFFER);
                ZipEntry entry = new ZipEntry(getLastPathComponent(sourcePath));
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void zipSubFolder(ZipOutputStream out, File folder,
                                    int basePathLength) throws IOException {

        final int BUFFER = 2048;

        File[] fileList = folder.listFiles();
        BufferedInputStream origin = null;
        for (File file : fileList) {
            if (file.isDirectory()) {
                zipSubFolder(out, file, basePathLength);
            } else {
                byte data[] = new byte[BUFFER];
                String unmodifiedFilePath = file.getPath();
                String relativePath = unmodifiedFilePath
                        .substring(basePathLength);
                Log.i("ZIP SUBFOLDER", "Relative Path : " + relativePath);
                FileInputStream fi = new FileInputStream(unmodifiedFilePath);
                origin = new BufferedInputStream(fi, BUFFER);
                ZipEntry entry = new ZipEntry(relativePath);
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
            }
        }
    }

    /*
     * gets the last path component
     *
     * Example: getLastPathComponent("downloads/example/fileToZip");
     * Result: "fileToZip"
     */
    public static String getLastPathComponent(String filePath) {
        String[] segments = filePath.split("/");
        String lastPathComponent = segments[segments.length - 1];
        return lastPathComponent;
    }

    public static void viewSnackbarToast(final String label, int colorcode, final RelativeLayout mRelativeLayout) {
        if (mSnackbarView != null)
            mRelativeLayout.removeAllViews();

        try {
            mSnackbarView = new SnackbarView(LibraryApplication.getLibraryApplication().getCurrentActivity(), colorcode);
            mRelativeLayout.addView(mSnackbarView);
            mSnackbarView.settextValue(label);

            mSnackbarView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setClickable(false);
                }
            });

            SlideToAbove(mRelativeLayout);
            mRelativeLayout.setClickable(false);

            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        synchronized (this) {
                            wait(CoreConstants.intervalSnackbar);

                            LibraryApplication.getLibraryApplication().getCurrentActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mRelativeLayout.setVisibility(View.GONE);
                                    Animation bottomUp = AnimationUtils.loadAnimation(LibraryApplication.getLibraryApplication().getCurrentActivity(),
                                            R.anim.bottom_down);
                                    mRelativeLayout.startAnimation(bottomUp);
                                }
                            });
                        }
                    } catch (InterruptedException e) {
                        EliteSession.eLog.e(MODULE, "Error in snack bar " + e.getMessage());
                    }
                }

            };
            thread.start();

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error in viewSnackbarToast - " + e.getMessage());
        }
    }

    public static void SlideToAbove(final RelativeLayout mRelativeLayout) {

        try {
            Animation bottomUp = AnimationUtils.loadAnimation(LibraryApplication.getLibraryApplication().getCurrentActivity(),
                    R.anim.bottom_up);
            mRelativeLayout.startAnimation(bottomUp);
            mRelativeLayout.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error in SlideAbove - " + e.getMessage());
        }
    }

    public static boolean isAlreadyConnected(String ssidName) {
        EliteSession.eLog.d(MODULE, "isAlreadyConnected method invoked");
        Context context = LibraryApplication.getLibraryApplication().getLibraryContext();
        final WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null && wifiManager.getConnectionInfo() != null) {
            EliteSession.eLog.i(MODULE, " Connected with " + wifiManager.getConnectionInfo().getSSID());
            return wifiManager.getConnectionInfo().getSSID() != null && (wifiManager.getConnectionInfo().getSSID().compareTo(ssidName) == 0 || wifiManager.getConnectionInfo().getSSID().compareTo("\"" + ssidName + "\"") == 0) && EliteSMPUtility.getIPAddress(context) != null && EliteSMPUtility.getIPAddress(context).compareTo("0.0.0.0") != 0;
        }
        return false;
    }

    public static void hideKeyBoard(Activity activity) {
        // Check if no view has focus:
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void openKeyBoard(Activity activity, EditText editText) {

        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(
                editText.getApplicationWindowToken(),
                InputMethodManager.SHOW_FORCED, 0);
    }


    public static String getQueryURL(String Url, HashMap<String, String> queryParams) {

        String queryString = Url + "?";
        QueryParams qp = new QueryParams();


        if (queryParams != null && queryParams.size() > 0) {
            for (String paramsKey : queryParams.keySet()) {
                qp.addParam(paramsKey, queryParams.get(paramsKey));
            }
        }

        return queryString += qp.toQueryString();
    }

    public static void locationUpdateCall(Context ctx) {
        int locationCallTimer = 0;
        EliteSession.eLog.d(MODULE, "locationUpdateCall invoke");
        if (getKeyPairvalue(NFCALLBACKMODE, NFCALLBACK_TIMEMODE_VALUE).equals(NFCALLBACK_TIMEMODE_VALUE)) {
            EliteSession.eLog.d(MODULE, "Notification call back using Time base");
            if (spTask.getString(NFCALLBACKINTERVAL) != null) {
                EliteSession.eLog.d(MODULE, "interval set from server");
                locationCallTimer = Integer.parseInt(spTask.getString(NFCALLBACKINTERVAL));
            } else {
                EliteSession.eLog.d(MODULE, "interval set from local");
                locationCallTimer = 2;
            }
        } else if (spTask.getString(NFCALLBACKINTERVAL).equals(NFCALLBACK_LOCATIONMODE_VALUE)) {
            EliteSession.eLog.d(MODULE, "Notification call back using location base.");
            locationCallTimer = 10;
        }
        EliteSession.eLog.d(MODULE, "Nofication update time Interval Set - " + locationCallTimer);

        Intent intent = new Intent(ctx, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx, 234324243, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        if (Build.VERSION.SDK_INT >= 19) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()
                    , (locationCallTimer * (60 * 1000)), pendingIntent);
        } else {
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()
                    , (locationCallTimer * (60 * 1000)), pendingIntent);
        }
    }

    /**
     * Here we check Broadband user previous login or not If Broadband user previous
     * Login that time we cant call WSLogin from the SMP server. We directly connect
     * to SSID.
     *
     * @param username
     * @param password
     * @return
     */
    public static boolean checkBroadBandNewUser(String username, String password) {

        if (spTask.getString(BROADBAND_USERNAME).equals(username) &&
                spTask.getString(BROADBAND_PASSWORD).equals(password)) {
            EliteSession.eLog.d(MODULE, "User Previously Exits.");
            return false;
        } else {
            EliteSession.eLog.d(MODULE, "New User try to Login");

            return true;
        }
    }

    public static String getCurrentAppVersion(Context context) {

        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();

        String myVersionName = "not available"; // initialize String

        try {
            myVersionName = packageManager.getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return myVersionName;
    }

    public static int getCurrentAppVersionCode(Context context) {

        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();

        int myVersionName = 0; // initialize String

        try {
            myVersionName = packageManager.getPackageInfo(packageName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return myVersionName;
    }

    public static JSONObject concateJSONObjS(JSONObject... jsonObjects) throws JSONException {

        JSONObject jsonObject = new JSONObject();
        for (JSONObject temp : jsonObjects) {
            Iterator<String> keys = temp.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                jsonObject.put(key, temp.get(key));
            }
        }
        return jsonObject;
    }

    public static JSONObject concateREDIRECTPARAMS(JSONObject object) {
        boolean isConcet = true;

        EliteSession.eLog.d(MODULE, " concateREDIRECTPARAMS invoke");
        try {
            if (isConcet) {
                if (spTask.getString(KEY_HIDDENPARAMS) != null && spTask.getString(KEY_HIDDENPARAMS).length() > 0) {

                    JSONObject object1 = new JSONObject(spTask.getString(KEY_HIDDENPARAMS));

                    if (spTask.getString(APIDENTITY) != null && !spTask.getString(APIDENTITY).equalsIgnoreCase(""))
                        object1.put(APIDENTITY, spTask.getString(APIDENTITY));
                    if (spTask.getString(CALLEDSTATIONID) != null && !spTask.getString(CALLEDSTATIONID).equalsIgnoreCase(""))
                        object1.put(CALLEDSTATIONID, spTask.getString(CALLEDSTATIONID));

                    if (spTask.getString(SUBPARAM10) != null && !spTask.getString(SUBPARAM10).equalsIgnoreCase(""))
                        object1.put(SUBPARAM10, operatingSystemValue);
                    if (spTask.getString(NAS_IDENTIFIER) != null && !spTask.getString(NAS_IDENTIFIER).equalsIgnoreCase(""))
                        object1.put(NAS_IDENTIFIER, spTask.getString(NAS_IDENTIFIER));

                    if (object == null)
                        return object1;
                    else
                        return concateJSONObjS(object1, object);
                } else {

                    JSONObject object1 = new JSONObject();

                    if (spTask.getString(APIDENTITY) != null && !spTask.getString(APIDENTITY).equalsIgnoreCase(""))
                        object1.put(APIDENTITY, spTask.getString(APIDENTITY));
                    if (spTask.getString(CALLEDSTATIONID) != null && !spTask.getString(CALLEDSTATIONID).equalsIgnoreCase(""))
                        object1.put(CALLEDSTATIONID, spTask.getString(CALLEDSTATIONID));

                    if (spTask.getString(SUBPARAM10) != null && !spTask.getString(SUBPARAM10).equalsIgnoreCase(""))
                        object1.put(SUBPARAM10, operatingSystemValue);
                    if (spTask.getString(NAS_IDENTIFIER) != null && !spTask.getString(NAS_IDENTIFIER).equalsIgnoreCase(""))
                        object1.put(NAS_IDENTIFIER, spTask.getString(NAS_IDENTIFIER));

                    object1.put(USERAGENTTYPE, VALUE_USERAGENTTYPE + "_" + Build.VERSION.SDK_INT);

                    if (object == null)
                        return object1;
                    else
                        return concateJSONObjS(object1, object);
                }
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
            if (object == null)
                return new JSONObject();
            else
                return object;
        }

        if (object == null)
            return new JSONObject();
        else
            return object;
    }

    public static void showRoutDirection(Activity activity, GoogleMap googleMap, LatLng fromLocation, LatLng toLocation, String mode) {
        Map<String, LatLng> map = new HashMap<String, LatLng>();
        map.put(GetDirectionsAsyncTask.CURRENT_LATLNG, fromLocation);
        map.put(GetDirectionsAsyncTask.DESTINATION_LATLNG, toLocation);
        EliteSession.eLog.d(MODULE, "Call Show RoutDirection");
        GetDirectionsAsyncTask asyncTask = new GetDirectionsAsyncTask(googleMap, activity, mode);
        asyncTask.execute(map);
    }

    public static void showCustomProgressDialog(final Context context, final ProgressDialog pd, final String title, final String msg, final boolean isCancelable) {
        final Activity a = (Activity) context;
        a.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!a.isFinishing()) {
                    pd.setTitle(title);
                    pd.setMessage(msg);
                    pd.setCancelable(isCancelable);
                    pd.show();
                }
            }
        });
    }

    public static void dismissCustomProgressDialog(ProgressDialog pd) {
        if (pd != null)
            pd.dismiss();
    }

    public static String getDeviceResolution() {
        int density = LibraryApplication.getLibraryApplication().getLibraryContext().getResources().getDisplayMetrics().densityDpi;
        switch (density) {
            case DisplayMetrics.DENSITY_MEDIUM:
                return "MDPI";
            case DisplayMetrics.DENSITY_HIGH:
            case DisplayMetrics.DENSITY_280:
            case DisplayMetrics.DENSITY_360:
                return "HDPI";
            case DisplayMetrics.DENSITY_LOW:
                return "LDPI";
            case DisplayMetrics.DENSITY_XHIGH:
            case DisplayMetrics.DENSITY_400:
                return "XHDPI";
            case DisplayMetrics.DENSITY_TV:
                return "TV";
            case DisplayMetrics.DENSITY_XXHIGH:
            case DisplayMetrics.DENSITY_420:
                return "XXHDPI";
            case DisplayMetrics.DENSITY_560:
            case DisplayMetrics.DENSITY_XXXHIGH:
                return "XXXHDPI";
            default:
                return "OTHER";
        }


    }

    public static void setProfile(PojoWiFiProfiles pojoWiFiProfiles) {
        try {
            PojoProfile newProfile = new PojoProfile();
            newProfile.setName(pojoWiFiProfiles.getAndroidSettingName());
            newProfile.setLocal(false);
            if (pojoWiFiProfiles.getIsPreferable().compareToIgnoreCase("true") == 0) {
                newProfile.setDefault(true);
                newProfile.setActive(true);
                spTask.saveString(SharedPreferencesConstant.ACTIVEPROFILE, pojoWiFiProfiles.getAndroidSettingName());
            }
            DBOperation dbHelperProfile = null;
            try {
                dbHelperProfile = DBOperation.getDBHelperOperation();
                dbHelperProfile.open();
                dbHelperProfile.insertProfile(newProfile);
            } catch (Exception e) {
                EliteSession.eLog.e(MODULE, e.getMessage());
            } finally {
                if (dbHelperProfile != null) {
                    dbHelperProfile.close();
                }
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    public static void setConnections(String profileName, Set<PojoWiFiConnections> wificonnection) {

        DBOperation dbHelperOperationObj = DBOperation.getDBHelperOperation();
        try {
            if (wificonnection != null) {
                for (PojoWiFiConnections pojoWiFiConnections : wificonnection) {
                    dbHelperOperationObj.open();
                    PojoConnection pojoConnection = new PojoConnection();
                    pojoConnection.setSsid(pojoWiFiConnections.getSsidName());
                    // pojoConnection.setEncryptionMethod(WifiUtility.getEncryptionType(pojoWiFiConnections.getSsidName()));
                    if (pojoWiFiConnections.getSecurityType() != null) {
                        if (pojoWiFiConnections.getSecurityType().compareTo(CoreConstants.WIFI_SECURITY_EAP) == 0)
                            pojoConnection.setSecurity(WifiUtility.EAP_802_1X);
                        else if (pojoWiFiConnections.getSecurityType().compareTo(CoreConstants.WIFI_SECURITY_WPAWPA2) == 0)
                            pojoConnection.setSecurity(WifiUtility.WPAWPA2PSK);
                        else if (pojoWiFiConnections.getSecurityType().compareTo(CoreConstants.WIFI_SECURITY_OPEN) == 0)
                            pojoConnection.setSecurity(WifiUtility.OPEN);
                        else
                            pojoConnection.setSecurity(pojoWiFiConnections.getSecurityType());
                    }
                    if (pojoWiFiConnections.getSecurityType() != null && pojoWiFiConnections.getSecurityType().compareTo(CoreConstants.WIFI_SECURITY_EAP) == 0 && pojoWiFiConnections.getEapType() != null) {
                        if (pojoWiFiConnections.getEapType().compareTo(CoreConstants.WIFI_EAP_ELITESIM) == 0)
                            pojoConnection.setAuthenticationMethod(WifiUtility.TTLS);
                        else if (pojoWiFiConnections.getEapType().compareTo(CoreConstants.WIFI_EAP_SIM) == 0)
                            pojoConnection.setAuthenticationMethod(WifiUtility.SIM);
                        else
                            pojoConnection.setAuthenticationMethod(pojoWiFiConnections.getEapType());
                    }
                    if (pojoWiFiConnections.getProtocolType() != null) {
                        pojoConnection.setPhase2Authentication(pojoWiFiConnections.getProtocolType());
                    }
                    if (pojoWiFiConnections.getUserIdentity() != null) {
                        if (pojoWiFiConnections.getUserIdentity().contains(CoreConstants.WIFI_USERNAME_VARIABLE)) {
                            String userid = pojoWiFiConnections.getUserIdentity().replace(CoreConstants.WIFI_USERNAME_VARIABLE, ElitelibUtility.getIMSI(true, false, ""));
                            pojoConnection.setIdentity(userid);
                        } else {
                            pojoConnection.setIdentity(pojoWiFiConnections.getUserIdentity());
                        }
                    }
                    if (pojoWiFiConnections.getPassword() != null) {
                        pojoConnection.setPassword(pojoWiFiConnections.getPassword());
                    }
                    if (pojoWiFiConnections.getIsPreferable() != null && pojoWiFiConnections.getIsPreferable().compareToIgnoreCase("true") == 0) {
                        pojoConnection.setDefault(true);
                    } else {
                        pojoConnection.setDefault(false);
                    }
                    pojoConnection.setShowPassword(false);
                    pojoConnection.setActive(false);
                    pojoConnection.setLocal(false);
                    try {
                        // dbHelper_Connection.deleteAllConnections();
                        dbHelperOperationObj.insertConnection(pojoConnection);
                        addConnectionToProfile(profileName, pojoConnection);
                    } catch (Exception e) {
                        EliteSession.eLog.e(MODULE, e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        } finally {
            if (dbHelperOperationObj != null)
                dbHelperOperationObj.close();
        }
    }

    public static void addConnectionToProfile(String profileName, PojoConnection connection) {
        DBOperation dbHelperOperationObj = DBOperation.getDBHelperOperation();
        try {
            // Add default profile-connection relation into relation DB.

            dbHelperOperationObj.open();
            dbHelperOperationObj.insertRelation(profileName, connection.getSsid());

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        } finally {
            if (dbHelperOperationObj != null) {
                dbHelperOperationObj.close();
            }
        }
    }

    public static void loadAllLocation() {
        EliteSession.eLog.d(MODULE, "Loading all location from database.");

        ArrayList<PojoLocation> locationList = DBOperation.getDBHelperOperation().getAllLocations();
        if (locationList != null && locationList.size() > 0) {
            int count = 0;
            spTask.saveInt(CoreConstants.POJOLOCATION_COUNT, locationList.size());
            for (PojoLocation pojoLocation : locationList) {
                Gson gObj = new Gson();
                spTask.saveString(CoreConstants.POJOLOCATION + count, gObj.toJson(pojoLocation));
                count++;
            }

        }
        EliteSession.eLog.d(MODULE, "Loading all location from database completed.");
    }

    public static String getConfigProperty(String key) {
        try {
            return config_properties.getProperty(key);
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());

        }
        return null;
    }

    public static String decrypt(String property) throws GeneralSecurityException, IOException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(CoreConstants.ENCRYPTION_ALGORITHM);
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
        Cipher pbeCipher = Cipher.getInstance(CoreConstants.ENCRYPTION_ALGORITHM);
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
        return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
    }

    private static byte[] base64Decode(String property) throws IOException {
        return new BASE64Decoder().decodeBuffer(property);
    }

    public static void appVersionUpgradeDialog(final Activity activity, String title, String message, boolean newTheme) {
        EliteSession.eLog.i(MODULE, "Check Application Version Update");
        EliteSession.eLog.i(MODULE, "Current Version = " + ElitelibUtility.getCurrentAppVersion(activity));
        EliteSession.eLog.i(MODULE, "VERSION_NEW_FROM_SERVER - " + spTask.getString(SharedPreferencesConstant.VERSION_NEW_FROM_SERVER));
        AlertDialog.Builder builder;
        if (!spTask.getString(SharedPreferencesConstant.VERSION_NEW_FROM_SERVER).equals("") && !spTask.getString(SharedPreferencesConstant.VERSION_NEW_FROM_SERVER).equals(ElitelibUtility.getCurrentAppVersion(activity))) {
            EliteSession.eLog.i(MODULE, "Version Update Found");
            if (newTheme) {
                builder = new AlertDialog.Builder(new ContextThemeWrapper(activity, R.style.AlertDialog));
            } else {
                builder = new AlertDialog.Builder(activity);
            }

//                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage(message);
            builder.setTitle(title);
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.upgrade, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    EliteSession.eLog.i(MODULE, "Upgrad Button Click");
                    Uri uri = Uri.parse(spTask.getString(SharedPreferencesConstant.VERSION_UPDATE_URL));
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    activity.finish();
                }
            });

            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    EliteSession.eLog.i(MODULE, "Cancel BUtton Click");
                    dialogInterface.cancel();
                }
            });

            AlertDialog dialog = builder.create();
            if (!dialog.isShowing())
                dialog.show();

            if (!spTask.getString(SharedPreferencesConstant.VERSION_FORCE_UPGRADE).equals("")
                    && spTask.getString(SharedPreferencesConstant.VERSION_FORCE_UPGRADE).equals(CoreConstants.VERSION_FORCEUPDATE_TRUE)) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setVisibility(View.GONE);
                EliteSession.eLog.d(MODULE, "Version to be upgraded forcefully.");
            }
        } else {
            EliteSession.eLog.i(MODULE, "Allready update Version");

            if (spTask.getBoolean(MONETIZATIONREG)) {
                EliteSession.eLog.i(MODULE, "Monetization Call");

                new InterNetAvailabilityCheckTask(101, new OnInternetCheckCompleteListner() {

                    @Override
                    public void isInterNetAvailable(int requestId, String status, String json) {

                        EliteSession.eLog.i(MODULE, "Internet Status - " + status);

                        if (status.equalsIgnoreCase(SUCCESS)) {
                            PojoSubscriber subscriberData;

                            Gson gson = new Gson();
                            subscriberData = gson.fromJson(spTask.getString(REGISTRATIONJSON), PojoSubscriber.class);

                            try {
                                PackageInfo pInfo = LibraryApplication.getLibraryApplication().getLibraryContext().getPackageManager().getPackageInfo(LibraryApplication.getLibraryApplication().getLibraryContext().getPackageName(), 0);
                                subscriberData.setAppVersion(pInfo.versionName);
                            } catch (Exception e) {
                                EliteSession.eLog.e(MODULE, e.getMessage());
                            }
                            try {

                                ConnectionManagerTaskNew task = new ConnectionManagerTaskNew(new ConnectionManagerCompleteListner() {
                                    @Override
                                    public void onConnnectionManagerTaskComplete(String result, int requestId) {
                                        EliteSession.eLog.d(MODULE, " Registration Success");

                                        if (!result.trim().equals("")) {
                                            try {
                                                PojoConfigModelResponse resObj = new Gson().fromJson(result, PojoConfigModelResponse.class);
                                                if (resObj.getResponseCode() == 1) {
                                                    EliteSession.eLog.d(MODULE, " registration response code  is " + resObj.getResponseCode());
                                                    spTask.saveBoolean(MONETIZATIONREG, false);
                                                }
                                            } catch (Exception e) {
                                                EliteSession.eLog.e(MODULE, e.getMessage());
                                            }
                                        } else {
                                            EliteSession.eLog.e(MODULE, "result null");
                                        }
                                    }
                                }, CoreConstants.MONETIZATION_REGISTERUSERPROFILE_REQUESTID);
                                task.execute(gson.toJson(subscriberData), LibraryApplication.getGetterSetterObj().getSERVERHOSTMONETIZATION() +
                                        CoreConstants.MONETIZATION_REGISTERUSERPROFILE);
                            } catch (Exception e) {
                                EliteSession.eLog.e(MODULE, " Registration request error " + e.getMessage());
                            }
                        }
                    }
                }, CoreConstants.INTERNET_CHECK_URL).execute();
            }
        }
    }

    /**
     * Using this method we pass the arabic numeric argument and its convert to
     * English
     *
     * @param number
     * @return
     */
    public static String arabicToenglish(String number) {
//        EliteSession.eLog.d(MODULE, "Arabic Language - " + number);
        char[] chars = new char[number.length()];
        for (int i = 0; i < number.length(); i++) {
            char ch = number.charAt(i);
            if (ch >= 0x0660 && ch <= 0x0669)
                ch -= 0x0660 - '0';
            else if (ch >= 0x06f0 && ch <= 0x06F9)
                ch -= 0x06f0 - '0';
            chars[i] = ch;
        }
//        EliteSession.eLog.d(MODULE, "English Language Convert - " + new String(chars));
        return new String(chars);
    }

    /**
     * Check if String arabic or not
     *
     * @param s
     * @return
     */
    public static boolean isProbablyArabic(String s) {
        for (int i = 0; i < s.length(); ) {
            int c = s.codePointAt(i);
            if (c >= 0x0600 && c <= 0x06E0)
                return true;
            i += Character.charCount(c);
        }
        return false;
    }

    /*
     * Function to display advertisement and reload it with specific time interval
     *
     */
    public static void displayAdvertisement(final WebView wv_advertiseMent, final String screenName, final String htmlText, final Activity ctx,
                                            final View.OnClickListener onclick) {
        EliteSession.eLog.d(MODULE, " in displayAdvertisement ");
        ctx.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                wv_advertiseMent.setWebViewClient(new AdvWebviewClient(ctx, htmlText));
                wv_advertiseMent.setWebChromeClient(new AdvWebviewCrome(ctx));
                wv_advertiseMent.getSettings().setJavaScriptEnabled(true);
                wv_advertiseMent.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);



                EliteSession.eLog.d(MODULE, "htmlText===" + htmlText);
                wv_advertiseMent.loadDataWithBaseURL(null, htmlText, "text/html", "utf-8", null);
                wv_advertiseMent.setVerticalScrollBarEnabled(false);
                wv_advertiseMent.setHorizontalScrollBarEnabled(false);
                wv_advertiseMent.getSettings().setUseWideViewPort(true);
                wv_advertiseMent.setInitialScale(100);

                wv_advertiseMent.setOnClickListener(onclick);
                wv_advertiseMent.setBackgroundColor(Color.TRANSPARENT);
                int adRefreshTime = 20;
                try {
                    if (spTask.getString(CoreConstants.ADREFRESHINTERVAL).equals("")) {
                        adRefreshTime = Integer.parseInt(ElitelibUtility.getConfigProperty(CoreConstants.ADREFRESHINTERVAL));
                    } else {
                        adRefreshTime = Integer.parseInt(spTask.getString(CoreConstants.ADREFRESHINTERVAL));
                    }

                    EliteSession.eLog.d(MODULE, "advertisement refresh time interval - " + adRefreshTime);
                } catch (Exception e) {
                    EliteSession.eLog.e(MODULE, "Error in advertisement refresh time interval" + e.getMessage());
                }

                AdUtility.getAdUtilityInstance().setContext(ctx);
                AdUtility.getAdUtilityInstance().setScreenName(screenName);
                AdUtility.getAdUtilityInstance().setSeconds(adRefreshTime);
                AdUtility.getAdUtilityInstance().setWv(wv_advertiseMent);
                AdUtility.getAdUtilityInstance().setHtmlText(htmlText);
                AdUtility.getAdUtilityInstance().reloadAdvertisement();

            }
        });
    }

    public static ArrayList<HashMap<String, String>> getAdvertisementUrl() {
        return CoreConstants.advertisementURLList;
    }

    public static void setAdvertisementUrl(String urlKey, String urlValue) {
        CoreConstants.androidUrlSaveHashMap.put(urlKey, urlValue);
        CoreConstants.advertisementURLList.add(CoreConstants.androidUrlSaveHashMap);
        EliteSession.eLog.d(MODULE, "Add Advertisement URL");
    }

    public static String getDecodedInvocationCode(String screenName) {
        String invocationCode = "";
        try {

            EliteSession.eLog.d(MODULE, "screenName  = " + screenName);

            invocationCode = getUTF8DecodedString(LibraryApplication.getLibraryApplication().getAdMappingHashMap().get(screenName).getInvocationCode());
//            invocationCode="<html><head><style> img {width : 100%;}  body {margin:0px; padding:0px;}</style></head><body> <script type='text/javascript' src='http://192.168.6.133:1000/ads/www/delivery/ads.js?zoneid=7f7cef77-1d91-11e6-a3da-525400dd8119'></script> </body></html>";
//            invocationCode="<html><head><style> img {width : 100%;}  body {margin:0px; padding:0px;}</style></head><body> <script type='text/javascript' src='http://192.168.6.133:1000/ads/www/delivery/ads.js?zoneid=7f7cef77-1d91-11e6-a3da-525400dd8119'></script> </body></html>";
//			language={}&agerange={}&location={}&geographic={}&gender={}&livein={}
//			language                              english
//			agerange                             18 to 25
//			location                                du shop
//			geographic                          india
//			livein                                     yes
//			gender                                 male

            if (invocationCode.contains(CoreConstants.AD_LANGUAGE)) {
                EliteSession.eLog.d(MODULE, "AD_LANGUAGE " + spTask.getString(CoreConstants.ADVERTISEMENT_LANGUAGE));
                invocationCode = invocationCode.replace(CoreConstants.AD_LANGUAGE, spTask.getString(CoreConstants.ADVERTISEMENT_LANGUAGE).toLowerCase());
            }
            if (invocationCode.contains(CoreConstants.AD_AGERANGE)) {
                EliteSession.eLog.d(MODULE, "AD_AGERANGE " + spTask.getString(CoreConstants.AD_AGERANGE));
                String ageGroup = spTask.getString(CoreConstants.ADVERTISEMENT_AGERANGE);

                if (ageGroup.equals("Under 18")) {
                    ageGroup = "17";
                } else if (ageGroup.equals("18 to 25")) {
                    ageGroup = "18";
                } else if (ageGroup.equals("26 to 35")) {
                    ageGroup = "26";
                } else if (ageGroup.equals("36 to 45")) {
                    ageGroup = "36";
                } else if (ageGroup.equals("46 to 55")) {
                    ageGroup = "46";
                } else if (ageGroup.equals("56 and Above")) {
                    ageGroup = "56";
                }
                EliteSession.eLog.d(MODULE, "AD_AGERANGE formatted " + ageGroup);
                invocationCode = invocationCode.replace(CoreConstants.AD_AGERANGE, ageGroup);
            }

            if (invocationCode.contains(CoreConstants.AD_LOCATION)) {
                EliteSession.eLog.d(MODULE, "AD_LOCATION -" + spTask.getString(SharedPreferencesConstant.ADVLOCATION_PARAM));
                invocationCode = invocationCode.replace(CoreConstants.AD_LOCATION, URLEncoder.encode(spTask.getString(SharedPreferencesConstant.ADVLOCATION_PARAM).trim(), "UTF-8"));
            }
            if (invocationCode.contains(CoreConstants.AD_GEOGRAPHICS)) {
                EliteSession.eLog.d(MODULE, "AD_GEOGRAPHICS " + spTask.getString(CoreConstants.ADVERTISEMENT_COUNTRY));
                invocationCode = invocationCode.replace(CoreConstants.AD_GEOGRAPHICS, spTask.getString(CoreConstants.ADVERTISEMENT_COUNTRY).toLowerCase().trim());
            }
            if (invocationCode.contains(CoreConstants.AD_LIVEIN)) {
                EliteSession.eLog.d(MODULE, "AD_LIVEIN " + spTask.getString(CoreConstants.ADVERTISEMENT_LIVEIN));
                invocationCode = invocationCode.replace(CoreConstants.AD_LIVEIN, spTask.getString(CoreConstants.ADVERTISEMENT_LIVEIN).toLowerCase());
            }
            if (invocationCode.contains(CoreConstants.AD_GENDER)) {
                EliteSession.eLog.d(MODULE, "AD_GENDER " + spTask.getString(CoreConstants.ADVERTISEMENT_GENDER));
                invocationCode = invocationCode.replace(CoreConstants.AD_GENDER, spTask.getString(CoreConstants.ADVERTISEMENT_GENDER).toLowerCase());
            }
            if (invocationCode.contains(CoreConstants.AD_RANDOMKEY)) {
                int min = 1;
                int max = 10000;

                Random r = new Random();
                int i1 = r.nextInt(max - min + 1) + min;
                invocationCode = invocationCode.replace(CoreConstants.AD_RANDOMKEY, i1 + "");
            }


        } catch (Exception e) {
            // TODO: handle exception
            EliteSession.eLog.e(MODULE, "Error while getting advertisment invocaiton code for " + e.getMessage());
        }

        return invocationCode;
    }

    // local setup for wifi
    public static PojoConnection setUP_BSNLWIFI(int type) {

        EliteSession.eLog.d(MODULE, "Create local setup wifi connection profile");
        PojoConnection activateConnection = new PojoConnection();
        switch (type) {
            case TYPE_OFFLOAD_WIFI:
                EliteSession.eLog.d(MODULE, "Setting offload SSID");
                activateConnection.setSsid(getKeyPairvalue(KEY_OFFLOADSSID, spTask.getString(KEY_OFFLOADSSID_LOCAL)));
                activateConnection.setSecurity(EliteWiFIConstants.WIFI_EAPSIM);
                return activateConnection;
        }
        return null;
    }

    public static void callLocationUpdateAlarm(Context ctx) {
        int locationCallTimer = 0;
        if (getConfigProperty(CoreConstants.NFCALLBACKMODE).equals(CoreConstants.NFCALLBACKMODETIME_VALUE)) {
            try {
//                locationCallTimer = Integer.parseInt(Utility.getConfigProperty(CoreConstants.NFCALLBACKINTERVAL));
                locationCallTimer = Integer.parseInt(spTask.getString(CoreConstants.NFCALLBACKINTERVAL));
            } catch (Exception e) {
                EliteSession.eLog.d(MODULE, "locationCallTimer not set properly in config file or from server");
                //setting default time
                locationCallTimer = 2;
            }
        } else if (getConfigProperty(CoreConstants.NFCALLBACKMODE).equals(CoreConstants.NFCALLBACKMODEDISTANCE_VALUE)) {
            locationCallTimer = 10;
        }
        Intent intent = new Intent(ctx, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                ctx, 234324243, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        if (Build.VERSION.SDK_INT >= 19) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                    , (locationCallTimer * (60 * 1000)), pendingIntent);
        } else {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                    , (locationCallTimer * (60 * 1000)), pendingIntent);
        }
    }

    public static ArrayList<String> getMccMncFromActiveNetworkSim() {
        ArrayList<String> mccmnc = new ArrayList<>();

        EliteSession.eLog.d(MODULE, "Get Value From Netowork oprater");
        TelephonyManager tm = (TelephonyManager) LibraryApplication.getLibraryApplication().getLibraryContext().getSystemService(Context.TELEPHONY_SERVICE);
//        String networkOperator = tm.getSimOperator();
        String networkOperator = tm.getNetworkOperator();
        try {
            if (networkOperator != null && networkOperator.trim().compareTo("") != 0) {
                mccmnc.add(networkOperator.substring(0, 3).trim());
                mccmnc.add(networkOperator.substring(3).trim());

            } else {
                mccmnc.add("0");
                mccmnc.add("0");
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Get MCC MNC Error from Network operater");
            mccmnc.add("0");
            mccmnc.add("0");
        }
        return mccmnc;
    }

    public static ArrayList<String> getMccMnc(TelephonyManager tm) {
        ArrayList<String> integerArrayList = new ArrayList<>();
        EliteSession.eLog.d(MODULE, "Get MCC MNC");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            SubscriptionManager manager = (SubscriptionManager) LibraryApplication.getLibraryApplication().getLibraryContext().getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);

            List<SubscriptionInfo> infoList = manager.getActiveSubscriptionInfoList();

            if (infoList != null && infoList.size() > 0) {

                EliteSession.eLog.d(MODULE, "Avilable SIM Count - " + infoList.size());

                for (SubscriptionInfo info : infoList) {

                    EliteSession.eLog.d(MODULE, " DisplayName - " + info.getDisplayName());
                    EliteSession.eLog.d(MODULE, " CarrierName - " + info.getCarrierName());
                    EliteSession.eLog.d(MODULE, " MCC - " + info.getMcc());
                    EliteSession.eLog.d(MODULE, " MNC - " + info.getMnc());

                    integerArrayList.add(String.valueOf(info.getMcc()));
                    integerArrayList.add(String.valueOf(info.getMnc()));

                    break;
                }
            } else {

                integerArrayList.add("0");
                integerArrayList.add("0");

                EliteSession.eLog.d(MODULE, "Get MCC MNC not get from Sim");
            }
        } else {
            if (tm.getSimOperator() != null && !tm.getSimOperator().equals("")) {
                EliteSession.eLog.d(MODULE, "Get Value From SIMOperater");
                String networkOperator = tm.getSimOperator();
                try {
                    if (networkOperator != null) {
                        integerArrayList.add(networkOperator.substring(0, 3).trim());
                        integerArrayList.add(networkOperator.substring(3).trim());
                    }
                } catch (Exception e) {
                    EliteSession.eLog.e(MODULE, "Get MCC MNC Error from Sim Oprater");
                }
            } else if (tm.getNetworkOperator() != null) {
                EliteSession.eLog.d(MODULE, "Get Value From Netowork oprater");
                String networkOperator = tm.getNetworkOperator();
                try {
                    if (networkOperator != null && networkOperator.trim().compareTo("") != 0) {
                        integerArrayList.add(networkOperator.substring(0, 3).trim());
                        integerArrayList.add(networkOperator.substring(3).trim());
                    } else {
                        integerArrayList.add("0");
                        integerArrayList.add("0");
                    }
                } catch (Exception e) {
                    EliteSession.eLog.e(MODULE, "Get MCC MNC Error from Network operater");
                    integerArrayList.add("0");
                    integerArrayList.add("0");
                }
            } else {
                integerArrayList.add("0");
                integerArrayList.add("0");

                EliteSession.eLog.d(MODULE, "Get MCC MNC not get from Sim Oprater or Network oprater");
            }
        }
        EliteSession.eLog.d(MODULE, "MCC MNC Array - " + integerArrayList);
        return integerArrayList;
    }

    /**
     * Find the poin
     *
     * @param point
     * @param range
     * @param bearing
     * @return
     */
    public static PointF calculateDerivedPosition(PointF point, double range, double bearing) {
        double EarthRadius = 6371000; // m

        double latA = Math.toRadians(point.x);
        double lonA = Math.toRadians(point.y);
        double angularDistance = range / EarthRadius;
        double trueCourse = Math.toRadians(bearing);

        double lat = Math.asin(
                Math.sin(latA) * Math.cos(angularDistance) +
                        Math.cos(latA) * Math.sin(angularDistance)
                                * Math.cos(trueCourse));

        double dlon = Math.atan2(
                Math.sin(trueCourse) * Math.sin(angularDistance)
                        * Math.cos(latA),
                Math.cos(angularDistance) - Math.sin(latA) * Math.sin(lat));

        double lon = ((lonA + dlon + Math.PI) % (Math.PI * 2)) - Math.PI;

        lat = Math.toDegrees(lat);
        lon = Math.toDegrees(lon);

        PointF newPoint = new PointF((float) lat, (float) lon);

        return newPoint;

    }

    // QTL library secreate key generation
    public static void setSecretKey(String sharedKey) {
        EliteSession.eLog.d(MODULE + " validating security information");
        StringBuffer hexString = new StringBuffer();
        try {
            StringBuilder encrypt = new StringBuilder();
            if (spTask.getString(LIBRARY_PACKAGE_NAME) == null && spTask.getString(LIBRARY_PACKAGE_NAME).equals("")) {
                encrypt.append(LibraryApplication.getLibraryApplication().getLibraryContext().getPackageName());
                EliteSession.eLog.d(MODULE + " package name :: " + LibraryApplication.getLibraryApplication().getLibraryContext().getPackageName());
            } else {
                encrypt.append(spTask.getString(LIBRARY_PACKAGE_NAME));
                EliteSession.eLog.d(MODULE + " package name :: " + spTask.getString(LIBRARY_PACKAGE_NAME));
            }
            encrypt.append(sharedKey);

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(encrypt.toString().getBytes("UTF-8"));

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            EliteSession.eLog.d(MODULE + " Generate Secret key successfully");
            LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().saveString(CoreConstants.SECRETKEY, hexString.toString());
//			LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().saveString(CoreConstants.SECRETKEY, "39943779bf9ad09ebc11567b56048cb81e927028169507946a5a6d462a2162b7");
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE + " Security setting error " + e.getMessage());
        }
    }


    public static String getGradleValue(String gradleparam, String rawKey) {
        String value = "";
        try {
            if (gradleparam != null && !gradleparam.equals("")) {
                value = gradleparam;
                EliteSession.eLog.d(MODULE, "value from gradle");
            } else if (ElitePropertiesUtil.getInstance().getConfigProperty(rawKey) != null
                    && !ElitePropertiesUtil.getInstance().getConfigProperty(rawKey).equals("")) {
                EliteSession.eLog.d(MODULE, "value from raw file");
                value = ElitePropertiesUtil.getInstance().getConfigProperty(rawKey);
            } else {
                EliteSession.eLog.d(MODULE, "value not available gradle or raw file");
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "error in get gradle value - " + e.getMessage());
        }
        return value;
    }

    public static String getMetaDataValue(String Key) {

        String metaValue = "";

        try {
            ApplicationInfo ai = LibraryApplication.getLibraryApplication().getLibraryContext().getPackageManager().getApplicationInfo(LibraryApplication.getLibraryApplication().getLibraryContext().getPackageName(), PackageManager.GET_META_DATA);
            metaValue = ai.metaData.get(Key).toString();

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "error in get meta data value - " + e.getMessage());
        }

        return metaValue;
    }

    public static boolean isInternetConnction() {

        try {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {

                        EliteSession.eLog.i(MODULE, "Before Waiting for connection");
                        HttpURLConnection urlConnection = null;
                        try {
                            URL url = new URL(CoreConstants.INTERNET_CHECK_URL);
                            urlConnection = (HttpURLConnection) url.openConnection();
                            urlConnection.setRequestMethod("POST");
                            urlConnection.setInstanceFollowRedirects(true);
                            urlConnection.setConnectTimeout(CoreConstants.INTERNET_CHECK_TIMEOUT);
                            urlConnection.setReadTimeout(CoreConstants.INTERNET_CHECK_TIMEOUT);
                            urlConnection.setUseCaches(false);
                            urlConnection.getInputStream();
                            // we got a valid response, but not from the real google
                            // urlConnection.gethe
                            if (urlConnection != null)
                                EliteSession.eLog.d(MODULE, "Response Internet connectivity code is :: " + urlConnection.getResponseCode());

                            if (urlConnection.getResponseCode() == 204) {    //check connection

                                EliteSession.eLog.d(MODULE, "Response code 204");
                                result = true;
                            } else {
                                result = false;
                            }
                        } catch (IOException e) {
                            result = false;
                            EliteSession.eLog.e(MODULE, e.getMessage());

                        } finally {
                            if (urlConnection != null) {
                                urlConnection.disconnect();
                            }
                        }
                        EliteSession.eLog.i(MODULE, "After Waiting for connection");

                        status = -1;
                    } catch (Exception e) {
                        EliteSession.eLog.e(MODULE, e.getMessage());
                        status = -1;
                    }
                }
            }).start();

            while (status > 0) {
                Thread.sleep(100);
            }
            status = 1;
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "internet Check - " + e.getMessage());
        }

        return result;
    }

    public static int calculateAge(Date birthDate) {
        long ageMillis = System.currentTimeMillis() - birthDate.getTime();
        EliteSession.eLog.d("age mills " + ageMillis);
        return (int) ((ageMillis / CoreConstants.MILLIS_PER_YEAR));
    }

    public static Date stringToDate(String date, String format) {
        Date mDate = null;
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
        try {
            mDate = mSimpleDateFormat.parse(date);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mDate;
    }

    private static void loadGoogleRelationShip() {
        CoreConstants.googleRelationShipHashMap.put(0, "SINGLE");
        CoreConstants.googleRelationShipHashMap.put(1, "IN_A_RELATIONSHIP");
        CoreConstants.googleRelationShipHashMap.put(2, "ENGAGED");
        CoreConstants.googleRelationShipHashMap.put(3, "MARRIED");
        CoreConstants.googleRelationShipHashMap.put(4, "ITS_COMPLICATED");
        CoreConstants.googleRelationShipHashMap.put(5, "OPEN_RELATIONSHIP");
        CoreConstants.googleRelationShipHashMap.put(6, "WIDOWED");
        CoreConstants.googleRelationShipHashMap.put(7, "IN_DOMESTIC_PARTNERSHIP");
        CoreConstants.googleRelationShipHashMap.put(8, "IN_CIVIL_UNION");
    }

    public static class AdvWebviewCrome extends WebChromeClient {
        private Activity ctx;

        public AdvWebviewCrome(Activity activity) {
            this.ctx = activity;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
    }

    public static class AdvWebviewClient extends WebViewClient {

        private ProgressDialog progressDialog;
        private Activity ctx;
        private String htmlText;
        private String url;


        public AdvWebviewClient(Activity activity, String htmlText) {
            this.ctx = activity;
            this.htmlText = htmlText;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            EliteSession.eLog.d(MODULE, "shouldOverrideUrlLoading ,  URL is:" + url);

            clickToAdv(view, url);

            return true;
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);

            EliteSession.eLog.d(MODULE, "onLoadResource URL - " + url);

            clickToAdv(view, url);

            if (progressDialog == null && !isAddLoaded) {
                // in standard case YourActivity.this
                progressDialog = new ProgressDialog(ctx);
                progressDialog.setMessage(ctx.getResources().getString(R.string.processing));
                //progressDialog.show();
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            try {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    progressDialog = null;
                    isAddLoaded = true;
                }
//                else {
//                    view.setVisibility(View.GONE);
//                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
//            view.setVisibility(View.GONE);
        }


        private void clickToAdv(WebView view, String url) {

            if (url != null && url.contains("oadest=")) {
                String redirect = url.substring(url.indexOf("oadest="));
                EliteSession.eLog.d(MODULE, "onLoadResource" + redirect);

                this.url = url;
                try {
                    EliteSession.eLog.d(MODULE, "Check Internet Connection.");

//                    boolean status = CommonWiFiUtility.isConnectionAvailable();
                    boolean status = isInternetConnction();

                    if (status) {
                        EliteSession.eLog.d(MODULE, "Internet available");
                        ctx.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    } else {
                        EliteSession.eLog.d(MODULE, "Internet Not available");
                        try {
                            String urlKey = "";
                            Toast.makeText(LibraryApplication.getLibraryApplication().getLibraryContext(),
                                    LibraryApplication.getLibraryApplication().getLibraryContext().getString
                                            (R.string.networkerror_advertise), Toast.LENGTH_SHORT).show();
                            Uri uri = Uri.parse(url);
                            urlKey = uri.getQueryParameter(CoreConstants.AD_URLPARAMETER);

                            if (urlKey != null && !urlKey.equalsIgnoreCase("")) {
                                setAdvertisementUrl(urlKey, url);
                            } else {
                                setAdvertisementUrl(AD_CLICK_URL, url);
                            }
                        } catch (Exception e) {
                            EliteSession.eLog.e(MODULE, "Error to displaye alert");
                        }
                    }

                } catch (Exception e) {
                    EliteSession.eLog.d(MODULE, "INternet check Call exception - " + e.getMessage());
                }

                view.loadDataWithBaseURL(null, htmlText, "text/html", "utf-8", null);
                return;
            } else {
                EliteSession.eLog.d(MODULE, "'oadest=' Check this key, but not available in URL");
            }
        }

    }


    public static class DownloadSpeedTest extends AsyncTask<String, Void, String> {
        public OnWiFiTaskCompleteListner wifiTaskCompleteListener;

        public DownloadSpeedTest(OnWiFiTaskCompleteListner wifiTaskCompleteListener) {
            this.wifiTaskCompleteListener = wifiTaskCompleteListener;
        }

        @Override
        protected String doInBackground(String... paramVarArgs) {
            try {
                final SpeedTestSocket speedTestSocket = new SpeedTestSocket();
                speedTestSocket.addSpeedTestListener(new ISpeedTestListener() {

                    @Override
                    public void onCompletion(final SpeedTestReport report) {
                        EliteSession.eLog.d(MODULE, "RateBit: " + report.getTransferRateBit() + " :: " + report.getTotalPacketSize() + " ::" + report.getTransferRateOctet());

                        String mString = report.getTransferRateBit() + "";
                        Double mDouble = Double.parseDouble(mString) / 1024;
                        mDouble = mDouble / 1024;
                        DownloadSpeed = mDouble + "";
                        new UploadSpeedTest(wifiTaskCompleteListener).execute();
                    }

                    @Override
                    public void onError(final SpeedTestError speedTestError, final String errorMessage) {
                        EliteSession.eLog.e(MODULE, errorMessage);

                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put(EliteWiFIConstants.RESPONSEMESSAGE1, EliteWiFIConstants.FAILURE_MESSAGE_NETWORKSPEED);
                            jsonObject.put(EliteWiFIConstants.REQUESTID, EliteWiFIConstants.FAILURE_CODE_NETWORKSPEED);
                        } catch (JSONException je) {
                            EliteSession.eLog.e(MODULE, je.getMessage());
                        }
                        wifiTaskCompleteListener.getResponseData(jsonObject.toString());
                    }

                    @Override
                    public void onProgress(final float percent, final SpeedTestReport downloadReport) {
                    }
                });

                speedTestSocket.startFixedDownload(SPEED_TEST_SERVER_URI_DL,
                        SPEED_TEST_DURATION, REPORT_INTERVAL);

            } catch (Exception e) {
                EliteSession.eLog.d(MODULE, e.getMessage());
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(EliteWiFIConstants.RESPONSEMESSAGE1, EliteWiFIConstants.FAILURE_MESSAGE_NETWORKSPEED);
                    jsonObject.put(EliteWiFIConstants.REQUESTID, EliteWiFIConstants.FAILURE_CODE_NETWORKSPEED);
                } catch (JSONException je) {
                    EliteSession.eLog.e(MODULE, je.getMessage());
                }
                wifiTaskCompleteListener.getResponseData(jsonObject.toString());
            }
            return null;
        }

        protected void onPostExecute(String result) {
            if (result != null) {
            }
        }
    }

    private static class UploadSpeedTest extends AsyncTask<String, Void, String> {
        public OnWiFiTaskCompleteListner wifiTaskCompleteListener;

        public UploadSpeedTest(OnWiFiTaskCompleteListner wifiTaskCompleteListener) {
            this.wifiTaskCompleteListener = wifiTaskCompleteListener;
        }

        @Override
        protected String doInBackground(String... paramVarArgs) {
            try {
                final SpeedTestSocket speedTestSocket = new SpeedTestSocket();
                speedTestSocket.addSpeedTestListener(new ISpeedTestListener() {

                    @Override
                    public void onCompletion(final SpeedTestReport report) {
                        EliteSession.eLog.d(MODULE, "RateBit: " + report.getTransferRateBit() + " :: " + report.getTotalPacketSize() + " ::" + report.getTransferRateOctet());

                        String mString = report.getTransferRateBit() + "";
                        Double mDouble = Double.parseDouble(mString) / 1024;
                        mDouble = mDouble / 1024;
                        UploadSpeed = mDouble + "";

                        spTask.saveString(UPLOAD, String.format("%.2f", Double.valueOf(UploadSpeed)));
                        spTask.saveString(DOWNLOAD, String.format("%.2f", Double.valueOf(DownloadSpeed)));
                        spTask.saveString(SYNCTIME, String.valueOf(Calendar.getInstance().getTimeInMillis()));

//                        LibraryApplication.getLibraryApplication().getCurrentActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put(EliteWiFIConstants.RESPONSEMESSAGE1, EliteWiFIConstants.SUCCESS_MESSAGE_NETWORKSPEED);
                            jsonObject.put(EliteWiFIConstants.REQUESTID, EliteWiFIConstants.SUCCESS_CODE_NETWORKSPEED);
                            jsonObject.put(EliteWiFIConstants.UPLOAD_SPEED, UploadSpeed);
                            jsonObject.put(EliteWiFIConstants.DOWNLOAD_SPEED, DownloadSpeed);
                        } catch (JSONException je) {
                            EliteSession.eLog.e(MODULE, je.getMessage());
                        }
                        wifiTaskCompleteListener.getResponseData(jsonObject.toString());
//                            }
//                        });

                    }

                    @Override
                    public void onError(final SpeedTestError speedTestError, final String errorMessage) {
                        EliteSession.eLog.e(MODULE, errorMessage);
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put(EliteWiFIConstants.RESPONSEMESSAGE1, EliteWiFIConstants.FAILURE_MESSAGE_NETWORKSPEED);
                            jsonObject.put(EliteWiFIConstants.REQUESTID, EliteWiFIConstants.FAILURE_CODE_NETWORKSPEED);
                        } catch (JSONException je) {
                            EliteSession.eLog.e(MODULE, je.getMessage());
                        }
                        wifiTaskCompleteListener.getResponseData(jsonObject.toString());
                    }

                    @Override
                    public void onProgress(final float percent, final SpeedTestReport downloadReport) {
                    }
                });
                speedTestSocket.startFixedUpload(SPEED_TEST_SERVER_URI_DL_UPLOAD, 10000000, 10000);
            } catch (Exception e) {
                EliteSession.eLog.d(MODULE, e.getMessage());
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(EliteWiFIConstants.RESPONSEMESSAGE1, EliteWiFIConstants.FAILURE_MESSAGE_NETWORKSPEED);
                    jsonObject.put(EliteWiFIConstants.REQUESTID, EliteWiFIConstants.FAILURE_CODE_NETWORKSPEED);
                } catch (JSONException je) {
                    EliteSession.eLog.e(MODULE, je.getMessage());
                }
                wifiTaskCompleteListener.getResponseData(jsonObject.toString());
            }
            return null;
        }

        protected void onPostExecute(String result) {

        }
    }


//    @SuppressLint("LongLogTag")
//    public static void showAdd(WebView wv_advertiseMent, Activity activity, final View.OnClickListener onclick, String screenName) {
//
//        String SCREEN_FOOTER = "SCREENFOOTER";
//        String SCREEN_HEADER = "SCREENHEADER";
//        String SCREEN_FULLSCREEN = "SCREENFULLSCREEN";
//
//        Log.e("screenName", "Name : " + screenName);
//
//        if (spTask.getInt(CoreConstants.ADENABLE) == 1) {
//            //Advertisement settings
//            Display mDisplay = activity.getWindowManager().getDefaultDisplay();
//            final int width = mDisplay.getWidth();
//            final int height = mDisplay.getHeight();
//
//            try {
//                EliteSession.eLog.d(MODULE, "getting advertisement if available");
//                Log.e("Hashmap", "Map : " + LibraryApplication.getLibraryApplication().getAdMappingHashMap());
//                if (LibraryApplication.getLibraryApplication().getAdMappingHashMap() != null && LibraryApplication.getLibraryApplication().getAdMappingHashMap().size() > 0) {
//                    Log.e("getAdMappingHashMap", "getAdMappingHashMap If : " + screenName);
//                    if (LibraryApplication.getLibraryApplication().getAdMappingHashMap().containsKey(screenName + "_" + SCREEN_FOOTER) && LibraryApplication.getLibraryApplication().getAdMappingHashMap().get(screenName + "_" + SCREEN_FOOTER).getScreenLocation().compareTo(CoreConstants.ADLOCATIONFOOTER) == 0) {
//                        Log.e("getAdMappingHashMap", "getAdMappingHashMap Footer If : " + screenName);
//                        wv_advertiseMent = activity.findViewById(R.id.wv_advertiseMent_footer);
//                        wv_advertiseMent.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//                        wv_advertiseMent.setVisibility(View.VISIBLE);
//                        displayAdvertisement(wv_advertiseMent, screenName, getDecodedInvocationCode(screenName + "_" + SCREEN_FOOTER), activity, onclick);
//                    } else if (LibraryApplication.getLibraryApplication().getAdMappingHashMap().containsKey(screenName + "_" + SCREEN_HEADER) && LibraryApplication.getLibraryApplication().getAdMappingHashMap().get(screenName + "_" + SCREEN_HEADER).getScreenLocation().compareTo(CoreConstants.ADLOCATIONHEADER) == 0) {
//                        Log.e("getAdMappingHashMap", "getAdMappingHashMap Header If : " + screenName);
//                        wv_advertiseMent = activity.findViewById(R.id.wv_advertiseMent_header);
//                        wv_advertiseMent.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//                        wv_advertiseMent.setVisibility(View.VISIBLE);
//                        displayAdvertisement(wv_advertiseMent, screenName, getDecodedInvocationCode(screenName + "_" + SCREEN_HEADER), activity, onclick);
//                    } else if (LibraryApplication.getLibraryApplication().getAdMappingHashMap().containsKey(screenName + "_" + SCREEN_FULLSCREEN) && LibraryApplication.getLibraryApplication().getAdMappingHashMap().get(screenName + "_" + SCREEN_FULLSCREEN).getScreenLocation().compareTo(CoreConstants.ADLOCATIONFULLSCREEN) == 0) {
//                        Log.e("getAdMappingHashMap", "getAdMappingHashMap Alert If : " + screenName);
//                        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
//                        WebView wv = new WebView(activity);
//                        wv.setLayoutParams(new LinearLayout.LayoutParams(width, height));
//                        wv.getSettings().setJavaScriptEnabled(true);
//                        wv.loadDataWithBaseURL(null, getDecodedInvocationCode(screenName + "_" + SCREEN_FULLSCREEN), "text/html", "utf-8", null);
//                        wv.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//                        wv.setWebViewClient(new WebViewClient() {
//                            @Override
//                            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                                view.loadUrl(url);
//                                return true;
//                            }
//                        });
//                        alert.setView(wv);
//                        alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.dismiss();
//                            }
//                        });
//                        alert.show();
//                    } else {
//                        Log.e("getAdMappingHashMap", "getAdMappingHashMap Else Else: " + screenName);
//                    }
//                } else {
//
//                    Log.e("getAdMappingHashMap else", "getAdMappingHashMap Else : " + screenName);
//                }
//            } catch (Exception e) {
//                // TODO: handle exception
//                EliteSession.eLog.e(MODULE, " Error in whilte getting advertisement " + e.getMessage());
//            }
//        } else {
//
//        }
//
//    }

    public static int getBatteryPercentage(Context context) {

        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, iFilter);

        int level = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : -1;
        int scale = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : -1;

        float batteryPct = level / (float) scale;

        return (int) (batteryPct * 100);
    }


    public static class LatencyCheck extends AsyncTask<String, Void, String> {


        public OnWiFiTaskCompleteListner wifiTaskCompleteListener;

        public LatencyCheck(OnWiFiTaskCompleteListner wifiTaskCompleteListener) {
            EliteSession.eLog.e(MODULE, "LatencyCheck: Async Task Execute");
            this.wifiTaskCompleteListener = wifiTaskCompleteListener;
        }


        @Override
        protected String doInBackground(String... paramVarArgs) {
            try {

                String value = getLatency(Integer.parseInt(spTask.getString(SharedPreferencesConstant.ANDSF_LATENCYPINGCOUNT)),
                        spTask.getString(SharedPreferencesConstant.ANDSF_LATENCYURL));
                wifiTaskCompleteListener.getResponseData(value);
            } catch (Exception e) {
                e.printStackTrace();
                EliteSession.eLog.d(MODULE, e.getMessage());
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(EliteWiFIConstants.RESPONSEMESSAGE1, EliteWiFIConstants.FAILURE_MESSAGE_LATENCYCHECK);
                    jsonObject.put(EliteWiFIConstants.REQUESTID, EliteWiFIConstants.FAILURE_CODE_LATENCYCHECK);
                } catch (JSONException je) {
                    EliteSession.eLog.e(MODULE, je.getMessage());
                }
                wifiTaskCompleteListener.getResponseData(jsonObject.toString());
            }
            return null;
        }

        protected void onPostExecute(String result) {

        }
    }

    private static String getLatency(int LatencyCount, String URL) {

        EliteSession.eLog.e(MODULE, "getLatency: Call");

        String pingCommand = "/system/bin/ping -c " + LatencyCount + " " + URL;
        String inputLine = "";
        //double avgRtt = 0;
        LatencyValue = 0;
        LatencySentPackets = 0;
        LatencySentPackets = 0;

        JSONObject jsonObject = new JSONObject();

        try {
            // execute the command on the environment interface
            Process process = Runtime.getRuntime().exec(pingCommand);
            // gets the input stream to get the output of the executed command
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            inputLine = bufferedReader.readLine();


            while ((inputLine != null)) {


                if (inputLine.length() > 0 && inputLine.contains("avg")) {  // when we get to the last line of executed ping command
                    String afterEqual = inputLine.substring(inputLine.indexOf("="), inputLine.length()).trim();
                    String afterFirstSlash = afterEqual.substring(afterEqual.indexOf('/') + 1, afterEqual.length()).trim();
                    String strAvgRtt = afterFirstSlash.substring(0, afterFirstSlash.indexOf('/'));
                    LatencyValue = Double.valueOf(strAvgRtt);
                }

                if (inputLine.length() > 0 && inputLine.contains("packets transmitted")) {
                    EliteSession.eLog.d("packets transmitted", "packets transmitted : " + inputLine.substring(0, 1));
                    LatencySentPackets = Integer.parseInt(inputLine.substring(0, 1));
                }

                if (inputLine.length() > 0 && inputLine.contains("received")) {
                    EliteSession.eLog.d("packets received", "packets received : " + inputLine.split(",")[1].trim().substring(0, 1));
                    LatencyReceivePackets = Integer.parseInt(inputLine.split(",")[1].trim().substring(0, 1));
                }
                inputLine = bufferedReader.readLine();
                EliteSession.eLog.d("inputLine", "inputLine : " + inputLine);
//                if(inputLine != null || !inputLine.equals(null) || !inputLine.equals("") || !inputLine.equals("null")) {
//
//                }

                EliteSession.eLog.i(MODULE, "LatencySentPackets = " + LatencySentPackets
                        + " LatencyReceivePackets : " + LatencyReceivePackets + " LatencyValue : " + LatencyValue);
            }


            try {

                spTask.saveString(CoreConstants.LATENCY, String.valueOf(LatencyValue));
                spTask.saveString(CoreConstants.LATENCY_SYNC_TIME, String.valueOf(Calendar.getInstance().getTimeInMillis()));

                jsonObject.put(EliteWiFIConstants.RESPONSEMESSAGE1, EliteWiFIConstants.SUCCESS_MESSAGE_LATENCYCHECK);
                jsonObject.put(EliteWiFIConstants.REQUESTID, EliteWiFIConstants.SUCCESS_CODE_LATENCYCHECK);
                jsonObject.put(EliteWiFIConstants.ANDSF_LATENCYVALUE, LatencyValue);
                jsonObject.put(EliteWiFIConstants.ANDSF_LATENCYPACKETSENT, LatencySentPackets);
                jsonObject.put(EliteWiFIConstants.ANDSF_LATENCYPACKETRECIEVE, LatencyReceivePackets);
            } catch (JSONException je) {
                EliteSession.eLog.e(MODULE, je.getMessage());
                je.printStackTrace();
            }
        } catch (IOException e) {
            EliteSession.eLog.e(MODULE, "getLatency: EXCEPTION");
            try {
                jsonObject.put(EliteWiFIConstants.RESPONSEMESSAGE1, EliteWiFIConstants.FAILURE_MESSAGE_LATENCYCHECK);
                jsonObject.put(EliteWiFIConstants.REQUESTID, EliteWiFIConstants.FAILURE_CODE_LATENCYCHECK);

            } catch (JSONException je) {
                EliteSession.eLog.e(MODULE, je.getMessage());
            }
            e.printStackTrace();
        }

        // Extracting the average round trip time from the inputLine string


        return jsonObject.toString();
    }

    private static boolean getIpv4() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    System.out.println("ip1--:" + inetAddress);
                    System.out.println("ip2--:" + inetAddress.getHostAddress());

                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        String ipaddress = inetAddress.getHostAddress().toString();
                        EliteSession.eLog.i("Device IPV4 ip is " + ipaddress);
                        if (ipaddress.equals("") || ipaddress.equals(null) || ipaddress != null) {
                            return false;
                        } else {
                            return true;
                        }

                    }


                }
            }
        } catch (Exception ex) {
            Log.e("IP Address", ex.toString());
        }
        return false;
    }

    public static GsmCellLocation geGSMCellLocation(Context context) {
        CustomPhoneStateListner listner = new CustomPhoneStateListner(context);
        callPhoneStateListner(context,listner);
        return listner.getCellLocation();
    }



    private static void callPhoneStateListner(Context context, CustomPhoneStateListner listner) {
        TelephonyManager tManager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        tManager.listen(listner,
                PhoneStateListener.LISTEN_CALL_STATE
                        | PhoneStateListener.LISTEN_CELL_INFO // Requires API 17
                        | PhoneStateListener.LISTEN_CELL_LOCATION
                        | PhoneStateListener.LISTEN_DATA_ACTIVITY
                        | PhoneStateListener.LISTEN_DATA_CONNECTION_STATE
                        | PhoneStateListener.LISTEN_SERVICE_STATE
                        | PhoneStateListener.LISTEN_SIGNAL_STRENGTHS
                        | PhoneStateListener.LISTEN_CALL_FORWARDING_INDICATOR
                        | PhoneStateListener.LISTEN_MESSAGE_WAITING_INDICATOR);
    }

    //ipv6
    private static boolean getLocalIpV6() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {

                    InetAddress inetAddress = enumIpAddr.nextElement();
                    System.out.println("ip1--:" + inetAddress);
                    System.out.println("ip2--:" + inetAddress.getHostAddress());

                    if (!inetAddress.isLinkLocalAddress() && inetAddress instanceof Inet6Address) {
                        String ipaddress = inetAddress.getHostAddress().toString();
                        EliteSession.eLog.i("Device IPV6 ip is " + ipaddress);
                        if (ipaddress.equals("") || ipaddress.equals(null) || ipaddress != null) {
                            return false;
                        } else {
                            return true;
                        }

                    }


                }
            }
        } catch (Exception ex) {
            Log.e("IP Address", ex.toString());
        }
        return false;
    }

    public static boolean isOnlyIPV4() {

        boolean isIPV4 = getIpv4();
        boolean isIPV6 = getLocalIpV6();

        if (isIPV6 == false) {
            return true;
        } else {
            return false;
        }

    }
}

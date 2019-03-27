package com.elitecorelib.core;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.wifi.WifiManager;

import com.elitecorelib.core.fcm.NotificationClass;
import com.elitecorelib.core.pojo.PojoAdMapping;
import com.elitecorelib.core.pojo.PojoSecure;
import com.elitecorelib.core.utility.EliteAnalytics;
import com.elitecorelib.core.utility.ElitelibUtility;
import com.elitecorelib.core.utility.SharedPreferencesTask;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONObject;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;

import static com.elitecorelib.core.CoreConstants.GRADLE_MONETIZATION_SERVER_URL;
import static com.elitecorelib.core.utility.ElitelibUtility.getMetaDataValue;

public class LibraryApplication {

    private static PojoSecure getterSetterObj;
    private static LibraryApplication libraryApplication;
    private static String MODULE = "LibraryApplication";
    public EliteAnalytics eliteAnalytics;
    public NotificationClass notificationClass;
    private Context libraryContext;
    private SharedPreferencesTask librarySharedPreferences;
    private WifiManager wifiManager;
    //Local Or server side License Mechanism.
    private String licenseMechanism;
    private JSONObject faceBookUser;
    private Person googleUser;
    private String loggedInUserName;
    private boolean isToggleByCode;
    private Location locationToCheckDistanceWith;
    private Activity activity;
    private HashMap<String, PojoAdMapping> adMappingHashMap = new HashMap<String, PojoAdMapping>();
    private GoogleSignInAccount mGoogleSignInAccount;

    public static LibraryApplication getLibraryApplication() {
        if (libraryApplication == null) {
            libraryApplication = new LibraryApplication();
            return libraryApplication;
        } else {
            return libraryApplication;
        }
    }

    public static PojoSecure getGetterSetterObj() {
        try {
            if (getterSetterObj == null) {
                getterSetterObj = (PojoSecure) ElitelibUtility.fromString(getSerialKey());
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return getterSetterObj;
    }

    private static String getSerialKey() {
        try {
            String monetization_key  = getMetaDataValue(GRADLE_MONETIZATION_SERVER_URL);
            EliteSession.eLog.d(MODULE + "Serial Key set by user application is :" + monetization_key);
            return monetization_key;

        } catch (Exception e) {
            // TODO: handle exception
            EliteSession.eLog.e(MODULE + " Error while getting serial  key from the menifest file.");
        }
        return null;

    }


    public static boolean isLicenseValidated(Context context) {
        try {
            BasicLicenseValidator validatorObj = new BasicLicenseValidator();
            if (validatorObj.isValidLicense(context))
                return true;
            else
                return false;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return false;

    }

    public NotificationClass getNotificationClass() {
        return notificationClass;
    }

    public void setNotificationClass(NotificationClass notificationClass) {
        this.notificationClass = notificationClass;
    }

    public EliteAnalytics getEliteAnalytics() {
        if (eliteAnalytics == null) {
            eliteAnalytics = new EliteAnalytics(LibraryApplication.getLibraryApplication().getLibraryContext());
            return eliteAnalytics;
        }

        return eliteAnalytics;
    }

    public HashMap<String, PojoAdMapping> getAdMappingHashMap() {
        return adMappingHashMap;
    }

    public void setAdMappingHashMap(HashMap<String, PojoAdMapping> adMappingHashMap) {
        this.adMappingHashMap = adMappingHashMap;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Activity getCurrentActivity() {
        return activity;
    }

    public boolean isToggleByCode() {
        return isToggleByCode;
    }

    public void setToggleByCode(boolean isToggleByCode) {
        this.isToggleByCode = isToggleByCode;
    }

    public Location getLocationToCheckDistanceWith() {
        return locationToCheckDistanceWith;
    }

    public void setLocationToCheckDistanceWith(Location locationToCheckDistanceWith) {
        this.locationToCheckDistanceWith = locationToCheckDistanceWith;
    }

    public String getLoggedInUserName() {
        return loggedInUserName;
    }

    public void setLoggedInUserName(String loggedInUserName) {
        this.loggedInUserName = loggedInUserName;
    }

    public Context getLibraryContext() {
        return libraryContext;
    }

    public void setLibraryContext(Context libraryContext) {
        this.libraryContext = libraryContext;
    }

    public SharedPreferencesTask getlibrarySharedPreferences() {
        return librarySharedPreferences;
    }

    public void setlibrarySharedPreferences(
            SharedPreferencesTask librarySharedPreferences) {
        this.librarySharedPreferences = librarySharedPreferences;
    }

    public WifiManager getWiFiManager() {
        if (wifiManager == null) {
            wifiManager = (WifiManager) LibraryApplication.getLibraryApplication().getLibraryContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        }
        return wifiManager;
    }

    public String getLicenseMechanism() {
        return licenseMechanism;
    }

    public void setLicenseMechanism(String licenseMechanism) {
        this.licenseMechanism = licenseMechanism;
    }

    public String getSomething() {
        String something = "";
        try {
            something = LicenseUtility.decrypt(CoreConstants.ELITECORE_PASSWORD);
        } catch (GeneralSecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return something;
    }

    public JSONObject getFaceBookUser() {
        return faceBookUser;
    }

    public void setFaceBookUser(JSONObject faceBookUser) {
        this.faceBookUser = faceBookUser;
    }

    public Person getGoogleUser() {
        return googleUser;
    }

    public void setGoogleUser(Person account) {
        this.googleUser = account;
    }

    public GoogleSignInAccount getmGoogleSignInAccount() {
        return mGoogleSignInAccount;
    }

    public void setmGoogleSignInAccount(GoogleSignInAccount mGoogleSignInAccount) {
        this.mGoogleSignInAccount = mGoogleSignInAccount;
    }
}

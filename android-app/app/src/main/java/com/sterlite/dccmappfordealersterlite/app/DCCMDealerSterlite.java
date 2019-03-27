package com.sterlite.dccmappfordealersterlite.app;

import android.content.Context;
import android.location.Location;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;

import com.crashlytics.android.Crashlytics;
import com.elitecore.elitesmp.utility.ElitePropertiesUtil;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.dao.DBOperation;
import com.elitecorelib.core.utility.SharedPreferenceConstants;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import io.fabric.sdk.android.Fabric;
import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.data.AppDataManager;
import com.sterlite.dccmappfordealersterlite.data.DataManager;
import com.sterlite.dccmappfordealersterlite.data.db.AppDbHelper;
import com.sterlite.dccmappfordealersterlite.data.network.AppApiHelper;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.helper.Foreground;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by etech3 on 21/11/17.
 */

public class DCCMDealerSterlite extends MultiDexApplication {
    public static Location globleLocation = null;
    public static Context appContext;
    private static DCCMDealerSterlite appClass;
    private static DataManager dataManager;

    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;
    private String dbPath;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        Fabric.with(this, new Crashlytics());
        dataManager = new AppDataManager(this, new AppDbHelper(), new AppPreferencesHelper(this), new AppApiHelper());
        AppUtils.checkOrCreateAppDirectories(getApplicationContext());
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(getApplicationContext().getString(R.string.font_regular))
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        Foreground forground = Foreground.init(this);

        sAnalytics = GoogleAnalytics.getInstance(this);

        EliteSession.setELiteConnectSession(this);
        LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().saveInt(SharedPreferenceConstants.PROCESSID, android.os.Process.myPid());

        dbPath = getFilesDir().getAbsolutePath();
        DCCMDealerSterlite.getApplication().setDbPath(dbPath);
        LibraryApplication.getLibraryApplication().setAdMappingHashMap(DBOperation.getDBHelperOperation().getAdMappingHashMap());


        try {
            ElitePropertiesUtil.createInstance(R.raw.elitesmp);
        } catch (Exception e) {
            EliteSession.eLog.e("App", e.getMessage());
        }

    }

    public static DCCMDealerSterlite getApplication() {
        if (appClass == null) {
            appClass = new DCCMDealerSterlite();
        }
        return appClass;
    }

    public String getDbPath() {
        return dbPath;
    }

    public void setDbPath(String dbPath) {
        this.dbPath = dbPath;
    }

    public static DataManager getDataManager() {
        return dataManager;
    }

    synchronized public Tracker getDefaultTracker() {
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (sTracker == null) {
            sTracker = sAnalytics.newTracker(R.xml.global_tracker);
        }

        return sTracker;
    }

}

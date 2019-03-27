package com.elitecorelib.analytics.realm;

import android.content.Context;

import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.realm.RealmClassInvoke;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AnalyticsRealmClassInvoke {

    public static AnalyticsRealmClassInvoke realmClass;
    private static Realm analyticsRealm;
    private final String MODULE = "AnalyticsRealmClass";
    private Context mContext;
    private RealmConfiguration realmConfiguration;
    private long dbVersion;
    private String filename = "AnalyticsSterlite";
    private String DB_FILE_FORMATE = ".realm";

    public AnalyticsRealmClassInvoke(Context context) {
        this.mContext = context;
    }

    public static AnalyticsRealmClassInvoke getAnalyticsRealmInstance(Context context) {
        if (realmClass == null) {
            return new AnalyticsRealmClassInvoke(context);
        } else {
            return realmClass;
        }
    }

    public static Realm getAnalyticsRealm() {
        return analyticsRealm;
    }

    public Long getDatabaseVersion() {
        return dbVersion;
    }

    public void setDatabaseVersion(long version) {
        this.dbVersion = version;
    }

    public void init() {

        try {
            EliteSession.eLog.i(MODULE, "Initialize AnalyticsRealmClass");

            if (dbVersion == 0) {
                EliteSession.eLog.e(MODULE, "Database Version not set");
                return;

            }
            String appName = null;
            try {
//            appName = mContext.getString(mContext.getApplicationInfo().labelRes);
                appName = "AnalyticsSterlite";
            } catch (Exception e) {
                appName = "AnalyticsSterlite";
            }
            try {
                realmConfiguration = new RealmConfiguration.Builder()
                        .name(this.filename + DB_FILE_FORMATE)
                        .schemaVersion(getDatabaseVersion())
                        .deleteRealmIfMigrationNeeded()
//                .encryptionKey(CoreConstants.SCHEMA_ENCRYPTIONKEY.getBytes())
                        .build();

                // Realm.setDefaultConfiguration(realmConfiguration);

                if (new File(realmConfiguration.getPath()).exists()) {
                    EliteSession.eLog.d(MODULE, "realm exist ");
                } else {
                    EliteSession.eLog.d(MODULE, "Realm not exist ");
                }

                this.analyticsRealm = Realm.getInstance(realmConfiguration);
            } catch (Exception e) {
                EliteSession.eLog.d(MODULE, "Realm not exist " + e.getMessage());
            }


        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Create Realm Error - " + e.getMessage());
        }
    }
}

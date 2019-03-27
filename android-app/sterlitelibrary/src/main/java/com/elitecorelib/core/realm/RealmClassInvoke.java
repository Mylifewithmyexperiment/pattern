package com.elitecorelib.core.realm;

import android.content.Context;

import com.elitecorelib.core.EliteSession;

import java.io.File;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;

/**
 * Created by Chirag Parmar on 12/03/18.
 */

public class RealmClassInvoke {

    public static RealmClassInvoke realmClass;
    private final String MODULE = "RealmClass";
    private Context mContext;
    private RealmConfiguration realmConfiguration;
    private long dbVersion;
    private String DB_FILE_FORMATE = ".realm";

    public RealmClassInvoke(Context context) {
        this.mContext = context;
    }

    public static RealmClassInvoke getInstance(Context context) {

        if (realmClass == null) {
            return new RealmClassInvoke(context);
        } else {
            return realmClass;
        }
    }

    public Long getDatabaseVersion() {
        return dbVersion;
    }

    public void setDatabaseVersion(long version) {
        this.dbVersion = version;
    }

    public void init() {

        try {
            EliteSession.eLog.i(MODULE, "Initialize RealmClass");

            if (dbVersion == 0) {
                EliteSession.eLog.e(MODULE, "Database Version not set");
                return;

            }

            Realm.init(mContext);

            String appName = null;
            try {
//            appName = mContext.getString(mContext.getApplicationInfo().labelRes);
                appName = "SterliteApp";
            } catch (Exception e) {
                appName = "SterliteApp";
            }
            try {
                realmConfiguration = new RealmConfiguration.Builder()
                        .name(appName + DB_FILE_FORMATE)
                        .schemaVersion(getDatabaseVersion())
                        .deleteRealmIfMigrationNeeded()
//                .encryptionKey(CoreConstants.SCHEMA_ENCRYPTIONKEY.getBytes())
                        .build();

                Realm.setDefaultConfiguration(realmConfiguration);


                if (new File(realmConfiguration.getPath()).exists()) {
                    EliteSession.eLog.d(MODULE, "realm exist ");
                } else {
                    EliteSession.eLog.d(MODULE, "Realm not exist ");
                }

            } catch (Exception e) {
                EliteSession.eLog.d(MODULE, "Realm not exist " + e.getMessage());
            }


        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Create Realm Error - " + e.getMessage());
        }
    }
}

package com.elitecorelib.core;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.elitecorelib.core.fcm.NotificationClass;
import com.elitecorelib.core.logger.EliteLog;
import com.elitecorelib.core.logger.EliteMail;
import com.elitecorelib.core.realm.RealmClassInvoke;
import com.elitecorelib.core.utility.SharedPreferencesTask;

import java.security.MessageDigest;

public class EliteSession {

    private static final String MODULE = "EliteSession";
    public static EliteLog eLog;
    public static EliteMail eMail;
    private static EliteSession session;

    public static void setELiteConnectSession(Context context) {
        if (session == null) {
            session = new EliteSession();
            LibraryApplication.getLibraryApplication().setLibraryContext(context);
            LibraryApplication.getLibraryApplication().setlibrarySharedPreferences(new SharedPreferencesTask());
            LibraryApplication.getLibraryApplication().setLicenseMechanism(CoreConstants.CURRENT_LICENSE_MECHANISAM);

            eLog = EliteLog.getLogInstance();
            eMail = EliteMail.getMailInstance();
            invokerealmDB(context);

            if (LibraryApplication.getLibraryApplication().getLicenseMechanism().compareTo(CoreConstants.LICENSE_SERVER) == 0) {
                if (LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY).compareTo("") == 0)
                    setSecurity();
            } else {
                if (LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.ELITECORE_LICENSE_KEY).compareTo("") == 0)
                    setApplicationKey(context);
            }
            LibraryApplication.getLibraryApplication().setNotificationClass(NotificationClass.getIntance(context));

        }
    }

    private static void setSecurity() {
        EliteSession.eLog.d(MODULE + " validating security information");
        StringBuffer hexString = new StringBuffer();
        try {
            StringBuilder encrypt = new StringBuilder();
            if (LibraryApplication.getGetterSetterObj() != null) {
//				if(LibraryApplication.getGetterSetterObj().getHOSTNAME()!=null)
//				{
//					encrypt.append(LibraryApplication.getGetterSetterObj().getHOSTNAME());
//				}
//                if (LibraryApplication.getGetterSetterObj().getPACKAGENAME() != null) {
//                    encrypt.append(LibraryApplication.getGetterSetterObj().getPACKAGENAME());
//                }
                encrypt.append("sterliteapp.com.sterlitemobilecardappdesign");
                if (LibraryApplication.getGetterSetterObj().getSHAREDSECRETKEY() != null) {
                    encrypt.append(LibraryApplication.getGetterSetterObj().getSHAREDSECRETKEY());
                }

                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(encrypt.toString().getBytes("UTF-8"));

                for (int i = 0; i < hash.length; i++) {
                    String hex = Integer.toHexString(0xff & hash[i]);
                    if (hex.length() == 1) hexString.append('0');
                    hexString.append(hex);
                }
                EliteSession.eLog.d(MODULE + " Generate Secret key successfully");
                LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().saveString(CoreConstants.SECRETKEY, hexString.toString());
            }
//			LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().saveString(CoreConstants.SECRETKEY, "39943779bf9ad09ebc11567b56048cb81e927028169507946a5a6d462a2162b7");
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE + " Security setting error " + e.getMessage());
        }

    }

    private static void setApplicationKey(Context _context) {
        try {
            EliteSession.eLog.d(MODULE + " Reading key from the android menifest file.");
            ApplicationInfo ai = _context.getPackageManager().getApplicationInfo(_context.getPackageName(), PackageManager.GET_META_DATA);
            String key = ai.metaData.get(CoreConstants.ELITECORE_LICENSE_KEY).toString();
            EliteSession.eLog.d(MODULE + " Key set by user application is :" + key);
            LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().saveString(CoreConstants.ELITECORE_LICENSE_KEY, key);
        } catch (Exception e) {
            // TODO: handle exception
            EliteSession.eLog.e(MODULE + " Error while getting application key from the menifest file.");
        }

    }

    private static void invokerealmDB(Context context) {
        EliteSession.eLog.d(MODULE + " invokeREalm DB");
        RealmClassInvoke realmDB = RealmClassInvoke.getInstance(context);
        realmDB.setDatabaseVersion(1);
        realmDB.init();
    }

}

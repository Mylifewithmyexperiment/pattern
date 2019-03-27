package com.elitecorelib.core.utility;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.elitecore.wifi.pojo.PojoConnection;
import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.dao.DBOperation;
import com.elitecorelib.core.dao.DBQueryFieldConstants;
import com.elitecorelib.core.pojo.PojoProfile;
import com.elitecorelib.core.services.ConnectionManagerCompleteListner;
import com.elitecorelib.wifi.utility.WifiUtility;

import java.io.IOException;
import java.lang.reflect.Method;
import java.security.GeneralSecurityException;

public class DefaultConfig implements ConnectionManagerCompleteListner {


    private static final String MODULE = "DefaultConfig";
    private static DefaultConfig defaultConfig = null;
    private PojoConnection defaultConnection;
    private String imsi;
    private SharedPreferencesTask spTask = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();
    private PojoProfile dafaultProfile;
    private Context context = LibraryApplication.getLibraryApplication().getLibraryContext();
    private String requiredSimOperatorName;

    private DefaultConfig() {
        try {
            String simOperatorName = null;
            TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            try {
                Class<?> telephonyClass = Class.forName(telephony.getClass().getName());
                Class<?>[] methodParameter = new Class[1];
                methodParameter[0] = int.class;
                requiredSimOperatorName = ElitelibUtility.getConfigProperty("SimOperatorName");
                if (requiredSimOperatorName == null) {
                    EliteSession.eLog.e(MODULE,
                            "RequiredSimOperatorName is null please configure in configuration.properties reading default IMSI from available slot");
                    imsi = telephony.getSubscriberId();
                    EliteSession.eLog.e(MODULE, "IMSI is " + imsi);
                    return;
                }
                Method getSimOperatorNameDs = telephonyClass.getMethod("getSimOperatorNameDs", methodParameter);
                if (getSimOperatorNameDs != null) {
                    Object[] slotNumber = new Object[1];
                    slotNumber[0] = 0;
                    simOperatorName = (String) getSimOperatorNameDs.invoke(telephony, slotNumber);
                    Method getSubscriberIdDs;
                    if (simOperatorName != null && simOperatorName.equalsIgnoreCase(requiredSimOperatorName)) {
                        EliteSession.eLog.e(MODULE, "SIM Operator Name from first slot is :" + simOperatorName);
                        getSubscriberIdDs = telephonyClass.getMethod("getSubscriberIdDs", methodParameter);
                        slotNumber[0] = 0;
                        if (getSubscriberIdDs != null) {
                            imsi = (String) getSubscriberIdDs.invoke(telephony, slotNumber);
                            EliteSession.eLog.e(MODULE, "IMSI from first slot is :" + imsi);
                        } else {
                            EliteSession.eLog.e(MODULE, "Method getSubscriberIdDs is null reading default IMSI from available slot");
                            imsi = telephony.getSubscriberId();
                            EliteSession.eLog.e(MODULE, "IMSI is " + imsi);
                        }
                    } else {
                        EliteSession.eLog.e(MODULE, "SIM Operator Name is not " + requiredSimOperatorName + " trying second slot");
                        slotNumber[0] = 1;
                        simOperatorName = (String) getSimOperatorNameDs.invoke(telephony, slotNumber);
                        if (simOperatorName != null && simOperatorName.equalsIgnoreCase(requiredSimOperatorName)) {
                            EliteSession.eLog.e(MODULE, "SIM Operator Name from Second slot is :" + simOperatorName);
                            getSubscriberIdDs = telephonyClass.getMethod("getSubscriberIdDs", methodParameter);
                            slotNumber[0] = 1;
                            if (getSubscriberIdDs != null) {
                                imsi = (String) getSubscriberIdDs.invoke(telephony, slotNumber);
                                EliteSession.eLog.e(MODULE, "IMSI from second slot is :" + imsi);
                            } else {
                                EliteSession.eLog.e(MODULE, "Method getSubscriberIdDs is null reading default IMSI from available slot");
                                imsi = telephony.getSubscriberId();
                                EliteSession.eLog.e(MODULE, "IMSI is " + imsi);
                            }
                        } else {
                            if (simOperatorName != null) {
                                EliteSession.eLog.e(MODULE, "SIM Operator Name " + simOperatorName + " is which is not required sim operator");
                            }
                            EliteSession.eLog.e(MODULE, "SIM Operator Name " + requiredSimOperatorName + " is not in second slot also");
                            imsi = null;
                            EliteSession.eLog.e(MODULE, "IMSI is " + imsi);
                        }
                    }
                } else {
                    EliteSession.eLog.e(MODULE, "Method getSimOperatorNameDs is null reading default imsi from available slot");
                    imsi = telephony.getSubscriberId();
                    EliteSession.eLog.e(MODULE, "IMSI is " + imsi);
                }
            } catch (Exception e) {
                EliteSession.eLog.e(MODULE, "in first catch Exception");
                try {
                    Class<?> telephonyClass = Class.forName(telephony.getClass().getName());
                    Class<?>[] methodParameter = new Class[1];
                    methodParameter[0] = int.class;
                    Method getSimOperatorName = telephonyClass.getMethod("getSimOperatorName", methodParameter);
                    if (getSimOperatorName != null) {
                        Object[] slotNumber = new Object[1];
                        slotNumber[0] = 0;
                        simOperatorName = (String) getSimOperatorName.invoke(telephony, slotNumber);
                        Method getSubscriberId;
                        requiredSimOperatorName = ElitelibUtility.getConfigProperty("SimOperatorName");
                        if (requiredSimOperatorName == null) {
                            EliteSession.eLog.d(MODULE,
                                    "RequiredSimOperatorName is null please configure in configuration.properties reading default IMSI from available slot");
                            imsi = telephony.getSubscriberId();
                            EliteSession.eLog.e(MODULE, "IMSI is " + imsi);
                            return;
                        }
                        if (simOperatorName != null && simOperatorName.equalsIgnoreCase(requiredSimOperatorName)) {
                            EliteSession.eLog.e(MODULE, "SIM Operator Name from first slot is :" + simOperatorName);
                            getSubscriberId = telephonyClass.getMethod("getSubscriberId", methodParameter);
                            slotNumber[0] = 0;
                            if (getSubscriberId != null) {
                                imsi = (String) getSubscriberId.invoke(telephony, slotNumber);
                                EliteSession.eLog.e(MODULE, "IMSI from first slot is :" + imsi);
                            } else {
                                EliteSession.eLog.e(MODULE, "Method getSubscriberIdDs is null reading default IMSI from available slot");
                                imsi = telephony.getSubscriberId();
                                EliteSession.eLog.e(MODULE, "IMSI is " + imsi);
                            }
                        } else {
                            EliteSession.eLog.e(MODULE, "SIM Operator Name is not " + requiredSimOperatorName + " trying second slot");
                            slotNumber[0] = 1;
                            simOperatorName = (String) getSimOperatorName.invoke(telephony, slotNumber);
                            if (simOperatorName != null && simOperatorName.equalsIgnoreCase(requiredSimOperatorName)) {
                                EliteSession.eLog.e(MODULE, "SIM Operator Name from Second slot is :" + simOperatorName);
                                getSubscriberId = telephonyClass.getMethod("getSubscriberId", methodParameter);
                                slotNumber[0] = 1;
                                if (getSubscriberId != null) {
                                    imsi = (String) getSubscriberId.invoke(telephony, slotNumber);
                                    EliteSession.eLog.e(MODULE, "IMSI from second slot is :" + imsi);
                                } else {
                                    EliteSession.eLog.e(MODULE, "Method getSubscriberIdDs is null reading default IMSI from available slot");
                                    imsi = telephony.getSubscriberId();
                                    EliteSession.eLog.e(MODULE, "IMSI is " + imsi);
                                }
                            } else {
                                if (simOperatorName != null) {
                                    EliteSession.eLog.e(MODULE, "SIM Operator Name " + simOperatorName + " is which is not required sim operator");
                                }
                                EliteSession.eLog.e(MODULE, "SIM Operator Name " + requiredSimOperatorName + " is not in second slot also");
                                imsi = null;
                                EliteSession.eLog.e(MODULE, "IMSI is " + imsi);
                            }
                        }
                    } else {
                        EliteSession.eLog.e(MODULE, "Method getSimOperatorNameDs is null reading default imsi from available slot");
                        imsi = telephony.getSubscriberId();
                        EliteSession.eLog.e(MODULE, "IMSI is " + imsi);
                    }
                } catch (Exception ee) {
                    if (telephony != null) {
                        EliteSession.eLog.e(MODULE, "Exception :" + ee.getMessage() + " so reading default imsi from available slot");
                        imsi = telephony.getSubscriberId();
                    }
                    EliteSession.eLog.e(MODULE, "IMSI is " + imsi);
                }
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    public static synchronized DefaultConfig getInstance() {
        try {
            if (null == defaultConfig) {
                defaultConfig = new DefaultConfig();
            }
        } catch (Exception e) {

        }
        return defaultConfig;
    }

    public void setDefaultConnection(String authMethod) {
        defaultConnection = new PojoConnection();
        String defaultSSID = ElitelibUtility.getConfigProperty(CoreConstants.DEFAULT_SSID);
        EliteSession.eLog.d(MODULE, " Default SSID - "+defaultSSID);
        defaultConnection.setSsid(defaultSSID);
        defaultConnection.setSecurity(ElitelibUtility.getConfigProperty(CoreConstants.DEFAULT_SECURITY));
        defaultConnection.setEncryptionMethod(WifiUtility.getEncryptionType(defaultSSID));
        defaultConnection.setAuthenticationMethod(authMethod);
        defaultConnection.setPhase2Authentication(ElitelibUtility.getConfigProperty(CoreConstants.DEFAULT_PHASE2_AUTHENTICATION));
        defaultConnection.setIdentity(imsi);
        String passString = ElitelibUtility.getConfigProperty(CoreConstants.DEFAULT_PASSWORD);
        try {
            String pass = ElitelibUtility.decrypt(passString);
            defaultConnection.setPassword(pass);
            EliteSession.eLog.d("in default conf file encrypted password is after decryption" + defaultConnection.getPassword());
        } catch (GeneralSecurityException e1) {
            EliteSession.eLog.e(MODULE, e1.getMessage());
        } catch (IOException e1) {
            EliteSession.eLog.e(MODULE, e1.getMessage());
        } catch (Exception e) {
        }
        defaultConnection.setShowPassword(false);
        defaultConnection.setDefault(true);
        defaultConnection.setActive(true);
        spTask.saveString(SharedPreferencesConstant.ACTIVECONNECTION, defaultSSID);
        spTask.saveString(SharedPreferencesConstant.DEFAULTCONNECTION, defaultSSID);
        try {
            DBOperation.getDBHelperOperation().insertConnection(defaultConnection);
            addConnectionToProfile(defaultConnection);
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        } finally {

        }
    }

    public void setDefaultProfile() {
        dafaultProfile = new PojoProfile();
        String strProfile = ElitelibUtility.getConfigProperty(CoreConstants.DEFAULT_PROFILE_NAME);
        dafaultProfile.setName(strProfile);
        dafaultProfile.setDefault(true);
        dafaultProfile.setActive(true);
        spTask.saveString(SharedPreferencesConstant.ACTIVEPROFILE, strProfile);
        try {
            DBOperation.getDBHelperOperation().insertProfile(dafaultProfile);
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    private void addConnectionToProfile(PojoConnection connection) {
        try {
            // Add default profile-connection relation into relation DB.
            DBOperation.getDBHelperOperation().insertRelation(dafaultProfile.getName(), connection.getSsid());
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    public void doFactoryReset() {
        DBOperation.getDBHelperOperation().deleteAllValues(DBQueryFieldConstants.TABLENAME_CONNECTION);
        DBOperation.getDBHelperOperation().deleteAllValues(DBQueryFieldConstants.TABLENAME_PROFILE);
        DBOperation.getDBHelperOperation().deleteAllValues(DBQueryFieldConstants.TABLENAME_RELATION);
        //spTask.clearPreferences();
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    @Override
    public void onConnnectionManagerTaskComplete(String wifiServerData, int requestId) {
        // TODO Auto-generated method stub

    }
}
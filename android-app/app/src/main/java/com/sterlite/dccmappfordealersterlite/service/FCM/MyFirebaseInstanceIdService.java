package com.sterlite.dccmappfordealersterlite.service.FCM;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.data.network.ApiHelper;
import com.sterlite.dccmappfordealersterlite.data.network.model.DeviceToken;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    @SuppressLint("LongLogTag")
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        Log.e("MyFirebaseInstanceIdService", "" + FirebaseInstanceId.getInstance().getToken());
        DCCMDealerSterlite.getDataManager().setBoolean(AppPreferencesHelper.PREF_IS_FIREBASE_TOKEN_CHANGED, true);
        registerDeviceTokenToServer();
    }


    public static void registerDeviceTokenToServer() {
        String USER_ID = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.PREF_SUBSCRIPTION_NUMBER, "");
        if (!DCCMDealerSterlite.getDataManager().getBoolean(AppPreferencesHelper.PREF_IS_FIREBASE_TOKEN_CHANGED, false)) {
            return;
        }
//        String USER_ID = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID, "");
        if (USER_ID.equalsIgnoreCase(""))
            return;

        DeviceToken token = new DeviceToken();
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        token.setDeviceToken(refreshToken);
        token.setEnvironment("production");
        TelephonyManager tm = (TelephonyManager) DCCMDealerSterlite.appContext.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (DCCMDealerSterlite.appContext.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    if (tm != null && tm.getDeviceId() != null) {
                        token.setDeviceId(tm.getDeviceId());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Build.MODEL != null)
            token.setDeviceModel(Build.MODEL);

        if (Build.BRAND != null)
            token.setDeviceName(Build.BRAND);

        token.setDeviceVersion(Build.VERSION.SDK_INT + "");
        token.setLanguage(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.LOCALE, Constants.Preffered_Language));
        token.setUserId(USER_ID);
        DCCMDealerSterlite.getDataManager().doRegisterDeviceTokenToServer(token, new ApiHelper.OnApiCallback<DeviceToken>() {
            @Override
            public void onSuccess(DeviceToken baseResponse) {
                DCCMDealerSterlite.getDataManager().setBoolean(AppPreferencesHelper.PREF_IS_FIREBASE_TOKEN_CHANGED, false);
            }

            @Override
            public void onFailed(int code, String message) {
            }
        });
    }
}

/*

    public static void registerDeviceTokenToServer() {
        String userId = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.PREF_SUBSCRIPTION_NUMBER, "");

        if (!DCCMDealerSterlite.getDataManager().getBoolean(AppPreferencesHelper.PREF_IS_FIREBASE_TOKEN_CHANGED, false)) {
            return;
        }
        //String userId = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID, "");
        if (userId.equalsIgnoreCase("")) {
            return;
        }
//        String USER_ID = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID, "");
        if (userId.equalsIgnoreCase(""))
            return;

        DeviceToken token = new DeviceToken();
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        token.setDeviceToken(refreshToken);
        token.setEnvironment("production");
        TelephonyManager tm = (TelephonyManager) DCCMDealerSterlite.appContext.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (DCCMDealerSterlite.appContext.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    if (tm != null && tm.getDeviceId() != null) {
                        token.setDeviceId(tm.getDeviceId());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Build.MODEL != null)
            token.setDeviceModel(Build.MODEL);

        if (Build.BRAND != null)
            token.setDeviceName(Build.BRAND);

        token.setDeviceVersion(Build.VERSION.SDK_INT + "");
        token.setLanguage(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.LOCALE, "en"));
        token.setUserId(userId);
        DCCMDealerSterlite.getDataManager().doRegisterDeviceTokenToServer(token, new ApiHelper.OnApiCallback<DeviceToken>() {
            @Override
            public void onSuccess(DeviceToken baseResponse) {
                DCCMDealerSterlite.getDataManager().setBoolean(AppPreferencesHelper.PREF_IS_FIREBASE_TOKEN_CHANGED, false);
            }

            @Override
            public void onFailed(int code, String message) {
            }
        });
    }
}
*/

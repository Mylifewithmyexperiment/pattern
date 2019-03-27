package com.elitecorelib.core.sms;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.elitecorelib.core.EliteSession;


/**
 * Created by nehal.maheshwari on 9/4/2015.
 */
public class SmsService extends Service {
    private final String MODULE = "SmsService";
    private IntentFilter intentFilter;
    private IncomingSmsReceiver smsReceiver;

    @Override
    public IBinder onBind(Intent paramIntent) {
        return null;
    }


    public void onCreate() {
        super.onCreate();
//        LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().removePreference("otpVerificationCode");
        EliteSession.eLog.d(MODULE, "SmsReceiver Services Invoked!");
        // bind sms receiver broadcast
        this.smsReceiver = new IncomingSmsReceiver();
        this.intentFilter = new IntentFilter();
        this.intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(this.smsReceiver, this.intentFilter);
    }

    public void onDestroy() {
        super.onDestroy();
        // release sms receiver broadcast
        unregisterReceiver(this.smsReceiver);
    }
}

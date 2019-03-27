package com.elitecorelib.core.captiveportal;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.elitecorelib.core.EliteSession;

/**
 * Created by Chirag Parmar on 13-Jan-17.
 */

public class CaptiveService extends Service {

    private final String MODULE = "CaptiveService";
    private ServiceBinder mServiceBinder = new ServiceBinder();
    public static CaptivePortal captivePortal;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        if(captivePortal == null)
            captivePortal = new CaptivePortal(mServiceBinder);

        EliteSession.eLog.d(MODULE,"On Service Bind..");
        return mServiceBinder;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        EliteSession.eLog.d(MODULE,"Moblie have Low memory Space..");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        EliteSession.eLog.d(MODULE,"On Service Start..");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        EliteSession.eLog.d(MODULE,"On Unbind Service.");
        return super.onUnbind(intent);
    }

    public class ServiceBinder extends Binder {
        public CaptiveService getService() {
            return CaptiveService.this;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EliteSession.eLog.d(MODULE,"On Destory Service.");
    }
}

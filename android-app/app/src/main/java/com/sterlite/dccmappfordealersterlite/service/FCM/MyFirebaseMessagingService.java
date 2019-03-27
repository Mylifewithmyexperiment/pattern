package com.sterlite.dccmappfordealersterlite.service.FCM;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.util.TypedValue;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.activity.Splash.SplashActivity;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;

import static com.sterlite.dccmappfordealersterlite.service.FCM.MyFirebaseInstanceIdService.registerDeviceTokenToServer;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.e("MyFirebaseMessagingService", "token refreshed" + FirebaseInstanceId.getInstance().getToken());
        DCCMDealerSterlite.getDataManager().setBoolean(AppPreferencesHelper.PREF_IS_FIREBASE_TOKEN_CHANGED, true);
        registerDeviceTokenToServer();
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d("MyFirebaseMessagingService", "Notification Message Body: " + remoteMessage.getData());

        JSONObject obj = new JSONObject(remoteMessage.getData());
        try {
            String message = obj.getString("message");
            sendNotification(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendNotification(final String message) {

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();
        Log.e("screen on.....", "" + isScreenOn);
        if (isScreenOn == false) {
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "MyLock");
            wl.acquire(10000);
            PowerManager.WakeLock wl_cpu = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyCpuLock");

            wl_cpu.acquire(10000);
        }
        Intent intent = new Intent(this, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        int ic;
        if(Constants.APP.equalsIgnoreCase(Constants.MTN)) {
            ic=R.mipmap.ic_launcher_mtn;
        }
        else if(Constants.APP.equalsIgnoreCase(Constants.TELKOMSEL)) {
            ic=R.mipmap.ic_launcher_new;
        }
        else if(Constants.APP.equalsIgnoreCase(Constants.VODAFONE)) {
            ic=R.mipmap.ic_vodafone;
        }
        else{
            ic=R.mipmap.icon;
        }
        TypedValue typedValue =  new TypedValue();
        getTheme().resolveAttribute(R.attr.colorMain,typedValue,true);

        String title = getString(R.string.app_name);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getPackageName())
                .setContentTitle(title)
                .setContentText(message)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true)
                .setColor(getResources().getColor(typedValue.resourceId))
                .setSmallIcon(ic)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[]{1000, 1000})
                .setContentIntent(pendingIntent);

        showNotification(builder, 1);


    }

    private void showNotification(NotificationCompat.Builder builder, int number) {

        String CHANNEL_ID = "channel_1";
        Resources resources = getResources();

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel mChannel;
        int importance = NotificationManager.IMPORTANCE_DEFAULT;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID, this.getString(R.string.app_name), importance);
            // Configure the notification channel.
            mChannel.setDescription("notification");
            mNotificationManager.createNotificationChannel(mChannel);
        }
        builder.setChannelId(CHANNEL_ID);
        mNotificationManager.notify(number, builder.build());
    }

}

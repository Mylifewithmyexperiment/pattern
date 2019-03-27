package com.elitecorelib.core.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.elitecore.eliteconnectlibrary.R;
import com.elitecorelib.andsf.utility.ANDSFConstant;
import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.utility.ElitelibUtility;
import com.elitecorelib.core.utility.SharedPreferencesTask;
import com.elitecorelib.wifi.receiver.NotificationReceiver;

/**
 * Created by Chirag Parmar on 13-Jul-16.
 */
public class NotificationClass {

    public static NotificationClass notificationClass;
    private final String MODULE = "NotificationClass";
    private Context mContext;
    private int totalNotification = 0;
    private int logoID;
    private int notificationLogo_Id = 0;
    private SharedPreferencesTask task = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();
    private Intent notificationIntent;
    private  PendingIntent pi;

    public NotificationClass(Context context) {
        this.mContext = context;
    }

    public static NotificationClass getIntance(Context context) {

        if (notificationClass == null) {
            notificationClass = new NotificationClass(context);
        }
        return notificationClass;
    }


    public void showNotification(String message) {
        /**
         * When Registration not do
         */
        EliteSession.eLog.d(MODULE, "Checking regsitration");

        if (task.getInt(CoreConstants.KEY_IS_REGISTRAION) == 1 || task.getBooleanFirstFalse(CoreConstants.DO_REGISTER)) {

            logoID = task.getInt(CoreConstants.KEY_LOGO);
            notificationLogo_Id = task.getInt(CoreConstants.KEY_NOTIFICATION_LOGO);

            if (notificationLogo_Id == 0 || Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                notificationLogo_Id = logoID;
            }
            EliteSession.eLog.i(MODULE, "small Icon is " + notificationLogo_Id);


            EliteSession.eLog.d(MODULE, "Message before decode " + message);
//        String decodedMessage = ElitelibUtility.getUTF8DecodedString(message);
            String decodedMessage = message;
            EliteSession.eLog.d(MODULE, "Message after decode " + decodedMessage);
            //large icon for notification,normally use App icon
            Bitmap largeIcon = BitmapFactory.decodeResource(mContext.getResources(), logoID);
            int smalIcon = logoID;

            /*create intent for show notification details when user clicks notification*/
            try {
                if (task.getBooleanFirstFalse(ANDSFConstant.IS_ANDSF_NOTIFICATION)) {
                    notificationIntent = new Intent(mContext, NotificationReceiver.class);
                } else {
                    notificationIntent = new Intent(mContext, Class.forName(task.getString(CoreConstants.NOTIFICATION_REDIRECT_CLASS)));
                }

                notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                notificationIntent.putExtra(CoreConstants.NOTIFICATIONMESSAGE, decodedMessage);
                NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

                if (decodedMessage == null)
                    return;

                totalNotification = ElitelibUtility.seprateMessage(decodedMessage).size();
                String notificationContent = totalNotification + " " + CoreConstants.ANDSF_NOTIFICATION_MSG;

                if (totalNotification == 1) {
                    notificationContent = decodedMessage;
                }
                notificationIntent.putExtra(CoreConstants.NOTIFICATIONCOUNT, totalNotification);
                for (String notificationMsg : ElitelibUtility.seprateMessage(decodedMessage)) {
                    inboxStyle.addLine(notificationMsg);
                }
                if (task.getBooleanFirstFalse(ANDSFConstant.IS_ANDSF_NOTIFICATION)) {
                    pi = PendingIntent.getBroadcast(mContext, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                } else {
                    pi = PendingIntent.getActivity(mContext, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                }
                /*get the system service that manage notification NotificationManager*/

                NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

                String CHANNEL_ID = String.valueOf(System.currentTimeMillis());// The id of the channel.
                CharSequence name = task.getString(CoreConstants.MENU_TITLE_PREF);// The user-visible name of the channel.
                int importance = NotificationManager.IMPORTANCE_HIGH;

                /*build the notification*/
                Notification.Builder builder = new Notification.Builder(mContext)
                        .setWhen(System.currentTimeMillis())
                        .setContentText(notificationContent)
                        .setContentTitle(task.getString(CoreConstants.MENU_TITLE_PREF))
                        .setAutoCancel(true)
                        .setTicker(task.getString(CoreConstants.MENU_TITLE_PREF))
//                        .setLargeIcon(largeIcon)
                        .setPriority(Notification.PRIORITY_MIN)
                        //.setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_VIBRATE| Notification.DEFAULT_SOUND)
                        .setContentIntent(pi);
                /*Create notification with builder*/

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                    notificationManager.createNotificationChannel(mChannel);
                    builder.setChannelId(CHANNEL_ID);
                }

                try {
                    builder.setSmallIcon(android.R.drawable.sym_def_app_icon);
                } catch (IllegalArgumentException e) {
                    EliteSession.eLog.i(MODULE, "Small icon invalide or null, Get small icon from library");
                    builder.setSmallIcon(android.R.drawable.sym_def_app_icon);
                }

//			builder.setStyle(inboxStyle);

                Notification notification = new Notification.BigTextStyle(builder).bigText(notificationContent).build();
                /*sending notification to system.Here we use unique id (when)for making different each notification
                 * if we use same id,then first notification replace by the last notification*/
                if (task.getString(CoreConstants.APPLICATION_NOTIFICATION).equals("") ||
                        task.getString(CoreConstants.APPLICATION_NOTIFICATION).equals(CoreConstants.ENABLE)) {

                    notificationManager.notify((int) 999, notification);
                    EliteSession.eLog.d(MODULE, "Application Notification set Enable.");
                } else {
                    EliteSession.eLog.d(MODULE, "Application Notification set Disable.");
                }

            } catch (ClassNotFoundException e) {
                EliteSession.eLog.e(MODULE, "Error :" + e.getMessage());
            } catch (Exception e) {
                EliteSession.eLog.e(MODULE, "Error :" + e.getMessage());
            }
        } else {
            EliteSession.eLog.d(MODULE, "Please Check Registration ");
        }
    }
}

package com.elitecorelib.core.fcm;

import com.elitecorelib.andsf.api.ANDSFClient;
import com.elitecorelib.andsf.pojo.ANDSFConfig;
import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Chirag Parmar on 12-Jul-16.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String MODULE = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        EliteSession.eLog.d(MODULE, "From: " + remoteMessage.getFrom());
        EliteSession.eLog.d(MODULE, "Notification Message Body: " + remoteMessage.getData().get("message"));

        sendNotification(remoteMessage.getData().get("message"));
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody) {

        if (messageBody != null) {

            if (messageBody.contains(CoreConstants.Silent_Notification)) {
                EliteSession.eLog.d(MODULE, "Silent notification generatation " + messageBody);

                try {
                    ANDSFClient client = ANDSFClient.getClient();
                    ANDSFConfig config = new ANDSFConfig();
                    config.setPolicyCall(false);
                    config.setContext(LibraryApplication.getLibraryApplication().getLibraryContext());
                    client.invokeANDSFClient(config);

                } catch (Exception e) {
                    EliteSession.eLog.e(MODULE, "Error in  - " + e.getMessage());
                }
            } else {
                LibraryApplication.getLibraryApplication().getNotificationClass().showNotification(messageBody);
            }
        }
    }
}

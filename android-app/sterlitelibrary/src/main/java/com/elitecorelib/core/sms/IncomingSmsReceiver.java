package com.elitecorelib.core.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.utility.SharedPreferencesTask;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.elitecorelib.core.CoreConstants.SMS_SENDER;
import static com.elitecorelib.core.CoreConstants.SMS_SENDER_NAME;
import static com.elitecorelib.core.utility.ElitelibUtility.getKeyPairvalue;

/**
 * Created by nehal.maheshwari on 9/4/2015.
 */
public class IncomingSmsReceiver extends BroadcastReceiver {

    private final SharedPreferencesTask spTask = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();

    @Override
    public void onReceive(Context context, Intent intent) {
        final String MODULE = "IncomingSmsReceiver";
        final Bundle bundle = intent.getExtras();
        SmsMessage[] messages = null;
        String strMessage = "";

        try {
            if (bundle != null) {
                LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().removePreference("otpVerificationCode");
                EliteSession.eLog.d(MODULE, "Calling listner");
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                messages = new SmsMessage[pdusObj.length];

                for (int i = 0; i < messages.length; i++) {
                    // read received message
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        String format = bundle.getString("format");
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i], format);
                    } else {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    }

                    String phoneNumber = messages[i].getDisplayOriginatingAddress();
                    String senderNum = phoneNumber;
                    EliteSession.eLog.d("Sender number", senderNum);

                    if(senderNum.equals(getKeyPairvalue(SMS_SENDER_NAME,SMS_SENDER))) {

                        EliteSession.eLog.i(MODULE, "SMS Sender Valid");

                        String message = messages[i].getDisplayMessageBody();

                        EliteSession.eLog.i(MODULE, "Message is - " + message);
                        int otpCount = Integer.valueOf(getKeyPairvalue(CoreConstants.OTPCOUNT, CoreConstants.OTPCOUNT_VALUE));

                        Pattern pattern = Pattern.compile("(\\d{"+otpCount+"})");
                        Matcher m = pattern.matcher(message);
                        String OTP = "";
                        while (m.find()) {
                            OTP = m.group();
                            EliteSession.eLog.i(MODULE, "OTP Group - " + m.group());
                        }
                        EliteSession.eLog.i(MODULE, "OTP  - " + OTP);

//                        String otpMessageFormat = getKeyPairvalue(CoreConstants.OTPMESSAGE, CoreConstants.OTPMESSAGE_VALUE);
//                        String msgFormatList[] = otpMessageFormat.split("@@");
//                        for (String format : msgFormatList) {
//                            EliteSession.eLog.d(MODULE, "OTP Message Formate - " + format);
//                            String startString = format.substring(0, format.indexOf("#") - 1);
//                            if (message != null && !message.equals("")) {
//                                if (message.startsWith(startString)) {
//                                    OTP = message.substring(format.indexOf("#"), format.lastIndexOf("#") + 1);
//                                    if (OTP.matches(REGEXSTR)) {
//                                        EliteSession.eLog.i(MODULE, "Valid Formate - " + format);
//                                        break;
//                                    } else {
//                                        OTP = "";
//                                    }
//                                } else {
//                                    EliteSession.eLog.d(MODULE, "Message startwith format not match");
//                                }
//                            }
//                        }

                        if (!OTP.isEmpty()) {
                            EliteSession.eLog.i(MODULE, "Sender Mobile No : " + senderNum + " ; Message : " + message + " OTP = " + OTP);
                            // send otp code to related activity
                            Intent sendCodetoActivty = new Intent();
                            sendCodetoActivty.setAction("otpAction");
                            sendCodetoActivty.putExtra("otpVerificationCode", message);
                            context.sendBroadcast(sendCodetoActivty);
                            LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().saveString("otpVerificationCode", OTP);
                        } else {
                            EliteSession.eLog.i(MODULE, "Not Valid SMS Formate");
                        }
                    } else {
                        EliteSession.eLog.i(MODULE, "SMS sender not valid");
                    }
                }
                //this.abortBroadcast();
            }
        } catch (Exception e) {
            EliteSession.eLog.e("MSG", "Exception smsReceiver" + e);
        }
    }
}

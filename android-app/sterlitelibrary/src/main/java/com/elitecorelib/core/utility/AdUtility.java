package com.elitecorelib.core.utility;

import android.app.Activity;
import android.webkit.WebView;

import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

/**
 * Created by Chirag Parmar on 6/7/2016.
 */
public class AdUtility {


    private static AdUtility adUtilityObj;
    private int seconds;
    private Activity context;
    private Timer timer;
    private WebView wv;
    private String htmlText, screenName;

    private AdUtility() {

    }

    public static AdUtility getAdUtilityInstance() {
        if (adUtilityObj == null) {
            adUtilityObj = new AdUtility();
        }

        return adUtilityObj;

    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public WebView getWv() {
        return wv;
    }

    public void setWv(WebView wv) {
        this.wv = wv;
    }

    public String getHtmlText() {
        return htmlText;
    }

    public void setHtmlText(String htmlText) {
        this.htmlText = htmlText;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public Activity getContext() {
        return context;
    }

    public void setContext(Activity context) {
        this.context = context;
    }

    public void reloadAdvertisement() {

        new ReloadWebView(this.context, this.screenName, this.seconds, this.wv, this.htmlText);
    }


}

class ReloadWebView extends TimerTask {
    private static String MODULE = "AdUtility";
    Activity context;
    Timer timer;
    WebView wv;
    String htmlText, screenName, screenPosition ="";
    private SharedPreferencesTask spTask = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();

    public ReloadWebView(Activity context, String screenName, int seconds, WebView wv, String htmlText) {
        this.context = context;
        this.wv = wv;
        this.htmlText = htmlText;
        this.screenName = screenName;

        String screenContain[] = screenName.split(Pattern.quote("|"));

        this.screenName = screenContain[0];

        if (screenContain.length > 1)
            screenPosition = screenContain[1];

        timer = new Timer();
            /* execute the first task after seconds */
        timer.schedule(this,
                0,  // initial delay
                seconds * 1000); // subsequent rate

			/* if you want to execute the first task immediatly */
            /*
            timer.schedule(this,
	                0,               // initial delay null
	                seconds * 1000); // subsequent rate
			 */
    }

    @Override
    public void run() {
//        if (context == null || context.isFinishing() || context.isChild() || context.isTaskRoot()) {
        // Activity killed
        if (CoreConstants.androidAdActiveHashMap.get(screenName) != null && !CoreConstants.androidAdActiveHashMap.get(screenName)) {
            this.cancel();
            EliteSession.eLog.d(MODULE, "ScreenName Finish -------->" + screenName);
            return;
        } else {
            EliteSession.eLog.d(MODULE, "ScreenName Not Finish -------->" + screenName);
        }

        /**
         * When Power button off that time Advertisement not load
         */

        if (!spTask.getBoolean(CoreConstants.POWER_BUTTON)) {
            this.cancel();
            EliteSession.eLog.d(MODULE, "Power Button OFF");
        }
//        }

        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                    EliteSession.eLog.d(MODULE, " HHHHHHHHHHHH Ad " + EliteConnect.getEasyConnectApplication().isActivity());
//                    if(EliteConnect.getEasyConnectApplication().isActivity() == false) {

                String htmlText = ElitelibUtility.getDecodedInvocationCode(screenName + screenPosition);
                wv.loadDataWithBaseURL(null, htmlText, "text/html", "utf-8", null);
                EliteSession.eLog.d(MODULE, "Ad refrsh time interval exceed and calling invocation code" + htmlText);
                EliteSession.eLog.d(MODULE, "ScreenName -------->" + screenName);

//                    }else{
//                        EliteSession.eLog.d(MODULE, " >>>>>>>>>>>>>>>>>>>> Ad not refrsh");
//                    }
            }
        });
    }
}
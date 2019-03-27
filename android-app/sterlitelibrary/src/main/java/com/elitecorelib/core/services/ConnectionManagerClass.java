package com.elitecorelib.core.services;

import android.os.AsyncTask;

import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.utility.SharedPreferencesConstant;

import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Chirag Parmar on 19/03/18.
 */

public class ConnectionManagerClass extends AsyncTask<String, Void, String> {

    private static final String MODULE = "ConnectionManagerClass";
    private final MediaType JSON = MediaType.parse("application/json;charset=UTF-8");
    private final MediaType TEXT = MediaType.parse("text/plain");
    private final ConnectionManagerListner callback;
    private final int requestId;
    private int responseCode = 0;
    private String responseMessage;

    public ConnectionManagerClass(ConnectionManagerListner callback, int requestId) {
        this.callback = callback;
        this.requestId = requestId;
    }

    @Override
    protected String doInBackground(String... userdata) {
        String serverResponse = "";
        try {
            String MAIN_URL = userdata[1];

            if (MAIN_URL == null)
                return null;

            EliteSession.eLog.i(MODULE, " Service Url : " + MAIN_URL);
            EliteSession.eLog.i(MODULE, " Request parameter : " + userdata[0]);

            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(20000, TimeUnit.MILLISECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .build();

            RequestBody requestBody;
            if (userdata.length > 2 && userdata[2] != null && userdata[2].trim().length() > 0) {
                requestBody = RequestBody.create(TEXT, userdata[0]);
            } else {
                requestBody = RequestBody.create(JSON, userdata[0]);
            }

            Request request = new Request.Builder()
                    .url(MAIN_URL)
                    .post(requestBody)
                    .build();

            Response response = httpClient.newCall(request).execute();
            EliteSession.eLog.d(MODULE, " Response code : " + response.code());
            EliteSession.eLog.d(MODULE, " Response Message : " + response.message());

            responseCode = response.code();
            responseMessage = response.message();


            // Header get from response
            EliteSession.eLog.d(MODULE, " Response Message Header: " + response.header(CoreConstants.Key_Header_Date));
            LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().saveString(SharedPreferencesConstant.HEADER_DATE, response.header(CoreConstants.Key_Header_Date));

            serverResponse = response.body().string();

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
        return serverResponse;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if (callback != null) {
            try {
                if (result == null || responseCode == 0) {

                    EliteSession.eLog.e(MODULE, "Response null");
                    callback.onConnectionFailure("", 100);

                } else {

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        callback.onConnectionSuccess(result, requestId);
                    } else {
                        callback.onConnectionFailure(responseMessage, responseCode);
                    }
                }
            } catch (Exception e) {
                EliteSession.eLog.e(MODULE, " Error during call back onTaskComplete");
                callback.onConnectionFailure(e.getMessage(), 100);
            }
        }
    }
}

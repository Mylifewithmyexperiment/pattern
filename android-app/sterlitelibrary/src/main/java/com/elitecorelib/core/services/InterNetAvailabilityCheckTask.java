package com.elitecorelib.core.services;

import android.os.AsyncTask;

import com.elitecore.wifi.listener.OnInternetCheckCompleteListner;
import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.utility.SharedPreferencesTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.elitecorelib.core.CoreConstants.KEY_HIDDENPARAMS;

public class InterNetAvailabilityCheckTask extends AsyncTask<String, Void, String> {

    private static final String MODULE = "InterNetAvailabilityCheckTask";
    private static final int MAX_RETRIES = 3;
    private int count;
    private String URL = "";
    private OnInternetCheckCompleteListner callback;
    private String htmlParamJson;
    private SharedPreferencesTask spTask = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();
    private int mRequestId = 0;

    public InterNetAvailabilityCheckTask(int requestId, OnInternetCheckCompleteListner callback, String URL) {
        this.callback = callback;
        this.URL = URL;
        this.mRequestId = requestId;
    }

    public InterNetAvailabilityCheckTask(OnInternetCheckCompleteListner callback, String URL) {
        this.callback = callback;
        this.URL = URL;
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);

        if (callback != null) {
            try {
                EliteSession.eLog.d(MODULE, "Giving call back");
                callback.isInterNetAvailable(mRequestId, result, htmlParamJson);
            } catch (Exception e) {
                callback.isInterNetAvailable(mRequestId, result, htmlParamJson);
                EliteSession.eLog.e(MODULE + " Error during call back onTaskComplete");
            }
        }
    }

    @Override
    protected String doInBackground(String... strings) {

        String result = "";
        try {
            EliteSession.eLog.d(MODULE, " Process invoked to check internet connection");
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(URL);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setInstanceFollowRedirects(true);
                urlConnection.setConnectTimeout(CoreConstants.INTERNET_CHECK_TIMEOUT);
                urlConnection.setReadTimeout(CoreConstants.INTERNET_CHECK_TIMEOUT);
                urlConnection.setUseCaches(false);
                urlConnection.getInputStream();
                // we got a valid response, but not from the real google
                // urlConnection.gethe
                if (urlConnection != null)
                    EliteSession.eLog.d(MODULE, "Response Internet connectivity code is :: " + urlConnection.getResponseCode());

                if (urlConnection.getResponseCode() == 204) {    //check connection

                    EliteSession.eLog.d(MODULE, "Response code 204");
                    result = CoreConstants.INTERNET_CHECK_SUCCESS;
                    /*String connection = urlConnection.getHeaderField(CoreConstants.INTERNET_CHECK_HEADER);
                    if (connection != null) {
                        EliteSession.eLog.d(MODULE, "Header Not null, Checking Connection");
                        if (connection.compareToIgnoreCase(CoreConstants.INTERNET_CHECK_HEADER_CLOSE) == 0) {
                            EliteSession.eLog.d(MODULE, "204 but Connection is close");
                            result = CoreConstants.INTERNET_CHECK_FAIL;
                        }
                    }*/
                } else if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    EliteSession.eLog.d(MODULE, "Internet connection not available with 200");
                    result = CoreConstants.INTERNET_CHECK_FAIL;


                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    EliteSession.eLog.d(MODULE, "else case Internet connection not available >>> " + sb.toString());
                    String htmlContentInStringFormat = sb.toString();
                    if (htmlContentInStringFormat.equals("")) {
                        EliteSession.eLog.d(MODULE, "else case There is no HTML file to parse");
                    } else {
                        try {
                            Document htmlDocument = Jsoup.parse(htmlContentInStringFormat);
                            EliteSession.eLog.d(MODULE, "else case HTML Content available");
                            Map<String, String> hiddenList = new HashMap<String, String>();
                            Elements hidden = htmlDocument.select("input[type=hidden]");
                            for (Element el1 : hidden) {
                                if (!el1.attr("value").equals("")) {
                                    hiddenList.put(el1.attr("name"), el1.attr("value"));
                                }
                                EliteSession.eLog.d(MODULE, "Logs name - " + el1.attr("name") + " value  -" + el1.attr("value"));
                            }

                            htmlParamJson = hiddenList.toString();

                            EliteSession.eLog.d(MODULE, "Save Hidden Parmas");
                            spTask.saveString(KEY_HIDDENPARAMS, htmlParamJson);

                        } catch (Exception e) {
                            result = CoreConstants.INTERNET_CHECK_FAIL;
                            EliteSession.eLog.e(MODULE, e.getMessage());
                            return result;
                        }
                    }
                } else {
                    String location = urlConnection.getHeaderField("Location");
                    if (location != null) {
                        EliteSession.eLog.d(MODULE, location);
                        HttpURLConnection urlConnectionRecall = null;
                        try {
                            URL urlRecall = new URL(location);
                            urlConnectionRecall = (HttpURLConnection) urlRecall.openConnection();
                            urlConnectionRecall.setInstanceFollowRedirects(true);
                            urlConnectionRecall.setConnectTimeout(CoreConstants.INTERNET_CHECK_TIMEOUT);
                            urlConnectionRecall.setReadTimeout(CoreConstants.INTERNET_CHECK_TIMEOUT);
                            urlConnectionRecall.setUseCaches(false);
                            urlConnectionRecall.getInputStream();
                            // we got a valid response, but not from the real google
                            // urlConnection.gethe
                            if (urlConnectionRecall != null)
                                EliteSession.eLog.d(MODULE, "Recall Response Internet connectivity code is :: " + urlConnectionRecall.getResponseCode());

                            if (urlConnectionRecall.getResponseCode() == 200) {
                                EliteSession.eLog.d(MODULE, "Recall Else part Internet connection not available");
                                result = CoreConstants.INTERNET_CHECK_FAIL;

                                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnectionRecall.getInputStream()));
                                StringBuilder sb = new StringBuilder();
                                String line;
                                while ((line = br.readLine()) != null) {
                                    sb.append(line + "\n");
                                }
                                br.close();
                                EliteSession.eLog.d(MODULE, "Recall else case Internet connection not available >>> " + sb.toString());
                                String htmlContentInStringFormat = sb.toString();
                                if (htmlContentInStringFormat.equals("")) {
                                    EliteSession.eLog.d(MODULE, "Recall else case There is no HTML file to parse");
                                } else {
                                    try {
                                        Document htmlDocument = Jsoup.parse(htmlContentInStringFormat);
                                        EliteSession.eLog.d(MODULE, "Recall else case HTML Content available");
                                        Map<String, String> hiddenList = new HashMap<String, String>();
                                        Elements hidden = htmlDocument.select("input[type=hidden]");
                                        for (Element el1 : hidden) {
                                            hiddenList.put(el1.attr("name"), el1.attr("value"));
                                            EliteSession.eLog.d(MODULE, "Recall Logs name - " + el1.attr("name") + " value  -" + el1.attr("value"));
                                        }

                                        htmlParamJson = hiddenList.toString();

                                        EliteSession.eLog.d(MODULE, "Recall Save Hidden Parmas");
                                        spTask.saveString(KEY_HIDDENPARAMS, htmlParamJson);

                                    } catch (Exception e) {
                                        result = CoreConstants.INTERNET_CHECK_FAIL;
                                        EliteSession.eLog.e(MODULE, e.getMessage());
                                        return result;
                                    }
                                }
                            }
                        } catch (IOException e) {
                            result = CoreConstants.INTERNET_CHECK_FAIL;
                            EliteSession.eLog.e(MODULE, e.getMessage());
                            return result;
                        } finally {
                            if (urlConnectionRecall != null) {
                                urlConnectionRecall.disconnect();
                            }
                        }
                    } else {
                        EliteSession.eLog.d(MODULE, "Location not found from header");
                    }
                }
                return result;
            } catch (EOFException eof) {

                EliteSession.eLog.d(MODULE,"EOFException Count is  - "+count);

                if(count < MAX_RETRIES) {
                    EliteSession.eLog.d(MODULE,"Call from EOF Exception");
                    doInBackground();
                    count++;
                } else {
                    EliteSession.eLog.e(MODULE,"EoF Exception mac count done");
                    result = CoreConstants.INTERNET_CHECK_FAIL;
                    EliteSession.eLog.e(MODULE, eof.getMessage());
                    return result;
                }

            } catch (IOException e) {
                result = CoreConstants.INTERNET_CHECK_FAIL;
                EliteSession.eLog.e(MODULE, e.getMessage());
                return result;
            }finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

        } catch (Exception e) {
            result = CoreConstants.INTERNET_CHECK_FAIL;
            EliteSession.eLog.e(MODULE, e.getMessage());

        }
        EliteSession.eLog.d(MODULE, " Final Internet status:" + result);
        return result;
    }
}

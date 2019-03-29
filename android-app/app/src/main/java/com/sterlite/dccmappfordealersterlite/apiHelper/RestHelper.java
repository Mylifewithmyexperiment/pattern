package com.sterlite.dccmappfordealersterlite.apiHelper;

import android.util.Log;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.data.network.AppApiHelper;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;


/**
 * Created by etech10 on 10/8/17.
 */

public class RestHelper implements RestClientListener {

    public static final int REQUEST_TYPE_POST = 1;
    public static final int REQUEST_TYPE_GET = 0;

    public static final int CONTENT_TYPE_FROMDATA = 0;
    public static final int CONTENT_TYPE_JSON = 1;
    public static final int CONTENT_TYPE_MULTIPART = 2;

    static final String INTERNAL_SERVER_ERR = "Internal Server Error. Please try again later.";
    static final String NO_INTERNET_ERR = "No internet connection";

    RestClient restClient = new RestClient();

    public interface RestHelperCallback {
        public void onRequestCallback(int code, String message, RestResponse restResponse);
    }

    private String action;
    private String identifier;
    private HashMap<String, String> qryparams;
    private HashMap<String, Object> params;
    private HashMap<String, String> headers;
    private RestHelperCallback callback;
    private int contentType;
    private int reqType;

    public RestHelper() {

    }


    public void sendRequest(int flag, String identifier, String action, HashMap<String, String> qryparams, HashMap<String, Object> params, HashMap<String, String> headers, RestHelper.RestHelperCallback callback) {
        this.sendRequest(flag, identifier, action, qryparams, params, headers, callback, RestHelper.CONTENT_TYPE_FROMDATA, RestHelper.REQUEST_TYPE_POST, null);
    }

    public void sendRequest(int flag, String identifier, String action, HashMap<String, Object> params, HashMap<String, String> headers, RestHelper.RestHelperCallback callback) {
        this.sendRequest(flag, identifier, action, null, params, headers, callback, RestHelper.CONTENT_TYPE_FROMDATA, RestHelper.REQUEST_TYPE_POST, null);
    }


    public void sendRequest(int flag, String identifier, String action, HashMap<String, Object> params, RestHelper.RestHelperCallback callback) {
        this.sendRequest(flag, identifier, action, null, params, null, callback, RestHelper.CONTENT_TYPE_FROMDATA, RestHelper.REQUEST_TYPE_POST, null);
    }

    //1
    public void sendRequest(int flag, String identifier, String action, RestHelper.RestHelperCallback callback) {
        this.sendRequest(flag, identifier, action, null, null, null, callback, RestHelper.CONTENT_TYPE_FROMDATA, RestHelper.REQUEST_TYPE_GET, null);
    }


    public void sendRequest(int flag, String identifier, String action, RestHelper.RestHelperCallback callback, JSONObject json) {
        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        this.sendRequest(flag, identifier, action, null, null, header, callback, RestHelper.CONTENT_TYPE_JSON, RestHelper.REQUEST_TYPE_POST, json);
    }

    public void sendRequest(int flag, String identifier, String action, RestHelper.RestHelperCallback callback, JSONArray json) {
        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        //   this.sendRequest(flag,identifier, action, null, null, header, callback, RestHelper.CONTENT_TYPE_JSON, RestHelper.REQUEST_TYPE_POST, json);
        if (flag == 1) {
            String security= DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.BSS_ADAPTER_SECURITY, Constants.BSS_SECURITY);
            String ip=DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.BSS_ADAPTER_IP, Constants.BSS_Adapter_IP);;
            String port=DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.BSS_ADAPTER_PORT, Constants.BSS_Adapter_PORT);;
            String baseurl = security+ip+":"+port+"/";

            //String baseurl = AppApiHelper.BSS_ADAPTER_BASE_URL;
            sendRequest(baseurl, REQUEST_TYPE_POST, identifier, action, contentType, qryparams, params, headers, callback, null, null, json);
        } else if (flag == 2) {
            String security= DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.DCCM_SECURITY, Constants.DCCM_SECURITY);
            String ip=DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.DCCM_IP, Constants.DCCM_IP);;
            String port=DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.DCCM_PORT, Constants.DCCM_PORT);;
            String baseurl = security+ip+":"+port+"/";
           // String baseurl = AppApiHelper.BASE_HTTPS_URL;
            sendRequest(baseurl, reqType, identifier, action, contentType, qryparams, params, headers, callback, null, null, json);

        } else if (flag == 3) {
            String baseurl = AppApiHelper.BASE_TOKEN_URL;
            sendRequest(baseurl, reqType, identifier, action, contentType, qryparams, params, headers, callback, null, null, json);

        }  else if (flag == 4) {
            String baseurl = AppApiHelper.CRM_BASE_URL;
            sendRequest(baseurl, reqType, identifier, action, contentType, qryparams, params, headers, callback, null, null, json);

        }else {

            String security= DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.DCCM_SECURITY, Constants.DCCM_SECURITY);
            String ip=DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.DCCM_IP, Constants.DCCM_IP);;
            String port=DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.DCCM_PORT, Constants.DCCM_PORT);;
            String baseurl = security+ip+":"+port+"/";
           // String baseurl = AppApiHelper.BASE_HTTPS_URL;
            sendRequest(baseurl, reqType, identifier, action, contentType, qryparams, params, headers, callback, null, null, json);

        }
    }

    //2
    public void sendRequest(int flag, final String identifier, String action, HashMap<String, String> qryparams, HashMap<String, Object> params, HashMap<String, String> headers, final RestHelper.RestHelperCallback callback, int contentType, int reqType, JSONObject json) {
        this.sendRequest(flag, identifier, action, qryparams, params, headers, callback, contentType, reqType, json, null, null);
    }

    //3
    public void sendRequest(int flag, final String identifier, String action, HashMap<String, String> qryparams, HashMap<String, Object> params, HashMap<String, String> headers, final RestHelper.RestHelperCallback callback, int contentType, int reqType, JSONObject json, String imageKey, String imagePath) {
        sendRequest(flag, reqType, identifier, action, contentType, qryparams, params, headers, callback, imageKey, imagePath, json);
    }


    public void sendRequest(int flag, int reqType, String identifier, String action, int contentType, HashMap<String, String> qryparams, HashMap<String, Object> params, HashMap<String, String> headers, RestHelperCallback callback, JSONObject json) {
        sendRequest(flag, reqType, identifier, action, contentType, qryparams, params, headers, callback, null, null, json);
    }

    //4
    public void sendRequest(int flag, int reqType, String identifier, String action, int contentType, HashMap<String, String> qryparams, HashMap<String, Object> params, HashMap<String, String> headers, RestHelperCallback callback, String imageKey, String imagePath, JSONObject json) {


        if (flag == 1) {
            String security= DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.BSS_ADAPTER_SECURITY, Constants.BSS_SECURITY);
            String ip=DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.BSS_ADAPTER_IP, Constants.BSS_Adapter_IP);
            String port=DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.BSS_ADAPTER_PORT, Constants.BSS_Adapter_PORT);
            String baseurl = security+ip+":"+port+"/";
           // String baseurl = AppApiHelper.BSS_ADAPTER_BASE_URL;
            Log.e("BSS URL",baseurl+" ");

            sendRequest(baseurl, reqType, identifier, action, contentType, qryparams, params, headers, callback, imageKey, imagePath, json);
        } else if (flag == 2) {
            String security= DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.DCCM_SECURITY, Constants.DCCM_SECURITY);
            String ip=DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.DCCM_IP, Constants.DCCM_IP);
            String port=DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.DCCM_PORT, Constants.DCCM_PORT);
            String baseurl = security+ip+":"+port+"/";
                Log.e("DCCM URL",baseurl+" ");
           // String baseurl = AppApiHelper.BASE_HTTPS_URL;
            sendRequest(baseurl, reqType, identifier, action, contentType, qryparams, params, headers, callback, imageKey, imagePath, json);

        } else if (flag == 3) {
            String baseurl = AppApiHelper.BASE_TOKEN_URL;
            sendRequest(baseurl, reqType, identifier, action, contentType, qryparams, params, headers, callback, imageKey, imagePath, json);

        }  else if (flag == 4) {
            String security= DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.CRM_SECURITY, Constants.CRM_SECURITY);
            String ip=DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.CRM_IP, Constants.CRM_IP);
            String port=DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.CRM_PORT, Constants.CRM_PORT);
            String context=DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.CRM_CONTEXT, Constants.CRM_CONTEXT);

            String baseurl = security+ip+":"+port+"/";
            Log.e("CRM URL",baseurl+" ");
            sendRequest(baseurl, reqType, context+identifier, context+action, contentType, qryparams, params, headers, callback, imageKey, imagePath, json);

        }else {
            String security= DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.DCCM_SECURITY, Constants.DCCM_SECURITY);
            String ip=DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.DCCM_IP, Constants.DCCM_IP);
            String port=DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.DCCM_PORT, Constants.DCCM_PORT);
            String baseurl = security+ip+":"+port+"/";
           // String baseurl = AppApiHelper.BASE_HTTPS_URL;
            sendRequest(baseurl, reqType, identifier, action, contentType, qryparams, params, headers, callback, imageKey, imagePath, json);

        }
    }

    public void sendRequest(String baseurl, int reqType, String identifier, String action, int contentType, HashMap<String, String> qryparams, HashMap<String, Object> params, HashMap<String, String> headers, RestHelperCallback callback, String imageKey, String imagePath, JSONObject json) {

        this.action = action;
        this.params = params;
        this.headers = headers;
        this.callback = callback;
        this.contentType = contentType;
        this.identifier = identifier;
        this.reqType = reqType;


        String queryString = "";
        if (qryparams != null && qryparams.size() > 0) {
            for (Map.Entry<String, String> entry : qryparams.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                queryString = queryString + key + "=" + value + "&";
            }

            //queryString = queryString.substring(0, qryparams.toString().lastIndexOf("&"));
        }
        String url;
        if (queryString != null && queryString.length() > 0)
            url = action + "?" + queryString;
        else
            url = action;

        RestRequest restRequest = new RestRequest();
        restRequest.setIdentifier(identifier);

        if (reqType == RestHelper.REQUEST_TYPE_POST) {
            restRequest.setReqMethod(RestConst.RequestMethod.METHOD_POST);
        } else {
            restRequest.setReqMethod(RestConst.RequestMethod.METHOD_GET);
        }
        restRequest.setBaseUrl(baseurl);
        restRequest.setAction(url);
        restRequest.setJsonObject(json);
        Log.d("API NAME: ", baseurl + url);
        if (params != null && params.size() > 0) {
            setDefaultParams();
            for (Map.Entry<String, Object> entry : params.entrySet()) {

                Log.d("PARAMS: key=" + entry.getKey(), ", value=" + entry.getValue());
                restRequest.addParam(entry.getKey(), entry.getValue());

            }

        }

        if (headers != null && headers.size() > 0) {

            for (Map.Entry<String, String> entry : headers.entrySet()) {

                Log.d("HEADERS: key=" + entry.getKey(), ", value=" + entry.getValue());
                restRequest.addheader(entry.getKey(), entry.getValue());

            }

        }
        if (contentType == RestHelper.CONTENT_TYPE_MULTIPART) {
            restRequest.setContentType(RestConst.ContentType.CONTENT_MULTIPART);
            if (imagePath != null) {
                restRequest.setImageKey(imageKey);
                restRequest.setImagepath(imagePath);
            } else if (params != null && params.containsKey("media")) {
                restRequest.setImageKey("media");
                ArrayList<String> uriArrayList = (ArrayList<String>) params.get("media");
                restRequest.setMeadiapatharray(uriArrayList);
                params.remove("media");
            }
        } else if (contentType == RestHelper.CONTENT_TYPE_JSON) {
            restRequest.setContentType(RestConst.ContentType.CONTENT_JSON);
        } else {
            restRequest.setContentType(RestConst.ContentType.CONTENT_FORMDATA);
        }

        try {
            if (AppUtils.isConnectingToInternet()) {
                Log.d("executeRestRequest", ":--" + "Network  Available");
                Log.d("exr Action:", ":-" + restRequest.getAction());
                Log.d("REST REQ:", ":-" + restRequest.toString());

                restClient.execute(restRequest, this);
            } else {
                Log.d("executeRestRequest", ":--" + "Network not Available");
                RestResponse res = new RestResponse();
                res.setError("No internet connection");
                callback.onRequestCallback(Constants.FAIL_INTERNET_CODE, "No internet connection", res);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void sendRequest(String baseurl, int reqType, String identifier, String action, int contentType, HashMap<String, String> qryparams, HashMap<String, Object> params, HashMap<String, String> headers, RestHelperCallback callback, String imageKey, String imagePath, JSONArray json) {

        contentType = RestHelper.CONTENT_TYPE_JSON;
        headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        this.action = action;
        this.params = params;
        this.headers = headers;
        this.callback = callback;
        this.contentType = contentType;
        this.identifier = identifier;
        this.reqType = reqType;


        //this.contentType=RestHelper.CONTENT_TYPE_JSON;
        String queryString = "";
        if (qryparams != null && qryparams.size() > 0) {
            for (Map.Entry<String, String> entry : qryparams.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                queryString = queryString + key + "=" + value + "&";
            }

            //queryString = queryString.substring(0, qryparams.toString().lastIndexOf("&"));
        }
        String url;
        if (queryString != null && queryString.length() > 0)
            url = action + "?" + queryString;
        else
            url = action;

        RestRequest restRequest = new RestRequest();
        restRequest.setIdentifier(identifier);

        if (reqType == RestHelper.REQUEST_TYPE_POST) {
            restRequest.setReqMethod(RestConst.RequestMethod.METHOD_POST);
        } else {
            restRequest.setReqMethod(RestConst.RequestMethod.METHOD_GET);
        }
        restRequest.setBaseUrl(baseurl);
        restRequest.setAction(url);
        restRequest.setJsonArray(json);
        Log.d("API NAME: ", baseurl + url);
        if (params != null && params.size() > 0) {
            setDefaultParams();
            for (Map.Entry<String, Object> entry : params.entrySet()) {

                Log.d("PARAMS: key=" + entry.getKey(), ", value=" + entry.getValue());
                restRequest.addParam(entry.getKey(), entry.getValue());

            }

        }

        if (headers != null && headers.size() > 0) {

            for (Map.Entry<String, String> entry : headers.entrySet()) {

                Log.d("HEADERS: key=" + entry.getKey(), ", value=" + entry.getValue());
                restRequest.addheader(entry.getKey(), entry.getValue());

            }

        }
        if (contentType == RestHelper.CONTENT_TYPE_MULTIPART) {
            restRequest.setContentType(RestConst.ContentType.CONTENT_MULTIPART);
            if (imagePath != null) {
                restRequest.setImageKey(imageKey);
                restRequest.setImagepath(imagePath);
            } else if (params != null && params.containsKey("media")) {
                restRequest.setImageKey("media");
                ArrayList<String> uriArrayList = (ArrayList<String>) params.get("media");
                restRequest.setMeadiapatharray(uriArrayList);
                params.remove("media");
            }
        } else if (contentType == RestHelper.CONTENT_TYPE_JSON) {
            restRequest.setContentType(RestConst.ContentType.CONTENT_JSON);
        } else {
            restRequest.setContentType(RestConst.ContentType.CONTENT_FORMDATA);
        }

        try {
            if (AppUtils.isConnectingToInternet()) {
                Log.d("executeRestRequest", ":--" + "Network  Available");
                Log.d("exr Action:", ":-" + restRequest.getAction());
                Log.d("REST REQ:", ":-" + restRequest.toString());

                restClient.execute(restRequest, this);
            } else {
                Log.d("executeRestRequest", ":--" + "Network not Available");
                RestResponse res = new RestResponse();
                res.setError("No internet connection");
                callback.onRequestCallback(Constants.FAIL_INTERNET_CODE, "No internet connection", res);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDefaultParams() {
//        params.put(Constants.LANGUAGE, AppUtils.getDefaultLanguage());
    }

    public void cancelRequest() {
        restClient.cancelRequest();
    }

    @Override
    public void onRequestComplete(RestConst.ResponseCode resCode, RestResponse restRes) {

        try {
            if (restRes != null) {

                Log.d("restRes", ":--" + resCode);
                Log.d("restRes String", ":--" + restRes.getResString());

                if (resCode.equals(RestConst.ResponseCode.SUCCESS)) {

                    HashMap<String, Object> resObjMap = restRes.getResObjectMap();
                    if (restRes.getResString() != null && resObjMap != null) {
//                        if (resObjMap.containsKey(Constants.RES_CODE_KEY) && resObjMap.get(Constants.RES_CODE_KEY).toString().equalsIgnoreCase(String.valueOf(Constants.SUCCESS_CODE))) {
//                            callback.onRequestCallback(Constants.SUCCESS_CODE, resObjMap.get(Constants.RES_MSG_KEY).toString(), restRes);
//                        } else {
//                            callback.onRequestCallback(Constants.FAIL_CODE, resObjMap.get(Constants.RES_MSG_KEY).toString(), restRes);
//                        }
                        callback.onRequestCallback(Constants.SUCCESS_CODE, "Success", restRes);
                    } else {
                        callback.onRequestCallback(Constants.FAIL_CODE, INTERNAL_SERVER_ERR, restRes);
                    }
                } else {
                    Log.d("RestResponse", ":--" + resCode);
                    RestResponse res = new RestResponse();
                    res.getError();
                    if (resCode.equals(RestConst.ResponseCode.CANCEL)) {
                        callback.onRequestCallback(Constants.CANCEL_CODE, INTERNAL_SERVER_ERR, res);
                    } else if (resCode.equals(RestConst.ResponseCode.ERROR) && !AppUtils.isConnectingToInternet()) {
                        callback.onRequestCallback(Constants.FAIL_INTERNET_CODE, NO_INTERNET_ERR, res);
                    } else {
                        callback.onRequestCallback(Constants.FAIL_CODE, INTERNAL_SERVER_ERR, res);
                    }
                }
            } else {

                Log.d("RestResponse", ":--" + resCode);
                RestResponse res = new RestResponse();
                res.getError();
                if (resCode.equals(RestConst.ResponseCode.CANCEL)) {
                    callback.onRequestCallback(Constants.CANCEL_CODE, INTERNAL_SERVER_ERR, res);
                } else if (resCode.equals(RestConst.ResponseCode.ERROR) && !AppUtils.isConnectingToInternet()) {
                    callback.onRequestCallback(Constants.FAIL_INTERNET_CODE, NO_INTERNET_ERR, res);
                } else {
                    callback.onRequestCallback(Constants.FAIL_CODE, INTERNAL_SERVER_ERR, res);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

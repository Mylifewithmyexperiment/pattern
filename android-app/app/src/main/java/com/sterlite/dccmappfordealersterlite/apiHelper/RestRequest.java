package com.sterlite.dccmappfordealersterlite.apiHelper;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by etech7 on 19/6/17.
 */

public class RestRequest {

    private final String TAG = "RestRequest";

    private RestConst.RequestMethod reqMethod;

    //Set Content-Type Like Formdata,,json and Multipart
    private RestConst.ContentType contentType;

    private Context context;

    //Action Use For Endpoint Of Url
    private String action = null;
    private String identifier = null;

    private Map<String, Object> params = null;

    private Map<String, String> multipartData = null;

    //Path  use For Media Path when Upload Image
    private String baseUrl = null;

    private ArrayList<String> meadiapatharray;

    private String imageKey = null;

    private JSONObject jsonObject = null;

    private JSONArray jsonArray = null;

    private Map<String, String> header = null;

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    private  String imagepath = null;

    public RestRequest() {
        //params = new HashMap<>();
        multipartData = new HashMap<>();
        //header = new HashMap<>();

    }


    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    public void setReqMethod(RestConst.RequestMethod reqMethod) {
        this.reqMethod = reqMethod;
    }

    public RestConst.RequestMethod getReqMethod() {
        return reqMethod;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setContentType(RestConst.ContentType contentType) {
        this.contentType = contentType;
    }

    public RestConst.ContentType getContentType() {
        return contentType;
    }


    public String getImageKey() {
        return imageKey;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public Map<String, String> getMultipartData() {
        return multipartData;
    }


    public ArrayList<String> getMeadiapatharray() {
        return meadiapatharray;
    }

    public void setMeadiapatharray(ArrayList<String> meadiapatharray) {
        this.meadiapatharray = meadiapatharray;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    //addParam Method Use for Set Parameter
    public void addParam(String key, Object value) {
        if (key == null) {
            Log.e(TAG, "Key can not be null");
        } else if (value == null) {
            Log.e(TAG, "Value can not be null");
        } else {

            if (params == null) {
                params = new HashMap<>();
            }

            params.put(key, value);
        }
    }

    public void addFile(String key, String value) {
        if (key == null) {
            Log.e(TAG, "Key can not be null");
        } else if (value == null) {
            Log.e(TAG, "Value can not be null");
        } else {
            multipartData.put(key, value);
        }
    }


    public void addheader(String key, String value) {
        if (key == null) {
            Log.e(TAG, "Key can not be null");
        } else if (value == null) {
            Log.e(TAG, "Value can not be null");
        } else {
            if (header == null) {
                header = new HashMap<>();
            }
            header.put(key, value);
        }
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return "RestRequest{" +
                "TAG='" + TAG + '\'' +
                ", reqMethod=" + reqMethod +
                ", contentType=" + contentType +
                ", context=" + context +
                ", action='" + action + '\'' +
                ", identifier='" + identifier + '\'' +
                ", params=" + params +
                ", multipartData=" + multipartData +
                ", baseUrl='" + baseUrl + '\'' +
                ", meadiapatharray=" + meadiapatharray +
                ", imageKey='" + imageKey + '\'' +
                ", jsonObject=" + jsonObject +
                ", jsonArray=" + jsonArray +
                ", header=" + header +
                ", imagepath='" + imagepath + '\'' +
                '}';
    }
}

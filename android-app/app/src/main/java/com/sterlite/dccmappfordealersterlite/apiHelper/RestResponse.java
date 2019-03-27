package com.sterlite.dccmappfordealersterlite.apiHelper;

import android.graphics.Bitmap;

import java.util.HashMap;

/**
 * Created by etech7 on 20/6/17.
 */

public class RestResponse {

    private String resString = null;
    private String error = null;
    private HashMap<String, Object> resObjectMap = null;
    private RestRequest request;
    private  RestConst.ResponseType resType;
    private Bitmap bitmap;
    private byte[] byteArray;

    public byte[] getByteArray() {
        return byteArray;
    }

    public void setByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setResType(RestConst.ResponseType resType) {
        this.resType = resType;
    }

    public RestConst.ResponseType getResType() {
        return resType;
    }

    public String getResString() {
        return resString;
    }

    public void setResString(String resString) {
        this.resString = resString;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public HashMap<String, Object> getResObjectMap() {
        return resObjectMap;
    }

    public void setResObjectMap(HashMap<String, Object> resObjectMap) {
        this.resObjectMap = resObjectMap;
    }


    public void setRequest(RestRequest request) {
        this.request = request;
    }

    public RestRequest getRequest() {
        return request;
    }
}

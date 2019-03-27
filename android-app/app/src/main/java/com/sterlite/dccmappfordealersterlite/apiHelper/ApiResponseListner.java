package com.sterlite.dccmappfordealersterlite.apiHelper;

/**
 * Created by etech10 on 10/8/17.
 */

public interface ApiResponseListner {
    public void onResponseComplete(RestConst.ResponseCode responseCode, RestResponse restResponse);
}

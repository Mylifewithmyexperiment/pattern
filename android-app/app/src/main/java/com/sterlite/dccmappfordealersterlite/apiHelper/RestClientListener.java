package com.sterlite.dccmappfordealersterlite.apiHelper;

/**
 * Created by etech7 on 20/6/17.
 */

public interface RestClientListener {
    public void onRequestComplete(RestConst.ResponseCode responseCode, RestResponse restResponse);
}

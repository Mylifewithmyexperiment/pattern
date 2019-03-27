package com.elitecorelib.core.services;

/**
 * Created by Chirag Parmar on 19/03/18.
 */

public interface ConnectionManagerListner {

    void onConnectionSuccess(String result, int requestID);

    void onConnectionFailure(String message, int resultCode);

}

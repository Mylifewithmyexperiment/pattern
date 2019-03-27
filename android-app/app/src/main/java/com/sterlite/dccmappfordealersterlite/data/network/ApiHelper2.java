package com.sterlite.dccmappfordealersterlite.data.network;

public interface ApiHelper2 {

    interface OnApiCallback<V> {
        void onSuccess(V baseResponse);

        void onFaild(V baseResponse);
    }
//    public void doSignUp(final String name, final String email, final String mobile,final String city ,final OnApiCallback<User> onApiCallback);

}

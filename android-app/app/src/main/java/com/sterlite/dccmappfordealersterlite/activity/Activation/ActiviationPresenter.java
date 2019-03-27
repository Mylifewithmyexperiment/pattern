package com.sterlite.dccmappfordealersterlite.activity.Activation;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.apiHelper.RestHelper;
import com.sterlite.dccmappfordealersterlite.apiHelper.RestResponse;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;
import com.sterlite.dccmappfordealersterlite.data.network.ApiHelper;
import com.sterlite.dccmappfordealersterlite.data.network.ApiHelper2;
import com.sterlite.dccmappfordealersterlite.data.network.AppApiHelper;
import com.sterlite.dccmappfordealersterlite.data.network.AppApiHelper2;
import com.sterlite.dccmappfordealersterlite.data.network.model.Pagging;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.model.Activation.ActivationResponseModel;
import com.sterlite.dccmappfordealersterlite.model.MyOrdersListing.MyOrdersListResponseModel;
import com.sterlite.dccmappfordealersterlite.model.MyOrdersListing.Order;
import com.sterlite.dccmappfordealersterlite.model.dto.auth.AuthResponseDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.dccmdto.order.DeliveryOrderEntryGroupWsDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.dccmdto.order.OrderEntryWsDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.dccmdto.order.OrderWsDTO;

/**
 * Created by etech3 on 27/6/18.
 */

public class ActiviationPresenter<V extends ActivationContract.View> extends BasePresenter<V> implements ActivationContract.Presenter<V> {

    Pagging<Order> pagging;

    //dummy mode variables
//    boolean isFirstPageLoaded = false;
//    boolean isSecondPageLoaded = false;

    private String orderNo = null;

    @Override
    public void onAttach(V baseView) {
        super.onAttach(baseView);
        pagging = new Pagging<>();
    }

    @Override
    public void init() {
        getView().setUpView(false);
        loadMoreRecords();
    }


    @Override
    public void reset() {
        getView().setUpView(true);
        clearPagging();
        loadMoreRecords();

    }

    private void clearPagging() {
//        isFirstPageLoaded = false;
        pagging = new Pagging<>();
    }

    @Override
    public void loadMoreRecords() {


        if (pagging.isHasMore()) {
            pagging.setHasMore(false);
            startApiCall();

            DCCMDealerSterlite.getDataManager().getMyOrders(new ApiHelper.OnApiCallback<MyOrdersListResponseModel>() {
                @Override
                public void onSuccess(MyOrdersListResponseModel baseResponse) {
                    if (getView() == null) return;
                    if (baseResponse != null) {
                        getView().setNotRecordsFoundView(false);
                        ActiviationPresenter.super.onSuccess();
                        if (baseResponse.getOrders() != null && baseResponse.getOrders().size() > 0) {
                            getView().loadDataToView((ArrayList<Order>) baseResponse.getOrders());
                        } else {
                            getView().setNotRecordsFoundView(true);
                            return;
                        }
                    }
                }

                @Override
                public void onFailed(int code, String message) {
                    if (getView() == null) return;
                    ActiviationPresenter.super.onFaild(code, message);
                }
            });
        }
    }


    public void getOrderDNNumber() {
        try {

            AppApiHelper2.getAuthToken(new ApiHelper2.OnApiCallback() {
                @Override
                public void onSuccess(Object baseResponse) {

                    RestHelper restHelper = new RestHelper();

                    String authToken = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.AUTH__TOKEN,null);

                    String userId = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID, "");
                    String orderId = orderNo;

                    String urlAdition = userId + "/orders/" + orderId;

                    HashMap<String, String> queryParams = new HashMap<>();
                    queryParams.put("access_token", authToken);


                    //restHelper.sendRequest(2, AppApiHelper.API_ORDER_DETAIL, AppApiHelper.API_ORDER_DETAIL + urlAdition,
                    restHelper.sendRequest(2, RestHelper.REQUEST_TYPE_GET, AppApiHelper.API_ORDER_DETAIL, AppApiHelper.API_ORDER_DETAIL + urlAdition, RestHelper.CONTENT_TYPE_FROMDATA, queryParams, null, null,
                            new RestHelper.RestHelperCallback() {
                                @Override
                                public void onRequestCallback(int code, String message, RestResponse restResponse) {
                                    final JSONObject finalJsonObject = AppUtils.checkResponse(code, message, restResponse);

                                    String dnNumber = null;

                                    if (finalJsonObject != null) {
                                        try {

                                            if(finalJsonObject.has("deliveryOrderGroups")) {

                                                JSONArray deliveryOrderGroups = finalJsonObject.getJSONArray("deliveryOrderGroups");

                                                if(deliveryOrderGroups != null && deliveryOrderGroups.length() > 0) {

                                                    for (int i = 0; i < deliveryOrderGroups.length(); i++) {
                                                        JSONObject obj = deliveryOrderGroups.getJSONObject(i);

                                                        if(obj.has("entries")) {
                                                            JSONArray entries = obj.getJSONArray("entries");

                                                            if(entries != null && entries.length() > 0) {
                                                                for (int j = 0; j < entries.length(); j++) {
                                                                    JSONObject entry = entries.getJSONObject(j);

                                                                    if(entry.has("dnNumber") && entry.getString("dnNumber") != null) {
                                                                        dnNumber = entry.getString("dnNumber");
                                                                     //   DCCMDealerSterlite.getDataManager(AppPreferencesHelper.DN_NUMBER.set);
                                                                        break;
                                                                    }
                                                                }

                                                            }
                                                        }

                                                        //   dnNumber = "9825019919";

                                                        if(dnNumber != null) {
                                                            break;
                                                        }
                                                    }

                                                }

                                            }

                                            //onApiCallback.onSuccess(activationResponseModel);
                                        } catch (Exception e) {
                                            //onApiCallback.onFailed(code, message);
                                            e.printStackTrace();
                                        }

                                        findSubscriptionByEmailId(dnNumber);

                                    } else {
                                        //onApiCallback.onFailed(code, message);
                                    }

                                    if(dnNumber == null) {
                                        ActiviationPresenter.super.onFaild(code, "Number not found");
                                    }
                                }
                            }, null);

//                    restHelper.sendRequest(2, AppApiHelper.API_ORDER_DETAIL, AppApiHelper.API_ORDER_DETAIL + urlAdition,
//                            new RestHelper.RestHelperCallback() {
//                                @Override
//                                public void onRequestCallback(int code, String message, RestResponse restResponse) {
//                                    final JSONObject finalJsonObject = AppUtils.checkResponse(code, message, restResponse);
//
//                                    String dnNumber = null;
//
//                                    if (finalJsonObject != null) {
//                                        try {
//
//                                            if(finalJsonObject.has("deliveryOrderGroups")) {
//
//                                                JSONArray deliveryOrderGroups = finalJsonObject.getJSONArray("deliveryOrderGroups");
//
//                                                if(deliveryOrderGroups != null && deliveryOrderGroups.length() > 0) {
//
//                                                    for (int i = 0; i < deliveryOrderGroups.length(); i++) {
//                                                        JSONObject obj = deliveryOrderGroups.getJSONObject(i);
//
//                                                        if(obj.has("entries")) {
//                                                            JSONArray entries = obj.getJSONArray("entries");
//
//                                                            if(entries != null && entries.length() > 0) {
//                                                                for (int j = 0; j < entries.length(); j++) {
//                                                                    JSONObject entry = entries.getJSONObject(j);
//
//                                                                    if(entry.has("dnNumber") && entry.getString("dnNumber") != null) {
//                                                                        dnNumber = entry.getString("dnNumber");
//                                                                        break;
//                                                                    }
//                                                                }
//
//                                                            }
//                                                        }
//
//                                                        if(dnNumber != null) {
//                                                            break;
//                                                        }
//                                                    }
//
//                                                }
//
//                                            }
//
//                                            //onApiCallback.onSuccess(activationResponseModel);
//                                        } catch (Exception e) {
//                                            //onApiCallback.onFailed(code, message);
//                                            e.printStackTrace();
//                                        }
//
//                                        findSubscriptionByEmailId(dnNumber);
//
//                                    } else {
//                                        //onApiCallback.onFailed(code, message);
//                                    }
//                                }
//                            }, params);
                }

                @Override
                public void onFaild(Object baseResponse) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void findSubscriptionByEmailId(final String dnNumber) {
        try {
            RestHelper restHelper = new RestHelper();
            final JSONObject params = new JSONObject();

            String userId = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID, "");

            HashMap<String, String> qryparams = new HashMap<>();
            qryparams.put("uid", userId.toUpperCase());


            restHelper.sendRequest(2, RestHelper.REQUEST_TYPE_POST, AppApiHelper.API_FIND_SUBSCRIPTION_BY_EMAIL_ID, AppApiHelper.API_FIND_SUBSCRIPTION_BY_EMAIL_ID,
                    RestHelper.CONTENT_TYPE_JSON, qryparams, null, null,
                    new RestHelper.RestHelperCallback() {
                        @Override
                        public void onRequestCallback(int code, String message, RestResponse restResponse) {
                            final JSONObject finalJsonObject = AppUtils.checkResponse(code, message, restResponse);

                            String subscriptionNumber = null;

                            if (finalJsonObject != null) {
                                try {

                                    if(finalJsonObject.has("subscriptiondatas")) {

                                        JSONArray subscriptionDatas = finalJsonObject.getJSONArray("subscriptiondatas");

                                        if(subscriptionDatas != null && subscriptionDatas.length() > 0) {

                                            for (int i = 0; i < subscriptionDatas.length(); i++) {
                                                JSONObject obj = subscriptionDatas.getJSONObject(i);

                                                if(obj.has("dnNumber") &&
                                                        obj.getString("dnNumber").equalsIgnoreCase(dnNumber) &&
                                                        obj.has("subscriptionNumber")) {
                                                    subscriptionNumber = obj.getString("subscriptionNumber");
                                                    DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.PREF_SUBSCRIPTION_NUMBER, subscriptionNumber);
                                                    Log.e("serviceInstanceNumber",subscriptionNumber);
                                                    break;
                                                }
                                            }
                                        }
                                    }

                                    //onApiCallback.onSuccess(activationResponseModel);
                                } catch (Exception e) {
                                    //onApiCallback.onFailed(code, message);
                                    e.printStackTrace();
                                }

                                if(subscriptionNumber != null) {
                                    invokeActivateApi(subscriptionNumber);
                                }
                            } else {
                                //onApiCallback.onFailed(code, message);
                            }

                            if(subscriptionNumber == null) {
                                ActiviationPresenter.super.onFaild(code, "Subcription number not found!");
                            }
                        }
                    }, null);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void activateOrder(String orderNo) {
        this.orderNo = orderNo;
        getView().showProgressBar(true);
        getView().hideConnectionLostView();
        getOrderDNNumber();
    }

    private void invokeActivateApi(String subscriptionNumber) {
        DCCMDealerSterlite.getDataManager().doActivateMyOrder(orderNo, subscriptionNumber, new ApiHelper.OnApiCallback<ActivationResponseModel>() {
            @Override
            public void onSuccess(ActivationResponseModel baseResponse) {
                if (getView() == null)
                    return;
                getView().hideProgressBar(true);
                String message = null;
                if (baseResponse != null)
                 //   message = baseResponse.getResponseMessage();
                    message = baseResponse.getResponseMessage();
                getView().gotoSuccessScreen(message);
            }

            @Override
            public void onFailed(int code, String message) {
                if (getView() == null)
                    return;
                getView().hideProgressBar(true);
                ActiviationPresenter.super.onFaild(code, message);
            }
        });
    }


    public void getOrderDNNumber(final String orderno) {
        try {

            AppApiHelper2.getAuthToken(new ApiHelper2.OnApiCallback() {
                @Override
                public void onSuccess(Object baseResponse) {

                    RestHelper restHelper = new RestHelper();

                    String authToken = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.AUTH__TOKEN,null);

                    String userId = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID, "");
                    String orderId = orderno;

                    String urlAdition = userId + "/orders/" + orderId;

                    HashMap<String, String> queryParams = new HashMap<>();
                    queryParams.put("access_token", authToken);


                    //restHelper.sendRequest(2, AppApiHelper.API_ORDER_DETAIL, AppApiHelper.API_ORDER_DETAIL + urlAdition,
                    restHelper.sendRequest(2, RestHelper.REQUEST_TYPE_GET, AppApiHelper.API_ORDER_DETAIL, AppApiHelper.API_ORDER_DETAIL + urlAdition, RestHelper.CONTENT_TYPE_FROMDATA, queryParams, null, null,
                            new RestHelper.RestHelperCallback() {
                                @Override
                                public void onRequestCallback(int code, String message, RestResponse restResponse) {
                                    final JSONObject finalJsonObject = AppUtils.checkResponse(code, message, restResponse);

                                    String dnNumber = null;

                                    if (finalJsonObject != null) {
                                        try {

                                            if(finalJsonObject.has("deliveryOrderGroups")) {

                                                JSONArray deliveryOrderGroups = finalJsonObject.getJSONArray("deliveryOrderGroups");

                                                if(deliveryOrderGroups != null && deliveryOrderGroups.length() > 0) {

                                                    for (int i = 0; i < deliveryOrderGroups.length(); i++) {
                                                        JSONObject obj = deliveryOrderGroups.getJSONObject(i);

                                                        if(obj.has("entries")) {
                                                            JSONArray entries = obj.getJSONArray("entries");

                                                            if(entries != null && entries.length() > 0) {
                                                                for (int j = 0; j < entries.length(); j++) {
                                                                    JSONObject entry = entries.getJSONObject(j);

                                                                    if(entry.has("dnNumber") && entry.getString("dnNumber") != null) {
                                                                        dnNumber = entry.getString("dnNumber");
                                                                    }
                                                                }

                                                            }
                                                        }

                                                        // dnNumber = "9825019919";

                                                        if(dnNumber != null) {
                                                            break;
                                                        }
                                                    }

                                                }

                                            }

                                            //onApiCallback.onSuccess(activationResponseModel);
                                        } catch (Exception e) {
                                            //onApiCallback.onFailed(code, message);
                                            e.printStackTrace();
                                        }

                                        findSubscriptionByEmailId(dnNumber);

                                    } else {
                                        //onApiCallback.onFailed(code, message);
                                    }

                                    if(dnNumber == null) {
                                        ActiviationPresenter.super.onFaild(code, "Number not found");
                                    }
                                }
                            }, null);

//                    restHelper.sendRequest(2, AppApiHelper.API_ORDER_DETAIL, AppApiHelper.API_ORDER_DETAIL + urlAdition,
//                            new RestHelper.RestHelperCallback() {
//                                @Override
//                                public void onRequestCallback(int code, String message, RestResponse restResponse) {
//                                    final JSONObject finalJsonObject = AppUtils.checkResponse(code, message, restResponse);
//
//                                    String dnNumber = null;
//
//                                    if (finalJsonObject != null) {
//                                        try {
//
//                                            if(finalJsonObject.has("deliveryOrderGroups")) {
//
//                                                JSONArray deliveryOrderGroups = finalJsonObject.getJSONArray("deliveryOrderGroups");
//
//                                                if(deliveryOrderGroups != null && deliveryOrderGroups.length() > 0) {
//
//                                                    for (int i = 0; i < deliveryOrderGroups.length(); i++) {
//                                                        JSONObject obj = deliveryOrderGroups.getJSONObject(i);
//
//                                                        if(obj.has("entries")) {
//                                                            JSONArray entries = obj.getJSONArray("entries");
//
//                                                            if(entries != null && entries.length() > 0) {
//                                                                for (int j = 0; j < entries.length(); j++) {
//                                                                    JSONObject entry = entries.getJSONObject(j);
//
//                                                                    if(entry.has("dnNumber") && entry.getString("dnNumber") != null) {
//                                                                        dnNumber = entry.getString("dnNumber");
//                                                                        break;
//                                                                    }
//                                                                }
//
//                                                            }
//                                                        }
//
//                                                        if(dnNumber != null) {
//                                                            break;
//                                                        }
//                                                    }
//
//                                                }
//
//                                            }
//
//                                            //onApiCallback.onSuccess(activationResponseModel);
//                                        } catch (Exception e) {
//                                            //onApiCallback.onFailed(code, message);
//                                            e.printStackTrace();
//                                        }
//
//                                        findSubscriptionByEmailId(dnNumber);
//
//                                    } else {
//                                        //onApiCallback.onFailed(code, message);
//                                    }
//                                }
//                            }, params);
                }

                @Override
                public void onFaild(Object baseResponse) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void findSubscriptionByEmailId(final ApiHelper.OnApiCallback callback) {
        try {
            RestHelper restHelper = new RestHelper();
            final JSONObject params = new JSONObject();

            String userId = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID, "");

            HashMap<String, String> qryparams = new HashMap<>();
            qryparams.put("uid", userId.toUpperCase());


            restHelper.sendRequest(2, RestHelper.REQUEST_TYPE_POST, AppApiHelper.API_FIND_SUBSCRIPTION_BY_EMAIL_ID, AppApiHelper.API_FIND_SUBSCRIPTION_BY_EMAIL_ID,
                    RestHelper.CONTENT_TYPE_JSON, qryparams, null, null,
                    new RestHelper.RestHelperCallback() {
                        @Override
                        public void onRequestCallback(int code, String message, RestResponse restResponse) {
                            final JSONObject finalJsonObject = AppUtils.checkResponse(code, message, restResponse);

                            String subscriptionNumber = null;
                            String dnNumber = null;

                            if (finalJsonObject != null) {
                                try {

                                    if(finalJsonObject.has("subscriptiondatas")) {

                                        JSONArray subscriptionDatas = finalJsonObject.getJSONArray("subscriptiondatas");

                                        if(subscriptionDatas != null && subscriptionDatas.length() > 0) {

                                            for (int i = 0; i < subscriptionDatas.length(); i++) {
                                                JSONObject obj = subscriptionDatas.getJSONObject(i);
                                                if(obj.getString("subscriptionNumber")!=null) {
                                                    subscriptionNumber = obj.getString("subscriptionNumber");
                                                    DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.PREF_SUBSCRIPTION_NUMBER, subscriptionNumber);
                                                    Log.e("serviceInstanceNumber",subscriptionNumber);

                                                    // break;
                                                }
                                                //Divya_NEW
                                                if(obj.getString("dnNumber")!=null) {
                                                    dnNumber = obj.getString("dnNumber");
                                                    DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.PREF_DN_NUMBER, dnNumber);
                                                    Log.e("dnNumber",dnNumber);

                                                    break;
                                                }
//                                                if(obj.has("dnNumber") &&
//                                                        obj.getString("dnNumber").equalsIgnoreCase(dnNumber) &&
//                                                        obj.has("subscriptionNumber")) {
//                                                    subscriptionNumber = obj.getString("subscriptionNumber");
//                                                }
                                            }
                                            callback.onSuccess(null);
                                        }
                                        else {
                                            callback.onFailed(1,null);

                                        }
                                    }


                                } catch (Exception e) {
                                    callback.onFailed(1,null);
                                    e.printStackTrace();
                                }

                                /*if(subscriptionNumber != null) {
                                 //   invokeActivateApi(subscriptionNumber);
                                }*/
                            } else {
                                callback.onFailed(1,null);
                            }

                            if(subscriptionNumber == null) {
                                ActiviationPresenter.super.onFaild(code, "Subcription number not found!");
                            }
                        }
                    }, null);


        } catch (Exception e) {
            e.printStackTrace();
            callback.onFailed(1,null);

        }
    }
    @Override
    public void getDnNumWithAuthToken(final Order order) {
        Log.e("ActiviationPresenter","orderEntryWsDTO :: null");
        if (getView() == null) return;
        getView().showProgressBar();
        String client_id="authbridge";
        String client_secret="secret";
        String grant_type="client_credentials";
        String b="bhoomi";
        AppApiHelper2.getWebServices(AppApiHelper2.AUTH_URL).getAuthToken(client_id,client_secret,grant_type,b,new Callback<AuthResponseDTO>() {
            @Override
            public void success(AuthResponseDTO authResponseDTO, Response response) {
                Log.e(" In Success Response : ", " From DCCM :: " + authResponseDTO.getAccess_token());
                Log.e("Token : ", authResponseDTO.getAccess_token());
                DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.AUTH__TOKEN.toString(), authResponseDTO.getAccess_token());
                getDNNumberbyOrder(order,authResponseDTO.getAccess_token());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("getAuthToken :", "RetrofitError" + error);
            }
        });
    }
   /* @Override
    public void getDNNumberbyOrder(final String orderno,final ApiHelper.OnApiCallback<String> onApiCallback) {
        try {
            AppApiHelper2.getAuthToken(new ApiHelper2.OnApiCallback() {
                @Override
                public void onSuccess(Object baseResponse) {

                    RestHelper restHelper = new RestHelper();

                    String authToken = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.AUTH__TOKEN,null);

                    String userId = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID, "");
                    String orderId = orderno;

                    String urlAdition = userId + "/orders/" + orderId;

                    HashMap<String, String> queryParams = new HashMap<>();
                    queryParams.put("access_token", authToken);


                    //restHelper.sendRequest(2, AppApiHelper.API_ORDER_DETAIL, AppApiHelper.API_ORDER_DETAIL + urlAdition,
                    restHelper.sendRequest(2, RestHelper.REQUEST_TYPE_GET, AppApiHelper.API_ORDER_DETAIL, AppApiHelper.API_ORDER_DETAIL + urlAdition, RestHelper.CONTENT_TYPE_FROMDATA, queryParams, null, null,
                            new RestHelper.RestHelperCallback() {
                                @Override
                                public void onRequestCallback(int code, String message, RestResponse restResponse) {
                                    final JSONObject finalJsonObject = AppUtils.checkResponse(code, message, restResponse);
                                    Log.e("finalJsonObject",finalJsonObject + " ");
                                    boolean callback=true;
                                    String dnNumber = null;

                                    if (finalJsonObject != null) {
                                        try {

                                            if(finalJsonObject.has("deliveryOrderGroups")) {

                                                JSONArray deliveryOrderGroups = finalJsonObject.getJSONArray("deliveryOrderGroups");

                                                if(deliveryOrderGroups != null && deliveryOrderGroups.length() > 0) {

                                                    for (int i = 0; i < deliveryOrderGroups.length(); i++) {
                                                        JSONObject obj = deliveryOrderGroups.getJSONObject(i);

                                                        if(obj.has("entries")) {
                                                            JSONArray entries = obj.getJSONArray("entries");

                                                            if(entries != null && entries.length() > 0) {
                                                                for (int j = 0; j < entries.length(); j++) {
                                                                    JSONObject entry = entries.getJSONObject(j);

                                                                    if(entry.has("dnNumber") && entry.getString("dnNumber") != null) {
                                                                        dnNumber = entry.getString("dnNumber");
                                                                        Log.e("dnNumber",dnNumber + " ");
                                                                        onApiCallback.onSuccess(dnNumber);
                                                                        callback=false;
                                                                        break;
                                                                    }
                                                                }

                                                            }
                                                        }

                                                        // dnNumber = "9825019919";

                                                       *//* if(dnNumber != null) {
                                                            break;
                                                        }*//*
                                                    }

                                                }
                                            }
                                            if(callback) {
                                                callback=false;
                                                onApiCallback.onFailed(1, "Fail");
                                            }

                                        } catch (Exception e) {
                                            //onApiCallback.onFailed(code, message);
                                            e.printStackTrace();
                                            callback=false;
                                            onApiCallback.onFailed(1, "Fail");
                                        }

                                       // findSubscriptionByEmailId(dnNumber);

                                    } else {
                                        callback = false;
                                        onApiCallback.onFailed(1, "Fail");
                                    }

                                    if(dnNumber == null && callback) {
                                        callback = false;
                                        onApiCallback.onFailed(code, "Number not found");
                                    }
                                }
                            }, null);

//                    restHelper.sendRequest(2, AppApiHelper.API_ORDER_DETAIL, AppApiHelper.API_ORDER_DETAIL + urlAdition,
//                            new RestHelper.RestHelperCallback() {
//                                @Override
//                                public void onRequestCallback(int code, String message, RestResponse restResponse) {
//                                    final JSONObject finalJsonObject = AppUtils.checkResponse(code, message, restResponse);
//
//                                    String dnNumber = null;
//
//                                    if (finalJsonObject != null) {
//                                        try {
//
//                                            if(finalJsonObject.has("deliveryOrderGroups")) {
//
//                                                JSONArray deliveryOrderGroups = finalJsonObject.getJSONArray("deliveryOrderGroups");
//
//                                                if(deliveryOrderGroups != null && deliveryOrderGroups.length() > 0) {
//
//                                                    for (int i = 0; i < deliveryOrderGroups.length(); i++) {
//                                                        JSONObject obj = deliveryOrderGroups.getJSONObject(i);
//
//                                                        if(obj.has("entries")) {
//                                                            JSONArray entries = obj.getJSONArray("entries");
//
//                                                            if(entries != null && entries.length() > 0) {
//                                                                for (int j = 0; j < entries.length(); j++) {
//                                                                    JSONObject entry = entries.getJSONObject(j);
//
//                                                                    if(entry.has("dnNumber") && entry.getString("dnNumber") != null) {
//                                                                        dnNumber = entry.getString("dnNumber");
//                                                                        break;
//                                                                    }
//                                                                }
//
//                                                            }
//                                                        }
//
//                                                        if(dnNumber != null) {
//                                                            break;
//                                                        }
//                                                    }
//
//                                                }
//
//                                            }
//
//                                            //onApiCallback.onSuccess(activationResponseModel);
//                                        } catch (Exception e) {
//                                            //onApiCallback.onFailed(code, message);
//                                            e.printStackTrace();
//                                        }
//
//                                        findSubscriptionByEmailId(dnNumber);
//
//                                    } else {
//                                        //onApiCallback.onFailed(code, message);
//                                    }
//                                }
//                            }, params);
                }

                @Override
                public void onFaild(Object baseResponse) {
                    if(getView()==null)return;
                        getView().hideProgressBar();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

    @Override
    public void getDNNumberbyOrder(final Order order, String access_token) {
        try {

            String userId = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID, "");
            AppApiHelper2.getWebServices().getDnNumber(userId, order.getOrderId(), access_token, new Callback<OrderWsDTO>() {
                @Override
                public void success(OrderWsDTO orderWsDTO, Response response) {
                    Log.e("ActiviationPresenter","orderWsDTO :: " + orderWsDTO);
                    boolean isDn = true;
                    if (getView() == null) return;
                    getView().hideProgressBar();
                    if(orderWsDTO!=null){
                        List<DeliveryOrderEntryGroupWsDTO> list =orderWsDTO.getDeliveryOrderGroups();
                        for (DeliveryOrderEntryGroupWsDTO deliveryOrderEntryGroupWsDTO:list) {
                            for (OrderEntryWsDTO orderEntryWsDTO:deliveryOrderEntryGroupWsDTO.getEntries()) {
                                if(orderEntryWsDTO.getDnNumber()!=null && isDn ) {
                                    Log.e("ActiviationPresenter", "deliveryOrderEntryGroupWsDTO.getEntries :: " + orderEntryWsDTO.getDnNumber());
                                    isDn = false;
                                    if (getView() == null) return;
                                    getView().hideProgressBar();
                                    getView().getDNNumberbyOrderApiCallSuccess(orderEntryWsDTO.getDnNumber(), order);
                                    break;
                                }
                            }
                        }
                        List<OrderEntryWsDTO> orderEntryWsDTOS =orderWsDTO.getUnconsignedEntries();
                            for (OrderEntryWsDTO orderEntryWsDTO:orderEntryWsDTOS) {
                                if(orderEntryWsDTO.getDnNumber()!=null && isDn ) {
                                    Log.e("ActiviationPresenter", "orderEntryWsDTO :: " + orderEntryWsDTO.getDnNumber());
                                    isDn = false;
                                    if (getView() == null) return;
                                    getView().hideProgressBar();
                                    getView().getDNNumberbyOrderApiCallSuccess(orderEntryWsDTO.getDnNumber(), order);
                                    break;
                                }
                            }

                    }
                    if (isDn){
                        Log.e("ActiviationPresenter","orderEntryWsDTO :: null");
                        if (getView() == null) return;
                        getView().hideProgressBar();
                        getView().getDNNumberbyOrderApiCallSuccess(null,order);
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("ActiviationPresenter","RetrofitError :: " + error);
                    if (getView() == null) return;
                    getView().hideProgressBar();
                    getView().getDNNumberbyOrderApiCallSuccess(null,order);
                }
            });

        } catch (Exception e) {
            Log.e("ActiviationPresenter","Exception :: " + e);
            if(getView()==null)return;
            getView().hideProgressBar();
            getView().getDNNumberbyOrderApiCallSuccess(null,order);
        }
    }
}

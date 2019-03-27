package com.sterlite.dccmappfordealersterlite.fragment.home;


import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;
import com.sterlite.dccmappfordealersterlite.data.network.ApiHelper;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.model.HomeFragment.SubscriberServicewiseBalance;
import com.sterlite.dccmappfordealersterlite.model.bssrest.ServiceInstanceDetail;
import com.sterlite.dccmappfordealersterlite.model.bssrest.ServiceInventoryResponse;

/**
 * Created by etech3 on 27/6/18.
 */

public class HomePresenter<V extends HomeContract.View> extends BasePresenter<V> implements HomeContract.Presenter<V> {


    List<ServiceInventoryResponse> invList;
    HashMap<String,String> invHashMap;
    HashMap<String,String> billHashMap;
    HashMap<String,String> chargingPatternMap;

    ServiceInventoryResponse inv;
    static int length;
    // 20180830
    @Override
    public void onAttach(V baseView) {
        super.onAttach(baseView);

    }


    @Override
    public void init() {
        if (getView() == null)
            return;
        getView().setUpView(false);
        /*if (Constants.IS_DUMMY_MODE) {
            getView().loadDataToView(null);
        } else {*/
        startApiCall();
        String subscriberIdentifier = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.PREF_SUBSCRIPTION_NUMBER, null);
        //  subscriberIdentifier = "767576037";
        String tenantId = "0";
        if (subscriberIdentifier != null) {
            DCCMDealerSterlite.getDataManager().doGetMyAccountData(subscriberIdentifier, tenantId, new ApiHelper.OnApiCallback<SubscriberServicewiseBalance>() {
                @Override
                public void onSuccess(SubscriberServicewiseBalance baseResponse) {
                    if (getView() == null) return;
                    HomePresenter.super.onSuccess();

                    getView().loadDataToView(baseResponse);
                }

                @Override
                public void onFailed(int code, String message) {
                    if (getView() == null) return;
                    HomePresenter.super.onFaild(code, message);
                    getView().FailData(message);
                }
            });
        /* }*/
        }
    }
    @Override
    public void admin() {
        getView().FailData("Admin");

    }

    @Override
    public void reset() {
        if (getView() == null)
            return;
        init();
    }





    @Override
    public void getCustomerDetails(String userId, final boolean isServiceInstance) {
        startApiCall();
        if (userId != null) {
            DCCMDealerSterlite.getDataManager().getCustomerDetails(userId.toUpperCase(), new ApiHelper.OnApiCallback<ServiceInstanceDetail[]>() {
                @Override
                public void onSuccess(ServiceInstanceDetail[] serviceInstanceDetails) {
                    Log.e("Success:CustomerDetails", serviceInstanceDetails.toString());

                    if (getView() == null)
                        return;
                    if (serviceInstanceDetails != null) {
                        invList = new ArrayList<>();

                        length = serviceInstanceDetails.length;
                        Log.e("length", length + "");

                        invHashMap = new HashMap<>();
                        billHashMap = new HashMap<>();
                        chargingPatternMap = new HashMap<>();
                        for (final ServiceInstanceDetail serviceInstanceDetail : serviceInstanceDetails) {
                            Log.e("serviceInstanceDetail::" + length, serviceInstanceDetail.toString());

                            getInventoryNumber(serviceInstanceDetail.getAccountNumber(), new ApiHelper.OnApiCallback<String>() {
                                @Override
                                public void onSuccess(String baseResponse) {
                                    if (getView() == null)
                                        return;
                                    if (baseResponse != null) {
                                        Log.e("getInventoryNumber", baseResponse.toString());
                                        inv = new ServiceInventoryResponse();
                                        inv.setUserName(serviceInstanceDetail.getUsername());
                                        inv.setServiceInstanceNumber(serviceInstanceDetail.getAccountNumber());
                                        inv.setPlanName(serviceInstanceDetail.getBasicProduct().getName());

                                        inv.setInventoryNumber(baseResponse);
                                        invList.add(inv);
                                        //onApiCallback.onSuccess(baseResponse);
                                        // invList.put(serviceInstanceDetail.getAccountNumber(), baseResponse.trim());
                                        //   Log.e("InventoryList", invList.toString());
                                        invHashMap.put(baseResponse, serviceInstanceDetail.getAccountNumber());
                                        billHashMap.put(serviceInstanceDetail.getAccountNumber(), serviceInstanceDetail.getBillingAccountRef().getAccountNumber());
                                        chargingPatternMap.put(baseResponse, serviceInstanceDetail.getChargingPattern());
                                        length--;
                                    }
                                    if (length == 0) {
                                        DCCMDealerSterlite.getDataManager().saveMap(AppPreferencesHelper.SERVICE_INSTANCE_LIST, invHashMap);
                                        DCCMDealerSterlite.getDataManager().saveMap(AppPreferencesHelper.BILLING_DETAIL_MAP, billHashMap);
                                        DCCMDealerSterlite.getDataManager().saveMap(AppPreferencesHelper.CHARGING_PATTERN_MAP, chargingPatternMap);
                                        getView().hideProgressBar();
                                        getView().onSuccessCustDetails(invList, isServiceInstance);
                                    }

                                }

                                @Override
                                public void onFailed(int code, String message) {
                                    if (getView() == null)
                                        return;
                                    //  onApiCallback.onFailed(code,message);

                                    getView().onFail();

                                }
                            });
                        }

                        //getView().onSuccessCustDetails(baseResponse);
                    }
                    Log.e("InventoryList:Out", invList.toString());

                    //  getView().gotoSuccessScreen(baseResponse.getResponseMessage());
                }

                @Override
                public void onFailed(int code, String message) {
                    Log.e("Fail:CustomerDetails", message);
               /* if (getView() == null)
                    return;*/
                    if (getView() == null)
                        return;
                    getView().hideProgressBar();
                    getView().onFail();

//                AppUtils.showToast(context,"Interna");
                    //Log.e("getCustomerDetails:fail",message);

                    //  InternetPackSummeryPresenter.super.onFaild(code, message);
                    //  getView().gotoFailScreen();

                }
            });
        }
    }

    public void getInventoryNumber(final String userId, final ApiHelper.OnApiCallback<String> onApiCallback) {
        // userId = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID, null);
        startApiCall();
        //  String userId="";
        if (userId != null) {

            DCCMDealerSterlite.getDataManager().getInventoryNumber(userId, new ApiHelper.OnApiCallback<String>() {
                @Override
                public void onSuccess(String baseResponse) {
                    if (getView() == null)
                        return;
                    if (baseResponse != null) {
                        Log.e("getInventoryNumber", baseResponse.toString());
                        onApiCallback.onSuccess(baseResponse);

                    }

                }

                @Override
                public void onFailed(int code, String message) {
                    if (getView() == null)
                        return;
                    onApiCallback.onFailed(code, message);


                }
            });
        }
    }
}

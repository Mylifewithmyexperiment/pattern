package com.sterlite.dccmappfordealersterlite.activity.loginnew;

import android.util.Log;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.sterlite.dccmappfordealersterlite.activity.TicketCreate.TicketPresenter;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;
import com.sterlite.dccmappfordealersterlite.data.network.ApiHelper;
import com.sterlite.dccmappfordealersterlite.data.network.AppApiHelper;
import com.sterlite.dccmappfordealersterlite.data.network.AppApiHelper2;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.model.CustomerListWsDTO;
import com.sterlite.dccmappfordealersterlite.model.HomeFragment.SubscriberServicewiseBalance;
import com.sterlite.dccmappfordealersterlite.model.Ticket;
import com.sterlite.dccmappfordealersterlite.model.UpdateCustomer.GetUpdateCustomerRequestData;
import com.sterlite.dccmappfordealersterlite.model.UpdateCustomer.GetUpdateCustomerResponseData;
import com.sterlite.dccmappfordealersterlite.model.dto.user.UserResponseWsDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.validatecustomer.ValidateCustomerWsDTO;

import java.util.ArrayList;


/**
 * Created by etech3 on 27/6/18.
 */

public class LoginPresenter<V extends LoginContract.View> extends BasePresenter<V> implements LoginContract.Presenter<V> {

    @Override
    public void doLogin(ValidateCustomerWsDTO validateCustomerWsDTO, final Boolean isPartner) {
        getView().showProgressBar();


        AppApiHelper2.getWebServices().doLogin(validateCustomerWsDTO, new Callback<UserResponseWsDTO>() {
            @Override
            public void success(final UserResponseWsDTO userResponseWsDTO, Response response) {

                Log.e("LoginPresenter ::: ", userResponseWsDTO.toString() + " ");
                if (userResponseWsDTO.getResponseCode().equalsIgnoreCase("1")) {
                    DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.CUSTOMER_UID, userResponseWsDTO.getUser().getUid());
                   Log.e("Customer UID:::",userResponseWsDTO.getUser().getUid()+" ");
                    if (isPartner) {
                       DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.PARTNER_UID, userResponseWsDTO.getUser().getUid());
                       DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.PARTNER_NAME, userResponseWsDTO.getUser().getName());

                   }
                    DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.CUSTOMER_NAME, userResponseWsDTO.getUser().getName());
                    DCCMDealerSterlite.getDataManager().getMyCart(new ApiHelper.OnApiCallback<String>() {
                        @Override
                        public void onSuccess(String baseResponse) {
                            if (getView() == null)
                                return;
                            getView().hideProgressBar();
                            getView().onSuccessLogin(userResponseWsDTO);
                        }

                        @Override
                        public void onFailed(int code, String message) {
                            if (getView() == null)
                                return;
                            getView().hideProgressBar();
                            getView().onSuccessLogin(userResponseWsDTO);
                        }
                    });
                } else {
                    if (getView() == null)
                        return;
                    getView().hideProgressBar();
                    getView().onFailLogin();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getView() == null)
                    return;
                getView().hideProgressBar();
                getView().onFailLogin();
                Log.e("LoginPresenter ::: ", error.toString() + " ");
            }
        });
    }



    @Override
    public void getAccountData() {
        if (getView() == null)
            return;
        getView().showProgressBar();
        startApiCall();
        String subscriberIdentifier = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.PREF_SUBSCRIPTION_NUMBER, null);
        //  subscriberIdentifier = "767576037";
        String tenantId = "0";
        DCCMDealerSterlite.getDataManager().doGetMyAccountData(subscriberIdentifier, tenantId, new ApiHelper.OnApiCallback<SubscriberServicewiseBalance>() {
            @Override
            public void onSuccess(SubscriberServicewiseBalance baseResponse) {
                if (getView() == null) return;
                getView().hideProgressBar();
                Log.e("LoginPresenter", "baseResponse " + baseResponse);
                getView().getAccountDataApiSucc(baseResponse);
            }

            @Override
            public void onFailed(int code, String message) {
                if (getView() == null) return;
                getView().hideProgressBar();
                Log.e("LoginPresenter", "message " + message);
                getView().getAccountDataApiSucc(null);
            }
        });
        /* }*/
    }


    @Override
    public void getAccountProfileData(String customerNumber) {

        getView().showProgressBar();

        if(getView()!=null)getView().hideProgressBar();
        Log.e("getProfileData"," "+customerNumber);
        DCCMDealerSterlite.getDataManager().getProfileDetailsbyCustomerNumber(customerNumber, new ApiHelper.OnApiCallback<GetUpdateCustomerResponseData>() {

            @Override
            public void onSuccess(GetUpdateCustomerResponseData getUpdateCustomerResponseData) {
                Log.e("on success", getUpdateCustomerResponseData + "");
                if (getView() == null) return;
                getView().hideProgressBar();
                if (getUpdateCustomerResponseData != null) {
                    getView().onSuccessGetProfileData(getUpdateCustomerResponseData);
                }
            }

            @Override
            public void onFailed(int code, String message) {
                if (getView() == null) return;
                LoginPresenter.super.onFaild(code, message);
            }
        });
    }

    @Override
    public void updateAccountProfileData(GetUpdateCustomerRequestData getUpdateCustomerRequestData) {

        getView().showProgressBar();

        if(getView()!=null)getView().hideProgressBar();
        Log.e("updateProfileData"," "+getUpdateCustomerRequestData);
        DCCMDealerSterlite.getDataManager().updateCustomerProfileDetails(getUpdateCustomerRequestData, new ApiHelper.OnApiCallback<GetUpdateCustomerResponseData>() {

            @Override
            public void onSuccess(GetUpdateCustomerResponseData getUpdateCustomerResponseData) {
                Log.e("on success", getUpdateCustomerResponseData + "");
                if (getView() == null) return;
                getView().hideProgressBar();
                if (getUpdateCustomerResponseData != null) {
                    getView().onSuccessUpdateProfileData(getUpdateCustomerResponseData);
                }
            }

            @Override
            public void onFailed(int code, String message) {
                if (getView() == null) return;
                LoginPresenter.super.onFaild(code, message);
            }
        });
    }

   /* @Override
    public void success(boolean b){
        Log.e("login","success");
    }*/



}

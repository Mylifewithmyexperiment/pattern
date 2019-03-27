package com.sterlite.dccmappfordealersterlite.activity.InternetPackSummery;


import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;
import com.sterlite.dccmappfordealersterlite.data.network.ApiHelper;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.model.SubscribeAddOn.SubscribeAddOnResponceModel;
import com.sterlite.dccmappfordealersterlite.model.customer.BaseResponse;

/**
 * Created by etech3 on 27/6/18.
 */

public class InternetPackSummeryPresenter<V extends InternetPackSummeryContract.View> extends BasePresenter<V> implements InternetPackSummeryContract.Presenter<V> {

    // 20180830
    @Override
    public void onAttach(V baseView) {
        super.onAttach(baseView);
    }

    @Override
    public void doSubscribeAddOn(String planName, String paymentMode) {
        String subscriptionNumber = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.PREF_SUBSCRIPTION_NUMBER, null);
        startApiCall();
        DCCMDealerSterlite.getDataManager().doSubscribeAddOn(paymentMode,subscriptionNumber, planName, new ApiHelper.OnApiCallback<SubscribeAddOnResponceModel>() {
            @Override
            public void onSuccess(SubscribeAddOnResponceModel baseResponse) {
                if (getView() == null)
                    return;
                InternetPackSummeryPresenter.super.onSuccess();
                getView().gotoSuccessScreen(baseResponse.getResponseMessage());
            }

            @Override
            public void onFailed(int code, String message) {
                if (getView() == null)
                return;
                InternetPackSummeryPresenter.super.onFaild(code, message);
                getView().gotoFailScreen();

            }
        });
    }

    @Override
    public void changePlan(String planName) {
        startApiCall();
        DCCMDealerSterlite.getDataManager().changePlan(planName,new ApiHelper.OnApiCallback<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse baseResponse) {
                if (getView() == null) return;
                if (baseResponse != null) {
                    if (getView() == null)
                        return;
                    InternetPackSummeryPresenter.super.onSuccess();
                    getView().gotoSuccessScreen(baseResponse.getResponseMessage());
                }
            }

            @Override
            public void onFailed(int code, String message) {
                if (getView() == null)
                    return;
                InternetPackSummeryPresenter.super.onFaild(code, message);
                getView().gotoFailScreen();
            }
        });

    }

}

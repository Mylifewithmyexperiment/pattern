package com.sterlite.dccmappfordealersterlite.activity.RechargeSummery;


import android.util.Log;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;
import com.sterlite.dccmappfordealersterlite.data.network.AppApiHelper2;
import com.sterlite.dccmappfordealersterlite.model.Recharge;
import com.sterlite.dccmappfordealersterlite.model.dto.recharge.RechargeDetailResponseData;
import com.sterlite.dccmappfordealersterlite.model.dto.recharge.RechargeRequestData;

/**
 * Created by etech3 on 27/6/18.
 */

public class RechargeSummeryPresenter<V extends RechargeSummeryContract.View> extends BasePresenter<V> implements RechargeSummeryContract.Presenter<V> {

    // 20180830
    @Override
    public void onAttach(V baseView) {
        super.onAttach(baseView);

    }

    @Override
    public void doRecharge(Recharge recharge) {

        if (getView() == null) return;
        getView().showProgressBar();
        RechargeRequestData rechargeRequestData = new RechargeRequestData();
        rechargeRequestData.setRechargeType(Constants.RECHARGE_TYPE);
        rechargeRequestData.setMobileNumber(recharge.getNumber());
        rechargeRequestData.setDenominationAmount(recharge.getAmount());
        rechargeRequestData.setSourceChannel(Constants.SOURCE);

        AppApiHelper2.getWebServices(AppApiHelper2.BSS_ADAPTER_BASE_URL).doRecharge(rechargeRequestData, new Callback<RechargeDetailResponseData>() {
            @Override
            public void success(RechargeDetailResponseData rechargeDetailResponseData, Response response) {
                if (getView() == null) return;
                getView().hideProgressBar();
                Log.e("RechargeSummery","rechargeDetailResponseData :: " + rechargeDetailResponseData);
                getView().rechargeApiCallSuccess(rechargeDetailResponseData);
            }

            @Override
            public void failure(RetrofitError error) {
                if (getView() == null) return;
                getView().hideProgressBar();
                Log.e("RechargeSummery","error :: " + error);
            }
        });
    }
}

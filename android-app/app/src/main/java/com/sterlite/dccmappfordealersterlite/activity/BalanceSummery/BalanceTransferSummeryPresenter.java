package com.sterlite.dccmappfordealersterlite.activity.BalanceSummery;


import android.util.Log;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;
import com.sterlite.dccmappfordealersterlite.data.network.AppApiHelper2;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.model.Recharge;
import com.sterlite.dccmappfordealersterlite.model.dto.transferbalance.TransferBalanceRequestData;
import com.sterlite.dccmappfordealersterlite.model.dto.transferbalance.TransferBalanceResponseData;

/**
 * Created by etech3 on 27/6/18.
 */

public class BalanceTransferSummeryPresenter<V extends BalanceTransferSummeryContract.View> extends BasePresenter<V> implements BalanceTransferSummeryContract.Presenter<V> {

    // 20180830
    @Override
    public void onAttach(V baseView) {
        super.onAttach(baseView);

    }
    @Override
    public void balanceTransfer(Recharge transfer) {
        if (getView() == null) return;
        getView().showProgressBar();
        TransferBalanceRequestData requestData = new TransferBalanceRequestData();
        requestData.setFromMobileNumber(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.PREF_DN_NUMBER,""));
        requestData.setToMobileNumber(transfer.getNumber());
        requestData.setTransferAmount(Long.valueOf(transfer.getAmount())*100);

        AppApiHelper2.getWebServices(AppApiHelper2.BSS_ADAPTER_BASE_URL).balanceTransfer(requestData, new Callback<TransferBalanceResponseData>() {
            @Override
            public void success(TransferBalanceResponseData transferBalanceResponseData, Response response) {
                if (getView() == null) return;
                getView().hideProgressBar();
                Log.e("BalanceTransfer","rechargeDetailResponseData :: " + transferBalanceResponseData);
                getView().balanceTransferApiCallSuccess(transferBalanceResponseData);
            }

            @Override
            public void failure(RetrofitError error) {
                if (getView() == null) return;
                getView().hideProgressBar();
                Log.e("BalanceTransfer","error :: " + error);
                getView().balanceTransferApiCallSuccess(null);
            }
        });

    }

}

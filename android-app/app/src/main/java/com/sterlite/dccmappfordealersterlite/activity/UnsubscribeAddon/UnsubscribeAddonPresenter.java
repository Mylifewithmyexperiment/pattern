package com.sterlite.dccmappfordealersterlite.activity.UnsubscribeAddon;

import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;
import com.sterlite.dccmappfordealersterlite.data.network.ApiHelper;
import com.sterlite.dccmappfordealersterlite.model.bssrest.ServiceInstanceDetail;

/**
 * Created by elitecore on 23/10/18.
 */
//public class TrackingDetailPresenter<V extends TrackingDetailContract.View> extends BasePresenter<V> implements TrackingDetailContract.Presenter<V> {

public class UnsubscribeAddonPresenter<V extends UnsubscribeAddonContract.View> extends BasePresenter<V> implements UnsubscribeAddonContract.Presenter<V> {


    @Override
    public void onAttach(V baseView) {
        super.onAttach(baseView);

    }

    @Override
    public void init(String serviceInstanceNumber) {

        DCCMDealerSterlite.getDataManager().getSIDetails(serviceInstanceNumber, new ApiHelper.OnApiCallback<ServiceInstanceDetail>() {
            @Override
            public void onSuccess(ServiceInstanceDetail baseResponse) {
                if (getView() == null)
                    return;
                getView().loadDataToView(baseResponse);

            }

            @Override
            public void onFailed(int code, String message) {
                if (getView() == null)
                    return;
                getView().hideProgressBar();

            }
        });

    }

    @Override
    public void unsubscribeAddon(String serviceInstanceNumber, String externalReferenceID) {
        DCCMDealerSterlite.getDataManager().doUnSubscribeAddOn(serviceInstanceNumber, externalReferenceID, new ApiHelper.OnApiCallback<String>() {
            @Override
            public void onSuccess(String baseResponse) {
                if (getView() == null)
                    return;
                getView().unsubscribeSuccess(baseResponse);
            }

            @Override
            public void onFailed(int code, String message) {
                if (getView() == null)
                    return;
                getView().hideProgressBar();

            }
        });
    }
}

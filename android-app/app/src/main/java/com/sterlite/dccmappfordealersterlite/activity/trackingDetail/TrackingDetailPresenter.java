package com.sterlite.dccmappfordealersterlite.activity.trackingDetail;


import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;
import com.sterlite.dccmappfordealersterlite.data.network.ApiHelper;
import com.sterlite.dccmappfordealersterlite.model.OrderTracking.OrderTrackingResponseModel;

/**
 * Created by etech3 on 27/6/18.
 */

public class TrackingDetailPresenter<V extends TrackingDetailContract.View> extends BasePresenter<V> implements TrackingDetailContract.Presenter<V> {

    // 20180830
    @Override
    public void onAttach(V baseView) {
        super.onAttach(baseView);

    }


    @Override
    public void init(String orderId) {
        if (getView() == null) return;
        getView().setUpView();
        startApiCall();
        /*if (Constants.IS_DUMMY_MODE) {
            getView().showProgressBar();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (getView() == null) return;
                    OrderDetails orderDetails = DummyLists.getOrderDetails();
                    orderDetails.setCurrentDeliveryStatus("Order is being readied for dispatch");
                    orderDetails.setArrTrackDetails(DummyLists.getTrackingDetail());
                    getView().loadDataToView(orderDetails);
                    getView().hideProgressBar();
                }
            }, 1000);
        }*/
        DCCMDealerSterlite.getDataManager().doGetOrderTrackingDetails(orderId, new ApiHelper.OnApiCallback<OrderTrackingResponseModel>() {
            @Override
            public void onSuccess(OrderTrackingResponseModel baseResponse) {
                if(getView()==null)return;
                TrackingDetailPresenter.super.onSuccess();
                getView().loadDataToView(baseResponse);
            }

            @Override
            public void onFailed(int code, String message) {
                if(getView()==null)return;
                TrackingDetailPresenter.super.onFaild(code,message);
            }
        });
    }


}

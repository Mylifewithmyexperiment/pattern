package com.sterlite.dccmappfordealersterlite.activity.trackingDetail;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;
import com.sterlite.dccmappfordealersterlite.model.OrderTracking.OrderTrackingResponseModel;

/**
 * Created by etech3 on 27/6/18.
 */

public interface TrackingDetailContract {
    interface View extends BaseContractView {

        void setUpView();

        void loadDataToView(OrderTrackingResponseModel responseModel);
    }

    interface Presenter<V extends View> extends BaseContractPresenter<V> {

        void init(String orderId);

    }
}

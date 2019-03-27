package com.sterlite.dccmappfordealersterlite.activity.Activation;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;
import com.sterlite.dccmappfordealersterlite.model.MyOrdersListing.Order;

/**
 * Created by etech3 on 27/6/18.
 */

public interface ActivationContract {
    interface View extends BaseContractView {
        void setUpView(boolean isReset);

        void loadDataToView(ArrayList<Order> list);

        void setNotRecordsFoundView(boolean isActive);

        void gotoSuccessScreen(String message);

        void updateFooterFalse();

        void showProgressBar(boolean isFullScreen);

        void hideProgressBar(boolean isFullScreen);

        void getDNNumberbyOrderApiCallSuccess(String dnNum,Order order);

    }

    interface Presenter<V extends View> extends BaseContractPresenter<V> {
        void init();

        void loadMoreRecords();

//        void getDNNumberbyOrder(final String orderNo, final ApiHelper.OnApiCallback<String> apiCallback);
        void getDNNumberbyOrder(Order orderNo,String auth);
        void getDnNumWithAuthToken(final Order order);

        void activateOrder(String orderNo);

        void reset();
    }
}

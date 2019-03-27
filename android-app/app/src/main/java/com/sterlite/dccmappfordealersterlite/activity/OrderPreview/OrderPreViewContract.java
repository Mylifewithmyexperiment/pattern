package com.sterlite.dccmappfordealersterlite.activity.OrderPreview;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;
import com.sterlite.dccmappfordealersterlite.model.OrderDetails;

public class OrderPreViewContract {
    interface View extends BaseContractView{

        void setOrderDetails(OrderDetails details);

        void gotoOtpScreen();

        void finishActivity();

    }
    interface Presenter<V extends View> extends BaseContractPresenter<V> {

        void init();

        void placeOrder();

        void reset();

    }
}

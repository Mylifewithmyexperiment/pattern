package com.sterlite.dccmappfordealersterlite.activity.OrderDetails;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;
import com.sterlite.dccmappfordealersterlite.model.OrderDetails;

public class OrderDetailsContract {
    interface View extends BaseContractView{

        void setOrderDetails(OrderDetails details);

    }
    interface Presenter<V extends View> extends BaseContractPresenter<V> {

        void init();


    }
}

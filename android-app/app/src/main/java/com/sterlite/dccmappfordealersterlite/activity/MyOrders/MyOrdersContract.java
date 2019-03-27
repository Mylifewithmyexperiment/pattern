package com.sterlite.dccmappfordealersterlite.activity.MyOrders;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;
import com.sterlite.dccmappfordealersterlite.model.MyOrdersListing.Order;

public class MyOrdersContract {
    interface View extends BaseContractView {
        void setUpView(boolean isReset);

        void loadDataToView(ArrayList<Order> list);

        void setNotRecordsFoundView(boolean isActive);

        void updateFooterFalse();
    }

    interface Presenter<V extends View> extends BaseContractPresenter<V> {
        void init();

        void loadMoreRecords();

        void reset();
    }
}

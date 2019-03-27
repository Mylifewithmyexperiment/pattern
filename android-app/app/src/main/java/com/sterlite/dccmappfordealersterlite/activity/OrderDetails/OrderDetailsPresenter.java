package com.sterlite.dccmappfordealersterlite.activity.OrderDetails;

import android.os.Handler;

import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.Utils.DummyLists;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;

public class OrderDetailsPresenter<V extends OrderDetailsContract.View> extends BasePresenter<V> implements OrderDetailsContract.Presenter<V> {
    @Override
    public void init() {
        if (Constants.IS_DUMMY_MODE) {
            if(getView()==null)
                return;
            getView().showProgressBar();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(getView()==null)
                        return;
                    getView().hideProgressBar();
                    getView().setOrderDetails(DummyLists.getOrderDetails());
                }
            },500);

        }
    }
}

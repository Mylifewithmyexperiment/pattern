package com.sterlite.dccmappfordealersterlite.activity.OrderPreview;

import android.os.Handler;

import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.Utils.DummyLists;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;

public class OrderPreviewPresenter<V extends OrderPreViewContract.View> extends BasePresenter<V> implements OrderPreViewContract.Presenter<V> {
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

    @Override
    public void placeOrder() {
        if(Constants.IS_DUMMY_MODE){
            if(getView()==null){
                return;
            }
            getView().showProgressBar();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(getView()==null)
                        return;
                    getView().hideProgressBar();
                    getView().gotoOtpScreen();
                    getView().finishActivity();
                }
            },500);
        }
    }

    @Override
    public void reset() {
        init();
    }
}

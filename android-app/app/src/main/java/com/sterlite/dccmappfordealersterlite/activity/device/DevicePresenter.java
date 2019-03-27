package com.sterlite.dccmappfordealersterlite.activity.device;

import android.os.Handler;

import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.Utils.DummyLists;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;


/**
 * Created by etech3 on 27/6/18.
 */

public class DevicePresenter<V extends DeviceContract.View> extends BasePresenter<V> implements DeviceContract.Presenter<V> {

    @Override
    public void onAttach(V baseView) {
        super.onAttach(baseView);

    }


    @Override
    public void init() {
        getView().setUpView(false);
        if (Constants.IS_DUMMY_MODE) {

            getView().showProgressBar();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (getView() == null) return;
                    getView().loadDataToView(DummyLists.getDevices());
                    getView().hideProgressBar();
                }
            }, 1000);
        }
    }

}

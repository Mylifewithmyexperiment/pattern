package com.sterlite.dccmappfordealersterlite.activity.device;


import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;
import com.sterlite.dccmappfordealersterlite.model.Device;

/**
 * Created by etech3 on 27/6/18.
 */

public interface DeviceContract {
    interface View extends BaseContractView {

        void setUpView(boolean isReset);

        void loadDataToView(ArrayList<Device> list);
    }

    interface Presenter<V extends DeviceContract.View> extends BaseContractPresenter<V> {

        void init();

    }
}

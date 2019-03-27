package com.sterlite.dccmappfordealersterlite.activity.pincode;


import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;

/**
 * Created by etech3 on 27/6/18.
 */

public interface PincodeContract {
    interface View extends BaseContractView {

        void onSuccessValidate();
        void onFailValidate();

    }

    interface Presenter<V extends View> extends BaseContractPresenter<V> {

        void doValidation(String pincode);

    }
}

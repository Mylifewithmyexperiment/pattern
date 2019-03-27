package com.sterlite.dccmappfordealersterlite.activity.InternetPackSummery;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;

/**
 * Created by etech3 on 27/6/18.
 */

public interface InternetPackSummeryContract {
    interface View extends BaseContractView {

        void gotoSuccessScreen(String message);
        void gotoFailScreen();

    }

    interface Presenter<V extends View> extends BaseContractPresenter<V> {

        void doSubscribeAddOn(String planName,String paymentMode);

        void changePlan(String planName);


    }
}

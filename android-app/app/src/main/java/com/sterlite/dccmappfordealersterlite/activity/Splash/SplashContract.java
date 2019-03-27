package com.sterlite.dccmappfordealersterlite.activity.Splash;


import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;

/**
 * Created by etech3 on 27/6/18.
 */

public interface SplashContract {
    interface View extends BaseContractView {

        void openLandingPage();

        void finishActivity();

    }

    interface Presenter<V extends View> extends BaseContractPresenter<V> {

    }
}

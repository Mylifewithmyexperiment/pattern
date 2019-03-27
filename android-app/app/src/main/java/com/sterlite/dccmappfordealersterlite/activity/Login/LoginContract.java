package com.sterlite.dccmappfordealersterlite.activity.Login;


import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;

/**
 * Created by etech3 on 27/6/18.
 */

public interface LoginContract {
    interface View extends BaseContractView {

        void onSuccessLogin();

        void finishActivity();

    }

    interface Presenter<V extends View> extends BaseContractPresenter<V> {

        void doLogin(String username, String password);

    }
}

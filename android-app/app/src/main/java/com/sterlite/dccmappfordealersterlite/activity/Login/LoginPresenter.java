package com.sterlite.dccmappfordealersterlite.activity.Login;

import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;


/**
 * Created by etech3 on 27/6/18.
 */

public class LoginPresenter<V extends LoginContract.View> extends BasePresenter<V> implements LoginContract.Presenter<V> {

    @Override
    public void doLogin(String username, String password) {
        getView().showProgressBar();
        if(Constants.IS_DUMMY_MODE){
            getView().hideProgressBar();
            getView().onSuccessLogin();
            getView().finishActivity();
        }
    }

}

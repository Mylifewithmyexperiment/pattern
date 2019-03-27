package com.sterlite.dccmappfordealersterlite.activity.SignUp;

import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;
import com.sterlite.dccmappfordealersterlite.model.User;

/**
 * Created by etech-10 on 30/8/18.
 */

public class SignUpPresenter<V extends SignUpContract.View> extends BasePresenter<V> implements SignUpContract.Presenter<V> {
    @Override
    public void doSignUp(User user) {
        if (Constants.IS_DUMMY_MODE) {
            getView().showProgressBar();
            getView().hideProgressBar();
            getView().signUpSuccessful(user);
        }
    }
}

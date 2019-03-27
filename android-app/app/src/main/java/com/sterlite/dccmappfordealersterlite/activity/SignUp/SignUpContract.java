package com.sterlite.dccmappfordealersterlite.activity.SignUp;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;
import com.sterlite.dccmappfordealersterlite.model.User;

/**
 * Created by etech-10 on 30/8/18.
 */

public interface SignUpContract  {
    interface View extends BaseContractView {

        void signUpSuccessful(User user);

    }

    interface Presenter<V extends View> extends BaseContractPresenter<V> {

        void doSignUp(User user);

    }
}

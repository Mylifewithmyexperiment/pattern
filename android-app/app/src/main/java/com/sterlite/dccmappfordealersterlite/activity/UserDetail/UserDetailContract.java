package com.sterlite.dccmappfordealersterlite.activity.UserDetail;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;
import com.sterlite.dccmappfordealersterlite.model.dto.usersignup.UserSignUpWsDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.usersignup.UserSignUpWsResponseDTO;

/**
 * Created by etech3 on 27/6/18.
 */

public interface UserDetailContract {
    interface View extends BaseContractView {

        void signUpSuccessful(UserSignUpWsResponseDTO user);

        void loadDataToView(ArrayList<String> arrayList);
    }

    interface Presenter<V extends View> extends BaseContractPresenter<V> {

        void doSignUp(UserSignUpWsDTO user);

        void init();
    }
}

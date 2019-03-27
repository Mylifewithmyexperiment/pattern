package com.sterlite.dccmappfordealersterlite.activity.UserDetail;


import android.util.Log;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.Utils.DummyLists;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;
import com.sterlite.dccmappfordealersterlite.data.network.AppApiHelper2;
import com.sterlite.dccmappfordealersterlite.model.dto.usersignup.UserSignUpWsDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.usersignup.UserSignUpWsResponseDTO;

/**
 * Created by etech3 on 27/6/18.
 */

public class UserDetailPresenter<V extends UserDetailContract.View> extends BasePresenter<V> implements UserDetailContract.Presenter<V> {

    // 20180830
    @Override
    public void onAttach(V baseView) {
        super.onAttach(baseView);

    }

    @Override
    public void init() {

        if (Constants.IS_DUMMY_MODE) {
            getView().loadDataToView(DummyLists.getCitys());
        }
    }


    @Override
    public void doSignUp(UserSignUpWsDTO user) {
//        if (Constants.IS_DUMMY_MODE) {
//            getView().showProgressBar();
//            getView().hideProgressBar();
//            getView().signUpSuccessful(user);
//        }
        AppApiHelper2.getWebServices().doSignUp(user, new Callback<UserSignUpWsResponseDTO>() {

            @Override
            public void success(UserSignUpWsResponseDTO userSignUpWsResponseDTO, Response response) {
                Log.e("userSignUpWsResponseDTO"," "+userSignUpWsResponseDTO);
                if(getView()==null)return;
                getView().hideProgressBar();
                getView().signUpSuccessful(userSignUpWsResponseDTO);
            }

            @Override
            public void failure(RetrofitError error) {
                if(getView()==null)return;
                getView().hideProgressBar();
                Log.e("userSignUpWsResponseDTO"," "+error);
            }
        });
    }
}

package com.sterlite.dccmappfordealersterlite.activity.LandingPage;


import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;

/**
 * Created by etech3 on 27/6/18.
 */

public class LandingPagePresenter<V extends LandingPageContract.View> extends BasePresenter<V> implements LandingPageContract.Presenter<V> {

    // 20180830
    @Override
    public void onAttach(V baseView) {
        super.onAttach(baseView);

    }


    @Override
    public void init() {
        getView().setUpView(false);
        if (Constants.IS_DUMMY_MODE) {
            getView().loadDataToView(null);
        }
    }


}

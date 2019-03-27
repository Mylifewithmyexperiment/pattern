package com.sterlite.dccmappfordealersterlite.activity.MakeMyPlanDetail;


import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;

/**
 * Created by etech3 on 27/6/18.
 */

public class MakeMyPlanDetailPresenter<V extends MakeMyPlanDetailContract.View> extends BasePresenter<V> implements MakeMyPlanDetailContract.Presenter<V> {

    // 20180830
    @Override
    public void onAttach(V baseView) {
        super.onAttach(baseView);

    }


    @Override
    public void init() {
        if (getView() == null) return;
        getView().setUpView();
        if (Constants.IS_DUMMY_MODE) {
            getView().showProgressBar();
            getView().loadDataToView();
            getView().hideProgressBar();

        }
    }
}

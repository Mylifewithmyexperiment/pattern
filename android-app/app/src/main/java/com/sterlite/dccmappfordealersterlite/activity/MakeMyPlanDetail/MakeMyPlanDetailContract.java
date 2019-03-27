package com.sterlite.dccmappfordealersterlite.activity.MakeMyPlanDetail;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;

/**
 * Created by etech3 on 27/6/18.
 */

public interface MakeMyPlanDetailContract {
    interface View extends BaseContractView {
        void setUpView();

        void loadDataToView();

    }

    interface Presenter<V extends View> extends BaseContractPresenter<V> {

        void init();
    }
}

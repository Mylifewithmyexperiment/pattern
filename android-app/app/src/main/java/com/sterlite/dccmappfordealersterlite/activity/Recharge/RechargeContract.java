package com.sterlite.dccmappfordealersterlite.activity.Recharge;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;
import com.sterlite.dccmappfordealersterlite.model.ReccomandedPlanPOJO;

/**
 * Created by etech3 on 27/6/18.
 */

public interface RechargeContract {
    interface View extends BaseContractView {

        public void setUpView(boolean isReset);
        public void loadDataToView(ArrayList<ReccomandedPlanPOJO> reccomandedPlan);
        public void showPopUp(String title,String message);

    }

    interface Presenter<V extends View> extends BaseContractPresenter<V> {

        void init(Boolean pretopost);

    }
}

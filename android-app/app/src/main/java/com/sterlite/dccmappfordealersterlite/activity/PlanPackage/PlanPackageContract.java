package com.sterlite.dccmappfordealersterlite.activity.PlanPackage;


import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;
import com.sterlite.dccmappfordealersterlite.model.PlanPackage;

/**
 * Created by etech3 on 27/6/18.
 */

public interface PlanPackageContract {
    interface View extends BaseContractView {

        void setUpView(boolean isReset);

        void loadDataToView(ArrayList<PlanPackage> list);
    }

    interface Presenter<V extends PlanPackageContract.View> extends BaseContractPresenter<V> {

        void init(String planType);

    }
}

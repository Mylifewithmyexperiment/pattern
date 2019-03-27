package com.sterlite.dccmappfordealersterlite.activity.MakeMyPlanList;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;
import com.sterlite.dccmappfordealersterlite.model.MakeMyPlanFilter.FilterContainer;
import com.sterlite.dccmappfordealersterlite.model.MakeMyPlanModel.MakeMyPlanItems;

/**
 * Created by etech3 on 27/6/18.
 */

public interface MakeMyPlanListContract {
    interface View extends BaseContractView {

        void setUpView();

        void loadDataToView(ArrayList<MakeMyPlanItems> list);

        void loadFilterDataToView(ArrayList<MakeMyPlanItems> list);

        void loadFilterContainerToView(ArrayList<FilterContainer> filterContainers);
    }

    interface Presenter<V extends View> extends BaseContractPresenter<V> {

        void init();

        void applyFilter(int min,int max);

        void applyReset();

    }
}

package com.sterlite.dccmappfordealersterlite.activity.ServiceDetail;


import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;
import com.sterlite.dccmappfordealersterlite.model.Store;

/**
 * Created by etech3 on 27/6/18.
 */

public interface ServiceDetailContract {
    interface View extends BaseContractView {

        void setUpView();

        void loadDataToView(ArrayList<Store> list);

        void setNotRecordsFoundView(boolean isActive);

    }

    interface Presenter<V extends View> extends BaseContractPresenter<V> {

        void initReset();

        void loadMoreRecords();


    }
}

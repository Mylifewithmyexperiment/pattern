package com.sterlite.dccmappfordealersterlite.activity.dashboard;


import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;

/**
 * Created by etech3 on 27/6/18.
 */

public interface DashboardContract {
    interface View extends BaseContractView {

        void logoutCompleted();
        void showAd();

    }

    interface Presenter<V extends View> extends BaseContractPresenter<V> {

        void doLogout();
        void startSyncDataService();
    }
}

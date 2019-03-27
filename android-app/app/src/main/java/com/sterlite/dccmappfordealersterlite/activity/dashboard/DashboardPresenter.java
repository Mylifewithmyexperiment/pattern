package com.sterlite.dccmappfordealersterlite.activity.dashboard;


import com.sterlite.dccmappfordealersterlite.base.BasePresenter;

/**
 * Created by etech3 on 27/6/18.
 */

public class DashboardPresenter<V extends com.sterlite.dccmappfordealersterlite.activity.dashboard.DashboardContract.View> extends BasePresenter<V> implements com.sterlite.dccmappfordealersterlite.activity.dashboard.DashboardContract.Presenter<V> {

    //HashMap<String, String> invList;

    @Override
    public void onAttach(V baseView) {
        super.onAttach(baseView);
    }


    @Override
    public void doLogout() {

    }

    @Override
    public void startSyncDataService() {

    }

    private void callAdvertiesmentApi() {

    }


}

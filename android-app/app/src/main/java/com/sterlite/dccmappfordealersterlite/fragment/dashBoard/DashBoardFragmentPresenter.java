package com.sterlite.dccmappfordealersterlite.fragment.dashBoard;

import com.sterlite.dccmappfordealersterlite.base.BasePresenter;

/**
 * Created by etech3 on 27/6/18.
 */

public class DashBoardFragmentPresenter<V extends DashBoardFragmentContract.View> extends BasePresenter<V> implements DashBoardFragmentContract.Presenter<V> {


    @Override
    public void onAttach(V baseView) {
        super.onAttach(baseView);
    }

}

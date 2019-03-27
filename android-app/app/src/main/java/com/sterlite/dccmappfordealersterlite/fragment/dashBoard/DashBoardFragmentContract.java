package com.sterlite.dccmappfordealersterlite.fragment.dashBoard;


import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;

/**
 * Created by etech3 on 27/6/18.
 */

public interface DashBoardFragmentContract {
    interface View extends BaseContractView {


    }

    interface Presenter<V extends View> extends BaseContractPresenter<V> {


    }
}

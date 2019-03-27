package com.sterlite.dccmappfordealersterlite.activity.LandingPage;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;
import com.sterlite.dccmappfordealersterlite.model.LandingPageItem;

/**
 * Created by etech3 on 27/6/18.
 */

public interface LandingPageContract {
    interface View extends BaseContractView {

        void setUpView(boolean isReset);

        void loadDataToView(ArrayList<LandingPageItem> list);
    }

    interface Presenter<V extends View> extends BaseContractPresenter<V> {

        void init();

    }
}

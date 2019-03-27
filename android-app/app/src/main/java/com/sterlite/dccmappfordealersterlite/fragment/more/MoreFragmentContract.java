package com.sterlite.dccmappfordealersterlite.fragment.more;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;
import com.sterlite.dccmappfordealersterlite.model.MoreItem;

/**
 * Created by etech3 on 27/6/18.
 */

public interface MoreFragmentContract {
    interface View extends BaseContractView {

        void setUpView(boolean isReset);

        void loadDataToView(ArrayList<MoreItem> list);
    }

    interface Presenter<V extends View> extends BaseContractPresenter<V> {
        void init();

    }
}

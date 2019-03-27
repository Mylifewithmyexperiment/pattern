package com.sterlite.dccmappfordealersterlite.fragment.more;


import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;

/**
 * Created by etech3 on 27/6/18.
 */

public class MoreFragmentPresenter<V extends MoreFragmentContract.View> extends BasePresenter<V> implements MoreFragmentContract.Presenter<V> {


    @Override
    public void onAttach(V baseView) {
        super.onAttach(baseView);

    }


    @Override
    public void init() {
        getView().setUpView(false);
        if (Constants.IS_DUMMY_MODE) {
            getView().loadDataToView(null);
        }
    }



}

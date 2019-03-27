package com.sterlite.dccmappfordealersterlite.activity.Recharge;


import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.DummyLists;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;

/**
 * Created by etech3 on 27/6/18.
 */

public class RechargePresenter<V extends RechargeContract.View> extends BasePresenter<V> implements RechargeContract.Presenter<V> {

    // 20180830
    @Override
    public void onAttach(V baseView) {
        super.onAttach(baseView);

    }


 /*   @Override
    public void init() {
        if (getView() == null)
            return;

        getView().setUpView(false);


        getView().loadDataToView(DummyLists.getRecommandedPlanPOJOS());

    }*/
 @Override
 public void init(Boolean pretopost) {
     if (getView() == null)
         return;

     getView().setUpView(false);

     getView().loadDataToView(DummyLists.getRecommandedPlanPOJOS());
     if (pretopost) {
         if (DCCMDealerSterlite.getDataManager().getBoolean(AppPreferencesHelper.PREF_IS_USER_LOGGED_IN, false))
             getView().showPopUp(DCCMDealerSterlite.appContext.getString(R.string.title_special_offer), DCCMDealerSterlite.appContext.getString(R.string.msg_we_have_extra_benefits));
     }
     }
}

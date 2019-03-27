package com.sterlite.dccmappfordealersterlite.activity.RechargeSummery;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;
import com.sterlite.dccmappfordealersterlite.model.Recharge;
import com.sterlite.dccmappfordealersterlite.model.dto.recharge.RechargeDetailResponseData;

/**
 * Created by etech3 on 27/6/18.
 */

public interface RechargeSummeryContract {
    interface View extends BaseContractView {
        void rechargeApiCallSuccess(RechargeDetailResponseData rechargeDetailResponseData);
    }

    interface Presenter<V extends View> extends BaseContractPresenter<V> {
        void doRecharge(Recharge recharge);
    }
}

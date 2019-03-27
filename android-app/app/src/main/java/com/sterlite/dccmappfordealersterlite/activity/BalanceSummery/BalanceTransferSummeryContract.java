package com.sterlite.dccmappfordealersterlite.activity.BalanceSummery;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;
import com.sterlite.dccmappfordealersterlite.model.Recharge;
import com.sterlite.dccmappfordealersterlite.model.dto.transferbalance.TransferBalanceResponseData;

/**
 * Created by etech3 on 27/6/18.
 */

public interface BalanceTransferSummeryContract {
    interface View extends BaseContractView {

        void balanceTransferApiCallSuccess(TransferBalanceResponseData transferBalanceResponseData);

    }

    interface Presenter<V extends View> extends BaseContractPresenter<V> {

        void balanceTransfer(Recharge transfer);
    }
}

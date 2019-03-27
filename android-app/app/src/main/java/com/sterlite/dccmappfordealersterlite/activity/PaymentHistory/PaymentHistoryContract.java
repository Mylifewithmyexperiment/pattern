package com.sterlite.dccmappfordealersterlite.activity.PaymentHistory;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;
import com.sterlite.dccmappfordealersterlite.model.bssrest.PaymentHistory.PaymentDetailData;

/**
 * Created by etech3 on 27/6/18.
 */

public interface PaymentHistoryContract {
    interface View extends BaseContractView {
        void setUpView(boolean isReset);

        void getPaymentDetailDatas(ArrayList<PaymentDetailData> list);

        void loadDataToView(ArrayList<PaymentDetailData> list);

        void setNotRecordsFoundView(boolean isActive);

        void updateFooterFalse();

        void showProgressBar(boolean isFullScreen);

        void hideProgressBar(boolean isFullScreen);

    }

    interface Presenter<V extends View> extends BaseContractPresenter<V> {
        void init();

        void loadMoreRecords(String startDate, String endDate);

        void reset(String startDate, String endDate);
    }
}

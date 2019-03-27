package com.sterlite.dccmappfordealersterlite.activity.invoice;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;
import com.sterlite.dccmappfordealersterlite.model.bssrest.BillingDetailData;

/**
 * Created by elitecore on 30/10/18.
 */

public interface InvoiceContract {

    interface View extends BaseContractView {
        void setUpView(boolean isReset);

        void loadDataToView(ArrayList<BillingDetailData> list);

        void setNotRecordsFoundView(boolean isActive);

        void updateFooterFalse();

        void showProgressBar(boolean isFullScreen);

        void hideProgressBar(boolean isFullScreen);


    }

    interface Presenter<V extends InvoiceContract.View> extends BaseContractPresenter<V> {

            void getBill(String accountNumber);
            void DownloadBill(String billNumber);
            void loadMoreRecords(String startDate, String endDate);
            void reset(String startDate, String endDate);

    }
}

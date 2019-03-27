package com.sterlite.dccmappfordealersterlite.activity.TicketView;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;
import com.sterlite.dccmappfordealersterlite.model.Ticket;

import java.util.ArrayList;

/**
 * Created by elitecore on 15/10/18.
 */

public class ViewTicketContract {
    interface View extends BaseContractView {
        void setUpView(boolean isReset);

        void loadDataToView(ArrayList<Ticket> list);

        void setNotRecordsFoundView(boolean isActive);

        void updateFooterFalse();

        void showProgressBar(boolean isFullScreen);

        void hideProgressBar(boolean isFullScreen);


    }

    interface Presenter<V extends ViewTicketContract.View> extends BaseContractPresenter<V> {

        void getTicket(String accountNumber);
        void reset(String startDate, String endDate);

    }
}

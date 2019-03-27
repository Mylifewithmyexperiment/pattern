package com.sterlite.dccmappfordealersterlite.activity.TicketCreate;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;
import com.sterlite.dccmappfordealersterlite.model.Ticket;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by elitecore on 15/10/18.
 */

public class TicketContract{
    interface View extends BaseContractView {
        void loadDataToView(final ArrayList<String> category,final HashMap<String,ArrayList<String>> subCategory);
        void successCreateTicket(Ticket ticketResponse);
    }

    interface Presenter<V extends View> extends BaseContractPresenter<V> {
        void init();
        void createTicket(Ticket ticket);
    }
}

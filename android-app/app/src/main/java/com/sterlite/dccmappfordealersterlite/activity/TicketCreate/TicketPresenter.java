package com.sterlite.dccmappfordealersterlite.activity.TicketCreate;

import android.util.Log;

import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.Utils.DummyLists;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;
import com.sterlite.dccmappfordealersterlite.data.AppDataManager;
import com.sterlite.dccmappfordealersterlite.data.network.ApiHelper;
import com.sterlite.dccmappfordealersterlite.data.network.DCCMWebServices;
import com.sterlite.dccmappfordealersterlite.model.Ticket;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by elitecore on 15/10/18.
 */

public class TicketPresenter<V extends TicketContract.View> extends BasePresenter<V> implements TicketContract.Presenter<V> {
    static int cnt = 0;
    HashMap<String,ArrayList<String>> hm;
    @Override
    public void onAttach(V baseView) {
        super.onAttach(baseView);
    }

    @Override
    public void init() {
        if (Constants.IS_DUMMY_MODE) {
            cnt = 0;
            hm = new HashMap<>();
            DCCMDealerSterlite.getDataManager().getTicketCategory(new ApiHelper.OnApiCallback< ArrayList<String>>() {
                @Override
                public void onSuccess(final ArrayList<String> categoryBaseResponse) {
                    if(categoryBaseResponse != null &&
                            !categoryBaseResponse.isEmpty()){
                        for (final String key : categoryBaseResponse){
                            Log.e("getTicketCategory :","key :"+key);
                            DCCMDealerSterlite.getDataManager().getTicketSubCategory(key,new ApiHelper.OnApiCallback<ArrayList<String>>() {
                                @Override
                                public void onSuccess(ArrayList<String> baseResponse) {
                                    cnt++;
                                    Log.e("TicketPresenter :" ," cnt "+cnt);
                                    hm.put(key,baseResponse);
                                    for(String arr : hm.keySet()){
                                        Log.e("key :" + arr," val arr"+hm.get(arr));
                                    }
                                    if(categoryBaseResponse.size() == cnt ){
                                        Log.e("getTicketSubCategory :","categoryBaseResponse.size() == cnt  :"+cnt);
                                        for(String arr : hm.keySet()){
                                            Log.e("final key :" + arr," val arr"+hm.get(arr));
                                        }
                                        getView().loadDataToView(categoryBaseResponse,hm);
                                    }
                                }

                                @Override
                                public void onFailed(int code, String message) {
                                    Log.e("TicketPresenter","getTicketSubCategory : fail");
                                }
                            });
                        }
                    }
                }

                @Override
                public void onFailed(int code, String message) {
                    Log.e("TicketPresenter","getTicketCategory : fail");
                }
            });

        }
    }


    @Override
    public void createTicket(Ticket ticket) {
        if(getView()!=null)getView().hideProgressBar();
        Log.e("createTicket "," "+ticket);
        DCCMDealerSterlite.getDataManager().createTicket(ticket, new ApiHelper.OnApiCallback<Ticket>() {

            @Override
            public void onSuccess(Ticket ticketResponse) {
                Log.e("base1", ticketResponse + "");
                if (getView() == null) return;
                if (ticketResponse != null) {
                    getView().successCreateTicket(ticketResponse);
                }
            }

            @Override
            public void onFailed(int code, String message) {
                if (getView() == null) return;
                TicketPresenter.super.onFaild(code, message);
            }
        });
    }
}

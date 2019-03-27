package com.sterlite.dccmappfordealersterlite.activity.TicketView;

import android.util.Log;

import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.activity.invoice.InvoiceContract;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;
import com.sterlite.dccmappfordealersterlite.data.network.ApiHelper;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.model.Ticket;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by elitecore on 15/10/18.
 */

public class ViewTicketPresenter<V extends ViewTicketContract.View> extends BasePresenter<V> implements ViewTicketContract.Presenter<V> {
    @Override
    public void getTicket(String accountNumber) {
        try {
            String customerUID= DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID,null);
            DCCMDealerSterlite.getDataManager().getTicketByCustomer(customerUID, new ApiHelper.OnApiCallback<ArrayList<Ticket>>() {
                @Override

                public void onSuccess(ArrayList<Ticket> list) {
                    if (getView() == null) return;
                    getView().setNotRecordsFoundView(false);
                    ViewTicketPresenter.super.onSuccess();
                    if (list.size() > 0) {
                        getView().loadDataToView(list);
                    } else {
                        getView().setNotRecordsFoundView(true);
                        return;
                    }
                }

                @Override
                public void onFailed(int code, String message) {

                }
            });

//            ArrayList<Ticket> list;
//            String strJson = AppUtils.loadJSONFromAsset("ticket.json");
//            JSONObject baseResponse = new JSONObject(strJson);
//            Log.e("base1", baseResponse + "");
//            if (getView() == null) return;
//            if (baseResponse != null) {
//                JSONArray array = baseResponse.getJSONArray("tickets");
//                ObjectMapper mapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//                list = mapper.readValue(array.toString(), new TypeReference<ArrayList<Ticket>>() {});
//
//
//
//                getView().setNotRecordsFoundView(false);
//                ViewTicketPresenter.super.onSuccess();
//                if (list.size() >= 1) {
//                    getView().loadDataToView(list);
//                } else {
//                    getView().setNotRecordsFoundView(true);
//                    return;
//                }
//            }
        }catch(Exception e){
            Log.e("getTicket "," "+e);
        }
    }


    @Override
    public void reset(String startDate, String endDate) {

    }
}

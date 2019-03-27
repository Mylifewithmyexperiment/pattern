package com.sterlite.dccmappfordealersterlite.activity.PaymentHistory;


import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;
import com.sterlite.dccmappfordealersterlite.data.network.ApiHelper;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.model.bssrest.PaymentHistory.PaymentDetailData;
import com.sterlite.dccmappfordealersterlite.model.bssrest.PaymentHistory.SearchPaymentResponseData;

/**
 * Created by etech3 on 27/6/18.
 */

public class PaymentHistoryPresenter<V extends PaymentHistoryContract.View> extends BasePresenter<V> implements PaymentHistoryContract.Presenter<V> {
    private ArrayList<PaymentDetailData> paymentDetailDatas = new ArrayList<>();

   @Override
    public void onAttach(V baseView) {
        super.onAttach(baseView);
    }

    @Override
    public void init() {
        getView().setUpView(false);

        startApiCall();
        Map<String,String> billDetailMap = DCCMDealerSterlite.getDataManager().loadMap(AppPreferencesHelper.BILLING_DETAIL_MAP);
        String serviceInstance= DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.PREF_SUBSCRIPTION_NUMBER,null);
        Log.e("billAccNumber",billDetailMap.get(serviceInstance)+"");
        String accNo = billDetailMap.get(serviceInstance)+"";

        DCCMDealerSterlite.getDataManager().getPaymentHistory(accNo,new ApiHelper.OnApiCallback<SearchPaymentResponseData>() {
            @Override
            public void onSuccess(SearchPaymentResponseData baseResponse) {
                if (getView() == null) return;
                if (baseResponse != null) {
                    getView().setNotRecordsFoundView(false);
                    PaymentHistoryPresenter.super.onSuccess();
                    if (baseResponse!= null && baseResponse.getPaymentDetailDatas()!=null && baseResponse.getPaymentDetailDatas().size() >= 0 ) {
                        getView().getPaymentDetailDatas((ArrayList<PaymentDetailData>) baseResponse.getPaymentDetailDatas());
                        paymentDetailDatas = (ArrayList<PaymentDetailData>) baseResponse.getPaymentDetailDatas();
//                        getView().loadDataToView((ArrayList<PaymentDetailData>) baseResponse.getPaymentDetailDatas());
                    } else {
                        getView().setNotRecordsFoundView(true);
                        return;
                    }
                }
            }

            @Override
            public void onFailed(int code, String message) {
                if (getView() == null) return;
                PaymentHistoryPresenter.super.onFaild(code, message);
            }
        });


//        loadMoreRecords(startDate,endDate);
    }


    @Override
    public void reset(String sdate,String edate) {
        getView().setUpView(true);
        loadMoreRecords(sdate,edate);
    }


    @Override
    public void loadMoreRecords(String startDate,String endDate) {
       try {
           String pattern = "yyyy-MM-dd";
           SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
           long sdate = simpleDateFormat.parse(startDate).getTime();
           long edate = simpleDateFormat.parse(endDate).getTime();

           AppUtils.errorLog("PaymentHistory" , "sdate "+sdate);
           AppUtils.errorLog("PaymentHistory" , "edate "+edate);



           ArrayList<PaymentDetailData> datas = new ArrayList<>();
           for (PaymentDetailData data:paymentDetailDatas) {
               AppUtils.errorLog("PaymentHistory" , "compare "+data.getPaymentDate());
               if(sdate<data.getPaymentDate() && edate>data.getPaymentDate()){
                   datas.add(data);
               }
           }
           getView().loadDataToView(datas);
//           if(datas.isEmpty()){
//               getView().setNotRecordsFoundView(true);
//           }else {
//               getView().setNotRecordsFoundView(false);
//           }
       }catch (Exception e){
           AppUtils.errorLog("PaymentHistory" , "error "+e);
       }
    }

}

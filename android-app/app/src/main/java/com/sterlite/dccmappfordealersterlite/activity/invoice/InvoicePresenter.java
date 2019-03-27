package com.sterlite.dccmappfordealersterlite.activity.invoice;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;
import com.sterlite.dccmappfordealersterlite.data.network.ApiHelper;
import com.sterlite.dccmappfordealersterlite.model.bssrest.BillingDetailData;

/**
 * Created by elitecore on 30/10/18.
 */

public class InvoicePresenter<V extends InvoiceContract.View> extends BasePresenter<V> implements InvoiceContract.Presenter<V> {

    private ArrayList<BillingDetailData> billingDetailData = new ArrayList<>();

    @Override
    public void onAttach(V baseView) {
        super.onAttach(baseView);

    }

    @Override
    public void getBill(String accountNumber) {
        Log.e("billingAccountNumber",accountNumber+"");
        if (accountNumber!=null) {
            DCCMDealerSterlite.getDataManager().getBillDetails(accountNumber, new ApiHelper.OnApiCallback<BillingDetailData[]>() {
                @Override
                public void onSuccess(BillingDetailData[] baseResponse) {
                    Log.e("base1", baseResponse + "");
                    if (getView() == null) return;
                    if (baseResponse != null) {

                        getView().setNotRecordsFoundView(false);
                        InvoicePresenter.super.onSuccess();
                        if (baseResponse.length >= 1) {
                            //  billingDetailData=baseResponse;
                            billingDetailData = new ArrayList<>(Arrays.asList(baseResponse));

                            getView().loadDataToView(billingDetailData);
                        } else {
                            getView().setNotRecordsFoundView(true);
                            return;
                        }
                    }

                }

                @Override
                public void onFailed(int code, String message) {
                    if (getView() == null) return;
                    InvoicePresenter.super.onFaild(code, message);
                    getView().setNotRecordsFoundView(true);

                }
            });
        }
        else
            getView().setNotRecordsFoundView(true);

    }

    @Override
    public void DownloadBill(String billNumber) {
        DCCMDealerSterlite.getDataManager().downloadBill(billNumber, new ApiHelper.OnApiCallback<String>() {
            @Override
            public void onSuccess(String baseResponse) {
                Log.e("base2",baseResponse+" ");

            }

            @Override
            public void onFailed(int code, String message) {

            }
        });
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

            AppUtils.errorLog("BillData" , "sdate "+sdate);
            AppUtils.errorLog("BillData" , "edate "+edate);



            ArrayList<BillingDetailData> datas = new ArrayList<>();
            for (BillingDetailData data:billingDetailData) {
                AppUtils.errorLog("BillingData" , "compare "+data.getBillDate());
                if(sdate<(Long.parseLong(data.getBillDate())) && edate>(Long.parseLong(data.getBillDate()))){
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
            AppUtils.errorLog("BillingData" , "error "+e);
        }
    }

}

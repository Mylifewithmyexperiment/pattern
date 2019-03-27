package com.sterlite.dccmappfordealersterlite.activity.ServiceDetail;

import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;
import com.sterlite.dccmappfordealersterlite.data.network.ApiHelper;
import com.sterlite.dccmappfordealersterlite.model.Store;


/**
 * Created by etech3 on 27/6/18.
 */

public class ServiceDetailPresenter<V extends ServiceDetailContract.View> extends BasePresenter<V> implements ServiceDetailContract.Presenter<V> {


    @Override
    public void onAttach(V baseView) {
        super.onAttach(baseView);
    }


    @Override
    public void initReset() {
        if (getView() == null) return;
        getView().setUpView();
        loadMoreRecords();
    }


    @Override
    public void loadMoreRecords() {
        if (getView() == null) return;

        startApiCall();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getView() == null) return;
               /* getView().loadDataToView(DummyLists.getStoreList());
                getView().hideProgressBar();*/
                findStore();
            }
        }, 1000);
//            DCCMDealerSterlite.getDataManager().doGetDashBoardIncident(pagging, new ApiHelper.OnApiCallback<Pagging>() {
//                @Override
//                public void onSuccess(Pagging baseResponse) {
//                    if (getView() == null) return;
//                    ServiceDetailPresenter.super.onSuccess();
//                    pagging = baseResponse;
//
//                    if (pagging.getPageNumber() == 1) {
//                        if (pagging.getArrList().size() > 0) {
//                            getView().loadDataToView(pagging.getArrList());
//                        } else {
//                            getView().setNotRecordsFoundView(true);
//                        }
//                    }
//
//                    if (pagging.getArrList().size() > 0) {
//                        getView().loadDataToView(pagging.getArrList());
//                    }
//
//                }
//
//                @Override
//                public void onFailed(int code, String message) {
//                    if (pagging.getPageNumber() == 1) {
//                        ServiceDetailPresenter.super.onFailed(code, message, false, true);
//                    } else {
//                        ServiceDetailPresenter.super.onFailed(code, message, true, false);
//                    }
//                }
//            });

    }










    public void findStore() {

       // startApiCall();

          String pincode= DCCMDealerSterlite.getDataManager().get(Constants.PIN_CODE,null);
        DCCMDealerSterlite.getDataManager().findStore(pincode, new ApiHelper.OnApiCallback<ArrayList<Store>>() {
            @Override
            public void onSuccess(ArrayList<Store> baseResponse) {
                if (getView() == null)
                    return;
                if (baseResponse != null) {
                    Log.e("findStore", baseResponse.toString());
                   // dccmStore=baseResponse;
                    getView().loadDataToView(baseResponse);
                    getView().hideProgressBar();
                }

            }

            @Override
            public void onFailed(int code, String message) {
                if (getView() == null)
                    return;
                getView().hideProgressBar();

            }
        });

    }




}

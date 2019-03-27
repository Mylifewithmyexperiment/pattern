package com.sterlite.dccmappfordealersterlite.activity.MyOrders;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;
import com.sterlite.dccmappfordealersterlite.data.network.ApiHelper;
import com.sterlite.dccmappfordealersterlite.data.network.model.Pagging;
import com.sterlite.dccmappfordealersterlite.model.MyOrdersListing.MyOrdersListResponseModel;
import com.sterlite.dccmappfordealersterlite.model.MyOrdersListing.Order;

public class MyOrdersPresenter<V extends MyOrdersContract.View> extends BasePresenter<V> implements MyOrdersContract.Presenter<V> {
    Pagging<Order> pagging;

    //dummy mode variables
    /*boolean isFirstPageLoaded = false;
    boolean isSecondPageLoaded = false;*/


    @Override
    public void onAttach(V baseView) {
        super.onAttach(baseView);
        pagging = new Pagging<>();
    }

    @Override
    public void init() {
        getView().setUpView(false);
        loadMoreRecords();
    }


    @Override
    public void reset() {
        getView().setUpView(true);
        clearPagging();
        loadMoreRecords();

    }

    private void clearPagging() {
        pagging = new Pagging<>();
    }

    @Override
    public void loadMoreRecords() {
        if (pagging.isHasMore()) {
            pagging.setHasMore(false);
            startApiCall();
            DCCMDealerSterlite.getDataManager().getMyOrders(new ApiHelper.OnApiCallback<MyOrdersListResponseModel>() {
                @Override
                public void onSuccess(MyOrdersListResponseModel baseResponse) {
                    if (getView() == null) return;
                    if (baseResponse != null) {
                        getView().setNotRecordsFoundView(false);
                        MyOrdersPresenter.super.onSuccess();
                        if (baseResponse.getOrders() != null && baseResponse.getOrders().size() > 0) {
                            getView().loadDataToView((ArrayList<Order>) baseResponse.getOrders());
                        } else {
                            getView().setNotRecordsFoundView(true);
                            return;
                        }
                    }
                }

                @Override
                public void onFailed(int code, String message) {
                    if (getView() == null) return;
                    MyOrdersPresenter.super.onFaild(code, message);
                }
            });
                /*new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(getView()==null)
                                return;

                        Pagging<Order> baseResponse = null;
                        if (!isFirstPageLoaded) {
//                    baseResponse= DummyLists.getNoRecordsForOrder(1);
//                    baseResponse=DummyLists.getTenRecords(1,true);
                            baseResponse = DummyLists.getTenRecords(1, true,true);
                        } else if (!isSecondPageLoaded) {
//                    baseResponse=DummyLists.getTenRecords(2,true);
                    baseResponse=DummyLists.getTenRecords(2,true,true);
                        } else {
                    baseResponse= DummyLists.getTenRecords(3,false,true);
                        }
                        if (baseResponse != null) {
                            MyOrdersPresenter.super.onSuccess();
                            pagging = baseResponse;

                            if (pagging.getPageNumber() == 1) {
                                isFirstPageLoaded = true;
                                if (pagging.getArrList().size() > 0) {
//                                    getView().loadDataToView(pagging.getArrList());
                                } else {
                                    getView().setNotRecordsFoundView(true);
                                    return;
                                }
                            }
                            if (pagging.getPageNumber() == 2) {
                                isSecondPageLoaded = true;
                            }

                            if (pagging.getArrList().size() > 0) {
                                getView().loadDataToView(pagging.getArrList());
                            }
                            if(!pagging.isHasMore()){
                            getView().updateFooterFalse();
                            }
                        } else {
                            MyOrdersPresenter.super.onFaild(Constants.FAIL_CODE,"error occurred");
                        }
                    }
                },2000);*/

        }
    }

}

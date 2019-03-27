package com.sterlite.dccmappfordealersterlite.activity.SearchCustomer;

import android.util.Log;

import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;
import com.sterlite.dccmappfordealersterlite.data.network.ApiHelper;
import com.sterlite.dccmappfordealersterlite.model.CustomerListWsDTO;

/**
 * Created by elitecore on 24/12/18.
 */

public class SearchCustomerPresenter<V extends SearchCustomerContract.View> extends BasePresenter<V> implements SearchCustomerContract.Presenter<V>  {


    @Override
    public void onAttach(V baseView) {
        super.onAttach(baseView);

    }
    @Override
    public void searchCustomer(String dealerID) {

            DCCMDealerSterlite.getDataManager().findCustomerByDealerId(dealerID, new ApiHelper.OnApiCallback<CustomerListWsDTO>() {
                @Override
                public void onSuccess(CustomerListWsDTO baseResponse) {
                   if (getView() == null)
                        return;
                    Log.e("baseResponse::",baseResponse.toString());

                    if (baseResponse.getLstCustomer()!=null && baseResponse.getLstCustomer().size()>0)
                        getView().loadDataToView(baseResponse.getLstCustomer());
                    else
                        getView().onFailSearchCustomer();
                //  getView().onSuccessSearchCustomer(baseResponse);
                }

                @Override
                public void onFailed(int code, String message) {
                    if (getView() == null)
                        return;
                    getView().onFailSearchCustomer();
                }
            });
        }


}

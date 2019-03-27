package com.sterlite.dccmappfordealersterlite.activity.SearchCustomer;

import com.sterlite.dccmappfordealersterlite.activity.RechargeSummery.RechargeSummeryContract;
import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;
import com.sterlite.dccmappfordealersterlite.model.CustomerListWsDTO;
import com.sterlite.dccmappfordealersterlite.model.CustomerWsDTO;
import com.sterlite.dccmappfordealersterlite.model.Recharge;
import com.sterlite.dccmappfordealersterlite.model.bssrest.BillingDetailData;
import com.sterlite.dccmappfordealersterlite.model.dto.recharge.RechargeDetailResponseData;

import java.util.ArrayList;

/**
 * Created by elitecore on 24/12/18.
 */

    public interface SearchCustomerContract {
        interface View extends BaseContractView {
            void onSuccessSearchCustomer(CustomerListWsDTO customerListWsDTO);
            void onFailSearchCustomer();
            void setUpView(boolean isReset);
            void loadDataToView(ArrayList<CustomerWsDTO> list);
            void setNotRecordsFoundView(boolean isActive);
            void updateFooterFalse();
            void showProgressBar(boolean isFullScreen);
            void hideProgressBar(boolean isFullScreen);


        }

        interface Presenter<V extends com.sterlite.dccmappfordealersterlite.activity.SearchCustomer.SearchCustomerContract.View> extends BaseContractPresenter<V> {
            void searchCustomer(String dealerID);
        }
    }
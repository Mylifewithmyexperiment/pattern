package com.sterlite.dccmappfordealersterlite.activity.loginnew;


import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;
import com.sterlite.dccmappfordealersterlite.model.HomeFragment.SubscriberServicewiseBalance;
import com.sterlite.dccmappfordealersterlite.model.UpdateCustomer.GetUpdateCustomerRequestData;
import com.sterlite.dccmappfordealersterlite.model.UpdateCustomer.GetUpdateCustomerResponseData;
import com.sterlite.dccmappfordealersterlite.model.dto.user.UserResponseWsDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.validatecustomer.ValidateCustomerWsDTO;

/**
 * Created by etech3 on 27/6/18.
 */

public interface LoginContract {
    interface View extends BaseContractView {

        void onSuccessLogin(UserResponseWsDTO userResponseWsDTO);

        void onFailLogin();

        void finishActivity();

        void getAccountDataApiSucc(SubscriberServicewiseBalance baseResponse);

        void onSuccessUpdateProfileData(GetUpdateCustomerResponseData getUpdateCustomerResponseData);

        void onSuccessGetProfileData(GetUpdateCustomerResponseData getUpdateCustomerResponseData);



    }

    interface Presenter<V extends View> extends BaseContractPresenter<V> {

        void doLogin(ValidateCustomerWsDTO validateCustomerWsDTO,Boolean isPartner);
        void getAccountProfileData(String customerNumber);
        void updateAccountProfileData(GetUpdateCustomerRequestData getUpdateCustomerRequestData);
        void getAccountData();
      //  void success(boolean b);
    }
}

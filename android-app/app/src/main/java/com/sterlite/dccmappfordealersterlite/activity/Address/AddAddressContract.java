package com.sterlite.dccmappfordealersterlite.activity.Address;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;
import com.sterlite.dccmappfordealersterlite.model.Address;

public class AddAddressContract {
    interface View extends BaseContractView{
        void setAddress(Address address);

        void setValuesToModel();

        void gotoOrderPreview();

        void goToShippingAddressScreen();

        void finishActivity();
    }
    interface Presentner<V extends View> extends BaseContractPresenter<V>{
        void init(boolean isShippingAddress,boolean isPrefillAddress);

        void saveAndGotoPlaceOrder(Address address);

        void saveAddress(Address address);

        void saveAddressAndFinish(Address address);
    }
}

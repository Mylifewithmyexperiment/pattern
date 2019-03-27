package com.sterlite.dccmappfordealersterlite.activity.Address;

import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;
import com.sterlite.dccmappfordealersterlite.model.Address;

public class AddAddressPresenter<V extends AddAddressContract.View> extends BasePresenter<V> implements AddAddressContract.Presentner<V> {
    @Override
    public void init(boolean isShippingAddress,boolean isPrefillData) {
        if (Constants.IS_DUMMY_MODE) {
            if (!isShippingAddress && isPrefillData) {
                getView().setAddress(new Address("etech", "developer", "9876543210", "AddressLine1", "AddressLine2", "Abd", "Gujarat", "India", "3800015", true));
            }
        }
    }

    @Override
    public void saveAndGotoPlaceOrder(Address address) {
            if (getView() == null)return;
        getView().setValuesToModel();
        getView().gotoOrderPreview();
    }

    @Override
    public void saveAddress(Address address) {
        if (getView() == null)return;
        getView().setValuesToModel();
        getView().goToShippingAddressScreen();

    }

    @Override
    public void saveAddressAndFinish(Address address) {
        if (getView() == null)return;
        getView().setValuesToModel();
        getView().finishActivity();
    }
}

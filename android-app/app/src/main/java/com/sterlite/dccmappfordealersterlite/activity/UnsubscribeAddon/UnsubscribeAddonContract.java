package com.sterlite.dccmappfordealersterlite.activity.UnsubscribeAddon;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;
import com.sterlite.dccmappfordealersterlite.model.bssrest.ServiceInstanceDetail;

/**
 * Created by elitecore on 23/10/18.
 */

public class UnsubscribeAddonContract {
    interface View extends BaseContractView {

        void loadDataToView(ServiceInstanceDetail responseModel);
        void unsubscribeSuccess(String baseResponse);


    }

    interface Presenter<V extends UnsubscribeAddonContract.View> extends BaseContractPresenter<V> {

        void init(String serviceInstanceNumber);
        void unsubscribeAddon(String serviceInstanceNumber,String externalReferenceID);



    }
}

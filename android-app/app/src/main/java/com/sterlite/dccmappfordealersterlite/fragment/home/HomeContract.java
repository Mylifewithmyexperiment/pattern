package com.sterlite.dccmappfordealersterlite.fragment.home;

import java.util.List;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;
import com.sterlite.dccmappfordealersterlite.data.network.ApiHelper;
import com.sterlite.dccmappfordealersterlite.model.HomeFragment.SubscriberServicewiseBalance;
import com.sterlite.dccmappfordealersterlite.model.bssrest.ServiceInventoryResponse;

/**
 * Created by etech3 on 27/6/18.
 */

public interface HomeContract {
    interface View extends BaseContractView {

        void setUpView(boolean isReset);

        void loadDataToView(SubscriberServicewiseBalance subscriberServicewiseBalance);

        void onSuccessCustDetails(List<ServiceInventoryResponse> serviceInventoryResponses,boolean isServiceInstance);
        void onFail();
        void FailData(String message);

    }

    interface Presenter<V extends View> extends BaseContractPresenter<V> {

        void init();

        void admin();

        void reset();

        void getCustomerDetails(String userId,boolean isServiceInstance);
        void getInventoryNumber(String userId, ApiHelper.OnApiCallback<String> onApiCallback);

        }
}

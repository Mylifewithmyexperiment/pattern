package com.sterlite.dccmappfordealersterlite.activity.kycdetail;

import android.content.Context;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;
import com.sterlite.dccmappfordealersterlite.model.Address;

public class KYCReviewContract {
    interface View extends BaseContractView{

        void setAddress(Address details);

    }
    interface Presenter<V extends View> extends BaseContractPresenter<V> {

        void init(String kycNumber, Context context);

    }
}

package com.sterlite.dccmappfordealersterlite.activity.planDetail;


import android.content.Context;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;
import com.sterlite.dccmappfordealersterlite.model.Plan;
import com.sterlite.dccmappfordealersterlite.model.dto.addProductToCart.AddToCartResponseWsDTO;

/**
 * Created by etech3 on 27/6/18.
 */

public interface PlanDetailContract {
    interface View extends BaseContractView {

        void setUpView(boolean isReset);

        void loadDataToView(ArrayList<Plan> list);

        void addToCartApiCallSuccess(AddToCartResponseWsDTO addToCartResponseWsDTO);

    }

    interface Presenter<V extends PlanDetailContract.View> extends BaseContractPresenter<V> {

        void init(String planType, Context cntxt);
        void getPlans(final String categoryCode);

        void addToCart(String productCode);
    }
}

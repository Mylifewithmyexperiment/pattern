package com.sterlite.dccmappfordealersterlite.fragment.cart;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;
import com.sterlite.dccmappfordealersterlite.model.CartDetails;

/**
 * Created by etech3 on 27/6/18.
 */

public interface CartFragmentContract {
    interface View extends BaseContractView {

        void setCartDetails(CartDetails cartDetails);

    }

    interface Presenter<V extends View> extends BaseContractPresenter<V> {

        void init();

        void reset();

    }
}

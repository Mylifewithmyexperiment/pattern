package com.sterlite.dccmappfordealersterlite.fragment.cart;


import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.Utils.DummyLists;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;
import com.sterlite.dccmappfordealersterlite.model.CartDetails;

/**
 * Created by etech3 on 27/6/18.
 */

public class CartFragmentPresenter<V extends CartFragmentContract.View> extends BasePresenter<V> implements CartFragmentContract.Presenter<V> {


    @Override
    public void onAttach(V baseView) {
        super.onAttach(baseView);
    }

    @Override
    public void init() {
        if (getView() == null)
            return;
        if (Constants.IS_DUMMY_MODE) {
            getView().showProgressBar();
          /*  new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {*/
//                    CartDetails cartDetails = DummyLists.getCartDetailsWithNoItems();
            CartDetails cartDetails = DummyLists.getCartDetailsWithItems();
            if (getView() == null)
                return;
            getView().setCartDetails(cartDetails);
            getView().hideProgressBar();
               /* }
            },2000);*/

        }
    }

    @Override
    public void reset() {
        if(Constants.IS_DUMMY_MODE)
        init();
    }
}

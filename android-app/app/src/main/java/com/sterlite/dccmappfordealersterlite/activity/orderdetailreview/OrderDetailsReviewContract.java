package com.sterlite.dccmappfordealersterlite.activity.orderdetailreview;

import android.content.Context;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;
import com.sterlite.dccmappfordealersterlite.model.Address;
import com.sterlite.dccmappfordealersterlite.model.OrderDetails;
import com.sterlite.dccmappfordealersterlite.model.dto.BssResponse.BSSDataResponse;
import com.sterlite.dccmappfordealersterlite.model.dto.addProductToCart.AddToCartResponseWsDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.dccmdto.order.OrderWsDTO;

public class OrderDetailsReviewContract {
    interface View extends BaseContractView{

        void setOrderDetails(OrderDetails details);

        void addToCartApiCallSuccess(AddToCartResponseWsDTO addToCartResponseWsDTO);

        void addInventoryToCartSuccess(BSSDataResponse bssDataResponse);

        void setDeliveryAddressApiCallSuccess(BSSDataResponse bssDataResponse);

        void setDeliveryModeApiCallSuccess(BSSDataResponse bssDataResponse);

        void placeOrderApiCallSuccess(OrderWsDTO orderWsDTO);

        void getAuthTokenApiCallSuccess(String authToken);
        //void deleteCartAPISuccess(String cartId);

        void finishActivity();


//        void addAddonToCartApiCallSuccess(CartModificationWsDTO cartModificationWsDTO);
    }
    interface Presenter<V extends View> extends BaseContractPresenter<V> {

        void init(Address address, Context context);

        void addToCart(String productCode,String bundledTemplateID,String productCategory);

        void addInventoryToCart(String inv, String type);

        void setDeliveryAddress(Address address);

        void setDeliveryMode();

        void placeOrder();

        void deleteCart(String cartId);


        void getAuthToken();

//        void addAddonToCart(String code);
    }
}

package com.sterlite.dccmappfordealersterlite.activity.inventory;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;
import com.sterlite.dccmappfordealersterlite.model.Inventory;
import com.sterlite.dccmappfordealersterlite.model.InventoryFilter.InventoryFilter;
import com.sterlite.dccmappfordealersterlite.model.dto.BssResponse.BSSDataResponse;

/**
 * Created by etech3 on 27/6/18.
 */

public interface InventoryContract {
    interface View extends BaseContractView {

        void setUpView(boolean isReset);

        void loadDataToView(ArrayList<Inventory> list, InventoryFilter inventoryFilter);

        void saveInventory(ArrayList<Inventory> inventories);

        void addInventoryToCartSuccess(BSSDataResponse bssDataResponse);
    }

    interface Presenter<V extends View> extends BaseContractPresenter<V> {

        void init();

        void addInventoryToCart(String inv,String productCode);

    }
}

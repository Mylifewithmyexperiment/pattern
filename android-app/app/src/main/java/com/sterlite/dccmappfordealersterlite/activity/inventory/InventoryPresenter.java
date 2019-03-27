package com.sterlite.dccmappfordealersterlite.activity.inventory;


import android.util.Log;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import com.sterlite.dccmappfordealersterlite.Utils.DummyLists;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;
import com.sterlite.dccmappfordealersterlite.data.network.AppApiHelper2;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.model.Inventory;
import com.sterlite.dccmappfordealersterlite.model.dto.BssResponse.BSSDataResponse;
import com.sterlite.dccmappfordealersterlite.model.dto.inventory.InventoryDetailDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.inventory.InventoryDetailWsDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.inventory.NumberSelectionInvWsDTO;

/**
 * Created by etech3 on 27/6/18.
 */

public class InventoryPresenter<V extends InventoryContract.View> extends BasePresenter<V> implements InventoryContract.Presenter<V> {

    // 20180830
    @Override
    public void onAttach(V baseView) {
        super.onAttach(baseView);

    }

    @Override
    public void init() {
        if (getView() == null) return;
        getView().setUpView(false);
        getView().showProgressBar();

        AppApiHelper2.getWebServices().getInventory(new Callback<InventoryDetailWsDTO>() {
            @Override
            public void success(InventoryDetailWsDTO inventoryDetailWsDTO, Response response) {
                if (getView() == null) return;
                getView().hideProgressBar();
                Log.e("InventoryPresenter" , inventoryDetailWsDTO + " ");
                ArrayList<Inventory> inventories  =new ArrayList<>();
                for (InventoryDetailDTO inv:inventoryDetailWsDTO.getLstInvenotries()) {
                    inventories.add(getInventory(inv));
                    for (InventoryDetailDTO invInn:inv.getVariants()) {
                        inventories.add(getInventory(invInn));
                    }
                }
                getView().saveInventory(inventories);
                getView().loadDataToView(inventories,DummyLists.getInventoryFilters());
//                getInventory("0", "premium", "Rp 5000", "1234567890", "20% discount applied", "price expire - 2 h 0 mins");


            }

            @Override
            public void failure(RetrofitError error) {
                if (getView() == null) return;
                getView().hideProgressBar();
                Log.e("InventoryPresenter" , error + " ");
                ArrayList<Inventory> inventories=new ArrayList<>();
                getView().saveInventory(inventories);
                getView().loadDataToView(inventories,DummyLists.getInventoryFilters());
//                if (Constants.IS_DUMMY_MODE) {
//                    getView().showProgressBar();
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (getView() == null) return;
//                            getView().loadDataToView(DummyLists.getInventoryList(),DummyLists.getInventoryFilters());
//                            getView().hideProgressBar();
//                        }
//                    }, 1000);
//                }
            }
            private Inventory getInventory(String id, String type, String price, String number, String disCount, String expire,String luckyNumber) {
                Inventory inventory = new Inventory();
                inventory.setId(id);
                inventory.setNumberType(type);
                inventory.setNumber(number);
                inventory.setDiscountText(disCount);
                inventory.setExpireDateText(expire);
                inventory.setPrice(price);
                inventory.setLuckyNumber(luckyNumber);
                return inventory;
            }
            private Inventory getInventory(InventoryDetailDTO inv) {
                String discount;
                if(inv.getInventoryDetailPlanPrice()!= null
                        && !inv.getInventoryDetailPlanPrice().equals("")
                        && !inv.getInventoryDetailPlanPrice().equals("0")
                        && inv.getVanityCategory().equalsIgnoreCase("Gold")){
                    discount = "20% discount applied" ;
                }else{
                    discount = "";
                }
                return  getInventory(inv.getInventoryID(),
                        inv.getVanityCategory(),
                        (inv.getInventoryDetailPlanPrice()== null ? "0" : inv.getInventoryDetailPlanPrice())+"",
                        inv.getInventoryNumber(),
                        discount+"",
                        "",
                        inv.getLuckyNumber());
            }

        });
    }


    @Override
    public void addInventoryToCart(String inv, String type) {
        Log.e("addInventoryToCart","addInventoryToCart :: START" + inv + "  ::::: "+type);
        if (getView() == null) return;
        getView().showProgressBar();
        NumberSelectionInvWsDTO number = new NumberSelectionInvWsDTO();
        number.setUserName(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID,""));
        number.setRootBundleName("Postpaid");
        number.setBundleName("Postpaid-Service Plan Components");
        number.setProductName("SIM");
        number.setNumberCategory(type);
        number.setCurrentPage(0);
        number.setSelectedNumber(inv);
        number.setIsFreeNumberFilterOn(false);
        number.setShowMore(false);
//        number.setOrderEntryNumber("1");
        AppApiHelper2.getWebServices().addInventoryToCart(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID,""),
                DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CART_ID,""),
                number, new Callback<BSSDataResponse>() {
                    @Override
                    public void success(BSSDataResponse bssDataResponse, Response response) {
                        Log.e("InventoryPresenter","bssDataResponse :: " + bssDataResponse);
                        if (getView() == null) return;
                        getView().hideProgressBar();
                        getView().addInventoryToCartSuccess(bssDataResponse);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("InventoryPresenter","error :: " + error);
                        if (getView() == null) return;
                        getView().hideProgressBar();
                        getView().addInventoryToCartSuccess(null);

//                if(Constants.IS_DUMMY_MODE){
//                    BSSDataResponse bssDataResponse = new BSSDataResponse();
//                    bssDataResponse.setResponseCode("-1");
//                    getView().addInventoryToCartSuccess(bssDataResponse);
//                }
                    }
                });
    }

}

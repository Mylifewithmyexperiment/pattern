package com.sterlite.dccmappfordealersterlite.activity.orderdetailreview;

import android.content.Context;
import android.util.Log;

import java.util.Calendar;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.Utils.DateTimeUtils;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;
import com.sterlite.dccmappfordealersterlite.data.network.AppApiHelper2;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.model.Address;
import com.sterlite.dccmappfordealersterlite.model.Inventory;
import com.sterlite.dccmappfordealersterlite.model.OrderDetails;
import com.sterlite.dccmappfordealersterlite.model.Plan;
import com.sterlite.dccmappfordealersterlite.model.dto.BssResponse.BSSDataResponse;
import com.sterlite.dccmappfordealersterlite.model.dto.addProductToCart.AddToCartResponseWsDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.addProductToCart.AddToCartWsDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.address.AddressWsDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.auth.AuthResponseDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.country.CountryWsDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.dccmdto.order.OrderWsDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.inventory.NumberSelectionInvWsDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.region.RegionWsDTO;

public class OrderDetailsReviewPresenter<V extends OrderDetailsReviewContract.View> extends BasePresenter<V> implements OrderDetailsReviewContract.Presenter<V> {
    Address address;
    Context context;

    @Override
    public void init(Address address, Context context) {
       /* if (Constants.IS_DUMMY_MODE) {
            if(getView()==null)
                return;
            getView().showProgressBar();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(getView()==null)
                        return;
                    getView().hideProgressBar();
                    getView().setAddress(DummyLists.getOrderDetailPreview());
                }
            },500);

        }*/

        if (getView() == null)
            return;
        this.address = address;
        this.context = context;
        try {
            this.address = AppUtils.getObjFromString(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.DELIVERY_ADDRESS, null), Address.class);
        } catch (Exception e) {
            Log.e("OrderDetailsRevAct", e + " ");
        }
        getView().setOrderDetails(getOrderDetailPreview());
    }


    @Override
    public void onAttach(V baseView) {
        super.onAttach(baseView);
    }

    public OrderDetails getOrderDetailPreview() {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setDisplayOrderId("");
        Plan plan = AppUtils.getObjFromString(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.PRODUCT_PLAN, null), Plan.class);
        Log.e("PRODUCT_BENEFITS ", " plan :::: " + DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.PRODUCT_PLAN, null));

        Calendar date = Calendar.getInstance();
        orderDetails.setOrderDate(DateTimeUtils.getFormatedDate(date) + " 12:00 PM");
        orderDetails.setOrderTotal(plan.getPricePerMonth());
        orderDetails.setOrderStatus(context.getString(R.string.status_pending));
        orderDetails.setBillingAddressDetails(address);
        orderDetails.setShippingAddressDetails(address);
        orderDetails.setTrackable(true);
        orderDetails.setPlanName(plan.getPlanTitle());
        int cnt = 0;
        String benefit = new String();
        if (plan.getArrBenefits() != null)
            for (int i = 0; i < plan.getArrBenefits().size(); i++) {

                Log.e("PRODUCT_BENEFITS " + cnt++, plan.getArrBenefits().get(i).getDescription());
                benefit += plan.getArrBenefits().get(i).getDescription();
                if (i != plan.getArrBenefits().size() - 1)
                    benefit += "\n";
            }
        orderDetails.setPlanBenefits(benefit);
        orderDetails.setPlanAmount(plan.getPricePerMonth());
        orderDetails.setGrandTotal(plan.getPricePerMonth());

        boolean isBB = DCCMDealerSterlite.getDataManager().getBoolean(AppPreferencesHelper.IS_BB, false);
        if (!isBB) {
            orderDetails.setDeliveryAmount(context.getString(R.string.free));
            Inventory inventory = AppUtils.getObjFromString(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.INVENTORY_DATA, ""), Inventory.class);
            orderDetails.setNumber(inventory.getNumber());
            orderDetails.setNumberAmount(inventory.getPrice());
            orderDetails.setSimStatus(context.getString(R.string.activation));
            String invPrice = inventory.getPrice();
            if (invPrice != null) {
                orderDetails.setGrandTotal(String.valueOf(Double.parseDouble(plan.getPricePerMonth()) + Double.parseDouble(invPrice)));
            } else {
                orderDetails.setGrandTotal(plan.getPricePerMonth());
            }
            orderDetails.setSimAmount(context.getString(R.string.free));
        }
        if (address != null) {
            orderDetails.setDeliveryDate(DateTimeUtils.getFormatedDate(address.getDate()) + " 12:00 PM");
        }
        orderDetails.setExtraPlanDetail(context.getString(R.string.free_benefits_worth_rp) + plan.getFreeBenefitInRupee());
        orderDetails.setUserEmail(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.EMAIL_ID, ""));
        orderDetails.setDeliveryType(context.getString(R.string.shipping_address));
        return orderDetails;
    }

    @Override
    public void addToCart(String productCode, String bundledTemplateID, String productCategory) {
        Log.e("addToCart", "addToCart :: START" + productCode);
        Log.e("addToCart:userid", DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID, "")+"H");

        if (getView() == null) return;
        getView().showProgressBar();
        final AddToCartWsDTO addToCartWsDTO = new AddToCartWsDTO();
        addToCartWsDTO.setProductCode(productCode);
        addToCartWsDTO.setRemoveOlderEntries(true);
        addToCartWsDTO.setBundledTemplateID(bundledTemplateID);
        addToCartWsDTO.setProductCategory(productCategory);
        AppApiHelper2.getWebServices().addToCart(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID, ""), addToCartWsDTO, new Callback<AddToCartResponseWsDTO>() {
            @Override
            public void success(AddToCartResponseWsDTO addToCartResponseWsDTO, Response response) {
                Log.e("PlanDetailPresenter", "addToCartResponseWsDTO :: " + addToCartResponseWsDTO);
                if (getView() == null) return;
                getView().hideProgressBar();
                getView().addToCartApiCallSuccess(addToCartResponseWsDTO);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("PlanDetailPresenter", "error :: " + error);
                if (getView() == null) return;
                getView().hideProgressBar();
                getView().addToCartApiCallSuccess(null);
                if (Constants.IS_DUMMY_MODE) {
                    AddToCartResponseWsDTO addToCartResponseWsDTO = new AddToCartResponseWsDTO();
                    addToCartResponseWsDTO.setSuccess(false);
                    getView().addToCartApiCallSuccess(addToCartResponseWsDTO);
                }
            }
        });
    }

//    @Override
//    public void addAddonToCart(String code) {
//        Log.e("addAddonToCart","addAddonToCart :: START" + code);
//        OrderEntryWsDTO orderEntryWsDTO = new OrderEntryWsDTO();
//        ProductWsDTO productWsDTO = new ProductWsDTO();
//        productWsDTO.setCode(code);
//        orderEntryWsDTO.setQuantity(1l);
//        orderEntryWsDTO.setProduct(productWsDTO);
//
//
//        if (getView() == null) return;
//        getView().showProgressBar();
//        AppApiHelper2.getWebServices().addToCartWithCartID(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID, ""),
//                DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CART_ID, ""),
//                DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.AUTH__TOKEN, ""),
//                code,
//                orderEntryWsDTO,
//                new Callback<CartModificationWsDTO>() {
//                    @Override
//                    public void success(CartModificationWsDTO cartModificationWsDTO, Response response) {
//                        Log.e("addAddonToCart","cartModificationWsDTO :: " + cartModificationWsDTO);
//                        if (getView() == null) return;
//                        getView().hideProgressBar();
//                        getView().addAddonToCartApiCallSuccess(cartModificationWsDTO);
//                    }
//
//                    @Override
//                    public void failure(RetrofitError error) {
//                        Log.e("addAddonToCart","RetrofitError :: " + error);
//                        if (getView() == null) return;
//                        getView().hideProgressBar();
//                        getView().addAddonToCartApiCallSuccess(null);
//                    }
//                });
//    }

    @Override
    public void addInventoryToCart(String inv, String type) {
        Log.e("addInventoryToCart", "addInventoryToCart :: START" + inv + "  ::::: " + type);
        if (getView() == null) return;
        getView().showProgressBar();
        NumberSelectionInvWsDTO number = new NumberSelectionInvWsDTO();
        number.setUserName(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID, ""));
        number.setRootBundleName("Postpaid");
        number.setBundleName("Postpaid-Service Plan Components");
        number.setProductName("SIM");
        number.setNumberCategory(type);
        number.setCurrentPage(0);
        number.setSelectedNumber(inv);
        number.setIsFreeNumberFilterOn(false);
        number.setShowMore(false);
//        number.setOrderEntryNumber("1");
        AppApiHelper2.getWebServices().addInventoryToCart(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID, ""),
                DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CART_ID, ""),
                number, new Callback<BSSDataResponse>() {
                    @Override
                    public void success(BSSDataResponse bssDataResponse, Response response) {
                        Log.e("InventoryPresenter", "bssDataResponse :: " + bssDataResponse);
                        if (getView() == null) return;
                        getView().hideProgressBar();
                        getView().addInventoryToCartSuccess(bssDataResponse);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("InventoryPresenter", "error :: " + error);
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

    @Override
    public void setDeliveryAddress(Address address) {
        Log.e("setDeliveryAddress", "setDeliveryAddress :: START" + address.toString());
        if (getView() == null) return;
        getView().showProgressBar();
        CountryWsDTO countryWsDTO = new CountryWsDTO();
        RegionWsDTO regionWsDTO = new RegionWsDTO();


        countryWsDTO.setIsocode("IN");
        countryWsDTO.setName(address.getCountry());

        regionWsDTO.setIsocode("MUM");
        regionWsDTO.setName(address.getCity());
      /*  if (address.getCountry().equalsIgnoreCase("india")) {
            countryWsDTO.setIsocode("IN");
            countryWsDTO.setName("India");
        } else {
            countryWsDTO.setIsocode("IN");
            countryWsDTO.setName("USA");
        }
        if (address.getState().equalsIgnoreCase("Gujarat")) {
            regionWsDTO.setIsocode("GJ");
            regionWsDTO.setName("Gujarat");
        } else if (address.getState().equalsIgnoreCase("mumbai")) {
            regionWsDTO.setIsocode("MUM");
            regionWsDTO.setName("Mumbai");
        }
        else if (address.getState().equalsIgnoreCase("California")) {
            regionWsDTO.setIsocode("MUM");
            regionWsDTO.setName("California");
        }
        else {
            regionWsDTO.setIsocode("MUM");
            regionWsDTO.setName("Mumbai");
        }
*/
        AddressWsDTO addressWsDTO = new AddressWsDTO();
        addressWsDTO.setTitle("Mr.");
        addressWsDTO.setTitleCode("603");
        addressWsDTO.setFirstName(address.getFname());
        addressWsDTO.setLine1(address.getAddressLine1());
        addressWsDTO.setLine2(address.getAddressLine2());
        addressWsDTO.setTown(address.getCity());
        addressWsDTO.setPostalCode(address.getPin());
        addressWsDTO.setDefaultAddress(true);
        addressWsDTO.setShippingAddress(true);
        addressWsDTO.setCountry(countryWsDTO);
        addressWsDTO.setRegion(regionWsDTO);

        AppApiHelper2.getWebServices().addDeliveryAddress(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID, ""),
                DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CART_ID, ""),
                addressWsDTO, new Callback<BSSDataResponse>() {
                    @Override
                    public void success(BSSDataResponse bssDataResponse, Response response) {
                        Log.e("setDeliveryAddress", "setDeliveryAddress :: " + bssDataResponse);
                        if (getView() == null) return;
                        getView().hideProgressBar();
                        getView().setDeliveryAddressApiCallSuccess(bssDataResponse);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("setDeliveryAddress", "RetrofitError :: " + error);
                        if (getView() == null) return;
                        getView().hideProgressBar();
                        getView().setDeliveryAddressApiCallSuccess(null);
                    }
                });
    }

    @Override
    public void setDeliveryMode() {
        Log.e("setDeliveryMode", "setDeliveryMode :: START" + address);
        if (getView() == null) return;
        getView().showProgressBar();

        AppApiHelper2.getWebServices().setDeliveryModeAddress(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID, ""),
                "homeDelivery-net",
                "",
                new Callback<BSSDataResponse>() {
                    @Override
                    public void success(BSSDataResponse bssDataResponse, Response response) {
                        Log.e("setDeliveryMode", "bssDataResponse :: " + bssDataResponse);
                        if (getView() == null) return;
                        getView().hideProgressBar();
                        getView().setDeliveryModeApiCallSuccess(bssDataResponse);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("setDeliveryMode", "RetrofitError :: " + error);
                        if (getView() == null) return;
                        getView().hideProgressBar();
                        getView().setDeliveryModeApiCallSuccess(null);
                    }
                });

    }

    //https://10.121.25.109:9002/telcocommercewebservices/v2/b2ctelco/users/les10091800062/orders?cartId=00000182&access_token=f4835ae1-f2ea-4ae0-9a38-a80ad6babe35
    @Override
    public void placeOrder() {
        Log.e("placeOrder", "placeOrder :: START");
        if (getView() == null) return;
        getView().showProgressBar();

        AppApiHelper2.getWebServices().placeOrder(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID, ""),
                DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CART_ID, ""),
                DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.AUTH__TOKEN, ""),
                "",
                new Callback<OrderWsDTO>() {
                    @Override
                    public void success(OrderWsDTO orderWsDTO, Response response) {
                        Log.e("placeOrder", "orderWsDTO :: " + orderWsDTO);
                        if (getView() == null) return;
                        getView().hideProgressBar();
                        getView().placeOrderApiCallSuccess(orderWsDTO);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("placeOrder", "RetrofitError :: " + error);
                        if (getView() == null) return;
                        getView().hideProgressBar();
                        getView().placeOrderApiCallSuccess(null);
                    }
                });
    }

    @Override
    public void getAuthToken() {
        Log.e("getAuthToken", "getAuthToken :: START");
        if (getView() == null) return;
        getView().showProgressBar();
        String client_id = "authbridge";
        String client_secret = "secret";
        String grant_type = "client_credentials";
        String b = "bhoomi";
        AppApiHelper2.getWebServices(AppApiHelper2.AUTH_URL).getAuthToken(client_id, client_secret, grant_type, b, new Callback<AuthResponseDTO>() {
            @Override
            public void success(AuthResponseDTO authResponseDTO, Response response) {


                Log.e(" In Success Response : ", " From DCCM :: " + authResponseDTO.getAccess_token());

                Log.e("Token : ", authResponseDTO.getAccess_token());
                DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.AUTH__TOKEN.toString(), authResponseDTO.getAccess_token());
                if (getView() == null) return;
                getView().hideProgressBar();
                getView().getAuthTokenApiCallSuccess(authResponseDTO.getAccess_token());

            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("getAuthToken :", "RetrofitError" + error);
                if (getView() == null) return;
                getView().hideProgressBar();
                getView().getAuthTokenApiCallSuccess(null);
            }
        });
    }

    @Override
    public void deleteCart(final String cartId) {

        try {


            String userId = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID, null);
            String authToken = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.AUTH__TOKEN, null);

            Log.e("userId::", userId);
            Log.e("authToken::", authToken);
            Log.e("cartId::", cartId);
            AppApiHelper2.getWebServices().deleteCart(userId, cartId, authToken, new Callback<String>() {
                @Override
                public void success(String s, Response response) {
                    Log.e("response::", response + " ");

                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

        }catch (Exception e){
            Log.e("Exception::", e + " ");

        }
    }

}
/*
    public static OrderDetails getOrderDetailPreview() {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setDisplayOrderId("14520000414");
        orderDetails.setOrderDate("07-Dec-2017 12:00 PM");
        orderDetails.setOrderTotal("Rp 5549.00");
        orderDetails.setGrandTotal("Rp 5549.00");
        orderDetails.setOrderStatus("pending");
        orderDetails.setPaymentMethod("cash on delivery");
//        orderDetails.setBillingAddressDetails(new Address("etech", "developer", "9876543210", "AddressLine1", "AddressLine2", "Abd", "Gujarat", "India", "3800015", true));
        orderDetails.setOrderBillTo("etech developer");
        orderDetails.setOrderShipTo("etech developer");
//        orderDetails.setShippingAddressDetails(new Address("etech", "developer", "9876543210", "AddressLine1", "AddressLine2", "Abd", "Gujarat", "India", "3800015", true));
        orderDetails.setItemTotal("Rp 5399.00");
        orderDetails.setShippingAmount("Rp 100.00");
        orderDetails.setTotalBeforeTax("Rp 5499.00");
        orderDetails.setTaxAmount("Rp 50.00");
        orderDetails.setTrackable(true);
//        orderDetails.setTrackable(false);
        orderDetails.setShippingMethod("home delivery");
//        orderDetails.setShippingMethod("store pick up");
//        orderDetails.setStoreName("abc store");
//        orderDetails.setStoreAddressDetails(new Address("etech", "developer", "9876543210", "AddressLine1", "AddressLine2", "Abd", "Gujarat", "India", "3800015", true));

        orderDetails.setPlanName("RED Basic");
        orderDetails.setPlanBenefits("Hello dsfkjhdsfkh sdjkfkjdh\n jsfhsdf sdhjfhdshf jskdhhsdg hjskhdhdsjg hsdjhdjg");
        orderDetails.setDeliveryAmount("FREE");
        orderDetails.setNumber("0123456789");
        orderDetails.setNumberAmount("Rs. 0");
        orderDetails.setSimStatus("Activation");
        orderDetails.setSimAmount("FREE");
        orderDetails.setDeliveryDate("07-Dec-2017 12:00 PM");
        orderDetails.setExtraPlanDetail("Free Benefits worth Rs. 499");
        orderDetails.setUserEmail("user@mail.com");
        orderDetails.setPlanAmount("Rs.299/mon");
        orderDetails.setDeliveryType("Shipping Address");
        return orderDetails;
    }
    */


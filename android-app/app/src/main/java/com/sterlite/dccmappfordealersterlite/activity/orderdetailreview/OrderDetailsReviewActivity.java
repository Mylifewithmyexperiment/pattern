package com.sterlite.dccmappfordealersterlite.activity.orderdetailreview;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.activity.Activation.ActiviationPresenter;
import com.sterlite.dccmappfordealersterlite.activity.Address.AddAddressActivity;
import com.sterlite.dccmappfordealersterlite.activity.InputPaymentInformation.InputPaymentInformationActivity;
import com.sterlite.dccmappfordealersterlite.activity.OrederReceiveSuccess.OrderReceiveSuccess;
import com.sterlite.dccmappfordealersterlite.activity.ServiceDetail.ServiceDetailActivity;
import com.sterlite.dccmappfordealersterlite.activity.inventory.InventoryActivity;
import com.sterlite.dccmappfordealersterlite.activity.planDetail.PlanDetailActivity;
import com.sterlite.dccmappfordealersterlite.activity.trackingDetail.TrackingDetailActivity;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.data.network.ApiHelper;
import com.sterlite.dccmappfordealersterlite.data.network.AppApiHelper2;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityOrderDetailsReviewBinding;
import com.sterlite.dccmappfordealersterlite.model.Address;
import com.sterlite.dccmappfordealersterlite.model.FindSubscriptionByEmailModel;
import com.sterlite.dccmappfordealersterlite.model.Inventory;
import com.sterlite.dccmappfordealersterlite.model.OrderDetails;
import com.sterlite.dccmappfordealersterlite.model.Plan;
import com.sterlite.dccmappfordealersterlite.model.Store;
import com.sterlite.dccmappfordealersterlite.model.dto.BssResponse.BSSDataResponse;
import com.sterlite.dccmappfordealersterlite.model.dto.addProductToCart.AddToCartResponseWsDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.dccmdto.order.OrderWsDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.inventory.InventoryDetailWsDTO;

public class OrderDetailsReviewActivity extends BaseActivity implements OrderDetailsReviewContract.View, View.OnClickListener,ApiHelper.OnApiCallback<FindSubscriptionByEmailModel> {
    public static final String NEW_CONNACTION = "newConnation";
    public static final String IS_ORDER_PREVIEW = "argOrderDetailsReviewActivityIsOrderPreview";
    OrderDetailsReviewContract.Presenter<OrderDetailsReviewContract.View> presenter;
    ActivityOrderDetailsReviewBinding binding;
    boolean isOrderPreview = false;
    boolean isMMP = false;
    boolean isBB = false;
    boolean isPickup = false;
    public ArrayList<Inventory> inventories;
    boolean isAddonAdded = false;
    private OrderDetails orderDetails;
    Address address;
    Calendar calendar;
    static int count;
    Store store;
    Calendar calendar1;
    String cartCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        setAppTheme();
        init();
    }

    private void init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_details_review);
        isOrderPreview = getIntent().getBooleanExtra(IS_ORDER_PREVIEW, false);
        isMMP = DCCMDealerSterlite.getDataManager().getBoolean(AppPreferencesHelper.ARG_FROM_MMP,false);
        isBB = DCCMDealerSterlite.getDataManager().getBoolean(AppPreferencesHelper.IS_BB,false);

        Intent intent = getIntent();

        isPickup = intent.getBooleanExtra(AppPreferencesHelper.PICK_UP_STORE,false);
        Log.e("isPickup",isPickup+"");
        String title;
        if (isOrderPreview) {
            title = getString(R.string.title_order_preview);
        } else {
            title = getString(R.string.title_order_details);
        }
        if (isBB){
            binding.lbldeliverydate.setText(R.string.lbl_installation_date_time);
        }
        setUpView(binding.toolbar.toolbar, binding.extraView, title, true);
        initUI();
        setClickListeners();
        presenter = new OrderDetailsReviewPresenter<>();
        presenter.onAttach(this);

        if (isPickup)
            address=setPickupAddress();
        else
            address = (Address) getIntent().getSerializableExtra(AddAddressActivity.ADDRESS);


        presenter.init(address,this);
        AppUtils.errorLog(getClass().getSimpleName()," setAddress " + address);


    }

   public Address setPickupAddress(){

        store=(Store)getIntent().getSerializableExtra(AppPreferencesHelper.STORE_ADDRESS);
        Log.e("Store Data",store+" ");
        address= new Address();
        calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(System.currentTimeMillis() + (86400000 * 3));
        address.setDate(calendar1);
        address.setPin(store.getPostalCode());
        // Region region = Region.getRegion(store.getPostalCode());
        address.setFname(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_NAME, null));
        address.setMobileNo(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.MOBILE_NO, null));
        //address.setAddressLine1(store.getStoreAddress());
        address.setAddressLine1(store.getStoreName());
        address.setAddressLine2(store.getStoreAddress());
        address.setCity("Jakarta");
        address.setState("Jakarta");
        address.setCountry("Indonesia");

        address.setIsUseSameForShipping(true);
        return address;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPickup)
            address = setPickupAddress();
        else
            address = (Address) getIntent().getSerializableExtra(AddAddressActivity.ADDRESS);

        AppUtils.errorLog(getClass().getSimpleName(),"address HARSH::" +address);
        presenter.init(address,this);
        Log.e("OrderDetailsRevAct","onResume HARSH::");
    }

    private void initUI() {
        if (isOrderPreview) {
            binding.ivNumberEdit.setVisibility(View.VISIBLE);
            binding.ivDeliveryEdit.setVisibility(View.VISIBLE);
            binding.ivPlanEdit.setVisibility(View.VISIBLE);
            binding.ivDeliveryDate.setVisibility(View.VISIBLE);
            binding.relOrderNoContainer.setVisibility(View.GONE);
//            binding.ivEmail.setVisibility(View.VISIBLE);
            binding.btnProceed.setText(getString(R.string.btn_place_order));
        } else {
            binding.relOrderNoContainer.setVisibility(View.VISIBLE);
        }
        if(isMMP)
        {
            binding.ivPlanEdit.setVisibility(View.GONE);
            binding.rlPlanBenefits.setVisibility(View.GONE);
            count = DCCMDealerSterlite.getDataManager().getint(AppPreferencesHelper.ADDON_SIZE,0);
        }else{
            count=0;
        }

        if (isPickup){
            binding.ivDeliveryDate.setVisibility(View.GONE);

        }
        if(isBB){
            binding.llInv.setVisibility(View.GONE);
        }
    }

    private void setClickListeners() {
        binding.btnProceed.setOnClickListener(this);
        binding.tvPlus.setOnClickListener(this);
        binding.txtPlanBenefits.setOnClickListener(this);
        binding.ivNumberEdit.setOnClickListener(this);
        binding.ivDeliveryEdit.setOnClickListener(this);
        binding.ivPlanEdit.setOnClickListener(this);
        binding.ivDeliveryDate.setOnClickListener(this);
        binding.ivEmail.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnProceed:
                if (isOrderPreview) {
                    performPlaceOrderAPICalls();
                } else {
                    gotoTrackActivity();
                }
                break;
            case R.id.tvPlus:
            case R.id.txtPlanBenefits:
                binding.txtPlanBenefits.setMaxLines(20);
                binding.tvPlus.setVisibility(View.INVISIBLE);
                break;


            case R.id.ivNumberEdit:
                gotoInventoryScreen();
                break;
            case R.id.ivPlanEdit:
                gotoPlanDetailScree();
                break;
            case R.id.ivDeliveryDate:
            case R.id.ivDeliveryEdit:
                if(isPickup)
                    gotoMapActivity();
                else
                gotoShippingAddress();
                break;
            case R.id.ivEmail:
                break;
        }
    }

    private void gotoMapActivity() {
        Intent intent = new Intent(this, ServiceDetailActivity.class);
       // intent.putExtra(AddAddressActivity.FROM, OrderDetailsReviewActivity.class.getName());
    //    intent.putExtra(AddAddressActivity.ARG_IS_SHIPPING_ADDRESS, true);
       // this.address = AppUtils.getObjFromString(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.DELIVERY_ADDRESS, null), Address.class);
      //  intent.putExtra(AddAddressActivity.ADDRESS, address);
        startActivity(intent);
    }

    private void gotoTrackActivity() {
        if (orderDetails != null) {
            Intent intent = new Intent(this, TrackingDetailActivity.class);
            intent.putExtra(TrackingDetailActivity.ARG_ORDER_ID, orderDetails.getDisplayOrderId());
            intent.putExtra(TrackingDetailActivity.ARG_ORDER_DATE, orderDetails.getOrderDate());
            intent.putExtra(TrackingDetailActivity.ARG_ORDER_STATUS, orderDetails.getOrderStatus());
            startActivity(intent);
        }
    }

    private void gotoShippingAddress() {
        Intent intent = new Intent(this, AddAddressActivity.class);
        intent.putExtra(AddAddressActivity.FROM, OrderDetailsReviewActivity.class.getName());
        intent.putExtra(AddAddressActivity.ARG_IS_SHIPPING_ADDRESS, true);
        this.address = AppUtils.getObjFromString(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.DELIVERY_ADDRESS, null), Address.class);
        intent.putExtra(AddAddressActivity.ADDRESS, address);
        startActivity(intent);
    }

    private void gotoPlanDetailScree() {
        Intent intent = new Intent(this, PlanDetailActivity.class);
        intent.putExtra(PlanDetailActivity.ARG_PLAN_TYPE, Constants.PLAN_PREPAID);
        intent.putExtra(PlanDetailActivity.ARG_FROM, OrderDetailsReviewActivity.class.getName());
        startActivityForResult(intent, 101);
    }

    private void gotoInventoryScreen() {
        Intent intent = new Intent(this, InventoryActivity.class);
        intent.putExtra(InventoryActivity.ARG_FROM, OrderDetailsReviewActivity.class.getName());
        startActivityForResult(intent, 102);

    }

    /**
     * to go to otp screen after orderReview
     */


    @Override
    public void setOrderDetails(OrderDetails details) {
        this.orderDetails = details;
        Log.e("OrderDetails",details.toString());

        binding.tvOrderNo.setText(details.getDisplayOrderId());
        binding.tvOrderDate.setText(details.getDeliveryDate());
        binding.tvStatus.setText(details.getOrderStatus());
        binding.tvPlan.setText(details.getPlanName());
        binding.tvPlanAmount.setText(AppUtils.getPriceWithSymbol(details.getPlanAmount()));
        binding.txtPlanBenefits.setText(details.getPlanBenefits());
        binding.tvDeliveryAmount.setText(details.getDeliveryAmount());
        binding.tvNumber.setText(details.getNumber());
        binding.tvNumberAmount.setText(Constants.CURRANCY_SYMBOL+" "+details.getNumberAmount());
        binding.tvSim.setText(details.getSimStatus());
        binding.tvSimAmount.setText(details.getSimAmount());

        binding.tvDeliveryDate.setText(details.getDeliveryDate());
        binding.tvFreeBenefits.setText(details.getExtraPlanDetail());
        //binding.tvEmail.setText(details.getUserEmail());
        binding.tvEmail.setText(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.EMAIL_ID,null));
        binding.tvTotalCost.setText(AppUtils.getPriceWithSymbol(details.getGrandTotal()));
        //binding.tvTotalCost.setText(details.getGrandTotal());
        //        DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.INVENTORY_PRICE,inventory.getPrice());

        if (isPickup){
          //  binding.lbldelivery.setVisibility(View.INVISIBLE);
            binding.lbldelivery.setText(R.string.lbl_pickup_store);
            binding.lbldeliverydate.setVisibility(View.GONE);
            details.setDeliveryType(store.getStoreName());
            binding.tvDelivery.setText(details.getDeliveryType());

        }
        else if(isBB){
            details.setDeliveryType(getString(R.string.lbl_install_address));
            binding.lbldelivery.setVisibility(View.INVISIBLE);
            AppUtils.setAddressToTextViewSmall(this, details.getShippingAddressDetails(), binding.tvDeliveryAddress);
            binding.tvDelivery.setText(details.getDeliveryType());

        }
        else {
            AppUtils.setAddressToTextViewSmall(this, details.getShippingAddressDetails(), binding.tvDeliveryAddress);
            binding.tvDelivery.setText(details.getDeliveryType());

        }if (!details.isTrackable()) {
            binding.btnProceed.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();

    }

    public void performPlaceOrderAPICalls() {
        presenter.getAuthToken();
    }
    @Override
    public void getAuthTokenApiCallSuccess(String authToken) {
        Log.e("AuthTokenApiCallSuc","AuthTokenApiCallSuc :: START" );

        Plan plan = AppUtils.getObjFromString(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.PRODUCT_PLAN, ""),Plan.class);

        if (authToken!=null ){
            DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.AUTH__TOKEN,authToken);
            if(isMMP) {
                presenter.addToCart(plan.getPlanId(),Constants.BUNDLE_TEMPLATE_MMP,"BASIC");
            }else{
                presenter.addToCart(plan.getPlanId(),Constants.BUNDLE_TEMPLATE_NEW_CONNECTION,"BASIC");
            }
        }else{
            AppUtils.someThingWentWrong(this);
        }

    }

    @Override
    public void addToCartApiCallSuccess(AddToCartResponseWsDTO addToCartResponseWsDTO) {
        Log.e("addToCartApiCallSuccess","addToCartApiCallSuccess :: START" );
        String bundledTemplateID = "";
        String productCategory = "ADDON";
        Log.e("isBB",isBB+"");


        if(addToCartResponseWsDTO!=null && addToCartResponseWsDTO.isSuccess()){
                DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.CART_ID,addToCartResponseWsDTO.getCartCode());
                 cartCode=addToCartResponseWsDTO.getCartCode();
            if(isMMP){

                    if(count == 0){
                        Inventory inventory = AppUtils.getObjFromString(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.INVENTORY_DATA,""), Inventory.class);
                        presenter.addInventoryToCart(inventory.getNumber(),inventory.getNumberType());
                    }else {
                        String code = AppPreferencesHelper.MMP_ADDON_CODES+count;
                        String categoryCode = AppPreferencesHelper.MMP_ADDON_CATEGORY+count;
                        Log.e("addToCartApiCallSuccess","count  :: " + count );
                        Log.e("addToCartApiCallSuccess","code  :: " + AppPreferencesHelper.MMP_ADDON_CODES+count);
                        Log.e("addToCartApiCallSuccess","category  :: " + AppPreferencesHelper.MMP_ADDON_CATEGORY+count);
                        count--;
                        presenter.addToCart(DCCMDealerSterlite.getDataManager().get(code, ""),
                                DCCMDealerSterlite.getDataManager().get(categoryCode, ""),
                                productCategory);
                    }

                }else if(isBB){

                    String bbType= DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.BB_TYPE,Constants.TYPE_BROADBAND);
                    Log.e("Broadband",bbType);
                   // if (bbType.equalsIgnoreCase(Constants.TYPE_BROADBAND_MODEM)||bbType.equalsIgnoreCase(Constants.TYPE_IPTV)){
                        setInventoryDetails();
                   // }
                 //   else {
                     /*   BSSDataResponse bssDataResponse = new BSSDataResponse();
                        bssDataResponse.setResponseCode("Success");
                        addInventoryToCartSuccess(bssDataResponse);
                    }*/
                }else{
                    Inventory inventory = AppUtils.getObjFromString(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.INVENTORY_DATA,""), Inventory.class);
                    presenter.addInventoryToCart(inventory.getNumber(),inventory.getNumberType());                }
            }else{
                AppUtils.someThingWentWrong(this);
            }
    }

            private void setInventoryDetails() {

        // ArrayList<Inventory> inventories= new ArrayList<>();
        AppApiHelper2.getWebServices().getInventory(new Callback<InventoryDetailWsDTO>() {
            @Override
            public void success(InventoryDetailWsDTO inventoryDetailWsDTO, Response response) {
                hideProgressBar();
                Log.e("InventoryPresenter" , inventoryDetailWsDTO + " ");
             //   ArrayList<Inventory> inventories  =new ArrayList<>();
                /*for (InventoryDetailDTO inv:inventoryDetailWsDTO.getLstInvenotries()) {
                    inventories.add(getInventory(inv));
                    for (InventoryDetailDTO invInn:inv.getVariants()) {
                        inventories.add(getInventory(invInn));
                    }
                }*/
                Log.e("InventoryNumber" , inventoryDetailWsDTO.getLstInvenotries().get(0).getInventoryNumber());
                Log.e("InventoryType" , inventoryDetailWsDTO.getLstInvenotries().get(0).getVanityCategory()+" ");
                presenter.addInventoryToCart(inventoryDetailWsDTO.getLstInvenotries().get(0).getInventoryNumber(),inventoryDetailWsDTO.getLstInvenotries().get(0).getVanityCategory());
                     }

            @Override
            public void failure(RetrofitError error) {
                hideProgressBar();
                AppUtils.showToast(OrderDetailsReviewActivity.this,getString(R.string.msg_something_went_wrong));

            }

                });



    }



    @Override
    public void addInventoryToCartSuccess(BSSDataResponse bssDataResponse) {
        Log.e("addInventoryToCartSuc","addInventoryToCartSuccess :: START" );

        if (bssDataResponse!=null && bssDataResponse.getResponseCode().equalsIgnoreCase("Success")){
//            DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.INVENTORY_NO,inventory.getNumber());
            presenter.setDeliveryAddress(address);
        }else{
            AppUtils.someThingWentWrong(this);
        }
    }

    @Override
    public void setDeliveryAddressApiCallSuccess(BSSDataResponse bssDataResponse) {
        Log.e("setDelAddApiCallSuc","addInventoryToCartSuccess :: START" );

        if (bssDataResponse!=null && bssDataResponse.getResponseCode().equalsIgnoreCase("0")){
//            DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.INVENTORY_NO,inventory.getNumber());
            presenter.setDeliveryMode();
        }else{
            AppUtils.someThingWentWrong(this);
        }
    }

    @Override
    public void setDeliveryModeApiCallSuccess(BSSDataResponse bssDataResponse) {
        if (bssDataResponse!=null && bssDataResponse.getResponseCode().equalsIgnoreCase("0")){
//            DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.INVENTORY_NO,inventory.getNumber());
            presenter.placeOrder();
        }else{
            AppUtils.someThingWentWrong(this);
        }
    }

    @Override
    public void placeOrderApiCallSuccess(OrderWsDTO orderWsDTO) {
        Log.e("placeOrderApiCallSuc","placeOrderApiCallSuccess :: START" );
        if (orderWsDTO!=null &&
                orderWsDTO.getCode()!=null &&
                !orderWsDTO.getCode().isEmpty()){
            //DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.INVENTORY_NO,inventory.getNumber());
            //order placed
            presenter.deleteCart(cartCode);
           // findSubscriptionbyUserId(orderWsDTO.getCode());

            AppUtils.removePrefrences();

            Intent intent = new Intent(this, InputPaymentInformationActivity.class);
            intent.putExtra(OrderReceiveSuccess.ARG_FROM, Constants.PLACEORDER);

            intent.putExtra(OrderReceiveSuccess.ARG_ORDER_NO, orderWsDTO.getCode());
            intent.putExtra(OrderReceiveSuccess.ARG_TRANSACTION_NO, "TR/"+orderWsDTO.getCode());
            intent.putExtra(OrderReceiveSuccess.ARG_PAID, orderDetails.getGrandTotal());
            intent.putExtra(OrderReceiveSuccess.ARG_SUCCESS_MESSAGE, getString(R.string.lbl_order_placed_successfully));
            intent.putExtra(OrderReceiveSuccess.ARG_IS_TRACK, getIntent().getBooleanExtra(OrderReceiveSuccess.ARG_IS_TRACK, false));
            intent.putExtra(TrackingDetailActivity.ARG_ORDER_ID, orderWsDTO.getCode());
            intent.putExtra(TrackingDetailActivity.ARG_ORDER_DATE, orderWsDTO.getCreated());
            intent.putExtra(TrackingDetailActivity.ARG_ORDER_STATUS, orderWsDTO.getStatusDisplay());
            startActivity(intent);
            finishActivity();

        }else{
            AppUtils.someThingWentWrong(this);
        }
    }

    @Override
    public void finishActivity() {
        finish();
    }


    public void findSubscriptionbyUserId(String orderNo)
    {
       /* String userId=DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID,null);
        DCCMDealerSterlite.getDataManager().findSubscriptionByEmailId(userId,this);*/
       ActiviationPresenter activiationPresenter = new ActiviationPresenter();
       activiationPresenter.getOrderDNNumber(orderNo);

    }

    @Override
    public void onSuccess(FindSubscriptionByEmailModel baseResponse) {
        DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.PREF_SUBSCRIPTION_NUMBER,baseResponse.getDnNumber());

    }

    @Override
    public void onFailed(int code, String message) {

    }
}

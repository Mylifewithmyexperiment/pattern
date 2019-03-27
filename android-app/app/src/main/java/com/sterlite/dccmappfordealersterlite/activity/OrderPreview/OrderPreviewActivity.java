package com.sterlite.dccmappfordealersterlite.activity.OrderPreview;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.activity.Address.AddAddressActivity;
import com.sterlite.dccmappfordealersterlite.activity.OrederReceiveSuccess.OrderReceiveSuccess;
import com.sterlite.dccmappfordealersterlite.activity.Otp.OtpActivity;
import com.sterlite.dccmappfordealersterlite.activity.planDetail.PlanDetailActivity;
import com.sterlite.dccmappfordealersterlite.adapter.OrderDetailsItemsAdapter;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityOrderPreviewBinding;
import com.sterlite.dccmappfordealersterlite.model.CartItem;
import com.sterlite.dccmappfordealersterlite.model.OrderDetails;

public class OrderPreviewActivity extends BaseActivity implements OrderPreViewContract.View, View.OnClickListener {
    public static final String IS_SKIP_OTP = "isSkipOtp";
    OrderPreViewContract.Presenter<OrderPreViewContract.View> presenter;
    ActivityOrderPreviewBinding binding;
    OrderDetailsItemsAdapter adapter;
    OrderDetails details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        setAppTheme();
        init();
    }

    private void init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_preview);
        setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.title_order_preview), true);
        initUI();
        setClickListeners();
        presenter = new OrderPreviewPresenter<>();
        presenter.onAttach(this);
        presenter.init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.reset();
    }

    private void initUI() {
        adapter = new OrderDetailsItemsAdapter(this, new OnRecyclerViewItemClickListener<CartItem>() {
            @Override
            public void onClicked(CartItem bean, View view, int position, ViewType viewType) {
                AppUtils.showUnderDevelopmentToast(OrderPreviewActivity.this);
            }

            @Override
            public void onLastItemReached() {

            }
        });
        adapter.isAddFooter = false;
        binding.rvProductList.setAdapter(adapter);
    }

    private void setClickListeners() {
//        binding.btnChangeBillingAddress.setOnClickListener(this);
//        binding.btnChangeShippingAddress.setOnClickListener(this);
        binding.btnProceed.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnProceed:
                presenter.placeOrder();
                break;
//            case R.id.btnChangeBillingAddress:
//                gotoBillingAddress();
//                break;
//            case R.id.btnChangeShippingAddress:
//                gotoShippingAddress();
//                break;
        }
    }

    private void gotoShippingAddress() {
        Intent intent = new Intent(this, AddAddressActivity.class);
        intent.putExtra(AddAddressActivity.FROM, OrderPreviewActivity.class.getName());
        intent.putExtra(AddAddressActivity.ARG_IS_SHIPPING_ADDRESS, true);
        intent.putExtra(AddAddressActivity.ADDRESS, details.getShippingAddressDetails());
        startActivity(intent);
    }

    private void gotoBillingAddress() {
        Intent intent = new Intent(this, AddAddressActivity.class);
        intent.putExtra(AddAddressActivity.FROM, OrderPreviewActivity.class.getName());
        intent.putExtra(AddAddressActivity.ADDRESS, details.getBillingAddressDetails());
        startActivity(intent);
    }

    private void gotoPlanDetailScree() {
        Intent intent = new Intent(this, PlanDetailActivity.class);
        intent.putExtra(PlanDetailActivity.ARG_PLAN_TYPE, Constants.PLAN_PREPAID);
        intent.putExtra(PlanDetailActivity.ARG_FROM, OrderPreviewActivity.class.getName());
        startActivityForResult(intent, 101);
    }

    @Override
    public void setOrderDetails(OrderDetails details) {
        this.details = details;


        /*if (details.getProductDetails() == null || details.getProductDetails().size() <= 0) {
            binding.tvLblShipmentDetails.setVisibility(View.GONE);
            binding.relProductContainer.setVisibility(View.GONE);
        } else {*/
        adapter.removeAll();
        adapter.addItems(details.getProductDetails());
        /*}*/
        if (details.getStoreAddressDetails() != null) {


            AppUtils.setAddressToTextView(this, details.getStoreAddressDetails(), binding.tvStoreAddress);
            binding.tvStoreName.setText(details.getStoreName());

            binding.relStoreAddressContainer.setVisibility(View.VISIBLE);
            binding.tvLblStoreAddress.setVisibility(View.VISIBLE);


            binding.relBillAddressContainer.setVisibility(View.GONE);
            binding.tvLblBillingAddress.setVisibility(View.GONE);
            binding.relShipAddressContainer.setVisibility(View.GONE);
            binding.tvLblShippingAddress.setVisibility(View.GONE);
        } else {
            if (details.getBillingAddressDetails() == null) {
                binding.relBillAddressContainer.setVisibility(View.GONE);
                binding.tvLblBillingAddress.setVisibility(View.GONE);
            } else {
                binding.relBillAddressContainer.setVisibility(View.VISIBLE);
                binding.tvLblBillingAddress.setVisibility(View.VISIBLE);
                binding.tvBillTo.setText(details.getOrderBillTo());
                AppUtils.setAddressToTextView(this, details.getBillingAddressDetails(), binding.tvBillingAddress);
            }
            if (details.getShippingAddressDetails() == null) {
                binding.relShipAddressContainer.setVisibility(View.GONE);
                binding.tvLblShippingAddress.setVisibility(View.GONE);
            } else {
                binding.relShipAddressContainer.setVisibility(View.VISIBLE);
                binding.tvLblShippingAddress.setVisibility(View.VISIBLE);
                binding.tvShipTo.setText(details.getOrderShipTo());
                AppUtils.setAddressToTextView(this, details.getShippingAddressDetails(), binding.tvShippingAddress);
            }
            binding.relStoreAddressContainer.setVisibility(View.GONE);
            binding.tvLblStoreAddress.setVisibility(View.GONE);
        }
        binding.tvShippingInformation.setText(details.getShippingMethod());
        binding.tvItemTotal.setText(details.getItemTotal());
        if (!TextUtils.isEmpty(details.getShippingAmount())) {
            binding.llShippingChargesContainer.setVisibility(View.VISIBLE);
            binding.llTotalBeforeTaxContainer.setVisibility(View.VISIBLE);
            binding.tvShippingCharges.setText(details.getShippingAmount());
            binding.tvTotalBeforeTax.setText(details.getTotalBeforeTax());
        } else {
            binding.llShippingChargesContainer.setVisibility(View.GONE);
            binding.llTotalBeforeTaxContainer.setVisibility(View.GONE);
        }

        binding.tvTax.setText(details.getTaxAmount());

        binding.tvGrandOrderTotal.setText(details.getOrderTotal());

    }

    @Override
    public void gotoOtpScreen() {
        if (getIntent().getBooleanExtra(IS_SKIP_OTP, false)) {
            Intent intent = new Intent(this, OrderReceiveSuccess.class);
            intent.putExtra(OrderReceiveSuccess.ARG_ORDER_NO, "0987654321");
            intent.putExtra(OrderReceiveSuccess.ARG_TRANSACTION_NO, "0987654321");
            intent.putExtra(OrderReceiveSuccess.ARG_PAID, AppUtils.getPriceWithSymbol("100.00"));
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, OtpActivity.class);
            intent.putExtra(OtpActivity.ARG_LAST_THREE_DIGITS_OR_EMAIL_END, "951");
            intent.putExtra(OrderReceiveSuccess.ARG_IS_TRACK, true);
            intent.putExtra(OtpActivity.ARG_FROM,OrderPreviewActivity.class.getName());
            startActivity(intent);
        }

    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();

    }
}

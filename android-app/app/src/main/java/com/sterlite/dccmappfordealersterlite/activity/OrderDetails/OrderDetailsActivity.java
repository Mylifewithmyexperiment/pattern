package com.sterlite.dccmappfordealersterlite.activity.OrderDetails;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.activity.trackingDetail.TrackingDetailActivity;
import com.sterlite.dccmappfordealersterlite.adapter.OrderDetailsItemsAdapter;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityOrderDetailsBinding;
import com.sterlite.dccmappfordealersterlite.model.CartItem;
import com.sterlite.dccmappfordealersterlite.model.OrderDetails;

public class OrderDetailsActivity extends BaseActivity implements OrderDetailsContract.View, View.OnClickListener {
    OrderDetailsContract.Presenter<OrderDetailsContract.View> presenter;
    ActivityOrderDetailsBinding binding;
    OrderDetailsItemsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        setAppTheme();
        init();
    }

    private void init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_details);
        setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.title_order_details), true);
        initUI();
        setClickListeners();
        presenter = new OrderDetailsPresenter<>();
        presenter.onAttach(this);
        presenter.init();
    }

    private void initUI() {
        adapter = new OrderDetailsItemsAdapter(this, new OnRecyclerViewItemClickListener<CartItem>() {
            @Override
            public void onClicked(CartItem bean, View view, int position, ViewType viewType) {
                AppUtils.showUnderDevelopmentToast(OrderDetailsActivity.this);
            }

            @Override
            public void onLastItemReached() {

            }
        });
        adapter.isAddFooter = false;
        binding.rvProductList.setAdapter(adapter);
    }

    private void setClickListeners() {
        binding.btnProceed.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnProceed:
                gotoTrackActivity();
                break;
        }
    }

    private void gotoTrackActivity() {
        Intent intent=new Intent(this, TrackingDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void setOrderDetails(OrderDetails details) {
        binding.tvOrderDate.setText(details.getOrderDate());
        binding.tvOrderNo.setText(details.getDisplayOrderId());
        binding.tvOrderTotal.setText(details.getOrderTotal());
        binding.tvOrderStatus.setText(details.getOrderStatus());
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
            if (details.getBillingAddressDetails()==null) {
                binding.relBillAddressContainer.setVisibility(View.GONE);
                binding.tvLblBillingAddress.setVisibility(View.GONE);
            } else {
                binding.relBillAddressContainer.setVisibility(View.VISIBLE);
                binding.tvLblBillingAddress.setVisibility(View.VISIBLE);
                binding.tvBillTo.setText(details.getOrderBillTo());
                AppUtils.setAddressToTextView(this, details.getBillingAddressDetails(),  binding.tvBillingAddress);
            }
            if (details.getShippingAddressDetails()==null) {
                binding.relShipAddressContainer.setVisibility(View.GONE);
                binding.tvLblShippingAddress.setVisibility(View.GONE);
            } else {
                binding.relShipAddressContainer.setVisibility(View.VISIBLE);
                binding.tvLblShippingAddress.setVisibility(View.VISIBLE);
                binding.tvShipTo.setText(details.getOrderShipTo());
                AppUtils.setAddressToTextView(this,details.getShippingAddressDetails(),binding.tvShippingAddress);
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
        }else{
            binding.llShippingChargesContainer.setVisibility(View.GONE);
            binding.llTotalBeforeTaxContainer.setVisibility(View.GONE);
        }
        binding.tvTax.setText(details.getTaxAmount());
        binding.tvGrandOrderTotal.setText(details.getOrderTotal());
        if (!details.isTrackable()) {
            binding.btnProceed.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();

    }
}

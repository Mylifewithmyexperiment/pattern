package com.sterlite.dccmappfordealersterlite.activity.trackingDetail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.adapter.TrackingDetailAdapter;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityTrackingDetailBinding;
import com.sterlite.dccmappfordealersterlite.model.OrderTracking.OrderTrackingResponseModel;
import com.sterlite.dccmappfordealersterlite.model.Track;


public class TrackingDetailActivity extends BaseActivity implements TrackingDetailContract.View {


    private TrackingDetailAdapter trackingDetailAdapter;
    private ActivityTrackingDetailBinding binding;
    private TrackingDetailContract.Presenter<TrackingDetailContract.View> presenter;
    private String orderID,invNumber,orderStatus;

    public static final String ARG_ORDER_ID = "argTrackingDetailActivityOrderId";
    public static final String ARG_ORDER_DATE = "argTrackingDetailActivityOrderDate";
    public static final String ARG_ORDER_STATUS = "argTrackingDetailActivityOrderStatus";
    public static final String ARG_INVENTORY_NUMBER = "argTrackingDetailActivityInvNum";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        setAppTheme();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tracking_detail);
        setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.title_tracking_detail), true);
        presenter = new TrackingDetailPresenter<>();
        presenter.onAttach(this);
        initUI();

    }

    private void initUI() {
        binding.lldnNum.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        if (intent.hasExtra(ARG_ORDER_ID)) {
            binding.tvOrderNo.setText(intent.getStringExtra(ARG_ORDER_ID));
            orderID = intent.getStringExtra(ARG_ORDER_ID);
        }
        if (intent.hasExtra(ARG_ORDER_DATE))
            binding.tvSelectedNo.setText(intent.getStringExtra(ARG_ORDER_DATE));
         if (intent.hasExtra(ARG_INVENTORY_NUMBER)) {
             invNumber=intent.getStringExtra(ARG_INVENTORY_NUMBER);
             binding.lldnNum.setVisibility(View.VISIBLE);
         }else{
             binding.lldnNum.setVisibility(View.GONE);
         }
        if (intent.hasExtra(ARG_ORDER_STATUS))
            orderStatus=intent.getStringExtra(ARG_ORDER_STATUS);
        if (orderID != null) {
           // orderID = "DCHD30081800000";
            presenter.init(orderID);
        }

    }


    @Override
    public void setUpView() {
        trackingDetailAdapter = new TrackingDetailAdapter(this);
        binding.rvTrack.setAdapter(trackingDetailAdapter);
    }

    @Override
    public void loadDataToView(OrderTrackingResponseModel responseModel) {
        if (responseModel.getDisplayOrderStatus()!=null) {
            binding.tvDeliveryDetail.setText(responseModel.getDisplayOrderStatus());
            binding.lldnNum.setVisibility(View.INVISIBLE);
            if (responseModel.getCurrentOrderStatus().equalsIgnoreCase("Notify CRM") && responseModel.getDisplayOrderStatus().equalsIgnoreCase("Order Completed")){
                binding.tvOrderStatus.setText("Completed");
            }
            else
                binding.tvOrderStatus.setText(orderStatus);

        }
        else {
            binding.tvDeliveryDetail.setText(responseModel.getCurrentOrderStatus());
            binding.tvDNumber.setText(invNumber);
            binding.tvOrderStatus.setText(orderStatus);

        }
        if (trackingDetailAdapter != null) {
            trackingDetailAdapter.addItem((ArrayList<Track>) responseModel.getTrackArrayList());
        }
    }

    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

}

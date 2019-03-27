package com.sterlite.dccmappfordealersterlite.activity.OrederReceiveSuccess;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.activity.Feedback.UserFeedbackActivity;
import com.sterlite.dccmappfordealersterlite.activity.LandingPage.LandingPageActivity;
import com.sterlite.dccmappfordealersterlite.activity.trackingDetail.TrackingDetailActivity;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityOrderReceiveSuccessBinding;

public class OrderReceiveSuccess extends BaseActivity implements View.OnClickListener {
    public static final String ARG_FROM ="ordersuccess" ;
    ActivityOrderReceiveSuccessBinding binding;
    public final static String ARG_ORDER_NO = "argOrderNo";
    public final static String ARG_TRANSACTION_NO = "argTransactionNo";
    public final static String ARG_PAID = "argPaid";
    public final static String ARG_SUCCESS_MESSAGE = "argSuccessMessage";
    public final static String ARG_IS_TRACK = "argTrack";
    public final static String ARG_IS_RECHARGE = "argRecharge";

    private boolean isTrack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        setAppTheme();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_receive_success);
        setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.title_thank_you), true);

        initUI();
        setClickListners();

    }

    private void initUI() {
        String orderNo = getIntent().getStringExtra(ARG_ORDER_NO);
        String transactionNo = getIntent().getStringExtra(ARG_TRANSACTION_NO);
        String paid = getIntent().getStringExtra(ARG_PAID);
        String successMessage = getIntent().getStringExtra(ARG_SUCCESS_MESSAGE);
        isTrack = getIntent().getBooleanExtra(ARG_IS_TRACK, false);
        if (TextUtils.isEmpty(orderNo)) {
            binding.llOrederNoContainer.setVisibility(View.GONE);
        } else {
            binding.tvOrderNo.setText(orderNo);
        }
        if (TextUtils.isEmpty(transactionNo)) {
            binding.llTransactionContainer.setVisibility(View.GONE);
        } else {
            binding.tvTransactionNo.setText(transactionNo);
        }
        if (TextUtils.isEmpty(paid)) {
            binding.llPaidContainer.setVisibility(View.GONE);
        } else {
            binding.tvPaid.setText(AppUtils.getPriceWithSymbol(this,paid));
        }
        if (!TextUtils.isEmpty(successMessage)) {
            binding.tvKycSuccessMessage.setText(successMessage);
        }

        if (isTrack) {
            binding.btnProceed.setText(getString(R.string.btn_track));
        }
    }

    private void setClickListners() {
        binding.btnProceed.setOnClickListener(this);
        binding.toolbar.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectUserToScreen();
            }
        });
    }

   /* public void redirectUserToScreen() {
        try {
            if (AppUtils.isUserRegistered())
                gotoDashBoard();
            else
              gotoLandingPage();
        } catch (Exception e) {
            Log.e("OrderRecSucc" , e + " ");
        }
    }*/

    public void redirectUserToScreen() {
        Intent newIntent = new Intent(this, UserFeedbackActivity.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(newIntent);
        finish();
    }

    private void gotoLandingPage() {
        Intent newIntent = new Intent(this, LandingPageActivity.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(newIntent);
    }

    private void gotoDashBoard() {
        Intent newIntent = new Intent(OrderReceiveSuccess.this, com.sterlite.dccmappfordealersterlite.activity.dashboard.DashBoardActivity.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        newIntent.putExtra(com.sterlite.dccmappfordealersterlite.activity.dashboard.DashBoardActivity.ARG_CURRENT, "2");
        startActivity(newIntent);
    }

    @Override
    public void onClick(View v) {
        if (Constants.IS_DUMMY_MODE) {
            redirectUserToScreen();
            if (isTrack) {
                Intent intent=new Intent(OrderReceiveSuccess.this, TrackingDetailActivity.class);
                intent.putExtra(TrackingDetailActivity.ARG_ORDER_ID, getIntent().getStringExtra(TrackingDetailActivity.ARG_ORDER_ID));
                intent.putExtra(TrackingDetailActivity.ARG_ORDER_DATE, getIntent().getStringExtra(TrackingDetailActivity.ARG_ORDER_DATE));
                intent.putExtra(TrackingDetailActivity.ARG_ORDER_STATUS, getIntent().getStringExtra(TrackingDetailActivity.ARG_ORDER_STATUS));
                startActivity(intent);
            }
        }

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        redirectUserToScreen();
    }
}

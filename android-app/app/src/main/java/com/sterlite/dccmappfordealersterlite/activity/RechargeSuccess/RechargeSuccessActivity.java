package com.sterlite.dccmappfordealersterlite.activity.RechargeSuccess;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.activity.Address.AddAddressActivity;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityRechargeSuccessBinding;

public class RechargeSuccessActivity extends BaseActivity implements View.OnClickListener {
    ActivityRechargeSuccessBinding binding;
    public final static String ARG_ORDER_NO = "argOrderNo";
    public final static String ARG_TRANSACTION_NO = "argTransactionNo";
    public final static String ARG_PAID = "argPaid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAppTheme();
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recharge_success);
        setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.title_recharge_success), false);
        setClickListners();
    }


    private void setClickListners() {
        binding.btnProceed.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (Constants.IS_DUMMY_MODE) {
            Intent newIntent = new Intent(RechargeSuccessActivity.this, com.sterlite.dccmappfordealersterlite.activity.dashboard.DashBoardActivity.class);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(newIntent);
        }

    }

    private void gotoPinAskScreeen() {
//        todo implementation pending
    }

    private void gotoAddressScreens() {
//        todo goto address screens
        startActivity(new Intent(this, AddAddressActivity.class));
        finish();
    }
}

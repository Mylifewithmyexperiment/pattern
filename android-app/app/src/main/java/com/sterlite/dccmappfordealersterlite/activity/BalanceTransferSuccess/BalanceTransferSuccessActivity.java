package com.sterlite.dccmappfordealersterlite.activity.BalanceTransferSuccess;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityBalanceTransferSuccessBinding;

public class BalanceTransferSuccessActivity extends BaseActivity implements View.OnClickListener {
    ActivityBalanceTransferSuccessBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        setAppTheme();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_balance_transfer_success);
        setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.title_balance_transfer), false);
        setClickListners();
    }


    private void setClickListners() {
        binding.btnProceed.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (Constants.IS_DUMMY_MODE) {
            Intent newIntent = new Intent(BalanceTransferSuccessActivity.this, com.sterlite.dccmappfordealersterlite.activity.dashboard.DashBoardActivity.class);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(newIntent);
        }

    }

}

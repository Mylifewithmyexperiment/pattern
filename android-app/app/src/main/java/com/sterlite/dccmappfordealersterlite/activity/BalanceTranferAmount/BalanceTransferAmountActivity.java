package com.sterlite.dccmappfordealersterlite.activity.BalanceTranferAmount;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityBalanceTransferBinding;


public class BalanceTransferAmountActivity extends BaseActivity implements BalanceTransferAmountContract.View {

    private ActivityBalanceTransferBinding binding;

    private BalanceTransferAmountContract.Presenter<BalanceTransferAmountContract.View> presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        setAppTheme();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_balance_transfer);
        setUpNavigationView(binding.drawerLayout,binding.layoutNavMenu);
        setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.title_balance_transfer), true);

        presenter = new BalanceTransferAmountPresenter<>();
        presenter.onAttach(this);

      /*  binding.btnSendNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(BalanceTransferAmountActivity.this, BalanceTransferSuccessActivity.class);
                startActivity(newIntent);
            }
        });*/
    }



    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }



}

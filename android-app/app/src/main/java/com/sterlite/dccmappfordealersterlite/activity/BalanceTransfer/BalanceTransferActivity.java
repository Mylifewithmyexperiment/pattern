package com.sterlite.dccmappfordealersterlite.activity.BalanceTransfer;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.activity.BalanceSummery.BalanceTransferSummeryActivity;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityBalanceTransferBinding;
import com.sterlite.dccmappfordealersterlite.model.Recharge;


public class BalanceTransferActivity extends BaseActivity implements BalanceTransferContract.View, View.OnClickListener {

    private ActivityBalanceTransferBinding binding;

    private BalanceTransferContract.Presenter<BalanceTransferContract.View> presenter;
    private Recharge transfer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        setAppTheme();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_balance_transfer);
        setUpNavigationView(binding.drawerLayout, binding.layoutNavMenu);
        setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.title_balance_transfer), true);
        setErrorListener();
        setClickListner();

        binding.tvBalance.setText(AppUtils.getPriceWithSymbol(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.BALANCE,"0")));
        binding.tvBalance.setFocusable(false);
        presenter = new BalanceTransferPresenter<>();
        presenter.onAttach(this);

       /* binding.btnSendNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(BalanceTransferActivity.this, BalanceTransferAmountActivity.class);
                startActivity(newIntent);
            }
        });*/
    }

    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    private void setClickListner() {
        binding.btnContinue.setOnClickListener(this);
    }

    private void setErrorListener() {
        AppUtils.addTextChangedListener(binding.edtNumber, binding.iedtNumber);
        AppUtils.addTextChangedListener(binding.edtAmount, binding.iedtAmount);
        AppUtils.addTextChangedListener(binding.edtComment, binding.iedtComment);

        AppUtils.addTextFocusChangeListener(binding.edtNumber, binding.scrollMainContainer,binding.toolbar.appBar);
        AppUtils.addTextFocusChangeListener(binding.edtAmount, binding.scrollMainContainer,binding.toolbar.appBar);
        AppUtils.addTextFocusChangeListener(binding.edtComment, binding.scrollMainContainer,binding.toolbar.appBar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            if (AppUtils.isUserRegistered()) {
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.activity_user_detail_menu, menu);
                return true;
            } else {
                if (drawer != null)
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,GravityCompat.END);
                return super.onCreateOptionsMenu(menu);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return super.onCreateOptionsMenu(menu);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnContinue:
                if (isValidData()) {
                    transfer=new Recharge();
                    transfer.setAmount(binding.edtAmount.getText().toString());
                    transfer.setNumber(binding.edtNumber.getText().toString());
                    if(binding.edtComment.getText()!=null)
                    transfer.setComment(binding.edtComment.getText().toString().trim());
                    gotoRechargeSummaryScreen();
                }
                break;
        }
    }
    private void gotoRechargeSummaryScreen() {
        if (transfer != null) {
            Intent newIntent = new Intent(BalanceTransferActivity.this, BalanceTransferSummeryActivity.class);
            newIntent.putExtra(BalanceTransferSummeryActivity.ARF_TRANSFER, transfer);
            startActivity(newIntent);
        }

    }

    private boolean isValidData() {
        if (binding.edtNumber.getText() == null || TextUtils.isEmpty(binding.edtNumber.getText().toString().trim())) {
            binding.iedtNumber.setError(getString(R.string.val_enter_mobile_no));
            binding.edtNumber.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.edtNumber.getY());
            return false;
        } else if (AppUtils.validateMobileNoLength(binding.edtNumber.getText().toString().trim().length())) {
            binding.iedtNumber.setError(getString(R.string.val_enter_valid_mobile_no));
            binding.edtNumber.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.edtNumber.getY());
            return false;
        } else if (binding.edtAmount.getText() == null || TextUtils.isEmpty(binding.edtAmount.getText().toString().trim())) {
            binding.iedtAmount.setError(getString(R.string.val_enter_mobile_no));
            binding.edtAmount.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.edtAmount.getY());
            return false;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_right_menu:
//                Toast.makeText(this, "MENU", Toast.LENGTH_LONG).show();
                binding.drawerLayout.openDrawer(GravityCompat.END);
                return true;

            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
}

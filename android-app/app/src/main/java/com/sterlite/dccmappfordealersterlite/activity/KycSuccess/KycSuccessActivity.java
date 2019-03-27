package com.sterlite.dccmappfordealersterlite.activity.KycSuccess;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.activity.Address.AddAddressActivity;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityKycSuccessBinding;

public class KycSuccessActivity extends BaseActivity implements View.OnClickListener {
    ActivityKycSuccessBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        setAppTheme();
        binding= DataBindingUtil.setContentView(this,R.layout.activity_kyc_success);
        setUpView(binding.toolbar.toolbar,binding.extraView,getString(R.string.title_kyc),false);
        setClickListners();
    }

    private void setClickListners() {
        binding.btnProceed.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(Constants.IS_DUMMY_MODE){
            if(DCCMDealerSterlite.getDataManager().getBoolean(AppPreferencesHelper.PREF_IS_HOME_DELIVERY,false)){
                gotoAddressScreens();
            }else{
                gotoPinAskScreeen();
            }
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

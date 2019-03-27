package com.sterlite.dccmappfordealersterlite.activity.pincode;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.activity.Address.AddAddressActivity;
import com.sterlite.dccmappfordealersterlite.activity.Kyc.KycActivity;
import com.sterlite.dccmappfordealersterlite.activity.ServiceDetail.ServiceDetailActivity;
import com.sterlite.dccmappfordealersterlite.activity.planDetail.PlanDetailActivity;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityPincodeBinding;

import static com.sterlite.dccmappfordealersterlite.activity.Address.AddAddressActivity.ARG_IS_SHIPPING_ADDRESS;


public class PincodeActivity extends BaseActivity implements PincodeContract.View, View.OnClickListener {
    public static final String ARG_FROM = "argFrom";
    private PincodeContract.Presenter<PincodeContract.View> presenter;
    private ActivityPincodeBinding binding;
    private static final String PINCODE = "pincode";
    private String pin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        setAppTheme();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pincode);
        String from = getIntent().getStringExtra(ARG_FROM);
        if (!AppUtils.isEmpty(from) && from.equals(PlanDetailActivity.class.getName())) {
            binding.btnValidate.setSelected(true);
            binding.rlType.setVisibility(View.GONE);
            binding.prepaidImage.setVisibility(View.GONE);
            binding.lbldeliverSim.setText(R.string.lbl_feasible_to);
            setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.title_feasibility), true);

        }else if(DCCMDealerSterlite.getDataManager().getBoolean(AppPreferencesHelper.IS_BB, false)){
            binding.btnValidate.setSelected(true);
            binding.rlType.setVisibility(View.GONE);
            binding.prepaidImage.setVisibility(View.GONE);
            binding.lbldeliverSim.setText(R.string.lbl_deliverable_to);

            setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.title_pincode), true);
        }else{
            setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.title_pincode), true);
        }
        String theme=DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.APP_THEME, Constants.DEFAULT_APP_THEME);
        if (theme.equalsIgnoreCase(Constants.APP_THEME)){
            binding.prepaidImage.setImageResource(R.drawable.banner1_red_prepaid);
        }
        else {
            binding.prepaidImage.setImageResource(R.drawable.banner1_blue_prepaid);

        }
      /*  if(DCCMDealerSterlite.getDataManager().getBoolean(AppPreferencesHelper.IS_BB, false)){
            binding.prepaidImage.setVisibility(View.INVISIBLE);

        }*/

        init();
    }

    private void init() {
        presenter = new PincodePresenter<>();
        presenter.onAttach(this);
        binding.btnValidate.setOnClickListener(this);
        binding.btnPickUpFromStore.setOnClickListener(this);
        AppUtils.addTextChangedListener(binding.etPincode, binding.ilPincode);
        AppUtils.addTextFocusChangeListener(binding.etPincode, binding.scrollMainContainer,binding.toolbar.appBar);


    }


    private boolean isValidData() {
        if ((binding.etPincode.getText() == null
                || TextUtils.isEmpty(binding.etPincode.getText().toString().trim()))
                || binding.etPincode.getText().toString().trim().length() < 3) {
            binding.ilPincode.setError(getString(R.string.val_invaild_pincode));
            binding.etPincode.requestFocus();
            return false;
        }
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnValidate:
                if (isValidData()) {
                    //if(binding.etPincode.getText().toString().trim().length() == 5) {
                       // pin =   padLeftZeros(binding.etPincode.getText().toString().trim(),6);
                  //  }
                   // else {
                        pin = binding.etPincode.getText().toString().trim();
                  //  }
                    if (binding.rbHomeDelivery.isChecked()) {
                        presenter.doValidation(pin);
                    } else {
                        Intent intent = new Intent(PincodeActivity.this,ServiceDetailActivity.class);
                        intent.putExtra(Constants.PIN_CODE,pin);
                        startActivity(intent);


                    }
                }
                break;

            case R.id.btnPickUpFromStore:
                startActivity(new Intent(this, ServiceDetailActivity.class));
                break;
        }
    }


    public static String padLeftZeros(String str, int n) {
        return String.format("%1$" + n + "s", str).replace(' ', '0');
    }


    @Override
    public void onSuccessValidate() {
        String from = getIntent().getStringExtra(ARG_FROM);
        if (!AppUtils.isEmpty(from) && from.equals(PlanDetailActivity.class.getName())) {
            startActivity(new Intent(this, KycActivity.class));
        }else {
            Intent intent = new Intent(this, AddAddressActivity.class);
            intent.putExtra(ARG_IS_SHIPPING_ADDRESS, true);
            intent.putExtra(AddAddressActivity.ARG_PIN, pin);
            startActivity(intent);
        }
    }
    @Override
    public void onFailValidate() {
        Log.e("pincodeActivity","pincode validation failed");
        AppUtils.showToast(this,getString(R.string.serviceisnotfeasible));
    }
    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }
}


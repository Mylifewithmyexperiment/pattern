package com.sterlite.dccmappfordealersterlite.activity.kycdetail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Calendar;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.activity.Address.AddAddressActivity;
import com.sterlite.dccmappfordealersterlite.activity.pincode.PincodeActivity;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityKycDetailBinding;
import com.sterlite.dccmappfordealersterlite.model.Address;

import static com.sterlite.dccmappfordealersterlite.activity.Address.AddAddressActivity.ARG_IS_SHIPPING_ADDRESS;

public class KYCDetailActivity extends BaseActivity implements KYCReviewContract.View, View.OnClickListener {

    public static final String ARG_KYC_NUMBER = "argKYCDetailActivityKycNumber";

    KYCReviewContract.Presenter<KYCReviewContract.View> presenter;
    ActivityKycDetailBinding binding;
    private Address address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        setAppTheme();
        init();
    }

    private void init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_kyc_detail);
        setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.title_kyc), true);
        initUI();
        setClickListeners();
        presenter = new KYCDetailsPresenter<>();
        presenter.onAttach(this);
        String kycNumber = getIntent().getStringExtra(ARG_KYC_NUMBER);
        binding.tvNumber.setText(kycNumber);
        presenter.init(kycNumber, this);
        if(DCCMDealerSterlite.getDataManager().getBoolean(AppPreferencesHelper.IS_BB,false)){
        //    binding.chkUseAddress.setChecked(false);
         //   binding.chkUseAddress.setVisibility(View.GONE);
        }
    }

    /* @Override
     protected void onResume() {
         super.onResume();
         String kycNumber = getIntent().getStringExtra(ARG_KYC_NUMBER);
         presenter.init(kycNumber, this);
     }
 */
    private void initUI() {

    }

    private void setClickListeners() {
        binding.btnProceed.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnProceed:
                if (!binding.chkUseAddress.isChecked()) {
                    if(DCCMDealerSterlite.getDataManager().getBoolean(AppPreferencesHelper.IS_BB,false)
                            && DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.BB_TYPE, "").equals(Constants.TYPE_BROADBAND)){
                        gotoShippingAddress();
                    }else {
                        gotoPincodeActivity();
                    }
                } else {
                    gotoShippingAddress();
                }
                break;
        }
    }

    private void gotoPincodeActivity() {

        Intent intent = new Intent(this, PincodeActivity.class);
        startActivity(intent);
    }


    private void gotoShippingAddress() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis() + (86400000 * 3));
        address.setDate(calendar);
        address.setIsUseSameForShipping(true);
        Intent intent = new Intent(this, AddAddressActivity.class);
        intent.putExtra(AddAddressActivity.FROM, KYCDetailActivity.class.getName());
        intent.putExtra(ARG_IS_SHIPPING_ADDRESS, true);
        intent.putExtra(AddAddressActivity.ADDRESS, address);
        startActivity(intent);
    }

    @Override
    public void setAddress(Address address) {
        if (address == null)
            return;

        this.address = address;
        Log.e("Address", address.toString());
     //   DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.CUSTOMER_NAME, binding.edtName.getText().toString().trim());

        binding.tvName.setText(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_NAME,address.getFname()));
        binding.tvMobile.setText(address.getMobileNo());
        binding.tvAddress1.setText(address.getAddressLine1());
        binding.tvAddress2.setText(address.getAddressLine2());
        binding.tvCity.setText(address.getCity());
        binding.tvState.setText(address.getState());
        binding.tvCountry.setText(address.getCountry());
        binding.tvPin.setText(address.getPin());
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();

    }


}

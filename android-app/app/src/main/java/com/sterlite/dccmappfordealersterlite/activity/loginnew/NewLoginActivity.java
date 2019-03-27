package com.sterlite.dccmappfordealersterlite.activity.loginnew;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.DecimalFormat;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.Utils.CustomAlertDialog;
import com.sterlite.dccmappfordealersterlite.activity.Activation.ActiviationPresenter;
import com.sterlite.dccmappfordealersterlite.activity.UserDetail.UserDetailActivity;
import com.sterlite.dccmappfordealersterlite.activity.dashboard.DashBoardActivity;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.data.network.ApiHelper;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityLoginNewBinding;
import com.sterlite.dccmappfordealersterlite.model.HomeFragment.BalanceData;
import com.sterlite.dccmappfordealersterlite.model.HomeFragment.SubscriberServicewiseBalance;
import com.sterlite.dccmappfordealersterlite.model.UpdateCustomer.GetUpdateCustomerRequestData;
import com.sterlite.dccmappfordealersterlite.model.UpdateCustomer.GetUpdateCustomerResponseData;
import com.sterlite.dccmappfordealersterlite.model.dto.region.RegionWsDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.user.UserResponseWsDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.validatecustomer.ValidateCustomerWsDTO;
import com.sterlite.dccmappfordealersterlite.service.FCM.MyFirebaseInstanceIdService;


public class NewLoginActivity extends BaseActivity implements LoginContract.View, View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private static final String EMAIL = "email";

    private LoginContract.Presenter<LoginContract.View> presenter;
    private ActivityLoginNewBinding binding;
    private String TAG = "NewLoginActivity";
    private ProgressDialog progressDialog;
    private ValidateCustomerWsDTO validateCustomerWsDTO;
    private RegionWsDTO regionWsDTO;
    private boolean id = false;
    String ARG_FROM;

    //   google login https://developers.google.com/identity/sign-in/android/sign-in?authuser=2
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());

        setAppTheme();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_new);
        if(Constants.APP.equalsIgnoreCase(Constants.MTN)) {
            binding.companyLogo.setImageResource(R.drawable.mtn_logo);
        }else if(Constants.APP.equalsIgnoreCase(Constants.TELKOMSEL)) {

            binding.companyLogo.setImageResource(R.drawable.company_logo);
        }
        else if(Constants.APP.equalsIgnoreCase(Constants.VODAFONE)) {

            binding.companyLogo.setImageResource(R.drawable.ic_vodafone1);
        }
        else
            binding.companyLogo.setVisibility(View.GONE);

        init();
        Intent intent = getIntent();
        id = intent.getBooleanExtra(Constants.PROFILE, false);
        ARG_FROM = intent.getStringExtra(UserDetailActivity.ARG_FROM);

        if (id ) {
            profilePage();
            setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.head_profile_details), true);
            binding.btnLoginSms.setVisibility(View.GONE);
            binding.tvLoginWith.setVisibility(View.GONE);
            binding.tvOR.setVisibility(View.GONE);
            binding.llSocialMedia.setVisibility(View.GONE);
            binding.llSwitch.setVisibility(View.GONE);
            binding.edtEmailId.setFocusable(false);
            binding.edtMobileNo.setFocusable(false);
            binding.edtAltEmailId.setVisibility(View.VISIBLE);
            binding.edtAltMobileNo.setVisibility(View.VISIBLE);
            binding.btnUpdate.setVisibility(View.VISIBLE);
            binding.edtAltEmailId.setFocusable(true);
            binding.edtAltMobileNo.setFocusable(true);

            //binding.edtAltMobileNo.set
            //    binding.edtCity.setFocusable(false);
        } else {
            setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.title_login), true);
           // setSupportActionBar(binding.toolbar.toolbar);
            binding.btnLoginSms.setVisibility(View.VISIBLE);
            binding.tvLoginWith.setVisibility(View.VISIBLE);
            binding.tvOR.setVisibility(View.VISIBLE);
            binding.llSocialMedia.setVisibility(View.VISIBLE);
            binding.llSwitch.setVisibility(View.VISIBLE);
            binding.edtEmailId.setFocusable(true);
            binding.edtMobileNo.setFocusable(true);
            binding.edtAltEmailId.setVisibility(View.GONE);
            binding.edtAltMobileNo.setVisibility(View.GONE);
            binding.btnUpdate.setVisibility(View.GONE);

            //  binding.edtCity.setFocusable(true);
        }


       // getCustomerDetails();
    }

    private void profilePage() {
      /*  DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.EMAIL_ID,null);
        DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.MOBILE_NO,null);
        DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CITY,null);*/

        binding.edtMobileNo.setText(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.MOBILE_NO, null));
        binding.edtEmailId.setText(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.EMAIL_ID, null));
        String customerUID= DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID,null);

        presenter.getAccountProfileData(customerUID);
       // binding.edtCity.setText(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CITY, null));


    }

    @Override
    protected void onStart() {
        super.onStart();
        // use following if user already loged in.
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

    }

    private void init() {
        presenter = new LoginPresenter<>();
        presenter.onAttach(this);
        validateCustomerWsDTO = new ValidateCustomerWsDTO();
        regionWsDTO = new RegionWsDTO();
        validateCustomerWsDTO.setRegion(regionWsDTO);
        setClickListeners();
        setErrorListener();
        progressDialog = AppUtils.initializeProgressDialog(NewLoginActivity.this);


    }

    private void setClickListeners() {
        binding.btnLoginSms.setOnClickListener(this);
        binding.btnUpdate.setOnClickListener(this);

        binding.switchOne.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked) {
                           // binding.switchOne.setThumbDrawable(getDrawable(R.drawable.ic_users));
                            AppUtils.showToast(NewLoginActivity.this, getString(R.string.dealer_login));
                            binding.edtEmailId.setHint(R.string.hint_lbl_login);
                           // DCCMDealerSterlite.getDataManager().setBoolean(AppPreferencesHelper.PARTNER,true);
                            setPartner(true);

                        } else {
                           // binding.switchOne.setThumbDrawable(getDrawable(R.drawable.ic_user));
                            AppUtils.showToast(NewLoginActivity.this, getString(R.string.subscriber_login));
                            binding.edtEmailId.setHint(R.string.hint_lbl_login);
                            setPartner(false);

                        }
                    }
                });
//        binding.btnLoginEmail.setOnClickListener(this);
    }

    private void setErrorListener() {
        AppUtils.addTextChangedListener(binding.edtMobileNo, binding.iedtMobileNo);
        AppUtils.addTextFocusChangeListener(binding.edtMobileNo, binding.scrollMainContainer, binding.toolbar.appBar);
    }



    private boolean isValidData() {

        if (binding.edtMobileNo.getText() == null || TextUtils.isEmpty(binding.edtMobileNo.getText().toString().trim())) {
            binding.iedtMobileNo.setError(getString(R.string.val_enter_mobile_no));
            binding.edtMobileNo.requestFocus();
            return false;
        }
        if (binding.edtMobileNo.getText() == null || TextUtils.isEmpty(binding.edtMobileNo.getText().toString().trim())) {
            binding.iedtMobileNo.setError(getString(R.string.val_enter_mobile_no));
            binding.edtMobileNo.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.iedtMobileNo.getY());
            return false;
        } else if (AppUtils.validateMobileNoLength(binding.edtMobileNo.getText().toString().trim().length())) {
            binding.iedtMobileNo.setError(getString(R.string.val_enter_valid_mobile_no));
            binding.edtMobileNo.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.iedtMobileNo.getY());
            return false;
        } else if (binding.edtEmailId.getText() == null || TextUtils.isEmpty(binding.edtEmailId.getText().toString().trim())) {
            binding.iedtEmailId.setError(getString(R.string.val_enter_email));
            binding.edtEmailId.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.iedtEmailId.getY());
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.edtEmailId.getText().toString().trim()).matches()) {
            binding.iedtEmailId.setError(getString(R.string.val_enter_valid_email));
            binding.edtEmailId.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.iedtEmailId.getY());
            return false;
        } /*else if (binding.edtCity.getText() == null || TextUtils.isEmpty(binding.edtCity.getText().toString().trim())) {
            binding.iedtCity.setError(getString(R.string.val_enter_city));
            binding.edtCity.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.iedtCity.getY());
            return false;
        }*//*if(!binding.edtCity.getText().toString().trim().equalsIgnoreCase("Mumbai")){
            AppUtils.showToast(NewLoginActivity.this,"Invalid User");
            return false;
        }*/
        regionWsDTO.setName("ESHOP");
        regionWsDTO.setIsocode("MUM");
        validateCustomerWsDTO.setEmail(binding.edtEmailId.getText().toString().trim());
        validateCustomerWsDTO.setMobileNumber(binding.edtMobileNo.getText().toString().trim());
        return true;
    }

    private boolean isValidDataAlt() {

        if (binding.edtAltMobileNo.getText() == null || TextUtils.isEmpty(binding.edtAltMobileNo.getText().toString().trim())) {
            binding.iedtAltMobileNo.setError(getString(R.string.val_enter_mobile_no));
            binding.edtAltMobileNo.requestFocus();
            return false;
        }
        if (binding.edtAltMobileNo.getText() == null || TextUtils.isEmpty(binding.edtAltMobileNo.getText().toString().trim())) {
            binding.iedtAltMobileNo.setError(getString(R.string.val_enter_mobile_no));
            binding.edtAltMobileNo.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.iedtAltMobileNo.getY());
            return false;
        } else if (AppUtils.validateMobileNoLength(binding.edtAltMobileNo.getText().toString().trim().length())) {
            binding.iedtAltMobileNo.setError(getString(R.string.val_enter_valid_mobile_no));
            binding.edtAltMobileNo.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.iedtAltMobileNo.getY());
            return false;
        } else if (binding.edtAltEmailId.getText() == null || TextUtils.isEmpty(binding.edtAltEmailId.getText().toString().trim())) {
            binding.iedtAltEmailId.setError(getString(R.string.val_enter_email));
            binding.edtAltEmailId.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.iedtAltEmailId.getY());
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.edtAltEmailId.getText().toString().trim()).matches()) {
            binding.iedtAltEmailId.setError(getString(R.string.val_enter_valid_email));
            binding.edtAltEmailId.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.iedtAltEmailId.getY());
            return false;
        }
        return true;

    }

        @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLoginSms:
                if(binding.edtEmailId.getText()!=null && binding.edtEmailId.getText().toString().equalsIgnoreCase(Constants.ADMIN_USER) && binding.edtMobileNo.getText().toString().equalsIgnoreCase(Constants.ADMIN_PASS) ){
                    setAdmin(true);
                    DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.CUSTOMER_UID,Constants.ADMIN_USER);
                    DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.EMAIL_ID,Constants.ADMIN_USER);
                    DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.MOBILE_NO,Constants.ADMIN_PASS);
                    DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.CUSTOMER_NAME,Constants.ADMIN_USER);

                    DCCMDealerSterlite.getDataManager().setBoolean(AppPreferencesHelper.PREF_IS_USER_LOGGED_IN, true);
                    // gotoDashboardActivity(true);
                    startActivity(new Intent(this, DashBoardActivity.class));
                    finishActivity();
                }
                else {
                    if (isValidData()) {
                        showProgressBar();
                        Log.e("userSignUpWsResponseDTO", " btnRegister ");
                        validateCustomerWsDTO = saveValuesToModel();
                        presenter.doLogin(validateCustomerWsDTO,getPartner());

                    }
                }
                break;

            case R.id.btnUpdate:
                if (isValidDataAlt()) {
                    GetUpdateCustomerRequestData getUpdateCustomerRequestData = new GetUpdateCustomerRequestData();
                    String customerUID = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID, null);

                    getUpdateCustomerRequestData.setCustomerNumber(customerUID);
                    getUpdateCustomerRequestData.setOtherEmail(binding.edtAltEmailId.getText().toString());
                    getUpdateCustomerRequestData.setOtherPhone(binding.edtAltMobileNo.getText().toString());
                    presenter.updateAccountProfileData(getUpdateCustomerRequestData);
                }
        }
    }

    private ValidateCustomerWsDTO saveValuesToModel() {
        return validateCustomerWsDTO;
    }

    private void gotoDashboardActivity(boolean isEmail) {
        /*Intent intent = new Intent(NewLoginActivity.this, DashBoardActivity.class);
        String value = getIntent().getStringExtra(UserDetailActivity.ARG_FROM);

        intent.putExtra(UserDetailActivity.ARG_FROM, value);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
        finish();*/
        Log.e("Activity NewLogin", "Starting");
        startActivity(new Intent(this, DashBoardActivity.class));
        finishActivity();

       /* Intent intent =new Intent(NewLoginActivity.this, OtpActivity.class);
        intent.putExtra(OtpActivity.ARG_FROM,NewLoginActivity.class.getName());
        String value = getIntent().getStringExtra(UserDetailActivity.ARG_FROM);
        intent.putExtra(UserDetailActivity.ARG_FROM, value);
        intent.putExtra(OtpActivity.ARG_IS_EMAIL_OTP,isEmail);
        intent.putExtra(OtpActivity.ARG_LAST_THREE_DIGITS_OR_EMAIL_END,binding.edtMobileNo.getText().toString().substring(7));
        startActivity(intent);*/
    }

    @Override
    public void showProgressBar() {
        if (progressDialog != null && !progressDialog.isShowing())
            progressDialog.show();

    }

    @Override
    public void hideProgressBar() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
            progressDialog.dismiss();
        }
    }

    @Override
    public void onSuccessLogin(UserResponseWsDTO userResponseWsDTO) {

        ActiviationPresenter activiationPresenter = new ActiviationPresenter();
        showProgressBar();
        activiationPresenter.findSubscriptionByEmailId(new ApiHelper.OnApiCallback() {
            @Override
            public void onSuccess(Object baseResponse) {
                Log.e("Activity NewLogin", "Success");
                hideProgressBar();
                //  presenter.success(true);
                //  gotoDashboardActivity(true);
               // presenter.getAccountData();
                DCCMDealerSterlite.getDataManager().setBoolean(AppPreferencesHelper.PREF_IS_USER_LOGGED_IN, true);
                DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.EMAIL_ID, binding.edtEmailId.getText().toString().trim());
                DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.MOBILE_NO, binding.edtMobileNo.getText().toString().trim());
           //     DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.CITY, binding.edtCity.getText().toString().trim());
                startActivity(new Intent(NewLoginActivity.this, DashBoardActivity.class).putExtra(UserDetailActivity.ARG_FROM, ARG_FROM));
                DCCMDealerSterlite.getDataManager().setBoolean(AppPreferencesHelper.PREF_IS_FIREBASE_TOKEN_CHANGED, true);
                MyFirebaseInstanceIdService.registerDeviceTokenToServer();
                finishActivity();

            }

            @Override
            public void onFailed(int code, String message) {
                hideProgressBar();
                Log.e("Activity NewLogin", "Fail");
                AppUtils.showToast(NewLoginActivity.this, getString(R.string.order_not_placed));
            }
        });
        //

    }

    @Override
    public void getAccountDataApiSucc(SubscriberServicewiseBalance baseResponse) {
        hideProgressBar();
        if (baseResponse != null && baseResponse.getBalanceDatas().get(0).getServiceAlias() != null) {
            saveBalance(baseResponse);
            DCCMDealerSterlite.getDataManager().setBoolean(AppPreferencesHelper.PREF_IS_USER_LOGGED_IN, true);
            DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.EMAIL_ID, binding.edtEmailId.getText().toString().trim());
            DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.MOBILE_NO, binding.edtMobileNo.getText().toString().trim());
           // DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.CITY, binding.edtCity.getText().toString().trim());
            startActivity(new Intent(NewLoginActivity.this, DashBoardActivity.class).putExtra(UserDetailActivity.ARG_FROM, ARG_FROM));
            DCCMDealerSterlite.getDataManager().setBoolean(AppPreferencesHelper.PREF_IS_FIREBASE_TOKEN_CHANGED, true);
            MyFirebaseInstanceIdService.registerDeviceTokenToServer();
            finishActivity();
        } else {
            AppUtils.showToast(NewLoginActivity.this, getString(R.string.mg_account_not_created));
            DCCMDealerSterlite.getDataManager().clear();
        }
    }

    @Override
    public void onSuccessUpdateProfileData(GetUpdateCustomerResponseData getUpdateCustomerResponseData) {
        String title,message;
        if(getUpdateCustomerResponseData.getResponseCode().equals("0")){
            Log.e("UpdateProfileData","onSuccess");
            title = "Profile Update";
            message = "Your Profile is updated successfully !!!";
        }else{
            title = "Report Problem";
            message = "Something went wrong , please try after some time";
        }
        showPopUp(title, message);
    }

    @Override
    public void onSuccessGetProfileData(GetUpdateCustomerResponseData getUpdateCustomerResponseData) {
        if (getUpdateCustomerResponseData!=null) {
            binding.edtAltMobileNo.setText(getUpdateCustomerResponseData.getOtherPhone());
            binding.edtAltEmailId.setText(getUpdateCustomerResponseData.getOtherEmail());
        }
    }

    private void saveBalance(SubscriberServicewiseBalance subscriberServicewiseBalance) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        for (BalanceData balanceData1 : subscriberServicewiseBalance.getBalanceDatas()) {
            if (balanceData1.getBalanceType().equalsIgnoreCase("AMOUNT")) {
                DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.BALANCE,String.valueOf(df.format(balanceData1.getAvailableBalance()/100)));
            }
        }
    }
    @Override
    public void onFailLogin() {
       if(Constants.IS_DUMMY_MODE) {
          /*  DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.CUSTOMER_UID,"LES24101800099");
            DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.EMAIL_ID,"doll@stl.com");
            DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.MOBILE_NO,"9879879877");
            DCCMDealerSterlite.getDataManager().setBoolean(AppPreferencesHelper.PREF_IS_USER_LOGGED_IN, true);
            // gotoDashboardActivity(true);
            startActivity(new Intent(this, DashBoardActivity.class));
            finishActivity();*/
        }
       hideProgressBar();
        Toast.makeText(NewLoginActivity.this, R.string.string_invalid_user, Toast.LENGTH_LONG).show();
    }

    @Override
    public void finishActivity() {
        finish();
    }


    @Override
    public void showError(int code, String resId) {
        if (code == Constants.FAIL_CODE) {
            super.showError(code, getString(R.string.msg_login_faild));
        } else {
            super.showError(code, resId);
        }
    }

    @Override
    protected void onDestroy() {
        hideProgressBar();
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        todo implement
        AppUtils.showToast(NewLoginActivity.this, getString(R.string.msg_connection_failed));
    }

    /*private void getCustomerDetails() {
        HomePresenter dashboardPresenter = new HomePresenter();
       // dashboardPresenter.getCustomerDetails(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID, null));
        dashboardPresenter.getCustomerDetails("test");
    }
*/
    public void showPopUp(String title, String message) {
        if (DCCMDealerSterlite.getDataManager().getBoolean(AppPreferencesHelper.PREF_IS_USER_LOGGED_IN, false)) {
            CustomAlertDialog.showAlertDialog(this, title, message, getString(R.string.btn_proceed), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            }, null, null, false);
        }
    }

}

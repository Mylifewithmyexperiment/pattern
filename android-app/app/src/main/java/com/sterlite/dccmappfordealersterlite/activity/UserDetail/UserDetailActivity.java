package com.sterlite.dccmappfordealersterlite.activity.UserDetail;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.activity.MakeMyPlanList.MakeMyPlanListActivity;
import com.sterlite.dccmappfordealersterlite.activity.planDetail.PlanDetailActivity;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityUserRegistrationBinding;
import com.sterlite.dccmappfordealersterlite.model.dto.usersignup.UserSignUpWsDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.usersignup.UserSignUpWsResponseDTO;


public class UserDetailActivity extends BaseActivity implements UserDetailContract.View, View.OnClickListener {

    public static String ARG_FROM = "argFrom";
    public static String ARG_FROM_EXTRA = "argExtra";
    private ActivityUserRegistrationBinding binding;
    private UserDetailContract.Presenter<UserDetailContract.View> presenter;
    private UserSignUpWsDTO user;
    private ProgressDialog progressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        setAppTheme();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_registration);
        setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.title_registration), true);

        String theme=DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.APP_THEME, Constants.DEFAULT_APP_THEME);
        if (theme.equalsIgnoreCase(Constants.APP_THEME)){
            binding.prepaidImage.setImageResource(R.drawable.banner1_red_prepaid);
        }
        else {
            binding.prepaidImage.setImageResource(R.drawable.banner1_blue_prepaid);

        }

        // binding.edtCity.setThreshold(1);
        Intent intent = getIntent();
        String value = intent.getStringExtra(ARG_FROM);


        if (value.equals("newfixedConnection")) {
            binding.prepaidImage.setVisibility(View.GONE);
        }
        init();


    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setErrorListener();
    }

    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }



    private void checkAndProcessd() {
        Intent intent = getIntent();
        String value = intent.getStringExtra(ARG_FROM);
        if (value.equals("makeMyPlan")) {
            startActivity(new Intent(UserDetailActivity.this, MakeMyPlanListActivity.class).putExtra(MakeMyPlanListActivity.ARG_PLAN_TYPE, intent.getStringExtra(ARG_FROM_EXTRA)));
            finish();
        } else if (value.equals("newConnection")) {
            startActivity(new Intent(UserDetailActivity.this, PlanDetailActivity.class).putExtra(PlanDetailActivity.ARG_PLAN_TYPE, intent.getStringExtra(ARG_FROM_EXTRA)));
            finish();
        }
        else if (value.equals("newfixedConnection")) {

            startActivity(new Intent(UserDetailActivity.this, PlanDetailActivity.class).putExtra(PlanDetailActivity.ARG_PLAN_TYPE, intent.getStringExtra(ARG_FROM_EXTRA)));
            finish();
        }/*else if (value.equals("myAccount")) {
            Intent newIntent = new Intent(UserDetailActivity.this, DashBoardActivity.class);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(newIntent);
            finish();
        } else if (value.equals("activation")) {
            startActivity(new Intent(UserDetailActivity.this, ActivationActivity.class));
            finish();
        } else if (value.equals("orderTracking")) {
            Intent newIntent = new Intent(UserDetailActivity.this, DashBoardActivity.class);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(newIntent);
            startActivity(new Intent(UserDetailActivity.this, MyOrdersActivity.class));
            finish();
        }*/
    }


    private void init() {
        presenter = new UserDetailPresenter<>();
        presenter.onAttach(this);
        presenter.init();
        setClickListeners();
        progressDialog = AppUtils.initializeProgressDialog(this);


    }

    private void setErrorListener() {
        AppUtils.addTextChangedListener(binding.edtName, binding.iedtName);
        AppUtils.addTextChangedListener(binding.edtEmailId, binding.iedtEmailId);
        AppUtils.addTextChangedListener(binding.edtMobileNo, binding.iedtMobileNo);
        //AppUtils.addTextChangedListener(binding.edtCity, binding.iedtCity);

        AppUtils.addTextFocusChangeListener(binding.edtName, binding.scrollMainContainer, binding.toolbar.appBar);
        AppUtils.addTextFocusChangeListener(binding.edtEmailId, binding.scrollMainContainer, binding.toolbar.appBar);
        AppUtils.addTextFocusChangeListener(binding.edtMobileNo, binding.scrollMainContainer, binding.toolbar.appBar);
        AppUtils.addTextFocusChangeListener(binding.edtCity, binding.scrollMainContainer, binding.toolbar.appBar);
    }

    private void setClickListeners() {
        binding.btnContinue.setOnClickListener(this);
        binding.edtCity.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        // Identifier of the action. This will be either the identifier you supplied,
                        // or EditorInfo.IME_NULL if being called due to the enter key being pressed.
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                          //  binding.edtCity.dismissDropDown();
                            AppUtils.hideKeyboard(UserDetailActivity.this);
                        }
                        // Return true if you have consumed the action, else false.
                        return true;
                    }
                });
        binding.scrollMainContainer.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
             //   binding.edtCity.dismissDropDown();
            }
        });
    }


    @Override
    public void showError(int code, String resId) {
        if (code == Constants.FAIL_CODE)
            super.showError(code, getString(R.string.msg_registration_faild));
        else {
            super.showError(code, resId);
        }
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_user_detail_menu, menu);
        return true;
    }
*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_right_menu:

                Toast.makeText(this, "MENU", Toast.LENGTH_LONG).show();
                return true;

            case android.R.id.home:
                finish();
                return true;


            default:
                return super.onOptionsItemSelected(item);

        }

    }
    @Override
    public void signUpSuccessful(UserSignUpWsResponseDTO user) {
        if(user!=null) {
            if(user.getCustomerId()!=null) {
                DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.CUSTOMER_UID, user.getCustomerId());
                DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.EMAIL_ID, binding.edtEmailId.getText().toString().trim());
                DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.MOBILE_NO, binding.edtMobileNo.getText().toString().trim());
                DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.CITY, binding.edtCity.getText().toString().trim());
                DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.CUSTOMER_NAME, binding.edtName.getText().toString().trim());
                checkAndProcessd();
            }else{
                if(user.getResponseMessage() != null) {
                    if(user.getResponseMessage().contains("minimum 10 digits and must start between 6 and 9")){
                        AppUtils.showToast(this, getString(R.string.msg_mobile_validation));
                    }else{
                        AppUtils.showToast(this, user.getResponseMessage());
                    }
                }else{
                    AppUtils.someThingWentWrong(this);
                }
            }
        }else{
            AppUtils.someThingWentWrong(this);
        }
    }
    @Override
    public void loadDataToView(ArrayList<String> arrayList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, arrayList);
       // binding.edtCity.setAdapter(adapter);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnContinue:
                if (isValidData()) {
                    showProgressBar();
                    Log.e("userSignUpWsResponseDTO"," btnRegister ");
                    saveValuesToModel();
                    presenter.doSignUp(user);
                }
                break;

        }
    }
    private UserSignUpWsDTO saveValuesToModel() {
        Log.e("userSignUpWsResponseDTO"," saveValuesToModel ");

        user=new UserSignUpWsDTO();
        user.setName(binding.edtName.getText().toString());
        user.setMobileNumber(binding.edtMobileNo.getText().toString());
        user.setEmail(binding.edtEmailId.getText().toString());
        user.setActivationCategory("IOIP");
        user.setLocation("0009");
        user.setChannelName("ESHOP");
        String parentUID= DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID,null);
        if (parentUID!=null)
        user.setParentCustomerUId(parentUID);
        return user;
    }
    private boolean isValidData() {
        if (binding.edtName.getText() == null || TextUtils.isEmpty(binding.edtName.getText().toString().trim())) {
            binding.iedtName.setError(getString(R.string.val_enter_name));
            binding.edtName.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.iedtName.getY());
            return false;
        } else if (binding.edtMobileNo.getText() == null || TextUtils.isEmpty(binding.edtMobileNo.getText().toString().trim())) {
            binding.iedtMobileNo.setError(getString(R.string.val_enter_mobile_no));
            binding.edtMobileNo.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.iedtMobileNo.getY());
            return false;
        }else if (AppUtils.validateMobileNoLength(binding.edtMobileNo.getText().toString().trim().length())) {
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
        } else if (binding.edtCity.getText() == null || TextUtils.isEmpty(binding.edtCity.getText().toString().trim())) {
            binding.iedtCity.setError(getString(R.string.val_enter_city));
            binding.edtCity.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.iedtCity.getY());
            return false;
        }
        return true;
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


}

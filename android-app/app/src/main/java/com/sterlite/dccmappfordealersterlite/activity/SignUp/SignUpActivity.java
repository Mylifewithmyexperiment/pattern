package com.sterlite.dccmappfordealersterlite.activity.SignUp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.activity.dashboard.DashBoardActivity;
import com.sterlite.dccmappfordealersterlite.activity.Login.LoginActivity;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.databinding.ActivitySignUpBinding;
import com.sterlite.dccmappfordealersterlite.model.User;

public class SignUpActivity extends BaseActivity implements SignUpContract.View, View.OnClickListener {
    ActivitySignUpBinding binding;
    SignUpContract.Presenter<SignUpContract.View> presenter;
    private ProgressDialog progressDialog;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        setAppTheme();
        binding= DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        init();
    }

    private void init() {
        presenter=new SignUpPresenter<>();
        presenter.onAttach(this);
        setClickListeners();
        setErrorListener();
        progressDialog = AppUtils.initializeProgressDialog(SignUpActivity.this);
    }

    private void setErrorListener() {
        AppUtils.addTextChangedListener(binding.edtFirstName,binding.iedtFirstName);
        AppUtils.addTextChangedListener(binding.edtLastName,binding.iedtLastName);
        AppUtils.addTextChangedListener(binding.edtEmailId,binding.iedtEmailId);
        AppUtils.addTextChangedListener(binding.edtMobileNo,binding.iedtMobileNo);
        AppUtils.addTextChangedListener(binding.edtPassword,binding.iedtPassword);
    }

    private void setClickListeners() {
        binding.edtPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    binding.edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    return false;
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (event.getRawX() >= (binding.edtPassword.getRight() - binding.edtPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) { // your action here
                        binding.edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        return true;
                    }
                }
                return false;
            }
        });
        binding.btnRegister.setOnClickListener(this);
        binding.tvSignIn.setOnClickListener(this);
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
    public void showError(int code, String resId) {
        if (code == Constants.FAIL_CODE)
            super.showError(code, getString(R.string.msg_registration_faild));
        else {
            super.showError(code, resId);
        }
    }


    @Override
    public void signUpSuccessful(User user) {
        Intent newIntent = new Intent(this, DashBoardActivity.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(newIntent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegister:
                if(isValidData()){
                    saveValuesToModel();
                    presenter.doSignUp(user);
                }
                break;
            case R.id.tvSignIn:
                gotoLoginScreen();
                break;
        }
    }

    private void saveValuesToModel() {
            user=new User();
            user.setFname(binding.edtFirstName.getText().toString());
            user.setLname(binding.edtLastName.getText().toString());
            user.setMobileNo(binding.edtMobileNo.getText().toString());
            user.setEmail(binding.edtEmailId.getText().toString());
            if(binding.rbMale.isChecked())
            user.setGender("male");
            else
                user.setGender("female");
            user.setPassword(binding.edtPassword.getText().toString());
    }

    private boolean isValidData() {
        if(binding.edtFirstName.getText()==null ||  TextUtils.isEmpty(binding.edtFirstName.getText().toString().trim()))
        {
            binding.iedtFirstName.setError(getString(R.string.val_enter_first_name));
            binding.edtFirstName.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.iedtFirstName.getY());
            return false;
        }else if(binding.edtLastName.getText()==null ||  TextUtils.isEmpty(binding.edtLastName.getText().toString().trim())){
            binding.iedtLastName.setError(getString(R.string.val_enter_last_name));
            binding.edtLastName.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.iedtLastName.getY());
            return false;
        }else if(binding.edtMobileNo.getText()==null ||  TextUtils.isEmpty(binding.edtMobileNo.getText().toString().trim())){
            binding.iedtMobileNo.setError(getString(R.string.val_enter_mobile_no));
            binding.edtMobileNo.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.iedtMobileNo.getY());
            return false;
        }else if(binding.edtMobileNo.getText().toString().trim().length()!=10){
            binding.iedtMobileNo.setError(getString(R.string.val_enter_valid_mobile_no));
            binding.edtMobileNo.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.iedtMobileNo.getY());
            return false;
        }else if (binding.edtEmailId.getText() == null || TextUtils.isEmpty(binding.edtEmailId.getText().toString().trim())) {
            binding.iedtEmailId.setError(getString(R.string.val_enter_email));
            binding.edtEmailId.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.iedtEmailId.getY());
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.edtEmailId.getText().toString().trim()).matches()) {
            binding.iedtEmailId.setError(getString(R.string.val_enter_valid_email));
            binding.edtEmailId.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.iedtEmailId.getY());
            return false;
        } else if(binding.edtPassword.getText()==null ||  TextUtils.isEmpty(binding.edtPassword.getText().toString())){
            binding.iedtPassword.setError(getString(R.string.val_pword_required));
            binding.edtPassword.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.iedtPassword.getY());
            return false;
        }else if(binding.edtPassword.getText().toString().length()<6){
            binding.iedtPassword.setError(getString(R.string.val_enter_valid_password));
            binding.edtPassword.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.iedtPassword.getY());
            return false;
        }
        return true;
    }

    private void gotoLoginScreen() {
        Intent intent=new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onDestroy() {
        hideProgressBar();
        presenter.onDetach();
        super.onDestroy();
    }
}

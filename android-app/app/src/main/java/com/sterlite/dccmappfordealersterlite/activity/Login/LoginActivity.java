package com.sterlite.dccmappfordealersterlite.activity.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.activity.dashboard.DashBoardActivity;
import com.sterlite.dccmappfordealersterlite.activity.SignUp.SignUpActivity;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityLoginBinding;


public class LoginActivity extends BaseActivity implements LoginContract.View, View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private static final String EMAIL = "email";

    private LoginContract.Presenter<LoginContract.View> presenter;
    private ActivityLoginBinding binding;
    private String TAG = "NewLoginActivity";
    private ProgressDialog progressDialog;
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 1001;
    private CallbackManager callbackManager;

    //   google login https://developers.google.com/identity/sign-in/android/sign-in?authuser=2
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        setAppTheme();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        init();
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
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
//        mGoogleSignInClient=new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        callbackManager = CallbackManager.Factory.create();
        binding.loginButton.setReadPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);
        // Callback registration
        binding.loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                AppUtils.showToast(LoginActivity.this, getString(R.string.msg_fb_login_sucess));
            }

            @Override
            public void onCancel() {
                // App code
                AppUtils.showToast(LoginActivity.this, getString(R.string.msg_fb_login_cancel));
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                exception.printStackTrace();
            }
        });
        setClickListeners();
        setErrorListener();
        progressDialog = AppUtils.initializeProgressDialog(LoginActivity.this);
        binding.edtpsw.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    binding.edtpsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    return false;
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (event.getRawX() >= (binding.edtpsw.getRight() - binding.edtpsw.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) { // your action here
                        binding.edtpsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        return true;
                    }
                }
                return false;
            }
        });

    }

    private void setClickListeners() {
        binding.btnLogin.setOnClickListener(this);
        binding.ivClose.setOnClickListener(this);
        binding.tvSignUp.setOnClickListener(this);
        binding.googleSignIn.setOnClickListener(this);
    }

    private void setErrorListener() {
        AppUtils.addTextChangedListener(binding.edtUserName, binding.iedtUserName);
        AppUtils.addTextChangedListener(binding.edtpsw, binding.iedtpsw);
    }

    private boolean isValidData() {

        if (binding.edtUserName.getText() == null || TextUtils.isEmpty(binding.edtUserName.getText().toString().trim())) {
            binding.iedtUserName.setError(getString(R.string.val_user_name_required));
            binding.edtUserName.requestFocus();
            return false;
        } else if (binding.edtpsw.getText() == null || TextUtils.isEmpty(binding.edtpsw.getText().toString())) {
            binding.iedtpsw.setError(getString(R.string.val_pword_required));
            binding.edtpsw.requestFocus();
            return false;
        } else if (binding.edtpsw.getText().toString().length() < 6) {
            binding.iedtpsw.setError(getString(R.string.val_enter_valid_password));
            binding.edtpsw.requestFocus();
            return false;
        }
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                if (isValidData())
                    presenter.doLogin(binding.edtUserName.getText().toString().trim(), binding.edtpsw.getText().toString());
                break;
            case R.id.ivClose:
                finish();
                break;
            case R.id.tvSignUp:
                gotoSignUpActivity();
                break;
            case R.id.googleSignIn:
                googleSignIn();
                break;
        }
    }

    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void gotoSignUpActivity() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
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
    public void onSuccessLogin() {
        startActivity(new Intent(this, DashBoardActivity.class));
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
        AppUtils.showToast(LoginActivity.this, getString(R.string.msg_connection_failed));
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleGoogleSignInResults(task);
        }

    }

    private void handleGoogleSignInResults(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            //if comes here it means sign in successful
            Log.d(TAG, " " + account.getEmail());
            AppUtils.showToast(LoginActivity.this, getString(R.string.msg_login_sucess));
        } catch (ApiException e) {
            e.printStackTrace();
            //if comes here means erroer in login with google

        }
    }
}

package com.sterlite.dccmappfordealersterlite.activity.Otp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.activity.InputPaymentInformation.InputPaymentInformationActivity;
import com.sterlite.dccmappfordealersterlite.activity.OrderPreview.OrderPreviewActivity;
import com.sterlite.dccmappfordealersterlite.activity.OrederReceiveSuccess.OrderReceiveSuccess;
import com.sterlite.dccmappfordealersterlite.activity.UserDetail.UserDetailActivity;
import com.sterlite.dccmappfordealersterlite.activity.dashboard.DashBoardActivity;
import com.sterlite.dccmappfordealersterlite.activity.loginnew.NewLoginActivity;
import com.sterlite.dccmappfordealersterlite.activity.orderdetailreview.OrderDetailsReviewActivity;
import com.sterlite.dccmappfordealersterlite.activity.trackingDetail.TrackingDetailActivity;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityOtpBinding;


public class OtpActivity extends BaseActivity implements OtpContract.View {

    public static final String ARG_LAST_THREE_DIGITS_OR_EMAIL_END = "argLastThreeDigitsOrEmailEnd";
    public static final String ARG_FROM = "argOtpActivityFrom";
    public static final String ARG_IS_EMAIL_OTP = "argIsEmailOTP";

    private ActivityOtpBinding binding;
    private OtpContract.Presenter<OtpContract.View> presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        setAppTheme();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_otp);
        setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.title_verify_oto), true);
        initUI();
        presenter = new OtpPresenter<>();
        presenter.onAttach(this);
    }

    private void initUI() {
        if (getIntent().getStringExtra(ARG_FROM).equalsIgnoreCase(NewLoginActivity.class.getName())) {
            if (getIntent().getBooleanExtra(ARG_IS_EMAIL_OTP, false)) {
                binding.tvLastThreeNum.setText(R.string.lbl_otp_has_bin_sent_to_email);
            }
        }
        String lastThreeDigitsOrEmailEnd = getIntent().getStringExtra(ARG_LAST_THREE_DIGITS_OR_EMAIL_END);
        if (!TextUtils.isEmpty(lastThreeDigitsOrEmailEnd)) {
            binding.tvLastThreeNum.setText(binding.tvLastThreeNum.getText() + lastThreeDigitsOrEmailEnd);
        }
        AppUtils.addTextChangedListener(binding.edtOTP, binding.iedtOTP);
        binding.btnOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent newIntent = new Intent(OtpActivity.this, DashBoardActivity.class);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(newIntent);
                startActivity(new Intent(OtpActivity.this, KYCDetailActivity.class));
                finish();*/
                if (isValidData()) {
                       // DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.PREF_SUBSCRIPTION_NUMBER,"SUN0050004");
                    if (getIntent().getStringExtra(ARG_FROM).equalsIgnoreCase(OrderPreviewActivity.class.getName()) || getIntent().getStringExtra(ARG_FROM).equalsIgnoreCase(OrderDetailsReviewActivity.class.getName())) {
                        DCCMDealerSterlite.getDataManager().setBoolean(AppPreferencesHelper.PREF_IS_USER_LOGGED_IN, true);
                        Intent intent = new Intent(OtpActivity.this, InputPaymentInformationActivity.class);
                        intent.putExtra(OrderReceiveSuccess.ARG_ORDER_NO, "0987654321");
                        intent.putExtra(OrderReceiveSuccess.ARG_TRANSACTION_NO, "0987654321");
                        intent.putExtra(OrderReceiveSuccess.ARG_PAID, "Rp 100.00");
                        intent.putExtra(OrderReceiveSuccess.ARG_SUCCESS_MESSAGE, getString(R.string.lbl_order_placed_successfully));
                        intent.putExtra(OrderReceiveSuccess.ARG_IS_TRACK, getIntent().getBooleanExtra(OrderReceiveSuccess.ARG_IS_TRACK, false));
                        intent.putExtra(TrackingDetailActivity.ARG_ORDER_ID, getIntent().getStringExtra(TrackingDetailActivity.ARG_ORDER_ID));
                        intent.putExtra(TrackingDetailActivity.ARG_ORDER_DATE, getIntent().getStringExtra(TrackingDetailActivity.ARG_ORDER_DATE));
                        intent.putExtra(TrackingDetailActivity.ARG_ORDER_STATUS, getIntent().getStringExtra(TrackingDetailActivity.ARG_ORDER_STATUS));
                        startActivity(intent);
                    } else if (getIntent().getStringExtra(ARG_FROM).equalsIgnoreCase(NewLoginActivity.class.getName())) {
                        Intent intent = new Intent(OtpActivity.this, DashBoardActivity.class);
                        String value = getIntent().getStringExtra(UserDetailActivity.ARG_FROM);
                        intent.putExtra(UserDetailActivity.ARG_FROM, value);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }

    private boolean isValidData() {
        if (binding.edtOTP.getText() == null || TextUtils.isEmpty(binding.edtOTP.getText().toString().trim())) {
            binding.iedtOTP.setError(getString(R.string.val_enter_opt));
            binding.edtOTP.requestFocus();
            return false;
        } else if (binding.edtOTP.getText().toString().trim().length() != 4) {
            binding.iedtOTP.setError(getString(R.string.val_enter_valid_otp));
            binding.edtOTP.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }


}

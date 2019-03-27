package com.sterlite.dccmappfordealersterlite.activity.Splash;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.common.GoogleApiAvailability;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.Utils.StatusBarUtilEtech;
import com.sterlite.dccmappfordealersterlite.activity.LandingPage.LandingPageActivity;
import com.sterlite.dccmappfordealersterlite.activity.dashboard.DashBoardActivity;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.databinding.ActivitySplashBinding;


public class SplashActivity extends BaseActivity implements SplashContract.View {

    private SplashContract.Presenter<SplashContract.View> presenter;
    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtilEtech.setTransparent(this);
        setAppTheme();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        if(Constants.APP.equalsIgnoreCase(Constants.MTN)) {
            binding.imgSplash.setImageDrawable(getResources().getDrawable(R.mipmap.splashscreen_mtn));


        }else if(Constants.APP.equalsIgnoreCase(Constants.TELKOMSEL)){
            binding.rrLayout.setBackground(getResources().getDrawable(R.mipmap.splashscreen_telkomsel));
        }
        else if(Constants.APP.equalsIgnoreCase(Constants.VODAFONE)){
//            binding.rrLayout.setBackground(getResources().getDrawable(R.mipmap.splash_vodafone));
        binding.imgSplash.setImageDrawable(getResources().getDrawable(R.mipmap.splash_vodafone));
        }
        else{
            binding.imgSplash.setVisibility(View.GONE);
            binding.rrLayout.setBackground(getResources().getDrawable(R.mipmap.splash_sterlite));
        }
        String lang= DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.LOCALE,null);
        if(lang==null){
         lang=Constants.Preffered_Language;
        }
        AppUtils.setLocale(this,lang);
        GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this);
    }

    private void init() {
        presenter = new SplashPresenter<>();
        presenter.onAttach(this);
    }

    @Override
    public void openLandingPage() {

        if(DCCMDealerSterlite.getDataManager().getBoolean(AppPreferencesHelper.PREF_IS_USER_LOGGED_IN,false)){
            Intent intent = new Intent(SplashActivity.this, DashBoardActivity.class);
            startActivity(intent);
        }else{
//            Intent intent = new Intent(SplashActivity.this, LandingPageActivity.class);
            Intent intent = new Intent(SplashActivity.this, LandingPageActivity.class);
            startActivity(intent);
        }

//        Intent intent = new Intent(SplashActivity.this, KYCDetailActivity.class);
//                intent.putExtra("kyc", "3174051504710009");
//        startActivity(intent);
//        Intent newIntent = new Intent(SplashActivity.this, InputPaymentInformationActivity.class);
//        newIntent.putExtra(OtpActivity.ARG_LAST_THREE_DIGITS_OR_EMAIL_END, "951");

//        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        newIntent.putExtra(sterliteapp.com.sterlitemobilecardappdesign.activity.dashboard.DashBoardActivity.ARG_CURRENT, "2");
//        startActivity(newIntent);
    }


    @Override
    public void finishActivity() {
        finish();
    }


    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showConnectionLostView() {

    }

    @Override
    public void hideConnectionLostView() {

    }

    @Override
    public void showError(int code, String res) {

    }


}

package com.sterlite.dccmappfordealersterlite.base;

import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.github.aakira.expandablelayout.ExpandableLayoutListener;
import com.github.aakira.expandablelayout.Utils;


import io.fabric.sdk.android.Fabric;
import com.sterlite.dccmappfordealersterlite.BuildConfig;
import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.Utils.CustomAlertDialog;
import com.sterlite.dccmappfordealersterlite.Utils.StatusBarUtilEtech;
import com.sterlite.dccmappfordealersterlite.activity.About.AboutActivity;
import com.sterlite.dccmappfordealersterlite.activity.Activation.ActivationActivity;
import com.sterlite.dccmappfordealersterlite.activity.Activation.ActiviationPresenter;
import com.sterlite.dccmappfordealersterlite.activity.BalanceTransfer.BalanceTransferActivity;
import com.sterlite.dccmappfordealersterlite.activity.LandingPage.LandingPageActivity;
import com.sterlite.dccmappfordealersterlite.activity.MakeMyPlanList.MakeMyPlanListActivity;


import com.sterlite.dccmappfordealersterlite.activity.PaymentHistory.PaymentHistoryActivity;
import com.sterlite.dccmappfordealersterlite.activity.PlanPackage.PlanPackageListActivity;
import com.sterlite.dccmappfordealersterlite.activity.Recharge.RechargeActivity;
import com.sterlite.dccmappfordealersterlite.activity.SearchCustomer.SearchCustomerActivity;
import com.sterlite.dccmappfordealersterlite.activity.SelectLanguage.SelectLanguageActivity;
import com.sterlite.dccmappfordealersterlite.activity.Settings.SettingsActivity;
import com.sterlite.dccmappfordealersterlite.activity.TicketView.ViewTicket;
import com.sterlite.dccmappfordealersterlite.activity.UnsubscribeAddon.UnsubscribeAddonActivity;
import com.sterlite.dccmappfordealersterlite.activity.UserDetail.UserDetailActivity;
import com.sterlite.dccmappfordealersterlite.activity.invoice.InvoiceActivity;
import com.sterlite.dccmappfordealersterlite.activity.planDetail.PlanDetailActivity;
import com.sterlite.dccmappfordealersterlite.activity.dashboard.DashBoardActivity;

import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.data.network.ApiHelper;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.databinding.LayoutExtraViewBinding;
import com.sterlite.dccmappfordealersterlite.databinding.NavMainMenuBinding;
import com.sterlite.dccmappfordealersterlite.service.FCM.MyFirebaseInstanceIdService;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.sterlite.dccmappfordealersterlite.Utils.AppUtils.setVisibility;


public class BaseActivity extends AppCompatActivity implements BaseContractView {

    protected final String TAG = BaseActivity.class.getName();
    public static String USER_ID;
    ReceiverNotification receiverNotification;
    IntentFilter intentFilter;
    public DrawerLayout drawer;

    public BaseContractPresenter<BaseContractView> baseContractPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtilEtech.setTransparentForWindow(this);
        Fabric.with(this, new Crashlytics());
        getUserId();
        MyFirebaseInstanceIdService.registerDeviceTokenToServer();
        baseContractPresenter = new BasePresenter<>();



        /*
                String theme=DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.APP_THEME, Constants.APP_THEME);
        if (theme.equalsIgnoreCase(Constants.APP_THEME))
        {
            setTheme(R.style.AppTheme);
        }
        else {
            setTheme(R.style.Telkomsel);

        }*/
    }

    public static String getUserId() {
        if (TextUtils.isEmpty(USER_ID))
            USER_ID = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID, "");
        return USER_ID;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    Toolbar toolbar;
    LayoutExtraViewBinding extraViewBinding;


    public void setUpView(Toolbar toolbar, LayoutExtraViewBinding extraViewBinding) {
        /*String theme=DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.APP_THEME, Constants.APP_THEME);
        Log.e("Default Theme:::",theme);

        //getActionBar().hide();
        if (theme.equalsIgnoreCase(Constants.APP_THEME))
        {
            setTheme(R.style.Sterlite);
        }
        else {
            setTheme(R.style.Telkomsel);

        }*/
        setUpView(toolbar, extraViewBinding, null, false);
    }

    public void setUpView(Toolbar toolbar, LayoutExtraViewBinding extraViewBinding, String title, boolean isBackEnable) {
        this.toolbar = toolbar;
        this.extraViewBinding = extraViewBinding;
      /* if(getActionBar()!=null) {
        Log.e("action bar not null","happy");

        getActionBar().hide();
          // getSupportActionBar().hide();
       }*/

        if(getSupportActionBar()!=null) {
            Log.e("support bar not null","happy");

           // getActionBar().hide();
          //  getSupportActionBar().hide();
         //  setSupportActionBar(toolbar);

        }

        setSupportActionBar(toolbar);
        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
        }
        if (isBackEnable) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            }
        }

        if (extraViewBinding == null) return;
        this.extraViewBinding.btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRetryClicked();
            }
        });

    }

    public void setUpNavigationView(final DrawerLayout drawer, final NavMainMenuBinding layoutNavMenu) {
        this.drawer = drawer;
        layoutNavMenu.tvVersion.setText(BuildConfig.VERSION_NAME);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (layoutNavMenu.ellMakeMyPlan.isExpanded())
                    layoutNavMenu.ellMakeMyPlan.collapse();
                if (layoutNavMenu.ellNewConnection.isExpanded())
                    layoutNavMenu.ellNewConnection.collapse();
                if (layoutNavMenu.ellMyAccount.isExpanded())
                    layoutNavMenu.ellMyAccount.collapse();
                if (layoutNavMenu.ellNewBroadbandConnection.isExpanded())
                    layoutNavMenu.ellNewBroadbandConnection.collapse();
                if (layoutNavMenu.ellNewCustomerConnection.isExpanded())
                    layoutNavMenu.ellNewCustomerConnection.collapse();


            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
//        navigationView.getMenu().clear();
//        navigationView.inflateMenu(R.menu.activity_drawer_menu_before_login);
//        navigationView.setNavigationItemSelectedListener(this);

        if (!AppUtils.isEmpty(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.PREF_CART_IDS, ""))) {
            layoutNavMenu.tvNavCart.setText(getString(R.string.nav_menu_my_cart) + " (1)");
        } else {
            layoutNavMenu.tvNavCart.setText(getString(R.string.nav_menu_my_cart));
        }

        layoutNavMenu.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.END);
            }
        });
        layoutNavMenu.relMakeMyPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutNavMenu.ellMakeMyPlan.toggle();
            }
        });
        layoutNavMenu.relNewConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutNavMenu.ellNewConnection.toggle();
            }
        });
        layoutNavMenu.relNewBroadbandConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutNavMenu.ellNewBroadbandConnection.toggle();
            }
        });
        layoutNavMenu.relNewCustomerConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutNavMenu.ellNewCustomerConnection.toggle();
            }
        });
        layoutNavMenu.relMyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutNavMenu.ellMyAccount.toggle();
            }
        });
        layoutNavMenu.ellNewConnection.setListener(new ExpandableLayoutListener() {
            @Override
            public void onAnimationStart() {

            }

            @Override
            public void onAnimationEnd() {

            }

            @Override
            public void onPreOpen() {
                createRotateAnimator(layoutNavMenu.ivNewConnectionDownArrow, 0f, 180f).start();

            }

            @Override
            public void onPreClose() {
                createRotateAnimator(layoutNavMenu.ivNewConnectionDownArrow, 180f, 0f).start();

            }

            @Override
            public void onOpened() {

            }

            @Override
            public void onClosed() {

            }
        });
        layoutNavMenu.ellMakeMyPlan.setListener(new ExpandableLayoutListener() {
            @Override
            public void onAnimationStart() {

            }

            @Override
            public void onAnimationEnd() {

            }

            @Override
            public void onPreOpen() {
                createRotateAnimator(layoutNavMenu.ivMakeMyPlanDownArrow, 0f, 180f).start();

            }

            @Override
            public void onPreClose() {
                createRotateAnimator(layoutNavMenu.ivMakeMyPlanDownArrow, 180f, 0f).start();
            }

            @Override
            public void onOpened() {

            }

            @Override
            public void onClosed() {

            }
        });
        layoutNavMenu.ellMyAccount.setListener(new ExpandableLayoutListener() {
            @Override
            public void onAnimationStart() {

            }

            @Override
            public void onAnimationEnd() {

            }

            @Override
            public void onPreOpen() {
                createRotateAnimator(layoutNavMenu.ivMyAccountDownArrow, 0f, 180f).start();
            }

            @Override
            public void onPreClose() {
                createRotateAnimator(layoutNavMenu.ivMyAccountDownArrow, 180f, 0f).start();

            }

            @Override
            public void onOpened() {
                layoutNavMenu.nestedScrollMainContainer.smoothScrollTo(0, (int) layoutNavMenu.ellMyAccount.getY());
            }

            @Override
            public void onClosed() {

            }
        });



        layoutNavMenu.ellNewBroadbandConnection.setListener(new ExpandableLayoutListener() {
            @Override
            public void onAnimationStart() {

            }

            @Override
            public void onAnimationEnd() {

            }

            @Override
            public void onPreOpen() {
                createRotateAnimator(layoutNavMenu.ivNewBroadbandConnectionDownArrow, 0f, 180f).start();
            }

            @Override
            public void onPreClose() {
                createRotateAnimator(layoutNavMenu.ivNewBroadbandConnectionDownArrow, 180f, 0f).start();

            }

            @Override
            public void onOpened() {
                layoutNavMenu.nestedScrollMainContainer.smoothScrollTo(0, (int) layoutNavMenu.ellNewBroadbandConnection.getY());
            }

            @Override
            public void onClosed() {

            }
        });

        layoutNavMenu.ellNewCustomerConnection.setListener(new ExpandableLayoutListener() {
            @Override
            public void onAnimationStart() {

            }

            @Override
            public void onAnimationEnd() {

            }

            @Override
            public void onPreOpen() {
                createRotateAnimator(layoutNavMenu.ivNewCustomerConnectionDownArrow, 0f, 180f).start();

            }

            @Override
            public void onPreClose() {
                createRotateAnimator(layoutNavMenu.ivNewCustomerConnectionDownArrow, 180f, 0f).start();

            }

            @Override
            public void onOpened() {

            }

            @Override
            public void onClosed() {

            }
        });

//        profileImage = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.profile_image);
//        tvUserName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvUsername);
//        tvMobile = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvMobile);

        boolean isPartnerCustomer=DCCMDealerSterlite.getDataManager().getBoolean(AppPreferencesHelper.PARTNER_CUSTOMER,false);
        Log.e("isPartnerCustomer",isPartnerCustomer+" ");
        if(getAdmin()) {

            layoutNavMenu.relNewConnection.setVisibility(View.GONE);
            layoutNavMenu.ellNewConnection.setVisibility(View.GONE);
            layoutNavMenu.separator1.setVisibility(View.GONE);

            layoutNavMenu.relMakeMyPlan.setVisibility(View.GONE);
            layoutNavMenu.ellMakeMyPlan.setVisibility(View.GONE);
            layoutNavMenu.separator2.setVisibility(View.GONE);

            layoutNavMenu.relNewBroadbandConnection.setVisibility(View.GONE);
            layoutNavMenu.ellNewBroadbandConnection.setVisibility(View.GONE);
            layoutNavMenu.separator3.setVisibility(View.GONE);

            layoutNavMenu.relNewCustomerConnection.setVisibility(View.GONE);
            layoutNavMenu.ellNewCustomerConnection.setVisibility(View.GONE);
            layoutNavMenu.separator4.setVisibility(View.GONE);

            layoutNavMenu.llOrderTracking.setVisibility(View.GONE);
            layoutNavMenu.separator5.setVisibility(View.GONE);

            layoutNavMenu.llMyCart.setVisibility(View.GONE);
            layoutNavMenu.separator6.setVisibility(View.GONE);

            layoutNavMenu.relMyAccount.setVisibility(View.GONE);
            layoutNavMenu.ellMyAccount.setVisibility(View.GONE);
            layoutNavMenu.separator7.setVisibility(View.GONE);

            layoutNavMenu.llAbout.setVisibility(View.VISIBLE);
            layoutNavMenu.separator8.setVisibility(View.VISIBLE);

            layoutNavMenu.llSettings.setVisibility(View.VISIBLE);
            layoutNavMenu.separator9.setVisibility(View.VISIBLE);

            layoutNavMenu.llSearch.setVisibility(View.GONE);
            layoutNavMenu.separator10.setVisibility(View.GONE);

            layoutNavMenu.llBackDealer.setVisibility(View.GONE);
            layoutNavMenu.separator11.setVisibility(View.GONE);

            layoutNavMenu.llLogout.setVisibility(View.VISIBLE);
        }

        else if(getPartner()){
            layoutNavMenu.relNewConnection.setVisibility(View.GONE);
            layoutNavMenu.ellNewConnection.setVisibility(View.GONE);
            layoutNavMenu.separator1.setVisibility(View.GONE);

            layoutNavMenu.relMakeMyPlan.setVisibility(View.GONE);
            layoutNavMenu.ellMakeMyPlan.setVisibility(View.GONE);
            layoutNavMenu.separator2.setVisibility(View.GONE);

            layoutNavMenu.relNewBroadbandConnection.setVisibility(View.GONE);
            layoutNavMenu.ellNewBroadbandConnection.setVisibility(View.GONE);
            layoutNavMenu.separator3.setVisibility(View.GONE);

            layoutNavMenu.relNewCustomerConnection.setVisibility(View.VISIBLE);
            layoutNavMenu.ellNewCustomerConnection.setVisibility(View.VISIBLE);
            layoutNavMenu.separator4.setVisibility(View.VISIBLE);

            layoutNavMenu.llOrderTracking.setVisibility(View.GONE);
            layoutNavMenu.separator5.setVisibility(View.GONE);

            layoutNavMenu.llMyCart.setVisibility(View.GONE);
            layoutNavMenu.separator6.setVisibility(View.GONE);

            layoutNavMenu.relMyAccount.setVisibility(View.GONE);
            layoutNavMenu.ellMyAccount.setVisibility(View.GONE);
            layoutNavMenu.separator7.setVisibility(View.GONE);

            layoutNavMenu.llAbout.setVisibility(View.GONE);
            layoutNavMenu.separator8.setVisibility(View.GONE);

            layoutNavMenu.llSettings.setVisibility(View.GONE);
            layoutNavMenu.separator9.setVisibility(View.GONE);

            layoutNavMenu.llSearch.setVisibility(View.VISIBLE);
            layoutNavMenu.separator10.setVisibility(View.VISIBLE);

            layoutNavMenu.llBackDealer.setVisibility(View.GONE);
            layoutNavMenu.separator11.setVisibility(View.GONE);

            layoutNavMenu.llLogout.setVisibility(View.VISIBLE);


            if (isPartnerCustomer){
                layoutNavMenu.relNewConnection.setVisibility(View.VISIBLE);
                layoutNavMenu.ellNewConnection.setVisibility(View.VISIBLE);
                layoutNavMenu.separator1.setVisibility(View.VISIBLE);

                layoutNavMenu.relMakeMyPlan.setVisibility(View.VISIBLE);
                layoutNavMenu.ellMakeMyPlan.setVisibility(View.VISIBLE);
                layoutNavMenu.separator2.setVisibility(View.VISIBLE);

                layoutNavMenu.relNewBroadbandConnection.setVisibility(View.VISIBLE);
                layoutNavMenu.ellNewBroadbandConnection.setVisibility(View.VISIBLE);
                layoutNavMenu.separator3.setVisibility(View.VISIBLE);

                layoutNavMenu.relNewCustomerConnection.setVisibility(View.GONE);
                layoutNavMenu.ellNewCustomerConnection.setVisibility(View.GONE);
                layoutNavMenu.separator4.setVisibility(View.GONE);

                layoutNavMenu.llOrderTracking.setVisibility(View.VISIBLE);
                layoutNavMenu.separator5.setVisibility(View.VISIBLE);

                layoutNavMenu.llMyCart.setVisibility(View.VISIBLE);
                layoutNavMenu.separator6.setVisibility(View.VISIBLE);

                layoutNavMenu.relMyAccount.setVisibility(View.VISIBLE);
                layoutNavMenu.ellMyAccount.setVisibility(View.VISIBLE);
                layoutNavMenu.separator7.setVisibility(View.VISIBLE);

                layoutNavMenu.llAbout.setVisibility(View.GONE);
                layoutNavMenu.separator8.setVisibility(View.GONE);

                layoutNavMenu.llSettings.setVisibility(View.GONE);
                layoutNavMenu.separator9.setVisibility(View.GONE);

                layoutNavMenu.llSearch.setVisibility(View.GONE);
                layoutNavMenu.separator10.setVisibility(View.GONE);

                layoutNavMenu.llBackDealer.setVisibility(View.VISIBLE);
                layoutNavMenu.separator11.setVisibility(View.VISIBLE);

                layoutNavMenu.llLogout.setVisibility(View.GONE);
            }


        }
        else {
            layoutNavMenu.relNewConnection.setVisibility(View.VISIBLE);
            layoutNavMenu.ellNewConnection.setVisibility(View.VISIBLE);
            layoutNavMenu.separator1.setVisibility(View.VISIBLE);

            layoutNavMenu.relMakeMyPlan.setVisibility(View.VISIBLE);
            layoutNavMenu.ellMakeMyPlan.setVisibility(View.VISIBLE);
            layoutNavMenu.separator2.setVisibility(View.VISIBLE);

            layoutNavMenu.relNewBroadbandConnection.setVisibility(View.VISIBLE);
            layoutNavMenu.ellNewBroadbandConnection.setVisibility(View.VISIBLE);
            layoutNavMenu.separator3.setVisibility(View.VISIBLE);

            layoutNavMenu.relNewCustomerConnection.setVisibility(View.GONE);
            layoutNavMenu.ellNewCustomerConnection.setVisibility(View.GONE);
            layoutNavMenu.separator4.setVisibility(View.GONE);

            layoutNavMenu.llOrderTracking.setVisibility(View.VISIBLE);
            layoutNavMenu.separator5.setVisibility(View.VISIBLE);

            layoutNavMenu.llMyCart.setVisibility(View.VISIBLE);
            layoutNavMenu.separator6.setVisibility(View.VISIBLE);

            layoutNavMenu.relMyAccount.setVisibility(View.VISIBLE);
            layoutNavMenu.ellMyAccount.setVisibility(View.VISIBLE);
            layoutNavMenu.separator7.setVisibility(View.VISIBLE);

            layoutNavMenu.llAbout.setVisibility(View.GONE);
            layoutNavMenu.separator8.setVisibility(View.GONE);

            layoutNavMenu.llSettings.setVisibility(View.GONE);
            layoutNavMenu.separator9.setVisibility(View.GONE);

            layoutNavMenu.llSearch.setVisibility(View.GONE);
            layoutNavMenu.separator10.setVisibility(View.GONE);

            layoutNavMenu.llBackDealer.setVisibility(View.GONE);
            layoutNavMenu.separator11.setVisibility(View.GONE);

            layoutNavMenu.llLogout.setVisibility(View.VISIBLE);
        }

        String version = null;
        if (BuildConfig.DEBUG) {
            version = getString(R.string.txt_version) + BuildConfig.VERSION_NAME + getString(R.string.txt_build) + Constants.BUILD_VERSION;
        } else
            version = getString(R.string.txt_version) + BuildConfig.VERSION_NAME;
//        tvVersionCode.setText(version);

        View.OnClickListener navClickListner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.llHome:
                        if (!(BaseActivity.this instanceof DashBoardActivity)) {
                            Intent newIntent = new Intent(BaseActivity.this, DashBoardActivity.class);
                            newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            newIntent.putExtra(DashBoardActivity.ARG_CURRENT, "2");
                            startActivity(newIntent);
                        }
                        break;
                    case R.id.llNewConnectionPrepaid: {
                        AppUtils.removePrefrences();
                        startActivity(new Intent(BaseActivity.this, PlanDetailActivity.class).putExtra(PlanDetailActivity.ARG_PLAN_TYPE, Constants.PLAN_PREPAID));
                    }
                    break;
                    case R.id.llNewConnectionPostPaid: {
                        AppUtils.removePrefrences();
                        startActivity(new Intent(BaseActivity.this, PlanDetailActivity.class).putExtra(PlanDetailActivity.ARG_PLAN_TYPE, Constants.PLAN_POSTPAID));
                    }
                    break;
                 /*   case R.id.llNewBroadbandConnectionPrepaid: {
                        AppUtils.removePrefrences();
                        startActivity(new Intent(BaseActivity.this, PlanDetailActivity.class).putExtra(PlanDetailActivity.ARG_PLAN_TYPE, Constants.BROADBAND_PLAN_PREPAID));
                    }
                    break;*/
                    case R.id.llNewBroadbandConnectionPostPaid: {
                        AppUtils.removePrefrences();
                        startActivity(new Intent(BaseActivity.this, PlanDetailActivity.class).putExtra(PlanDetailActivity.ARG_PLAN_TYPE, Constants.BROADBAND_PLAN_POSTPAID));
                    }

                    break;

                    case R.id.llNewCustomerConnectionPrepaid: {
                        AppUtils.removePrefrences();
                        startActivity(new Intent(BaseActivity.this, UserDetailActivity.class).putExtra(UserDetailActivity.ARG_FROM, "newConnection").putExtra(UserDetailActivity.ARG_FROM_EXTRA, Constants.PLAN_PREPAID));
                    }
                    break;
                    case R.id.llNewCustomerConnectionPostPaid: {
                        AppUtils.removePrefrences();
                        startActivity(new Intent(BaseActivity.this, UserDetailActivity.class).putExtra(UserDetailActivity.ARG_FROM, "newConnection").putExtra(UserDetailActivity.ARG_FROM_EXTRA, Constants.PLAN_POSTPAID));
                    }
                    break;
                    case R.id.llNewCustomerConnectionFixedLine: {
                        AppUtils.removePrefrences();
                        startActivity(new Intent(BaseActivity.this, UserDetailActivity.class).putExtra(UserDetailActivity.ARG_FROM, "newfixedConnection").putExtra(UserDetailActivity.ARG_FROM_EXTRA, Constants.PLAN_POSTPAID));
                    }
                    break;
                    case R.id.llMakeMyPlanPrepaid: {
                        AppUtils.removePrefrences();
                        startActivity(new Intent(BaseActivity.this, MakeMyPlanListActivity.class));
                    }
                    break;
                    case R.id.llMakeMyPlanPostPaid: {
                        AppUtils.removePrefrences();
                        startActivity(new Intent(BaseActivity.this, MakeMyPlanListActivity.class));
                    }
                    break;
                    /*case R.id.llActivation: {
                        startActivity(new Intent(BaseActivity.this, ActivationActivity.class).putExtra(ActivationActivity.ARG_IS_TRACK, false));
                    }
                    break;*/
                    case R.id.llOrderTracking: {
                        startActivity(new Intent(BaseActivity.this, ActivationActivity.class).putExtra(ActivationActivity.ARG_IS_TRACK, true));
                    }
                    break;
                    case R.id.llMyCart: {
                        if (AppUtils.isEmpty(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.PREF_CART_IDS, ""))) {
                            AppUtils.showToast(BaseActivity.this, getString(R.string.msg_empty_cart));
                        } else {
                            startActivity(new Intent(BaseActivity.this, PlanDetailActivity.class).putExtra(PlanDetailActivity.ARG_PLAN_TYPE, Constants.PLAN_CART));
                        }

                    }
                    break;
                    case R.id.llRecharge: {
                        Intent intent = new Intent(BaseActivity.this, RechargeActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.llPaymentHistory: {
                        Intent intent = new Intent(BaseActivity.this, PaymentHistoryActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.llUsage:

                    case R.id.llInternetPack: {
                        Intent intent = new Intent(BaseActivity.this, PlanPackageListActivity.class);
                        intent.putExtra(PlanPackageListActivity.ARG_PLAN_TITLE, getString(R.string.nav_menu_internet_packs));
                        intent.putExtra(PlanPackageListActivity.ARG_PLAN_TYPE, Constants.PLAN_PACK_INTERNET);
                        startActivity(intent);
                        break;
                    }
                    /*case R.id.llRoamingPack: {
                        Intent intent = new Intent(BaseActivity.this, PlanPackageListActivity.class);
                        intent.putExtra(PlanPackageListActivity.ARG_PLAN_TITLE, getString(R.string.nav_menu_roaming_packs));
                        intent.putExtra(PlanPackageListActivity.ARG_PLAN_TYPE, Constants.PLAN_PACK_ROAMING);
                        startActivity(intent);
                        break;
                    }*/

                    case R.id.llEntertainment: {
                        Intent intent = new Intent(BaseActivity.this, PlanPackageListActivity.class);
                        intent.putExtra(PlanPackageListActivity.ARG_PLAN_TITLE, getString(R.string.nav_menu_entertainment));
                        intent.putExtra(PlanPackageListActivity.ARG_PLAN_TYPE, Constants.PLAN_PACK_ENTERTAINMENT);
                        startActivity(intent);
                        break;
                    }

                    case R.id.llsms: {
                        Intent intent = new Intent(BaseActivity.this, PlanPackageListActivity.class);
                        intent.putExtra(PlanPackageListActivity.ARG_PLAN_TITLE, getString(R.string.nav_menu_sms));
                        intent.putExtra(PlanPackageListActivity.ARG_PLAN_TYPE, Constants.PLAN_PACK_SMS);
                        startActivity(intent);
                        break;
                    }
                    case R.id.llunsubscribe: {
                        Intent intent = new Intent(BaseActivity.this, UnsubscribeAddonActivity.class);
                        intent.putExtra(PlanPackageListActivity.ARG_PLAN_TITLE, getString(R.string.nav_menu_unsubscribe));
                        startActivity(intent);
                        break;
                    }

                    case R.id.llbill: {
                        Intent intent = new Intent(BaseActivity.this, InvoiceActivity.class);
                        intent.putExtra(PlanPackageListActivity.ARG_PLAN_TITLE, getString(R.string.nav_menu_bill));
                        startActivity(intent);
                        break;
                    }
                    case R.id.llTicket: {
                        Intent intent = new Intent(BaseActivity.this, ViewTicket.class);
                        intent.putExtra(PlanPackageListActivity.ARG_PLAN_TITLE, getString(R.string.nav_menu_ticket));
                        startActivity(intent);
                        break;
                    }
                    case R.id.llBalanceTransfer: {
                        Intent intent = new Intent(BaseActivity.this, BalanceTransferActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.llSearch: {
                        Intent intent = new Intent(BaseActivity.this, SearchCustomerActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.llChangeLanguage: {
                        Intent intent = new Intent(BaseActivity.this, SelectLanguageActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.llSettings: {
                        Intent intent = new Intent(BaseActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.llAbout: {
                        Intent intent = new Intent(BaseActivity.this, AboutActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.llLogout: {
                        logout();
                        break;
                    }
                    case R.id.llBackDealer:{
                        String partnerid=DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.PARTNER_UID, null);
                        String partnerName=DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.PARTNER_NAME, null);
                        DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.CUSTOMER_UID, partnerid);
                        DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.CUSTOMER_NAME, partnerName);
                        ActiviationPresenter presenter= new ActiviationPresenter();
                        presenter.findSubscriptionByEmailId(new ApiHelper.OnApiCallback() {
                            @Override
                            public void onSuccess(Object baseResponse) {
                                Intent newIntent = new Intent(BaseActivity.this, DashBoardActivity.class);
                                DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.P_CUSTOMER_EMAIL,null);
                                DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.P_CUSTOMER_MOBILE,null);
                                DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.P_CUSTOMER_NAME,null);
                                DCCMDealerSterlite.getDataManager().setBoolean(AppPreferencesHelper.PARTNER_CUSTOMER,false);
                                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(newIntent);
                            }

                            @Override
                            public void onFailed(int code, String message) {
                                Toast.makeText(BaseActivity.this, R.string.string_no_subs_found, Toast.LENGTH_LONG).show();


                            }
                        });

                        break;

                    }

                }
                drawer.closeDrawer(GravityCompat.END);

            }
        };

        layoutNavMenu.llHome.setOnClickListener(navClickListner);
        layoutNavMenu.llNewConnectionPrepaid.setOnClickListener(navClickListner);
        layoutNavMenu.llNewConnectionPostPaid.setOnClickListener(navClickListner);
      //  layoutNavMenu.llNewBroadbandConnectionPrepaid.setOnClickListener(navClickListner);
        layoutNavMenu.llNewBroadbandConnectionPostPaid.setOnClickListener(navClickListner);
        layoutNavMenu.llNewCustomerConnectionPrepaid.setOnClickListener(navClickListner);
        layoutNavMenu.llNewCustomerConnectionPostPaid.setOnClickListener(navClickListner);
        layoutNavMenu.llNewCustomerConnectionFixedLine.setOnClickListener(navClickListner);

        layoutNavMenu.llMakeMyPlanPrepaid.setOnClickListener(navClickListner);
        layoutNavMenu.llMakeMyPlanPostPaid.setOnClickListener(navClickListner);

        layoutNavMenu.llActivation.setOnClickListener(navClickListner);
        layoutNavMenu.llOrderTracking.setOnClickListener(navClickListner);
        layoutNavMenu.llMyCart.setOnClickListener(navClickListner);
        layoutNavMenu.llRecharge.setOnClickListener(navClickListner);
        layoutNavMenu.llPaymentHistory.setOnClickListener(navClickListner);
        layoutNavMenu.llUsage.setOnClickListener(navClickListner);
        layoutNavMenu.llInternetPack.setOnClickListener(navClickListner);
       // layoutNavMenu.llRoamingPack.setOnClickListener(navClickListner);
        layoutNavMenu.llBalanceTransfer.setOnClickListener(navClickListner);
        layoutNavMenu.llEntertainment.setOnClickListener(navClickListner);
        layoutNavMenu.llsms.setOnClickListener(navClickListner);
        layoutNavMenu.llunsubscribe.setOnClickListener(navClickListner);
        layoutNavMenu.llbill.setOnClickListener(navClickListner);
        layoutNavMenu.llTicket.setOnClickListener(navClickListner);
        layoutNavMenu.llSearch.setOnClickListener(navClickListner);
        layoutNavMenu.llChangeLanguage.setOnClickListener(navClickListner);
        layoutNavMenu.llSettings.setOnClickListener(navClickListner);

        layoutNavMenu.llAbout.setOnClickListener(navClickListner);
        layoutNavMenu.llBackDealer.setOnClickListener(navClickListner);
        layoutNavMenu.llLogout.setOnClickListener(navClickListner);
    }

    private void logout() {
        DCCMDealerSterlite.getDataManager().clear();
        Intent intent = new Intent(this, LandingPageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    public void setTitle(int titleId) {
        setTitle(getString(titleId));
    }

    public void setTitle(String title) {
        if (getSupportActionBar() == null) return;
        getSupportActionBar().setTitle(title);
    }

    public void showProgressView() {
        if (extraViewBinding == null) return;
        setVisibility(extraViewBinding.relProgressView, View.VISIBLE);
    }

    public void hideProgressView() {
        if (extraViewBinding == null) return;
        setVisibility(extraViewBinding.relProgressView, View.GONE);
    }


    // Fragment's Method and variables.
    public BaseFragment baseFragment;
    private int count = 0;

    public void addFragment(BaseFragment fragment, boolean isClearStack) {
        this.addFragment(fragment, isClearStack, true);
    }

    public void addFragment(BaseFragment fragment, boolean isClearStack, boolean isAddToStack) {
        Log.d(TAG, fragment.getClass().getName());
        String tag = fragment.getClass().getName() + count;
        baseFragment = fragment;
        if (isClearStack) {
            clearFragmentStack();
        }
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
//        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

        if (isAddToStack)
            transaction.addToBackStack(tag);
        transaction.replace(R.id.frm_container, fragment, tag);
        transaction.commit();
        count = count + 1;
    }

    public void clearFragmentStack() {
        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
            String name = getSupportFragmentManager().getBackStackEntryAt(0).getName();
            getSupportFragmentManager().popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            break;
        }
    }


    //Connection Broadcast
    private static boolean isConnected = false;
    ConnectionServices connectionServices;


    public class ConnectionServices extends BroadcastReceiver {
        Boolean isCalled = false;

        @Override
        public void onReceive(Context context, Intent intent) {

            if (AppUtils.isConnectingToInternet()) {
                isConnected = true;
            } else {
                isConnected = false;
            }
            if (isCalled) {
                isCalled = false;
                onNetWorkChanged(isConnected);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isCalled = true;
                    }
                }, 1000);
            } else {
                isCalled = true;
            }
        }
    }

    public void onNetWorkChanged(boolean isNetworkConnected) {

        if (baseFragment != null)
            baseFragment.onNetworkChanged(isNetworkConnected);
        if (!isNetworkConnected) {
            showConnectionLostView();
        } else {
            onRetryClicked();
            hideConnectionLostView();
        }
    }

    public void onRetryClicked() {
        if (AppUtils.isConnectingToInternet()) {
            hideConnectionLostView();
            if (baseFragment != null)
                baseFragment.onRetryClicked();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (receiverNotification == null) {
            receiverNotification = new ReceiverNotification();
            intentFilter = new IntentFilter();
            intentFilter.addAction(Constants.INTENT_FILTER_RECEIVER_NOTIFICATION);
            LocalBroadcastManager.getInstance(BaseActivity.this).registerReceiver(receiverNotification, intentFilter);
        }
        connectionServices = new ConnectionServices();
        registerReceiver(connectionServices, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(connectionServices);
    }

    @Override
    protected void onDestroy() {
        if (receiverNotification != null) {
            LocalBroadcastManager.getInstance(BaseActivity.this).unregisterReceiver(receiverNotification);
            receiverNotification = null;
        }
        baseContractPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.PERMISSION_CODE) {
            if (AppUtils.isPermissionGranted(this, AppUtils.PERMISSIONS))
                AppUtils.checkOrCreateAppDirectories(this);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onSupportNavigateUp() {
        AppUtils.hideKeyboard(this);
        onBackPressed();
        return true;
    }

    public void onReceiverNotification(Context context, String type, Intent intent) {
        if (baseFragment != null)
            baseFragment.onReceiverNotification(context, type, intent);
    }

    public class ReceiverNotification extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            onReceiverNotification(context, intent.getStringExtra(Constants.INTENT_NOTIFFICATION_TYPE), intent);
        }
    }

    @Override
    public void showProgressBar() {
        showProgressView();
    }

    @Override
    public void hideProgressBar() {
        hideProgressView();
    }

    @Override
    public void showConnectionLostView() {
        if (extraViewBinding == null) return;
        setVisibility(extraViewBinding.relConnectionView, View.VISIBLE);
    }

    @Override
    public void hideConnectionLostView() {
        if (extraViewBinding == null) return;
        setVisibility(extraViewBinding.relConnectionView, View.GONE);
    }

    @Override
    public void showError(int code, String resId) {

        if (code == Constants.FAIL_INTERNET_CODE) {
            resId = getString(R.string.msg_no_network_connection);
        }

        CustomAlertDialog.showAlert(this, getString(R.string.app_name), resId, getString(R.string.btn_ok), new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(200);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }
    public void setPartner(boolean isPartner){
        DCCMDealerSterlite.getDataManager().setBoolean(AppPreferencesHelper.PARTNER,isPartner);

    }

    public boolean getPartner(){
    boolean isPartner= DCCMDealerSterlite.getDataManager().getBoolean(AppPreferencesHelper.PARTNER,false);
        return isPartner;
    }

    public Boolean isPretoPostEnabled(){
     /*   SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(this);*/
        Boolean switchPref = DCCMDealerSterlite.getDataManager().getBooleanAppPref(AppPreferencesHelper.KEY_PREF_PRE_TO_POST,true);
        return switchPref;
    }

    public void setAdmin(boolean isAdmin){
        DCCMDealerSterlite.getDataManager().setBoolean(AppPreferencesHelper.ADMIN,isAdmin);

    }

    public boolean getAdmin(){
        boolean isAdmin= DCCMDealerSterlite.getDataManager().getBoolean(AppPreferencesHelper.ADMIN,false);
        return isAdmin;
    }

    public  void setAppTheme(){
        String theme=DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.APP_THEME, Constants.DEFAULT_APP_THEME);
        Log.e("Default Theme:::",theme);

        if (theme.equalsIgnoreCase(Constants.APP_THEME))
        {
            setTheme(R.style.Telkomsel);
        }
        else {
            setTheme(R.style.Sterlite);

        }
    }
}

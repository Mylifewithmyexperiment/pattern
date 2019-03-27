package com.sterlite.dccmappfordealersterlite.activity.dashboard;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.Toast;

import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.services.ConnectionManagerCompleteListner;
import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppAdServiceCall;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.activity.Activation.ActivationActivity;
import com.sterlite.dccmappfordealersterlite.activity.BalanceTransfer.BalanceTransferActivity;
import com.sterlite.dccmappfordealersterlite.activity.UserDetail.UserDetailActivity;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.base.BaseFragment;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityDashBoardBinding;
import com.sterlite.dccmappfordealersterlite.fragment.home.HomeFragment;
import com.sterlite.dccmappfordealersterlite.fragment.cart.CartFragment;
import com.sterlite.dccmappfordealersterlite.fragment.more.MoreFragment;


public class DashBoardActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, com.sterlite.dccmappfordealersterlite.activity.dashboard.DashboardContract.View {
    public static final String ARG_CURRENT = "current_item";
    private String TAG = getClass().getName();
    public static boolean isProfileChange = true;
    private boolean isBackPress = false;
    private Toolbar toolbar;

    private ActivityDashBoardBinding binding;
    public FloatingActionButton fab;
    private Context context = DashBoardActivity.this;
    Boolean isPartnerCustomer=false;
    private enum NavMenuOption {
        IDLE,
        HOME,
        CART,
        MORE
    }

    private NavMenuOption currentTab = NavMenuOption.IDLE;

    private com.sterlite.dccmappfordealersterlite.activity.dashboard.DashboardContract.Presenter<DashboardContract.View> presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());

        setAppTheme();
        init();
        presenter = new com.sterlite.dccmappfordealersterlite.activity.dashboard.DashboardPresenter<>();
        presenter.onAttach(this);

        setupFlow();
        //   setwebView();
        //  startAndCheckAdvertiesment();

    }

   /* private void setwebView() {
        WebSettings webSettings = binding.webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        binding.webview.loadDataWithBaseURL(null,"javascript:<script> (function(w, d, s, l, i) { var f = d.getElementsByTagName(s)[0], j = d.createElement(s); j.async = !0; j.src = '//apps.amplifyreach.com/' + s + '/' + l + '/' + i + '.js?t=' + new Date().getTime(); f.parentNode.insertBefore(j, f) })(window, document, 'script', '1410', 'd8519054092fdbcf'); </script>","text/html","utf-8",null);;

      //  binding.webview.addJavascriptInterface(new WebAppInterface(this), "Android");


    }*/

    private void startAndCheckAdvertiesment() {
        showAd();
        AppAdServiceCall appAdServiceCall = new AppAdServiceCall(new ConnectionManagerCompleteListner() {
            @Override
            public void onConnnectionManagerTaskComplete(String result, int requestId) {
                showAd();
            }
        });
        appAdServiceCall.showAdvertisement();
    }


    private void setupFlow() {
        String value = getIntent().getStringExtra(UserDetailActivity.ARG_FROM);
        if (!AppUtils.isEmpty(value)) {
            if (value.equals("activation")) {
                startActivity(new Intent(this, ActivationActivity.class).putExtra(ActivationActivity.ARG_IS_TRACK, false));
            } else if (value.equals("orderTracking")) {
                startActivity(new Intent(this, ActivationActivity.class).putExtra(ActivationActivity.ARG_IS_TRACK, true));
            } else if (value.equals("balanceTransfer")) {
                startActivity(new Intent(this, BalanceTransferActivity.class));
            }
        }
    }

    private void init() {

        DashBoardActivity.isProfileChange = true;

        binding = DataBindingUtil.setContentView(this, R.layout.activity_dash_board);
        //setUpNavigationView(binding.drawerLayout, binding.layoutNavMenu);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setUpNavigationView(binding.drawerLayout, binding.layoutNavMenu);
        setUpView(toolbar, binding.appBarDashBoard.containerFrame.extraView, "", true);


    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setHomeTab();
        Intent intent = getIntent();
        if (intent != null) {
            if (!TextUtils.isEmpty(intent.getStringExtra(ARG_CURRENT)) && intent.getStringExtra(ARG_CURRENT).equals("1")) {

            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isProfileChange) {
            isProfileChange = false;
            setProfileDetail();
        }
    }

    private void setProfileDetail() {
//        if (!TextUtils.isEmpty(SterliteDCCM.getDataManager().get(AppPreferencesHelper.PREF_PROFILE_URL, ""))) {
//            AppUtils.setImageUrl(this, SterliteDCCM.getDataManager().get(AppPreferencesHelper.PREF_PROFILE_URL, ""), profileImage, null);
//        }
//        tvUserName.setText(SterliteDCCM.getDataManager().get(AppPreferencesHelper.PREF_USERNAME, ""));
//        tvMobile.setText("+"+SterliteDCCM.getDataManager().get(AppPreferencesHelper.PREF_MOBILE, ""));

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        boolean isSelected = true;
        final int id = item.getItemId();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (id == R.id.nav_home) {
                    addFragment(HomeFragment.newInstance(), true, NavMenuOption.HOME);
                } else if (id == R.id.nav_cart) {
                    addFragment(CartFragment.newInstance(), false, NavMenuOption.CART);
                } else if (id == R.id.nav_more) {
                    addFragment(MoreFragment.newInstance(), false, NavMenuOption.MORE);
                }
            }
        }, 500);


        if (isSelected) {
            drawer.closeDrawer(GravityCompat.END);
            return true;
        }
        return false;
    }


    private void setHomeTab() {
        //navigationView.setCheckedItem(R.id.nav_home);
        addFragment(HomeFragment.newInstance(), true, NavMenuOption.HOME);
//        nav_bottom.setSelectedItemId(R.id.nav_home);
    }

    private void addFragment(BaseFragment baseFragment, boolean isClearTask, NavMenuOption item) {
        if (this.currentTab != item) {
            this.currentTab = item;
            addFragment(baseFragment, isClearTask);
        }
    }

    Toast toast;

    @Override
    public void onBackPressed() {
        hideProgressBar();

        int pos = getSupportFragmentManager().getBackStackEntryCount();
        Log.d(TAG, " " + pos);
        if (drawer != null && drawer.isDrawerOpen(Gravity.RIGHT)) {
            drawer.closeDrawer(GravityCompat.END);
        } else if (pos > 1) {
            setHomeTab();
        } else {
            if (!isBackPress) {
                isBackPress = true;
                toast = Toast.makeText(this, getResources().getString(R.string.lbl_press_again_to_exit), Toast.LENGTH_SHORT);
                toast.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isBackPress = false;
                    }
                }, 2000);
            } else {
                if (toast != null)
                    toast.cancel();
                this.finishAffinity();
            }

        }
    }

    /* public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
         ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
         animator.setDuration(200);
         animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
         return animator;
     }
 */
    @Override
    public void logoutCompleted() {

    }

    @Override
    public void showAd() {
        AppUtils.showAdd(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }, CoreConstants.DASHBOARD_SCREEN);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_user_detail_menu, menu);
        return true;
    }



   /* @Override
    public void onSuccessCustDetails()
    {

    }*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_right_menu:
                Log.e("action_change", " action_change");
                binding.drawerLayout.openDrawer(GravityCompat.END);
                return true;

         /*   case R.id.action_change:
                Log.e("action_change" , " action_change");
                HomePresenter presenter= new HomePresenter();
                presenter.getCustomerDetails(SterliteDCCM.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID,null));
                return true;*/

            case android.R.id.home:
                finish();
                return true;


            default:
                return super.onOptionsItemSelected(item);

        }

    }
}

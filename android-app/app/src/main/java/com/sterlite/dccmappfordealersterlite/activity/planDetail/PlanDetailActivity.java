package com.sterlite.dccmappfordealersterlite.activity.planDetail;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.activity.Kyc.KycActivity;
import com.sterlite.dccmappfordealersterlite.activity.inventory.InventoryActivity;
import com.sterlite.dccmappfordealersterlite.activity.orderdetailreview.OrderDetailsReviewActivity;
import com.sterlite.dccmappfordealersterlite.activity.pincode.PincodeActivity;
import com.sterlite.dccmappfordealersterlite.adapter.AdditionalBenefitsAdapter;
import com.sterlite.dccmappfordealersterlite.adapter.BenefitsAdapter;
import com.sterlite.dccmappfordealersterlite.adapter.PlanDetailViewpagerAdapter;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityPlanDetailBinding;
import com.sterlite.dccmappfordealersterlite.model.Plan;
import com.sterlite.dccmappfordealersterlite.model.PlanBenefit;
import com.sterlite.dccmappfordealersterlite.model.dto.addProductToCart.AddToCartResponseWsDTO;


public class PlanDetailActivity extends BaseActivity implements PlanDetailContract.View {

    public static final String ARG_FROM = "argFrom";
    public static final String ARG_PLAN_TYPE = "plan_type";
    public static final String IS_REDIRECT_TO_ORDER = "isredirectToOrder";

    private String planType;
    private String title = "";
    private PlanDetailContract.Presenter<PlanDetailContract.View> presenter;
    private ActivityPlanDetailBinding binding;
    private PlanDetailViewpagerAdapter adapter;
    private BenefitsAdapter benefitsAdapter;
    private AdditionalBenefitsAdapter additionalBenefitsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());

        setAppTheme();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_plan_detail);
        planType = getIntent().getStringExtra(ARG_PLAN_TYPE);
        binding.btnSelectPlan.setText(getString(R.string.btn_select_plan));
        if (!AppUtils.isEmpty(planType) && planType.equals(Constants.PLAN_POSTPAID)) {
            title = getString(R.string.lbl_shop_postpaid_service);
        } else if (planType.equals(Constants.PLAN_PREPAID)) {
            title = getString(R.string.lbl_shop_prepaid_service);
        } else if (planType.equals(Constants.PLAN_CART)) {
            binding.btnSelectPlan.setText(getString(R.string.btn_continue));
            title = getString(R.string.nav_menu_my_cart);
        } else if (planType.equals(Constants.BROADBAND_PLAN_PREPAID)) {
            title = getString(R.string.lbl_new_broadband_connection);
        } else if (planType.equals(Constants.BROADBAND_PLAN_POSTPAID)) {
            title = getString(R.string.lbl_new_broadband_connection);
        }
        setUpNavigationView(binding.drawerLayout, binding.layoutNavMenu);

        setUpView(binding.toolbar.toolbar, binding.extraView, title, true);


        binding.vpPlans.setClipToPadding(false);
        binding.vpPlans.setPageMargin((int) getResources().getDimension(R.dimen._15sdp));


        presenter = new PlanDetailPresenter<>();
        presenter.onAttach(this);
        presenter.init(planType, this);

        binding.ivNavigationStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.vpPlans.setCurrentItem(binding.vpPlans.getCurrentItem() - 1);
            }
        });
        binding.ivNavigationEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.vpPlans.setCurrentItem(binding.vpPlans.getCurrentItem() + 1);
            }
        });


        binding.btnSelectPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String from = getIntent().getStringExtra(ARG_FROM);
                if (!AppUtils.isEmpty(from) && from.equals(OrderDetailsReviewActivity.class.getName())) {
                    setPrefrences();
                    Intent intent = new Intent();
                    intent.putExtra("planId", getCurrentPlanDetail().getPlanId());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                } else {
                    if (!AppUtils.isEmpty(planType) && (planType.equals(Constants.BROADBAND_PLAN_PREPAID) || planType.equals(Constants.BROADBAND_PLAN_POSTPAID))) {
                        if(getCurrentPlanDetail().getPlanId().equals(Constants.CODE_BB)) {
                            startActivity(new Intent(PlanDetailActivity.this, KycActivity.class).putExtra(OrderDetailsReviewActivity.NEW_CONNACTION, false));
                            setPrefrences(Constants.TYPE_BROADBAND);
                        }else if(getCurrentPlanDetail().getPlanId().equals(Constants.CODE_IPTV)) {
                            startActivity(new Intent(PlanDetailActivity.this, KycActivity.class));
                            setPrefrences(Constants.TYPE_IPTV);
                        }else{
                            startActivity(new Intent(PlanDetailActivity.this, PincodeActivity.class).putExtra(PincodeActivity.ARG_FROM, PlanDetailActivity.class.getName()));
                            setPrefrences(Constants.TYPE_BROADBAND_MODEM);
                        }
                    }else{
                        setPrefrences();
                        startActivity(new Intent(PlanDetailActivity.this, InventoryActivity.class).putExtra(OrderDetailsReviewActivity.NEW_CONNACTION, true));
                    }
//                    if (getIntent().getBooleanExtra(IS_REDIRECT_TO_ORDER, false)) {
//                        Intent intent = new Intent(PlanDetailActivity.this, InternetPackSummeryActivity.class);
//                        intent.putExtra(InternetPackSummeryActivity.ARG_PLAN_NAME, "RED Entertainment+");
//                        intent.putExtra(InternetPackSummeryActivity.ARG_PLAN_PRICE, "Rp 399");
//                        intent.putExtra(InternetPackSummeryActivity.ARG_PLAN_DESCRIPTION, "SAVE upto 33%! RS398=1.4GB/D+UNLTD Local + STD + Ntnl Roam Calls; 84 divas. Receive benifits as much as RS. 597 (199*3) in Rs. 399 only ! Receive RS.19.9 TALKTIME, Only on this app! TT cradit in 4 hrs.Tnc Apply.");
//                        startActivity(intent);
//                    } else {
////                        cartId=00000182
//                        setPrefrences();
//                        startActivity(new Intent(PlanDetailActivity.this, InventoryActivity.class).putExtra(OrderDetailsReviewActivity.NEW_CONNACTION, true));
////                      presenter.addToCart(getCurrentPlanDetail().getPlanId());
//                    }
                }

            }
        });
        if (!AppUtils.isEmpty(planType) && (planType.equals(Constants.PLAN_POSTPAID) )) {
            binding.tvAdditionalText.setText(getString(R.string.lbl_additional_benefits));
        }
        else if( planType.equals(Constants.BROADBAND_PLAN_POSTPAID)){
            binding.tvAdditionalText.setText("Basic benefits across Fixed Line");

        }
        else {
            binding.tvAdditionalText.setText(getString(R.string.lbl_additional_benefits_prepaid));
        }
        Log.e("plan:Plan:userid", DCCMDealerSterlite.getDataManager().getBoolean(AppPreferencesHelper.PREF_IS_USER_LOGGED_IN, false)+"H");

    }

    private void setPrefrences(String ... type) {
        DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.PRODUCT_PLAN, AppUtils.getStringFromObj(getCurrentPlanDetail()));
        DCCMDealerSterlite.getDataManager().setBoolean(AppPreferencesHelper.ARG_FROM_MMP, false);
        if(planType.equals(Constants.BROADBAND_PLAN_PREPAID)||planType.equals(Constants.BROADBAND_PLAN_POSTPAID)){
            DCCMDealerSterlite.getDataManager().setBoolean(AppPreferencesHelper.IS_BB, true);
            DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.BB_TYPE, type[0]);
            Log.e("plantype",type[0]);
        } else {
            DCCMDealerSterlite.getDataManager().setBoolean(AppPreferencesHelper.IS_BB, false);
        }
        Log.e("plandetail act ", "setPrefrences " + AppUtils.getStringFromObj(getCurrentPlanDetail()));
    }

    @Override
    public void setUpView(boolean isReset) {
        if (isReset) {
            adapter = null;
        }
        if (adapter == null) {
            adapter = new PlanDetailViewpagerAdapter(this, new PlanDetailViewpagerAdapter.OnViewpagerItemListener() {
                @Override
                public void onItemClicked(int position, int viewType) {

                }
            });
        }

        binding.vpPlans.setAdapter(adapter);
        binding.vpPlans.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeDetailView(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void changeDetailView(int position) {
        AppUtils.setVisibility(binding.btnSelectPlan, View.VISIBLE);
        AppUtils.setVisibility(binding.loginscroll, View.VISIBLE);
        AppUtils.setVisibility(binding.ivNavigationStart, View.VISIBLE);
        AppUtils.setVisibility(binding.ivNavigationEnd, View.VISIBLE);
        if (position == 0) {
            AppUtils.setVisibility(binding.ivNavigationStart, View.GONE);
        }
        if (position == adapter.getCount() - 1) {
            AppUtils.setVisibility(binding.ivNavigationEnd, View.GONE);
        }

        Plan plan = adapter.get(position);

        animateCurrancySymbol();

        int value = Integer.parseInt(plan.getFreeBenefitInRupee());
        binding.tvFreeBenefits.animateFromZero(value, 800);

//        Animation aniSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.silde_down);
//        binding.ivCurrancy.startAnimation(aniSlide);


//        if (Constants.IS_DUMMY_MODE) {
//            ArrayList<PlanBenefit> arrBenefits = new ArrayList<>();
//            for (int i = 0; i < 5; i++) {
//                arrBenefits.add(new PlanBenefit());
//            }
//            plan.setArrBenefits(arrBenefits);
//        }
        if (plan.getArrBenefits() != null && plan.getArrBenefits().size() > 0) {
            benefitsAdapter = new BenefitsAdapter(this, plan.getArrBenefits(), new OnRecyclerViewItemClickListener<PlanBenefit>() {
                @Override
                public void onClicked(PlanBenefit bean, View view, int position, ViewType viewType) {

                }

                @Override
                public void onLastItemReached() {

                }
            });
            benefitsAdapter.isAddFooter = false;
            binding.rvBenefits.setAdapter(benefitsAdapter);
            binding.rvBenefits.setNestedScrollingEnabled(false);
        }

        if (plan.getArrAdditionalBenefits() != null && plan.getArrAdditionalBenefits().size() > 0) {
            AppUtils.setVisibility(binding.rvAdditionalBefenits, View.VISIBLE);
            AppUtils.setVisibility(binding.tvAdditionalText, View.VISIBLE);
            additionalBenefitsAdapter = new AdditionalBenefitsAdapter(this, plan.getArrAdditionalBenefits(), new OnRecyclerViewItemClickListener<PlanBenefit>() {
                @Override
                public void onClicked(PlanBenefit bean, View view, int position, ViewType viewType) {

                }

                @Override
                public void onLastItemReached() {

                }
            });
            additionalBenefitsAdapter.isAddFooter = false;
            binding.rvAdditionalBefenits.setAdapter(additionalBenefitsAdapter);
            binding.rvAdditionalBefenits.setNestedScrollingEnabled(false);
        } else {
            AppUtils.setVisibility(binding.rvAdditionalBefenits, View.GONE);
            AppUtils.setVisibility(binding.tvAdditionalText, View.GONE);
        }


    }


    private void animateCurrancySymbol() {

        binding.ivCurrancy.clearAnimation();
        TranslateAnimation mAnimation = new TranslateAnimation(
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, -0.2f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0.05f);
        mAnimation.setDuration(350);
        mAnimation.setRepeatCount(2);
        binding.ivCurrancy.setAnimation(mAnimation);

    }

    @Override
    public void loadDataToView(ArrayList<Plan> list) {
        ArrayList<Plan> listFinal = new ArrayList<>();
        if (planType.equals(Constants.PLAN_CART)) {
            String productId = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.PREF_CART_IDS, "");
            Log.e("ProductID",productId);
            if (!AppUtils.isEmpty(productId)) {
                for (Plan plan : list) {
                    if (plan.getPlanId().equals(productId)) {
                        listFinal.add(plan);
                    }
                }
            } else {
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.app_name))
                        .setMessage(getString(R.string.msg_empty_cart))
                        .setPositiveButton(getString(R.string.btn_ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .show();
            }
        } else {
            listFinal.addAll(list);
        }
        adapter.updateList(listFinal);
        if (adapter.getCount() > 0) {
            changeDetailView(binding.vpPlans.getCurrentItem());
        }
    }


    @Override
    protected void onDestroy() {
        hideProgressBar();

        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            if (AppUtils.isUserRegistered()) {
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.activity_user_detail_menu, menu);
                return true;
            } else {
                if (drawer != null)
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.END);
                return super.onCreateOptionsMenu(menu);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return super.onCreateOptionsMenu(menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_right_menu:
//                Toast.makeText(this, "MENU", Toast.LENGTH_LONG).show();
                binding.drawerLayout.openDrawer(GravityCompat.END);
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        binding.btnSelectPlan.setText(getString(R.string.btn_select_plan));
        planType = intent.getStringExtra(ARG_PLAN_TYPE);
        if (!AppUtils.isEmpty(planType) && planType.equals(Constants.PLAN_POSTPAID)) {
            title = getString(R.string.lbl_shop_postpaid_service);
        } else if (planType.equals(Constants.PLAN_PREPAID)) {
            title = getString(R.string.lbl_shop_prepaid_service);
        } else if (!AppUtils.isEmpty(planType) && planType.equals(Constants.BROADBAND_PLAN_POSTPAID)) {
            title = getString(R.string.lbl_shop_postpaid_service);
        } else if (planType.equals(Constants.BROADBAND_PLAN_PREPAID)) {
            title = getString(R.string.lbl_shop_prepaid_service);
        } else if (planType.equals(Constants.PLAN_CART)) {
            binding.btnSelectPlan.setText(getString(R.string.btn_continue));
            title = getString(R.string.nav_menu_my_cart);
        }
        setUpView(binding.toolbar.toolbar, binding.extraView, title, true);
        setUpView(true);
        presenter.init(planType, this);
        if (!AppUtils.isEmpty(planType) && (planType.equals(Constants.PLAN_POSTPAID) || planType.equals(Constants.BROADBAND_PLAN_POSTPAID))) {
            binding.tvAdditionalText.setText(getString(R.string.lbl_additional_benefits));
        } else {
            binding.tvAdditionalText.setText(getString(R.string.lbl_additional_benefits_prepaid));
        }
    }

    @Override
    public void addToCartApiCallSuccess(AddToCartResponseWsDTO addToCartResponseWsDTO) {
        if (addToCartResponseWsDTO.isSuccess()) {
            DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.CART_ID, addToCartResponseWsDTO.getCartCode());
            startActivity(new Intent(PlanDetailActivity.this, InventoryActivity.class));
        } else {
            AppUtils.showToast(this, this.getResources().getString(R.string.msg_somtehing_went_wrong));
            DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.CART_ID, "00000284");
            startActivity(new Intent(PlanDetailActivity.this, InventoryActivity.class));
        }
    }

    private Plan getCurrentPlanDetail() {
        return adapter.get(binding.vpPlans.getCurrentItem());
    }
}

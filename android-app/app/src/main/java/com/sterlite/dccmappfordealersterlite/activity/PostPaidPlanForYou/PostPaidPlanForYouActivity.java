package com.sterlite.dccmappfordealersterlite.activity.PostPaidPlanForYou;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
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
import com.sterlite.dccmappfordealersterlite.activity.InternetPackSummery.InternetPackSummeryActivity;
import com.sterlite.dccmappfordealersterlite.adapter.AdditionalBenefitsAdapter;
import com.sterlite.dccmappfordealersterlite.adapter.BenefitsAdapter;
import com.sterlite.dccmappfordealersterlite.adapter.PlanDetailViewpagerAdapter;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityPostPaidPlanForYouBinding;
import com.sterlite.dccmappfordealersterlite.model.Plan;
import com.sterlite.dccmappfordealersterlite.model.PlanBenefit;

public class PostPaidPlanForYouActivity extends BaseActivity implements PostPaidPlanForYouContract.View {

    private ActivityPostPaidPlanForYouBinding binding;
    private PostPaidPlanForYouContract.Presenter<PostPaidPlanForYouContract.View> presenter;
    private PlanDetailViewpagerAdapter adapter;
    private BenefitsAdapter benefitsAdapter;
    private AdditionalBenefitsAdapter additionalBenefitsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAppTheme();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_paid_plan_for_you);
        setUpNavigationView(binding.drawerLayout, binding.layoutNavMenu);
        setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.title_post_paid_plan_for_you), true);
        binding.vpPlans.setPageMargin((int) getResources().getDimension(R.dimen._15sdp));
        setClickListners();
        presenter = new PostPaidPlanForYouPresenter<>();
        presenter.onAttach(this);
        presenter.init();
    }

    private void setClickListners() {
        binding.btnSelectPlan.setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View view) {
                                                         int selectedPlanIndex=binding.vpPlans.getCurrentItem();
                                                         Plan plan=adapter.get(selectedPlanIndex);
                                                         Intent intent = new Intent(PostPaidPlanForYouActivity.this, InternetPackSummeryActivity.class);
                                                         intent.putExtra(InternetPackSummeryActivity.ARG_PLAN_NAME, plan.getPlanTitle());
                                                         intent.putExtra(InternetPackSummeryActivity.ARG_PLAN_PRICE, String.valueOf(plan.getPricePerMonth()));
                                                         intent.putExtra(InternetPackSummeryActivity.ARG_FROM, PostPaidPlanForYouActivity.class.getName());
                                                         startActivity(intent);

                                                     }
                                                 }
        );
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

    @Override
    public void loadDataToView(String rechargeAmt, String dataUsage, String voiceUseage, String primeUsage,String netflixUsage,String youtubeUsage, ArrayList<Plan> list) {
        if (!TextUtils.isEmpty(rechargeAmt))
          //  binding.tvAvgRecharge.setText(rechargeAmt + getString(R.string.lbl_use_mb));
            binding.tvAvgRecharge.setText(rechargeAmt + "");
        else {
            binding.relAvgRechargeContainer.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(dataUsage) )
            binding.tvAvgData.setText(dataUsage +" ");
        else {
            binding.relAvgDataContainer.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(netflixUsage) && (!netflixUsage.equalsIgnoreCase("0")))
            binding.tvAvgNetflixData.setText(netflixUsage +" ");
        else {
            binding.relAvgNetflixDataContainer.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(youtubeUsage) && !youtubeUsage.equalsIgnoreCase("0"))
            binding.tvAvgYoutubeData.setText(youtubeUsage +" ");
        else {
            binding.relAvgYoutubeDataContainer.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(primeUsage) && !primeUsage.equalsIgnoreCase("0"))
            binding.tvAvgPrimeData.setText(primeUsage +" ");
        else {
            binding.relAvgPrimeDataContainer.setVisibility(View.GONE);
        }



        if (!TextUtils.isEmpty(voiceUseage) )
            binding.tvAvgVoice.setText(voiceUseage + " ");
        else {
            binding.relAvgVoiceContainer.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(rechargeAmt) && TextUtils.isEmpty(dataUsage) && TextUtils.isEmpty(voiceUseage)) {
            binding.cardAnalysisContainer.setVisibility(View.GONE);
        }

        ArrayList<Plan> listFinal = new ArrayList<>();
       /* if (planType.equals(Constants.PLAN_CART)) {
            String productId = SterliteDCCM.getDataManager().get(AppPreferencesHelper.PREF_CART_IDS, "");
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
        } else {*/
        listFinal.addAll(list);
//        }

        adapter.updateList(listFinal);
        if (adapter.getCount() > 0) {
            changeDetailView(binding.vpPlans.getCurrentItem());
        }
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
        binding.currencySymbol.setText(String.format(" %s ", Constants.CURRANCY_SYMBOL));

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
}

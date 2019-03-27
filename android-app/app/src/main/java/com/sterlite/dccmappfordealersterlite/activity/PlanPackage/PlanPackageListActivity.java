package com.sterlite.dccmappfordealersterlite.activity.PlanPackage;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.activity.InternetPackSummery.InternetPackSummeryActivity;
import com.sterlite.dccmappfordealersterlite.adapter.PlanPackageViewpagerAdapter;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityPlanPlanPackageBinding;
import com.sterlite.dccmappfordealersterlite.model.PlanPackage;


public class PlanPackageListActivity extends BaseActivity implements PlanPackageContract.View {

    public static String ARG_PLAN_TYPE = "argPlanType";
    public static String ARG_PLAN_TITLE = "argPlanTitle";
    private String planType;
    private String title;
    private PlanPackageContract.Presenter<PlanPackageContract.View> presenter;
    private ActivityPlanPlanPackageBinding binding;
    private PlanPackageViewpagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        setAppTheme();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_plan_plan_package);
        planType = getIntent().getStringExtra(ARG_PLAN_TYPE);
        title = getIntent().getStringExtra(ARG_PLAN_TITLE);
        setUpNavigationView(binding.drawerLayout, binding.layoutNavMenu);
        setUpView(binding.toolbar.toolbar, binding.extraView, title, true);


        binding.vpPlans.setClipToPadding(false);
        binding.vpPlans.setPageMargin((int) getResources().getDimension(R.dimen._15sdp));


        presenter = new PlanPackagePresenter<>();
        presenter.onAttach(this);
        presenter.init(planType);

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

                PlanPackage planPackage = getCurrentPlanDetail();
                Intent intent = new Intent(PlanPackageListActivity.this, InternetPackSummeryActivity.class);
                intent.putExtra(InternetPackSummeryActivity.ARG_PLAN_NAME, planPackage.getName());
                intent.putExtra(InternetPackSummeryActivity.ARG_PLAN_PRICE, String.valueOf(planPackage.getOneTimeProductPrice().getValue()));
                intent.putExtra(InternetPackSummeryActivity.ARG_PLAN_DESCRIPTION, planPackage.getDescription());
                startActivity(intent);

            }
        });

    }

    @Override
    public void setUpView(boolean isReset) {
        if (isReset) {
            adapter = null;
        }
        if (adapter == null) {
            adapter = new PlanPackageViewpagerAdapter(this, new PlanPackageViewpagerAdapter.OnViewpagerItemListener() {
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
        } else if (position == adapter.getCount() - 1) {
            AppUtils.setVisibility(binding.ivNavigationEnd, View.GONE);
        }
    }

    @Override
    public void loadDataToView(ArrayList<PlanPackage> list) {
        adapter.updateList(list);
        changeDetailView(0);
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
        planType = intent.getStringExtra(ARG_PLAN_TYPE);
        title = intent.getStringExtra(ARG_PLAN_TITLE);
        setTitle(title);
        setUpView(true);
        presenter.init(planType);
    }

    private PlanPackage getCurrentPlanDetail() {
        return adapter.get(binding.vpPlans.getCurrentItem());
    }


}

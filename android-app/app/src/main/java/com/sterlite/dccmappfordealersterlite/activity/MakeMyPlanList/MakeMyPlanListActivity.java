package com.sterlite.dccmappfordealersterlite.activity.MakeMyPlanList;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.activity.MakeMyPlanDetail.MakeMyPlanDetailActivity;
import com.sterlite.dccmappfordealersterlite.adapter.FilterMainAdapter;
import com.sterlite.dccmappfordealersterlite.adapter.MakeMyPlanAdapter;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityMakeMyPlanListBinding;
import com.sterlite.dccmappfordealersterlite.model.MakeMyPlanFilter.FilterContainer;
import com.sterlite.dccmappfordealersterlite.model.MakeMyPlanModel.MakeMyPlanItems;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;


public class MakeMyPlanListActivity extends BaseActivity implements MakeMyPlanListContract.View {

    public static String ARG_PLAN_TYPE = "plan_type";
    private ActivityMakeMyPlanListBinding binding;
    private MakeMyPlanListContract.Presenter<MakeMyPlanListContract.View> presenter;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private int min=0,max=0;
    private String cur = "";
    FilterMainAdapter adapter;
    MakeMyPlanAdapter makeMyPlanAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        setAppTheme();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_make_my_plan_list);
        setUpNavigationView(binding.drawerLayout, binding.layoutNavMenu);
        setUpFilterView();
        setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.lbl_make_my_plan), true);
        presenter = new MakeMyPlanListPresenter<>();
        presenter.onAttach(this);

        presenter.init();
        binding.swRefreshInventory.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.init();
            }
        });
        binding.rvMakeMyPlans.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {


            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Log.d("mmpl", "ideal");

                    if (!binding.floatingActionButton.isShown()) {
                        binding.floatingActionButton.setVisibility(View.VISIBLE);
                    }
                } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    if ( binding.floatingActionButton.isShown())
                        binding.floatingActionButton.setVisibility(View.INVISIBLE);
                    Log.d("mmpl", "dragging");
                }
            }
        });
/*
        binding.rvMakeMyPlans.addOnScrollListener(new RecyclerView.OnScrollListener(){

            boolean isAnimationRunning=false;
            FloatingActionButton.OnVisibilityChangedListener listeneter = new FloatingActionButton.OnVisibilityChangedListener(){
                @Override
                public void onShown(FloatingActionButton fab) {
                    super.onShown(fab);
                    isAnimationRunning=false;
                }

                @Override
                public void onHidden(FloatingActionButton fab) {
                    super.onHidden(fab);
                    isAnimationRunning=false;
                }
            };

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (((dy > 0 ||dy<0) && binding.floatingActionButton.isShown())&& !isAnimationRunning)
                    Log.d("mmpl","scroll stable hide fab"+dx+" "+dy);
                    isAnimationRunning=true;
                    binding.floatingActionButton.hide(listeneter);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE ){
                    Log.d("mmpl","scroll stable show fab");
                    if(!isAnimationRunning)
                    {isAnimationRunning=true;
                    binding.floatingActionButton.show(listeneter);}else{
                    }
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });*/
//        binding.btnSelectPlan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MakeMyPlanListActivity.this, MakeMyPlanDetailActivity.class));
//            }
//        });

    }

    private void setUpFilterView() {
        drawer = binding.drawerLayout;
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.rvMenuContainer.getContext(),
                VERTICAL);
        binding.rvMenuContainer.addItemDecoration(dividerItemDecoration);
        binding.btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
                //adapter.getAll();
               // AppUtils.showToast(MakeMyPlanListActivity.this, getString(R.string.msg_apply_clicked));
                presenter.applyFilter(binding.rbMMPfilter.getSelectedMinValue().intValue(),binding.rbMMPfilter.getSelectedMaxValue().intValue());
            }
        });
        binding.btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
//                todo make api call and clear adapter list;
                resetRangeBar();
                if (makeMyPlanAdapter!=null)
                    makeMyPlanAdapter.clearAllItem();
                presenter.applyReset();
                //AppUtils.showToast(MakeMyPlanListActivity.this, getString(R.string.msg_reset_clicked));
            }
        });
        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        binding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        binding.rbMMPfilter.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {

                String textMin = cur+" "+String.valueOf(minValue.intValue());
                binding.tvMin.setText(textMin);

                String textMax = cur+" "+String.valueOf(maxValue.intValue());
                binding.tvMax.setText(textMax);

            }
        });
    }


    @Override
    public void setUpView() {

        adapter = new FilterMainAdapter(this, new OnRecyclerViewItemClickListener<FilterContainer>() {
            @Override
            public void onClicked(FilterContainer bean, View view, int position, ViewType viewType) {

            }

            @Override
            public void onLastItemReached() {

            }
        });
        adapter.isAddFooter = false;
        binding.rvMenuContainer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvMenuContainer.setAdapter(adapter);

        makeMyPlanAdapter = new MakeMyPlanAdapter(this, new OnRecyclerViewItemClickListener<MakeMyPlanItems>() {
            @Override
            public void onClicked(MakeMyPlanItems bean, View view, int position, ViewType viewType) {
                DCCMDealerSterlite.getDataManager().setBoolean(AppPreferencesHelper.ARG_FROM_MMP,true);
                DCCMDealerSterlite.getDataManager().setBoolean(AppPreferencesHelper.IS_BB, false);
                Intent intent = new Intent(MakeMyPlanListActivity.this, MakeMyPlanDetailActivity.class);
                intent.putExtra(MakeMyPlanDetailActivity.ARG_MMP, bean);
                startActivity(intent);
            }

            @Override
            public void onLastItemReached() {

            }
        });
        binding.rvMakeMyPlans.setAdapter(makeMyPlanAdapter);
    }

    @Override
    public void loadDataToView(ArrayList<MakeMyPlanItems> list) {
        makeMyPlanAdapter.addItems(list);
        binding.swRefreshInventory.setRefreshing(false);

        if (list!=null && list.size()>1){
            cur = list.get(0).getOneTimeProductPrice().getCurrencyIso();
            min = list.get(0).getOneTimeProductPrice().getValue();
            max = list.get(list.size()-1).getOneTimeProductPrice().getValue();
            resetRangeBar();
        }
    }
//

    @Override
    public void showProgressBar() {
        super.showProgressBar();
        binding.floatingActionButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgressBar() {
        super.hideProgressBar();
        binding.floatingActionButton.setVisibility(View.VISIBLE);

    }

    @Override
    public void loadFilterDataToView(ArrayList<MakeMyPlanItems> list) {
        makeMyPlanAdapter.clearAllItem();
        makeMyPlanAdapter.addItems(list);
        binding.swRefreshInventory.setRefreshing(false);
    }

    @Override
    public void loadFilterContainerToView(ArrayList<FilterContainer> filterContainers) {
        adapter.getAll().clear();
        adapter.addItems(filterContainers);
    }

    @Override
    public void onDestroy() {
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
    public void onBackPressed() {
        if (drawer != null && (drawer.isDrawerOpen(GravityCompat.START) || drawer.isDrawerOpen(GravityCompat.END))) {
            drawer.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

    private void resetRangeBar(){
        binding.rbMMPfilter.setMinValue(min).setMaxValue(max).apply();
        binding.rbMMPfilter.setMinStartValue(min).setMaxStartValue(max).apply();

        String textMin = cur+" "+String.valueOf(binding.rbMMPfilter.getSelectedMinValue().intValue());
        binding.tvMin.setText(textMin);

        String textMax = cur+" "+String.valueOf(binding.rbMMPfilter.getSelectedMaxValue().intValue());
        binding.tvMax.setText(textMax);
    }
}

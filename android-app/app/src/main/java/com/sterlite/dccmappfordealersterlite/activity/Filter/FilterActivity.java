package com.sterlite.dccmappfordealersterlite.activity.Filter;

import android.databinding.DataBindingUtil;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.Utils.DummyLists;
import com.sterlite.dccmappfordealersterlite.adapter.FilterMainAdapter;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityFilterBinding;
import com.sterlite.dccmappfordealersterlite.model.MakeMyPlanFilter.FilterContainer;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class FilterActivity extends BaseActivity {
    ActivityFilterBinding binding;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    FilterMainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAppTheme();
        AppUtils.sendAnalytics(this, getClass().getSimpleName());

        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter);
        toolbar = binding.toolbar.toolbar;
        setUpView(toolbar, binding.extraView);
        setUpNavigationView();
    }


    private void setUpNavigationView() {
        drawer = binding.drawerLayout;
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
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
//        set items to filer here after api call
        if(Constants.IS_DUMMY_MODE)
        adapter.addItems(DummyLists.getFilterContainers());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.rvMenuContainer.getContext(),
                VERTICAL);
        binding.rvMenuContainer.addItemDecoration(dividerItemDecoration);
        binding.btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
                adapter.getAll();
                AppUtils.showToast(FilterActivity.this, getString(R.string.msg_apply_clicked));
            }
        });
        binding.btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
//                todo make api call and clear adapter list;
                AppUtils.showToast(FilterActivity.this, getString(R.string.msg_reset_clicked));
            }
        });
    }
}

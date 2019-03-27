package com.sterlite.dccmappfordealersterlite.activity.TicketView;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.util.Log;

import android.view.View;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.activity.TicketCreate.CreateTicket;
import com.sterlite.dccmappfordealersterlite.adapter.TicketViewAdapter;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityViewTicketBinding;
import com.sterlite.dccmappfordealersterlite.model.Ticket;

import java.util.ArrayList;
import java.util.Map;

public class ViewTicket extends BaseActivity implements ViewTicketContract.View {
    
    ActivityViewTicketBinding binding;
    ViewTicketContract.Presenter<ViewTicketContract.View> presenter;
    TicketViewAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        init();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewTicket.this,CreateTicket.class));
            }
        });
    }

    private void init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_ticket);
        setUpNavigationView(binding.drawerLayout, binding.layoutNavMenu);

        setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.title_activity_view_ticket), true);
        setUpView(true);
        presenter = new ViewTicketPresenter<>();
        presenter.onAttach(this);
        Map<String,String> billDetailMap = DCCMDealerSterlite.getDataManager().loadMap(AppPreferencesHelper.BILLING_DETAIL_MAP);
        String serviceInstance= DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.PREF_SUBSCRIPTION_NUMBER,null);
        Log.e("billAccNumber",billDetailMap.get(serviceInstance)+"");
        presenter.getTicket(billDetailMap.get(serviceInstance));
    }

    @Override
    public void loadDataToView(ArrayList<Ticket> list) {
        adapter.getAll().clear();
        Log.e("List",list+"");
        if (list!=null) {
            if (list.size() >= 1) {
                Log.e("FinalList", list + "");
                adapter.addItems(list);
                adapter.notifyDataSetChanged();
            } else
                setNotRecordsFoundView(true);
        }
        else
            setNotRecordsFoundView(true);
    }

    @Override
    public void setUpView(boolean isReset) {
        if (isReset) {
            adapter = null;
        }
        AppUtils.setVisibility(binding.tvNotFound, View.GONE);

        if (adapter == null) {
            adapter = new TicketViewAdapter(this);
            binding.rvMyOrders.setAdapter(adapter);
        } else {
            binding.rvMyOrders.setAdapter(adapter);
        }

    }

    @Override
    public void setNotRecordsFoundView(boolean isActive) {
        if (isActive) {
            binding.tvNotFound.setVisibility(View.VISIBLE);
        } else {
            binding.tvNotFound.setVisibility(View.GONE);
        }
    }

    @Override
    public void updateFooterFalse() {
        if (adapter != null) {
            adapter.isAddFooter = false;
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void showProgressBar(boolean isFullScreen) {
        if (isFullScreen)
            super.showProgressBar();
        else {
            if (adapter != null) {
                adapter.isAddFooter = true;
                adapter.updateBottomProgress(0);
                adapter.notifyDataSetChanged();
            } else {
                super.showProgressBar();
            }
        }
    }

    @Override
    public void hideProgressBar(boolean isFullScreen) {
        if (isFullScreen)
            super.hideProgressBar();
        else {
            if (adapter != null) {
                adapter.updateBottomProgress(2);
                adapter.notifyDataSetChanged();
            } else {
                super.hideProgressBar();
            }
        }
    }

}

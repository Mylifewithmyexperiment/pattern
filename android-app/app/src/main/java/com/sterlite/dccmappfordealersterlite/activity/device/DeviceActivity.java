package com.sterlite.dccmappfordealersterlite.activity.device;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.activity.inventory.InventoryActivity;
import com.sterlite.dccmappfordealersterlite.adapter.DeviceViewpagerAdapter;
import com.sterlite.dccmappfordealersterlite.adapter.SpecificationAdapter;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityDeviceBinding;
import com.sterlite.dccmappfordealersterlite.model.Device;


public class DeviceActivity extends BaseActivity implements DeviceContract.View {


    private DeviceContract.Presenter<DeviceContract.View> presenter;
    private ActivityDeviceBinding binding;
    private DeviceViewpagerAdapter adapter;
    private SpecificationAdapter deviceFeatureAdapter;
    private SpecificationAdapter deviceTechSpecificationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        setAppTheme();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_device);
        setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.title_devices), true);


        binding.vpPlans.setClipToPadding(false);
        binding.vpPlans.setPageMargin((int) getResources().getDimension(R.dimen._15sdp));


        presenter = new DevicePresenter<>();
        presenter.onAttach(this);
        presenter.init();

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
        binding.btnSelecDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DeviceActivity.this, InventoryActivity.class).putExtra(InventoryActivity.ARG_FROM, "device"));
            }
        });


    }

    @Override
    public void setUpView(boolean isReset) {
        if (isReset) {
            adapter = null;
        }
        if (adapter == null) {
            adapter = new DeviceViewpagerAdapter(this, new DeviceViewpagerAdapter.OnViewpagerItemListener() {
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
        AppUtils.setVisibility(binding.loginscroll, View.VISIBLE);
        AppUtils.setVisibility(binding.btnSelecDevice, View.VISIBLE);
        AppUtils.setVisibility(binding.ivNavigationStart, View.VISIBLE);
        AppUtils.setVisibility(binding.ivNavigationEnd, View.VISIBLE);
        if (position == 0) {
            AppUtils.setVisibility(binding.ivNavigationStart, View.GONE);
        } else if (position == adapter.getCount() - 1) {
            AppUtils.setVisibility(binding.ivNavigationEnd, View.GONE);
        }

        Device plan = adapter.get(position);

        if (plan.getArrFeatures() != null && plan.getArrFeatures().size() > 0) {
            deviceFeatureAdapter = new SpecificationAdapter(this, plan.getArrFeatures(), new OnRecyclerViewItemClickListener<String>() {
                @Override
                public void onClicked(String bean, View view, int position, ViewType viewType) {

                }

                @Override
                public void onLastItemReached() {

                }
            });
            deviceFeatureAdapter.isAddFooter = false;
            binding.rvFeature.setAdapter(deviceFeatureAdapter);
            binding.rvFeature.setNestedScrollingEnabled(false);
            binding.tvFeatureText.setText(getString(R.string.lbl_features, plan.getName()));
        } else {
            AppUtils.setVisibility(binding.tvFeatureText, View.GONE);
            AppUtils.setVisibility(binding.rvFeature, View.GONE);
        }

        if (plan.getArrTechSpecification() != null && plan.getArrTechSpecification().size() > 0) {
            deviceTechSpecificationAdapter = new SpecificationAdapter(this, plan.getArrTechSpecification(), new OnRecyclerViewItemClickListener<String>() {
                @Override
                public void onClicked(String bean, View view, int position, ViewType viewType) {

                }

                @Override
                public void onLastItemReached() {

                }
            });
            deviceTechSpecificationAdapter.isAddFooter = false;
            binding.rvTechSpecification.setAdapter(deviceTechSpecificationAdapter);
            binding.rvTechSpecification.setNestedScrollingEnabled(false);
            binding.tvTechSpecificationText.setText(getString(R.string.lbl_tech_spec, plan.getName()));
        } else {
            AppUtils.setVisibility(binding.rvTechSpecification, View.GONE);
            AppUtils.setVisibility(binding.tvTechSpecificationText, View.GONE);
        }
    }

    @Override
    public void loadDataToView(ArrayList<Device> list) {
        adapter.updateList(list);
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

}

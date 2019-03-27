package com.sterlite.dccmappfordealersterlite.activity.MakeMyPlanDetail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.activity.inventory.InventoryActivity;
import com.sterlite.dccmappfordealersterlite.adapter.MakeMyPlanItemDetailAdapter;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityMakeMyPlanDetailBinding;
import com.sterlite.dccmappfordealersterlite.model.MakeMyPlanModel.MMPBase;
import com.sterlite.dccmappfordealersterlite.model.MakeMyPlanModel.MakeMyPlanItems;
import com.sterlite.dccmappfordealersterlite.model.Plan;


public class MakeMyPlanDetailActivity extends BaseActivity implements MakeMyPlanDetailContract.View {


    public static final String ARG_MMP = "argMMP";
    private ActivityMakeMyPlanDetailBinding binding;
    public float currantValue = 0;
    private MakeMyPlanDetailContract.Presenter<MakeMyPlanDetailContract.View> presenter;
    private MakeMyPlanItems makeMyPlan;
    private MakeMyPlanItemDetailAdapter makeMyPlanItemDetailAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        setAppTheme();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_make_my_plan_detail);
        setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.lbl_my_plan), true);
        makeMyPlan = (MakeMyPlanItems) getIntent().getSerializableExtra(ARG_MMP);
        presenter = new MakeMyPlanDetailPresenter<>();
        presenter.onAttach(this);

        presenter.init();

        binding.btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calculateCurrantValue();
                float percentage = (currantValue * 100) / makeMyPlan.getOneTimeProductPrice().getValue();
                if (percentage <= 100) {
                    Intent intent = new Intent(MakeMyPlanDetailActivity.this, InventoryActivity.class);
                    setdata();
                    startActivity(intent);
                } else {
                    AppUtils.showToast(MakeMyPlanDetailActivity.this, getString(R.string.msg_make_my_plan_detail));
                }

            }
        });
        binding.fitChart.setMinValue(0);
        binding.fitChart.setMaxValue(100);
    }

    private ArrayList<MakeMyPlanItems> getSelectedPlanObjects() {
        ArrayList<MakeMyPlanItems> arrSelectedPlans = new ArrayList<>();
        for (MMPBase mmpBase : makeMyPlanItemDetailAdapter.getAll()) {
            if (mmpBase.getCurrentValuePosition() >= 0 && mmpBase.getArrPlan() != null && mmpBase.getArrPlan().size() > 0) {
                arrSelectedPlans.add(mmpBase.getArrPlan().get(mmpBase.getCurrentValuePosition()));
            }
        }
        return arrSelectedPlans;
    }

    private void setdata() {

        Plan plan = new Plan();
        plan.setPlanId(makeMyPlan.getCode());
        plan.setPlanTitle(makeMyPlan.getName());
        plan.setPricePerMonth(makeMyPlan.getOneTimeProductPrice().getValue() + "");
        plan.setFreeBenefitInRupee(getString(R.string.four_hundred_min));
        DCCMDealerSterlite.getDataManager().setBoolean(AppPreferencesHelper.ARG_FROM_MMP, true);
        DCCMDealerSterlite.getDataManager().setBoolean(AppPreferencesHelper.IS_BB, false);
        DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.PRODUCT_PLAN, AppUtils.getStringFromObj(plan));

        ArrayList<MakeMyPlanItems> items = getSelectedPlanObjects();
        Log.e("MakeMyPlanItems", " START ");
        DCCMDealerSterlite.getDataManager().setint(AppPreferencesHelper.ADDON_SIZE, items.size());
        int cnt = 1;
        for (MakeMyPlanItems i : items) {
            Log.e("MakeMyPlanItems", " " + i);
            DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.MMP_ADDON_CODES + cnt, i.getCode());
            DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.MMP_ADDON_CATEGORY + cnt, i.getCategoryCode());
            cnt++;
        }
    }


    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }


    @Override
    public void setUpView() {
        makeMyPlanItemDetailAdapter = new MakeMyPlanItemDetailAdapter(this, new OnRecyclerViewItemClickListener<MMPBase>() {
            @Override
            public void onClicked(MMPBase bean, View view, int position, ViewType viewType) {

            }

            @Override
            public void onLastItemReached() {

            }
        });
        makeMyPlanItemDetailAdapter.isAddFooter = false;
        makeMyPlanItemDetailAdapter.setOnSeekBarValueChangeListener(new MakeMyPlanItemDetailAdapter.OnSeekBarValueChangeListener() {
            @Override
            public void onValueUpdated() {
                calculateCurrantValue();
                checkAndSetValueToDount();
            }

            @Override
            public void onDataAddAtLastValue() {
                reFillDount();
                makeMyPlanItemDetailAdapter.notifyDataSetChanged();
            }
        });
        binding.rvHomeItem.setNestedScrollingEnabled(false);
        binding.rvHomeItem.setAdapter(makeMyPlanItemDetailAdapter);

    }

    @Override
    public void loadDataToView() {
        ArrayList<MMPBase> arrBase = new ArrayList<>();
        if (makeMyPlan.getArrDataAddOn().getArrPlan() != null && makeMyPlan.getArrDataAddOn().getArrPlan().size() > 0) {
            arrBase.add(makeMyPlan.getArrDataAddOn());
        }
        if (makeMyPlan.getArrDataSubAddOn().getArrPlan() != null && makeMyPlan.getArrDataSubAddOn().getArrPlan().size() > 0) {
            MMPBase arrDataSubAddOn = makeMyPlan.getArrDataSubAddOn();
            arrDataSubAddOn.setSubItem(true);
            arrBase.add(arrDataSubAddOn);
        }
        if (makeMyPlan.getArrMinAddOn().getArrPlan() != null && makeMyPlan.getArrMinAddOn().getArrPlan().size() > 0) {
            arrBase.add(makeMyPlan.getArrMinAddOn());
        }
        if (makeMyPlan.getArrSMSAddOn().getArrPlan() != null && makeMyPlan.getArrSMSAddOn().getArrPlan().size() > 0) {
            arrBase.add(makeMyPlan.getArrSMSAddOn());
        }
        if (makeMyPlan.getArrRoamingAddOn().getArrPlan() != null && makeMyPlan.getArrRoamingAddOn().getArrPlan().size() > 0) {
            arrBase.add(makeMyPlan.getArrRoamingAddOn());
        }

        makeMyPlanItemDetailAdapter.addItems(arrBase);
        AppUtils.setVisibility(binding.scrollMainContainer, View.VISIBLE);
        binding.tvPrice.setText(AppUtils.getPriceWithSymbol(this, String.valueOf(makeMyPlan.getOneTimeProductPrice().getValue())));
    }


    private void calculateCurrantValue() {
        if (makeMyPlanItemDetailAdapter != null)
            currantValue = makeMyPlanItemDetailAdapter.calculateRemainingValue(false);
    }

    private void checkAndSetValueToDount() {
        float percentage = setValueToDonut();
        if (percentage >= 100) {
            boolean isCallOverLimit = makeMyPlanItemDetailAdapter.checkAndSetRemaingValue(makeMyPlan.getOneTimeProductPrice().getValue());
            if (isCallOverLimit) {
                if (percentage > 100)
                    AppUtils.showToast(MakeMyPlanDetailActivity.this, getString(R.string.msg_make_my_plan_detail_plan_value_over));
                makeMyPlanItemDetailAdapter.setOverLimit(true);
            } else {
                reFillDount();
            }
        } else {
            makeMyPlanItemDetailAdapter.setOverLimit(false);
        }
    }

    private void reFillDount() {
        calculateCurrantValue();
        setValueToDonut();
    }

    private float setValueToDonut() {
        float percentage = (currantValue * 100) / makeMyPlan.getOneTimeProductPrice().getValue();
        Log.d(TAG, "percentage: " + percentage);
        binding.fitChart.setValue(percentage);
        return percentage;
    }


}

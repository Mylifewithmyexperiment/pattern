package com.sterlite.dccmappfordealersterlite.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.databinding.ItemPlanDetailLayoutBinding;
import com.sterlite.dccmappfordealersterlite.model.Plan;

public class PlanDetailViewpagerAdapter extends PagerAdapter {
    private static final String TAG = "[HomePageFirstSliderAdapter] : ";
    private LayoutInflater inflater;
    private Context context;
    public static final int VIEW_TYPE_OTHER = 111;
    private List<Plan> arrPlans = new ArrayList<>();
    OnViewpagerItemListener onViewpagerItemListener;

    public PlanDetailViewpagerAdapter(Context context, OnViewpagerItemListener onViewpagerItemListener) {
        this.context = context;
        this.onViewpagerItemListener = onViewpagerItemListener;
        inflater = LayoutInflater.from(context);
    }

    public void updateList(ArrayList<Plan> arrPlans) {
        this.arrPlans.addAll(arrPlans);
        notifyDataSetChanged();
    }

    public Plan get(int position) {
        return arrPlans.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return arrPlans.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup view, final int position) {
        ItemPlanDetailLayoutBinding itemBinding = ItemPlanDetailLayoutBinding.inflate(inflater, view, false);
        Plan plan = arrPlans.get(position);
        itemBinding.tvPlanTitle.setText(plan.getPlanTitle());
        itemBinding.tvPlanData.setText(plan.getDataInGB());
        itemBinding.tvPrice.setText(AppUtils.getPriceWithSymbol(plan.getPricePerMonth()));
        itemBinding.tvRollover.setText(plan.getRolloverDataInGB());
        itemBinding.tvCallRates.setText(plan.getCallRate());
        itemBinding.tvSMS.setText(plan.getSms());
        itemBinding.tvPlanValidity.setText(plan.getValidity());

        view.addView(itemBinding.getRoot(), 0);
        return itemBinding.getRoot();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }


    public interface OnViewpagerItemListener {
        void onItemClicked(int position, int viewType);
    }
}

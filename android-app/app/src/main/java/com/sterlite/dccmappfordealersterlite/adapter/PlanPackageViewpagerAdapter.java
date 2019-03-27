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
import com.sterlite.dccmappfordealersterlite.databinding.ItemPlanPackageLayoutBinding;
import com.sterlite.dccmappfordealersterlite.model.PlanPackage;

public class PlanPackageViewpagerAdapter extends PagerAdapter {
    private LayoutInflater inflater;
    private Context context;
    private List<PlanPackage> arrPlans = new ArrayList<>();
    OnViewpagerItemListener onViewpagerItemListener;

    public PlanPackageViewpagerAdapter(Context context, OnViewpagerItemListener onViewpagerItemListener) {
        this.context = context;
        this.onViewpagerItemListener = onViewpagerItemListener;
        inflater = LayoutInflater.from(context);
    }

    public void updateList(ArrayList<PlanPackage> arrPlans) {
        this.arrPlans.addAll(arrPlans);
        notifyDataSetChanged();
    }

    public PlanPackage get(int position) {
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
        ItemPlanPackageLayoutBinding itemBinding = ItemPlanPackageLayoutBinding.inflate(inflater, view, false);
        PlanPackage plan = arrPlans.get(position);
        itemBinding.tvPlanTitle.setText(plan.getName());
        itemBinding.tvPrice.setText(AppUtils.getPriceWithSymbol(String.valueOf(plan.getOneTimeProductPrice().getValue())));

        if (!AppUtils.isEmpty(plan.getLeftMsg()))
            itemBinding.tvLeftMsg.setText(plan.getLeftMsg());

        if (!AppUtils.isEmpty(plan.getTopMsg()))
            itemBinding.tvTopMsg.setText(plan.getTopMsg());

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

package com.sterlite.dccmappfordealersterlite.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.databinding.ItemMakeMyPlanLayoutBinding;
import com.sterlite.dccmappfordealersterlite.model.MakeMyPlanModel.MMPBase;
import com.sterlite.dccmappfordealersterlite.model.MakeMyPlanModel.MakeMyPlanItems;

/**
 * Created by etech3 on 9/1/18.
 */
public class MakeMyPlanAdapter extends BaseMainAdpter {


    private ArrayList<MakeMyPlanItems> arrMakeMyPlans = new ArrayList<>();

    public MakeMyPlanAdapter(Context context, OnRecyclerViewItemClickListener<MakeMyPlanItems> onRecycleItemClickListener) {
        init(context, arrMakeMyPlans, onRecycleItemClickListener);
    }

    public void addItems(ArrayList<MakeMyPlanItems> arrMakeMyPlans) {
        this.arrMakeMyPlans.addAll(arrMakeMyPlans);
        notifyDataSetChanged();
    }

    public void clearAllItem(){
        arrMakeMyPlans.clear();
        notifyDataSetChanged();
    }

    public void remove(int position) {
        arrMakeMyPlans.remove(position);
        notifyDataSetChanged();
    }

    public MakeMyPlanItems get(int position) {
        return arrMakeMyPlans.get(position);
    }

    public ArrayList getAll() {
        return arrMakeMyPlans;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        if (viewType == TYPE_ITEM) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ItemMakeMyPlanLayoutBinding itemBinding = ItemMakeMyPlanLayoutBinding.inflate(layoutInflater, parent, false);
            viewHolder = new ViewHolder(itemBinding);
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            final MakeMyPlanItems item = arrMakeMyPlans.get(position);
            viewHolder.binding.tvPrice.setText(AppUtils.getPriceWithSymbol(context, String.valueOf(item.getOneTimeProductPrice().getValue())));
            MakeMyPlanItemAdapter makeMyPlanItemAdapter = new MakeMyPlanItemAdapter(context, item.getArrUpToBenefits(), new OnRecyclerViewItemClickListener<MMPBase>() {
                @Override
                public void onClicked(MMPBase bean, View view, int position, ViewType viewType) {

                }

                @Override
                public void onLastItemReached() {

                }
            });
            makeMyPlanItemAdapter.isAddFooter = false;
            viewHolder.binding.rvMakeMyPlanItems.setAdapter(makeMyPlanItemAdapter);
            viewHolder.binding.btnSelectPlan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRecycleItemClickListener.onClicked(get(viewHolder.getAdapterPosition()), view, viewHolder.getAdapterPosition(), OnRecyclerViewItemClickListener.ViewType.View);
                }
            });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemMakeMyPlanLayoutBinding binding;

        public ViewHolder(ItemMakeMyPlanLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

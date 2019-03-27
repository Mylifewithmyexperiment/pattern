package com.sterlite.dccmappfordealersterlite.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.databinding.ItemMakeMyPlanItemLayoutBinding;
import com.sterlite.dccmappfordealersterlite.model.MakeMyPlanModel.MMPBase;

/**
 * Created by etech3 on 9/1/18.
 */
public class MakeMyPlanItemAdapter extends BaseMainAdpter {


    private ArrayList<MMPBase> arrMakeMyPlanItems = new ArrayList<>();

    public MakeMyPlanItemAdapter(Context context, ArrayList<MMPBase> arrMakeMyPlanItems, OnRecyclerViewItemClickListener<MMPBase> onRecycleItemClickListener) {
        init(context, arrMakeMyPlanItems, onRecycleItemClickListener);
        this.arrMakeMyPlanItems = arrMakeMyPlanItems;
    }

    public MMPBase get(int position) {
        return arrMakeMyPlanItems.get(position);
    }

    public ArrayList getAll() {
        return arrMakeMyPlanItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        if (viewType == TYPE_ITEM) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ItemMakeMyPlanItemLayoutBinding itemBinding = ItemMakeMyPlanItemLayoutBinding.inflate(layoutInflater, parent, false);
            viewHolder = new ViewHolder(itemBinding);
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            final MMPBase item = arrMakeMyPlanItems.get(position);
            viewHolder.binding.ivItem.setImageResource(item.getItemImageId());
            String value = context.getString(R.string.lbl_upto) + " " + item.getLastItem().getName();
            viewHolder.binding.tvValue.setText(value);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemMakeMyPlanItemLayoutBinding binding;

        public ViewHolder(ItemMakeMyPlanItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

package com.sterlite.dccmappfordealersterlite.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.databinding.ItemCheckboxWithSearchResultsBinding;
import com.sterlite.dccmappfordealersterlite.model.MakeMyPlanFilter.Filter;

/**
 * Created by etech-10 on 6/9/18.
 */

public class FilterSubAdapter extends BaseMainAdpter {
    ArrayList<Filter> filterItemsContainerList = new ArrayList<>();


    public FilterSubAdapter(Context context, OnRecyclerViewItemClickListener<Filter> orderOnRecyclerViewItemClickListener) {
        init(context, filterItemsContainerList, orderOnRecyclerViewItemClickListener);
    }

    public void addItems(ArrayList<Filter> arrIncidentList) {
        this.filterItemsContainerList.addAll(arrIncidentList);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        filterItemsContainerList.remove(position);
        notifyDataSetChanged();
    }

    public Filter get(int position) {
        return filterItemsContainerList.get(position);
    }

    public ArrayList getAll() {
        return filterItemsContainerList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        if (viewType == TYPE_ITEM) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ItemCheckboxWithSearchResultsBinding binding = ItemCheckboxWithSearchResultsBinding.inflate(layoutInflater, parent, false);
            viewHolder = new ViewHolder(binding);
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            final Filter filter = filterItemsContainerList.get(position);
            viewHolder.binding.checkbox.setText(filter.getTitle());
            viewHolder.binding.checkbox.setChecked(filter.isChecked());
            viewHolder.binding.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    filter.setChecked(isChecked);
//                    notifyItemChanged(viewHolder.getAdapterPosition());
//                    onRecycleItemClickListener.onClicked(get(viewHolder.getAdapterPosition()), viewHolder.itemView, viewHolder.getAdapterPosition(), OnRecyclerViewItemClickListener.ViewType.View);
                }
            });
            viewHolder.binding.filterResults.setText(filter.getNoOfResults());
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemCheckboxWithSearchResultsBinding binding;

        public ViewHolder(ItemCheckboxWithSearchResultsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}

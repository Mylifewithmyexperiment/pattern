package com.sterlite.dccmappfordealersterlite.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;

import com.sterlite.dccmappfordealersterlite.databinding.ItemExpandedListViewBinding;
import com.sterlite.dccmappfordealersterlite.model.MakeMyPlanFilter.Filter;
import com.sterlite.dccmappfordealersterlite.model.MakeMyPlanFilter.FilterContainer;

/**
 * Created by etech-10 on 6/9/18.
 */

public class FilterMainAdapter extends BaseMainAdpter {
    ArrayList<FilterContainer> filterItemsContainerList = new ArrayList<>();


    public FilterMainAdapter(Context context, OnRecyclerViewItemClickListener<FilterContainer> orderOnRecyclerViewItemClickListener) {
        init(context, filterItemsContainerList, orderOnRecyclerViewItemClickListener);
    }

    public void addItems(ArrayList<FilterContainer> arrIncidentList) {
        this.filterItemsContainerList.addAll(arrIncidentList);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        filterItemsContainerList.remove(position);
        notifyDataSetChanged();
    }

    public FilterContainer get(int position) {
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
            ItemExpandedListViewBinding binding = ItemExpandedListViewBinding.inflate(layoutInflater, parent, false);
            viewHolder = new ViewHolder(binding);
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            final FilterContainer filterContainer = filterItemsContainerList.get(position);
            viewHolder.binding.tvTitle.setText(filterContainer.getTitle());
            FilterSubAdapter subAdapter = new FilterSubAdapter(context, new OnRecyclerViewItemClickListener<Filter>() {
                @Override
                public void onClicked(Filter bean, View view, int position, ViewType viewType) {
                    notifyItemChanged(holder.getAdapterPosition());
                }

                @Override
                public void onLastItemReached() {
                }
            });
            subAdapter.isAddFooter=false;
            subAdapter.addItems(filterContainer.getFilterList());
            viewHolder.binding.rvFilterContainer.setAdapter(subAdapter);

//            viewHolder.binding.tvOrderNo.setText(filterContainer.getOrderId());
//            viewHolder.binding.tvOrderDate.setText(filterContainer.getDate());
//            viewHolder.binding.tvName.setText(filterContainer.getName());
//            viewHolder.binding.tvStatus.setText(filterContainer.getOrderStatus());
//            viewHolder.binding.tvPrice.setText(filterContainer.getTotal());
            /*viewHolder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRecycleItemClickListener.onClicked(get(viewHolder.getAdapterPosition()), view, viewHolder.getAdapterPosition(), OnRecyclerViewItemClickListener.ViewType.View);
                }
            });*/
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemExpandedListViewBinding binding;

        public ViewHolder(ItemExpandedListViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}

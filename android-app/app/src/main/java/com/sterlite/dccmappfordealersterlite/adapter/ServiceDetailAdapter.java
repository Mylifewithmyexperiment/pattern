package com.sterlite.dccmappfordealersterlite.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.databinding.ItemServiceDetailBinding;
import com.sterlite.dccmappfordealersterlite.model.Store;

/**
 * Created by etech3 on 9/1/18.
 */

public class ServiceDetailAdapter extends BaseMainAdpter {


    private ArrayList<Store> arrIncidentList = new ArrayList<>();

    public ServiceDetailAdapter(Context context, OnRecyclerViewItemClickListener<Store> onRecycleItemClickListener) {
        init(context, arrIncidentList, onRecycleItemClickListener);
    }

    public void addIncidents(ArrayList<Store> arrIncidentList) {
        this.arrIncidentList.addAll(arrIncidentList);
        notifyDataSetChanged();
    }

    public void remove(int position){
        arrIncidentList.remove(position);
        notifyDataSetChanged();
    }

    public Store get(int position) {
        return arrIncidentList.get(position);
    }

    public ArrayList<Store> getAll() {
        return arrIncidentList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        if (viewType == TYPE_ITEM) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ItemServiceDetailBinding itemBinding = ItemServiceDetailBinding.inflate(layoutInflater, parent, false);
            viewHolder = new ViewHolder(itemBinding);
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            final Store store = arrIncidentList.get(position);
            viewHolder.bind(store);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRecycleItemClickListener.onClicked(get(viewHolder.getAdapterPosition()),view, viewHolder.getAdapterPosition(), OnRecyclerViewItemClickListener.ViewType.View);
                }
            });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemServiceDetailBinding binding;

        public ViewHolder(ItemServiceDetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Store store) {
            binding.setStore(store);
            binding.executePendingBindings();
        }
    }
}

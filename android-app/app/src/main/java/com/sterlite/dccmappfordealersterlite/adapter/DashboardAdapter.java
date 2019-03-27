package com.sterlite.dccmappfordealersterlite.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.databinding.ItemDashboardIncidentLayoutNewBinding;

/**
 * Created by etech3 on 9/1/18.
 */

public class DashboardAdapter extends BaseMainAdpter {


    private ArrayList arrIncidentList = new ArrayList<>();

    public DashboardAdapter(Context context, OnRecyclerViewItemClickListener onRecycleItemClickListener) {
        init(context, arrIncidentList, onRecycleItemClickListener);
    }

    public void addIncidents(ArrayList arrIncidentList) {
        this.arrIncidentList.addAll(arrIncidentList);
        notifyDataSetChanged();
    }

    public void remove(int position){
        arrIncidentList.remove(position);
        notifyDataSetChanged();
    }

    public Object getIncident(int position) {
        return arrIncidentList.get(position);
    }

    public ArrayList getIncidents() {
        return arrIncidentList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        if (viewType == TYPE_ITEM) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ItemDashboardIncidentLayoutNewBinding itemBinding = ItemDashboardIncidentLayoutNewBinding.inflate(layoutInflater, parent, false);
            viewHolder = new ViewHolder(itemBinding);
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            final Object incident = arrIncidentList.get(position);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRecycleItemClickListener.onClicked(getIncident(viewHolder.getAdapterPosition()),view, viewHolder.getAdapterPosition(), OnRecyclerViewItemClickListener.ViewType.View);
                }
            });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemDashboardIncidentLayoutNewBinding binding;

        public ViewHolder(ItemDashboardIncidentLayoutNewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

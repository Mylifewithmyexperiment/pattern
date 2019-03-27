package com.sterlite.dccmappfordealersterlite.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.databinding.ItemViewTicketBinding;
import com.sterlite.dccmappfordealersterlite.model.Ticket;

import java.util.ArrayList;

/**
 * Created by elitecore on 7/1/19.
 */

public class TicketViewAdapter extends BaseMainAdpter {

    private ArrayList<Ticket> TicketData = new ArrayList<>();

    public TicketViewAdapter(Context context) {
        init(context, TicketData, null);
    }

    public TicketViewAdapter(Context context, OnRecyclerViewItemClickListener<Ticket> onRecycleItemClickListener) {
        init(context, TicketData, onRecycleItemClickListener);
    }

    public void addItems(ArrayList<Ticket> Ticket) {
        this.TicketData.clear();
        this.TicketData.addAll(Ticket);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        TicketData.remove(position);
        notifyDataSetChanged();
    }

    public Ticket get(int position) {
        return TicketData.get(position);
    }

    public ArrayList getAll() {
        return TicketData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        if (viewType == TYPE_ITEM) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

            ItemViewTicketBinding itemBinding = ItemViewTicketBinding.inflate(layoutInflater, parent, false);
            viewHolder = new TicketViewAdapter.ViewHolder(itemBinding);
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof TicketViewAdapter.ViewHolder) {
            final TicketViewAdapter.ViewHolder viewHolder = (TicketViewAdapter.ViewHolder) holder;
            final Ticket data = TicketData.get(position);

            viewHolder.binding.tvTicketNumber.setText(data.getTicketNumber());
            viewHolder.binding.tvTitle.setText(data.getTitle());
            viewHolder.binding.tvCategory.setText(data.getCategory());
            viewHolder.binding.tvSubCategory.setText(data.getSubCategory());
            viewHolder.binding.tvDescription.setText(data.getDescription());
            viewHolder.binding.tvStatus.setText(data.getStatus());
            viewHolder.binding.tvCreatedTime.setText(data.getCreatedTime());
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemViewTicketBinding binding;

        public ViewHolder(ItemViewTicketBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
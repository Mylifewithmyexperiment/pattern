package com.sterlite.dccmappfordealersterlite.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.databinding.ItemMoreLayoutBinding;
import com.sterlite.dccmappfordealersterlite.model.MoreItem;

/**
 * Created by etech3 on 9/1/18.
 */
public class MoreAdapter extends BaseMainAdpter {


    private ArrayList<MoreItem> arrMoreList = new ArrayList<>();

    public MoreAdapter(Context context, OnRecyclerViewItemClickListener<MoreItem> onRecycleItemClickListener) {
        init(context, arrMoreList, onRecycleItemClickListener);
    }

    public void addItems(ArrayList<MoreItem> arrIncidentList) {
        this.arrMoreList.addAll(arrIncidentList);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        arrMoreList.remove(position);
        notifyDataSetChanged();
    }

    public MoreItem get(int position) {
        return arrMoreList.get(position);
    }

    public ArrayList getAll() {
        return arrMoreList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        if (viewType == TYPE_ITEM) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ItemMoreLayoutBinding itemBinding = ItemMoreLayoutBinding.inflate(layoutInflater, parent, false);
            viewHolder = new ViewHolder(itemBinding);
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            final MoreItem item = arrMoreList.get(position);
            viewHolder.binding.imgMoreIcon.setImageResource(item.getMoreResId());
            viewHolder.binding.tvMoreName.setText(item.getMoreName());

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRecycleItemClickListener.onClicked(get(viewHolder.getAdapterPosition()), view, viewHolder.getAdapterPosition(), OnRecyclerViewItemClickListener.ViewType.View);
                }
            });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemMoreLayoutBinding binding;

        public ViewHolder(ItemMoreLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

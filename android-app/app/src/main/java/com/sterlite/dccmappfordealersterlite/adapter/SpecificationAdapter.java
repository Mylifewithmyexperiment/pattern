package com.sterlite.dccmappfordealersterlite.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.databinding.ItemFeatureLayoutBinding;

/**
 * Created by etech3 on 9/1/18.
 */
public class SpecificationAdapter extends BaseMainAdpter {


    private ArrayList<String> arrString = new ArrayList<>();

    public SpecificationAdapter(Context context, ArrayList<String> arrString, OnRecyclerViewItemClickListener<String> onRecycleItemClickListener) {
        init(context, arrString, onRecycleItemClickListener);
        this.arrString = arrString;
    }

    public void remove(int position) {
        arrString.remove(position);
        notifyDataSetChanged();
    }

    public String get(int position) {
        return arrString.get(position);
    }

    public ArrayList getAll() {
        return arrString;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        if (viewType == TYPE_ITEM) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ItemFeatureLayoutBinding itemBinding = ItemFeatureLayoutBinding.inflate(layoutInflater, parent, false);
            viewHolder = new ViewHolder(itemBinding);
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            final String item = arrString.get(position);
            viewHolder.binding.tvFeature.setText(item);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRecycleItemClickListener.onClicked(get(viewHolder.getAdapterPosition()), view, viewHolder.getAdapterPosition(), OnRecyclerViewItemClickListener.ViewType.View);
                }
            });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemFeatureLayoutBinding binding;

        public ViewHolder(ItemFeatureLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

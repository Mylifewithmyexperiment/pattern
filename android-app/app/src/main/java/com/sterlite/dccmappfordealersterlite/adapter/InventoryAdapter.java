package com.sterlite.dccmappfordealersterlite.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.activity.inventory.InventoryActivity;
import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.databinding.ItemInventoryLayoutBinding;
import com.sterlite.dccmappfordealersterlite.model.Inventory;

/**
 * Created by etech3 on 9/1/18.
 */
public class InventoryAdapter extends BaseMainAdpter {


    private ArrayList<Inventory> arrIncidentList = new ArrayList<>();

    public InventoryAdapter(Context context, OnRecyclerViewItemClickListener<Inventory> onRecycleItemClickListener) {
        init(context, arrIncidentList, onRecycleItemClickListener);
    }

    public void addItems(ArrayList<Inventory> arrIncidentList) {
        this.arrIncidentList.clear();
        this.arrIncidentList.addAll(arrIncidentList);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        arrIncidentList.remove(position);
        notifyDataSetChanged();
    }

    public Inventory get(int position) {
        return arrIncidentList.get(position);
    }

    public ArrayList getAll() {
        return arrIncidentList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        if (viewType == TYPE_ITEM) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ItemInventoryLayoutBinding itemBinding = ItemInventoryLayoutBinding.inflate(layoutInflater, parent, false);
            viewHolder = new ViewHolder(itemBinding);
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            final Inventory item = arrIncidentList.get(position);
            viewHolder.bind(item);
            if (item.getNumberType().equalsIgnoreCase(InventoryActivity.INV_PREMIUM)) {
                viewHolder.binding.ivNumberType.setImageResource(R.drawable.ic_type_premimum);
            } else {
                viewHolder.binding.ivNumberType.setImageResource(R.drawable.ic_type_free);
            }
            viewHolder.binding.tvPrice.setText(AppUtils.getPriceWithSymbol(item.getPrice()));
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRecycleItemClickListener.onClicked(get(viewHolder.getAdapterPosition()), view, viewHolder.getAdapterPosition(), OnRecyclerViewItemClickListener.ViewType.View);
                }
            });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemInventoryLayoutBinding binding;

        public ViewHolder(ItemInventoryLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Inventory inventory) {
            binding.setInventory(inventory);
            binding.executePendingBindings();
        }
    }
}
package com.sterlite.dccmappfordealersterlite.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.databinding.ItemOrderPreviewListBinding;
import com.sterlite.dccmappfordealersterlite.model.CartItem;

public class OrderDetailsItemsAdapter extends BaseMainAdpter {
    private ArrayList<CartItem> itemList = new ArrayList<>();

    public OrderDetailsItemsAdapter(Context context, OnRecyclerViewItemClickListener<CartItem> recyclerViewItemClickListener) {
        init(context, itemList, recyclerViewItemClickListener);
    }

    public void addItems(ArrayList<CartItem> arrCartList) {
        this.itemList.addAll(arrCartList);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        itemList.remove(position);
        notifyDataSetChanged();
    }
    public void removeAll() {
        itemList.clear();
        notifyDataSetChanged();
    }

    public CartItem get(int position) {
        return itemList.get(position);
    }

    public ArrayList getAll() {
        return itemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        if (viewType == TYPE_ITEM) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ItemOrderPreviewListBinding itemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_order_preview_list, parent, false);
            viewHolder = new ViewHolder(itemBinding);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            CartItem cartItem = get(position);
            AppUtils.setImageUrl(context, cartItem.getImageUrl(), viewHolder.binding.ivProductImg, viewHolder.binding.pbImg);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                                       @Override
                                                       public void onClick(View view) {
                                                           onRecycleItemClickListener.onClicked(get(viewHolder.getAdapterPosition()), view, viewHolder.getAdapterPosition(), OnRecyclerViewItemClickListener.ViewType.View);
                                                       }
                                                   }
            );
            viewHolder.binding.tvProductName.setText(cartItem.getTitle());
            viewHolder.binding.tvQty.setText(String.valueOf(cartItem.getQuantity()));
            viewHolder.binding.tvPrice.setText(cartItem.getRowTotal());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemOrderPreviewListBinding binding;

        public ViewHolder(ItemOrderPreviewListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}

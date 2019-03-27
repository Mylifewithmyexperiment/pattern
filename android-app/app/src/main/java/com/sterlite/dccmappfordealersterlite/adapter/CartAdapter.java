package com.sterlite.dccmappfordealersterlite.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.databinding.ItemCartBinding;
import com.sterlite.dccmappfordealersterlite.model.CartItem;

/**
 * Created by etech-10 on 31/8/18.
 */

public class CartAdapter extends BaseMainAdpter {
    private ArrayList<CartItem> arrCartList = new ArrayList<>();

    public CartAdapter(Context context, OnRecyclerViewItemClickListener<CartItem> onRecycleItemClickListener) {
        init(context, arrCartList, onRecycleItemClickListener);
    }

    public void addItems(ArrayList<CartItem> arrCartList) {
        this.arrCartList.addAll(arrCartList);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        arrCartList.remove(position);
        notifyDataSetChanged();
    }

    public CartItem get(int position) {
        return arrCartList.get(position);
    }

    public ArrayList getAll() {
        return arrCartList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        if (viewType == TYPE_ITEM) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ItemCartBinding itemBinding = ItemCartBinding.inflate(layoutInflater, parent, false);
            viewHolder = new CartAdapter.ViewHolder(itemBinding);
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof CartAdapter.ViewHolder) {
            final CartAdapter.ViewHolder viewHolder = (CartAdapter.ViewHolder) holder;
            final CartItem cartItem = arrCartList.get(position);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRecycleItemClickListener.onClicked(get(viewHolder.getAdapterPosition()), view, viewHolder.getAdapterPosition(), OnRecyclerViewItemClickListener.ViewType.View);
                }
            });
            viewHolder.binding.imgdelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRecycleItemClickListener.onClicked(get(viewHolder.getAdapterPosition()), view, viewHolder.getAdapterPosition(), OnRecyclerViewItemClickListener.ViewType.Delete);
                }
            });
            viewHolder.binding.imgplus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRecycleItemClickListener.onClicked(get(viewHolder.getAdapterPosition()), view, viewHolder.getAdapterPosition(), OnRecyclerViewItemClickListener.ViewType.Add);
                }
            });
            viewHolder.binding.imgminus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRecycleItemClickListener.onClicked(get(viewHolder.getAdapterPosition()), view, viewHolder.getAdapterPosition(), OnRecyclerViewItemClickListener.ViewType.Remove);
                }
            });
            viewHolder.binding.tvItemName.setText(cartItem.getTitle());
            viewHolder.binding.tvItemDescription.setText(cartItem.getDescription());
            viewHolder.binding.tvPrice.setText(cartItem.getPrice());
            viewHolder.binding.tvquantity.setText(String.valueOf(cartItem.getQuantity()));
            viewHolder.binding.tvRowTotal.setText(cartItem.getRowTotal());
            AppUtils.setImageUrl(context,cartItem.getImageUrl(),((ViewHolder) holder).binding.ivItemImage,((ViewHolder) holder).binding.progressBar);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemCartBinding binding;

        public ViewHolder(ItemCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

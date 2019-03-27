package com.sterlite.dccmappfordealersterlite.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.databinding.LayoutProgressBottomBinding;

/**
 * Created by etech3 on 1/6/18.
 */

public class BaseMainAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_FOOTER = 2;
    protected static final int TYPE_HEADER = 1;
    protected static final int TYPE_ITEM = 0;

    private ArrayList arrayList;
    protected Context context;
    protected OnRecyclerViewItemClickListener onRecycleItemClickListener;
    protected int isShowProgressBar = -1;
    public boolean isAddFooter = true;

    private int currentPosition;
    private Handler handler;

    public void init(Context context, ArrayList arrayList, OnRecyclerViewItemClickListener onRecycleItemClickListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.onRecycleItemClickListener = onRecycleItemClickListener;
        handler = new Handler();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_FOOTER) {
            LayoutProgressBottomBinding itemBinding = LayoutProgressBottomBinding.inflate(layoutInflater, parent, false);
            return new FooterViewHolder(itemBinding);
        }
        return null;
    }

    /**
     * @param isShowProgressBar 0=VISIBLE,1=INVISIBLE,2=GONE
     */
    public void updateBottomProgress(int isShowProgressBar) {
        this.isShowProgressBar = isShowProgressBar;
        try {
            notifyItemChanged(arrayList.size());
        } catch (Exception e) {

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        currentPosition = position;

        if (holder instanceof FooterViewHolder) {
            final FooterViewHolder viewHolder = (FooterViewHolder) holder;
            if (isShowProgressBar == 0) {
                viewHolder.binding.relProgressBottomView.setVisibility(View.VISIBLE);
            } else if (isShowProgressBar == 1) {
                viewHolder.binding.relProgressBottomView.setVisibility(View.INVISIBLE);
            } else {
                viewHolder.binding.relProgressBottomView.setVisibility(View.GONE);
            }
        }
        if(isAddFooter){
            if (arrayList.size() > 1 && position == arrayList.size()) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (currentPosition >= arrayList.size()) {
                            if(onRecycleItemClickListener!=null)
                                onRecycleItemClickListener.onLastItemReached();
                        }
                    }
                }, 1000);
            }
        }else{
            if (arrayList.size() > 1 && position == arrayList.size()-1) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (currentPosition >= arrayList.size()-1) {
                            if(onRecycleItemClickListener!=null)
                            onRecycleItemClickListener.onLastItemReached();
                        }
                    }
                }, 1000);
            }
        }

    }

    @Override
    public int getItemCount() {
        if (isAddFooter) {
        return arrayList.size() + 1;
    }
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (position == arrayList.size()) {
            return TYPE_FOOTER;
        }

        return TYPE_ITEM;
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        LayoutProgressBottomBinding binding;

        public FooterViewHolder(LayoutProgressBottomBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

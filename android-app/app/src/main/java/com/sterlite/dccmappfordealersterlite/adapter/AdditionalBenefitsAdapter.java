package com.sterlite.dccmappfordealersterlite.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.databinding.ItemAdditionBenefitLayoutBinding;
import com.sterlite.dccmappfordealersterlite.model.PlanBenefit;

/**
 * Created by etech3 on 9/1/18.
 */
public class AdditionalBenefitsAdapter extends BaseMainAdpter {


    private ArrayList<String> arrDescription = new ArrayList<>();

    public AdditionalBenefitsAdapter(Context context, ArrayList<String> arrIncidentList, OnRecyclerViewItemClickListener<PlanBenefit> onRecycleItemClickListener) {
        init(context, arrIncidentList, onRecycleItemClickListener);
        this.arrDescription = arrIncidentList;
    }

    public void remove(int position) {
        arrDescription.remove(position);
        notifyDataSetChanged();
    }

    public String get(int position) {
        return arrDescription.get(position);
    }

    public ArrayList getAll() {
        return arrDescription;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        if (viewType == TYPE_ITEM) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ItemAdditionBenefitLayoutBinding itemBinding = ItemAdditionBenefitLayoutBinding.inflate(layoutInflater, parent, false);
            viewHolder = new ViewHolder(itemBinding);
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            final String item = arrDescription.get(position);
            viewHolder.binding.tvBenefits.setText(item);
//            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onRecycleItemClickListener.onClicked(get(viewHolder.getAdapterPosition()), view, viewHolder.getAdapterPosition(), OnRecyclerViewItemClickListener.ViewType.View);
//                }
//            });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemAdditionBenefitLayoutBinding binding;

        public ViewHolder(ItemAdditionBenefitLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

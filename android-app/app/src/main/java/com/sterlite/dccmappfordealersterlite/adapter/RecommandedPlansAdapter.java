package com.sterlite.dccmappfordealersterlite.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.databinding.ItemRecommandedRoundPlansBinding;
import com.sterlite.dccmappfordealersterlite.model.ReccomandedPlanPOJO;

/**
 * Created by etech-10 on 8/9/18.
 */

public class RecommandedPlansAdapter extends BaseMainAdpter {
    ArrayList<ReccomandedPlanPOJO> reccomandedPlanList = new ArrayList<>();
    int selected_position =RecyclerView.NO_POSITION;


    public RecommandedPlansAdapter(Context context, OnRecyclerViewItemClickListener<ReccomandedPlanPOJO> orderOnRecyclerViewItemClickListener) {
        init(context, reccomandedPlanList, orderOnRecyclerViewItemClickListener);
        isAddFooter=false;
    }

    public void addItems(ArrayList<ReccomandedPlanPOJO> arrIncidentList) {
        this.reccomandedPlanList.addAll(arrIncidentList);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        reccomandedPlanList.remove(position);
        notifyDataSetChanged();
    }

    public ReccomandedPlanPOJO get(int position) {
        return reccomandedPlanList.get(position);
    }

    public ArrayList getAll() {
        return reccomandedPlanList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        if (viewType == TYPE_ITEM) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ItemRecommandedRoundPlansBinding itemBinding = ItemRecommandedRoundPlansBinding.inflate(layoutInflater, parent, false);
            viewHolder = new ViewHolder(itemBinding);
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            final ReccomandedPlanPOJO reccomandedPlan = reccomandedPlanList.get(position);
            viewHolder.binding.amount.setText(reccomandedPlan.getCurrencyCode()+" "+reccomandedPlan.getAmount());
            viewHolder.binding.sorDesc.setText(reccomandedPlan.getSortDesc());
            viewHolder.binding.amount.setTextColor(selected_position == position ? context.getResources().getColor(R.color.app_text_color_light) : context.getResources().getColor(R.color.black));
            holder.itemView.setBackground(selected_position == position ? context.getDrawable(R.drawable.bg_circle_red) : context.getDrawable(R.drawable.bg_circle));

            viewHolder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (viewHolder.getAdapterPosition() == RecyclerView.NO_POSITION) return;

                    // Updating old as well as new positions
                    notifyItemChanged(selected_position);
                    selected_position = viewHolder.getAdapterPosition();
                    notifyItemChanged(selected_position);
                    onRecycleItemClickListener.onClicked(get(viewHolder.getAdapterPosition()), view, viewHolder.getAdapterPosition(), OnRecyclerViewItemClickListener.ViewType.View);
                }
            });
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemRecommandedRoundPlansBinding binding;

        public ViewHolder(ItemRecommandedRoundPlansBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}

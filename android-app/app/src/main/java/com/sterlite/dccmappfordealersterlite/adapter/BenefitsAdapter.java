package com.sterlite.dccmappfordealersterlite.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.databinding.ItemBenefitLayoutBinding;
import com.sterlite.dccmappfordealersterlite.model.PlanBenefit;

/**
 * Created by etech3 on 9/1/18.
 */
public class BenefitsAdapter extends BaseMainAdpter {


    private ArrayList<PlanBenefit> arrIncidentList = new ArrayList<>();

    public BenefitsAdapter(Context context, ArrayList<PlanBenefit> arrIncidentList, OnRecyclerViewItemClickListener<PlanBenefit> onRecycleItemClickListener) {
        init(context, arrIncidentList, onRecycleItemClickListener);
        this.arrIncidentList = arrIncidentList;
    }

    public void remove(int position) {
        arrIncidentList.remove(position);
        notifyDataSetChanged();
    }

    public PlanBenefit get(int position) {
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
            ItemBenefitLayoutBinding itemBinding = ItemBenefitLayoutBinding.inflate(layoutInflater, parent, false);
            viewHolder = new ViewHolder(itemBinding);
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            final PlanBenefit item = arrIncidentList.get(position);
            viewHolder.binding.ivBenefit.setImageResource(getImageId(item.getBenefit_Image()));
            viewHolder.binding.tvDescription.setText(item.getDescription());
            viewHolder.binding.tvTitle.setText(item.getTitle());
//            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onRecycleItemClickListener.onClicked(get(viewHolder.getAdapterPosition()), view, viewHolder.getAdapterPosition(), OnRecyclerViewItemClickListener.ViewType.View);
//                }
//            });
        }
    }

    private int getImageId(String benefit_image) {
        switch (benefit_image) {
            case Constants.VOICE:
                return R.drawable.ic_voice;
            case Constants.VIDEO:
                return R.drawable.ic_youtube;
            case Constants.SMS:
                return R.drawable.ic_sms;
            case Constants.DATA:
                return R.drawable.ic_youtube;
            case Constants.ROUTER:
                return R.drawable.ic_router_black;
            case Constants.NETFLIX:
                return R.drawable.ic_netflix;
            case Constants.PRIME:
                return R.drawable.ic_amazon;
            case Constants.YOUTUBE:
                return R.drawable.ic_youtube;
            default:
                return R.drawable.ic_sms;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemBenefitLayoutBinding binding;

        public ViewHolder(ItemBenefitLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

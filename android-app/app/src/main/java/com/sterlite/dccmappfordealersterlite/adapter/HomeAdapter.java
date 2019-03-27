package com.sterlite.dccmappfordealersterlite.adapter;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.databinding.ItemHomeLayoutBinding;
import com.sterlite.dccmappfordealersterlite.model.HomeFragment.BalanceData;

/**
 * Created by etech3 on 9/1/18.
 */
public class HomeAdapter extends BaseMainAdpter {


    private ArrayList<BalanceData> arrIncidentList = new ArrayList<>();
    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;

    public HomeAdapter(Context context, OnRecyclerViewItemClickListener<BalanceData> onRecycleItemClickListener) {
        init(context, arrIncidentList, onRecycleItemClickListener);
    }

    public void addItems(ArrayList<BalanceData> arrIncidentList) {
        this.arrIncidentList.addAll(arrIncidentList);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        arrIncidentList.remove(position);
        notifyDataSetChanged();
    }

    public BalanceData get(int position) {
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
            ItemHomeLayoutBinding itemBinding = ItemHomeLayoutBinding.inflate(layoutInflater, parent, false);
            viewHolder = new ViewHolder(itemBinding);
        }
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_anim_out);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_anim_in);


        return viewHolder;

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            final BalanceData item = arrIncidentList.get(position);
            viewHolder.binding.ivMenuItem.setImageResource(item.getMenuResId());
            viewHolder.binding.tvMenuItem.setText(item.getMenuName());
            //viewHolder.binding.cardFrontImage.setImageResource(item.getMenuNameColor());
            //viewHolder.binding.cardBackImage.setImageResource(item.getMenuNameColor());
            TypedValue typedValue =  new TypedValue();
            context.getTheme().resolveAttribute(R.attr.colorMain,typedValue,true);

            viewHolder.binding.imgMenuCircle.setImageResource(typedValue.resourceId);
  //          int distance = 8000;
    //        float scale = context.getResources().getDisplayMetrics().density * distance;
            //viewHolder.binding.cardFront.setCameraDistance(scale);
            //viewHolder.binding.cardBack.setCameraDistance(scale);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

              /*      mSetRightOut.setTarget(viewHolder.binding.cardFront);
                    mSetLeftIn.setTarget(viewHolder.binding.cardBack);
                    mSetLeftIn.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mSetLeftIn.removeAllListeners();
                            mSetRightOut.setTarget(viewHolder.binding.cardBack);
                            mSetLeftIn.setTarget(viewHolder.binding.cardFront);
                            mSetRightOut.addListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    mSetRightOut.removeAllListeners();
              */                      onRecycleItemClickListener.onClicked(get(viewHolder.getAdapterPosition()), view, viewHolder.getAdapterPosition(), OnRecyclerViewItemClickListener.ViewType.View);
                /*                }
                            });

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mSetLeftIn.start();
                                    mSetRightOut.start();
                                }
                            },2);
*/
  /*                      }
                    });
                    mSetRightOut.start();
                    mSetLeftIn.start();
*/
                }
            });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemHomeLayoutBinding binding;

        public ViewHolder(ItemHomeLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

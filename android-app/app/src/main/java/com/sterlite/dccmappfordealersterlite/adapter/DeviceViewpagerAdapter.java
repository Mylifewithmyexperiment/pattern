package com.sterlite.dccmappfordealersterlite.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.databinding.ItemDeviceLayoutBinding;
import com.sterlite.dccmappfordealersterlite.model.Device;

public class DeviceViewpagerAdapter extends PagerAdapter {
    private static final String TAG = "[HomePageFirstSliderAdapter] : ";
    private LayoutInflater inflater;
    private Context context;
    public static final int VIEW_TYPE_OTHER = 111;
    private List<Device> arrPlans = new ArrayList<>();
    OnViewpagerItemListener onViewpagerItemListener;

    public DeviceViewpagerAdapter(Context context, OnViewpagerItemListener onViewpagerItemListener) {
        this.context = context;
        this.onViewpagerItemListener = onViewpagerItemListener;
        inflater = LayoutInflater.from(context);
    }

    public void updateList(ArrayList<Device> arrPlans) {
        this.arrPlans.addAll(arrPlans);
        notifyDataSetChanged();
    }

    public Device get(int position) {
        return arrPlans.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return arrPlans.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup view, final int position) {
        ItemDeviceLayoutBinding itemBinding = ItemDeviceLayoutBinding.inflate(inflater, view, false);
        Device plan = arrPlans.get(position);

        itemBinding.tvDeviceName.setText(plan.getName());
        if (plan.getInStock()) {
            AppUtils.setVisibility(itemBinding.relPrice, View.GONE);
            if (!AppUtils.isEmpty(plan.getPriceAfterDisCount())) {
                AppUtils.setVisibility(itemBinding.relPrice, View.VISIBLE);
                String str = context.getString(R.string.txt_price) + AppUtils.getPriceWithSymbol(context, plan.getPriceAfterDisCount());
                itemBinding.tvAfterDiscountPrice.setText(str);
            }

            AppUtils.setVisibility(itemBinding.relDiscount, View.GONE);
            if (!AppUtils.isEmpty(plan.getPrice()) && !plan.getPriceAfterDisCount().equals(plan.getPrice())) {
                itemBinding.tvPrice.setText(AppUtils.getPriceWithSymbol(context, plan.getPrice()));
                itemBinding.tvSaved.setText(context.getString(R.string.txt_you_save, AppUtils.getPriceWithSymbol(context,plan.getPriceSaved())));
                itemBinding.tvPrice.setPaintFlags(itemBinding.tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                AppUtils.setVisibility(itemBinding.relDiscount, View.VISIBLE);
            }
            itemBinding.tvStock.setText(context.getString(R.string.lbl_in_stock));
        } else {
            AppUtils.setVisibility(itemBinding.relDiscount, View.GONE);
            AppUtils.setVisibility(itemBinding.relPrice, View.GONE);
            itemBinding.tvStock.setText(context.getString(R.string.lbl_out_stock));
        }

        view.addView(itemBinding.getRoot(), 0);
        return itemBinding.getRoot();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }


    public interface OnViewpagerItemListener {
        void onItemClicked(int position, int viewType);
    }
}

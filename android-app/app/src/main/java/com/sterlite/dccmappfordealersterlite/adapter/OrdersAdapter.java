package com.sterlite.dccmappfordealersterlite.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.databinding.ItemOrderListBinding;
import com.sterlite.dccmappfordealersterlite.model.MyOrdersListing.Order;

public class OrdersAdapter extends BaseMainAdpter {

    ArrayList<Order> orderArrayList = new ArrayList<>();
    boolean isFromActivation;

    public OrdersAdapter(Context context, OnRecyclerViewItemClickListener<Order> orderOnRecyclerViewItemClickListener, boolean isFromActivation) {
        this.isFromActivation = isFromActivation;
        init(context, orderArrayList, orderOnRecyclerViewItemClickListener);
    }

    public void addItems(ArrayList<Order> arrIncidentList) {
        this.orderArrayList.addAll(arrIncidentList);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        orderArrayList.remove(position);
        notifyDataSetChanged();
    }

    public Order get(int position) {
        return orderArrayList.get(position);
    }

    public ArrayList getAll() {
        return orderArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        if (viewType == TYPE_ITEM) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ItemOrderListBinding itemBinding = ItemOrderListBinding.inflate(layoutInflater, parent, false);
            viewHolder = new ViewHolder(itemBinding);
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            final Order order = orderArrayList.get(position);
            viewHolder.binding.tvOrderNo.setText(order.getOrderId());
            viewHolder.binding.tvOrderDate.setText(getFormatedDate(order.getDate()));
            viewHolder.binding.tvName.setText(order.getName());
            viewHolder.binding.tvStatus.setText(order.getOrderStatus());

          //  viewHolder.binding.tvPrice.setText(order.getTotal());
            viewHolder.binding.tvPrice.setText(AppUtils.getPriceWithSymbol(String.valueOf(order.getObjTotal().getValue())));

           /* viewHolder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRecycleItemClickListener.onClicked(get(viewHolder.getAdapterPosition()), view, viewHolder.getAdapterPosition(), OnRecyclerViewItemClickListener.ViewType.View);
                }
            });*/
           if (order.getStatus().equalsIgnoreCase("Notify CRM"))
           {
               viewHolder.binding.btnActive.setVisibility(View.INVISIBLE);
           }
            else{
               viewHolder.binding.btnActive.setVisibility(View.VISIBLE);

               viewHolder.binding.btnActive.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       onRecycleItemClickListener.onClicked(get(viewHolder.getAdapterPosition()),v,viewHolder.getAdapterPosition(),OnRecyclerViewItemClickListener.ViewType.Active);
                   }
               });
               if(order.getStatus().equalsIgnoreCase(Constants.DELIVERED)){
                   viewHolder.binding.btnActive.setEnabled(true);

               }
               else {
                   viewHolder.binding.btnActive.setEnabled(false);
                   viewHolder.binding.btnActive.setBackgroundColor(Color.GRAY);

               }

           }
            viewHolder.binding.btnTrack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRecycleItemClickListener.onClicked(get(viewHolder.getAdapterPosition()),v,viewHolder.getAdapterPosition(),OnRecyclerViewItemClickListener.ViewType.Track);
                }
            });



           /* if (isFromActivation && order.isOpen()) {

                viewHolder.binding.btnActive.setVisibility(View.VISIBLE);

            } else {
                viewHolder.binding.btnActive.setVisibility(View.INVISIBLE);
            }

            if(order.isTrackable()){
                viewHolder.binding.btnTrack.setVisibility(View.VISIBLE);
            }else{
                viewHolder.binding.btnTrack.setVisibility(View.INVISIBLE);
            }*/
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemOrderListBinding binding;

        public ViewHolder(ItemOrderListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    private String getFormatedDate(String date) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
        Date result;
        try {
            result = df.parse(date);
//            System.out.println("date:"+result); //prints date in current locale
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            Log.e("ActivationActivity","getDate" +sdf.format(result));
            return sdf.format(result);
        }catch (Exception e){
            Log.e("ActivationActivity","getDate" + e);
        }
        return "";
    }

}

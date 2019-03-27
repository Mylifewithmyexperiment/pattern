package com.sterlite.dccmappfordealersterlite.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;

import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.DateTimeUtils;
import com.sterlite.dccmappfordealersterlite.databinding.ItemPaymentHistoryBinding;
import com.sterlite.dccmappfordealersterlite.model.bssrest.PaymentHistory.PaymentDetailData;


public class PaymentHistoryAdapter extends BaseMainAdpter {

    ArrayList<PaymentDetailData> paymentDetailData = new ArrayList<>();

    public PaymentHistoryAdapter(Context context) {
        init(context, paymentDetailData,null);
    }

    public void addItems(ArrayList<PaymentDetailData> arrIncidentList) {
        this.paymentDetailData.addAll(arrIncidentList);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        paymentDetailData.remove(position);
        notifyDataSetChanged();
    }

    public PaymentDetailData get(int position) {
        return paymentDetailData.get(position);
    }

    public ArrayList getAll() {
        return paymentDetailData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        if (viewType == TYPE_ITEM) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ItemPaymentHistoryBinding itemBinding = ItemPaymentHistoryBinding.inflate(layoutInflater, parent, false);
            viewHolder = new ViewHolder(itemBinding);
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            final PaymentDetailData data = paymentDetailData.get(position);
            viewHolder.binding.tvPaymentNumber.setText(data.getExternalSystemPaymentNumber());
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(data.getPaymentDate());
            viewHolder.binding.tvPaymentDate.setText(DateTimeUtils.getDateStringByPattern(calendar.getTime(),"dd-MM-yyyy"));
            viewHolder.binding.tvPaymentMode.setText(data.getOnlineMethod().getPaymentMode());
            viewHolder.binding.tvStatus.setText(data.getPaymentStatus());
            viewHolder.binding.tvAmount.setText(AppUtils.getPriceWithSymbol(String.valueOf(data.getTotalPyamentAmount())));
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemPaymentHistoryBinding binding;

        public ViewHolder(ItemPaymentHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

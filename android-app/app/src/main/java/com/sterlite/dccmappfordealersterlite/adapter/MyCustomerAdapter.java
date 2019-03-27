package com.sterlite.dccmappfordealersterlite.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.activity.Activation.ActiviationPresenter;
import com.sterlite.dccmappfordealersterlite.activity.UserDetail.UserDetailActivity;
import com.sterlite.dccmappfordealersterlite.activity.dashboard.DashBoardActivity;
import com.sterlite.dccmappfordealersterlite.activity.loginnew.LoginPresenter;
import com.sterlite.dccmappfordealersterlite.activity.loginnew.NewLoginActivity;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.data.network.ApiHelper;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.databinding.ItemCustomersBinding;
import com.sterlite.dccmappfordealersterlite.model.CustomerWsDTO;
import com.sterlite.dccmappfordealersterlite.model.dto.validatecustomer.ValidateCustomerWsDTO;
import com.sterlite.dccmappfordealersterlite.service.FCM.MyFirebaseInstanceIdService;

import java.util.ArrayList;

/**
 * Created by elitecore on 24/12/18.
 */

public class MyCustomerAdapter extends BaseMainAdpter {

    private ArrayList<CustomerWsDTO> customerListWsDTO = new ArrayList<>();

    public MyCustomerAdapter(Context context) {
        init(context, customerListWsDTO,null);
    }

    public MyCustomerAdapter(Context context, OnRecyclerViewItemClickListener<CustomerWsDTO> onRecycleItemClickListener) {
        init(context, customerListWsDTO, onRecycleItemClickListener);
    }

    public void addItems(ArrayList<CustomerWsDTO> customerListWsDTO) {
        this.customerListWsDTO.clear();
        this.customerListWsDTO.addAll(customerListWsDTO);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        customerListWsDTO.remove(position);
        notifyDataSetChanged();
    }

    public CustomerWsDTO get(int position) {
        return customerListWsDTO.get(position);
    }

    public ArrayList getAll() {
        return customerListWsDTO;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        if (viewType == TYPE_ITEM) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

            ItemCustomersBinding itemBinding = ItemCustomersBinding.inflate(layoutInflater, parent, false);
            viewHolder = new MyCustomerAdapter.ViewHolder(itemBinding);
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof MyCustomerAdapter.ViewHolder) {
            final MyCustomerAdapter.ViewHolder viewHolder = (MyCustomerAdapter.ViewHolder) holder;
            final CustomerWsDTO data = customerListWsDTO.get(position);

            Log.e("CustomerWsDTO",data+" ");
            viewHolder.binding.tvName.setText(data.getFirstName());
            viewHolder.binding.tvEmail.setText(data.getEmail());
            viewHolder.binding.tvMobNumber.setText(data.getMobileNumber());
            viewHolder.binding.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /* DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.EMAIL_ID, data.getEmail().trim());
                    DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.MOBILE_NO, data.getMobileNumber().trim());

*/
                    DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.CUSTOMER_UID, data.getCustomerId().toLowerCase());

                    ActiviationPresenter presenter= new ActiviationPresenter();
                    presenter.findSubscriptionByEmailId(new ApiHelper.OnApiCallback() {
                        @Override
                        public void onSuccess(Object baseResponse) {
                            DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.P_CUSTOMER_EMAIL, data.getEmail().trim());
                            DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.P_CUSTOMER_MOBILE, data.getMobileNumber().trim());
                            DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.P_CUSTOMER_NAME, data.getFirstName().trim());

                            DCCMDealerSterlite.getDataManager().setBoolean(AppPreferencesHelper.PARTNER_CUSTOMER,true);
                            //DCCMDealerSterlite.getDataManager().setBoolean(AppPreferencesHelper.PARTNER,false);

                            context.startActivity(new Intent(context, DashBoardActivity.class));

                        }

                        @Override
                        public void onFailed(int code, String message) {
                            DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.CUSTOMER_UID,DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.PARTNER_UID,null) );

                            Toast.makeText(context, R.string.string_no_subs_found, Toast.LENGTH_LONG).show();


                        }
                    });

                }
            });

        }

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemCustomersBinding binding;

        public ViewHolder(ItemCustomersBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }





}






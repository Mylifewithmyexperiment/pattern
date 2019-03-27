package com.sterlite.dccmappfordealersterlite.activity.UnsubscribeAddon;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Map;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.activity.OrederReceiveSuccess.OrderReceiveSuccess;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityUnsubscribeAddonBinding;
import com.sterlite.dccmappfordealersterlite.model.bssrest.ProductOfferDetail;
import com.sterlite.dccmappfordealersterlite.model.bssrest.ServiceInstanceDetail;

public class UnsubscribeAddonActivity extends BaseActivity implements UnsubscribeAddonContract.View , View.OnClickListener{
    private UnsubscribeAddonContract.Presenter<UnsubscribeAddonContract.View> presenter;
    private ActivityUnsubscribeAddonBinding binding;
    private String title;
    public static String ARG_PLAN_TITLE = "argPlanTitle";
    public static String serviceInstanceNumber;
    public static String externalReferenceId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        setAppTheme();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_unsubscribe_addon);
        setUpNavigationView(binding.drawerLayout, binding.layoutNavMenu);
        setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.lbl_unsubscribe), true);
        setSpinnerData();
        setClickListeners();
        presenter = new UnsubscribeAddonPresenter<>();
        presenter.onAttach(this);

        /*binding.btnUnsubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });*/
    }

    private void setClickListeners() {
        binding.btnUnsubscribe.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUnsubscribe:
                if (serviceInstanceNumber!=null && externalReferenceId!=null){
                    showProgressBar();
                    presenter.unsubscribeAddon(serviceInstanceNumber,externalReferenceId);
                }

                break;
        }
    }

    private void setSpinnerData() {



        final Map<String,String> mapID= DCCMDealerSterlite.getDataManager().loadMap(AppPreferencesHelper.SERVICE_INSTANCE_LIST);
        Log.e("SEVICE INSTANCE LIST",mapID+" ");

        final ArrayList<String> invList=new ArrayList<>(mapID.keySet());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, invList);
        binding.spnNumber.setAdapter(dataAdapter);

        binding.spnNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int index = parent.getSelectedItemPosition();
                serviceInstanceNumber=mapID.get(invList.get(index));
                Log.e("SelectedSI",serviceInstanceNumber);
                showProgressBar();
                presenter.init(serviceInstanceNumber);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });
        //setClickListerners();
    }


    @Override
    public void loadDataToView(final ServiceInstanceDetail responseModel) {
        hideProgressBar();
        if (responseModel!=null){
            if (responseModel.getAddonProducts().size()>0) {
                ArrayAdapter<ProductOfferDetail> dataAdapter = new ArrayAdapter<ProductOfferDetail>(this, android.R.layout.simple_dropdown_item_1line, responseModel.getAddonProducts());
                binding.spnAddon.setAdapter(dataAdapter);
                binding.spnAddon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        int index = parent.getSelectedItemPosition();
                        ProductOfferDetail productOfferDetail=responseModel.getAddonProducts().get(index);

                        externalReferenceId=String.valueOf(productOfferDetail.getProductSubscriptionId());
                        //showProgressBar();


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                        // sometimes you need nothing here
                    }
                });
            }
            else {
                AppUtils.showToast(this,getString(R.string.lbl_data_not_found));
                //finish();

            }
        }
        else {
            AppUtils.showToast(this,getString(R.string.lbl_data_not_found));
        }

    }

    @Override
    public void unsubscribeSuccess(String baseResponse) {
        hideProgressBar();
        Intent intent = new Intent(UnsubscribeAddonActivity.this, OrderReceiveSuccess.class);
        intent.putExtra(OrderReceiveSuccess.ARG_SUCCESS_MESSAGE, baseResponse);
        startActivity(intent);
    }
}

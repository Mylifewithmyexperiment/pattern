package com.sterlite.dccmappfordealersterlite.activity.SearchCustomer;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.activity.invoice.InvoiceActivity;
import com.sterlite.dccmappfordealersterlite.adapter.InvoiceInformationAdapter;
import com.sterlite.dccmappfordealersterlite.adapter.MyCustomerAdapter;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.databinding.ActivitySearchCustomerBinding;
import com.sterlite.dccmappfordealersterlite.model.CustomerListWsDTO;
import com.sterlite.dccmappfordealersterlite.model.CustomerWsDTO;
import com.sterlite.dccmappfordealersterlite.model.bssrest.BillingDetailData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchCustomerActivity extends BaseActivity implements SearchCustomerContract.View {

    ActivitySearchCustomerBinding binding;
    SearchCustomerContract.Presenter<SearchCustomerContract.View> presenter;
    MyCustomerAdapter adapter;
    List<String> searchId= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        setAppTheme();
        init();
        setClickListners();

    }

    private void init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_customer);
        setUpNavigationView(binding.drawerLayout, binding.layoutNavMenu);

        setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.nav_menu_search_customer), true);
        setUpView(true);
        presenter = new SearchCustomerPresenter<>();
        presenter.onAttach(this);

    /*    searchId.add("Name");
        searchId.add("Email");
        searchId.add("Mobile");*/
/*        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, searchId);
        binding.searchSpinner.setAdapter(dataAdapter);*/

    }

    private void setClickListners() {
        binding.tvSearch.setClickable(true);
        binding.tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.searchCustomer(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.PARTNER_UID,null));

            }
        });

    }


    @Override
    public void onSuccessSearchCustomer(CustomerListWsDTO customerListWsDTO) {

    }

    @Override
    public void onFailSearchCustomer() {

    }

    @Override
    public void setUpView(boolean isReset) {
        if (isReset) {
            adapter = null;
        }
        AppUtils.setVisibility(binding.tvNotFound, View.GONE);

        if (adapter == null) {
            adapter = new MyCustomerAdapter(this);
            binding.rvCustomers.setAdapter(adapter);
        } else {
            binding.rvCustomers.setAdapter(adapter);
        }

    }

    @Override
    public void loadDataToView(ArrayList<CustomerWsDTO> list) {
        adapter.getAll().clear();
        //  List<BillingDetailData> billingDetailDataList= Arrays.asList(list);
        //ArrayList<BillingDetailData> billDataList=(ArrayList<BillingDetailData>) billingDetailDataList;

        //  ArrayList<BillingDetailData> billingDetailDataList = new ArrayList<>(Arrays.asList(list));
        Log.e("List",list+"");

        ArrayList<CustomerWsDTO> filteredList= getFilteredList(list);
        // ArrayList<BillingDetailData> filteredList = getFilteredList(list);
        if (filteredList!=null) {
            if (filteredList.size() >= 1) {
                Log.e("FinalList", filteredList + "");
                adapter.addItems(filteredList);
                adapter.notifyDataSetChanged();
            } else
                setNotRecordsFoundView(true);
        }
        else
            setNotRecordsFoundView(true);
    }

    private ArrayList<CustomerWsDTO> getFilteredList(ArrayList<CustomerWsDTO> list) {

        boolean isName = binding.rbName.isChecked();
        boolean isEmail = binding.rbEmail.isChecked();
        boolean isMobile = binding.rbMobile.isChecked();

        Log.e("Name::",isName+" ");
        Log.e("isEmail::",isEmail+" ");
        Log.e("isMobile::",isMobile+" ");

        ArrayList<CustomerWsDTO> filteredlist=new ArrayList<>();
        String text=binding.edtSearchText.getText().toString().trim();
        if (isName){

            for (CustomerWsDTO customerWsDTO:list){
                if (customerWsDTO.getFirstName().toLowerCase().startsWith(text.toLowerCase()))
                {
                    filteredlist.add(customerWsDTO);
                }
            }
        }
        else if(isEmail)
        {
            for (CustomerWsDTO customerWsDTO:list){
                if (customerWsDTO.getEmail().toLowerCase().startsWith(text.toLowerCase()))
                {
                    filteredlist.add(customerWsDTO);
                }
            }
        }

        else if(isMobile)
        {
            for (CustomerWsDTO customerWsDTO:list){
                if (customerWsDTO.getMobileNumber().toLowerCase().startsWith(text.toLowerCase()))
                {
                    filteredlist.add(customerWsDTO);
                }
            }
        }
        else{
            filteredlist=list;
        }
        return filteredlist;
    }


    @Override
    public void setNotRecordsFoundView(boolean isActive) {
        if (isActive) {
            binding.tvNotFound.setVisibility(View.VISIBLE);
        } else {
            binding.tvNotFound.setVisibility(View.GONE);
        }
    }

    @Override
    public void updateFooterFalse() {
        if (adapter != null) {
            adapter.isAddFooter = false;
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void hideProgressBar(boolean isFullScreen) {
        if (isFullScreen)
            super.hideProgressBar();
        else {
            if (adapter != null) {
                adapter.updateBottomProgress(2);
                adapter.notifyDataSetChanged();
            } else {
                super.hideProgressBar();
            }
        }
    }

    @Override
    public void showProgressBar(boolean isFullScreen) {
        if (isFullScreen)
            super.showProgressBar();
        else {
            if (adapter != null) {
                adapter.isAddFooter = true;
                adapter.updateBottomProgress(0);
                adapter.notifyDataSetChanged();
            } else {
                super.showProgressBar();
            }
        }
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_right_menu:
                binding.drawerLayout.openDrawer(GravityCompat.END);
                return true;

            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_user_detail_menu, menu);
        return true;
    }*/
}

package com.sterlite.dccmappfordealersterlite.activity.inventory;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.Utils.DummyLists;
import com.sterlite.dccmappfordealersterlite.activity.Kyc.KycActivity;
import com.sterlite.dccmappfordealersterlite.activity.orderdetailreview.OrderDetailsReviewActivity;
import com.sterlite.dccmappfordealersterlite.activity.pincode.PincodeActivity;
import com.sterlite.dccmappfordealersterlite.adapter.InventoryAdapter;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityInventoryBinding;
import com.sterlite.dccmappfordealersterlite.model.Inventory;
import com.sterlite.dccmappfordealersterlite.model.InventoryFilter.InventoryFilter;
import com.sterlite.dccmappfordealersterlite.model.MakeMyPlanModel.MakeMyPlanItems;
import com.sterlite.dccmappfordealersterlite.model.Plan;
import com.sterlite.dccmappfordealersterlite.model.dto.BssResponse.BSSDataResponse;


public class InventoryActivity extends BaseActivity implements InventoryContract.View, TabLayout.OnTabSelectedListener {

    public static final String INV_PREMIUM = Constants.INV_PREMIUM;
    private InventoryAdapter inventoryAdapter;
    private ActivityInventoryBinding binding;
    private InventoryContract.Presenter<InventoryContract.View> presenter;
    public static String ARG_FROM = "argInventoryActivityFrom";
    private String from = "";
    private InventoryFilter inventoryFilter;
    ArrayAdapter<String> spinnerAdapter;
    ArrayList<Inventory> inventories;
    CopyOnWriteArrayList<Inventory> invsFiltered;
    Plan plan;
    MakeMyPlanItems makeMyPlanItems;

    //    Inventory inventory;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        setAppTheme();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inventory);
        setUpNavigationView();
        setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.title_select_number), true);

        from = getIntent().getStringExtra(ARG_FROM);
        inventories = new ArrayList<>();
        invsFiltered = new CopyOnWriteArrayList<>();
        presenter = new InventoryPresenter<>();
        presenter.onAttach(this);

        presenter.init();
        binding.swRefreshInventory.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.init();
            }
        });
        binding.rvInventory.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {


            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Log.d("mmpl", "ideal");

                    if (!binding.floatingActionButton.isShown()) {
                        binding.floatingActionButton.setVisibility(View.VISIBLE);
                    }
                } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    if ( binding.floatingActionButton.isShown())
                        binding.floatingActionButton.setVisibility(View.INVISIBLE);
                    Log.d("mmpl", "dragging");
                }
            }
        });

        Log.e("plan:inventory", DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID, null)+"H");

    }

    private void setUpNavigationView() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        binding.layoutNavMenu.btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comparator<Inventory> defaultComparator = new Comparator<Inventory>() {
                    @Override
                    public int compare(Inventory o1, Inventory o2) {
                        try {
                            return Double.valueOf(o1.getPrice()).compareTo(Double.valueOf(o2.getPrice()));
                        }catch (Exception e){
                            Log.e("InventoryActivity"," "+e);
                            return 0;
                        }
                    }
                };
                Comparator<Inventory> reversComparator = new Comparator<Inventory>() {
                    @Override
                    public int compare(Inventory o1, Inventory o2) {
                        try {
                            return Double.valueOf(o2.getPrice()).compareTo(Double.valueOf(o1.getPrice()));
                        }catch (Exception e){
                            Log.e("InventoryActivity"," "+e);
                            return 0;
                        }
                    }
                };

                if (inventoryFilter != null) {
                    invsFiltered = new CopyOnWriteArrayList<Inventory>(inventories) ;
                    if(binding.layoutNavMenu.rbAll.isChecked()){
                        // aal invsFiltered
                    }else if(binding.layoutNavMenu.rbFree.isChecked()){
                        for (Inventory inv :invsFiltered) {
                            if(!inv.getNumberType().equalsIgnoreCase("Default")) {
                                invsFiltered.remove(inv);
                            }
                        }
                    }else{
                        for (Inventory inv :invsFiltered) {
                            if(!inv.getNumberType().equalsIgnoreCase("Gold")) {
                                invsFiltered.remove(inv);
                            }
                        }
                        ArrayList<Inventory> tmp = new ArrayList<>(invsFiltered);
                        if(binding.layoutNavMenu.rbHTL.isChecked()){

                            Collections.sort(tmp, reversComparator);
                        }else{
                            Collections.sort(tmp, defaultComparator);
                        }
                        invsFiltered.clear();
                        invsFiltered.addAll(tmp);
                    }
                    if (binding.layoutNavMenu.edtLuckyNumber.getText() != null && !TextUtils.isEmpty(binding.layoutNavMenu.edtLuckyNumber.getText().toString().trim())) {
                        for (Inventory inv :inventories) {
                            Log.i("InvLuckyNum",inv.getNumber() + "  " + inv.getLuckyNumber());
                            if(!inv.getLuckyNumber().equalsIgnoreCase(binding.layoutNavMenu.edtLuckyNumber.getText().toString().trim())) {
                                invsFiltered.remove(inv);
                            }
                        }
                        inventoryFilter.setLuckyNumber(binding.layoutNavMenu.edtLuckyNumber.getText().toString().trim());
                    }
                    int selectedPos = binding.layoutNavMenu.spnNumSeries.getSelectedItemPosition();
                    if (selectedPos != -1 && !binding.layoutNavMenu.spnNumSeries.getSelectedItem().toString().equalsIgnoreCase("none")) {
                        Log.i("InvLuckyNum",binding.layoutNavMenu.spnNumSeries.getSelectedItem().toString());
                        for (Inventory inv :invsFiltered) {
                            Log.i("start with",inv.getNumber() );
                            if(!inv.getNumber().startsWith(binding.layoutNavMenu.spnNumSeries.getSelectedItem().toString())) {
                                invsFiltered.remove(inv);
                            }
                        }
                        inventoryFilter.setSelectedNumSeries(inventoryFilter.getNumberSeries().get(selectedPos));
                    }
                }
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                loadDataToView(new ArrayList<Inventory>(invsFiltered),inventoryFilter);
                Log.e("InventoryActivity", getString(R.string.msg_apply_clicked));
            }
        });
        binding.layoutNavMenu.btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invsFiltered.clear();
                invsFiltered = new CopyOnWriteArrayList<Inventory>(inventories) ;
                loadDataToView(inventories,DummyLists.getInventoryFilters());
                binding.layoutNavMenu.edtLuckyNumber.setText("");
                binding.layoutNavMenu.spnNumSeries.setSelection(-1);
                binding.layoutNavMenu.rbAll.setChecked(true);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                Log.e("InventoryActivity", getString(R.string.msg_reset_clicked));
            }
        });
       /* binding.layoutNavMenu.spnNumSeries.set(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (inventoryFilter != null) {
                    inventoryFilter.setSelectedNumSeries(inventoryFilter.getNumberSeries().get(position));
                }
            }
        });*/

       // check box all
        binding.layoutNavMenu.rbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (inventoryFilter != null) {
                    inventoryFilter.setAllChecked(isChecked);
                    if (isChecked) {
                        binding.layoutNavMenu.tvLvl2Title.setVisibility(View.GONE);
                        binding.layoutNavMenu.rglvl2.setVisibility(View.GONE);
                    }
                }
            }
        });
        binding.layoutNavMenu.rbFree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (inventoryFilter != null) {
//                    inventoryFilter.setAllChecked(isChecked);
                    if (isChecked) {
                        binding.layoutNavMenu.tvLvl2Title.setVisibility(View.GONE);
                        binding.layoutNavMenu.rglvl2.setVisibility(View.GONE);
                    }
                }
            }
        });binding.layoutNavMenu.rbPremium.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (inventoryFilter != null) {
//                    inventoryFilter.setAllChecked(!isChecked);
                    if (isChecked) {
                        binding.layoutNavMenu.tvLvl2Title.setVisibility(View.VISIBLE);
                        binding.layoutNavMenu.rbHTL.setChecked(true);
                        binding.layoutNavMenu.rglvl2.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        binding.layoutNavMenu.rbHTL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (inventoryFilter != null) {
                    inventoryFilter.setSortByHTL(isChecked);
                }
            }
        });
        binding.layoutNavMenu.btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inventoryFilter != null) {
                    binding.layoutNavMenu.spnNumSeries.setSelection(-1);
                    binding.layoutNavMenu.rbFree.setChecked(true);

                    if (binding.layoutNavMenu.edtLuckyNumber.getText() != null && !TextUtils.isEmpty(binding.layoutNavMenu.edtLuckyNumber.getText().toString().trim())) {
                        inventoryFilter.setLuckyNumber(binding.layoutNavMenu.edtLuckyNumber.getText().toString().trim());
                    }

                }
                AppUtils.hideKeyboard(InventoryActivity.this);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                AppUtils.showToast(InventoryActivity.this, getString(R.string.msg_go_clicked));
            }
        });
        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        binding.layoutNavMenu.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public void saveInventory(ArrayList<Inventory> inventories){
        this.inventories = inventories;
    }

    @Override
    public void setUpView(boolean isReset) {
        if (isReset) {
            inventoryAdapter = null;
            inventoryFilter = null;
            binding.layoutNavMenu.edtLuckyNumber.setText("");
            binding.layoutNavMenu.spnNumSeries.setSelection(-1);
            binding.layoutNavMenu.rbAll.setChecked(true);
        }
        binding.rvInventory.setLayoutManager(new GridLayoutManager(this, 2));
        if (inventoryAdapter == null) {
            inventoryAdapter = new InventoryAdapter(this, new OnRecyclerViewItemClickListener<Inventory>() {
                @Override
                public void onClicked(Inventory inventory, View view, int position, ViewType viewType) {
                    Log.e("rvInventory" , "on click "+inventory);

                    if(inventory!=null) {
                        if (!AppUtils.isEmpty(from) && from.equals(OrderDetailsReviewActivity.class.getName())) {
                            setData(inventory);
                            Intent intent = new Intent();
                            intent.putExtra("inventory", inventory);
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                        } else {
                            setData(inventory);
                            Intent newIntent = new Intent(InventoryActivity.this, KycActivity.class);
                            startActivity(newIntent);
                        }
                    }else{
                        Log.e("rvInventory" , "on click ELSE");
                    }
                }

                @Override
                public void onLastItemReached() {

                }
            });
            binding.rvInventory.setAdapter(inventoryAdapter);
        } else {
            binding.rvInventory.setAdapter(inventoryAdapter);
        }
        setUpTab();
    }

    private void setData(Inventory inventory) {
//        DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.INVENTORY_NO,inventory.getNumber());
//        DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.INVENTORY_TYPE,inventory.getNumberType());
//        DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.INVENTORY_PRICE,inventory.getPrice());
//        Log.e()
        DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.INVENTORY_DATA,AppUtils.getStringFromObj(inventory));

    }

    private void setUpTab() {
        ArrayList<String> arrList;
        if (!AppUtils.isEmpty(from) && from.equals("device")) {
            arrList = DummyLists.getFilterListForDevice();
        } else {
            arrList = DummyLists.getFilterList();
            binding.tabs.setVisibility(View.GONE);
        }
        int currentTab = binding.tabs.getSelectedTabPosition();
        if (currentTab < 0)
            currentTab = 0;
        binding.tabs.removeAllTabs();
        binding.tabs.removeOnTabSelectedListener(this);
        for (int i = 0; i < arrList.size(); i++) {
            binding.tabs.addTab(binding.tabs.newTab().setText(arrList.get(i)), i == currentTab);
        }
        binding.tabs.addOnTabSelectedListener(this);
    }

    @Override
    public void loadDataToView(ArrayList<Inventory> list, InventoryFilter inventoryFilter) {
        this.inventoryFilter = inventoryFilter;
        if(inventories==null || inventories.isEmpty()){
            binding.tvNotFound.setVisibility(View.VISIBLE);
            binding.tvNotFound.setText(R.string.msg_no_inv_found);
        }else if(list==null || list.isEmpty()){
            binding.tvNotFound.setVisibility(View.VISIBLE);
            binding.tvNotFound.setText(R.string.msg_no_inv_found_for_match);
        }else {
            binding.tvNotFound.setVisibility(View.GONE);
        }
        binding.swRefreshInventory.setRefreshing(false);
        if(binding.layoutNavMenu.edtLuckyNumber.getText() == null)
            binding.layoutNavMenu.edtLuckyNumber.setText("");

        invsFiltered = new CopyOnWriteArrayList<Inventory>(list) ;
        if(binding.layoutNavMenu.spnNumSeries.getSelectedItemPosition() == -1) {
            spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, inventoryFilter.getNumberSeries());
            binding.layoutNavMenu.spnNumSeries.setAdapter(spinnerAdapter);
            binding.layoutNavMenu.spnNumSeries.setSelection(1);
            for (Inventory inv :invsFiltered) {
                Log.i("start with",inv.getNumber());
                if(!inv.getNumber().startsWith(binding.layoutNavMenu.spnNumSeries.getSelectedItem().toString())) {
                    invsFiltered.remove(inv);
                }
            }
            if(invsFiltered==null || invsFiltered.isEmpty()) {
                binding.layoutNavMenu.spnNumSeries.setSelection(0);
            }
        }
        if(invsFiltered!=null && !invsFiltered.isEmpty()) {
            inventoryAdapter.addItems(new ArrayList<>(invsFiltered));
        }else {
            inventoryAdapter.addItems(list);
        }

        /*  binding.layoutNavMenu.rglvl1.removeAllViews();
      ArrayList<RadioButton> lvl1Filters = new ArrayList<>();
        if (inventoryFilter.getLvl1Filters() != null) {
            for (final Lvl1Filter filter : inventoryFilter.getLvl1Filters()) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                radioButton.setText(filter.getText());
                radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        binding.layoutNavMenu.tvLvl2Title.setText("");
                        binding.layoutNavMenu.rglvl2.removeAllViews();
                        if(filter.getLvl2Filter()!=null &&filter.getLvl2Filter().getRadioList()!=null &&filter.getLvl2Filter().getRadioList().size()>0){
                            binding.layoutNavMenu.tvLvl2Title.setText(filter.getLvl2Filter().getTitle());
                            boolean isFirstChecked=false;
                            for(RadioPOJO radioPOJO:filter.getLvl2Filter().getRadioList()){
                                RadioButton radioButton = new RadioButton(InventoryActivity.this);
                                radioButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                                radioButton.setText(radioPOJO.getText());
                                binding.layoutNavMenu.rglvl2.addView();
                                if(!isFirstChecked){
                                    radioButton
                                }
                            }
                        }
                    }
                });
            }
        }
*/
    }

    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        presenter.init();
        Log.d(TAG, "onTabSelected: " + tab.getText());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void addInventoryToCartSuccess(BSSDataResponse bssDataResponse) {
        if (bssDataResponse.getResponseCode().equalsIgnoreCase("0")){
//            DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.INVENTORY_NO,inventory.getNumber());
            Intent newIntent = new Intent(InventoryActivity.this, PincodeActivity.class);
            startActivity(newIntent);
        }else{
            AppUtils.showToast(InventoryActivity.this, "Something went Wrong");
            if(Constants.IS_DUMMY_MODE) {
                Intent newIntent = new Intent(InventoryActivity.this, PincodeActivity.class);
                startActivity(newIntent);
            }
        }
    }
}

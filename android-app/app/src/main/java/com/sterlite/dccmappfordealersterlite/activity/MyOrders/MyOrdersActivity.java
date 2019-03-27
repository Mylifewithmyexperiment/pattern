package com.sterlite.dccmappfordealersterlite.activity.MyOrders;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.view.GravityCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.activity.OrederReceiveSuccess.OrderReceiveSuccess;
import com.sterlite.dccmappfordealersterlite.activity.orderdetailreview.OrderDetailsReviewActivity;
import com.sterlite.dccmappfordealersterlite.activity.trackingDetail.TrackingDetailActivity;
import com.sterlite.dccmappfordealersterlite.adapter.OrdersAdapter;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityMyOrdersBinding;
import com.sterlite.dccmappfordealersterlite.model.MyOrdersListing.Order;

public class MyOrdersActivity extends BaseActivity implements MyOrdersContract.View {
    ActivityMyOrdersBinding binding;
    MyOrdersPresenter<MyOrdersContract.View> presenter;
    OrdersAdapter adapter;

    //dummy Mode variables
    boolean isFirstPageLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        setAppTheme();
        init();
    }

    private void init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_orders);
        setUpNavigationView(binding.drawerLayout,binding.layoutNavMenu);
        setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.title_my_orders), true);
        presenter = new MyOrdersPresenter<>();
        presenter.onAttach(this);
        presenter.init();
    }

    @Override
    public void setUpView(boolean isReset) {
        if (isReset) {
            adapter = null;
        }
        AppUtils.setVisibility(binding.tvNotFound, View.GONE);

        if (adapter == null) {
            adapter = new OrdersAdapter(this, new OnRecyclerViewItemClickListener<Order>() {
                @Override
                public void onClicked(Order bean, View view, int position, ViewType viewType) {
                    Log.e("MyOrdersActivity","ViewType. " + viewType.name() );

                    if(viewType==ViewType.View)
                        gotoOrderDetailsScreen();
                    else if (viewType==ViewType.Active){
                        Intent intent = new Intent(MyOrdersActivity.this, OrderReceiveSuccess.class);
                        intent.putExtra(OrderReceiveSuccess.ARG_SUCCESS_MESSAGE, MyOrdersActivity.this.getString(R.string.msg_activation_successful));
                        startActivity(intent);
                    }else if(viewType==ViewType.Track){
                        Intent intent=new Intent(MyOrdersActivity.this, TrackingDetailActivity.class);
                        intent.putExtra(TrackingDetailActivity.ARG_ORDER_ID,bean.getOrderId());
                        intent.putExtra(TrackingDetailActivity.ARG_ORDER_DATE,bean.getDate());
                        intent.putExtra(TrackingDetailActivity.ARG_ORDER_STATUS,bean.getOrderStatus());
                        startActivity(intent);
                    }
                }

                @Override
                public void onLastItemReached() {
                    presenter.loadMoreRecords();
                }
            },false);
            adapter.isAddFooter=false;
            binding.rvMyOrders.setAdapter(adapter);
        } else {
            binding.rvMyOrders.setAdapter(adapter);
        }

    }

    private void gotoOrderDetailsScreen() {
        startActivity(new Intent(this, OrderDetailsReviewActivity.class));
    }

    @Override
    public void loadDataToView(ArrayList<Order> list) {
        isFirstPageLoaded = true;
        adapter.addItems(list);
    }

    @Override
    public void setNotRecordsFoundView(boolean isActive) {
        isFirstPageLoaded = true;
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
    public void onRetryClicked() {
        isFirstPageLoaded = false;
        presenter.reset();
    }

    @Override
    public void showProgressBar() {
       /* if (isFirstPageLoaded) {
            adapter.isAddFooter = true;
            adapter.updateBottomProgress(0);
            adapter.notifyDataSetChanged();

        } else {*/
            super.showProgressBar();
        /*}*/
    }

    @Override
    public void hideProgressBar() {
        /*if (isFirstPageLoaded) {
            adapter.updateBottomProgress(2);
        } else {*/
            super.hideProgressBar();
        /*}*/
    }


    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_user_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_right_menu:
//                Toast.makeText(this, "MENU", Toast.LENGTH_LONG).show();
                binding.drawerLayout.openDrawer(GravityCompat.END);
                return true;

            case android.R.id.home:
                finish();
                return true;


            default:
                return super.onOptionsItemSelected(item);

        }

    }
}

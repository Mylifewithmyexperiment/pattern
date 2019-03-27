package com.sterlite.dccmappfordealersterlite.activity.Activation;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.activity.OrederReceiveSuccess.OrderReceiveSuccess;
import com.sterlite.dccmappfordealersterlite.activity.orderdetailreview.OrderDetailsReviewActivity;
import com.sterlite.dccmappfordealersterlite.activity.trackingDetail.TrackingDetailActivity;
import com.sterlite.dccmappfordealersterlite.adapter.OrdersAdapter;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityActivationBinding;
import com.sterlite.dccmappfordealersterlite.model.MyOrdersListing.Order;


public class ActivationActivity extends BaseActivity implements ActivationContract.View {


    ActivityActivationBinding binding;
    ActivationContract.Presenter<ActivationContract.View> presenter;
    OrdersAdapter adapter;
    public final static String ARG_IS_TRACK = "argTrack";

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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_activation);
        setUpNavigationView(binding.drawerLayout, binding.layoutNavMenu);
        boolean isTrack = getIntent().getBooleanExtra(ARG_IS_TRACK, false);
        if(!isTrack) {
            setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.title_activation), true);
            setUpView(true);

        }else{
            setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.title_my_orders), true);
            setUpView(true);
        }

       /* binding.btnTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ActivationActivity.this, TrackingDetailActivity.class);
                startActivity(intent);
            }
        });*/
        presenter = new ActiviationPresenter<>();
        presenter.onAttach(this);
        presenter.init();
    }

    @Override
    public void setUpView(boolean isReset) {
        if (isReset) {
            adapter = null;
            isFirstPageLoaded = false;
        }
        AppUtils.setVisibility(binding.tvNotFound, View.GONE);

        if (adapter == null) {
            adapter = new OrdersAdapter(this, new OnRecyclerViewItemClickListener<Order>() {
                @Override
                public void onClicked(final Order bean, View view, int position, ViewType viewType) {
                    Log.e("ActivationActivity","ViewType.Active" + viewType.name() );

                    if (viewType == ViewType.View)
                        gotoOrderDetailsScreen();
                    else if (viewType == ViewType.Active) {
                        Log.e("ActivationActivity","ViewType.Active"  );

                        String serviceIntanceNumber = bean.getOrderId();
//                        serviceIntanceNumber="SUN0050009";
                        presenter.activateOrder(serviceIntanceNumber);

                    } else if (viewType == ViewType.Track) {
                        Log.e("ActivationActivity","ViewType.Track"  );
                        presenter.getDnNumWithAuthToken(bean);
                    }
                }

                @Override
                public void onLastItemReached() {
                    presenter.loadMoreRecords();
                }
            }, true);
            binding.rvMyOrders.setAdapter(adapter);
        } else {
            binding.rvMyOrders.setAdapter(adapter);
        }

    }

    @Override
    public void getDNNumberbyOrderApiCallSuccess(String dnNum,Order bean) {
        if(dnNum!=null){
            Intent intent = new Intent(ActivationActivity.this, TrackingDetailActivity.class);
            intent.putExtra(TrackingDetailActivity.ARG_ORDER_ID, bean.getOrderId());
            intent.putExtra(TrackingDetailActivity.ARG_ORDER_DATE, getFormatedDate(bean.getDate()));
            intent.putExtra(TrackingDetailActivity.ARG_ORDER_STATUS, bean.getOrderStatus());
            intent.putExtra(TrackingDetailActivity.ARG_INVENTORY_NUMBER, dnNum);
            startActivity(intent);
        }else {
            Intent intent = new Intent(ActivationActivity.this, TrackingDetailActivity.class);
            intent.putExtra(TrackingDetailActivity.ARG_ORDER_ID, bean.getOrderId());
            intent.putExtra(TrackingDetailActivity.ARG_ORDER_DATE, getFormatedDate(bean.getDate()));
            intent.putExtra(TrackingDetailActivity.ARG_ORDER_STATUS, bean.getOrderStatus());
            startActivity(intent);
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

    /*@Override
    public void showProgressBar() {
        if (isFirstPageLoaded) {
            adapter.isAddFooter = true;
            adapter.updateBottomProgress(0);
            adapter.notifyDataSetChanged();

        } else {
            super.showProgressBar();
        }
    }

    @Override
    public void hideProgressBar() {
        if (isFirstPageLoaded) {
            adapter.updateBottomProgress(2);
        } else {
            super.hideProgressBar();
        }
    }
*/

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
    public void gotoSuccessScreen(String message) {
        message=getString(R.string.msg_customer_activate);
        Intent intent = new Intent(ActivationActivity.this, OrderReceiveSuccess.class);
        intent.putExtra(OrderReceiveSuccess.ARG_SUCCESS_MESSAGE, message);
        startActivity(intent);
    }
}

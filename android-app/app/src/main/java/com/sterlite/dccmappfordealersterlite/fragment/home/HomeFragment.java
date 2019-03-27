package com.sterlite.dccmappfordealersterlite.fragment.home;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amplifyreach.chatsdk.ARActivity;
import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.activity.loginnew.NewLoginActivity;
import com.sterlite.dccmappfordealersterlite.adapter.HomeAdapter;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.base.BaseFragment;
import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.databinding.FragmentHomeBinding;
import com.sterlite.dccmappfordealersterlite.model.HomeFragment.BalanceData;
import com.sterlite.dccmappfordealersterlite.model.HomeFragment.SubscriberServicewiseBalance;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.model.bssrest.ServiceInventoryResponse;

import static android.Manifest.permission.MANAGE_DOCUMENTS;
import static android.Manifest.permission.MODIFY_AUDIO_SETTINGS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.facebook.FacebookSdk.isDebugEnabled;


public class HomeFragment extends BaseFragment implements HomeContract.View {

    private Context context;
    private static final int PERMISSION_REQUEST_CODE = 200;

    public Boolean isServiceInstance = false;

    public static HomeFragment newInstance() {
        return newInstance(null);
    }

    public static HomeFragment newInstance(Bundle bundle) {
        HomeFragment fragment = new HomeFragment();
        if (bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }


    private HomeAdapter homeAdapter;
    private FragmentHomeBinding binding;
    private HomeContract.Presenter<HomeContract.View> presenter;
    private boolean isAdmin;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(activity, getClass().getSimpleName());

        isAdmin= DCCMDealerSterlite.getDataManager().getBoolean(AppPreferencesHelper.ADMIN,false);

        presenter = new HomePresenter<>();
        presenter.onAttach(this);
        context = getContext();
        if (!isAdmin)
        getCustomerDetails(isServiceInstance);
        // setwebView();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        activity.setTitle("");
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        setClickListener();
        if (!isAdmin)
        presenter.init();
        else
            presenter.admin();
        binding.tvGmId.setText(getString(R.string.good_morning) + " " + DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_NAME, null));

        String p_customer_name = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.P_CUSTOMER_NAME, null);
        if (p_customer_name != null) {
            // binding.tvMobile.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            binding.tvMobile.setText("Customer: " + p_customer_name + "         Number: " + DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.PREF_DN_NUMBER, null));
        } else {
            binding.tvMobile.setText(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.PREF_DN_NUMBER, null));
        }
        binding.ivTransform.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!isAdmin)
                getCustomerDetails(true);
            }
        });
        binding.ivChat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                chatBox();
            }
        });
        if (Constants.APP.equalsIgnoreCase(Constants.MTN)) {
            binding.companyLogo.setImageResource(R.drawable.mtn_logo);
        } else if (Constants.APP.equalsIgnoreCase(Constants.TELKOMSEL)) {
            binding.companyLogo.setImageResource(R.drawable.logo_name);
        } else if (Constants.APP.equalsIgnoreCase(Constants.VODAFONE)) {
            binding.companyLogo.setImageResource(R.drawable.ic_vodafone1);
        } else {
            binding.companyLogo.setVisibility(View.INVISIBLE);
        }
        String theme=DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.APP_THEME, Constants.DEFAULT_APP_THEME);
        if (theme.equalsIgnoreCase(Constants.APP_THEME)){
            binding.prepaidImage.setImageResource(R.drawable.banner1_red_prepaid);
        }
        else {
            binding.prepaidImage.setImageResource(R.drawable.banner1_blue_prepaid);

        }
        /*binding.btnLanding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(activity, LandingPageActivity.class);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(newIntent);
            }
        });*/


        return binding.getRoot();
    }

    private void chatBox() {
        requestPermission();
        Intent intent = new Intent(this.getActivity(), ARActivity.class);
        Bundle bundle = new Bundle();

        //Set client ID
        // uncomment following lines, for more information contact at sales@amplifyreach.com
        bundle.putLong("clientId", 1410);

        //Set bot ID
        // uncomment following line, for more information contact at sales@amplifyreach.com
        bundle.putString("botId", "d8519054092fdbcf");

        //set Authentication Token
        bundle.putString("authToken", "YOUR_AUTH_TOKEN");

        intent.putExtras(bundle);
        this.getActivity().startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isAdmin)
        presenter.init();
    }


    private void setClickListener() {
        binding.ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BaseActivity baseActivity = ((BaseActivity) getActivity());
                    if (baseActivity != null && baseActivity.drawer != null) {
                        baseActivity.drawer.openDrawer(GravityCompat.END);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        binding.imgUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BaseActivity baseActivity = ((BaseActivity) getActivity());
                    Intent intent = new Intent(baseActivity, NewLoginActivity.class);
                    intent.putExtra(Constants.PROFILE, true);
                    baseActivity.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        binding.tvRefreshTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvDeactivation.setVisibility(View.GONE);
                presenter.reset();
            }
        });


    }

    @Override
    public void setUpView(boolean isReset) {


        if (isReset) {
            homeAdapter = null;
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 4) /*{
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        }*/;
        binding.rvHomeItem.setLayoutManager(gridLayoutManager);
        if (homeAdapter == null &&  !isAdmin) {
            homeAdapter = new HomeAdapter(activity, new OnRecyclerViewItemClickListener<BalanceData>() {
                @Override
                public void onClicked(BalanceData bean, View view, int position, ViewType viewType) {
                    showDataInChart(bean);
                }

                @Override
                public void onLastItemReached() {

                }
            });
            binding.rvHomeItem.setAdapter(homeAdapter);
        } else {
            binding.rvHomeItem.setAdapter(homeAdapter);
        }
        homeAdapter.isAddFooter = false;
    }

    private void showDataInChart(BalanceData balanceData) {
        Log.e("BalanceData", balanceData + "");


        String strMenu = "", strValue = "", strTotal = "", strUnit = null;
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        if (balanceData.getBalanceType().equalsIgnoreCase("VOLUME")) {
            strValue = String.valueOf(df.format(balanceData.getAvailableBalance() / (1024 * 1024.0 * 1024.0)));
            Log.e("StrValue::", strValue);

            strTotal = String.valueOf(df.format(balanceData.getTotalUsageBalance() / (1024 * 1024.0 * 1024.0)));
            Log.e("strTotal::", strTotal);

          /*  binding.fitChart.setMaxValue((int)balanceData.getTotalUsageBalance()/(1024*1024*1024));
            binding.fitChart.setValue((*//*(balanceData.getTotalUsageBalance()/(1024*1024)) -*//*(int) (balanceData.getAvailableBalance()/(1024*1024*1024))));
          */
            binding.fitChart.setMaxValue((int) (balanceData.getTotalUsageBalance() / (1024 * 1024.0 * 1024.0)));
            binding.fitChart.setValue((int) (balanceData.getAvailableBalance() / (1024 * 1024.0 * 1024.0)));

            strUnit = getString(R.string.lbl_mb);
        } else if (balanceData.getBalanceType().equalsIgnoreCase("TIME")) {
            strValue = String.valueOf(df.format(balanceData.getAvailableBalance() / 60.0));
            strTotal = String.valueOf(df.format(balanceData.getTotalUsageBalance() / 60.0));
            binding.fitChart.setMaxValue((int) balanceData.getTotalUsageBalance() / 60);
            binding.fitChart.setValue(/*(balanceData.getTotalUsageBalance() /60)-*/ ((int) balanceData.getAvailableBalance() / 60));

            strUnit = getString(R.string.minutes);
        } else if (balanceData.getBalanceType().equalsIgnoreCase("EVENT")) {
            strValue = String.valueOf((int) balanceData.getAvailableBalance());
            strTotal = String.valueOf((int) balanceData.getTotalUsageBalance());
            binding.fitChart.setMaxValue((int) balanceData.getTotalUsageBalance());
            binding.fitChart.setValue(/*balanceData.getTotalUsageBalance() -*/(int) balanceData.getAvailableBalance());

            strUnit = getString(R.string.sms);
        } else if (balanceData.getBalanceType().equalsIgnoreCase("AMOUNT")) {
            if (!TextUtils.isEmpty(balanceData.getCurrency())) {
                strValue = String.valueOf(balanceData.getCurrency() + df.format(balanceData.getAvailableBalance() / 100));
                strTotal = String.valueOf(balanceData.getCurrency() + df.format(balanceData.getTotalUsageBalance() / 100));
            } else {
                strValue = String.valueOf(df.format(balanceData.getAvailableBalance() / 100));
                strTotal = String.valueOf(df.format(balanceData.getTotalUsageBalance() / 100));
            }
            binding.fitChart.setMaxValue(balanceData.getTotalUsageBalance());
            binding.fitChart.setValue(/*balanceData.getTotalUsageBalance() -*/ balanceData.getAvailableBalance());

            strUnit = AppUtils.getPriceWithSymbol("");
        }


        if (!TextUtils.isEmpty(strValue))
            binding.tvContentValue.setText(strValue);
        else
            binding.tvContentValue.setText("");

        if (!TextUtils.isEmpty(strTotal)) {
            binding.tvTotalUnits.setText(strTotal);
        } else {
            binding.tvTotalUnits.setText("");
        }
        if (!TextUtils.isEmpty(strUnit))
            binding.tvUnit.setText(strUnit);
        else
            binding.tvUnit.setText("");
        if (!TextUtils.isEmpty(balanceData.getMenuName()))
            binding.tvContentMenu.setText(balanceData.getMenuName());
        else
            binding.tvContentMenu.setText("");
    }


    @Override
    public void loadDataToView(SubscriberServicewiseBalance subscriberServicewiseBalance) {

        String partner_id = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.PARTNER_UID, null);
        String customer_uid = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID, null);
        boolean isPartner = DCCMDealerSterlite.getDataManager().getBoolean(AppPreferencesHelper.PARTNER, false);
        boolean isAdmin = DCCMDealerSterlite.getDataManager().getBoolean(AppPreferencesHelper.ADMIN, false);

        Log.e("Admin User::",isAdmin+" ");
        if (isAdmin)
        {
            binding.fitChart.setVisibility(View.GONE);
            binding.tvLblLeft.setText("Change Properties");
            binding.tvLblof.setVisibility(View.GONE);
            binding.tvRefreshTime.setVisibility(View.GONE);
        }
        else if (isPartner && customer_uid.equalsIgnoreCase(partner_id)){
            binding.fitChart.setVisibility(View.GONE);
            binding.tvLblLeft.setText("Onboard Customer from Menu");
            binding.tvLblof.setVisibility(View.GONE);
            binding.tvRefreshTime.setVisibility(View.GONE);
        }
        else {
            homeAdapter.getAll().clear();
        /*if (subscriberServicewiseBalance.getBalanceDatas()!=null){
            binding.tvLblLeft.setText(getString(R.string.lbl_left));
            binding.tvLblof.setText(getString(R.string.lbl_of));
        }
        else {
            binding.tvLblLeft.setText(R.string.msg_no_data);
            binding.tvLblof.setText("");
        }*/
            binding.tvLblLeft.setText(getString(R.string.lbl_left));
            binding.tvLblof.setText(getString(R.string.lbl_of));
            Log.e("SubscriberBalance::", subscriberServicewiseBalance.toString());
            if (subscriberServicewiseBalance.getBalanceDatas().size() >= 4) {
                binding.rvHomeItem.setLayoutManager(new GridLayoutManager(context, 4));
            } else {
                binding.rvHomeItem.setLayoutManager(new GridLayoutManager(context, subscriberServicewiseBalance.getBalanceDatas().size()));
            }
            homeAdapter.addItems((ArrayList<BalanceData>) subscriberServicewiseBalance.getBalanceDatas());
            binding.tvRemainDays.setText(subscriberServicewiseBalance.getDaysLeft());
            Date date = new Date();
            DateFormat dateFormat = android.text.format.DateFormat.getTimeFormat(getApplicationContext());
            binding.tvRefreshTime.setText(getString(R.string.refreshed_at) + dateFormat.format(date));
            showDataInChart(subscriberServicewiseBalance.getBalanceDatas().get(0));
            saveBalance(subscriberServicewiseBalance);
        } /*else {
            binding.fitChart.setVisibility(View.GONE);
            binding.tvLblLeft.setText("Onboard Customer from Menu");
            binding.tvLblof.setVisibility(View.GONE);
            binding.tvRefreshTime.setVisibility(View.GONE);
        }*/

    }

    private void saveBalance(SubscriberServicewiseBalance subscriberServicewiseBalance) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        for (BalanceData balanceData1 : subscriberServicewiseBalance.getBalanceDatas()) {
            if (balanceData1.getBalanceType().equalsIgnoreCase("AMOUNT")) {
                DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.BALANCE, String.valueOf(df.format(balanceData1.getAvailableBalance() / 100)));
            }
        }
    }

    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }


    private void getCustomerDetails(boolean isServiceInstance) {
        presenter.getCustomerDetails(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID, null), isServiceInstance);
    }


    private void setwebView() {
        WebSettings webSettings = binding.webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        binding.webview.loadDataWithBaseURL(null, "javascript:<script> (function(w, d, s, l, i) { var f = d.getElementsByTagName(s)[0], j = d.createElement(s); j.async = !0; j.src = '//apps.amplifyreach.com/' + s + '/' + l + '/' + i + '.js?t=' + new Date().getTime(); f.parentNode.insertBefore(j, f) })(window, document, 'script', '1410', 'd8519054092fdbcf'); </script>", "text/html", "utf-8", null);
        ;

        //  binding.webview.addJavascriptInterface(new WebAppInterface(this), "Android");


    }

    @Override
    public void onSuccessCustDetails(List<ServiceInventoryResponse> inventoryResponses, boolean isServiceInstance) {
        binding.tvDeactivation.setVisibility(View.GONE);
        if (inventoryResponses.size() > 0) {
            if (isServiceInstance) {

                AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
                // ArrayList<String> listServiceInstance = Preferences.getPreferenceArray(context1, PrefEntity.LIST_SERVICE_INSTANCE_NUMBER);
                //List<ServiceInstanceResponse> serviceInstanceResponseList = new ArrayList<>();
                // List<ServiceInstanceResponse> serviceInstanceResponseVOISL = new UFTserviceInstanceResponseVOISL();
                builderSingle.setTitle(R.string.select_subscription);
                final ArrayAdapter<ServiceInventoryResponse> arrayAdapter = new ArrayAdapter<ServiceInventoryResponse>(context, R.layout.select_dialog_single, inventoryResponses);


                builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        hideProgressBar();
                    }
                }).setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int selectedPosition = which;
                        Log.e("which:::", selectedPosition + " ");

                        final String inventoryNumber = arrayAdapter.getItem(selectedPosition).getInventoryNumber();
                        Log.e("inventoryNumber:::", inventoryNumber);

                        final String serviceInstanceNumber = arrayAdapter.getItem(selectedPosition).getServiceInstanceNumber();
                        Log.e("serviceInstanceNumber", serviceInstanceNumber);
                        DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.PREF_DN_NUMBER, inventoryNumber);
                        DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.PREF_SUBSCRIPTION_NUMBER, serviceInstanceNumber);

                        binding.tvMobile.setText(inventoryNumber);

                        AlertDialog.Builder builderInner = new AlertDialog.Builder(getActivity());
                        builderInner.setMessage("Setting " + inventoryNumber + " as your Default Service Instance.");
                        builderInner.setTitle("Service Instance");
                        builderInner.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.e("inv No ", inventoryNumber + " ");
                                //Constants.setPlan(context1, serviceInstanceNumber);

                                dialog.dismiss();
                                presenter.reset();

                       /* Intent i = new Intent(DashBoardActivity.this, MainActivityPrepaid.class);
                        i.putExtra(Constants.FROM_WHERE, Constants.LOGIN_ACTIVITY);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context1.startActivity(i);
                        ((Activity) context1).finish();*/
                            }
                        });
                        builderInner.show();
                    }
                });
                builderSingle.show();

          /*  builderSingle.setOnKeyListener(new Dialog.OnKeyListener() {

                @Override
                public boolean onKey(DialogInterface dialog, int keyCode,
                                     KeyEvent event) {
                    // TODO Auto-generated method stub
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        dialog.dismiss();
                        hideProgressBar();
                        hideProgressBar();


                    }
                    return true;
                }
            });*/

            } else {
                hideProgressBar();
            }
        } else {
            hideProgressBar();
            AppUtils.showToast(getActivity(), getString(R.string.msg_service_instance_not_found));
        }
    }

    @Override
    public void onFail() {
        boolean isAdmin = DCCMDealerSterlite.getDataManager().getBoolean(AppPreferencesHelper.ADMIN, false);

        if (!isAdmin) {

            binding.tvDeactivation.setVisibility(View.VISIBLE);
            AppUtils.showToast(getActivity(), getString(R.string.msg_service_instance_not_found));
        }
    }

    @Override
    public void FailData(String message) {
        boolean isAdmin = DCCMDealerSterlite.getDataManager().getBoolean(AppPreferencesHelper.ADMIN, false);

        if (isAdmin)
        {
            binding.ivTransform.setVisibility(View.GONE);
            binding.fitChart.setVisibility(View.GONE);
            binding.tvLblLeft.setText("Change Properties from menu");
            binding.tvLblof.setVisibility(View.GONE);
            binding.tvRefreshTime.setVisibility(View.GONE);
        }
        else {
            binding.tvDeactivation.setVisibility(View.VISIBLE);
            binding.tvLblLeft.setText(R.string.msg_no_data);
            binding.tvLblof.setText("");
        }
        }


    private void requestPermission() {

        ActivityCompat.requestPermissions(this.getActivity(), new String[]{RECORD_AUDIO,MODIFY_AUDIO_SETTINGS,MANAGE_DOCUMENTS, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }
}

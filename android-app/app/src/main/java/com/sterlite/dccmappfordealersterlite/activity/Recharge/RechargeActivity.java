package com.sterlite.dccmappfordealersterlite.activity.Recharge;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import java.util.ArrayList;
import java.util.HashMap;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.Utils.CustomAlertDialog;
import com.sterlite.dccmappfordealersterlite.Utils.DummyLists;
import com.sterlite.dccmappfordealersterlite.activity.PostPaidPlanForYou.PostPaidPlanForYouActivity;
import com.sterlite.dccmappfordealersterlite.activity.RechargeSummery.RechargeSummeryActivity;
import com.sterlite.dccmappfordealersterlite.adapter.RecommandedPlansAdapter;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityRechargeBinding;
import com.sterlite.dccmappfordealersterlite.model.ReccomandedPlanPOJO;
import com.sterlite.dccmappfordealersterlite.model.Recharge;


public class RechargeActivity extends BaseActivity implements RechargeContract.View, View.OnClickListener {

    private ActivityRechargeBinding binding;

    private RechargeContract.Presenter<RechargeContract.View> presenter;
    private RecommandedPlansAdapter adapter;
    private Recharge recharge;
//    final int SELECT_PHONE_NUMBER = 1015;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        setAppTheme();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recharge);
//        always place setUpNavigationView above setupView;
        setUpNavigationView(binding.drawerLayout, binding.layoutNavMenu);
        setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.lbl_recharge), true);
        presenter = new RechargePresenter<>();
        presenter.onAttach(this);
        setErrorListener();
        setClickListner();
        Boolean pretopost= isPretoPostEnabled();
        presenter.init(pretopost);
       /* binding.btnRechargeNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(RechargeActivity.this, RechargeSummeryActivity.class);
                startActivity(newIntent);
            }
     -   });*/
    }

    @Override
    public void setUpView(boolean isReset) {
        if (isReset) {
            adapter = null;
        }

        if (adapter == null) {
            adapter = new RecommandedPlansAdapter(this, new OnRecyclerViewItemClickListener<ReccomandedPlanPOJO>() {
                @Override
                public void onClicked(ReccomandedPlanPOJO bean, View view, int position, ViewType viewType) {
                    binding.edtAmount.setText(bean.getAmount());
                    HashMap<String, String> hm = DummyLists.getRechargeSummery(bean.getAmount());
                    binding.tvData.setText(hm.get(Constants.DATA));
                    binding.tvMonetary.setText(AppUtils.getPriceWithSymbol(hm.get(Constants.MONETARY)));
                }

                @Override
                public void onLastItemReached() {

                }
            });
            binding.rvRecommandedContainer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            binding.rvRecommandedContainer.setAdapter(adapter);
        }
    }

    @Override
    public void loadDataToView(ArrayList<ReccomandedPlanPOJO> recommandedPlans) {
        if (adapter != null) {
            adapter.getAll().clear();
            adapter.addItems(recommandedPlans);
        }
        try {
            String dnNum = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.PREF_DN_NUMBER, null);
            Log.e("RechargeActivity", " AppUtils.isUserRegistered() :" + AppUtils.isUserRegistered());
            Log.e("RechargeActivity", " dnNum :" + dnNum);
            if (AppUtils.isUserRegistered() && dnNum != null) {
                binding.edtNumber.setText(dnNum);
            }
        } catch (Exception e) {
            Log.e("RechargeActivity", " " + e);
        }
    }

    private void setClickListner() {
        //  binding.tvMorePacks.setOnClickListener(this);
        binding.btnContinue.setOnClickListener(this);
        binding.edtNumber.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    AppUtils.hideKeyboard(RechargeActivity.this);
                    return true;
                }
                return false;
            }
        });
    }

    private void setErrorListener() {
        AppUtils.addTextChangedListener(binding.edtAmount, binding.iedtAmount);
        AppUtils.addTextChangedListener(binding.edtNumber, binding.iedtNumber);


        AppUtils.addTextFocusChangeListener(binding.edtAmount, binding.scrollMainContainer, binding.toolbar.appBar);
        AppUtils.addTextFocusChangeListener(binding.edtNumber, binding.scrollMainContainer, binding.toolbar.appBar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            if (AppUtils.isUserRegistered()) {
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.activity_user_detail_menu, menu);
                return true;
            } else {
                if (drawer != null)
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.END);
                return super.onCreateOptionsMenu(menu);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return super.onCreateOptionsMenu(menu);
        }
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
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //    case R.id.tvMorePacks:
//                AppUtils.showToast(this, getString(R.string.msg_more_pack_clicked));
            //      break;
            case R.id.btnContinue:
                if (isValidData()) {
                    recharge = new Recharge();
                    recharge.setAmount(binding.edtAmount.getText().toString());
                    recharge.setNumber(binding.edtNumber.getText().toString());
                    gotoRechargeSummaryScreen();
                }
        }
    }

    private void gotoRechargeSummaryScreen() {
        if (recharge != null) {
            Intent newIntent = new Intent(RechargeActivity.this, RechargeSummeryActivity.class);
            newIntent.putExtra(RechargeSummeryActivity.RECHARGE, recharge);
            startActivity(newIntent);
        }

    }

    private boolean isValidData() {
        if (binding.edtNumber.getText() == null || TextUtils.isEmpty(binding.edtNumber.getText().toString().trim())) {
            binding.iedtNumber.setError(getString(R.string.val_enter_mobile_no));
            binding.edtNumber.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.edtNumber.getY());
            return false;
        } else if (AppUtils.validateMobileNoLength(binding.edtNumber.getText().toString().trim().length())) {
            binding.iedtNumber.setError(getString(R.string.val_enter_valid_mobile_no));
            binding.edtNumber.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.edtNumber.getY());
            return false;
        } else if (binding.edtAmount.getText() == null || TextUtils.isEmpty(binding.edtAmount.getText().toString().trim())) {
            binding.iedtAmount.setError(getString(R.string.val_enter_mobile_no));
            binding.edtAmount.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.edtAmount.getY());
            return false;
        }
        return true;
    }

   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHONE_NUMBER && resultCode == RESULT_OK) {
            // Get the URI and query the content provider for the phone number
            Uri contactUri = data.getData();
            String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
            Cursor cursor = null;
            if (contactUri != null) {
                cursor = getContentResolver().query(contactUri, projection,
                        null, null, null);
            }

            // If the cursor returned is valid, get the phone number
            if (cursor != null && cursor.moveToFirst()) {
                int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberIndex);
                // Do something with the phone number
                binding.edtNumber.setText(number);
                cursor.close();
            }


        }
    }*/

    @Override
    public void showPopUp(String title, String message) {
        if (DCCMDealerSterlite.getDataManager().getBoolean(AppPreferencesHelper.PREF_IS_USER_LOGGED_IN, false)) {
            CustomAlertDialog.showAlertDialog(this, title, message, getString(R.string.btn_proceed), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(RechargeActivity.this, PostPaidPlanForYouActivity.class));
                }
            }, getString(R.string.btn_cancel), null, true);
        }
    }
}
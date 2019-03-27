package com.sterlite.dccmappfordealersterlite.activity.TicketCreate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.Utils.CustomAlertDialog;
import com.sterlite.dccmappfordealersterlite.activity.PostPaidPlanForYou.PostPaidPlanForYouActivity;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityCreateTicketBinding;
import com.sterlite.dccmappfordealersterlite.model.Ticket;

import java.util.ArrayList;
import java.util.HashMap;


public class CreateTicket extends BaseActivity implements TicketContract.View, View.OnClickListener {
    private ActivityCreateTicketBinding binding;
    private TicketContract.Presenter<TicketContract.View> presenter;
    private ProgressDialog progressDialog;
    private Ticket ticket;
    ArrayAdapter<String> spinnerCategoryAdapter;
    ArrayAdapter<String> spinnerSubCategoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_ticket);
        setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.title_create_ticket), true);
        init();
    }
    private void init() {
        presenter = new TicketPresenter<>();
        presenter.onAttach(this);
        presenter.init();
        setClickListeners();
        progressDialog = AppUtils.initializeProgressDialog(this);
    }

    @Override
    public void loadDataToView(final ArrayList<String> category,final HashMap<String,ArrayList<String>> subCategory) {
        spinnerCategoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, category);
        binding.spnCategory.setAdapter(spinnerCategoryAdapter);
        binding.spnCategory.setSelection(0);

        Log.e(" loadDataToView : "," "+subCategory.get(category.get(0)));
        binding.spnSubCategory.setAdapter(new ArrayAdapter<String>(CreateTicket.this,
                android.R.layout.simple_spinner_item, subCategory.get(category.get(0))));


        binding.spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = parent.getItemAtPosition(position).toString();
                binding.spnSubCategory.setAdapter(new ArrayAdapter<String>(CreateTicket.this,
                        android.R.layout.simple_spinner_item, subCategory.get(selectedCategory)));
                Log.e(" loadDataToView : ","selectedCategory :  "+selectedCategory +" : subCategory"+subCategory.get(selectedCategory));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        binding.spnSubCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSubCategory = parent.getItemAtPosition(position).toString();
             //   AppUtils.showToast(CreateTicket.this,selectedSubCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setErrorListener();
    }

    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }
    private void setErrorListener() {
        AppUtils.addTextChangedListener(binding.edtTitle, binding.iedtTitle);
        AppUtils.addTextChangedListener(binding.edtDescription, binding.iedtDescription);

        AppUtils.addTextFocusChangeListener(binding.edtTitle, binding.scrollMainContainer, binding.toolbar.appBar);
        AppUtils.addTextFocusChangeListener(binding.edtDescription, binding.scrollMainContainer, binding.toolbar.appBar);

    }

    private void setClickListeners() {
        binding.btnContinue.setOnClickListener(this);       
        binding.scrollMainContainer.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                binding.edtCity.dismissDropDown();
            }
        });
    }
    @Override
    public void showError(int code, String resId) {
        if (code == Constants.FAIL_CODE)
            super.showError(code, getString(R.string.msg_registration_faild));
        else {
            super.showError(code, resId);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnContinue:
                if (isValidData()) {
                    showProgressBar();
                    Log.e("CreateTicket "," btnContinue ");
                    saveValuesToModel();
                    presenter.createTicket(ticket);
                }
                break;

        }
    }
    private Ticket saveValuesToModel() {
        Log.e("userSignUpWsResponseDTO"," saveValuesToModel ");

        ticket =new Ticket();
        ticket.setTitle(binding.edtTitle.getText().toString());
        ticket.setDescription(binding.edtDescription.getText().toString());
        ticket.setCategory(binding.spnCategory.getSelectedItem().toString());
        ticket.setSubCategory(binding.spnSubCategory.getSelectedItem().toString());

        String customerUID= DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID,null);
        ticket.setCustomerNumber(customerUID.toUpperCase());

        return ticket;
    }
    private boolean isValidData() {
        if (binding.edtTitle.getText() == null || TextUtils.isEmpty(binding.edtTitle.getText().toString().trim())) {
            binding.iedtTitle.setError(getString(R.string.val_enter_name));
            binding.edtTitle.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.iedtTitle.getY());
            return false;
        } else if (binding.edtDescription.getText() == null || TextUtils.isEmpty(binding.edtDescription.getText().toString().trim())) {
            binding.iedtDescription.setError(getString(R.string.val_enter_mobile_no));
            binding.edtDescription.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.iedtDescription.getY());
            return false;
        }
        return true;
    }

    @Override
    public void showProgressBar() {
        if (progressDialog != null && !progressDialog.isShowing())
            progressDialog.show();

    }

    @Override
    public void hideProgressBar() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
            progressDialog.dismiss();
        }
    }

    @Override
    public void successCreateTicket(Ticket ticketResponse) {
        String title,message;
        if(ticketResponse.getResponseCode().equals("0")){
            Log.e("createTicket","onSuccess");
            title = "Report Problem";
            message = "Thank you for contacting support desk. Your ticket "+
                    ticketResponse.getTicketNumber()+" has been created successfully.";
        }else{
            title = "Report Problem";
            message = "Something went wrong , please try after some time";
        }
        showPopUp(title, message);
    }

    public void showPopUp(String title, String message) {
        if (DCCMDealerSterlite.getDataManager().getBoolean(AppPreferencesHelper.PREF_IS_USER_LOGGED_IN, false)) {
            CustomAlertDialog.showAlertDialog(this, title, message, getString(R.string.btn_proceed), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            }, null, null, false);
        }
    }
}

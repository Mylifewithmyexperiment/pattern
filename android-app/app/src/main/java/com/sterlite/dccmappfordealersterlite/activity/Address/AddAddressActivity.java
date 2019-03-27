package com.sterlite.dccmappfordealersterlite.activity.Address;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;

import java.util.Calendar;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.DateTimeUtils;
import com.sterlite.dccmappfordealersterlite.activity.kycdetail.KYCDetailActivity;
import com.sterlite.dccmappfordealersterlite.activity.orderdetailreview.OrderDetailsReviewActivity;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityAddAddressBinding;
import com.sterlite.dccmappfordealersterlite.model.Address;
import com.sterlite.dccmappfordealersterlite.model.Region;

public class AddAddressActivity extends BaseActivity implements AddAddressContract.View, View.OnClickListener {
    public static final String ARG_IS_SHIPPING_ADDRESS = "isShippingAddress";
    public static final String ARG_PIN = "argPin";
    public static final String FROM = "from";
    public static final String ADDRESS = "address";
//    todo add chagnes ....

    private ActivityAddAddressBinding binding;
    private AddAddressContract.Presentner<AddAddressContract.View> presenter;
    private boolean isShippingAddress = false;
    private Address address;
    private String from;
    private boolean isDateSeletected = false;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        setAppTheme();
        init();

        Log.e("plan:address", DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID, "")+"H");

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setErrorListner();
    }

    private void init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_address);
        if(DCCMDealerSterlite.getDataManager().getBoolean(AppPreferencesHelper.IS_BB,false)){
            binding.lblDate.setText(R.string.lbl_installation_date);
            binding.lblTime.setText(R.string.lbl_installation_time);
        }
        from = getIntent().getStringExtra(FROM);
        if (getIntent().getBooleanExtra(ARG_IS_SHIPPING_ADDRESS, false)) {
            isShippingAddress = true;
            setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.title_shiping_addredd), true);
            binding.checkBoxUseSameForShip.setVisibility(View.GONE);
            if (TextUtils.isEmpty(from))
                binding.btnProceed.setText(R.string.btn_place_order);
        } else {
            isShippingAddress = false;
            setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.title_billing_address), true);
        }
        if (!TextUtils.isEmpty(from) && from.equalsIgnoreCase(OrderDetailsReviewActivity.class.getName())) {

            binding.checkBoxUseSameForShip.setVisibility(View.GONE);
            binding.btnProceed.setText(R.string.btn_save);
        }
        address = new Address();

        setClickListeners();
//        binding.edtFirstName.setText(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CU`STOMER_NAME,null));
        presenter = new AddAddressPresenter<>();
        presenter.onAttach(this);

        address.setFname(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_NAME, null));
        address.setMobileNo(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.MOBILE_NO, null));
        if (getIntent().getStringExtra(ARG_PIN) != null) {
            String pin = getIntent().getStringExtra(ARG_PIN).trim();
            address.setPin(pin);
            Region region = Region.getRegion(pin);
            address.setAddressLine1(region.getUrban());
            address.setAddressLine2(region.getSubDistrict());
            address.setCity(region.getMunicipality());
            address.setState(region.getProvince());
        }
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis() + (86400000 * 3));
        address.setDate(calendar);
        if (TextUtils.isEmpty(from) || !from.equalsIgnoreCase(OrderDetailsReviewActivity.class.getName())) {
            presenter.init(isShippingAddress, true);
            if (!TextUtils.isEmpty(from) && from.equalsIgnoreCase(KYCDetailActivity.class.getName())) {
                address = (Address) getIntent().getSerializableExtra(ADDRESS);
            }
        } else {
            presenter.init(isShippingAddress, false);
            address = (Address) getIntent().getSerializableExtra(ADDRESS);
            AppUtils.errorLog(getClass().getSimpleName(), " address " + address);
        }

        setAddress(address);

        binding.spTime.setSelection(1);
        binding.spTitles.setSelection(1);
    }

    private void setErrorListner() {
        AppUtils.addTextChangedListener(binding.edtFirstName, binding.iedtFirstName);
        AppUtils.addTextChangedListener(binding.edtLastName, binding.iedtLastName);
        AppUtils.addTextChangedListener(binding.edtMobileNo, binding.iedtMobileNo);
        AppUtils.addTextChangedListener(binding.edtAddresLine1, binding.iedtAddresLine1);
        AppUtils.addTextChangedListener(binding.edtAddresLine2, binding.iedtAddresLine2);
        AppUtils.addTextChangedListener(binding.edtCity, binding.iedtCity);
        AppUtils.addTextChangedListener(binding.edtState, binding.iedtState);
        AppUtils.addTextChangedListener(binding.edtPin, binding.iedtPin);
        AppUtils.addTextChangedListener(binding.edtCountry, binding.iedtCountry);

        AppUtils.addTextFocusChangeListener(binding.edtFirstName, binding.scrollMainContainer, binding.toolbar.appBar);
        AppUtils.addTextFocusChangeListener(binding.edtLastName, binding.scrollMainContainer, binding.toolbar.appBar);
        AppUtils.addTextFocusChangeListener(binding.edtMobileNo, binding.scrollMainContainer, binding.toolbar.appBar);
        AppUtils.addTextFocusChangeListener(binding.edtAddresLine1, binding.scrollMainContainer, binding.toolbar.appBar);
        AppUtils.addTextFocusChangeListener(binding.edtAddresLine2, binding.scrollMainContainer, binding.toolbar.appBar);
        AppUtils.addTextFocusChangeListener(binding.edtCity, binding.scrollMainContainer, binding.toolbar.appBar);
        AppUtils.addTextFocusChangeListener(binding.edtState, binding.scrollMainContainer, binding.toolbar.appBar);
        AppUtils.addTextFocusChangeListener(binding.edtPin, binding.scrollMainContainer, binding.toolbar.appBar);
        AppUtils.addTextFocusChangeListener(binding.edtCountry, binding.scrollMainContainer, binding.toolbar.appBar);
    }

    private void setClickListeners() {
        if (TextUtils.isEmpty(from) || !from.equalsIgnoreCase(OrderDetailsReviewActivity.class.getName())) {
            if (!isShippingAddress) {
                binding.checkBoxUseSameForShip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            binding.btnProceed.setText(R.string.btn_place_order);
                        } else {
                            binding.btnProceed.setText(R.string.btn_continue);
                        }
                    }
                });
            }
        }
        binding.btnProceed.setOnClickListener(this);
        binding.tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(AddAddressActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                Calendar date = Calendar.getInstance();
                                date.set(Calendar.HOUR_OF_DAY, 9);
                                date.set(Calendar.MINUTE, 0);
                                date.set(Calendar.YEAR, year);
                                date.set(Calendar.MONTH, month + 1);
                                date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                calendar = date;
                                address.setDate(calendar);
                                setDate(date);

                            }
                        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() + (86400000 * 3));
                datePickerDialog.show();
            }
        });

    }

    private void setDate(Calendar date) {
        isDateSeletected = true;
        address.setDate(date);
        int monthCC = date.get(Calendar.MONTH)+1;
        String dateTime = date.get(Calendar.YEAR) + "" + ((monthCC < 10) ? "0" + monthCC : monthCC) + "" + date.get(Calendar.DAY_OF_MONTH);
        String dateCurrent = DateTimeUtils.parseDateTime(dateTime, "yyyyMMdd", "dd-MMM-yyyy");
        binding.tvDate.setText(dateCurrent);
        binding.iedtDate.setErrorEnabled(false);
    }

    @Override
    public void setAddress(Address address) {
        if (address != null) {
            AppUtils.errorLog(getClass().getSimpleName(), " setAddress " + address);
           // binding.edtFirstName.setText(address.getFname());
            binding.edtFirstName.setText(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_NAME,address.getFname()));

            //binding.edtFirstName.setText(address.getFname());
            //binding.edtLastName.setText(address.getLname());
            binding.edtMobileNo.setText(address.getMobileNo());
            binding.edtAddresLine1.setText(address.getAddressLine1());
            binding.edtAddresLine2.setText(address.getAddressLine2());
            binding.edtCity.setText(address.getCity());
            binding.edtState.setText(address.getState());
            binding.edtPin.setText(address.getPin());
            binding.edtCountry.setText(address.getCountry());
            binding.checkBoxUseSameForShip.setChecked(address.getIsUseSameForShipping());
            setDate(address.getDate());
//            setTime(address.getDate());
//            setTime(address.getDate());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnProceed:
                if (isValidData()) {
                    setValuesToModel();
                    if (TextUtils.isEmpty(from) || !from.equalsIgnoreCase(OrderDetailsReviewActivity.class.getName())) {
                        if (!isShippingAddress) {
                            if (binding.checkBoxUseSameForShip.isChecked()) {
                                presenter.saveAndGotoPlaceOrder(address);
                            } else {
                                presenter.saveAddress(address);
                            }
                        } else {
                            presenter.saveAndGotoPlaceOrder(address);
                        }
                    } else {
                        presenter.saveAddressAndFinish(address);
                    }
                }

        }
    }

    @Override
    public void gotoOrderPreview() {
        Intent intent = new Intent(this, OrderDetailsReviewActivity.class)
                .putExtra(OrderDetailsReviewActivity.IS_ORDER_PREVIEW, true)
                .putExtra(ADDRESS, address);
        startActivity(intent);
    }

    @Override
    public void goToShippingAddressScreen() {
        Intent intent = new Intent(this, AddAddressActivity.class);
        intent.putExtra(ARG_IS_SHIPPING_ADDRESS, true);
        startActivity(intent);
        finish();
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void setValuesToModel() {
        address.setFname(binding.edtFirstName.getText().toString());
       // address.setLname(binding.edtLastName.getText().toString());
        address.setMobileNo(binding.edtMobileNo.getText().toString());
        address.setAddressLine1(binding.edtAddresLine1.getText().toString());
        address.setAddressLine2(binding.edtAddresLine2.getText().toString());
        address.setCity(binding.edtCity.getText().toString());
        address.setState(binding.edtState.getText().toString());
        address.setPin(binding.edtPin.getText().toString());
        address.setCountry(binding.edtCountry.getText().toString());
        address.setIsUseSameForShipping(binding.checkBoxUseSameForShip.isChecked());
        address.setDate(address.getDate());
        String addJson = AppUtils.getStringFromObj(address);
        Log.e("AddressActivity", "addJson  " + addJson);
        DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.DELIVERY_ADDRESS, addJson);
    }

    private Calendar getTime(Calendar cal) {
        int item = binding.spTime.getSelectedItemPosition();
        Log.e("AddressActivity", "getTime  " + item);

        if (item == 0) {
            cal.set(Calendar.HOUR_OF_DAY, 8);
        } else if (item == 1) {
            cal.set(Calendar.HOUR_OF_DAY, 12);
        } else {
            cal.set(Calendar.HOUR_OF_DAY, 16);
        }
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    private void setTime(Calendar cal) {
        Log.e("AddressActivity", "getTime  " + Calendar.HOUR_OF_DAY);
        if (cal.get(Calendar.HOUR_OF_DAY) == 8) {
            binding.spTime.setSelection(0);
        } else if (cal.get(Calendar.HOUR_OF_DAY) == 12) {
            binding.spTime.setSelection(1);
        } else {
            binding.spTime.setSelection(2);
        }
    }

    public boolean isValidData() {
        if (binding.edtFirstName.getText() == null || TextUtils.isEmpty(binding.edtFirstName.getText().toString().trim())) {
            binding.iedtFirstName.setError(getString(R.string.val_enter_first_name));
            binding.edtFirstName.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.edtFirstName.getY());
            return false;
        }
        /*else if (binding.edtLastName.getText() == null || TextUtils.isEmpty(binding.edtLastName.getText().toString().trim())) {
            binding.iedtLastName.setError(getString(R.string.val_enter_last_name));
            binding.edtLastName.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.iedtLastName.getY());
            return false;
        }*/
        else if (binding.edtMobileNo.getText() == null || TextUtils.isEmpty(binding.edtMobileNo.getText().toString().trim())) {
            binding.iedtMobileNo.setError(getString(R.string.val_enter_mobile_no));
            binding.edtMobileNo.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.edtMobileNo.getY());
            return false;
        } else if (AppUtils.validateMobileNoLength(binding.edtMobileNo.getText().toString().trim().length())) {
            binding.iedtMobileNo.setError(getString(R.string.val_enter_valid_mobile_no));
            binding.edtMobileNo.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.edtMobileNo.getY());
            return false;
        } else if (binding.edtAddresLine1.getText() == null || TextUtils.isEmpty(binding.edtAddresLine1.getText().toString().trim())) {
            binding.iedtAddresLine1.setError(getString(R.string.val_enter_address_line1));
            binding.edtAddresLine1.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.edtAddresLine1.getY());
            return false;
        } else if (binding.edtAddresLine2.getText() == null || TextUtils.isEmpty(binding.edtAddresLine2.getText().toString().trim())) {
            binding.iedtAddresLine2.setError(getString(R.string.val_enter_address_line2));
            binding.edtAddresLine2.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.edtAddresLine2.getY());
            return false;
        } else if (binding.edtCity.getText() == null || TextUtils.isEmpty(binding.edtCity.getText().toString().trim())) {
            binding.iedtCity.setError(getString(R.string.val_enter_city));
            binding.edtCity.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.edtCity.getY());
            return false;
        } else if (binding.edtState.getText() == null || TextUtils.isEmpty(binding.edtState.getText().toString().trim())) {
            binding.iedtState.setError(getString(R.string.val_enter_state));
            binding.edtState.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.edtState.getY());
            return false;
        } else if (binding.edtPin.getText() == null || TextUtils.isEmpty(binding.edtPin.getText().toString().trim())) {
            binding.iedtPin.setError(getString(R.string.val_enter_pin));
            binding.edtPin.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.edtPin.getY());
            return false;
        } else if (binding.edtPin.getText().toString().trim().length() < 3) {
            binding.iedtPin.setError(getString(R.string.val_enter_valid_pin));
            binding.edtPin.requestFocus();
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.edtPin.getY());
            return false;
        } else if (!isDateSeletected) {
            binding.iedtDate.setError(getString(R.string.val_select_date));
            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.iedtDate.getY());
            return false;
        }
//        else if (binding.edtCountry.getText() == null || TextUtils.isEmpty(binding.edtCountry.getText().toString().trim())) {
//            binding.iedtCountry.setError(getString(R.string.val_enter_country));
//            binding.edtCountry.requestFocus();
//            binding.scrollMainContainer.smoothScrollTo(0, (int) binding.iedtCountry.getY());
//            return false;
//        }
        return true;
    }

    @Override
    protected void onDestroy() {
        hideProgressBar();
        presenter.onDetach();
        super.onDestroy();
    }
}

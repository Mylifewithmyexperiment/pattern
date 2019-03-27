package com.sterlite.dccmappfordealersterlite.activity.PaymentHistory;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.adapter.PaymentHistoryAdapter;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityPaymentHistoryBinding;
import com.sterlite.dccmappfordealersterlite.model.bssrest.PaymentHistory.PaymentDetailData;


public class PaymentHistoryActivity extends BaseActivity implements PaymentHistoryContract.View {


    ActivityPaymentHistoryBinding binding;
    PaymentHistoryContract.Presenter<PaymentHistoryContract.View> presenter;
    ArrayList<PaymentDetailData> paymentDetailDatas;
    PaymentHistoryAdapter adapter;
    private static final String START = "start";
    private static final String END = "end";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        setAppTheme();
        init();
    }

    private void init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_history);
        setUpNavigationView(binding.drawerLayout, binding.layoutNavMenu);

        setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.title_payment_history), true);
        setUpView(true);
        setupDatePicker();
        setClickListners();
        presenter = new PaymentHistoryPresenter<>();
        presenter.onAttach(this);
        presenter.init();
    }

    private void setupDatePicker() {
        Calendar ca = Calendar.getInstance();


        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yy");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");

        try {
            int month = ca.get(Calendar.MONTH);
            int year = ca.get(Calendar.YEAR);
            int date = ca.get(Calendar.DATE);
            ca.set(year, month, date);
            binding.tvEndDate.setText(format.format(ca.getTime()));
            binding.tvEndDate.setTag(format2.format(ca.getTime()));
            Log.e("tv_EndDate", binding.tvEndDate.getText().toString());

            int date1 = 1;
            ca.set(year, month, date1);
            binding.tvStartDate.setText(format.format(ca.getTime()));
            binding.tvStartDate.setTag(format2.format(ca.getTime()));
            Log.e("tv_StartDate", binding.tvStartDate.getText().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setClickListners() {
        binding.tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidData()) {
                    presenter.loadMoreRecords(binding.tvStartDate.getTag().toString(), binding.tvEndDate.getTag().toString());
                }
            }
        });
        binding.tvStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment newFragment = new DatePickerFragment();
                newFragment.setDateType(START);
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        binding.tvEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment newFragment = new DatePickerFragment();
                newFragment.setDateType(END);
                newFragment.show(getSupportFragmentManager(), "datePicker");            }
        });

    }

    private boolean isValidData() {
        Date date1 = new Date(), date2 = new Date(), date3 = new Date();
        String dateStart = binding.tvStartDate.getTag().toString();
        String dateEnd = binding.tvEndDate.getTag().toString();

        Log.e("start date", dateStart);
        Log.e("end date", dateEnd);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateandTime = dateFormat.format(new Date());
        try {
            date1 = dateFormat.parse(dateStart);
            date2 = dateFormat.parse(dateEnd);


            Log.e("Date1", date1.toString() + " ");
            Log.e("Date2", date2.toString() + " ");

        } catch (ParseException e) {
            Log.e("Exception", e.getMessage());
        }


        if (binding.tvStartDate.getText().equals("")) {
            AppUtils.showToast(this,"Start Date cannot be empty");
            return false;
        }
        if (binding.tvEndDate.getText().equals("")) {
            AppUtils.showToast(this,"End Date cannot be empty");
            return false;
        }

        if (date2.before(date1)) {
            AppUtils.showToast(this,"End Date cannot be before Start Date");
            return false;
        }
        return true;
    }
    @Override
    public void setUpView(boolean isReset) {
        if (isReset) {
            adapter = null;
        }
        AppUtils.setVisibility(binding.tvNotFound, View.GONE);

        if (adapter == null) {
            adapter = new PaymentHistoryAdapter(this);
            binding.rvMyOrders.setAdapter(adapter);
        } else {
            binding.rvMyOrders.setAdapter(adapter);
        }

    }


    @Override
    public void loadDataToView(ArrayList<PaymentDetailData> list) {
        adapter.getAll().clear();
        adapter.addItems(list);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void getPaymentDetailDatas(ArrayList<PaymentDetailData> list) {
       paymentDetailDatas = list;
       if(!list.isEmpty()){
           presenter.loadMoreRecords(binding.tvStartDate.getTag().toString(),binding.tvStartDate.getTag().toString());
       }
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
    public void onRetryClicked() {
        presenter.reset(binding.tvStartDate.getTag().toString(), binding.tvStartDate.getTag().toString());
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


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        private String dateType;

        public String getDateType() {
            return dateType;
        }

        public void setDateType(String dateType) {
            this.dateType = dateType;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog;

            if (getDateType().equals(START)) {
                datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, 1);
            } else {
                datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
            }
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
            // Create a new instance of DatePickerDialog and return it
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int yearPick, int monthPick, int dayPick) {
            // Do something with the date chosen by the user

            int month = monthPick + 1;
            String month1 = "";
            String monthString;
           /* if ((monthPick + 1) < 10)
                month = (monthPick + 1);
            else
                month = (monthPick + 1);
*/

            if ((monthPick + 1) < 10)
                month1 = "0" + (monthPick + 1);
            else
                month1 = String.valueOf((monthPick + 1));

            switch (month) {
                case 1:
                    monthString = "Jan";
                    break;
                case 2:
                    monthString = "Feb";
                    break;
                case 3:
                    monthString = "Mar";
                    break;
                case 4:
                    monthString = "Apr";
                    break;
                case 5:
                    monthString = "May";
                    break;
                case 6:
                    monthString = "Jun";
                    break;
                case 7:
                    monthString = "Jul";
                    break;
                case 8:
                    monthString = "Aug";
                    break;
                case 9:
                    monthString = "Sep";
                    break;
                case 10:
                    monthString = "Oct";
                    break;
                case 11:
                    monthString = "Nov";
                    break;
                case 12:
                    monthString = "Dec";
                    break;
                default:
                    monthString = "Invalid month";
                    break;
            }


            String day = "";
            if (dayPick < 10)
                day = "0" + dayPick;
            else
                day = String.valueOf(dayPick);

            TextView date;
            if (getDateType().equals(START)) {
                date = ((TextView) getActivity().findViewById(R.id.tv_StartDate));
                date.setText(day + "-" + monthString + "-" + yearPick);
                date.setTag(yearPick + "-" + month1 + "-" + day);

            } else {
                date = ((TextView) getActivity().findViewById(R.id.tv_EndDate));
                date.setText(day + "-" + monthString + "-" + yearPick);
                date.setTag(yearPick + "-" + month1 + "-" + day);
            }
        }
    }
}

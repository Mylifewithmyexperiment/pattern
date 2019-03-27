package com.sterlite.dccmappfordealersterlite.activity.InputPaymentInformation;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintSet;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.activity.OrederReceiveSuccess.OrderReceiveSuccess;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityInputPaymentInformationBinding;

public class InputPaymentInformationActivity extends BaseActivity implements InputPaymentInformationContract.View {
    String TAG = InputPaymentInformationActivity.class.getSimpleName();
    ActivityInputPaymentInformationBinding binding;
    InputPaymentInformationContract.Presenter<InputPaymentInformationContract.View> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAppTheme();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_input_payment_information);
        setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.title_payment_information), true);
        setListeners();

        presenter = new InputPaymentInformationPresenter<>();
        presenter.onAttach(this);
    }

    private void setListeners() {
        AppUtils.addTextChangedListener(binding.edtCardNumber, binding.iedtCardNumber);
        AppUtils.addTextChangedListener(binding.edtExpiryDate, binding.iedtExpiryDate);
        AppUtils.addTextChangedListener(binding.edtCvv, binding.iedtCvv);
        AppUtils.addTextFocusChangeListener(binding.edtCardNumber, binding.scrollMainContainer, binding.toolbar.appBar);
        AppUtils.addTextFocusChangeListener(binding.edtExpiryDate, binding.scrollMainContainer, binding.toolbar.appBar);
        AppUtils.addTextFocusChangeListener(binding.edtCvv, binding.scrollMainContainer, binding.toolbar.appBar);
        binding.edtCardNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    binding.edtExpiryDate.requestFocus();
                    return true;
                }
                return false;
            }
        });
        binding.edtExpiryDate.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    binding.edtCvv.requestFocus();
                    return true;
                }
                return false;
            }
        });
        binding.edtExpiryDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    formatCardExpiringDate(s);
                } catch (NumberFormatException e) {
                    s.clear();
                }
            }

            boolean isSlash = false; //class level initialization

            private void formatCardExpiringDate(Editable s) {
                String input = s.toString();
                String mLastInput = "";

                SimpleDateFormat formatter = new SimpleDateFormat("MM/yy");
                Calendar expiryDateDate = Calendar.getInstance();

                try {
                    expiryDateDate.setTime(formatter.parse(input));
                } catch (java.text.ParseException e) {
                    if (s.length() == 2 && !mLastInput.endsWith("/") && isSlash) {
                        isSlash = false;
                        int month = Integer.parseInt(input);
                        if (0 < month && month <= 12) {
                            binding.edtExpiryDate.setText(binding.edtExpiryDate.getText().toString().substring(0, 1));
                            binding.edtExpiryDate.setSelection(binding.edtExpiryDate.getText().toString().length());
                        } else {
                            s.clear();
                            binding.edtExpiryDate.setText("");
                            binding.edtExpiryDate.setSelection(binding.edtExpiryDate.getText().toString().length());
                            Toast.makeText(InputPaymentInformationActivity.this, "Enter a valid month", Toast.LENGTH_LONG).show();
                        }
                    } else if (s.length() == 2 && !mLastInput.endsWith("/") && !isSlash) {
                        isSlash = true;
                        int month = Integer.parseInt(input);
                        if (0 < month && month <= 12) {
                            binding.edtExpiryDate.setText(binding.edtExpiryDate.getText().toString() + "/");
                            binding.edtExpiryDate.setSelection(binding.edtExpiryDate.getText().toString().length());
                        } else {
                            binding.edtExpiryDate.setText("");
                            binding.edtExpiryDate.setSelection(binding.edtExpiryDate.getText().toString().length());
                            s.clear();
                        }


                    } else if (s.length() == 1) {

                        int month = Integer.parseInt(input);
                        if (month > 1 && month < 12) {
                            isSlash = true;
                            binding.edtExpiryDate.setText("0" + binding.edtExpiryDate.getText().toString() + "/");
                            binding.edtExpiryDate.setSelection(binding.edtExpiryDate.getText().toString().length());
                        }
                    }

                    mLastInput = binding.edtExpiryDate.getText().toString();
                    return;
                }
            }
        });
        final ConstraintSet constraintOrignalSet=new ConstraintSet();
        constraintOrignalSet.clone(binding.constraintMainContainer);
        final ConstraintSet constraintSetMasterCardGone=new ConstraintSet();
        constraintSetMasterCardGone.clone(binding.constraintMainContainer);
        constraintSetMasterCardGone.setVisibility(R.id.ivMastercard,ConstraintSet.GONE);
        final ConstraintSet visaGone=new ConstraintSet();
        visaGone.clone(binding.constraintMainContainer);
        visaGone.setVisibility(R.id.ivVisaCard,ConstraintSet.GONE);
        binding.edtCardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                Log.d(TAG, "onTextChanged char=" + charSequence + " length="+charSequence.length()+" start=" + start + " count=" + count + " after=" + after);
                if(charSequence.length()>=2){
                    Log.d(TAG,"substring"+charSequence.toString().substring(0, 2));
                }
                if (start == 0 && charSequence.length() > 0 && charSequence.charAt(0) == '4') {
                    binding.ivMastercard.setVisibility(View.GONE);
//                    TransitionManager.beginDelayedTransition(binding.constraintMainContainer);
//                    constraintSetMasterCardGone.applyTo(binding.constraintMainContainer);
                } else if ((start == 0 || start == 1) && charSequence.length() >= 2 &&
                        (charSequence.toString().substring(0, 2).equalsIgnoreCase("51") ||
                                charSequence.toString().substring(0, 2).equalsIgnoreCase("52") ||
                                charSequence.toString().substring(0, 2).equalsIgnoreCase("53") ||
                                charSequence.toString().substring(0, 2).equalsIgnoreCase("54") ||
                                charSequence.toString().substring(0, 2).equalsIgnoreCase("55"))) {
//                    TransitionManager.beginDelayedTransition(binding.constraintMainContainer);
//                    visaGone.applyTo(binding.constraintMainContainer);
                    binding.ivVisaCard.setVisibility(View.GONE);
                } else if (start == 0) {
//                    TransitionManager.beginDelayedTransition(binding.constraintMainContainer);
//                    constraintOrignalSet.applyTo(binding.constraintMainContainer);
                    binding.ivVisaCard.setVisibility(View.VISIBLE);
                    binding.ivMastercard.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        setClickListners();
    }

    private void setClickListners() {
        binding.btnMakePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidData()) {
                    AppUtils.hideKeyboard(InputPaymentInformationActivity.this);

                    binding.constraintMainContainer.setVisibility(View.INVISIBLE);
                    binding.constraintProgressContainer.setVisibility(View.VISIBLE);
//                    binding.grpProgressBarContrainer.setVisibility(View.VISIBLE);
//                    binding.grpMainScreen.setVisibility(View.INVISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(InputPaymentInformationActivity.this, OrderReceiveSuccess.class);
                            intent.putExtras(getIntent());
                            startActivity(intent);
                        }
                    },2000);
                }
            }
        });
    }

    private boolean isValidData() {
        if (TextUtils.isEmpty(binding.edtCardNumber.getText().toString())) {
            binding.iedtCardNumber.setError(getString(R.string.val_enter_card_number));
            return false;
        } else if (binding.edtCardNumber.getText().toString().length() != 16) {
            binding.iedtCardNumber.setError(getString(R.string.val_enter_valid_card_number));
            return false;
        } else if (TextUtils.isEmpty(binding.edtExpiryDate.getText().toString())) {
            binding.iedtExpiryDate.setError(getString(R.string.val_enter_expiry_date));
            return false;
        } else if (!isValidDate(binding.edtExpiryDate.getText().toString())) {
            binding.iedtExpiryDate.setError(getString(R.string.val_enter_valid_expiry_date));
            return false;
        } else if (TextUtils.isEmpty(binding.edtCvv.getText().toString())) {
            binding.iedtCvv.setError(getString(R.string.val_enter_cvv));
            return false;
        } else if (binding.edtCvv.getText().toString().length() != 3) {
            binding.iedtCvv.setError(getString(R.string.val_enter_valid_cvv));
            return false;
        }
        return true;
    }

    public static boolean isValidDate(String pDateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yy");
            dateFormat.setLenient(true);
            Date date=dateFormat.parse(pDateString);
            return new Date().before(date);
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public void onBackPressed()

    {
        Intent intent= getIntent();
        String ARG_FROM=intent.getStringExtra(OrderReceiveSuccess.ARG_FROM);
        if(ARG_FROM!=null && ARG_FROM.equalsIgnoreCase(Constants.PLACEORDER))
        {
            AppUtils.showToast(this,getString(R.string.lbl_payment_incomplete));
        }
    }
}

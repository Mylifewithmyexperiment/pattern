package com.sterlite.dccmappfordealersterlite.fragment.cart;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.activity.Kyc.KycActivity;
import com.sterlite.dccmappfordealersterlite.activity.pincode.PincodeActivity;
import com.sterlite.dccmappfordealersterlite.adapter.CartAdapter;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BaseFragment;
import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.databinding.FragmentCartBinding;
import com.sterlite.dccmappfordealersterlite.model.CartDetails;
import com.sterlite.dccmappfordealersterlite.model.CartItem;


public class CartFragment extends BaseFragment implements CartFragmentContract.View, View.OnClickListener {

    public static CartFragment newInstance() {
        return newInstance(null);
    }

    public static CartFragment newInstance(Bundle bundle) {
        CartFragment fragment = new CartFragment();
        if (bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }


    private FragmentCartBinding binding;
    private CartFragmentContract.Presenter<CartFragmentContract.View> presenter;
    private CartAdapter adapter;
    private CartDetails cartDetails;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(activity, getClass().getSimpleName());
        presenter = new CartFragmentPresenter<>();
        presenter.onAttach(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        activity.setTitle(R.string.menu_cart);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);
        initUI();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.reset();
    }

    public void initUI() {
        adapter = new CartAdapter(getContext(), new OnRecyclerViewItemClickListener<CartItem>() {
            @Override
            public void onClicked(CartItem bean, View view, int position, ViewType viewType) {
                if (viewType == OnRecyclerViewItemClickListener.ViewType.View) {
                    AppUtils.showToast(getContext(), getString(R.string.msg_item_clicked));
                } else if (viewType == ViewType.Add) {
                    if (bean.getMaxQuantity() > bean.getQuantity()) {
                        bean.setQuantity(bean.getQuantity() + 1);
                        adapter.notifyDataSetChanged();
                    }
                } else if (viewType == ViewType.Remove) {
                    if (bean.getQuantity() > 1) {
                        bean.setQuantity(bean.getQuantity() - 1);
                        adapter.notifyDataSetChanged();
                    }
                } else if (viewType == ViewType.Delete) {
                    adapter.remove(position);
                    if (cartDetails != null && cartDetails.getCartItemList() != null && cartDetails.getCartItemList().size() > position) {
                        cartDetails.getCartItemList().remove(position);
                        setCartDetails(cartDetails);
                    }
                }
            }

            @Override
            public void onLastItemReached() {
//todo nothing
            }
        });
        adapter.isAddFooter = false;
        binding.recycleCart.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.recycleCart.setAdapter(adapter);
        setClickListeners();
        presenter.init();
    }

    private void setClickListeners() {
        binding.btncheckout.setOnClickListener(this);
    }

    @Override
    public void onRetryClicked() {
        super.onRetryClicked();
        presenter.reset();
    }

    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setCartDetails(CartDetails cartDetails) {
        this.cartDetails = cartDetails;
        if (cartDetails.getCartItemList() == null || cartDetails.getCartItemList().size() <= 0) {
            binding.lltotal.setVisibility(View.GONE);
            binding.recycleCart.setVisibility(View.GONE);
            binding.tvNoFound.setVisibility(View.VISIBLE);
        } else {
            adapter.getAll().clear();
            adapter.addItems(cartDetails.getCartItemList());
            binding.tvGrandPrice.setText(cartDetails.getGrandTotal());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btncheckout:
                DCCMDealerSterlite.getDataManager().setBoolean(AppPreferencesHelper.PREF_IS_HOME_DELIVERY, binding.rbHomeDelivery.isChecked());
                gotoKycActivity();
                break;
        }
    }

    private void gotoKycActivity() {
        if (binding.rbHomeDelivery.isChecked()) {
            startActivity(new Intent(getContext(), KycActivity.class));
        } else {
            startActivity(new Intent(getContext(), PincodeActivity.class));
        }
    }
}

package com.sterlite.dccmappfordealersterlite.base;


import com.sterlite.dccmappfordealersterlite.Utils.Constants;

/**
 * Created by etech3 on 29/6/18.
 */

public class BasePresenter<V extends BaseContractView> implements BaseContractPresenter<V> {

    private V baseView;

    protected V getView() {
        return baseView;
    }


    @Override
    public void onAttach(V baseView) {
        this.baseView = baseView;
    }

    @Override
    public void onDetach() {
        baseView = null;
    }



    protected void startApiCall() {
        if (getView() == null) return;
        getView().showProgressBar();
        getView().hideConnectionLostView();
    }

    protected void onSuccess() {
        if (getView() == null) return;
        getView().hideProgressBar();
        getView().hideConnectionLostView();
    }

    protected void onFaild(int code, String message) {
        onFaild(code, message, true, true);
    }

    protected void onFaild(int code, String message, boolean isShowDialog, boolean isConnectionView) {
        if (getView() == null) return;
        getView().hideProgressBar();
        if (code == Constants.FAIL_CODE) {
            getView().showError(code, message);
        } else {
            if (code == Constants.FAIL_INTERNET_CODE && isConnectionView) {
                getView().showConnectionLostView();
            }
            if (isShowDialog)
                getView().showError(code, message);
        }
    }
}

package com.sterlite.dccmappfordealersterlite.base;

/**
 * Created by etech3 on 29/6/18.
 */

public interface BaseView {
    void showProgressBar();

    void hideProgressBar();

    void showError(int code, int resId);
}

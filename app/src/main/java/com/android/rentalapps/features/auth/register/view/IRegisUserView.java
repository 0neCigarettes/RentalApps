package com.android.rentalapps.features.auth.register.view;

public interface IRegisUserView {
    void initViews();

    void onRegis();

    void  onRegisSuccess(String mMsg);

    void  onRegisFailed(String mMsg);

    void onNetworkError(String cause);

    void showLoadingIndicator();

    void hideLoadingIndicator();

}

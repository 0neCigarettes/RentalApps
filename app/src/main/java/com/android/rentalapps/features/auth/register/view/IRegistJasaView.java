package com.android.rentalapps.features.auth.register.view;

public interface IRegistJasaView {
    void initViews();

    void onRegis();

    void  onRegirSuccess(String mMsg);

    void  onRegisFailed(String mMsg);

    void onNetworkError(String cause);

    void showLoadingIndicator();

    void hideLoadingIndicator();
}

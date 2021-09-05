package com.android.rentalapps.features.JasaRental.account.profile.view;

public interface IUpdateJasaView {
    void initViews();

    void onSubmit();

    void onNetworkError(String cause);

    void showLoadingIndicator();

    void hideLoadingIndicator();

    void onSubmitSuccses(String mMsg);

    void onSubmitFailed(String mMsg);

    void onGoToDashboard();

    void addPermission();
}

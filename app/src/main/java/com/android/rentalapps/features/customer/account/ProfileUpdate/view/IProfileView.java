package com.android.rentalapps.features.customer.account.ProfileUpdate.view;

public interface IProfileView {
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

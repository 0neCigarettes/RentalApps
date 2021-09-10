package com.android.rentalapps.features.auth.login.view;

import com.android.rentalapps.features.auth.model.LoginResponse;

public interface ILoginView {
    void initViews();

    void onSigninSuccess(LoginResponse response);

    void onSigninFailed(String caus);

    void onNetworkError(String cause);

    void showLoadingIndicator();

    void hideLoadingIndicator();
}

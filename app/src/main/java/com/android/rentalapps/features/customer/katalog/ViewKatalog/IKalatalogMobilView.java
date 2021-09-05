package com.android.rentalapps.features.customer.katalog.ViewKatalog;

import com.android.rentalapps.features.customer.katalog.model.ListMobil;

import java.util.List;

public interface IKalatalogMobilView {
    void initViews();

    void onDataReady(List<ListMobil> ListMobil);

    void onCall();

    void onRefresh();

    void onNetworkError(String cause);

    void showLoadingIndicator();

    void hideLoadingIndicator();

    void onSubmitSuccses(String mMsg);

    void onSubmitFailed(String mMsg);
}

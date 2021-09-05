package com.android.rentalapps.features.JasaRental.home.view;

import com.android.rentalapps.features.JasaRental.home.model.OrderJasa;

import java.util.List;

public interface IHomeJasaView {
    void initViews();

    void onDataReady(List<OrderJasa> listOrder);

    void onNetworkError(String cause);

    void onUpdateSuccess(String mMsg);

    void onUpdateFailed(String mMsg);

    void showLoadingIndicator();

    void hideLoadingIndicator();

    void onRefresh();

}

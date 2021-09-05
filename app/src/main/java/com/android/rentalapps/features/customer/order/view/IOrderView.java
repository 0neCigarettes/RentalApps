package com.android.rentalapps.features.customer.order.view;

import com.android.rentalapps.features.customer.order.model.MyOrder;

import java.util.List;

public interface IOrderView {
    void initViews();

    void onDataReady(List<MyOrder> listOrder);

    void onNetworkError(String cause);

    void showLoadingIndicator();

    void hideLoadingIndicator();
}

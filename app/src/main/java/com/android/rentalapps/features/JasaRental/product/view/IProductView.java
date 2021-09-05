package com.android.rentalapps.features.JasaRental.product.view;

import com.android.rentalapps.features.JasaRental.product.model.ProductModel;

import java.util.List;

public interface IProductView {
    void initViews();

    void onDataReady(List<ProductModel> listProduct);

    void onRefresh();

    void onDoAddProduct();

    void onDoUpdateProduct();

    void onNetworkError(String cause);

    void showLoadingIndicator();

    void hideLoadingIndicator();

    void onAddSuccess(String mMsg);

    void onAddFailed(String mMsg);

    void onUpdateSuccess(String mMsg);

    void onUpdateFailed(String mMsg);

    void onDeleteSuccess(String mMsg);

    void onDeleteFailed(String mMsg);

    void addPermission();
}

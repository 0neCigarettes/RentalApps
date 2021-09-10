package com.android.rentalapps.features.customer.home.ViewHomeUser;

import android.view.View;

import com.android.rentalapps.features.auth.model.User;
import com.google.android.gms.maps.GoogleMap;

import java.util.List;

public interface IHomeUserView {

    void initViews();

    void setMyLocation();

    void initMyLocationMarker();

    void onMapReady(GoogleMap googleMap);

    void onDataReady(List<User> ListJasa);

    void onGetListJasa(View v);

    void onNetworkError(String cause);

    void showLoadingIndicator();

    void hideLoadingIndicator();
}

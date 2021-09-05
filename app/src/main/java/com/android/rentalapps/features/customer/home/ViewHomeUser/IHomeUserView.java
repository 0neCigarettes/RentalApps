package com.android.rentalapps.features.customer.home.ViewHomeUser;

import com.android.rentalapps.features.customer.home.Model.ListJasaModel;
import com.google.android.gms.maps.GoogleMap;

import java.util.List;

public interface IHomeUserView {

    void initViews();

    void setMyLocation();

    void initMyLocationMarker();

    void onMapReady(GoogleMap googleMap);

    void onDataReady(List<ListJasaModel> ListJasa);

    void onGetListJasa();

    void onNetworkError(String cause);

    void showLoadingIndicator();

    void hideLoadingIndicator();
}

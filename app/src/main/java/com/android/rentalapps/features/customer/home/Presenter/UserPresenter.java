package com.android.rentalapps.features.customer.home.Presenter;

import com.android.rentalapps.features.customer.home.Model.ListJasaResponse;
import com.android.rentalapps.features.customer.home.ViewHomeUser.IHomeUserView;
import com.android.rentalapps.server.NetworkService;
import com.android.rentalapps.server.RestService;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserPresenter {
    final IHomeUserView view;
    public final Retrofit restService;

    public UserPresenter(IHomeUserView view){
        this.view = view;
        restService = RestService.getRetroftInstance();
    }

    public void getListJasa() {
        view.showLoadingIndicator();
        restService.create(NetworkService.class).getListJasa()
                .enqueue(new Callback<ListJasaResponse>() {
                    @Override
                    public void onResponse(Call<ListJasaResponse> call, Response<ListJasaResponse> response) {
                        view.hideLoadingIndicator();
                        System.out.println(new Gson().toJson(response.body()));
                        if (response.body().getmStatus())
                            view.onDataReady(response.body().getmResult());
                    }

                    @Override
                    public void onFailure(Call<ListJasaResponse> call, Throwable t) {
                        view.hideLoadingIndicator();
                        view.onNetworkError(t.getLocalizedMessage());
                    }
                });
    }

}

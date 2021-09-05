package com.android.rentalapps.features.JasaRental.home.presenter;

import com.android.rentalapps.features.JasaRental.home.model.OrderResponse;
import com.android.rentalapps.features.JasaRental.home.view.IHomeJasaView;
import com.android.rentalapps.model.CommonResponse;
import com.android.rentalapps.server.NetworkService;
import com.android.rentalapps.server.RestService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeJasaPresenter {
    final IHomeJasaView view;
    public final Retrofit restService;

    public HomeJasaPresenter(IHomeJasaView view) {
        this.view = view;
        this.restService = RestService.getRetroftInstance();
    }

    public void getOrders(String id) {
        view.showLoadingIndicator();
        restService.create(NetworkService.class).getOrders(id)
                .enqueue(new Callback<OrderResponse>() {
                    @Override
                    public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                        view.hideLoadingIndicator();
                        if (response.body().getmStatus())
                            view.onDataReady(response.body().getmResult());
                    }

                    @Override
                    public void onFailure(Call<OrderResponse> call, Throwable t) {
                        view.hideLoadingIndicator();
                        view.onNetworkError(t.getLocalizedMessage());
                    }
                });
    }

    public void updateStatusOrder(String id, String key) {
        view.showLoadingIndicator();
        restService.create(NetworkService.class).updateStatusOrder(id, key)
                .enqueue(new Callback<CommonResponse>() {
                    @Override
                    public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                        view.hideLoadingIndicator();
                        if (response.body().getmStatus()){
                            view.onUpdateSuccess(response.body().getmMsg());
                        } else {
                            view.onUpdateFailed(response.body().getmMsg());
                        }
                    }

                    @Override
                    public void onFailure(Call<CommonResponse> call, Throwable t) {
                        view.hideLoadingIndicator();
                        view.onNetworkError(t.getLocalizedMessage());
                    }
                });
    }
}

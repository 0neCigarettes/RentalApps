package com.android.rentalapps.features.customer.katalog.presenter;

import com.android.rentalapps.model.CommonResponse;
import com.android.rentalapps.server.NetworkService;
import com.android.rentalapps.server.RestService;
import com.android.rentalapps.features.customer.katalog.ViewKatalog.IKalatalogMobilView;
import com.android.rentalapps.features.customer.katalog.model.ListMobilResponse;
import com.android.rentalapps.features.customer.katalog.model.Order;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class KatalogPresenter {
    final IKalatalogMobilView view;
    public final Retrofit restService;

    public KatalogPresenter(IKalatalogMobilView view){
        this.view = view;
        restService = RestService.getRetroftInstance();
    }

    public void getListMobil(String id) {
        view.showLoadingIndicator();
        restService.create(NetworkService.class).getListMobil(id)
                .enqueue(new Callback<ListMobilResponse>() {
                    @Override
                    public void onResponse(Call<ListMobilResponse> call, Response<ListMobilResponse> response) {
                        view.hideLoadingIndicator();
                        if (response.body().getmStatus())
                            view.onDataReady(response.body().getmResult());
                    }

                    @Override
                    public void onFailure(Call<ListMobilResponse> call, Throwable t) {
                        view.hideLoadingIndicator();
                        view.onNetworkError(t.getLocalizedMessage());
                    }
                });
    }

    public void userOrder(Order userOrder) {
        view.showLoadingIndicator();
        restService.create(NetworkService.class).userOrder(userOrder)
                .enqueue(new Callback<CommonResponse>() {
                    @Override
                    public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                        view.hideLoadingIndicator();
                        if (response.body().getmStatus()) {
                            view.onSubmitSuccses(response.body().getmMsg());
                        } else {
                            view.onSubmitFailed(response.body().getmMsg());
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

package com.android.rentalapps.features.customer.order.presenter;

import com.android.rentalapps.server.NetworkService;
import com.android.rentalapps.server.RestService;
import com.android.rentalapps.features.customer.order.model.MyOrderResponse;
import com.android.rentalapps.features.customer.order.view.IOrderView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrderPresenter {
    final IOrderView view;
    public final Retrofit restService;

    public OrderPresenter(IOrderView view){
        this.view = view;
        restService = RestService.getRetroftInstance();
    }

    public void getMyOrder(String id) {
        view.showLoadingIndicator();
        restService.create(NetworkService.class).getMyOrder(id)
                .enqueue(new Callback<MyOrderResponse>() {
                    @Override
                    public void onResponse(Call<MyOrderResponse> call, Response<MyOrderResponse> response) {
                        view.hideLoadingIndicator();
//                        System.out.println(new Gson().toJson(response.body()));
                        if (response.body().getmStatus()) {
                            view.onDataReady(response.body().getmResult());
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<MyOrderResponse> call, Throwable t) {
                        view.hideLoadingIndicator();
                        view.onNetworkError(t.getLocalizedMessage());
                    }
                });
    }
}

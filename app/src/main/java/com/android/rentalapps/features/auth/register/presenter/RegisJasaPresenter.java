package com.android.rentalapps.features.auth.register.presenter;

import com.android.rentalapps.features.auth.model.User;
import com.android.rentalapps.features.auth.register.view.IRegistJasaView;
import com.android.rentalapps.model.CommonResponse;
import com.android.rentalapps.server.NetworkService;
import com.android.rentalapps.server.RestService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisJasaPresenter {
    final IRegistJasaView view;
    public final Retrofit restService;

    public RegisJasaPresenter(IRegistJasaView view) {
        this.view = view;
        restService = RestService.getRetroftInstance();
    }

    public void regisJasa(User model) {
        view.showLoadingIndicator();
        restService.create(NetworkService.class).signup(model)
                .enqueue(new Callback<CommonResponse>() {
                    @Override
                    public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                        view.hideLoadingIndicator();
                        if (response.body().getSuccess()){
                            view.onRegirSuccess(response.body().getmMsg());
                        }else {
                            view.onRegisFailed(response.body().getmMsg());
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
